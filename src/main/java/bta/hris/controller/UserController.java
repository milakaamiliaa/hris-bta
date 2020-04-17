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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @RequestMapping(value = "/pegawai", method = RequestMethod.GET)
    public String daftarPegawai(Model model){
        List<UserModel> listUser = userService.getAllUser();
        List<RoleModel> listRole = roleService.getAllRole();
        List<UserModel> activeUsers = new ArrayList<UserModel>();
        for (UserModel user : listUser){
            if(user.isActive()){
                activeUsers.add(user);
            }
        }
        model.addAttribute("listPegawai", activeUsers);
        model.addAttribute("listRole", listRole);
        return "daftar-pegawai";
    }

    @RequestMapping(value = "/pegawai/detail/{idUser}", method = RequestMethod.GET)
    public String detailPegawai(@PathVariable String idUser, Model model){
        UserModel pegawai = userService.getUserById(idUser);
        model.addAttribute("pegawai", pegawai);
        return "detail-pegawai";
    }

    @RequestMapping(value="/pegawai/tambah", method = RequestMethod.GET)
    public String tambahPegawaiForm(Model model) {
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
    public String tambahPegawaiSubmit(@ModelAttribute UserModel pegawai, Model model, RedirectAttributes redirect) {
            pegawai.setCreatedAt(LocalDate.now());

            String newNIP = "";

            if (pegawai.getMataPelajaran() != null) {
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
                }else if(pegawai.getMataPelajaran().equals("Sejarah")){
                    newNIP += "SEJ";
                }
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
            pegawai.setActive(true);
            userService.addUser(pegawai);
            model.addAttribute("newPegawai", pegawai);
            return "redirect:/pegawai/";
        
    }
    @RequestMapping(value = "/pegawai/ubah/{idUser}", method = RequestMethod.GET)
    public String ubahPegawaiForm(@PathVariable String idUser, Model model) {
        UserModel existingUser = userService.getUserById(idUser);
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

        model.addAttribute("listGolongan", listGolongan);
        model.addAttribute("listRole", listRole);
        model.addAttribute("mataPelajaran", mataPelajaran);
        model.addAttribute("pegawai", existingUser);
        return "form-ubah-pegawai";
    }

    @RequestMapping(value = "/pegawai/ubah/{idUser}", method = RequestMethod.POST)
    public String ubahPegawaiSubmit(@PathVariable String idUser, @ModelAttribute UserModel pegawai, Model model) {
            UserModel newPegawai = userService.changeUser(pegawai);
            model.addAttribute("newPegawai", newPegawai);
            return detailPegawai(idUser, model);
    }

    @RequestMapping(value = "/pegawai/hapus/{idUser}", method = RequestMethod.GET)
    public String hapusPegawai(@PathVariable String idUser, Model model) {
        UserModel targetUser = userService.getUserById(idUser);
        if (targetUser == null) {
            return "pegawai-tidak-ditemukan";
        }
        model.addAttribute("pegawai", targetUser);
        if (userService.deleteUser(targetUser)) {
            userService.deleteUser(targetUser);
            return "redirect:/pegawai/";
        }return "redirect:/pegawai/";

    }

}
