package bta.hris.controller;

import bta.hris.model.CabangModel;
import bta.hris.model.PresensiModel;
import bta.hris.model.UserModel;
import bta.hris.service.CabangService;
import bta.hris.service.PresensiService;
import bta.hris.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.List;

@Controller
public class PresensiController {

    @Autowired
    private PresensiService presensiService;

    @Autowired
    private UserService userService;

    @Autowired
    private CabangService cabangService;




    @RequestMapping(value="/presensi", method = RequestMethod.GET)
    public String daftarPresensi(Model model){
        UserModel user = userService.getByNip(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("allPresensi", presensiService.getAllPresensi());
//        model.addAttribute("daftarPresensi", presensiService.getAllPresensiByNip(user.getNip()));
        return "daftar-presensi";
    }

    @RequestMapping(value = "/presensi/tambah", method = RequestMethod.GET)
    public String tambahPresensiForm(Model model){
        model.addAttribute("presensi", new PresensiModel());
        model.addAttribute("cabangList", cabangService.getAllCabang());
        model.addAttribute("localDate", LocalDate.now());

        // PLEASE REMOVE LATER ---
        UserModel userTest = userService.getByNip("12345");
        model.addAttribute("userTest", userTest);
        // END OF REMOVE ---


        return "form-tambah-presensi";
    }

    @RequestMapping(value = "/presensi/tambah", method = RequestMethod.POST)
    public String tambahPresensi(@ModelAttribute PresensiModel presensi, Model model){

//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String nip = ((UserDetails) principal).getUsername();
        presensi.setTanggalPresensi(java.sql.Date.valueOf(LocalDate.now()));
        PresensiModel addedPresensi = presensiService.addPresensi(presensi);
        model.addAttribute("addedPresensi", presensi);
        model.addAttribute("presensi", new PresensiModel());
        model.addAttribute("cabangList", cabangService.getAllCabang());
        model.addAttribute("localDate", LocalDate.now());
        System.out.println("test");
        System.out.println(LocalDate.now());
        return "form-tambah-presensi";
    }

    @RequestMapping(value = "presensi/ubah/{idPresensi}", method = RequestMethod.GET)
    public String ubahPresensiForm(@PathVariable Long idPresensi, Model model) {
        PresensiModel existingPresensi = presensiService.getPresensiById(idPresensi);

        List<CabangModel> listCabang = cabangService.getAllCabang();

        model.addAttribute("presensi", existingPresensi);
        model.addAttribute("listCabang", listCabang);

        return "form-ubah-presensi";
    }

    @RequestMapping(value = "presensi/ubah/{idPresensi}", method = RequestMethod.POST)
    public String ubahPresensi(@PathVariable Long idPresensi, @ModelAttribute PresensiModel presensi, Model model) {
        PresensiModel newPresensi = presensiService.updatePresensi(presensi);
        model.addAttribute("presensi", newPresensi);
        return "redirect:/presensi";
    }
}
