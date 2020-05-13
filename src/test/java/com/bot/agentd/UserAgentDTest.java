package com.bot.agentd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

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

    @Test
    public void testMethodPeriksaMessage1(){
        String[] command = new String[]{"tambah","tugas individu", "adpro", "tugas individu 1", "02/02/2020"};
        String jawaban = user.periksaMessage(command);
        assertEquals("adpro berhasil ditambahkan sebagai tugas individu",jawaban);
    }

    @Test
    public void testMethodPeriksaMessage2(){
        String[] command = new String[]{"tambah","tugas individu", "adpro", "tugas individu 1", "02/02/2020"};
        String[] command2 = new String[]{"lihat","tugas individu"};
        user.periksaMessage(command);
        String jawaban = user.periksaMessage(command2);
        assertEquals("nama tugas : adpro\ndeskripsi : tugas individu 1\ndeadline : 02/02/2020\n\n",jawaban);
    }

    @Test
    public void testMethodPeriksaMessage3(){
        String[] command = new String[]{"halo"};
        String jawaban = user.periksaMessage(command);
        assertEquals("Command Tidak Dikenali",jawaban);
    }

    @Test
    public void testMethodAddJadwal(){
        try{
            assertEquals(0, user.getTugasIndividu().size());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            Date timeStart = timeFormat.parse("12:00:00");
            Date timeEnd = timeFormat.parse("14:00:00");
            Jadwal jadwal1 = new Jadwal ("Jadwal 1", "Monday", timeStart, timeEnd);
            user.addJadwal(jadwal1);
            assertEquals(1, user.getJadwal().size());
        }catch(ParseException e){
            e.printStackTrace();
        }
    }
    @Test
    public void testMethodGetJadwal() {
        try {
            assertEquals(0, user.getTugasIndividu().size());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            Date timeStart = timeFormat.parse("13:00:00");
            Date timeEnd = timeFormat.parse("14:00:00");
            Jadwal jadwal2 = new Jadwal("Jadwal 2", "Monday", timeStart, timeEnd);
            user.addJadwal(jadwal2);
            assertEquals("Jadwal 2", user.getJadwal().get(0).getName());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
