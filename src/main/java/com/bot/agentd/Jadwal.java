package com.bot.agentd;

import java.util.Date;

public class Jadwal{
    String name;
    Date date;
    Date timeStart;
    Date timeEnd;

    Jadwal(String name, Date date, Date timeStart, Date timeEnd){
        this.name = name;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Date getDate(){
        return this.date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Date getTimeStart(){
        return this.timeStart;
    }
    public void setTimeStart(Date timeStart){
        this.timeStart = timeStart;
    }
    public Date getTimeEnd(){
        return this.timeEnd;
    }
    public void setTimeEnd(Date timeEnd){
        this.timeEnd = timeEnd;
    }
}
