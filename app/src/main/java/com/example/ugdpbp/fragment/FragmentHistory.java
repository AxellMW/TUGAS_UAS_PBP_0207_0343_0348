package com.example.ugdpbp.fragment;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ugdpbp.Adapter.OrderAdapter;
import com.example.ugdpbp.Database.DatabaseClient;
import com.example.ugdpbp.model.Order;
import com.example.ugdpbp.Preferences.UserPreferences;
import com.example.ugdpbp.R;

import java.util.List;
import java.util.ArrayList;

public class FragmentHistory extends Fragment {
    private EditText etNamaLengkap, etNoTelp, etTipeKamar, etNoKamar, etTglPesan;
    private RecyclerView rv_orderList;
    private UserPreferences userPreferences;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private List<Order> orderList;
    private OrderAdapter orderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.history_page, container, false);
        etNamaLengkap = root.findViewById(R.id.etNamaLengkap);
        etNoTelp = root.findViewById(R.id.etNoTelp);
        etTipeKamar = root.findViewById(R.id.etTipeKamar);
        etNoKamar = root.findViewById(R.id.etNoKamar);
        etTglPesan = root.findViewById(R.id.etTglPesan);
        rv_orderList = root.findViewById(R.id.rv_orderList);

        userPreferences = new UserPreferences(getContext());

        rv_orderList.setLayoutManager(new LinearLayoutManager(getContext()));

        getOrders();

        orderList = new ArrayList<>();

        return root;
    }

    private void getOrders() {
        class GetOrders extends AsyncTask<Void, Void, List<Order>> {

            @Override
            protected List<Order> doInBackground(Void... voids) {
                List<Order> todoList = DatabaseClient.getInstance(getContext())
                        .getDatabase()
                        .orderDao()
                        .getOrdersByUserId(userPreferences.getUserLogin().getId());
                return todoList;
            }

            @Override
            protected void onPostExecute(List<Order> orders) {
                super.onPostExecute(orders);
                orderAdapter = new OrderAdapter(orders, getContext());
                rv_orderList.setAdapter(orderAdapter);
            }
        }

        GetOrders getOrders = new GetOrders();
        getOrders.execute();
    }
}
