package com.example.ugdpbp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ugdpbp.list.ListKamar;
import com.example.ugdpbp.model.Daftar;
import com.example.ugdpbp.R;
import com.example.ugdpbp.RecyclerVIew.RecyclerViewAdapter;
import com.example.ugdpbp.databinding.ListKamarBinding;

import java.util.ArrayList;

public class ActivityList extends AppCompatActivity{
    private ArrayList<Daftar> daftar;
    private RecyclerView rv;
    private RecyclerViewAdapter rv_adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ListKamarBinding binding;
    private RecyclerView rvPeserta;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.list_kamar);

        daftar = new ListKamar().daftar;

        rv = binding.rvPeserta;
        rv_adapter = new RecyclerViewAdapter(ActivityList.this, daftar);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(rv_adapter);
    }
}
