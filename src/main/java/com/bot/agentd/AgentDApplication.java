package com.bot.agentd;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@SpringBootApplication
@LineMessageHandler
public class AgentDApplication extends SpringBootServletInitializer {

    @Autowired
    private LineMessagingClient lineMessagingClient;

    static HashMap<String, UserAgentD> repo = new HashMap<>();
    String tidakDikenal = "Command tidak dikenal";
    LogManager lgmngr = LogManager.getLogManager();
    Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AgentDApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AgentDApplication.class, args);
    }

    @EventMapping
    public void handleTextEvent(MessageEvent<TextMessageContent> messageEvent){
        String pesan = messageEvent.getMessage().getText();
        String[] pesanSplit = pesan.split("-");
        String userId = messageEvent.getSource().getSenderId();
        if(repo.get(userId)==null){
            repo.put(userId,new UserAgentD(userId));
        }
        UserAgentD user = repo.get(userId);
        String jawaban = periksaMessage(pesanSplit, user);

        String replyToken = messageEvent.getReplyToken();
        replyText(replyToken, jawaban);
    }

    @EventMapping
    public void handleFollowEvent(FollowEvent event) {
        UserAgentD user = new UserAgentD(event.getSource().getSenderId());
        repo.put(event.getSource().getSenderId(),user);
    }

    private String periksaMessage(String[] pesanSplit, UserAgentD user){
        String jawaban = "";
        switch (pesanSplit[0].toLowerCase()){
            case("tambah"):
                switch (pesanSplit[1].toLowerCase()){
                    case("tugas individu"):
                        jawaban = tambahTugasIndividu(pesanSplit[2], pesanSplit[3], pesanSplit[4], user);
                        break;
                    case("tugas kelompok"):
                        jawaban = tambahTugasKelompok(pesanSplit[2], pesanSplit[3], pesanSplit[4], user);
                        break;
                    default:
                        jawaban = tidakDikenal;
                }
                break;
            case("lihat"):
                switch (pesanSplit[1].toLowerCase()){
                    case ("tugas individu"):
                        jawaban = lihatTugasIndividu(user);
                        break;
                    case ("tugas kelompok"):
                        jawaban = lihatTugasKelompok(user);
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

    private String tambahTugasIndividu(String nama, String deskripsi, String deadline, UserAgentD user) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date tanggal = dateFormat.parse(deadline);
            TugasIndividu tugas = new TugasIndividu(nama, deskripsi, tanggal);
            user.addTugasIndividu(tugas);
            return nama + " berhasil ditambahkan sebagai tugas individu";
        }catch (ParseException e){
            log.log(Level.INFO, "Error while parsing the date");
            return "Tanggal tidak dikenal";
        }
    }

    private String tambahTugasKelompok(String nama, String deskripsi, String deadline, UserAgentD user){
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date tanggal = dateFormat.parse(deadline);
            TugasKelompok tgsKelompok = new TugasKelompok(nama,deskripsi,tanggal,user);
            user.addTugasKelompok(tgsKelompok);
            return nama + " berhasil ditabahkan sebagai tugas kelompok";
        }catch (ParseException e){
            log.log(Level.INFO, "Error while parsing the date");
            return "Tanggal tidak dikenal";
        }
    }

    private String lihatTugasIndividu(UserAgentD user){
        String jawaban = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for(int i = 0;i<user.listTugasIndividu.size();i++){
            jawaban+="nama tugas : "+user.getTugasIndividu().get(i).getName()+"\n";
            jawaban+="deskripsi : "+user.getTugasIndividu().get(i).getDesc()+"\n";
            jawaban+="deadline : "+dateFormat.format(user.getTugasIndividu().get(i).getDeadline())+"\n\n";
        }
        return jawaban;
    }

    private String lihatTugasKelompok(UserAgentD user){
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


    private void replyText(String replyToken, String jawaban){
        TextMessage jawabanDalamBentukTextMessage = new TextMessage(jawaban);
        try {
            lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, jawabanDalamBentukTextMessage))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            log.log(Level.INFO, "Error while sending message");
            Thread.currentThread().interrupt();
        }
    }

}
