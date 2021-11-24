package com.example.parking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.parking.db.DatabaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends Activity {
    DatabaseHelper database;
    @BindView(R.id.firstNameEditText)
    EditText firstNameEditText;
    @BindView(R.id.emailEditText)
    EditText emailEditText;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;
    @BindView(R.id.lastNameEditText)
    EditText lastNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        database = new DatabaseHelper(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.register, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_register) {
            if (registerValidation()) {
                register();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void register() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        Boolean isMailAvailable = database.isMailAvailable(email);
        if (isMailAvailable) {
            boolean insert = database.insert(email, password, firstName, lastName);
            if (insert) {
                Toast.makeText(getApplicationContext(), R.string.register_succes,
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.email_exists_in_data,
                    Toast.LENGTH_SHORT).show();
        }

    }

    public Boolean registerValidation(){

        if (TextUtils.isEmpty(firstNameEditText.getText().toString())){
            firstNameEditText.setError(getString(R.string.name_empty));
            return false;
        }
        if (TextUtils.isEmpty(lastNameEditText.getText().toString())){
            lastNameEditText.setError(getString(R.string.last_name_empty));
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()){
            emailEditText.setError(getString(R.string.wrong_email));
            return false;
        }
        if (TextUtils.isEmpty(emailEditText.getText().toString())){
            emailEditText.setError(getString(R.string.email_empty));
            return false;
        }
        if (TextUtils.isEmpty(passwordEditText.getText().toString())){
            passwordEditText.setError(getString(R.string.password_empty));
            return false;
        }
        else
            return true;
    }
}
