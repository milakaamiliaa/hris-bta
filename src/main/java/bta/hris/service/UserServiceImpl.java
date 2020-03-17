package bta.hris.service;

import bta.hris.repository.UserDB;
import bta.hris.model.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.transaction.Transactional;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDB userDB;


    @Override
    public UserModel getUserById(String id){
        return userDB.findById(id).get();
    }
  
    @Override
    public UserModel getByNip(String nip) {
        return userDb.findByNip(nip);
    }

    @Override
    public List<UserModel> getAllUser(){
        return userDB.findAll();
    }

    @Override
    public UserModel addUser(UserModel user) {
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        return userDB.save(user);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public List<UserModel> getUserByRole(Long role){
        List<UserModel> allUser = userDB.findAll();
        List<UserModel> result = new ArrayList<UserModel>();

        for (UserModel user : allUser){
            if (user.getRole().getIdRole() == role){
                result.add(user);
            }
        }return result;
    }

    @Override
    public UserModel changeUser(UserModel pegawai){
        UserModel targetUser = userDB.findByIdUser(pegawai.getIdUser()).get();
        try{
            targetUser.setNama(pegawai.getNama());
            targetUser.setEmail(pegawai.getEmail());
            targetUser.setAlamat(pegawai.getAlamat());
            targetUser.setNoTelp(pegawai.getNoTelp());
            targetUser.setTglLahir(pegawai.getTglLahir());
            targetUser.setPassword(pegawai.getPassword());
            targetUser.setActive(pegawai.isActive());
            targetUser.setMataPelajaran(pegawai.getMataPelajaran());
            targetUser.setNoRekening(pegawai.getNoRekening());
            targetUser.setNamaBank(pegawai.getNamaBank());
            
            String newNIP = "";
        if (pegawai.getMataPelajaran().equals("Biologi")){
            newNIP += "BIO";
        }else if(pegawai.getMataPelajaran().equals("Ekonomi")){
            newNIP += "EKO";
        }else if(pegawai.getMataPelajaran().equals("Matematika")){
            newNIP += "MTK";
        }else if(pegawai.getMataPelajaran().equals("Kimia")){
            newNIP += "KIM";
        }else if(pegawai.getMataPelajaran().equals("Fisika")){
            newNIP += "FIS";
        }else if(pegawai.getMataPelajaran().equals("Sosiologi")){
            newNIP += "SOS";
        }else if(pegawai.getMataPelajaran().equals("Geografi")){
            newNIP += "GEO";
        }else if(pegawai.getMataPelajaran().equals("TPA")){
            newNIP += "TPA";
        }else if(pegawai.getMataPelajaran().equals("Bahasa Inggris")){
            newNIP += "ING";
        }else if(pegawai.getMataPelajaran().equals("Bahasa Indonesia")){
            newNIP += "IND";
        }

        newNIP += String.valueOf(LocalDate.now().getYear());
        newNIP += String.valueOf(pegawai.getTglLahir().getMonthValue());
        newNIP += String.valueOf(pegawai.getTglLahir().getYear());
        List<UserModel> allUser = getAllUser();
        List<UserModel> userInAYear = new ArrayList<UserModel>();
        for (UserModel u : allUser){
            if (u.getCreatedAt().getYear() == LocalDate.now().getYear()){
                userInAYear.add(u);
            }
        }

        if (userInAYear.size()<10){
            newNIP += "0"+String.valueOf(userInAYear.size());
        }else{
            newNIP += String.valueOf(userInAYear.size());
        }

        targetUser.setNip(newNIP);
        userDB.save(targetUser);
        return targetUser;
        }catch (NullPointerException e){
            return null;
        }
    }

    @Override
    public boolean deleteUser(UserModel user) {
        UserModel targetUser = userDB.findById(user.getIdUser()).get();
        if (targetUser.isActive()) {
            targetUser.setActive(false);
            return true;
        }
        return false;
    }
}

