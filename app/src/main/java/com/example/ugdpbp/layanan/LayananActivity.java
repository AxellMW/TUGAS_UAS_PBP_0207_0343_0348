package com.example.ugdpbp.layanan;

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
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ugdpbp.R;
import com.example.ugdpbp.api.LayananApi;
import com.example.ugdpbp.layanan.LayananActivity;
import com.example.ugdpbp.layanan.AddEditLayananActivity;
import com.example.ugdpbp.layanan.LayananActivity;
import com.example.ugdpbp.layanan.LayananAdapter;
import com.example.ugdpbp.model.LayananResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LayananActivity extends AppCompatActivity {

    public static final int LAUNCH_ADD_ACTIVITY = 123;

    private SwipeRefreshLayout srLayanan;
    private LayananAdapter adapter;
    private SearchView svLayanan;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan);
        queue = Volley.newRequestQueue(this);
        srLayanan = findViewById(R.id.sr_layanan);
        svLayanan = findViewById(R.id.sv_layanan);
        srLayanan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllLayanan();
            }
        });

        svLayanan.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                Intent i = new Intent(LayananActivity.this, AddEditLayananActivity.class);
                startActivityForResult(i, LAUNCH_ADD_ACTIVITY);
            }
        });

        RecyclerView rvLayanan = findViewById(R.id.rv_layanan);
        adapter = new LayananAdapter(new ArrayList<>(), this);
        rvLayanan.setLayoutManager(new LinearLayoutManager(LayananActivity.this,
                LinearLayoutManager.VERTICAL, false));
        rvLayanan.setAdapter(adapter);
        getAllLayanan();
    }

    @Override
    protected void onActivityResult
            (int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_ADD_ACTIVITY && resultCode == Activity.RESULT_OK)
            getAllLayanan();
    }

    private void getAllLayanan() {
        srLayanan.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(GET,
                LayananApi.GET_ALL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API
                menjadi java object LayananResponse menggunakan Gson */
                LayananResponse layananResponse =
                        gson.fromJson(response, LayananResponse.class);
                adapter.setLayananList(layananResponse.getLayananList());
                adapter.getFilter().filter(svLayanan.getQuery());
                Toast.makeText(LayananActivity.this,
                        layananResponse.getMessage(), Toast.LENGTH_SHORT).show();
                srLayanan.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                srLayanan.setRefreshing(false);
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(LayananActivity.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(LayananActivity.this, e.getMessage(),
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

    public void deleteLayanan(long id) {

        StringRequest stringRequest = new StringRequest(DELETE,
                LayananApi.DELETE_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API
                menjadi java object LayananResponse menggunakan Gson */
                LayananResponse layananResponse =
                        gson.fromJson(response, LayananResponse.class);
                Toast.makeText(LayananActivity.this,
                        layananResponse.getMessage(), Toast.LENGTH_SHORT).show();
                getAllLayanan();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(LayananActivity.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(LayananActivity.this, e.getMessage(),
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