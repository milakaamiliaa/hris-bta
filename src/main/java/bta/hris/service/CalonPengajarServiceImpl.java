package bta.hris.service;

import bta.hris.model.CalonPengajarModel;
import bta.hris.model.UserModel;
import bta.hris.repository.CalonPengajarDB;
import bta.hris.repository.GolonganDB;
import bta.hris.repository.RoleDB;
import bta.hris.repository.UserDB;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;


@Service
@Transactional
public class CalonPengajarServiceImpl implements CalonPengajarService{
    @Autowired
    private CalonPengajarDB calonPengajarDB;

    @Autowired
    private GolonganDB golonganDB;

    @Autowired
    private UserDB userDB;

    @Autowired
    private RoleDB RoleDB;

    @Override
    public CalonPengajarModel getCalonById(String id) {
        return calonPengajarDB.findByIdCalon(id).get();
    }

    @Override
    public CalonPengajarModel addCalon(CalonPengajarModel calon){
        String pass = encrypt(calon.getPassword());
        calon.setPassword(pass);
        return calonPengajarDB.save(calon);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public List<CalonPengajarModel> getAllCalon(){
        return calonPengajarDB.findAll();
    }

    @Override
    public CalonPengajarModel rekrutCalon(CalonPengajarModel calon){
        try{
            UserModel newPegawai = userDB.findByNip(calon.getUsername());            
            String newNIP = "";
            if (calon.getMataPelajaran() != null) {
                if (calon.getMataPelajaran().equals("Biologi")){
                    newNIP += "BIO";
                }else if(calon.getMataPelajaran().equals("Ekonomi")){
                    newNIP += "EKO";
                }else if(calon.getMataPelajaran().equals("Matematika")){
                    newNIP += "MTK";
                }else if(calon.getMataPelajaran().equals("Kimia")){
                    newNIP += "KIM";
                }else if(calon.getMataPelajaran().equals("Fisika")){
                    newNIP += "FIS";
                }else if(calon.getMataPelajaran().equals("Sosiologi")){
                    newNIP += "SOS";
                }else if(calon.getMataPelajaran().equals("Geografi")){
                    newNIP += "GEO";
                }else if(calon.getMataPelajaran().equals("TPA")){
                    newNIP += "TPA";
                }else if(calon.getMataPelajaran().equals("Bahasa Inggris")){
                    newNIP += "ING";
                }else if(calon.getMataPelajaran().equals("Bahasa Indonesia")){
                    newNIP += "IND";
                }
            }

            newNIP += String.valueOf(LocalDate.now().getYear());
            newNIP += String.valueOf(calon.getTglLahir().getMonthValue());
            newNIP += String.valueOf(calon.getTglLahir().getYear());

            List<UserModel> allUser = userDB.findAll();
            List<UserModel> userInAYear = new ArrayList<UserModel>();
            for (UserModel user : allUser){
                if (user.getCreatedAt().getYear() == LocalDate.now().getYear()){
                    userInAYear.add(user);
                }
            }

            if (userInAYear.size()<10){
                newNIP += "0"+String.valueOf(userInAYear.size());
            }else{
                newNIP += String.valueOf(userInAYear.size());
            }

            newPegawai.setNip(newNIP);
            newPegawai.setRole(RoleDB.findByNama("PENGAJAR").get());
            userDB.save(newPegawai);

            CalonPengajarModel targetCalon = calonPengajarDB.findByIdCalon(calon.getIdCalon()).get();
            targetCalon.setStatus("Telah direkrut");
            targetCalon.setUpdatedAt(LocalDate.now());
            calonPengajarDB.save(targetCalon);
            return targetCalon;
        }catch(NullPointerException e){
            return null;
        }
    }

    @Override
    public boolean hapusCalon(CalonPengajarModel calon){
        CalonPengajarModel target = calonPengajarDB.findByIdCalon(calon.getIdCalon()).get();
        if (target != null) {
            calonPengajarDB.delete(target);
            return true;
        }
        return false;
    }

    @Override
    public CalonPengajarModel tolakCalon(CalonPengajarModel calon){
        CalonPengajarModel targetCalon = calonPengajarDB.findByIdCalon(calon.getIdCalon()).get();
        try{
            targetCalon.setStatus("Ditolak");
            targetCalon.setUpdatedAt(LocalDate.now());
            calonPengajarDB.save(targetCalon);
            return targetCalon;
        }catch(NullPointerException e){
            return null;
        }
    }

    @Override
    public CalonPengajarModel undangCalon(CalonPengajarModel calon){
        CalonPengajarModel targetCalon = calonPengajarDB.findByIdCalon(calon.getIdCalon()).get();
        try{
            targetCalon.setStatus("Diundang");
            targetCalon.setUpdatedAt(LocalDate.now());
            calonPengajarDB.save(targetCalon);
            return targetCalon;
        }catch(NullPointerException e){
            return null;
        }
    }
  
    @Override
    public CalonPengajarModel createCalonPengajar(CalonPengajarModel calonPengajar){
        return calonPengajarDB.save(calonPengajar);
    }

    @Override
    public List<CalonPengajarModel>findAllCalonPengajar() {
        return calonPengajarDB.findAll();
    }
}