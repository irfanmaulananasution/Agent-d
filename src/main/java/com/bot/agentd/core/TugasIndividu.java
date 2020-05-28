package com.bot.agentd.core;

import java.util.Date;

public class TugasIndividu implements Tugas {
    String id;
    String name;
    String desc;
    Date deadline;
    static int counter = 0;

    TugasIndividu(String name,String desc, Date deadline){
        this.id = this.generateId();
        this.name = name;
        this.desc = desc;
        this.deadline = deadline;
    }

    private String generateId(){
        int count = counter++;
        return "TI" + count;
    }

    public String getId(){
        return this.id;
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
