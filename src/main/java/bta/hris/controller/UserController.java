package bta.hris.controller;

import bta.hris.model.GolonganModel;
import bta.hris.model.UserModel;
import bta.hris.model.RoleModel;
import bta.hris.service.GolonganService;
import bta.hris.service.UserService;
import bta.hris.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private GolonganService golonganService;

    @Autowired
    private RoleService roleService;


//    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
//    private String addUserSubmit(@ModelAttribute UserModel user) {
//        userService.addUser(user);
//        return "home";
//    }

    @RequestMapping(value="/pegawai/tambah", method = RequestMethod.GET)
    public String addPegawaiForm(Model model) {
        UserModel newUser = new UserModel();
        List<GolonganModel> listGolongan = golonganService.getAllGolongan();
        List<RoleModel> listRole = roleService.getAllRole();

        List<String> mataPelajaran = new ArrayList<String>();
        mataPelajaran.add("Biologi");
        mataPelajaran.add("Ekonomi");
        mataPelajaran.add("Matematika");
        mataPelajaran.add("Kimia");
        mataPelajaran.add("Fisika");
        mataPelajaran.add("Sosiologi");
        mataPelajaran.add("Geografi");
        mataPelajaran.add("TPA");
        mataPelajaran.add("Bahasa Inggris");
        mataPelajaran.add("Bahasa Indonesia");

        model.addAttribute("newPegawai", newUser);
        model.addAttribute("listGolongan", listGolongan);
        model.addAttribute("listRole", listRole);
        model.addAttribute("mataPelajaran", mataPelajaran);
        return "form-tambah-pegawai";
    }

    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
    public String submitAddPegawai(@ModelAttribute UserModel pegawai, Model model) {

        pegawai.setCreatedAt(LocalDate.now());

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

        List<UserModel> allUser = userService.getAllUser();
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

        pegawai.setNip(newNIP);
        userService.addUser(pegawai);
        model.addAttribute("newPegawai", pegawai);
        return "daftar-pegawai";
    }
}
