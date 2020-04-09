package com.bot.agentd;

import java.util.ArrayList;

public class UserAgentD {
    String id;
    ArrayList<Jadwal> listJadwal;

    UserAgentD(String userID){
        id = userID;
        this.listJadwal<Jadwal> = new ArrayList<Jadwal>();
    }
}
