package com.example.ugdpbp.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ugdpbp.list.ListKamar;
import com.example.ugdpbp.model.Kamar;
import com.example.ugdpbp.R;
import com.example.ugdpbp.RecyclerVIew.RecyclerViewAdapter;
import com.example.ugdpbp.databinding.ListKamarBinding;

import java.util.ArrayList;

public class FragmentList extends AppCompatActivity{
    private ArrayList<Kamar> list;
    private RecyclerView rv;
    private RecyclerViewAdapter rv_adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ListKamarBinding binding;
    private RecyclerView rvPeserta;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.list_kamar);

        list = new ListKamar().kamar;

        rv = binding.rvPeserta;
        rv_adapter = new RecyclerViewAdapter(FragmentList.this, list);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(rv_adapter);
    }
}
