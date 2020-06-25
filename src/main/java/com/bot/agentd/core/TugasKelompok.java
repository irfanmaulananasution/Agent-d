package com.bot.agentd.core;


import javax.persistence.*;
import java.util.Arrays;
import java.util.List;


@Entity
@Table(name = "tugas_kelompok")
public class TugasKelompok {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "deadline")
    private String deadline;

    @Column(name = "daftar_anggota")
    private String daftarAnggota="";

    @Column(name = "ownerId")
    private String ownerId;

    public TugasKelompok(){

    }

    public TugasKelompok(String nama, String desc, String deadline,UserAgentD owner){
        this.name = nama;
        this.description = desc;
        this.deadline = deadline;
        this.ownerId = owner.getId();
        this.daftarAnggota=this.ownerId+" ";
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDesc(String desc){
        this.description = desc;
    }

    public void setDeadline(String date){
        this.deadline = date;
    }

    public void setDaftarAnggota(String daftarAnggota) {
        this.daftarAnggota = addAnggota(daftarAnggota);
    }

    public long getId(){
        return this.id;
    }

    public String getOwnerId(){
        return this.ownerId;
    }

    public String getName(){
        return this.name;
    }

    public String getDesc(){
        return this.description;
    }

    public String getDeadline(){
        return this.deadline;
    }

    public String getDaftarAnggota(){
        return this.daftarAnggota;
    }



    public String addAnggota(String anggota){
        List<String> listAnggota = Arrays.asList(this.daftarAnggota.split(" "));
        String anggotaStr = this.daftarAnggota;
        if(!listAnggota.contains(anggota))
            anggotaStr+=anggota+" ";

        return anggotaStr;

    }
    public void removeAnggota(UserAgentD anggota) {
        List<String> listAnggota = Arrays.asList(this.daftarAnggota.split(" "));
        this.daftarAnggota="";
        for(int iteratorAnggota = 0; iteratorAnggota < listAnggota.size(); iteratorAnggota++){
            if(!listAnggota.get(iteratorAnggota).equals(anggota.getId())) daftarAnggota+=listAnggota.get(iteratorAnggota)+" ";
        }
    }

}
