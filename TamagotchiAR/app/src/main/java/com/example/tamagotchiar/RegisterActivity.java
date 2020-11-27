package com.example.tamagotchiar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tamagotchiar.db.DatabaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper database;
    @BindView(R.id.firstNameEditText)
    EditText firstNameEditText;
    @BindView(R.id.petNameEditText)
    EditText lastNameEditText;
    @BindView(R.id.emailEditText)
    EditText emailEditText;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        database = new DatabaseHelper(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.register,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_register){
            register();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void register() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String firstName = firstNameEditText.getText().toString();
        String petName = lastNameEditText.getText().toString();
        Boolean isMailAvailable = database.isMailAvailable(email);
        if (isMailAvailable){
            boolean insert = database.insert(email,password,firstName,petName);
            if(insert){
                Toast.makeText(getApplicationContext(),R.string.register_succes,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
            }
        }else{
            Toast.makeText(getApplicationContext(),R.string.email_exists_in_data,Toast.LENGTH_SHORT).show();
        }

    }
}
