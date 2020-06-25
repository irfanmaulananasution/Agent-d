package com.bot.agentd.core;

import com.bot.agentd.core.Jadwal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JadwalTests {
    private Jadwal jadwal;
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    @BeforeEach
    public void setUp() {
        String startTime = "13:00";
        String endTime = "15:00";
        String ownerId = "123abc";
        jadwal = new Jadwal("Jadwal Baru", "Monday", startTime, endTime, ownerId);
    }

    @Test
    public void testMethodGetName() {
        assertEquals("Jadwal Baru", jadwal.getName());
    }

    @Test
    public void testMethodSetName() {
        jadwal.setName("Jadwal Baru 2");
        assertEquals("Jadwal Baru 2", jadwal.getName());
    }

    @Test
    public void testMethodGetDate(){
        String day = jadwal.getDay();
        assertEquals("Monday", day);
    }

    @Test
    public void testMethodGetTimeStart() {
        String timeStart = jadwal.getTimeStart();
        assertEquals("13:00", timeStart);
    }

    @Test
    public void testMethodGetTimeEnd(){
        String timeEnd = jadwal.getTimeEnd();
        assertEquals("15:00", timeEnd);
    }
}
