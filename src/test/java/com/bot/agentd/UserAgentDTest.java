package com.bot.agentd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAgentDTest {
    UserAgentD user;

//    buat test method baru yang dipindahin
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
    public void testMethodTambahTugasIndividu1(){
        String jawaban = user.tambahTugasIndividu("adpro","tugas individu 1","02/02/2020");
        assertEquals("adpro berhasil ditambahkan sebagai tugas individu",jawaban);
    }

    @Test
    public void testMethodTambahTugasIndividu2(){
        String jawaban = user.tambahTugasIndividu("adpro","tugas individu 1","ab/02/2020");
        assertEquals("Tanggal tidak dikenal",jawaban);
    }

    @Test
    public void testMethodLihatTugasIndividu(){
        user.tambahTugasIndividu("adpro","tugas individu 1","02/02/2020");
        String jawaban = user.lihatTugasIndividu();
        assertEquals("nama tugas : adpro\ndeskripsi : tugas individu 1\ndeadline : 02/02/2020\n\n",jawaban);
    }

    @Test
    public void testMethodAddTugasKelompok(){
        try {
            assertEquals(0, user.getTugasIndividu().size());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date tanggal = simpleDateFormat.parse("01/01/2020");
            TugasKelompok task = new TugasKelompok("tugas 1", "desc tugas 1", tanggal,user);
            user.addTugasKelompok(task);
            assertEquals(1,user.getTugasKelompok().size());
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testMethodGetTugasKelompok(){
        try {
            assertEquals(0, user.getTugasKelompok().size());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date tanggal = simpleDateFormat.parse("01/01/2020");
            TugasKelompok task = new TugasKelompok("tugas 1", "desc tugas 1", tanggal, user);
            TugasKelompok task2 = new TugasKelompok("tugas 2", "desc tugas 2", tanggal, user);
            user.addTugasKelompok(task);
            user.addTugasKelompok(task2);
            assertEquals("tugas 1",user.getTugasKelompok().get(0).getName());
            assertEquals("tugas 2",user.getTugasKelompok().get(1).getName());
        }catch (ParseException e){
            e.printStackTrace();
        }
    }
}
