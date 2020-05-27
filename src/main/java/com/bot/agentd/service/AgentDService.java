package com.bot.agentd.service;

import com.bot.agentd.core.UserAgentD;

public interface AgentDService {
    UserAgentD findUserById(String id);
    boolean isUserRegistered(String id);
    void registerUser(String id, String uname);
    String periksaMessage(String id,String[] pesanSplit);
}
