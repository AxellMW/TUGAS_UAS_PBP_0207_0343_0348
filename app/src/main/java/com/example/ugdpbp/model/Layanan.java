package com.example.ugdpbp.model;



public class Layanan {
    private Long id;
    private String no_kamar;
    private String layanan;

    public Layanan(String no_kamar, String layanan) {
        this.no_kamar = no_kamar;
        this.layanan = layanan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo_kamar() {
        return no_kamar;
    }

    public void setNo_kamar(String no_kamar) {
        this.no_kamar = no_kamar;
    }

    public String getLayanan() {
        return layanan;
    }

    public void setLayanan(String layanan) {
        this.layanan = layanan;
    }
}
