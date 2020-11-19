package mszleag.com.carnote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Aktywność liczenia czasu potrzebnego na ropędzenie auta do setki
 */
public class GpsActivity extends AppCompatActivity implements LocationListener {

    private int bestTimeTo100;
    private int secTo100;
    private LocationManager locationManager;
    private Button button;
    private TextView currentSpeed;
    private TextView bestSpeed;
    private Date startTime;
    private boolean wasCounted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_layout);
        bestSpeed = findViewById(R.id.best_speed);
        currentSpeed = findViewById(R.id.current_speed);
        button = findViewById(R.id.button);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        button.setOnClickListener(v -> {
            startTime = new Date();
            wasCounted = false;
        });
    }


    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,500L,2.0f, this);
        //podepnij GPS
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
        //odepnij GPS
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //tu zapisać do bundla stan aktualnej pozycji
        //zapiszmy tu tez nasz rekord
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        float speed = location.getSpeed();
        float kmhspeed = speed *3600 / 1000;
        currentSpeed.setText("Bieżąca prędkość" + kmhspeed + "km/h");

        if(kmhspeed >= 100 && !wasCounted)
        {
            long diffInMs = new Date().getTime() - startTime.getTime();
            long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);
            bestSpeed.setText("Rekord ostatni " + diffInSec + "sekund");
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
