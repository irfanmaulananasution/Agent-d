package com.bot.agentd;

import java.util.Date;

public class TugasIndividu implements Tugas {
    String name;
    Date deadline;

    TugasIndividu(String name, Date deadline){
        this.name = name;
        this.deadline = deadline;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Date getDeadline(){
        return this.deadline;
    }

    public void setDeadline(Date date){
        this.deadline = date;
    }
}
