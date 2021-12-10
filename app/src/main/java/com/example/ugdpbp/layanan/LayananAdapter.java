package com.example.ugdpbp.layanan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ugdpbp.R;
import com.example.ugdpbp.layanan.AddEditLayananActivity;
import com.example.ugdpbp.layanan.LayananActivity;
import com.example.ugdpbp.layanan.LayananAdapter;
import com.example.ugdpbp.model.Layanan;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class LayananAdapter extends RecyclerView.Adapter<LayananAdapter.ViewHolder>
        implements Filterable {

    private List<Layanan> layananList, filteredLayananList;
    private Context context;

    public LayananAdapter(List<Layanan> layananList, Context context) {
        this.layananList = layananList;
        filteredLayananList = new ArrayList<>(layananList);
        this.context = context;
    }

    @NonNull
    @Override
    public LayananAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_layanan, parent, false);
        return new LayananAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LayananAdapter.ViewHolder holder, int position) {
        Layanan layanan = filteredLayananList.get(position);
        holder.tv_no_kamar.setText(layanan.getNo_kamar());
        holder.tv_layanan.setText(layanan.getLayanan());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder =
                        new MaterialAlertDialogBuilder(context);
                materialAlertDialogBuilder.setTitle("Konfirmasi")
                        .setMessage("Apakah anda yakin ingin menghapus data layanan ini?")
                        .setNegativeButton("Batal", null)
                        .setPositiveButton("Hapus", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (context instanceof LayananActivity)
                                            ((LayananActivity) context).deleteLayanan(layanan.getId());
                                    }
                                })
                        .show();
            }
        });

        holder.cvLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, AddEditLayananActivity.class);
                i.putExtra("id", layanan.getId());
                if (context instanceof LayananActivity)
                    ((LayananActivity) context).startActivityForResult(i,
                            LayananActivity.LAUNCH_ADD_ACTIVITY);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredLayananList.size();
    }

    public void setLayananList(List<Layanan> layananList) {
        this.layananList = layananList;
        filteredLayananList = new ArrayList<>(layananList);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                List<Layanan> filtered = new ArrayList<>();
                if (charSequenceString.isEmpty()) {
                    filtered.addAll(layananList);
                } else {
                    for (Layanan layanan : layananList) {
                        if (layanan.getLayanan().toLowerCase()
                                .contains(charSequenceString.toLowerCase()))
                            filtered.add(layanan);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults
                    filterResults) {
                filteredLayananList.clear();
                filteredLayananList.addAll((List<Layanan>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_no_kamar, tv_layanan;
        ImageButton btnDelete;
        CardView cvLayanan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_no_kamar = itemView.findViewById(R.id.tv_no_kamar);
            tv_layanan = itemView.findViewById(R.id.tv_layanan);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            cvLayanan = itemView.findViewById(R.id.cv_layanan);
        }
    }
}