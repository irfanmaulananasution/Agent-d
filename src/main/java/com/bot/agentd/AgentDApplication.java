package com.bot.agentd;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.ArrayList;


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
                    case ("jadwal"):
                        String name = pesanSplit[2];
                        String date = pesanSplit[3];
                        String startTime = pesanSplit[4];
                        String endTime = pesanSplit[5];
                        jawaban = tambahJadwal(name, date, startTime, endTime, user);
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
                    case ("jadwal"):
                        jawaban = lihatJadwal(user);
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

    private String tambahJadwal (String name, String date, String timeStart, String timeEnd, UserAgentD user){
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat( "HH:mm:ss");
            Date dateStart = dateFormat.parse(date);
            Date timeStartDate = timeFormat.parse(timeStart);
            Date timeEndDate = timeFormat.parse(timeEnd);
            Jadwal jadwal = new Jadwal(name, dateStart, timeStartDate, timeEndDate);
            user.addJadwal(jadwal);
            return (name + "berhasil ditambahkan dalam jadwal");
        }catch (ParseException e){
            log.log(Level.INFO, "Error while parsing the date");
            return "Tanggal tidak dikenal";
        }
    }
    private String lihatJadwal(UserAgentD user){
        String jawaban = "";
        ArrayList<Jadwal> listJadwal = user.getListJadwal();
        for (int i = 0; i < listJadwal.size(); i++){
            Jadwal jadwalSekarang = listJadwal.get(i);
            String name = jadwalSekarang.getName();
            Date date = jadwalSekarang.getDate();
            Date timeStart = jadwalSekarang.getTimeStart();
            Date timeEnd = jadwalSekarang.getTimeEnd();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String strDate = dateFormat.format(date);
            String strTimeStart = timeFormat.format(timeStart);
            String strTimeEnd = timeFormat.format(timeEnd);
            jawaban += name + " " + strDate + " " + strTimeStart + " - " + strTimeEnd + "\n";
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
