package com.example.ugdpbp.model;


import com.google.gson.annotations.SerializedName;

public class LayananResponse2 {

    private String message;

    @SerializedName("layanan")
    private Layanan layanan;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Layanan getLayanan() {
        return layanan;
    }

    public void setLayanan(Layanan layanan) {
        this.layanan = layanan;
    }
}
