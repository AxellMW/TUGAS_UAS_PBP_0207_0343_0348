package com.example.ugdpbp.fragment;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ugdpbp.Database.DatabaseClient;
import com.example.ugdpbp.model.Order;
import com.example.ugdpbp.Preferences.UserPreferences;
import com.example.ugdpbp.R;

import java.util.Calendar;

public class FragmentOrder extends AppCompatActivity {
    private EditText etNamaLengkap, etNoTelp, etTipeKamar, etNoKamar, etTglPesan;
    private Button btnHapus, btnTambah;
    private RecyclerView rv_orderlist;
    private UserPreferences userPreferences;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_page);
        etNamaLengkap = findViewById(R.id.etNamaLengkap);
        etNoTelp = findViewById(R.id.etNoTelp);
        etTipeKamar = findViewById(R.id.etTipeKamar);
        etNoKamar = findViewById(R.id.etNoKamar);
        etTglPesan = findViewById(R.id.etTglPesan);
        btnHapus = findViewById(R.id.btnHapus);
        btnTambah = findViewById(R.id.btnTambah);
        etTglPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        FragmentOrder.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }

        });
        dateSetListener = new DatePickerDialog.OnDateSetListener(){
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                etTglPesan.setText(date);
            }
        };

        userPreferences = new UserPreferences(FragmentOrder.this);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etNamaLengkap.getText().toString().isEmpty()) {
                    addOrder();
                }else if(!etNoTelp.getText().toString().isEmpty()){
                    addOrder();
                }else if(!etTipeKamar.getText().toString().isEmpty()){
                    addOrder();
                }else if(!etNoKamar.getText().toString().isEmpty()){
                    addOrder();
                }else if(!etTglPesan.getText().toString().isEmpty()){
                    addOrder();
                }else {
                    Toast.makeText(FragmentOrder.this, "Belum diisi!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etNamaLengkap.setText("");
                etNoTelp.setText("");
                etTipeKamar.setText("");
                etNoKamar.setText("");
                etTglPesan.setText("");
            }
        });

        //getOrders();
    }

    private void addOrder(){
        final String nama_lengkap = etNamaLengkap.getText().toString();
        final String no_telp = etNoTelp.getText().toString();
        final String tipe_kamar = etTipeKamar.getText().toString();
        final String no_kamar = etNoKamar.getText().toString();
        final String tanggal_pemesanan = etTglPesan.getText().toString();

        class AddOrder extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Order order = new Order();
                order.setNama_lengkap(nama_lengkap);
                order.setNo_telp(no_telp);
                order.setTipe_kamar(tipe_kamar);
                order.setNo_kamar(no_kamar);
                order.setTanggal_pemesanan(tanggal_pemesanan);
                order.setUser_id(userPreferences.getUserLogin().getId());

                DatabaseClient.getInstance(FragmentOrder.this)
                        .getDatabase()
                        .orderDao()
                        .insertOrder(order);

                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Toast.makeText(FragmentOrder.this, "Berhasil menambahkan data", Toast.LENGTH_SHORT).show();
                etNamaLengkap.setText("");
                etNoTelp.setText("");
                etTipeKamar.setText("");
                etNoKamar.setText("");
                etTglPesan.setText("");
                //getTodos();
            }

        }
        AddOrder addOrder = new AddOrder(  );
        addOrder.execute();
    }

    /**/

}
