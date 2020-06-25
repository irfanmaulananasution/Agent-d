package com.bot.agentd.core;

import com.bot.agentd.core.TugasIndividu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TugasIndividuTest {
    private TugasIndividu tugasIndividu;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @BeforeEach
    public void setUp() {
        String tanggal = "01/01/2020";
        tugasIndividu = new TugasIndividu("tugas 1", "desc tugas 1", tanggal, "123abc");
    }

    @Test
    public void testMethodGetName() {
        assertEquals("tugas 1", tugasIndividu.getName());
    }

    @Test
    public void testMethodSetName() {
        tugasIndividu.setName("tugas 2");
        assertEquals("tugas 2", tugasIndividu.getName());
    }

    @Test
    public void testMethodGetDesc() {
        assertEquals("desc tugas 1", tugasIndividu.getDesc());
    }

    @Test
    public void testMethodSetDesc() {
        tugasIndividu.setDesc("desc tugas 2");
        assertEquals("desc tugas 2", tugasIndividu.getDesc());
    }

    @Test
    public void testMethodGetDeadline() {
        String tanggal2 = "01/01/2020";
        assertEquals(tanggal2, tugasIndividu.getDeadline());
    }

    @Test
    public void testMethodSetDeadline() {
            String tanggal2 = "02/01/2020";
            tugasIndividu.setDeadline(tanggal2);
            assertEquals(tanggal2, tugasIndividu.getDeadline());
    }


}
