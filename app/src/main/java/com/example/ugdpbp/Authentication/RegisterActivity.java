package com.example.ugdpbp.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ugdpbp.Database.DatabaseClient;
import com.example.ugdpbp.model.User;
import com.example.ugdpbp.Preferences.UserPreferences;
import com.example.ugdpbp.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.messaging.FirebaseMessaging;

public class RegisterActivity extends AppCompatActivity {
    private EditText etName, etUsername, etPassword;
    private MaterialButton btnRegister, btnLogin;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseMessaging.getInstance().subscribeToTopic("TubesPBP");

        userPreferences = new UserPreferences(RegisterActivity.this);

        etName = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm()){
                    register(etName.getText().toString(), etUsername.getText().toString().trim(), etPassword.getText().toString().trim());
                }
            }
        });
    }

    private void register(String name,String username, String password){

        class RegisterUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                User user = new User();
                user.setName(name);
                user.setUsername(username);
                user.setPassword(password);

                DatabaseClient.getInstance(RegisterActivity.this)
                        .getDatabase()
                        .userDao()
                        .registerUser(user);

                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Toast.makeText(RegisterActivity.this, "Berhasil mendaftar", Toast.LENGTH_SHORT).show();
                clearField();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }

        }

        RegisterUser registerUser = new RegisterUser();
        registerUser.execute();
    }

    private void clearField(){
        etName.setText("");
        etUsername.setText("");
        etPassword.setText("");
    }


    private boolean validateForm(){
        if(etUsername.getText().toString().trim().isEmpty() || etPassword.getText().toString().trim().isEmpty() || etName.getText().toString().trim().isEmpty()){
            Toast.makeText(RegisterActivity.this,"Field tidak boleh Kosong",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
