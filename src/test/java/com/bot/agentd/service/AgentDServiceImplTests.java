package com.bot.agentd.service;

import com.bot.agentd.controller.AgentDController;
import com.bot.agentd.core.Jadwal;
import com.bot.agentd.core.TugasIndividu;
import com.bot.agentd.core.TugasKelompok;
import com.bot.agentd.core.UserAgentD;
import com.bot.agentd.repository.JadwalRepository;
import com.bot.agentd.repository.TugasIndividuRepository;
import com.bot.agentd.repository.TugasKelompokRepository;
import com.bot.agentd.repository.UserAgentDRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class AgentDServiceImplTests {

    @Mock
    private JadwalRepository jadwalRepo;

    @Mock
    private UserAgentDRepository userRepo;

    @Mock
    private TugasIndividuRepository tugasIndividuRepo;

    @Mock
    private TugasKelompokRepository tugasKelompokRepo;


    private AgentDController controller;
    private UserAgentD user;

    @InjectMocks
    private AgentDServiceImpl service;

    @BeforeEach
    public void setUp(){
        controller = new AgentDController();
        user = new UserAgentD("123abc", "satu");
    }

    @Test
    public void test_methodIsRegistered1(){
        service.isUserRegistered(user.getId());
        verify(userRepo, times(1)).existsById(user.getId());
    }

    @Test
    public void test_methodIsRegistered2(){
        assertEquals(false,service.isUserRegistered("abc124"));
    }


    @Test
    public void test_methodRegisterUser(){
        service.registerUser(user);
        verify(userRepo, times(1)).save(user);
    }

    @Test
    public void testMethodHelp(){
        String jawaban = "Halo, selamat datang di Agent-D! Untuk memanfaatkan Agent-D, gunakan command-command berikut :\n\n";
        jawaban += "untuk mengetahui command tugas individu, kirim help/tugas individu\n\n";
        jawaban += "untuk mengetahui command tugas kelompok, kirim help/tugas kelompok\n\n";
        jawaban += "untuk mengetahui command jadwal mingguan, kirim help/jadwal\n\n";
        jawaban += "untuk mengetahui command lain, kirim help/others\n";
        jawaban+="\nSemoga bermanfaat!";
        assertEquals(jawaban, service.help());
    }

    @Test
    public void testMethodHelpTI(){
        String jawaban = "Berikut adalah command yang berhubungan dengan tugas individu: \n\n";
        jawaban+="tambah/tugas individu/<nama tugas>/<deskripsi tugas>/<deadline tugas> => menambahkan tugas individu\n\n";
        jawaban+="lihat/tugas individu => melihat daftar tugas individu yang terdaftar oleh kamu\n\n";
        jawaban+="remove/tugas individu/<id tugas> => menghapus tugas individu sesuai id yang terdaftar\n";
        jawaban+="\nSemoga bermanfaat!";
        assertEquals(jawaban, service.helpTI());
    }

    @Test
    public void testMethodHelpTK(){
        String jawaban = "Berikut adalah command yang berhubungan dengan tugas kelompok : \n\n";
        jawaban+="tambah/tugas kelompok/<nama tugas>/<deskripsi tugas>/<deadline tugas> => menambahkan tugas kelompok\n\n";
        jawaban+="lihat/tugas kelompok => melihat daftar tugas kelompok dimana kamu adalah anggota\n\n";
        jawaban+="remove/tugas kelompok/<id tugas> => menghapus tugas kelompok sesuai id yang terdaftar\n\n";
        jawaban+="join tk/<id tugas> => mendaftarkan diri menjadi anggota tugas kelompok dengan id tertentu\n\n";
        jawaban+="remind tk/<id tugas> => mengingatkan semua anggota kelompok dalam tugas tersebut agar mengerjakan tugas\n";
        jawaban+="\nSemoga bermanfaat!";
        assertEquals(jawaban, service.helpTK());
    }

    @Test
    public void testMethodHelpJadwal(){
        String jawaban = "Berikut adalah command yang berhubungan dengan jadwal mingguan : \n\n";
        jawaban+="tambah/jadwal/<nama jadwal>/<hari jadwal>/<waktu mulai>/<waktu selesai> => menambahkan jadwal mingguan\n\n";
        jawaban+="lihat/jadwal => melihat daftar jadwal mingguan yang terdaftar oleh kamu\n\n";
        jawaban+="remove/jadwal/<id jadwal> => menghapus jadwal mingguan sesuai id yang terdaftar\n\n";
        jawaban+="lihat/jadwal/<hari> => melihat jadwal sesuai hari yang diinput\n";
        jawaban+="\nSemoga bermanfaat!";
        assertEquals(jawaban, service.helpJadwal());
    }

    @Test
    public void testMethodHelpOthers(){
        String jawaban = "Berikut adalah command yang dapat dijalankan di Agent-D : \n\n";
        jawaban+="cekid => memeriksa id kamu yang terdaftar saat menjadi user Agent-D\n/n";
        jawaban+="quote => mendapatkan quotes pilihan untuk menyemangati harimu\n\n";
        jawaban+="help => mendapatkan bantuan penggunaan Agent-D\n";
        jawaban+="\nSemoga bermanfaat!";
        assertEquals(jawaban, service.helpOther());
    }

    @Test
    public void testMethodTambahTugasIndividu(){
        TugasIndividu ti = new TugasIndividu("adpro", "tugas individu 1", "02-02-2020", user.getId());
        service.tambahTugasIndividu(user,ti);
        verify(tugasIndividuRepo,times(1)).save(ti);
    }

    @Test
    public void testMethodTambahTugasKelompok(){
        TugasKelompok ti = new TugasKelompok("adpro", "tugas individu 1", "02-02-2020", user);
        service.tambahTugasKelompok(user,ti);
        verify(tugasKelompokRepo,times(1)).save(ti);
    }

    @Test
    public void testMethodTambahJadwal(){
        Jadwal jadual = new Jadwal("Kelas", "Monday", "15:00", "17:00", user.getId());
        service.tambahJadwal(user,jadual);
        verify(jadwalRepo,times(1)).save(jadual);

    }

    @Test
    public void testMethodLihatTugasIndividu(){
        service.lihatTugasIndividu(user);
        verify(tugasIndividuRepo,times(1)).fetchTugasIndividu(user.getId());
    }

    @Test
    public void testMethodLihatTugasKelompok(){
        service.lihatTugasKelompok(user);
        verify(tugasKelompokRepo,times(1)).fetchTugasKelompok(user.getId());
    }

    @Test
    public void testMethodLihatJadwal(){
        service.lihatJadwal(user);
        verify(jadwalRepo,times(1)).fetchJadwal(user.getId());
    }

    @Test
    public void testMethodRemoveTugasIndividu(){
        service.removeTugasIndividu(Long.parseLong("57"));
        verify(tugasIndividuRepo,times(1)).deleteTugasIndividu(Long.parseLong("57"));
    }

    @Test
    public void testMethodRemoveTugasKelompok(){
        service.removeTugasKelompok(Long.parseLong("57"));
        verify(tugasKelompokRepo,times(1)).deleteTugasKelompok(Long.parseLong("57"));
    }

    @Test
    public void testMethodRemoveJadwal(){
        service.removeJadwal(Long.parseLong("57"));
        verify(jadwalRepo,times(1)).deleteJadwal(Long.parseLong("57"));
    }

//    @Test
//    public void testMethodGetDaftarAnggotaUsername(){
//        service.getDaftarAnggotaUserName("123abc ");
//        verify(userRepo,times(1)).findLineUserByUserId("123abc");
//    }
//
//    @Test
//    public void testMethodRemindTugasKelompok(){
//        service.remindTugasKelompok(57,user,controller);
//        verify(tugasKelompokRepo,times(1)).findById(Long.parseLong("57"));
//    }
}
