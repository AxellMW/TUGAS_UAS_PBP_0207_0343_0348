package com.example.ugdpbp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.ugdpbp.Authentication.LoginActivity;
import com.example.ugdpbp.Preferences.UserPreferences;
import com.example.ugdpbp.activity.ActivityAwal;
import com.example.ugdpbp.activity.ActivityHistory;
import com.example.ugdpbp.activity.ActivityOrder;
import com.example.ugdpbp.activity.ActivityProfil;
import com.example.ugdpbp.databinding.ActivityMainBinding;
import com.example.ugdpbp.activity.ActivityList;
import com.example.ugdpbp.Geolocation.Geolocation;
import com.example.ugdpbp.kamar.KamarActivity;
import com.example.ugdpbp.layanan.LayananActivity;
import com.example.ugdpbp.qr.QRStartActivity;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        userPreferences = new UserPreferences(MainActivity.this);
        activityMainBinding.setActivity(this);
        FirebaseMessaging.getInstance().subscribeToTopic("TubesPBP");
    }

    public View.OnClickListener btnOrder = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent mainActivity = new Intent(MainActivity.this, ActivityOrder.class);
            startActivity(mainActivity);
        }
    };

    public View.OnClickListener btnList = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent mainActivity = new Intent(MainActivity.this, ActivityList.class);
            startActivity(mainActivity);
        }
    };

    public View.OnClickListener btnHistory = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent mainActivity = new Intent(MainActivity.this, ActivityHistory.class);
            startActivity(mainActivity);
        }
    };

    public View.OnClickListener btnGeolocation = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent mainActivity = new Intent(MainActivity.this, Geolocation.class);
            startActivity(mainActivity);
        }
    };

    public View.OnClickListener btnKamar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent mainActivity = new Intent(MainActivity.this, KamarActivity.class);
            startActivity(mainActivity);
        }
    };

    public View.OnClickListener btnLayanan = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent mainActivity = new Intent(MainActivity.this, LayananActivity.class);
            startActivity(mainActivity);
        }
    };

    public View.OnClickListener btnQR = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent mainActivity = new Intent(MainActivity.this, QRStartActivity.class);
            startActivity(mainActivity);
        }
    };

    public View.OnClickListener btnProfil = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent mainActivity = new Intent(MainActivity.this, ActivityProfil.class);
            startActivity(mainActivity);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.home_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == R.id.logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure want to exit?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            userPreferences.logout();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            overridePendingTransition(0, 0);
                            finishAndRemoveTask();
                        }
                    })
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
}