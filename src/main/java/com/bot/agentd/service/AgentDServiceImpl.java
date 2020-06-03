package com.bot.agentd.service;

import com.bot.agentd.core.TugasIndividu;
import com.bot.agentd.core.UserAgentD;
import com.bot.agentd.repository.TugasIndividuRepository;
import com.bot.agentd.repository.UserAgentDRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    static String tidakDikenal = "Maaf, command tidak dikenali";

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

    public String periksaMessage(String id, String[] pesanSplit) {
        UserAgentD user = userRepo.findLineUserByUserId(id);
        String jawaban = "";
        switch (pesanSplit[0].toLowerCase()) {
            case ("tambah"):
                switch (pesanSplit[1].toLowerCase()) {
                    case ("tugas individu"):
                        jawaban = this.tambahTugasIndividu(user, pesanSplit[2], pesanSplit[3], pesanSplit[4]);
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
                    default:
                        jawaban = tidakDikenal;
                }
                break;
            case("remove"):
                switch (pesanSplit[1].toLowerCase()){
                    case ("tugas individu"):
                        jawaban = this.removeTugasIndividu(Long.parseLong(pesanSplit[2]));
                }
            default:
                jawaban = tidakDikenal;
        }
        return jawaban;
    }

    public String tambahTugasIndividu (UserAgentD user, String nama, String desc, String deadline){
        String[] deadlineSplit = deadline.split("-");
        LocalDateTime deadlineDate = LocalDateTime.of(Integer.parseInt(deadlineSplit[2]), Integer.parseInt(deadlineSplit[1]), Integer.parseInt(deadlineSplit[0]),0,0);
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




//    UserAgentDRepository repository;
//    String tidakDikenal = "Command Tidak Dikenali";
//
//    public AgentDServiceImpl(){
//        repository = new UserAgentDRepository();
//    }
//
//    public boolean isUserRegistered(String id){
//        return repository.isRegistered(id);
//    }
//
//    public void registerUser(String id, String uname){
//        repository.registerUser(id,uname);
//    }
//
//    public UserAgentD findUserById(String id){
//        return repository.findUserById(id);
//    }
//
//    public String periksaMessage(String id, String[] pesanSplit){
//        try {
//            UserAgentD user = this.findUserById(id);
//            String jawaban = "";
//            switch (pesanSplit[0].toLowerCase()) {
//                case ("quote"):
//                    if (!qRepo.isInitiated()) {
//                        qRepo.repoInitiation();
//                    }
//                    jawaban = qRepo.getQuote();
//                    break;
//                case ("tambah"):
//                    switch (pesanSplit[1].toLowerCase()) {
//                        case ("tugas individu"):
//                            jawaban = user.tambahTugasIndividu(pesanSplit[2], pesanSplit[3], pesanSplit[4]);
//                            break;
//                        case ("tugas kelompok"):
//                            jawaban = user.tambahTugasKelompok(pesanSplit[2], pesanSplit[3], pesanSplit[4]);
//                            break;
//                        case ("jadwal"):
//                            String name = pesanSplit[2];
//                            String day = pesanSplit[3];
//                            String startTime = pesanSplit[4];
//                            String endTime = pesanSplit[5];
//                            jawaban = user.tambahJadwal(name, day, startTime, endTime);
//                            break;
//                        default:
//                            jawaban = tidakDikenal;
//                    }
//                    break;
//                case ("lihat"):
//                    switch (pesanSplit[1].toLowerCase()) {
//                        case ("tugas individu"):
//                            jawaban = user.lihatTugasIndividu();
//                            break;
//                        case ("tugas kelompok"):
//                            jawaban = user.lihatTugasKelompok();
//                            break;
//                        case ("jadwal"):
//                            jawaban = user.lihatJadwal();
//                            break;
//                        default:
//                            jawaban = tidakDikenal;
//                    }
//                    break;
//                case("remove"):
//                    switch (pesanSplit[1].toLowerCase()){
//                        case("tugas individu"):
//                            String tugasId = pesanSplit[2];
//                            jawaban = user.removeTugasIndividu(tugasId);
//                            break;
//                        default:
//                            jawaban = tidakDikenal;
//                    }
//                    break;
//                case("cekid"):
//                    if(pesanSplit.length==1)jawaban = user.getId();
//                    else jawaban = tidakDikenal;
//                    break;
//                default:
//                    jawaban = tidakDikenal;
//            }
//            return jawaban;
//        }catch (Exception e){
//            UserAgentD.log.log(Level.INFO, "Error Happens in quote initiating");
//            return "Error Occured while initiating Quotes.";
//        }
//    }
}
