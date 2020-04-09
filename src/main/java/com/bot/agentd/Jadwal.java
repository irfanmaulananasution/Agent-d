package com.bot.agentd;

import java.util.Date;

public class Jadwal{
    String namaKegiatan;
    Date waktuKegiatan;

    Jadwal(String namaKegiatan, Date waktuKegiatan){
        this.namaKegiatan = namaKegiatan;
        this.waktuKegiatan = waktuKegiatan;
    }

    public String getName(){
        return this.namaKegiatan;
    }

    public void setName(String namaKegiatan){
        this.namaKegiatan = namaKegiatan;
    }

    public Date getDate(){
        return this.waktuKegiatan;
    }

    public void setDate(Date waktuKegiatan){
        this.waktuKegiatan = waktuKegiatan;
    }
}
