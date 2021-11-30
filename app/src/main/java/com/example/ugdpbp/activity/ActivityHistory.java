package com.example.ugdpbp.activity;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ugdpbp.Adapter.OrderAdapter;
import com.example.ugdpbp.Database.DatabaseClient;
import com.example.ugdpbp.Preferences.UserPreferences;
import com.example.ugdpbp.R;
import com.example.ugdpbp.model.Order;

import java.util.ArrayList;
import java.util.List;

public class ActivityHistory extends AppCompatActivity {
    private EditText etNamaLengkap, etNoTelp, etTipeKamar, etNoKamar, etTglPesan;
    private RecyclerView rv_orderList;
    private UserPreferences userPreferences;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private List<Order> orderList;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_page);
        etNamaLengkap = findViewById(R.id.etNamaLengkap);
        etNoTelp = findViewById(R.id.etNoTelp);
        etTipeKamar = findViewById(R.id.etTipeKamar);
        etNoKamar = findViewById(R.id.etNoKamar);
        etTglPesan = findViewById(R.id.etTglPesan);
        rv_orderList = findViewById(R.id.rv_orderList);

        userPreferences = new UserPreferences(ActivityHistory.this);

        rv_orderList.setLayoutManager(new LinearLayoutManager(ActivityHistory.this));

        getOrders();

        orderList = new ArrayList<>();
    }

    private void getOrders() {
        class GetOrders extends AsyncTask<Void, Void, List<Order>> {

            @Override
            protected List<Order> doInBackground(Void... voids) {
                List<Order> todoList = DatabaseClient.getInstance(ActivityHistory.this)
                        .getDatabase()
                        .orderDao()
                        .getOrdersByUserId(userPreferences.getUserLogin().getId());
                return todoList;
            }

            @Override
            protected void onPostExecute(List<Order> orders) {
                super.onPostExecute(orders);
                orderAdapter = new OrderAdapter(orders, ActivityHistory.this);
                rv_orderList.setAdapter(orderAdapter);
            }
        }

        GetOrders getOrders = new GetOrders();
        getOrders.execute();
    }
}
