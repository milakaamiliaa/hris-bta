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

    @RequestMapping(value = "/pegawai/daftar-pegawai", method = RequestMethod.GET)
    public String viewAllPegawai(Model model){
        List<UserModel> listPegawai = userService.getUserList();
        model.addAttribute("listPegawai", listPegawai);
        return "daftar-pegawai";
    }

    @RequestMapping(value = "/pegawai/daftar-pegawai/{role}", method = RequestMethod.GET)
    public String viewPegawaiByRole(@PathVariable Long role, Model model){
        List<UserModel> listPegawai = userService.getUserByRole(role);
        model.addAttribute("listPegawai", listPegawai);
        return "daftar-pegawai";
    }

    @RequestMapping(value = "/pegawai/daftar-pegawai/detail/{idUser}", method = RequestMethod.GET)
    public String viewPegawai(@PathVariable Long idUser, Model model){
        UserModel pegawai = userService.getUserById(String.valueOf(idUser));
        model.addAttribute("pegawai", pegawai);
        return "detail-pegawai";
    }

    @RequestMapping(value="/pegawai/tambah", method = RequestMethod.GET)
    public String addPegawaiForm(Model model) {
        UserModel newUser = new UserModel();
        List<GolonganModel> listGolongan = golonganService.getGolonganList();
        List<RoleModel> listRole = roleService.getRoleList();
    	model.addAttribute("newPegawai", newUser);
        model.addAttribute("listGolongan", listGolongan);
        model.addAttribute("listRole", listRole);
    	return "form-tambah-pegawai";
    }
    
    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
    public String submitAddPegawai(@ModelAttribute UserModel pegawai, Model model) {

        pegawai.setCreatedAt(LocalDate.now());

        String newNIP = "";
        if (pegawai.getMataPelajaran() == "Biologi"){
            newNIP += "BIO";
        }else if(pegawai.getMataPelajaran() == "Ekonomi"){
            newNIP += "EKO";
        }else if(pegawai.getMataPelajaran() == "Matematika"){
            newNIP += "MTK";
        }else if(pegawai.getMataPelajaran() == "Kimia"){
            newNIP += "KIM";
        }else if(pegawai.getMataPelajaran() == "Fisika"){
            newNIP += "FIS";
        }else if(pegawai.getMataPelajaran() == "Sosiologi"){
            newNIP += "SOS";
        }else if(pegawai.getMataPelajaran() == "Geografi"){
            newNIP += "GEO";
        }else if(pegawai.getMataPelajaran() == "TPA"){
            newNIP += "TPA";
        }else if(pegawai.getMataPelajaran() == "Bahasa Inggris"){
            newNIP += "ING";
        }else if(pegawai.getMataPelajaran() == "Bahasa Indonesia"){
            newNIP += "IND";
        }

        newNIP += String.valueOf(LocalDate.now().getYear());
        newNIP += String.valueOf(pegawai.getTglLahir().getMonthValue());
        newNIP += String.valueOf(pegawai.getTglLahir().getYear());

        List<UserModel> allUser = userService.getUserList();
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
