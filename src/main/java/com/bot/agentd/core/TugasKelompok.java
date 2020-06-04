package com.bot.agentd.core;


import javax.persistence.*;
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
    private List<UserAgentD> daftarAnggota;

    @Column(name = "ownerId")
    private String ownerId;

    public TugasKelompok(){

    }

    public TugasKelompok(String nama, String desc, String deadline,UserAgentD owner){
        this.name = nama;
        this.description = desc;
        this.deadline = deadline;
        this.ownerId = owner.getId();
        this.daftarAnggota.add(owner);
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

    public String getDaftarAnggotaString(){
        String listAnggota="";
        for(int iteratorAnggota = 0; iteratorAnggota < daftarAnggota.size(); iteratorAnggota++){
            if(iteratorAnggota !=daftarAnggota.size()-1)
                listAnggota+=daftarAnggota.get(iteratorAnggota).getUserName()+", ";
            else
                listAnggota+=daftarAnggota.get(iteratorAnggota).getUserName()+".";
        }
        return listAnggota;
    }



    public void addAnggota(UserAgentD anggota){
        if(!this.daftarAnggota.contains(anggota))
            this.daftarAnggota.add(anggota);
    }
    public void removeAnggota(UserAgentD anggota){
        if(this.daftarAnggota.contains(anggota))
            this.daftarAnggota.remove(anggota);
    }

}
