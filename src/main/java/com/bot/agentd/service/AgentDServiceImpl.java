package com.bot.agentd.service;

import com.bot.agentd.controller.AgentDController;
import com.bot.agentd.core.*;
import com.bot.agentd.repository.JadwalRepository;
import com.bot.agentd.repository.TugasIndividuRepository;
import com.bot.agentd.repository.TugasKelompokRepository;
import com.bot.agentd.repository.UserAgentDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


@Service
public class AgentDServiceImpl implements AgentDService {
    @Autowired
    private UserAgentDRepository userRepo;

    @Autowired
    private TugasIndividuRepository tugasIndividuRepo;

    @Autowired
    private TugasKelompokRepository tugasKelompokRepo;

    @Autowired
    private JadwalRepository jadwalRepo;

    static String tidakDikenal = "Maaf, command tidak dikenali.\nKetik 'help' untuk mengetahui command yang tersedia";

    static Quotes qRepo = new Quotes();

    static LogManager lgmngr = LogManager.getLogManager();
    public static Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public boolean isUserRegistered(String id){
        return userRepo.existsById(id);
    }

    @Override
    public void registerUser(String id, String uname) {
        UserAgentD newUser = new UserAgentD(id, uname);
        userRepo.save(newUser);
    }

    public String periksaMessage(String id, String[] pesanSplit, AgentDController controller) {
        UserAgentD user = userRepo.findLineUserByUserId(id);
        String jawaban = "";
        try {
            switch (pesanSplit[0].toLowerCase()) {
                case ("tambah"):
                    switch (pesanSplit[1].toLowerCase()) {
                        case ("tugas individu"):
                            jawaban = this.tambahTugasIndividu(user, pesanSplit[2], pesanSplit[3], pesanSplit[4]);
                            break;
                        case ("tugas kelompok"):
                            jawaban = this.tambahTugasKelompok(user, pesanSplit[2], pesanSplit[3], pesanSplit[4]);
                            break;
                        case ("jadwal"):
                            String name = pesanSplit[2];
                            String day = pesanSplit[3];
                            String startTime = pesanSplit[4];
                            String endTime = pesanSplit[5];
                            jawaban = this.tambahJadwal(user, name, day, startTime, endTime);
                            break;
                        default:
                            jawaban = tidakDikenal;
                    }
                    break;
                case ("lihat"):
                    switch (pesanSplit[1].toLowerCase()) {
                        case ("tugas individu"):
                            jawaban = this.lihatTugasIndividu(user);
                            break;
                        case ("tugas kelompok"):
                            jawaban = this.lihatTugasKelompok(user);
                            break;
                        case ("jadwal"):
                            jawaban = this.lihatJadwal(user);
                            break;
                        default:
                            jawaban = tidakDikenal;
                    }
                    break;
                case ("remove"):
                    switch (pesanSplit[1].toLowerCase()) {
                        case ("tugas individu"):
                            jawaban = this.removeTugasIndividu(Long.parseLong(pesanSplit[2]));
                            break;
                        case ("tugas kelompok"):
                            jawaban = this.removeTugasKelompok(Long.parseLong(pesanSplit[2]));
                            break;
                        case("jadwal"):
                            jawaban = this.removeJadwal(Long.parseLong(pesanSplit[2]));
                            break;
                        default:
                            jawaban = tidakDikenal;
                    }
                    break;
                case ("cekid"):
                    jawaban = "Hai " + user.getUserName() + ", id Agent-D kamu adalah " + user.getId();
                    break;
                case ("quote"):
                    if (!qRepo.isInitiated()) {
                        qRepo.repoInitiation();
                    }
                    jawaban = qRepo.getQuote();
                    break;
                case ("join tk"):
                    jawaban = this.joinTugasKelompok(Long.parseLong(pesanSplit[1]), user);
                    break;
                case("remind tk"):
                    jawaban = this.remindTugasKelompok(Long.parseLong(pesanSplit[1]), user, controller);
                    break;
                case("help"):
                    if(pesanSplit.length==1) {
                        jawaban = this.help();
                    }else{
                        switch (pesanSplit[1]){
                            case ("tugas individu"):
                                jawaban = this.helpTI();
                                break;
                            case("tugas kelompok"):
                                jawaban = this.helpTK();
                                break;
                            case("jadwal"):
                                jawaban = this.helpJadwal();
                                break;
                            case("others"):
                                jawaban = this.helpOther();
                                break;
                            default:
                                jawaban = tidakDikenal;
                        }
                    }
                    break;
                default:
                    jawaban = tidakDikenal;
            }
            return jawaban;
        }catch (Exception e){
            log.log(Level.INFO, "Error Happens in quote initiating");
            return "Error, Something happened";
        }
    }

    public String help(){
        String jawaban = "Halo, selamat datang di Agent-D! Untuk memanfaatkan Agent-D, gunakan command-command berikut :\n\n";
        jawaban += "untuk mengetahui command tugas individu, kirim help/tugas individu\n";
        jawaban += "untuk mengetahui command tugas kelompok, kirim help/tugas kelompok\n";
        jawaban += "untuk mengetahui command jadwal mingguan, kirim help/jadwal\n";
        jawaban += "untuk mengetahui command lain, kirim help/others\n";
        jawaban+="\nSemoga bermanfaat!";
        return jawaban;
    }

    public String helpTI(){
        String jawaban = "Berikut adalah command yang berhubungan dengan tugas individu: \n\n";
        jawaban+="tambah/tugas individu/<nama tugas>/<deskripsi tugas>/<deadline tugas> => menambahkan tugas individu\n";
        jawaban+="lihat/tugas individu => melihat daftar tugas individu yang terdaftar oleh kamu\n";
        jawaban+="remove/tugas individu/<id tugas> => menghapus tugas individu sesuai id yang terdaftar\n";
        jawaban+="\nSemoga bermanfaat!";
        return jawaban;
    }

    public String helpTK(){
        String jawaban = "Berikut adalah command yang berhubungan dengan tugas kelompok : \n\n";
        jawaban+="tambah/tugas kelompok/<nama tugas>/<deskripsi tugas>/<deadline tugas> => menambahkan tugas kelompok\n";
        jawaban+="lihat/tugas kelompok => melihat daftar tugas kelompok dimana kamu adalah anggota\n";
        jawaban+="remove/tugas kelompok/<id tugas> => menghapus tugas kelompok sesuai id yang terdaftar\n";
        jawaban+="join tk/<id tugas> => mendaftarkan diri menjadi anggota tugas kelompok dengan id tertentu\n";
        jawaban+="remind tk/<id tugas> => mengingatkan semua anggota kelompok dalam tugas tersebut agar mengerjakan tugas\n";
        jawaban+="\nSemoga bermanfaat!";
        return jawaban;
    }

    public String helpJadwal(){
        String jawaban = "Berikut adalah command yang berhubungan dengan jadwal mingguan : \n\n";
        jawaban+="tambah/jadwal/<nama jadwal>/<hari jadwal>/<waktu mulai>/<waktu selesai> => menambahkan jadwal mingguan\n";
        jawaban+="lihat/jadwal => melihat daftar jadwal mingguan yang terdaftar oleh kamu\n";
        jawaban+="remove/jadwal/<id jadwal> => menghapus jadwal mingguan sesuai id yang terdaftar\n";
        jawaban+="\nSemoga bermanfaat!";
        return jawaban;
    }

    public String helpOther(){
        String jawaban = "Berikut adalah command yang dapat dijalankan di Agent-D : \n\n";
        jawaban+="cekid => memeriksa id kamu yang terdaftar saat menjadi user Agent-D\n";
        jawaban+="quote => mendapatkan quotes pilihan untuk menyemangati harimu\n";
        jawaban+="help => mendapatkan bantuan penggunaan Agent-D\n";
        jawaban+="\nSemoga bermanfaat!";
        return jawaban;
    }

    public String tambahTugasIndividu (UserAgentD user, String nama, String desc, String deadline){
        TugasIndividu task = new TugasIndividu(nama, desc, deadline, user.getId());
        tugasIndividuRepo.save(task);
        return task.getName()+" telah ditambahkan ke dalam daftar tugas individu kamu, "+user.getUserName();
    }

    public String lihatTugasIndividu(UserAgentD user){
        List<TugasIndividu> listTugas = tugasIndividuRepo.fetchTugasIndividu(user.getId());
        String jawaban = "";
        for(TugasIndividu task :listTugas) {
            jawaban += "id tugas : " + task.getId() + "\n";
            jawaban += "nama tugas : " + task.getName() + "\n";
            jawaban += "deskripsi : " + task.getDesc() + "\n";
            jawaban += "deadline : " + task.getDeadline() + "\n\n";
        }
        return jawaban;
    }

    public String removeTugasIndividu(long id){
        tugasIndividuRepo.deleteTugasIndividu(id);
        return "Tugas Individu dengan ID : "+id+" telah dihapus";
    }

    public String tambahTugasKelompok(UserAgentD user, String nama, String desc, String deadline){
        TugasKelompok task = new TugasKelompok(nama, desc, deadline, user);
        tugasKelompokRepo.save(task);
        return task.getName()+" telah ditambahkan ke dalam daftar tugas kelompok kamu, "+user.getUserName();
    }
    public String lihatTugasKelompok(UserAgentD user){
        List<TugasKelompok> tasks = tugasKelompokRepo.fetchTugasKelompok(user.getId());
        String jawaban = "";
        for(TugasKelompok task :tasks) {

            jawaban += "id tugas : " + task.getId() + "\n";
            jawaban += "nama tugas : " + task.getName() + "\n";
            jawaban += "deskripsi : " + task.getDesc() + "\n";
            jawaban += "deadline : " + task.getDeadline() + "\n";
            jawaban += "daftar anggota : " + getDaftarAnggotaUserName(task.getDaftarAnggota()) + "\n\n";
        }
        return jawaban;
    }

    public String getDaftarAnggotaUserName(String daftarAnggota){
        String listAnggota = "";
        List<String> daftarAnggotaList = Arrays.asList(daftarAnggota.split(" "));
        for(int i=0;i<daftarAnggotaList.size();i++){
            String anggotaID = daftarAnggotaList.get(i);
            String username = userRepo.findLineUserByUserId(anggotaID).getUserName();
            if(i<daftarAnggotaList.size()-1)
                listAnggota+= username+", ";
            else
                listAnggota+= username+".";
        }
        return listAnggota;
    }

    public String remindTugasKelompok(long id, UserAgentD user, AgentDController controller){
        TugasKelompok tk = tugasKelompokRepo.findById(id).get();
        String[] daftarAnggota = tk.getDaftarAnggota().split(" ");
        String message = ""+ user.getUserName() +" mau ngingetin jangan lupa untuk mengerjakan tugas kelompok "+tk.getName()+" dengan deadline "+tk.getDeadline()+"!";
        for(int i = 0;i<daftarAnggota.length;i++){
            if(!daftarAnggota[i].equals(user.getId())) {
                UserAgentD pengguna = userRepo.findById(daftarAnggota[i]).get();
                String reminder = "[REMINDER!!] Halo " + pengguna.getUserName() + ", ";
                reminder += message;
                controller.handlePushEvent(daftarAnggota[i], reminder);
            }
        }
        return "Kamu telah melakukan reminder kepada semua anggota tugas kelompok id "+id;
    }

    public String joinTugasKelompok(long id, UserAgentD user){
        TugasKelompok tk = tugasKelompokRepo.findById(id).get();
        tk.setDaftarAnggota(user.getId());
        tugasKelompokRepo.save(tk);
        return "Selamat, kamu telah join menjadi anggota tugas kelompok dengan id "+id;
    }

    public String removeTugasKelompok(long id){
        tugasKelompokRepo.deleteTugasKelompok(id);
        return "Tugas Kelompok dengan ID : "+id+" telah dihapus";
    }

    public String tambahJadwal(UserAgentD user,String nama, String day, String startTime, String endTime){
        Jadwal schedule = new Jadwal(nama, day, startTime, endTime, user.getId());
        jadwalRepo.save(schedule);
        return "Jadwal "+nama+" telah ditambahkan dalam jadwal anda, "+user.getUserName();
    }

    public String lihatJadwal(UserAgentD user){
        String jawaban = "";
        List<Jadwal> jadwals = jadwalRepo.fetchJadwal(user.getId());
        for (Jadwal jadwalSekarang : jadwals) {
            String name = jadwalSekarang.getName();
            String day = jadwalSekarang.getDay();
            String timeStart = jadwalSekarang.getTimeStart();
            String  timeEnd = jadwalSekarang.getTimeEnd();
            jawaban += jadwalSekarang.getId()+" "+name + " " + day + " " + timeStart + " - " + timeEnd + "\n\n";
        }
        return jawaban;
    }

    public String removeJadwal(long id){
        jadwalRepo.deleteJadwal(id);
        return "Jadwal dengan ID : "+id+" telah dihapus";
    }
}
