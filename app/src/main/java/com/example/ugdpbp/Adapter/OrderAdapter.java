package com.example.ugdpbp.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ugdpbp.Database.DatabaseClient;
import com.example.ugdpbp.model.Order;
import com.example.ugdpbp.Preferences.UserPreferences;
import com.example.ugdpbp.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private List<Order> orderList;
    private Context context;
    private DatabaseClient databaseClient;
    private UserPreferences userPreferences;

    public OrderAdapter(List<Order> orderList, Context context){
        this.orderList = orderList;
        this.context = context;
        this.userPreferences = new UserPreferences(context);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //init view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tvNamaLengkap.setText(order.getNama_lengkap());
        holder.tvNoTelp.setText(order.getNo_telp());
        holder.tvTipeKamar.setText(order.getTipe_kamar());
        holder.tvNomorKamar.setText(order.getNo_kamar());
        holder.tvTanggalPemesanan.setText(order.getTanggal_pemesanan());
        databaseClient = DatabaseClient.getInstance(context);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseClient.getDatabase()
                        .orderDao()
                        .deleteOrder(order);
                Toast.makeText(context, "Berhasil menghapus", Toast.LENGTH_SHORT).show();
                orderList.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update);
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setLayout(width,height);
                dialog.show();

                EditText edit_nama = dialog.findViewById(R.id.edit_nama);
                EditText edit_noTelp = dialog.findViewById(R.id.edit_noTelp);
                EditText edit_tipeKamar = dialog.findViewById(R.id.edit_tipeKamar);
                EditText edit_noKamar = dialog.findViewById(R.id.edit_noKamar);
                EditText edit_tanggalPemesanan = dialog.findViewById(R.id.edit_tanggalPemesanan);
                Button btnUpdate = dialog.findViewById(R.id.btnUpdate);

                edit_nama.setText(order.getNama_lengkap());
                edit_noTelp.setText(order.getNo_telp());
                edit_tipeKamar.setText(order.getTipe_kamar());
                edit_noKamar.setText(order.getNo_kamar());
                edit_tanggalPemesanan.setText(order.getTanggal_pemesanan());

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!edit_nama.getText().toString().isEmpty()){
                            order.setNama_lengkap(edit_nama.getText().toString());
                            databaseClient.getDatabase()
                                    .orderDao()
                                    .updateOrder(order);
                            orderList.clear();
                            orderList.addAll(databaseClient.getDatabase().orderDao().getOrdersByUserId(userPreferences.getUserLogin().getId()));
                            notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(context, "Berhasil mengubah data", Toast.LENGTH_SHORT).show();
                        }else if(!edit_noTelp.getText().toString().isEmpty()){
                            order.setNo_telp(edit_noTelp.getText().toString());
                            databaseClient.getDatabase()
                                    .orderDao()
                                    .updateOrder(order);
                            orderList.clear();
                            orderList.addAll(databaseClient.getDatabase().orderDao().getOrdersByUserId(userPreferences.getUserLogin().getId()));
                            notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(context, "Berhasil mengubah data", Toast.LENGTH_SHORT).show();
                        }else if(!edit_tipeKamar.getText().toString().isEmpty()){
                            order.setTipe_kamar(edit_tipeKamar.getText().toString());
                            databaseClient.getDatabase()
                                    .orderDao()
                                    .updateOrder(order);
                            orderList.clear();
                            orderList.addAll(databaseClient.getDatabase().orderDao().getOrdersByUserId(userPreferences.getUserLogin().getId()));
                            notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(context, "Berhasil mengubah data", Toast.LENGTH_SHORT).show();
                        }else if(!edit_noKamar.getText().toString().isEmpty()){
                            order.setNo_kamar(edit_noKamar.getText().toString());
                            databaseClient.getDatabase()
                                    .orderDao()
                                    .updateOrder(order);
                            orderList.clear();
                            orderList.addAll(databaseClient.getDatabase().orderDao().getOrdersByUserId(userPreferences.getUserLogin().getId()));
                            notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(context, "Berhasil mengubah data", Toast.LENGTH_SHORT).show();
                        }else if(!edit_tanggalPemesanan.getText().toString().isEmpty()){
                            order.setTanggal_pemesanan(edit_tanggalPemesanan.getText().toString());
                            databaseClient.getDatabase()
                                    .orderDao()
                                    .updateOrder(order);
                            orderList.clear();
                            orderList.addAll(databaseClient.getDatabase().orderDao().getOrdersByUserId(userPreferences.getUserLogin().getId()));
                            notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(context, "Berhasil mengubah data", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNamaLengkap, tvNoTelp, tvTipeKamar, tvNomorKamar, tvTanggalPemesanan;
        private Button btnDelete,btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaLengkap = itemView.findViewById(R.id.tvNamaLengkap);
            tvNoTelp = itemView.findViewById(R.id.tvNoTelp);
            tvTipeKamar = itemView.findViewById(R.id.tvTipeKamar);
            tvNomorKamar = itemView.findViewById(R.id.tvNomorKamar);
            tvTanggalPemesanan = itemView.findViewById(R.id.tvTanggalPemesanan);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);

        }
    }
}
