package com.example.tamagotchiar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tamagotchiar.db.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.tamagotchiar.LoginActivity.LOGGED_EMAIL;

public class MainMenuActivity extends AppCompatActivity {
    private static final String NEW_WALK = "NEW_WALK" ;
    DatabaseHelper database;
    @BindView(R.id.arButton)
    Button arButton;
    @BindView(R.id.walkButton)
    Button walkButton;
    @BindView(R.id.hungerProgressBar)
    ProgressBar hungerProgressBar;
    @BindView(R.id.currentHungerTextView)
    TextView currentHungerTextView;
    @BindView(R.id.feedButton)
    Button feedButton;
    @BindView(R.id.happyProgressBar)
    ProgressBar happyProgressBar;
    @BindView(R.id.currentHappinessTextView)
    TextView currentHappinessTextView;
    @BindView(R.id.shopButton)
    Button shopButton;
    @BindView(R.id.currentFoodTextView)
    TextView currentFoodTextView;
    @BindView(R.id.currentCoinsTextView)
    TextView currentCoinsTextView;
    @BindView(R.id.userTextView)
    TextView userTextView;
    @BindView(R.id.petTextView)
    TextView petTextView;

    private String firstname;
    private String petName;
    private String lastModifiedData;
    private String currentDate;
    private Integer hunger;
    private Integer happiness;
    private Integer food;
    private Integer coins;
    private String userEmail;
    private Boolean zero = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);
        userEmail = getIntent().getStringExtra(LOGGED_EMAIL);
        database = new DatabaseHelper(this);

        setData();
        setTextsAndBars();
        hungerThread();

    }

    private void setTextsAndBars() {
        userTextView.setText(getString(R.string.welcome) + firstname);
        petTextView.setText(getString(R.string.thats)
                + petName + getString(R.string.stats));
        happyProgressBar.setProgress(happiness);
        hungerProgressBar.setProgress(hunger);
        currentHungerTextView.setText(String.valueOf(hunger));
        currentHappinessTextView.setText(String.valueOf(happiness));
        currentFoodTextView.setText(String.valueOf(food));
        currentCoinsTextView.setText(String.valueOf(coins));
    }

    private void setData() {
        firstname = database.getFirstNameFromDatabase(userEmail);
        petName = database.getPetNameFromDatabase(userEmail);
        hunger = database.getHungerFromDatabase(userEmail);
        happiness = database.getHappinessFromDatabase(userEmail);
        food = database.getFoodFromDatabase(userEmail);
        coins = database.getCoinsFromDatabase(userEmail);
        lastModifiedData = database.getDateFromDatabase(userEmail);
        currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        if(currentDate != lastModifiedData){
            zero = true;
            database.updateDateInDatabase(userEmail,currentDate);
        }
    }

    @OnClick(R.id.arButton)
    public void onArButtonClicked() {
        Intent intent = new Intent(this, ArActivity.class);
        intent.putExtra(LOGGED_EMAIL, userEmail);
        startActivity(intent);
    }

    @OnClick(R.id.walkButton)
    public void onWalkButtonClicked() {
        Intent intent = new Intent(this, WalkingActivity.class);
        intent.putExtra(LOGGED_EMAIL, userEmail);
        intent.putExtra(NEW_WALK,zero);
        startActivity(intent);
    }

    private void hungerThread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                coins = database.getCoinsFromDatabase(userEmail);
                                happiness = database.getHappinessFromDatabase(userEmail);
                                setTextsAndBars();
                                hungerChange();
                                happinessChange();
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        thread.start();
    }


    @OnClick(R.id.feedButton)
    public void feedButtonClicked() {
        if (food > 0) {
            hunger = hunger + 20;
            food--;
            currentFoodTextView.setText(String.valueOf(food));
            hungerProgressBar.setProgress(hunger);
            if (hunger > 100)
                hunger = 100;
            currentHungerTextView.setText(String.valueOf(hunger));
        }
        database.updateHungerInDatabase(userEmail, hunger);
        database.updateFoodInDatabase(userEmail, food);
    }

    private void hungerChange() {

        hungerWarning();
        hunger--;
        if (hunger < 0)
            hunger = 0;

        database.updateHungerInDatabase(userEmail, hunger);
    }
    private void hungerWarning() {
        if (hunger == 25)
            Toast.makeText(getApplicationContext(), R.string.hungry_pet, Toast.LENGTH_LONG).show();

        if (hunger <= 25)
            currentHungerTextView.setTextColor(Color.RED);
        else
            currentHungerTextView.setTextColor(Color.BLACK);

    }

    private void happinessChange() {

        happinessWarning();
        happiness -= 2;
        if (happiness < 0)
            happiness = 0;

        database.updateHappinessInDatabase(userEmail, happiness);
    }
    private void happinessWarning() {
        if (happiness == 25)
            Toast.makeText(getApplicationContext(), R.string.unhappy_pet, Toast.LENGTH_LONG).show();

        if (happiness <= 25)
            currentHappinessTextView.setTextColor(Color.RED);
        else
            currentHappinessTextView.setTextColor(Color.BLACK);
    }

    @OnClick(R.id.shopButton)
    public void shopButtonClicked() {
        if (coins >= 20) {
            coins -= 20;
            food++;
            currentCoinsTextView.setText(String.valueOf(coins));
            currentFoodTextView.setText(String.valueOf(food));

            database.updateCoinsInDatabase(userEmail, coins);
            database.updateFoodInDatabase(userEmail, food);
        }
    }
}
