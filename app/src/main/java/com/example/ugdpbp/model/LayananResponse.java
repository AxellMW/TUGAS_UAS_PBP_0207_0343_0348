package com.example.ugdpbp.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LayananResponse {
    private String message;

    @SerializedName("layanan")
    private List<Layanan> layananList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Layanan> getLayananList() {
        return layananList;
    }

    public void setLayananList(List<Layanan> layananList) {
        this.layananList = layananList;
    }
}
