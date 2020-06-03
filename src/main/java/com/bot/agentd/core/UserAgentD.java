package com.bot.agentd.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "UserAgentD")
public class UserAgentD{
    @Id
    @Column(name = "userId", nullable = false, unique = true)
    private String userId;

    @Column(name = "userName")
    private String userName;

    public UserAgentD(){

    }

    public UserAgentD(String id, String nama){
        this.userId = id;
        this.userName = nama;
    }

    public String getId(){
        return this.userId;
    }

    public void changeUserName(String nama){
        this.userName = nama;
    }

    public String getUserName(){
        return this.userName;
    }


}