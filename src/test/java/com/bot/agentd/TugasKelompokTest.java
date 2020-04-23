package com.bot.agentd;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TugasKelompokTest {
    private TugasKelompok tugasKelompok;
    UserAgentD owner;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @BeforeEach
    public void setUp() {
        try {
            Date tanggal = dateFormat.parse("01/01/2020");
            owner = new UserAgentD("1");
            tugasKelompok = new TugasKelompok("tugasKelompok1","desc1",tanggal,owner);
        }catch (ParseException e){
            e.printStackTrace();
        }
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
    public void testMethodGetDate() {
        try {
            Date tgl = dateFormat.parse("01/01/2020");
            assertEquals(tgl, tugasKelompok.getDeadline());
        }catch (ParseException e){
            e.printStackTrace();
        }
    }
    @Test
    public void testMethodSetDate() {
        try {
            Date tgl = dateFormat.parse("02/01/2020");
            tugasKelompok.setDeadline(tgl);
            assertEquals(tgl, tugasKelompok.getDeadline());
        }catch (ParseException e){
            e.printStackTrace();
        }
    }
    @Test
    public void testMethodGetOwner() {
        assertEquals(owner,tugasKelompok.getOwner());
    }
    @Test
    public void testMethodSetOwner() {
        UserAgentD owner2 = new UserAgentD("2");
        assertEquals(owner2,tugasKelompok.getOwner());
    }

}
