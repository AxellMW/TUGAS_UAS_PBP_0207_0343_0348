package com.example.ugdpbp.layanan;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ugdpbp.R;
import com.example.ugdpbp.api.LayananApi;
import com.example.ugdpbp.layanan.AddEditLayananActivity;
import com.example.ugdpbp.model.Layanan;
import com.example.ugdpbp.model.LayananResponse;
import com.example.ugdpbp.model.LayananResponse2;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AddEditLayananActivity extends AppCompatActivity {

    private static final String[] NO_KAMAR_LIST = new String[]{"111", "222", "333", "444", "555", "666"};
    private static final String[] LAYANAN_LIST = new String[]{"Makanan", "Minuman", "Bantal Tambahan", "Pembersihan Layanan"};
    private AutoCompleteTextView ed_no_kamar, ed_layanan;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_layanan);
        queue = Volley.newRequestQueue(this);
        ed_no_kamar = findViewById(R.id.ed_no_kamar);
        ed_layanan = findViewById(R.id.ed_layanan);
        ArrayAdapter<String> adapterNo_kamar =
                new ArrayAdapter<>(this, R.layout.kamar_list, NO_KAMAR_LIST);
        ed_no_kamar.setAdapter(adapterNo_kamar);
        ArrayAdapter<String> adapterLayanan =
                new ArrayAdapter<>(this, R.layout.kamar_list, LAYANAN_LIST);
        ed_layanan.setAdapter(adapterLayanan);
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
                    createLayanan();
                }
            });
        } else {
            tvTitle.setText(R.string.edit_pesan);
            getLayananById(id);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateLayanan(id);
                }
            });
        }
    }

    private void getLayananById(long id) {
        // Membuat request baru untuk mengambil data layanan berdasarkan id
        StringRequest stringRequest = new StringRequest(GET,
                LayananApi.GET_BY_ID_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                 /* Deserialiasai data dari response JSON dari API
                 menjadi java object LayananResponse menggunakan Gson */
                LayananResponse2 layananResponse2 = gson.fromJson(response, LayananResponse2.class);

                Layanan layanan = layananResponse2.getLayanan();

                ed_no_kamar.setText(layanan.getNo_kamar(), false);
                ed_layanan.setText(layanan.getLayanan(), false);

                Toast.makeText(AddEditLayananActivity.this,
                        layananResponse2.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(AddEditLayananActivity.this, errors.getString("message"),
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(AddEditLayananActivity.this, e.getMessage(),
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

    private void createLayanan() {
        Layanan layanan = new Layanan(
                ed_no_kamar.getText().toString(),
                ed_layanan.getText().toString());

        // Membuat request baru untuk membuat data layanan baru
        StringRequest stringRequest = new StringRequest(POST, LayananApi.ADD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                         /* Deserialiasai data dari response JSON dari API
                         menjadi java object LayananResponse menggunakan Gson */
                        LayananResponse layananResponse =
                                gson.fromJson(response, LayananResponse.class);
                        Toast.makeText(AddEditLayananActivity.this,
                                layananResponse.getMessage(),
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
                    Toast.makeText(AddEditLayananActivity.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(AddEditLayananActivity.this, e.getMessage(),
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
            // Menambahkan request body berupa object layanan
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                 /* Serialisasi data dari java object LayananResponse
                 menjadi JSON string menggunakan Gson */
                String requestBody = gson.toJson(layanan);
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

    private void updateLayanan(long id) {

        Layanan layanan = new Layanan(
                ed_no_kamar.getText().toString(),
                ed_layanan.getText().toString());

        // Membuat request baru untuk mengedit data layanan
        StringRequest stringRequest = new StringRequest(PUT,
                LayananApi.UPDATE_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                 /* Deserialiasai data dari response JSON dari API
                 menjadi java object LayananResponse menggunakan Gson */
                LayananResponse layananResponse =
                        gson.fromJson(response, LayananResponse.class);

                Toast.makeText(AddEditLayananActivity.this,
                        layananResponse.getMessage(), Toast.LENGTH_SHORT).show();

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

                    Toast.makeText(AddEditLayananActivity.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(AddEditLayananActivity.this, e.getMessage(),
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

            // Menambahkan request body berupa object layanan
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                 /* Serialisasi data dari java object LayananResponse
                 menjadi JSON string menggunakan Gson */
                String requestBody = gson.toJson(layanan);
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