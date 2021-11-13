package mszleag.com.carnote;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import mszleag.com.carnote.historylist.HistoryAdapter;
import mszleag.com.carnote.model.AutoData;
import mszleag.com.carnote.model.TankUpRecord;

public class MainMenuActivity extends AppCompatActivity {

    public static final String SPECIAL_DATA = "SPECIAL_DATA";
    public static final int NEW_CAR_REQUEST_CODE = 12345;
    private static final int TANK_REQUEST_CODE = 12346;
    public static final String AUTO_PREF = "AUTO_PREF";

    private Button goToTankFormButton;
    private Button goToNewCarButton;

    private Spinner autoChooseSpinner;
    private ArrayList<AutoData> autoList;
    private ArrayAdapter spinnerAdapter;
    private RecyclerView historyRecycleView;
    private RecyclerView.Adapter historyAdapter;
    private RecyclerView.LayoutManager historyLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_manu_layout);
        viewInit();
/*
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1231);
        Intent intent = new Intent(this,GpsActivity.class);
        startActivity(intent);*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(this,GpsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        editor.putString(AUTO_PREF,gson.toJson(autoList));
        editor.apply();
    }

    private void viewInit() {
        goToTankFormButton = findViewById(R.id.go_to_tank_form_button);
        autoChooseSpinner = findViewById(R.id.auto_choose_spinner);
        goToNewCarButton = findViewById(R.id.new_car_button);
        historyRecycleView = findViewById(R.id.historyRecycleView);


        initAutoList();
        initArrayAdapter();
        autoChooseSpinner.setAdapter(spinnerAdapter);
        initRecyclerView();



        goToTankFormButton.setOnClickListener(goToTankUpActivity());
        goToNewCarButton.setOnClickListener(goToAddCarActivity());
        autoChooseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initRecyclerView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(autoList.isEmpty()){
            Intent intent = new Intent(MainMenuActivity.this, AddCarActivity.class);
            startActivityForResult(intent, NEW_CAR_REQUEST_CODE);
        }
    }
    private void initRecyclerView()
    {
        historyLayoutManager = new LinearLayoutManager(this);
        historyRecycleView.setLayoutManager(historyLayoutManager);

        historyRecycleView.setHasFixedSize(true);

        historyAdapter = new HistoryAdapter(this,getCurrentAuto()!=null?getCurrentAuto().getTankUpRecord():new ArrayList<TankUpRecord>());
        historyRecycleView.setAdapter(historyAdapter);
    }

    private View.OnClickListener goToAddCarActivity() {
        return v -> {
            Intent intent = new Intent(MainMenuActivity.this, AddCarActivity.class);
            startActivityForResult(intent, NEW_CAR_REQUEST_CODE);
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_CAR_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    AutoData newAutoData = (AutoData) data.getExtras().get(AddCarActivity.AUTO_DATA_NEW_CAR);
                    Boolean isNewCarMasterCar = (Boolean) data.getExtras().get(AddCarActivity.IS_NEW_CAR_MASTER_CAR);

                    if(isNewCarMasterCar != null && isNewCarMasterCar) {
                        autoList.add(0,newAutoData);
                        autoChooseSpinner.setAdapter(spinnerAdapter);
                        autoChooseSpinner.setSelection(0,false);
                    } else {
                        autoList.add(newAutoData);
                        autoChooseSpinner.setAdapter(spinnerAdapter);
                        autoChooseSpinner.setSelection(autoList.size()-1,false);
                    }
                }
            }
        }
        if (requestCode == TANK_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    TankUpRecord newTankUp = (TankUpRecord) data.getExtras().get(GasTankUpActivity.NEW_TANK_UP_RECORD);

                    if(newTankUp != null) {
                        getCurrentAuto().getTankUpRecord().add(0,newTankUp);
                        historyAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }


    private View.OnClickListener goToTankUpActivity() {
        return v -> {
            Intent intent = new Intent(MainMenuActivity.this, GasTankUpActivity.class);
            intent.putExtra(SPECIAL_DATA, getCurrentAuto());
            startActivityForResult(intent,TANK_REQUEST_CODE);
        };
    }

    private void initArrayAdapter() {
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, autoList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void initAutoList() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String string = sharedPreferences.getString(AUTO_PREF, null);
        Gson gson = new Gson();
        ArrayList<AutoData> newAutoList = gson.fromJson(string, new TypeToken<ArrayList<AutoData>>(){
        }.getType());

        if (newAutoList != null){
            autoList = newAutoList;
        }else{
            autoList = new ArrayList<>();
        }
    }

    private AutoData getCurrentAuto() {
        return (AutoData) autoChooseSpinner.getSelectedItem();
    }
}
