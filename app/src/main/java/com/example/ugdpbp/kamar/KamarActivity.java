package com.example.ugdpbp.kamar;

import static com.android.volley.Request.Method.DELETE;
import static com.android.volley.Request.Method.GET;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ugdpbp.R;
import com.example.ugdpbp.api.KamarApi;
import com.example.ugdpbp.model.KamarResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KamarActivity extends AppCompatActivity {

    public static final int LAUNCH_ADD_ACTIVITY = 123;

    private SwipeRefreshLayout srKamar;
    private KamarAdapter adapter;
    private SearchView svKamar;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamar);
        queue = Volley.newRequestQueue(this);
        srKamar = findViewById(R.id.sr_kamar);
        svKamar = findViewById(R.id.sv_kamar);
        srKamar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllKamar();
            }
        });

        svKamar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KamarActivity.this, AddEditKamarActivity.class);
                startActivityForResult(i, LAUNCH_ADD_ACTIVITY);
            }
        });

        RecyclerView rvKamar = findViewById(R.id.rv_kamar);
        adapter = new KamarAdapter(new ArrayList<>(), this);
        rvKamar.setLayoutManager(new LinearLayoutManager(KamarActivity.this,
                LinearLayoutManager.VERTICAL, false));
        rvKamar.setAdapter(adapter);
        getAllKamar();
    }

    @Override
    protected void onActivityResult
            (int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_ADD_ACTIVITY && resultCode == Activity.RESULT_OK)
            getAllKamar();
    }

    private void getAllKamar() {
        srKamar.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(GET,
                KamarApi.GET_ALL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API
                menjadi java object KamarResponse menggunakan Gson */
                KamarResponse kamarResponse =
                        gson.fromJson(response, KamarResponse.class);
                adapter.setKamarList(kamarResponse.getKamarList());
                adapter.getFilter().filter(svKamar.getQuery());
                Toast.makeText(KamarActivity.this,
                        kamarResponse.getMessage(), Toast.LENGTH_SHORT).show();
                srKamar.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                srKamar.setRefreshing(false);
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(KamarActivity.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(KamarActivity.this, e.getMessage(),
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

    public void deleteKamar(long id) {

        StringRequest stringRequest = new StringRequest(DELETE,
                KamarApi.DELETE_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API
                menjadi java object KamarResponse menggunakan Gson */
                KamarResponse kamarResponse =
                        gson.fromJson(response, KamarResponse.class);
                Toast.makeText(KamarActivity.this,
                        kamarResponse.getMessage(), Toast.LENGTH_SHORT).show();
                getAllKamar();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(KamarActivity.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(KamarActivity.this, e.getMessage(),
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
}