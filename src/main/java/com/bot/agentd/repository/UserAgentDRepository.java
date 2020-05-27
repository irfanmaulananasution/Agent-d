package com.bot.agentd.repository;

import com.bot.agentd.core.UserAgentD;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class UserAgentDRepository implements UserAgentDRepo{
    HashMap<String, UserAgentD> repository;

    public UserAgentDRepository(){
        repository = new HashMap<>();
    }

    public boolean isRegistered(String id){
        return repository.containsKey(id);
    }

    public UserAgentD findUserById(String id){
        return repository.get(id);
    }

    public void registerUser(String id, String uname){
        repository.put(id, new UserAgentD(id, uname));
    }
}
