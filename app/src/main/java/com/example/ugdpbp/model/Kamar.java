package com.example.ugdpbp.model;


public class Kamar {
    private Long id;
    private String nama;
    private String no_telp;
    private String tipe_kamar;
    private String no_kamar;

    public Kamar(String nama, String no_telp, String tipe_kamar, String no_kamar) {
        this.nama = nama;
        this.no_telp = no_telp;
        this.tipe_kamar = tipe_kamar;
        this.no_kamar = no_kamar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getTipe_kamar() {
        return tipe_kamar;
    }

    public void setTipe_kamar(String tipe_kamar) {
        this.tipe_kamar = tipe_kamar;
    }

    public String getNo_kamar() {
        return no_kamar;
    }

    public void setNo_kamar(String no_kamar) {
        this.no_kamar = no_kamar;
    }
}
