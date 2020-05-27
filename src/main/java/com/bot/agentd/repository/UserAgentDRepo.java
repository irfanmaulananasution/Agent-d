package com.bot.agentd.repository;

import com.bot.agentd.core.UserAgentD;

public interface UserAgentDRepo {
    boolean isRegistered(String id);
    UserAgentD findUserById(String id);
    void registerUser(String id, String uname);
}
