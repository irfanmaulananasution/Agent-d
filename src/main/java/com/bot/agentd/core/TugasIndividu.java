package com.bot.agentd.core;

import javax.persistence.*;

@Entity
@Table(name = "tugas_individu")
public class TugasIndividu{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "deadline")
    private String deadline;

    @Column(name = "ownerId")
    private String ownerId;

    public TugasIndividu() {

    }

    public TugasIndividu(String nama, String desc, String deadline, String ownerId){
        this.name = nama;
        this.description = desc;
        this.deadline = deadline;
        this.ownerId = ownerId;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDesc(String desc){
        this.description = desc;
    }

    public void setDeadline(String date){
        this.deadline = date;
    }

    public long getId(){
        return this.id;
    }

    public String getOwnerId(){
        return this.ownerId;
    }

    public String getName(){
        return this.name;
    }

    public String getDesc(){
        return this.description;
    }

    public String getDeadline(){
        return this.deadline;
    }
}