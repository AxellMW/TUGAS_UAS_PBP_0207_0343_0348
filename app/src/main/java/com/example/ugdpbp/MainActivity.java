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

import com.example.ugdpbp.databinding.ActivityMainBinding;
import com.example.ugdpbp.fragment.FragmentList;
import com.example.ugdpbp.fragment.FragmentOrder;
import com.example.ugdpbp.fragment.FragmentHistory;
import com.example.ugdpbp.Geolocation.Geolocation;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        activityMainBinding.setActivity(this);
        FirebaseMessaging.getInstance().subscribeToTopic("TubesPBP");
    }

    public View.OnClickListener btnOrder = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent mainActivity = new Intent(MainActivity.this, FragmentOrder.class);
            startActivity(mainActivity);
        }
    };

    public View.OnClickListener btnList = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent mainActivity = new Intent(MainActivity.this, FragmentList.class);
            startActivity(mainActivity);
        }
    };

    public View.OnClickListener btnHistory = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent mainActivity = new Intent(MainActivity.this, FragmentHistory.class);
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
                            finishAndRemoveTask();
                        }
                    })
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
}