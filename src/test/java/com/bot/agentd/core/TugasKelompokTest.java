package com.bot.agentd.core;


import com.bot.agentd.core.TugasKelompok;
import com.bot.agentd.core.UserAgentD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TugasKelompokTest {
    private TugasKelompok tugasKelompok;
    UserAgentD user;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @BeforeEach
    public void setUp() {
        try {
            Date tanggal = dateFormat.parse("01/01/2020");
            user = new UserAgentD("1","satu");
            tugasKelompok = new TugasKelompok("tugasKelompok1","desc1",tanggal,user);
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testMethodAddAnggota() {
        assertEquals(1,tugasKelompok.getAnggota().size());
        UserAgentD user2 = new UserAgentD("2","dua");
        tugasKelompok.addAnggota(user2);
        assertEquals(2,tugasKelompok.getAnggota().size());
        tugasKelompok.addAnggota(user2);
        assertEquals(2,tugasKelompok.getAnggota().size());
        UserAgentD user3 = new UserAgentD("3","tiga");
        tugasKelompok.addAnggota(user3);
        assertEquals(3,tugasKelompok.getAnggota().size());
    }

    @Test
    public void testMethodRemoveAnggota() {
        UserAgentD user2 = new UserAgentD("2","dua");
        UserAgentD user3 = new UserAgentD("3","tiga");
        assertEquals(1,tugasKelompok.getAnggota().size());
        tugasKelompok.addAnggota(user2);
        tugasKelompok.addAnggota(user3);
        assertEquals(3,tugasKelompok.getAnggota().size());
        tugasKelompok.removeAnggota(user2);
        assertEquals(2,tugasKelompok.getAnggota().size());
        tugasKelompok.removeAnggota(user2);
        assertEquals(2,tugasKelompok.getAnggota().size());
        tugasKelompok.removeAnggota(user3);
        assertEquals(1,tugasKelompok.getAnggota().size());

    }

    @Test
    public void testMethodIsAnggotaExist() {
        UserAgentD user2 = new UserAgentD("2","dua");
        assertTrue(tugasKelompok.isAnggotaExist(user));
        assertFalse(tugasKelompok.isAnggotaExist(user2));
    }

    @Test
    public void testMethodRemindDeadline() {

    }

    @Test
    public void testMethodGetName() {
        assertEquals("tugasKelompok1",tugasKelompok.getName());
    }
    @Test
    public void testMethodSetName() {
        tugasKelompok.setName("tugasKelompok2");
        assertEquals("tugasKelompok2",tugasKelompok.getName());
    }
    @Test
    public void testMethodGetDesc() {
        assertEquals("desc1",tugasKelompok.getDesc());
    }
    @Test
    public void testMethodSetDesc() {
        tugasKelompok.setDesc("desc2");
        assertEquals("desc2",tugasKelompok.getDesc());
    }
    @Test
    public void testMethodGetDeadline() {
        try {
            Date tgl = dateFormat.parse("01/01/2020");
            assertEquals(tgl, tugasKelompok.getDeadline());
        }catch (ParseException e){
            e.printStackTrace();
        }
    }
    @Test
    public void testMethodSetDeadline() {
        try {
            Date tgl = dateFormat.parse("02/01/2020");
            tugasKelompok.setDeadline(tgl);
            assertEquals(tgl, tugasKelompok.getDeadline());
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testMethodGetAnggota(){
        UserAgentD user2 = new UserAgentD("2","dua");
        assertEquals(1,tugasKelompok.getAnggota().size());
        tugasKelompok.addAnggota(user2);
        assertEquals(2,tugasKelompok.getAnggota().size());
    }

}

