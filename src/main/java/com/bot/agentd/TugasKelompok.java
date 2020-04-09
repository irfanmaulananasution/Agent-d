package com.bot.agentd;

import java.util.ArrayList;
import java.util.Date;

public class TugasKelompok implements Tugas {
    String name;
    Date deadline;
    UserAgentD owner;
    ArrayList<UserAgentD> anggota;

    TugasKelompok(String name, Date deadline, UserAgentD owner){
        this.name = name;
        this.deadline = deadline;
        this.owner = owner;
        this.anggota = new ArrayList<UserAgentD>();
    }

    public void addAnggota(UserAgentD anggota){
        // add user lain kedalam tugas kelompok
    }

    public void removeAnggota(UserAgentD anggota){
        // remove sebuah anggota dari tugas kelompok
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
    public Date getDeadline() {
        return deadline;
    }

    @Override
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
