package com.example.ugdpbp.kamar;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ugdpbp.R;
import com.example.ugdpbp.api.KamarApi;
import com.example.ugdpbp.model.Kamar;
import com.example.ugdpbp.model.KamarResponse;
import com.example.ugdpbp.model.KamarResponse2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddEditKamarActivity extends AppCompatActivity {
    private static final String[] TIPE_KAMAR_LIST = new String[]{"Standart", "Deluxe", "Double", "Suite"};
    private static final String[] NO_KAMAR_LIST = new String[]{"111", "222", "333", "444", "555", "666"};
    private EditText etNama, etNo_telp;
    private AutoCompleteTextView edTipe_kamar, edNo_kamar;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_kamar);
        queue = Volley.newRequestQueue(this);
        etNama = findViewById(R.id.et_nama);
        etNo_telp = findViewById(R.id.et_no_telp);
        edTipe_kamar = findViewById(R.id.ed_tipe_kamar);
        edNo_kamar = findViewById(R.id.ed_no_kamar);
        ArrayAdapter<String> adapterTipe_kamar =
                new ArrayAdapter<>(this, R.layout.kamar_list, TIPE_KAMAR_LIST);
        edTipe_kamar.setAdapter(adapterTipe_kamar);
        ArrayAdapter<String> adapterNo_kamar =
                new ArrayAdapter<>(this, R.layout.kamar_list, NO_KAMAR_LIST);
        edNo_kamar.setAdapter(adapterNo_kamar);
        Button btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button btnSave = findViewById(R.id.btn_save);
        TextView tvTitle = findViewById(R.id.tv_title);
        long id = getIntent().getLongExtra("id", -1);
        if (id == -1) {
            tvTitle.setText(R.string.pesan_kamar);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createKamar();
                }
            });
        } else {
            tvTitle.setText(R.string.edit_pesan);
            getKamarById(id);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateKamar(id);
                }
            });
        }
    }

    private void getKamarById(long id) {
        // Membuat request baru untuk mengambil data kamar berdasarkan id
        StringRequest stringRequest = new StringRequest(GET,
                KamarApi.GET_BY_ID_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                 /* Deserialiasai data dari response JSON dari API
                 menjadi java object KamarResponse menggunakan Gson */
                KamarResponse2 kamarResponse2 = gson.fromJson(response, KamarResponse2.class);

                Kamar kamar = kamarResponse2.getKamar();

                etNama.setText(kamar.getNama());
                etNo_telp.setText(kamar.getNo_telp());
                edTipe_kamar.setText(kamar.getTipe_kamar(), false);
                edNo_kamar.setText(kamar.getNo_kamar(), false);

                Toast.makeText(AddEditKamarActivity.this,
                        kamarResponse2.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(AddEditKamarActivity.this, errors.getString("message"),
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(AddEditKamarActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            // Menambahkan header pada request
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");

                return headers;
            }
        };

        // Menambahkan request ke request queue
        queue.add(stringRequest);
    }

    private void createKamar() {
        Kamar kamar = new Kamar(
                etNama.getText().toString(),
                etNo_telp.getText().toString(),
                edTipe_kamar.getText().toString(),
                edNo_kamar.getText().toString());

        // Membuat request baru untuk membuat data kamar baru
        StringRequest stringRequest = new StringRequest(POST, KamarApi.ADD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                         /* Deserialiasai data dari response JSON dari API
                         menjadi java object KamarResponse menggunakan Gson */
                        KamarResponse kamarResponse = gson.fromJson(response, KamarResponse.class);
                        Toast.makeText(AddEditKamarActivity.this,
                                kamarResponse.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(AddEditKamarActivity.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(AddEditKamarActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            // Menambahkan header pada request
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
            // Menambahkan request body berupa object kamar
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                 /* Serialisasi data dari java object KamarResponse
                 menjadi JSON string menggunakan Gson */
                String requestBody = gson.toJson(kamar);
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
            // Mendeklarasikan content type dari request body yang ditambahkan
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        // Menambahkan request ke request queue
        queue.add(stringRequest);
    }

    private void updateKamar(long id) {

        Kamar kamar = new Kamar(
                etNama.getText().toString(),
                etNo_telp.getText().toString(),
                edTipe_kamar.getText().toString(),
                edNo_kamar.getText().toString());

        // Membuat request baru untuk mengedit data kamar
        StringRequest stringRequest = new StringRequest(PUT,
                KamarApi.UPDATE_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                 /* Deserialiasai data dari response JSON dari API
                 menjadi java object KamarResponse menggunakan Gson */
                KamarResponse kamarResponse =
                        gson.fromJson(response, KamarResponse.class);

                Toast.makeText(AddEditKamarActivity.this,
                        kamarResponse.getMessage(), Toast.LENGTH_SHORT).show();

                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(AddEditKamarActivity.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(AddEditKamarActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            // Menambahkan header pada request
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");

                return headers;
            }

            // Menambahkan request body berupa object kamar
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                 /* Serialisasi data dari java object KamarResponse
                 menjadi JSON string menggunakan Gson */
                String requestBody = gson.toJson(kamar);
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }

            // Mendeklarasikan content type dari request body yang ditambahkan
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        // Menambahkan request ke request queue
        queue.add(stringRequest);
    }
}