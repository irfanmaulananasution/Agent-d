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

import java.util.Date;
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
        String pesan = messageEvent.getMessage().getText();
        String[] pesanSplit = pesan.split("-");
        String userId = messageEvent.getSource().getSenderId();
        if(repo.get(userId)==null){
            repo.put(userId,new UserAgentD(userId));
        }
        UserAgentD user = repo.get(userId);
        String jawaban = "";
        switch (pesanSplit[0].toLowerCase()){
            case("tambah"):
                switch (pesanSplit[1].toLowerCase()){
                    case("tugasindividu"):
                        //pesan : Tambah-TugasIndividu-tugas kelompok adpro-9/4/2020
                        String namaTugas = pesanSplit[2];
                        String[] date = pesanSplit[3].split("/");
                        int tanggal = Integer.parseInt(date[0]);
                        int bulan = Integer.parseInt(date[1]);
                        int tahun = Integer.parseInt(date[2]);
                        Date deadline = new Date(tahun,bulan,tanggal);
                        user.listTugasIndividu.add(new TugasIndividu(namaTugas,deadline));
                        jawaban = namaTugas+" telah ditambahkan dalam list tugas individu";
                        break;
                    default:
                        jawaban = "maaf command tidak diketahui";
                }
                break;
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
