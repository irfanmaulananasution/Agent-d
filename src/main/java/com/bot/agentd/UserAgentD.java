package com.bot.agentd;

import java.util.ArrayList;

public class UserAgentD {
    String id;
    ArrayList<TugasIndividu> listTugasIndividu;
    ArrayList<TugasKelompok> listTugasKelompok;

    UserAgentD(String userID){
        id = userID;
        this.listTugasIndividu = new ArrayList<>();
        this.listTugasKelompok = new ArrayList<>();
    }

    void addTugasIndividu(TugasIndividu task){
        listTugasIndividu.add(task);
    }

    void addTugasKelompok(TugasKelompok tugasKelompok){
        listTugasKelompok.add(tugasKelompok);
    }
    void removeTugasKelompok(TugasKelompok tugasKelompok){
        listTugasKelompok.remove(tugasKelompok);
    }

    String remindTugasKelompok(TugasKelompok tugasKelompok){
        String namaTugas = tugasKelompok.getName();
        String deadline = tugasKelompok.getDeadline().toString();
        return "Jangan lupa kerjakan tugas "+namaTugas+", dengan deadline "+deadline;

    }

    ArrayList<TugasIndividu> getTugasIndividu(){
        return this.listTugasIndividu;
    }
}
