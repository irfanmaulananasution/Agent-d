package com.bot.agentd;

public class UserAgentD {
    String id;

    UserAgentD(String userID){
        id = userID;
        this.listJadwal<Jadwal> = new ArrayList<Jadwal>();
    }

}
