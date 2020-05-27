package com.bot.agentd.core;

import java.util.Date;

public class TugasIndividu implements Tugas {
    String name;
    String desc;
    Date deadline;

    TugasIndividu(String name,String desc, Date deadline){
        this.name = name;
        this.desc = desc;
        this.deadline = deadline;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDesc(){ return this.desc;}

    public void setDesc(String desc){ this.desc = desc;}

    public Date getDeadline(){
        return this.deadline;
    }

    public void setDeadline(Date date){
        this.deadline = date;
    }
}
