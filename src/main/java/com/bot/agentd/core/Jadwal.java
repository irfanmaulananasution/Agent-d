package com.bot.agentd.core;


import javax.persistence.*;


@Entity
@Table(name = "jadwal")
public class Jadwal{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "day")
    private String day;

    @Column(name = "timeStart")
    private String timeStart;

    @Column(name = "timeEnd")
    private String timeEnd;

    @Column(name = "ownerId")
    private String ownerId;

    public Jadwal() {

    }

    public Jadwal(String name, String day, String timeStart, String timeEnd, String ownerId){
        this.name = name;
        this.day = day;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.ownerId = ownerId;
    }

    public Long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getDay(){
        return this.day;
    }

    public String getTimeStart(){
        return this.timeStart;
    }

    public String getTimeEnd(){
        return this.timeEnd;
    }

    public String getOwnerId(){
        return this.ownerId;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    //    String name;
//    String day;
//    Date timeStart;
//    Date timeEnd;
//
//    public Jadwal(String name, String day, Date timeStart, Date timeEnd){
//        this.name = name;
//        this.day = day;
//        this.timeStart = timeStart;
//        this.timeEnd = timeEnd;
//    }
//
//    public String getName(){
//        return this.name;
//    }
//    public void setName(String name){
//        this.name = name;
//    }
//    public String getDay(){
//        return this.day;
//    }
//    public void setDay(String day){
//        this.day = day;
//    }
//    public Date getTimeStart(){
//        return this.timeStart;
//    }
//    public void setTimeStart(Date timeStart){
//        this.timeStart = timeStart;
//    }
//    public Date getTimeEnd(){
//        return this.timeEnd;
//    }
//    public void setTimeEnd(Date timeEnd){
//        this.timeEnd = timeEnd;
//    }
}
