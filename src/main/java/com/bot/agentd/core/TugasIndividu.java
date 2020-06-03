package com.bot.agentd.core;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    //test
}

//public class TugasIndividu implements Tugas {
//    String id;
//    String name;
//    String desc;
//    Date deadline;
//    static int counter = 0;
//
//    TugasIndividu(String name,String desc, Date deadline){
//        this.id = this.generateId();
//        this.name = name;
//        this.desc = desc;
//        this.deadline = deadline;
//    }
//
//    private String generateId(){
//        int count = counter++;
//        return "TI" + count;
//    }
//
//    public String getId(){
//        return this.id;
//    }
//    public String getName(){
//        return this.name;
//    }
//
//    public void setName(String name){
//        this.name = name;
//    }
//
//    public String getDesc(){ return this.desc;}
//
//    public void setDesc(String desc){ this.desc = desc;}
//
//    public Date getDeadline(){
//        return this.deadline;
//    }
//
//    public void setDeadline(Date date){
//        this.deadline = date;
//    }
//}
