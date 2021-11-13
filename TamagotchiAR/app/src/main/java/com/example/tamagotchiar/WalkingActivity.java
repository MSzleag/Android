package com.example.tamagotchiar;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tamagotchiar.db.DatabaseHelper;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.tamagotchiar.LoginActivity.LOGGED_EMAIL;

public class WalkingActivity extends AppCompatActivity implements SensorEventListener {
    private static final String NEW_WALK = "NEW_WALK" ;
    DatabaseHelper database;
    String userEmail;
    @BindView(R.id.walkTextView)
    TextView walkTextView;
    private SensorManager sensorManager;
    private Sensor mStepCounter;
    private boolean isCounterSensorPresent;
    private Boolean walked = false;
    private Boolean zero;
    private int stepCount=0;
    private int stepData;

    @BindView(R.id.progressCircular)
    CircularProgressBar progressCircular;
    @BindView(R.id.stepsTakenTextView)
    TextView stepsTakenTextView;
    @BindView(R.id.maxStepsTextView)
    TextView maxStepsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking);
        database = new DatabaseHelper(this);
        userEmail = getIntent().getStringExtra(LOGGED_EMAIL);
        zero = getIntent().getExtras().getBoolean(NEW_WALK);
        stepData = database.getStepsFromDatabase(userEmail);
        if (zero)
            stepData = 0;


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ButterKnife.bind(this);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            mStepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isCounterSensorPresent = true;
        } else {
            Toast.makeText(getApplicationContext(), R.string.sensor_isnt_working, Toast.LENGTH_LONG).show();
            isCounterSensorPresent = false;
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
            sensorManager.registerListener(this, mStepCounter, SensorManager.SENSOR_DELAY_NORMAL);

    }
    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
            sensorManager.unregisterListener(this, mStepCounter);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == mStepCounter) {
            stepCount = (int) event.values[0];

            database.updateStepsInDatabase(userEmail,stepCount);
            stepsTakenTextView.setText(String.valueOf(stepCount));
            progressCircular.setProgress((float) stepCount);

            if (stepCount >= 6000) {
                walkTextView.setText(R.string.walked_as_pet_need);
                if(stepCount == 6300){
                    int happiness = database.getHappinessFromDatabase(userEmail);
                    happiness +=80;
                    if (happiness > 120)
                        happiness = 120;
                    database.updateHappinessInDatabase(userEmail,happiness);
                    int coins = database.getCoinsFromDatabase(userEmail);
                    coins +=80;
                    database.updateCoinsInDatabase(userEmail,coins);
                    Toast.makeText(getApplicationContext(), R.string.walked_toast,Toast.LENGTH_LONG).show();
                }

            }
            else
                walkTextView.setText(R.string.didn_walk_as_pet_need);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
