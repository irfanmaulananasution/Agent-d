package com.bot.agentd.service;

import com.bot.agentd.controller.AgentDController;
import com.bot.agentd.core.UserAgentD;

public interface AgentDService {
    boolean isUserRegistered(String id);
    void registerUser(UserAgentD newUser);
    String periksaMessage(String id, String[] pesanSplit, AgentDController controller);
}
