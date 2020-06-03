package com.bot.agentd.service;

import com.bot.agentd.core.UserAgentD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AgentDServiceImplTests {
//    AgentDServiceImpl service;
//
//    @BeforeEach
//    public void setUp(){
//        service = new AgentDServiceImpl();
//        service.registerUser("123abc","Raihan");
//    }
//
//    @Test
//    public void test_methodIsRegistered1(){
//        assertEquals(true,service.isUserRegistered("123abc"));
//    }
//
//    @Test
//    public void test_methodIsRegistered2(){
//        assertEquals(false,service.isUserRegistered("abc124"));
//    }
//
//    @Test
//    public void test_methodFindUserById(){
//        UserAgentD user = service.findUserById("123abc");
//        assertEquals("123abc", user.getId());
//        assertEquals("Raihan",user.getuName());
//    }
//
//    @Test
//    public void test_methodRegisterUser(){
//        service.registerUser("abc124","Joko");
//        assertEquals(true, service.isUserRegistered("abc124"));
//    }
//
//    @Test
//    public void testMethodPeriksaMessage1(){
//        String[] command = new String[]{"tambah","tugas individu", "adpro", "tugas individu 1", "02-02-2020"};
//        String jawaban = service.periksaMessage("123abc",command);
//        assertEquals("adpro berhasil ditambahkan sebagai tugas individu kamu, Raihan",jawaban);
//    }
//
//    @Test
//    public void testMethodPeriksaMessage2(){
//        String[] command = new String[]{"tambah","tugas individu", "adpro", "tugas individu 1", "02-02-2020"};
//        String[] command2 = new String[]{"lihat","tugas individu"};
//        service.periksaMessage("123abc",command);
//        String jawaban = service.periksaMessage("123abc",command2);
//        assertEquals("id tugas : TI10\nnama tugas : adpro\ndeskripsi : tugas individu 1\ndeadline : 02/02/2020\n\n",jawaban);
//    }
//
//    @Test
//    public void testMethodPeriksaMessage3(){
//        String[] command = new String[]{"halo"};
//        String jawaban = service.periksaMessage("123abc",command);
//        assertEquals("Command Tidak Dikenali",jawaban);
//    }
//
//    @Test
//    public void testMethodPeriksaMessage4(){
//        String[] command = new String[]{"cekid"};
//        String jawaban = service.periksaMessage("123abc",command);
//        assertEquals("123abc",jawaban);
//    }
//
//    @Test
//    public void testMethodPeriksaMessage5(){
//        String[] command = new String[]{"cekid", "blablabla"};
//        String jawaban = service.periksaMessage("123abc",command);
//        assertEquals("Command Tidak Dikenali",jawaban);
//    }
}
