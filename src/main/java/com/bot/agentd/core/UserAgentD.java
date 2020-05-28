package com.bot.agentd.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class UserAgentD {
    String id;
    String uName;
    HashMap<String, TugasIndividu> mapTugasIndividu;
    ArrayList<TugasKelompok> listTugasKelompok;
    ArrayList<Jadwal>  listJadwal;
    static LogManager lgmngr = LogManager.getLogManager();
    public static Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static Quotes qRepo = new Quotes();

    public UserAgentD(String userID, String uname){
        id = userID;
        this.uName = uname;
        this.mapTugasIndividu = new HashMap<>();
        this.listTugasKelompok = new ArrayList<>();
        this.listJadwal = new ArrayList<>();
    }

    public String getId(){
        return this.id;
    }

    public String getuName(){
        return this.uName;
    }

    void addTugasIndividu(TugasIndividu task){
        mapTugasIndividu.put(task.getId(),task);
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

    ArrayList<Jadwal> getJadwal(){
        return this.listJadwal;
    }


    public String tambahTugasIndividu(String nama, String deskripsi, String deadline) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String[] deadlineSplit = deadline.split("-");
            String deadlineMerge = deadlineSplit[0]+"/"+deadlineSplit[1]+"/"+deadlineSplit[2];
            Date tanggal = dateFormat.parse(deadlineMerge);
            TugasIndividu tugas = new TugasIndividu(nama, deskripsi, tanggal);
            this.addTugasIndividu(tugas);
            return nama + " berhasil ditambahkan sebagai tugas individu kamu, "+this.uName;
        }catch (ParseException e){
            log.log(Level.INFO, "Error while parsing the date");
            return "Tanggal tidak dikenal";
        }
    }

    public String tambahTugasKelompok(String nama, String deskripsi, String deadline){
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String[] deadlineSplit = deadline.split("-");
            String deadlineMerge = deadlineSplit[0]+"/"+deadlineSplit[1]+"/"+deadlineSplit[2];
            Date tanggal = dateFormat.parse(deadlineMerge);
            TugasKelompok tgsKelompok = new TugasKelompok(nama,deskripsi,tanggal,this);
            this.addTugasKelompok(tgsKelompok);
            return nama + " berhasil ditabahkan sebagai tugas kelompok";
        }catch (ParseException e){
            log.log(Level.INFO, "Error while parsing the date");
            return "Tanggal tidak dikenal";
        }
    }

    public String tambahJadwal (String name, String day, String timeStart, String timeEnd) {
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            Date timeStartDate = timeFormat.parse(timeStart);
            Date timeEndDate = timeFormat.parse(timeEnd);
            Jadwal jadwal = new Jadwal(name, day, timeStartDate, timeEndDate);
            this.addJadwal(jadwal);
            return (name + "berhasil ditambahkan dalam jadwal");
        } catch (ParseException e) {
            log.log(Level.INFO, "Error while parsing the date");
            return "Jam tidak dikenal";
        }
    }

    public String lihatTugasIndividu(){
        String jawaban = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for(TugasIndividu task :this.mapTugasIndividu.values()) {
            jawaban += "id tugas : " + task.getId() + "\n";
            jawaban += "nama tugas : " + task.getName() + "\n";
            jawaban += "deskripsi : " + task.getDesc() + "\n";
            jawaban += "deadline : " + dateFormat.format(task.getDeadline()) + "\n\n";
        }
        return jawaban;
    }
    public String lihatJadwal() {
        String jawaban = "";
        for (Jadwal jadwalSekarang : this.getJadwal()) {
            String name = jadwalSekarang.getName();
            String day = jadwalSekarang.getDay();
            Date timeStart = jadwalSekarang.getTimeStart();
            Date timeEnd = jadwalSekarang.getTimeEnd();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String strTimeStart = timeFormat.format(timeStart);
            String strTimeEnd = timeFormat.format(timeEnd);
            jawaban += name + " " + day + " " + strTimeStart + " - " + strTimeEnd + "\n";
        }
        return jawaban;
    }

    void addJadwal(Jadwal jadwal){
        listJadwal.add(jadwal);
    }
    void removeJadwal(Jadwal jadwal) {
        listJadwal.remove(jadwal);
    }

    public String lihatTugasKelompok(){
        String jawaban = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for(int iteratorTugasKelompok = 0; iteratorTugasKelompok < this.listTugasKelompok.size(); iteratorTugasKelompok++){
            TugasKelompok tgsKelompok = this.getTugasKelompok().get(iteratorTugasKelompok);
            ArrayList<UserAgentD> anggota = tgsKelompok.getAnggota();
            String anggotaKelompok = "";
            for(int iteratorAnggota = 0; iteratorAnggota < anggota.size(); iteratorAnggota++){
                if(iteratorAnggota !=anggota.size()-1)
                    anggotaKelompok+=anggota.get(iteratorAnggota).uName+", ";
                else
                    anggotaKelompok+=anggota.get(iteratorAnggota).uName+".";
            }
            jawaban+="nama tugas kelompok : "+tgsKelompok.getName()+"\n";
            jawaban+="desktripsi : "+tgsKelompok.getDesc()+"\n";
            jawaban+="deadline : "+dateFormat.format(tgsKelompok.getDeadline())+"\n";
            jawaban+="anggota : "+anggotaKelompok+"\n\n";

        }
        return jawaban;
    }

    ArrayList<TugasKelompok> getTugasKelompok() {
        return listTugasKelompok;
    }
}
