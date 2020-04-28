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
    String tidakDikenal = "Command Tidak Dikenali";
    static LogManager lgmngr = LogManager.getLogManager();
    static Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);


    UserAgentD(String userID){
        id = userID;
        this.listTugasIndividu = new ArrayList<>();
    }

    void addTugasIndividu(TugasIndividu task){
        listTugasIndividu.add(task);
    }

    ArrayList<TugasIndividu> getTugasIndividu(){
        return this.listTugasIndividu;
    }

    public String periksaMessage(String[] pesanSplit){
        String jawaban = "";
        switch (pesanSplit[0].toLowerCase()){
            case("tambah"):
                switch (pesanSplit[1].toLowerCase()){
                    case("tugas individu"):
                        jawaban = this.tambahTugasIndividu(pesanSplit[2], pesanSplit[3], pesanSplit[4]);
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
}
