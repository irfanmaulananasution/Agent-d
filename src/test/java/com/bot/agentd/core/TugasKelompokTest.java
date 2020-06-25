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
        String tanggal = "01/01/2020";
        user = new UserAgentD("123abc","satu");
        tugasKelompok = new TugasKelompok("tugasKelompok1","desc1",tanggal,user);
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
        String tgl = "01/01/2020";
        assertEquals(tgl, tugasKelompok.getDeadline());
    }

    @Test
    public void testMethodSetDeadline() {
        String tgl = "02/01/2020";
        tugasKelompok.setDeadline(tgl);
        assertEquals(tgl, tugasKelompok.getDeadline());
    }

    @Test
    public void testMethodGetOwnerId(){
        assertEquals("123abc", tugasKelompok.getOwnerId());;
    }

    @Test
    public void testMethodGetDaftarAnggota(){
        assertEquals("123abc ", tugasKelompok.getDaftarAnggota());
    }

    @Test
    public void testMethodSetDaftarAnggota(){
        UserAgentD user2 = new UserAgentD("456bcd", "dua");
        tugasKelompok.setDaftarAnggota(user2.getId());
        assertEquals(2,tugasKelompok.getDaftarAnggota().split(" ").length);
    }

    @Test
    public void testMethodRemoveDaftarAnggota(){
        UserAgentD user2 = new UserAgentD("456bcd", "dua");
        tugasKelompok.setDaftarAnggota(user2.getId());
        tugasKelompok.removeAnggota(user2);
        assertEquals(1,tugasKelompok.getDaftarAnggota().split(" ").length);
    }

}

