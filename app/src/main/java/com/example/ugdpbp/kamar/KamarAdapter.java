package com.example.ugdpbp.kamar;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ugdpbp.R;
import com.example.ugdpbp.model.Kamar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class KamarAdapter extends RecyclerView.Adapter<KamarAdapter.ViewHolder>
        implements Filterable {

    private List<Kamar> kamarList, filteredKamarList;
    private Context context;

    public KamarAdapter(List<Kamar> kamarList, Context context) {
        this.kamarList = kamarList;
        filteredKamarList = new ArrayList<>(kamarList);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_kamar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Kamar kamar = filteredKamarList.get(position);
        holder.tvNama.setText(kamar.getNama());
        holder.tvNo_telp.setText(kamar.getNo_telp());
        holder.tvInfo.setText(kamar.getTipe_kamar() + " - " + kamar.getNo_kamar());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder =
                        new MaterialAlertDialogBuilder(context);
                materialAlertDialogBuilder.setTitle("Konfirmasi")
                        .setMessage("Apakah anda yakin ingin menghapus data kamar ini?")
                        .setNegativeButton("Batal", null)
                        .setPositiveButton("Hapus", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (context instanceof KamarActivity)
                                            ((KamarActivity) context).deleteKamar(kamar.getId());
                                    }
                                })
                        .show();
            }
        });

        holder.cvKamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, AddEditKamarActivity.class);
                i.putExtra("id", kamar.getId());
                if (context instanceof KamarActivity)
                    ((KamarActivity) context).startActivityForResult(i,
                            KamarActivity.LAUNCH_ADD_ACTIVITY);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredKamarList.size();
    }

    public void setKamarList(List<Kamar> kamarList) {
        this.kamarList = kamarList;
        filteredKamarList = new ArrayList<>(kamarList);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                List<Kamar> filtered = new ArrayList<>();
                if (charSequenceString.isEmpty()) {
                    filtered.addAll(kamarList);
                } else {
                    for (Kamar kamar : kamarList) {
                        if (kamar.getNama().toLowerCase()
                                .contains(charSequenceString.toLowerCase()))
                            filtered.add(kamar);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults
                    filterResults) {
                filteredKamarList.clear();
                filteredKamarList.addAll((List<Kamar>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvNo_telp, tvInfo;
        ImageButton btnDelete;
        CardView cvKamar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNo_telp = itemView.findViewById(R.id.tv_no_telp);
            tvNama = itemView.findViewById(R.id.tv_title);
            tvInfo = itemView.findViewById(R.id.tv_info);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            cvKamar = itemView.findViewById(R.id.cv_kamar);
        }
    }
}
