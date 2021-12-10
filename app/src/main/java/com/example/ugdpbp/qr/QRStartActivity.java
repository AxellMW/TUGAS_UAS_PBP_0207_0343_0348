package com.example.ugdpbp.qr;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ugdpbp.R;
import com.example.ugdpbp.databinding.ActivityQrstartBinding;

public class QRStartActivity extends AppCompatActivity {
    private ActivityQrstartBinding binding;
    private final ActivityResultLauncher<Intent> cameraResult =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                try {
                                    Intent intent = result.getData();
                                    String strQRRes = intent.getStringExtra("QR_RESULT");
                                    String[] res = strQRRes.split(";");
                                    binding.txtPembayaran.setText(res[0]);
                                } catch (Exception e) {
                                    binding.txtPembayaran.setText("");
                                    Toast.makeText(QRStartActivity.this, "QR CODE TIDAK VALID!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQrstartBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btnScan.setOnClickListener(v -> {
            cameraResult.launch(new Intent(this, QRScannerActivity.class));
        });
    }
}