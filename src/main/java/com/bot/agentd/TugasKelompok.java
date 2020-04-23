package com.bot.agentd;

import java.util.ArrayList;
import java.util.Date;

public class TugasKelompok implements Tugas {
    String name;
    String desc;
    Date deadline;
    UserAgentD owner;
    ArrayList<UserAgentD> anggota;

    TugasKelompok(String name,String desc, Date deadline, UserAgentD owner){
        this.name = name;
        this.desc = desc;
        this.deadline = deadline;
        this.owner = owner;
        this.anggota = new ArrayList<>();
    }

    public void addAnggota(UserAgentD anggota){
        if(isAnggotaExist(anggota)==false) {
            this.anggota.add(anggota);
            anggota.addTugasKelompok(this);
        }
    }

    public void removeAnggota(UserAgentD anggota){
        if(isAnggotaExist(anggota)){
            this.anggota.remove(anggota);
            anggota.removeTugasKelompok(this);
        }
    }

    private boolean isAnggotaExist(UserAgentD anggota){
        if(this.anggota.contains(anggota))
            return true;
        return false;
    }

    public void remindDeadline(){
        for(UserAgentD ang : anggota){
            ang.remindTugasKelompok(this);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public Date getDeadline() {
        return deadline;
    }

    @Override
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public UserAgentD getOwner() {
        return owner;
    }

    public void setOwner(UserAgentD owner) {
        this.owner = owner;
    }
}
