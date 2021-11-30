package com.example.ugdpbp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ugdpbp.Authentication.LoginActivity;
import com.example.ugdpbp.Authentication.RegisterActivity;
import com.example.ugdpbp.R;

public class ActivityAwal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);

        Button btnMasuk = findViewById(R.id.btnMasuk);
        Button btnDaftar = findViewById(R.id.btnDaftar);

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fragmentAwal = new Intent(ActivityAwal.this, LoginActivity.class);
                startActivity(fragmentAwal);
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fragmentAwal = new Intent(ActivityAwal.this, RegisterActivity.class);
                startActivity(fragmentAwal);
            }
        });
    }
}
