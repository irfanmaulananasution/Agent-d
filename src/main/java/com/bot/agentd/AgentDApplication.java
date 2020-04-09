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

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
@LineMessageHandler
public class AgentDApplication extends SpringBootServletInitializer {

    @Autowired
    private LineMessagingClient lineMessagingClient;

    static HashMap<String, UserAgentD> repo = new HashMap<String, UserAgentD>();

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AgentDApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AgentDApplication.class, args);
    }

    @EventMapping
    public void handleTextEvent(MessageEvent<TextMessageContent> messageEvent){
        String pesan = messageEvent.getMessage().getText().toLowerCase();
        String[] pesanSplit = pesan.split(" ");
        String userId = messageEvent.getSource().getSenderId();
        if(repo.get(userId)==null){
            repo.put(userId,new UserAgentD(userId));
        }
        String jawaban = "";
        switch (pesanSplit[0].toLowerCase()){
            case("tambah"):
                switch(pesanSplit[1].toLowerCase()){
                    case("jadwal"):
                        String namaKegiatan = pesanSplit[2];
                        String[] date = pesanSplit[3].split ("/");
                        int tanggal = Integer.parseInt(date[0]);
                        int bulan = Integer.parseInt(date[1]);
                        int tahun = Integer.parseInt(date[2]);
                        String[] time = pesanSplit[4].split (":");
                        int jam = Integer.parseInt(time[0]);
                        int menit = Integer.parseInt(time[1]);
                        int detik = Integer.parseInt(time[2]);
                        Date waktuKegiatan = new Date (tahun, bulan, tanggal, jam, menit, detik);
                        user.listJadwal.add(new Jadwal (namaKegiatan, waktuKegiatan));
                        jawaban = namaKegiatan + " telah ditambahkan dalam jadwal";
                    default:
                        jawaban = "command tidak diketahui";
                        break;
                }
            case("cek") :
                switch(pesanSplit[1].toLowerCase()){
                    case("jadwal"):
                        for (int i = 0; i < user.listJadwal.size(); i++){
                            jawaban += listJadwal.get(i).getName() + " " + listJadwal.get(i).getDate() + "\n";
                        }
                }
            default:
                jawaban = "Agent-D Dalam Pengembangan!";
        }
        String replyToken = messageEvent.getReplyToken();
        replyText(replyToken, jawaban);
    }

    @EventMapping
    public void handleFollowEvent(FollowEvent event) {
        String replyToken = event.getReplyToken();
        UserAgentD user = new UserAgentD(event.getSource().getSenderId());
        repo.put(event.getSource().getSenderId(),user);
    }


    private void replyText(String replyToken, String jawaban){
        TextMessage jawabanDalamBentukTextMessage = new TextMessage(jawaban);
        try {
            lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, jawabanDalamBentukTextMessage))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Ada error saat ingin membalas chat");
        }
    }

}
