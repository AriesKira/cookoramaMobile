package com.example.cookmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button loginBtn, pwdResetBtn;
    private EditText emailInput, pwdInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.loginBtn = findViewById(R.id.loginBtn);
        this.pwdResetBtn = findViewById(R.id.pwdResetBtn);
        this.emailInput = findViewById(R.id.loginEmail);
        this.pwdInput = findViewById(R.id.loginPassword);


        this.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportActionBar().hide();
                Intent a = new Intent(MainActivity.this, HomeActivity.class);

                startActivity(a);
                finish();
            }
        });

    }
}