package com.example.ugdpbp.model;


import com.google.gson.annotations.SerializedName;

public class KamarResponse2 {
    private String message;

    @SerializedName("kamar")
    private Kamar kamar;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Kamar getKamar() {
        return kamar;
    }

    public void setKamar(Kamar kamar) {
        this.kamar = kamar;
    }
}
