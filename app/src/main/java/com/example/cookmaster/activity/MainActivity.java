package com.example.cookmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.cookmaster.classes.ApiRequest;
import com.example.cookmaster.interfaces.LoginUserCallback;

public class MainActivity extends AppCompatActivity {


    private Button loginBtn, pwdResetBtn;
    private EditText emailInput, pwdInput;

    LottieAnimationView loadingAnimation;
    private ApiRequest apiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences settings = getSharedPreferences("PREFS", MODE_PRIVATE);
        this.loginBtn = findViewById(R.id.loginBtn);
        this.pwdResetBtn = findViewById(R.id.pwdResetBtn);
        this.emailInput = findViewById(R.id.loginEmail);
        this.pwdInput = findViewById(R.id.loginPassword);
        this.loadingAnimation = findViewById(R.id.animationView);

        apiRequest = new ApiRequest();

        loadingAnimation.setVisibility(View.VISIBLE);
        if (settings.contains("token") && !settings.getString("token", "-2").equals("-1")) {
            String token = settings.getString("token", "-1");

            apiRequest.connectUserToken("Bearer " + token, new LoginUserCallback() {
                @Override
                public void onConnectionSuccess(String token,int id) {
                    Log.d("API Response", "Token: " + token);
                    SharedPreferences.Editor edit = settings.edit();
                    edit.putString("token", token);
                    edit.putInt("id",id);
                    edit.apply();
                    Intent a = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(a);
                    finish();
                }

                @Override
                public void onConnectionFailure(String errorMessage) {
                    Log.d("API Response", "Error: " + errorMessage);
                    Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    loadingAnimation.setVisibility(View.GONE);
                }
            });
        }
        loadingAnimation.setVisibility(View.GONE);
        this.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (emailInput.getText().toString().isEmpty()) {
                    emailInput.setError(R.string.email_empty_error + "");
                    return;
                }
                if (pwdInput.getText().toString().isEmpty()) {
                    pwdInput.setError(R.string.pwd_empty_error + "");
                    return;
                }

                loadingAnimation.setVisibility(View.VISIBLE);
                Log.d(emailInput.getText().toString(), pwdInput.getText().toString());
                apiRequest.connectUser(emailInput.getText().toString(), pwdInput.getText().toString(), new LoginUserCallback() {
                    @Override
                    public void onConnectionSuccess(String token,int id) {
                        SharedPreferences.Editor edit = settings.edit();
                        edit.putString("token", token);
                        edit.putInt("id",id);
                        edit.apply();
                        Intent a = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(a);
                        finish();
                    }

                    @Override
                    public void onConnectionFailure(String errorMessage) {
                        loadingAnimation.setVisibility(View.GONE);
                        Log.d("API Response", "Error: " + errorMessage);
                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    }


                });


            }
        });

    }
}