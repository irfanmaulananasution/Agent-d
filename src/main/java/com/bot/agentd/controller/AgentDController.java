package com.bot.agentd.controller;

import com.bot.agentd.core.UserAgentD;
import com.bot.agentd.service.AgentDService;
import com.bot.agentd.service.AgentDServiceImpl;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

@RestController
@LineMessageHandler
public class AgentDController {

    @Autowired
    private LineMessagingClient lineMessagingClient;

    @Autowired
    private AgentDService agentDService;

    @EventMapping
    public void handleTextEvent(MessageEvent<TextMessageContent> messageEvent) {
        try {
            String userId = messageEvent.getSource().getUserId();
            String userName = lineMessagingClient.getProfile(userId).get().getDisplayName();
            String pesan = messageEvent.getMessage().getText();
            String[] pesanSplit = pesan.split("/");
            if(!agentDService.isUserRegistered(userId)) {
                agentDService.registerUser(new UserAgentD(userId, userName));
            }
            String jawaban = agentDService.periksaMessage(userId,pesanSplit, this);

            String replyToken = messageEvent.getReplyToken();
            replyText(replyToken, jawaban);
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
            AgentDServiceImpl.log.log(Level.INFO, "Error while sending message");
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Handle push message for reminder notification.
     *
     * @param userID  User identifier.
     * @param message Message that will be send to User.
     */
    public void handlePushEvent(String userID, String message) {
        TextMessage jawabanDalamBentukTextMessage = new TextMessage(message);
        try {
            lineMessagingClient
                    .pushMessage(new PushMessage(userID, jawabanDalamBentukTextMessage))
                    .get();
        } catch (NullPointerException | InterruptedException | ExecutionException e) {
            System.out.print("Error found, please try again\n");
        }
    }

    /**
     * Handle push message for reminder notification with Template Message.
     *
     * @param userID  User identifier.
     * @param message Message that will be send to User.
     */
    public void handlePushEventTemplateMessage(String userID, TemplateMessage message) {
        try {
            lineMessagingClient
                    .pushMessage(new PushMessage(userID, message))
                    .get();
        } catch (NullPointerException | InterruptedException | ExecutionException e) {
            System.out.print("Error found, please try again\n");
        }
    }

}
