package com.bot.agentd.service;

import com.bot.agentd.core.UserAgentD;

public interface AgentDService {
    boolean isUserRegistered(String id);
    void registerUser(String id, String uname);
    String periksaMessage(String id,String[] pesanSplit);
}
