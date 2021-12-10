package com.example.ugdpbp.list;

import java.util.ArrayList;

import com.example.ugdpbp.model.Daftar;

public class ListKamar{
    public ArrayList<Daftar> daftar;
    public ListKamar(){
        daftar = new ArrayList<>();
        daftar.add(DAFTAR_1);
        daftar.add(DAFTAR_2);
        daftar.add(DAFTAR_3);
        daftar.add(DAFTAR_4);
        daftar.add(DAFTAR_5);
        daftar.add(DAFTAR_6);
    }
    public static final Daftar DAFTAR_1 = new Daftar("Kamar 111", "Deluxe",
            200000, "https://cdn.idntimes.com/content-images/post/20200918/eh-eden-hotel-amsterdam-double-1width-2560-5ec5bb3051ff608d461c4acb97100b66.jpg");

    public static final Daftar DAFTAR_2 = new Daftar("Kamar 222", "Standart",
            150000, "https://asset.kompas.com/crops/33vZ6Rt128kzOfcC_aU3fy7oo0I=/0x36:640x463/750x500/data/photo/2020/07/10/5f081b41cc76c.jpeg");

    public static final Daftar DAFTAR_3 = new Daftar("Kamar 333", "Double",
            175000, "https://static.republika.co.id/uploads/images/inpicture_slide/kamar-hotel-_160916102439-638.jpg");

    public static final Daftar DAFTAR_4 = new Daftar("Kamar 444", "Suite",
            300000, "https://balitribune.co.id/sites/default/files/styles/xtra_large/public/field/image/Aplikasi%20Pemesanan%20Kamar%20Hotel%20Antisipasi%20Kenaikan%20Permintaan%20Libur%20Lebaran.jpg?itok=bT0PzY0_");

    public static final Daftar DAFTAR_5 = new Daftar("Kamar 555", "Deluxe",
            200000, "https://image.akurat.co/images/uploads/images/akurat_20190822090634_8L618a.jpg");

    public static final Daftar DAFTAR_6 = new Daftar("Kamar 666", "Suite",
            300000, "https://ecs7.tokopedia.net/blog-tokopedia-com/uploads/2020/02/4.-Family-room-sumber-gambar-newsaphirhotel.jpg");
}
