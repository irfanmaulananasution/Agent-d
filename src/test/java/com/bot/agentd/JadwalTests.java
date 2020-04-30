package com.bot.agentd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JadwalTests {
    private Jadwal jadwal;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    @BeforeEach
    public void setUp() {
        try {
            Date date = dateFormat.parse("01/01/2020");
            Date startTime = timeFormat.parse("13:00:00");
            Date endTime = timeFormat.parse("15:00:00");
            jadwal = new Jadwal("Jadwal Baru", date, startTime, endTime);
        }catch (ParseException e){
            e.printStackTrace();
        }
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
        Date date = jadwal.getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(date);
        assertEquals("01/01/2020", strDate);
    }

    @Test
    public void testMethodGetTimeStart(){
        Date timeStart = jadwal.getTimeStart();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String strTime = timeFormat.format(timeStart);
        assertEquals("13:00:00", strTime);
    }



    @Test
    public void testMethodGetTimeEnd(){
        Date timeEnd = jadwal.getTimeEnd();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String strTime = timeFormat.format(timeEnd);
        assertEquals("15:00:00", strTime);
    }

//    @Test
//    public void testSetDate(){
//    }

//    @Test
//    public void testMethodSetDate(){
//    }

//    @Test
//    public void testMethodSetTimeEnd(){
//    }

//    @Test
//    public void testMethodRemove(){
//    }
}
