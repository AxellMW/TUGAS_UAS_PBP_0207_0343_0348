package com.example.ugdpbp.list;

import java.util.ArrayList;
import com.example.ugdpbp.model.Kamar;

public class ListKamar{
    public ArrayList<Kamar> kamar;
    public ListKamar(){
        kamar = new ArrayList<>();
        kamar.add(kamar1);
        kamar.add(kamar2);
        kamar.add(kamar3);
        kamar.add(kamar4);
        kamar.add(kamar5);
        kamar.add(kamar6);
    }
    public static final Kamar kamar1 = new Kamar("Kamar 111", "Deluxe",
            200000, "https://cdn.idntimes.com/content-images/post/20200918/eh-eden-hotel-amsterdam-double-1width-2560-5ec5bb3051ff608d461c4acb97100b66.jpg");

    public static final Kamar kamar2 = new Kamar("Kamar 222", "Standart",
            150000, "https://asset.kompas.com/crops/33vZ6Rt128kzOfcC_aU3fy7oo0I=/0x36:640x463/750x500/data/photo/2020/07/10/5f081b41cc76c.jpeg");

    public static final Kamar kamar3 = new Kamar("Kamar 333", "Double",
            175000, "https://static.republika.co.id/uploads/images/inpicture_slide/kamar-hotel-_160916102439-638.jpg");

    public static final Kamar kamar4 = new Kamar("Kamar 444", "Suite",
            300000, "https://balitribune.co.id/sites/default/files/styles/xtra_large/public/field/image/Aplikasi%20Pemesanan%20Kamar%20Hotel%20Antisipasi%20Kenaikan%20Permintaan%20Libur%20Lebaran.jpg?itok=bT0PzY0_");

    public static final Kamar kamar5 = new Kamar("Kamar 555", "Deluxe",
            200000, "https://image.akurat.co/images/uploads/images/akurat_20190822090634_8L618a.jpg");

    public static final Kamar kamar6 = new Kamar("Kamar 666", "Suite",
            300000, "https://ecs7.tokopedia.net/blog-tokopedia-com/uploads/2020/02/4.-Family-room-sumber-gambar-newsaphirhotel.jpg");
}
