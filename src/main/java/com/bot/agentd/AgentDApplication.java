package com.bot.agentd;

import com.bot.agentd.core.UserAgentD;
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
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;



@SpringBootApplication
@LineMessageHandler
public class AgentDApplication extends SpringBootServletInitializer {

    @Autowired
    private LineMessagingClient lineMessagingClient;

    static HashMap<String, UserAgentD> repo = new HashMap<>();
    String tidakDikenal = "Command tidak dikenal";


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AgentDApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AgentDApplication.class, args);
    }

    @EventMapping
    public void handleTextEvent(MessageEvent<TextMessageContent> messageEvent) {
        try {
            String userId = messageEvent.getSource().getUserId();
            String userName = lineMessagingClient.getProfile(userId).get().getDisplayName();
            String pesan = messageEvent.getMessage().getText();
            String[] pesanSplit = pesan.split("/");
            if (repo.get(userId) == null) {
                repo.put(userId, new UserAgentD(userId, userName));
            }
            UserAgentD user = repo.get(userId);
            String jawaban = user.periksaMessage(pesanSplit);

            String replyToken = messageEvent.getReplyToken();
            replyText(replyToken, jawaban);
        }catch (ExecutionException | InterruptedException e){

        }
    }

    @EventMapping
    public void handleFollowEvent(FollowEvent event) {
        try {
            String userId = event.getSource().getUserId();
            String userName = lineMessagingClient.getProfile(userId).get().getDisplayName();
            UserAgentD user = new UserAgentD(userId, userName);
            repo.put(event.getSource().getSenderId(), user);
        }catch (ExecutionException | InterruptedException e){

        }
    }

    private void replyText(String replyToken, String jawaban){
        TextMessage jawabanDalamBentukTextMessage = new TextMessage(jawaban);
        try {
            lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, jawabanDalamBentukTextMessage))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            UserAgentD.log.log(Level.INFO, "Error while sending message");
            Thread.currentThread().interrupt();
        }
    }
}
