package com.bot.agentd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAgentDTest {
    UserAgentD user;

    @BeforeEach
    public void setUp(){
        user = new UserAgentD("123abc");
    }

    @Test
    public void testMethodAddTugasIndividu(){
        try {
            assertEquals(0, user.getTugasIndividu().size());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date tanggal = simpleDateFormat.parse("01/01/2020");
            TugasIndividu task = new TugasIndividu("tugas 1", "desc tugas 1", tanggal);
            user.addTugasIndividu(task);
            assertEquals(1,user.getTugasIndividu().size());
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testMethodGetTugasIndividu(){
        try {
            assertEquals(0, user.getTugasIndividu().size());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date tanggal = simpleDateFormat.parse("01/01/2020");
            TugasIndividu task = new TugasIndividu("tugas 1", "desc tugas 1", tanggal);
            TugasIndividu task2 = new TugasIndividu("tugas 2", "desc tugas 2", tanggal);
            user.addTugasIndividu(task);
            user.addTugasIndividu(task2);
            assertEquals("tugas 1",user.getTugasIndividu().get(0).getName());
            assertEquals("tugas 2",user.getTugasIndividu().get(1).getName());
        }catch (ParseException e){
            e.printStackTrace();
        }
    }
}
