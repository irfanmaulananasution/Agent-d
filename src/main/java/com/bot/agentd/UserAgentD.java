package com.bot.agentd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class UserAgentD {
    String id;
    ArrayList<TugasIndividu> listTugasIndividu;
    ArrayList<TugasKelompok> listTugasKelompok;
    ArrayList<Jadwal>  listJadwal;
    String tidakDikenal = "Command Tidak Dikenali";
    static LogManager lgmngr = LogManager.getLogManager();
    static Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);

    UserAgentD(String userID){
        id = userID;
        this.listTugasIndividu = new ArrayList<>();
        this.listTugasKelompok = new ArrayList<>();
        this.listJadwal = new ArrayList<>();
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
    ArrayList<Jadwal> getJadwal(){
        return this.listJadwal;
    }

    public String periksaMessage(String[] pesanSplit){
        String jawaban = "";
        switch (pesanSplit[0].toLowerCase()){
            case("tambah"):
                switch (pesanSplit[1].toLowerCase()){
                    case("tugas individu"):
                        jawaban = this.tambahTugasIndividu(pesanSplit[2], pesanSplit[3], pesanSplit[4]);
                        break;
                    case("tugas kelompok"):
                        jawaban = tambahTugasKelompok(pesanSplit[2], pesanSplit[3], pesanSplit[4]);
                        break;
                    case ("jadwal"):
                        String name = pesanSplit[2];
                        String day = pesanSplit[3];
                        String startTime = pesanSplit[4];
                        String endTime = pesanSplit[5];
                        jawaban = this.tambahJadwal(name, day, startTime, endTime);
                        break;
                    default:
                        jawaban = tidakDikenal;
                }
                break;
            case("lihat"):
                switch (pesanSplit[1].toLowerCase()){
                    case ("tugas individu"):
                        jawaban = this.lihatTugasIndividu();
                        break;
                    case ("tugas kelompok"):
                        jawaban = lihatTugasKelompok(this);
                        break;
                    case ("jadwal"):
                        jawaban = this.lihatJadwal();
                        break;
                    default:
                        jawaban = tidakDikenal;
                }
                break;
            default:
                jawaban = tidakDikenal;
        }

        return jawaban;
    }

    public String tambahTugasIndividu(String nama, String deskripsi, String deadline) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date tanggal = dateFormat.parse(deadline);
            TugasIndividu tugas = new TugasIndividu(nama, deskripsi, tanggal);
            this.addTugasIndividu(tugas);
            return nama + " berhasil ditambahkan sebagai tugas individu";
        }catch (ParseException e){
            log.log(Level.INFO, "Error while parsing the date");
            return "Tanggal tidak dikenal";
        }
    }

    public String tambahTugasKelompok(String nama, String deskripsi, String deadline){
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date tanggal = dateFormat.parse(deadline);
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
        for(int i = 0;i<this.listTugasIndividu.size();i++){
            jawaban+="nama tugas : "+this.getTugasIndividu().get(i).getName()+"\n";
            jawaban+="deskripsi : "+this.getTugasIndividu().get(i).getDesc()+"\n";
            jawaban+="deadline : "+dateFormat.format(this.getTugasIndividu().get(i).getDeadline())+"\n\n";
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
    }

    void addJadwal(Jadwal jadwal){
        listJadwal.add(jadwal);
    }
    void removeJadwal(Jadwal jadwal) {
        listJadwal.remove(jadwal);
    }

    public String lihatTugasKelompok(UserAgentD user){
        String jawaban = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for(int iteratorTugasKelompok = 0; iteratorTugasKelompok < user.listTugasKelompok.size(); iteratorTugasKelompok++){
            TugasKelompok tgsKelompok = user.getTugasKelompok().get(iteratorTugasKelompok);
            ArrayList<UserAgentD> anggota = tgsKelompok.getAnggota();
            String anggotaKelompok = "";
            for(int iteratorAnggota = 0; iteratorAnggota < anggota.size(); iteratorAnggota++){
                if(iteratorAnggota !=anggota.size()-1)
                    anggotaKelompok+=anggota.get(iteratorAnggota).id+", ";
                else
                    anggotaKelompok+=anggota.get(iteratorAnggota).id+".";
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
