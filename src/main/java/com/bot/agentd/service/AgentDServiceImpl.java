package com.bot.agentd.service;

import com.bot.agentd.core.UserAgentD;
import com.bot.agentd.repository.UserAgentDRepository;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

import static com.bot.agentd.core.UserAgentD.qRepo;

@Service
public class AgentDServiceImpl implements AgentDService {
    UserAgentDRepository repository;
    String tidakDikenal = "Command Tidak Dikenali";

    public AgentDServiceImpl(){
        repository = new UserAgentDRepository();
    }

    public boolean isUserRegistered(String id){
        return repository.isRegistered(id);
    }

    public void registerUser(String id, String uname){
        repository.registerUser(id,uname);
    }

    public UserAgentD findUserById(String id){
        return repository.findUserById(id);
    }

    public String periksaMessage(String id, String[] pesanSplit){
        try {
            UserAgentD user = this.findUserById(id);
            String jawaban = "";
            switch (pesanSplit[0].toLowerCase()) {
                case ("quote"):
                    if (!qRepo.isInitiated()) {
                        qRepo.repoInitiation();
                    }
                    jawaban = qRepo.getQuote();
                    break;

                case ("tambah"):
                    switch (pesanSplit[1].toLowerCase()) {
                        case ("tugas individu"):
                            jawaban = user.tambahTugasIndividu(pesanSplit[2], pesanSplit[3], pesanSplit[4]);
                            break;
                        case ("tugas kelompok"):
                            jawaban = user.tambahTugasKelompok(pesanSplit[2], pesanSplit[3], pesanSplit[4]);
                            break;
                        case ("jadwal"):
                            String name = pesanSplit[2];
                            String day = pesanSplit[3];
                            String startTime = pesanSplit[4];
                            String endTime = pesanSplit[5];
                            jawaban = user.tambahJadwal(name, day, startTime, endTime);
                            break;
                        default:
                            jawaban = tidakDikenal;
                    }
                    break;
                case ("lihat"):
                    switch (pesanSplit[1].toLowerCase()) {
                        case ("tugas individu"):
                            jawaban = user.lihatTugasIndividu();
                            break;
                        case ("tugas kelompok"):
                            jawaban = user.lihatTugasKelompok();
                            break;
                        case ("jadwal"):
                            jawaban = user.lihatJadwal();
                            break;
                        default:
                            jawaban = tidakDikenal;
                    }
                    break;
                case("cekid"):
                    if(pesanSplit.length==1)jawaban = user.getId();
                    else jawaban = tidakDikenal;
                    break;
                default:
                    jawaban = tidakDikenal;
            }
            return jawaban;
        }catch (Exception e){
            UserAgentD.log.log(Level.INFO, "Error Happens in quote initiating");
            return "Error Occured while initiating Quotes.";
        }
    }
}
