package com.bot.agentd;

import java.util.ArrayList;

public class UserAgentD {
    String id;
    ArrayList<TugasIndividu> listTugasIndividu;

    UserAgentD(String userID){
        id = userID;
        this.listTugasIndividu = new ArrayList<>();
    }

    void addTugasIndividu(TugasIndividu task){
        listTugasIndividu.add(task);
    }

    ArrayList<TugasIndividu> getTugasIndividu(){
        return this.listTugasIndividu;
    }

}
