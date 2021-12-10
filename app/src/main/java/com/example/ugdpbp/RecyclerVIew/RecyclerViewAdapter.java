package com.example.ugdpbp.RecyclerVIew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ugdpbp.R;
import com.example.ugdpbp.model.Daftar;
import com.example.ugdpbp.databinding.RecyclerViewAdapterBinding;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.viewHolder>{
    private List<Daftar> list;
    private Context context;

    public RecyclerViewAdapter(Context context, java.util.List list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new viewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_view_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position){
        final Daftar daftar = this.list.get(position);
        holder.bind(daftar);
    }

    @Override
    public int getItemCount() { return list.size(); }
    public class viewHolder extends RecyclerView.ViewHolder{
        public RecyclerViewAdapterBinding binding;

        public viewHolder(@NonNull RecyclerViewAdapterBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Daftar daftar){
            binding.setData(daftar);
            binding.setImgURL(daftar.imgURL);
            binding.executePendingBindings();
        }
    }
}
