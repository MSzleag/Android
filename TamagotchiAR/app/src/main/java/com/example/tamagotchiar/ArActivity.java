package com.example.tamagotchiar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tamagotchiar.ar.CustomArFragment;
import com.example.tamagotchiar.db.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;

import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.tamagotchiar.LoginActivity.LOGGED_EMAIL;

public class ArActivity extends AppCompatActivity implements Scene.OnUpdateListener {
    DatabaseHelper database;
    @BindView(R.id.feedButton)
    FloatingActionButton feedButton;
    private CustomArFragment arFragment;
    private String userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);
        ButterKnife.bind(this);
        userEmail = getIntent().getStringExtra(LOGGED_EMAIL);
        database = new DatabaseHelper(this);
        arFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (arFragment != null) {
            arFragment.getArSceneView().getScene().addOnUpdateListener(this);
        }
    }

    public void setupDatabase(Config config, Session session) {
        Bitmap tamagotchiBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tamagotchi_qr);
        Bitmap bowlBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bowl_qr);
        AugmentedImageDatabase augmentedImageDatabase = new AugmentedImageDatabase(session);
        augmentedImageDatabase.addImage("Tamagotchi", tamagotchiBitmap);
        augmentedImageDatabase.addImage("Bowl",bowlBitmap);
        config.setAugmentedImageDatabase(augmentedImageDatabase);
    }

    @Override
    public void onUpdate(FrameTime frameTime) {
        Frame frame = arFragment.getArSceneView().getArFrame();
        Collection<AugmentedImage> augmentedImages = frame.getUpdatedTrackables(AugmentedImage.class);

        for (AugmentedImage augmentedImage : augmentedImages) {
            if (augmentedImage.getTrackingState() == TrackingState.TRACKING) {
                if (augmentedImage.getName().equals("Tamagotchi")) {
                    Anchor anchor = augmentedImage
                            .createAnchor(augmentedImage.getCenterPose());
                    createModel(anchor);
                }
                if(augmentedImage.getName().equals("Bowl")){
                    Anchor anchor = augmentedImage
                            .createAnchor(augmentedImage.getCenterPose());
                    createBowl(anchor);
                }
            }
        }
    }

    private void createBowl(Anchor anchor) {
        ModelRenderable.builder()
                .setSource(this,R.raw.bowl).build().thenAccept(modelRenderable -> placeModel(modelRenderable,anchor));
    }

    private void createModel(Anchor anchor) {
        ModelRenderable.builder()
                .setSource(this, R.raw.fox).build().thenAccept(modelRenderable -> placeBowl(modelRenderable, anchor));
    }


    private void placeModel(ModelRenderable modelRenderable, Anchor anchor) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        anchorNode.setLocalScale(new Vector3(0.4f, 0.4f, 0.4f));
        arFragment.getArSceneView().getScene().addChild(anchorNode);

    }
    private void placeBowl(ModelRenderable modelRenderable, Anchor anchor) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        anchorNode.setLocalScale(new Vector3(0.01f, 0.01f, 0.01f));
        arFragment.getArSceneView().getScene().addChild(anchorNode);

    }

    @OnClick(R.id.feedButton)
    public void addBowlButtonClicked() {
        Integer hunger;
        hunger = database.getHungerFromDatabase(userEmail);
        hunger += 20;
        database.updateHungerInDatabase(userEmail,hunger);
        Toast.makeText(getApplicationContext(),"You fed your pet for 20 points",Toast.LENGTH_SHORT);
    }
}
