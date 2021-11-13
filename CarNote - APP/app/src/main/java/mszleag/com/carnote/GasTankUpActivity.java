package mszleag.com.carnote;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import mszleag.com.carnote.model.AutoData;
import mszleag.com.carnote.model.TankUpRecord;

public class GasTankUpActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String NOWE_TANKOWANIE = "Nowe tankowanie";
    public static final String NEW_TANK_UP_RECORD = "NEW_TANK_UP_RECORD";
    public static final String AUTO_DATA_OBJ = "AUTO_DATA_OBJ";
    private EditText dateEditText;
    private EditText mileageEditText;
    private EditText litersEditText;
    private EditText costEditText;
    private AutoData autoData;
    private DateFormat dateFormat;
    private TextView mileageEditTextLabel;
    private TextView costEditTextLabel;
    private TextView litersEditTextLabel;
    private Button confirmButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gas_tank_up_layout);
        obtainExtras();
        if(savedInstanceState != null){
            autoData = (AutoData) savedInstanceState.get(AUTO_DATA_OBJ);
        }

        viewInit();
        setTitle(NOWE_TANKOWANIE);
    }

    private void obtainExtras() {
        autoData = (AutoData) getIntent().getExtras().getSerializable(MainMenuActivity.SPECIAL_DATA);
    }

    private void viewInit() {
        dateEditText = findViewById(R.id.date);
        mileageEditText = findViewById(R.id.mileage);
        mileageEditTextLabel = findViewById(R.id.mileage_label);
        litersEditTextLabel = findViewById(R.id.liters_label);
        costEditTextLabel = findViewById(R.id.cost_label);
        litersEditText = findViewById(R.id.liters);
        costEditText = findViewById(R.id.cost);
        confirmButton = findViewById(R.id.confirm);

        dateEditText.setText(getCurrentDate());
        dateEditText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(GasTankUpActivity.this,GasTankUpActivity.this,year, month, day);

            datePickerDialog.show();
        });

        confirmButton.setOnClickListener(v -> {

            if (validateMileage() && validateCost() && validateLiters())
            {
                TankUpRecord tank = new TankUpRecord(getDateEditTextDate(), getMileageInteger(), getLitersInteger(), getCostInteger());
                Intent intent = new Intent();
                intent.putExtra(NEW_TANK_UP_RECORD,tank);
                autoData.getTankUpRecord().add(tank);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }

        });

        mileageEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                validateMileage();
            }
        });
        costEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                validateCost();
            }
        });
        litersEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                validateLiters();
            }
        });
    }

    private boolean validateLiters() {

        if (TextUtils.isEmpty(litersEditText.getText().toString())){
            litersEditTextLabel.setText(R.string.zero_liters);
            litersEditTextLabel.setTextColor(getResources().getColor(R.color.red,getResources().newTheme()));
            return false;

        }else if (Integer.parseInt(litersEditText.getText().toString()) <= 0){

            litersEditTextLabel.setText(R.string.minus_liters);
            litersEditTextLabel.setTextColor(getResources().getColor(R.color.red,getResources().newTheme()));
            return false;
        }
        else{

            litersEditTextLabel.setText(getResources().getString(R.string.liters));
            litersEditTextLabel.setTextColor(getResources().getColor(R.color.black,getResources().newTheme()));
            return true;

        }
    }


    private boolean validateCost() {
            if (TextUtils.isEmpty(costEditText.getText().toString())){

                costEditTextLabel.setText(R.string.zero_cost);
                costEditTextLabel.setTextColor(getResources().getColor(R.color.red,getResources().newTheme()));
                return false;

            }else if (Integer.parseInt(costEditText.getText().toString()) <= 0){

            costEditTextLabel.setText(R.string.minus_cost);
            costEditTextLabel.setTextColor(getResources().getColor(R.color.red,getResources().newTheme()));
            return false;

            }else{

                costEditTextLabel.setText(getResources().getString(R.string.cost));
                mileageEditTextLabel.setTextColor(getResources().getColor(R.color.black,getResources().newTheme()));
                return true;

            }
    }

    private boolean validateMileage() {

        if (TextUtils.isEmpty(mileageEditText.getText().toString())){
            mileageEditTextLabel.setText(R.string.zero_milleage);
            mileageEditTextLabel.setTextColor(getResources().getColor(R.color.red,getResources().newTheme()));
            return false;
        }
        if(Integer.parseInt(mileageEditText.getText().toString()) <=0) {
            mileageEditTextLabel.setText(R.string.minus_mileage);
            mileageEditTextLabel.setTextColor(getResources().getColor(R.color.red,getResources().newTheme()));
            return false;
        }
        if (autoData.getTankUpRecord().size() != 0){
            Integer newMileage = Integer.valueOf(mileageEditText.getText().toString());
            Integer oldMileage = autoData.getTankUpRecord().get(0).getMileage();
            if(newMileage <= oldMileage){
                mileageEditTextLabel.setText(R.string.wrong_mileage);
                mileageEditTextLabel.setTextColor(getResources().getColor(R.color.red,getResources().newTheme()));
                return false;
            }else
            {
                mileageEditTextLabel.setText(getResources().getString(R.string.mileage));
                mileageEditTextLabel.setTextColor(getResources().getColor(R.color.black,getResources().newTheme()));
                return true;
            }

        }
        return true;
    }

    private Date getDateEditTextDate() {

        try {
            return dateFormat.parse(dateEditText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = DateFormat.getDateInstance();
        return new Date();
    }

    private Integer getCostInteger() {
        return Integer.valueOf(costEditText.getText().toString());
    }

    private Integer getLitersInteger() {
        return Integer.valueOf(litersEditText.getText().toString());
    }

    private Integer getMileageInteger() {
        return Integer.valueOf(mileageEditText.getText().toString());
    }

    private String getCurrentDate() {
        dateFormat = DateFormat.getDateInstance();
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(AUTO_DATA_OBJ,autoData);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year,month,dayOfMonth);

        dateEditText.setText(dateFormat.format(calendar.getTime()));

    }
}
