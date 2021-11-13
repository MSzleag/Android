package com.example.tamagotchiar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tamagotchiar.db.DatabaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    public static final String LOGGED_EMAIL = "LOGGED_EMAIL";
    DatabaseHelper database;
    @BindView(R.id.emailEditText)
    EditText emailEditText;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.registerButton)
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        database = new DatabaseHelper(this);
    }

    @OnClick(R.id.loginButton)
    public void onLoginButtonClicked() {
        String emailText = emailEditText.getText().toString();
        String passwordText = passwordEditText.getText().toString();

        if(loginValidation()) {
            login(emailText, passwordText);
        }
    }


    @OnClick(R.id.registerButton)
    public void onRegisterButtonClicked() {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }


    private void login(String email, String password) {
        Boolean isEmailAndPasswordMatch = database
                .isEmailAndPasswordMatch(email,password);

            if (isEmailAndPasswordMatch){
                Intent intent = new Intent(this,MainMenuActivity.class);
                intent.putExtra(LOGGED_EMAIL,email);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),
                        R.string.login_succes,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),
                        R.string.wrong_email_or_password,Toast.LENGTH_SHORT).show();
            }
    }

    public Boolean loginValidation(){
        if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()){
            emailEditText.setError(getString(R.string.wrong_email));
            return false;
        }
        if (TextUtils.isEmpty(emailEditText.getText().toString())){
            emailEditText.setError(getString(R.string.email_empty));
            return false;
        }
        if (TextUtils.isEmpty(passwordEditText.getText().toString())){
            passwordEditText.setError(getString(R.string.email_empty));
            return false;
        }
        else
            return true;
    }
}


