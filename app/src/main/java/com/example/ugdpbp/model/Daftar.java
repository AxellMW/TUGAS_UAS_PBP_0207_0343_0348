package com.example.ugdpbp.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class Daftar {
    public String nama_kamar;
    public String tipe_kamar;
    public int harga;
    public String imgURL;

    public Daftar(String nama_kamar, String tipe_kamar, int harga, String imgURL){
        this.nama_kamar = nama_kamar;
        this.tipe_kamar = tipe_kamar;
        this.harga = harga;
        this.imgURL = imgURL;
    }

    public String getNama_kamar() {
        return nama_kamar;
    }

    public void setNama_kamar(String nama_kamar) {
        this.nama_kamar = nama_kamar;
    }

    public String getTipe_kamar() {
        return tipe_kamar;
    }

    public void setTipe_kamar(String tipe_kamar) {
        this.tipe_kamar = tipe_kamar;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getHargaString() {
        return String.valueOf(harga);
    }

    public void setHargaString(String harga){
        if(!harga.isEmpty()){
            this.harga = Integer.parseInt(harga);
        } else{
            this.harga = 0;
        }
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    @BindingAdapter("android:loadImage")
    public static void loadImage(ImageView view, String imageURL){
        Glide.with(view.getContext())
                .load(imageURL).apply(new RequestOptions())
                .into(view);
    }
}
