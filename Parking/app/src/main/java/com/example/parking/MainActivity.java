package com.example.parking;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper database;
    @BindView(R.layout.activity_main_menu)
    DrawerLayout dl;
    @BindView(R.id.nv)
    NavigationView nv;
    ActionBarDrawerToggle t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        t = new ActionBarDrawerToggle(this, dl,R.string.open, R.string.close);
        ButterKnife.bind(this);
        database = new DatabaseHelper(this);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.myAccount_id:
                        Toast.makeText(MainActivity.this, R.string.konto,Toast.LENGTH_SHORT).show();
                    case R.id.reserwationList_id:
                        Toast.makeText(MainActivity.this, R.string.moje_rezerwacje,Toast.LENGTH_SHORT).show();
                    case R.id.myCars_id:
                        Toast.makeText(MainActivity.this, R.string.moje_pojazdy,Toast.LENGTH_SHORT).show();
                    default:
                        return true;
                }





            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
