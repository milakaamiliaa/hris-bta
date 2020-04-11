package bta.hris.controller;

import bta.hris.model.CabangModel;
import bta.hris.model.PresensiModel;
import bta.hris.model.UserModel;
import bta.hris.service.CabangService;
import bta.hris.service.PresensiService;
import bta.hris.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        model.addAttribute("daftarPresensi", presensiService.getAllPresensiByNip(user.getNip()));

        return "daftar-presensi";
    }

    @RequestMapping(value = "/presensi/tambah", method = RequestMethod.GET)
    public String tambahPresensiForm(Model model){
        LocalDate localDate = LocalDate.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String formattedString = localDate.format(formatter);
        List<CabangModel> listCabang = cabangService.getCabangList();
        List<CabangModel> listActiveCabang = new ArrayList <CabangModel>();
        for (CabangModel cabang : listCabang){
            if (cabang.isActive()){
                listActiveCabang.add(cabang);
            }
        }

        model.addAttribute("presensi", new PresensiModel());
        model.addAttribute("cabangList", listActiveCabang);
        model.addAttribute("localDate", formattedString);

        return "form-tambah-presensi";
    }

    @RequestMapping(value = "/presensi/tambah", method = RequestMethod.POST)
    public String tambahPresensiSubmit(@ModelAttribute PresensiModel presensi, Model model, RedirectAttributes redirect){


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String nip = ((UserDetails) principal).getUsername();

        presensi.setTanggalPresensi(LocalDate.now());
        PresensiModel addedPresensi = presensiService.addPresensi(presensi,nip);
        List<CabangModel> listActiveCabang = new ArrayList <CabangModel>();
        List<CabangModel> listCabang = cabangService.getCabangList();
        for (CabangModel cabang : listCabang){
            if (cabang.isActive()){
                listActiveCabang.add(cabang);
            }
        }

        model.addAttribute("addedPresensi", addedPresensi);
        model.addAttribute("presensi", new PresensiModel());

        model.addAttribute("cabangList", listActiveCabang);
        model.addAttribute("localDate", LocalDate.now());
        redirect.addFlashAttribute("alertTambah", "Presensi Berhasil Ditambahkan");
        return "redirect:/presensi";
    }

    @RequestMapping(value = "presensi/ubah/{idPresensi}", method = RequestMethod.GET)
    public String ubahPresensiForm(@PathVariable Long idPresensi, Model model) {
        PresensiModel existingPresensi = presensiService.getPresensiById(idPresensi);

        List<CabangModel> listCabang = cabangService.getCabangList();

        LocalDate localDate = LocalDate.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String formattedString = localDate.format(formatter);

        model.addAttribute("localDate", formattedString);
        model.addAttribute("presensi", existingPresensi);
        model.addAttribute("listCabang", listCabang);

        return "form-ubah-presensi";
    }

    @RequestMapping(value = "presensi/ubah/{idPresensi}", method = RequestMethod.POST)
    public String ubahPresensiSubmit(@PathVariable Long idPresensi, @ModelAttribute PresensiModel presensi, Model model, RedirectAttributes redirect) {

        PresensiModel newPresensi = presensiService.updatePresensi(presensi);

        model.addAttribute("presensi", newPresensi);
        redirect.addFlashAttribute("alertUbah", "Presensi Berhasil Diubah");
        return "redirect:/presensi";
    }

    @RequestMapping(value = "presensi/kelola", method = RequestMethod.GET)
    public String daftarPengajuanPresensi(Model model) {
        UserModel user = userService.getByNip(SecurityContextHolder.getContext().getAuthentication().getName());
        CabangModel cabang = cabangService.getCabangByStafCabang(user).get();

        List<PresensiModel> daftarPresensi = presensiService.getAllPresensiByCabang(cabang);

        model.addAttribute("daftarPresensi", daftarPresensi);

        return "daftar-presensi-kelola";
    }

    @RequestMapping(value = "presensi/setujui/{idPresensi}", method = RequestMethod.GET)
    public String formSetujuiPresensi(@PathVariable Long idPresensi, Model model) {
        PresensiModel presensi = presensiService.getPresensiById(idPresensi);

        model.addAttribute("presensi", presensi);

        return "form-setujui-presensi";
    }

    @RequestMapping(value = "presensi/setujui/{idPresensi}", method = RequestMethod.POST)
    public String setujuiPresensi(@PathVariable Long idPresensi, @ModelAttribute PresensiModel presensi, Model model) {
        String month = "";
        if (String.valueOf(presensi.getTanggalPresensi().getMonthValue()).length() == 1) {
            month = "0" + presensi.getTanggalPresensi().getMonthValue();
        }

        else {
            month = String.valueOf(presensi.getTanggalPresensi().getMonthValue());
        }
        String year = String.valueOf(presensi.getTanggalPresensi().getYear()).substring(2,4);
        presensi.setKodeGaji(month + year);
        presensi.setStatus("disetujui");
        PresensiModel newPresensi = presensiService.approvePresensi(presensi);

        model.addAttribute("presensi", newPresensi);

        return "redirect:/presensi/kelola";
    }


}
