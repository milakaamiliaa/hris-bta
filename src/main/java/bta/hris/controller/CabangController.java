package bta.hris.controller;

import bta.hris.model.CabangDataModel;
import bta.hris.model.CabangModel;
import bta.hris.model.UserModel;

import bta.hris.service.CabangDataService;
import bta.hris.service.CabangService;
import bta.hris.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CabangController {
    @Autowired
    private CabangService cabangService;

    @Autowired
    private UserService userService;

    @Autowired
    private CabangDataService cabangDataService;

    @RequestMapping(value = "/cabang")
    public String daftarCabang(Model model) {
        List<CabangModel> listCabang = cabangService.getCabangList();
        List<CabangModel> listActiveCabang = new ArrayList<CabangModel>();
        for (CabangModel cabang : listCabang) {
            if (cabang.isActive()) {
                listActiveCabang.add(cabang);
            }
        }
        model.addAttribute("listCabang", listActiveCabang);
        return "daftar-cabang";
    }

    @RequestMapping(value = "/cabang/detail/{idCabang}", method = RequestMethod.GET)
    public String detailCabang(@PathVariable Long idCabang, Model model) {
        CabangModel cabang = cabangService.getCabangByIdCabang(idCabang).get();
        cabangService.detailCabang(cabang);

        model.addAttribute("cabang", cabang);
        return "detail-cabang";
    }

    @RequestMapping(value = "/cabang/tambah", method = RequestMethod.GET)
    public String tambahCabangForm(Model model) {
        CabangModel newCabang = new CabangModel();
        model.addAttribute("cabang", newCabang);

        return "form-tambah-cabang";
    }

    @RequestMapping(value = "/cabang/tambah", method = RequestMethod.POST)
    public String tambahCabangSubmit(@ModelAttribute CabangModel cabang, Model model, RedirectAttributes redirect) {
        CabangModel cabangSaved = cabangService.createCabang(cabang);

        // ==== CabangDataModel ====
        CabangDataModel cabangData = new CabangDataModel();
        cabangData.setCabang(cabangSaved);
        cabangDataService.addCabangData(cabangData);

        redirect.addFlashAttribute("alert", "Cabang " + cabang.getNama() + " Berhasil Ditambahkan. ");

        return "redirect:/cabang";
    }

    @RequestMapping(value = "cabang/ubah/{idCabang}", method = RequestMethod.GET)
    public String ubahCabangForm(@PathVariable Long idCabang, Model model) {
        CabangModel existingCabang = cabangService.getCabangByIdCabang(idCabang).get();
        ArrayList<UserModel> listCalonStaf = new ArrayList<>();
        List<UserModel> listUser = userService.getAllUser();
        ArrayList<UserModel> listStafCabang = new ArrayList<>();
        List<CabangModel> listCabang = cabangService.getCabangList();

        for (CabangModel cabang : listCabang) {
            if (cabang.getStafCabang() != null) {
                listStafCabang.add(cabang.getStafCabang());
            }
        }

        for (UserModel calonStaf : listUser) {
            if (calonStaf.getRole().getNama().equalsIgnoreCase("Staf Cabang")) {
                if (listStafCabang.contains(calonStaf) == false) {
                    if (calonStaf.isActive()) {
                        listCalonStaf.add(calonStaf);
                    }
                }
            }
        }

        try {
            model.addAttribute("namaStaf", existingCabang.getStafCabang().getNama());
        }

        catch (NullPointerException e) {
            model.addAttribute("namaStaf", "-");
        }

        model.addAttribute("cabang", existingCabang);
        model.addAttribute("listCalonStaf", listCalonStaf);

        return "form-ubah-cabang";
    }

    @RequestMapping(value = "cabang/ubah/{idCabang}", method = RequestMethod.POST)
    public String ubahCabangSubmit(@PathVariable Long idCabang, @ModelAttribute CabangModel cabang, Model model,
            RedirectAttributes redirect) {
        CabangModel newCabang = cabangService.updateCabang(cabang);
        LocalDate tanggal = LocalDate.now();
        String month = "";
        if (String.valueOf(tanggal.getMonthValue()).length() == 1) {
            month = "0" + tanggal.getMonthValue();
        } else {
            month = String.valueOf(tanggal.getMonthValue());
        }
        String year = String.valueOf(tanggal.getYear()).substring(2, 4);

        if (cabangDataService.getCabangDataByCabangAndPeriode(newCabang, month + year) == null) {
            CabangDataModel cabangData = new CabangDataModel();
            cabangData.setCabang(newCabang);
            cabangDataService.addCabangData(cabangData);
        }

        else {
            CabangDataModel cabangData = cabangDataService.getCabangDataByCabangAndPeriode(newCabang, month + year);
            cabangData.setJumlahSiswa(newCabang.getJumlahSiswa());
            cabangDataService.updateCabangData(cabangData);
        }
        model.addAttribute("cabang", newCabang);
        redirect.addFlashAttribute("alertUbah", "Cabang " + cabang.getNama() + " berhasil diubah.");
        return "redirect:/cabang";
    }

    @RequestMapping(value = "/cabang/hapus/{idCabang}", method = RequestMethod.POST)
    public String hapusCabang(@PathVariable Long idCabang, Model model, RedirectAttributes redirect) {
        CabangModel cabang = cabangService.getCabangByIdCabang(idCabang).get();
        model.addAttribute("cabang", cabang);
        cabangService.deleteCabang(cabang);
        redirect.addFlashAttribute("alertHapus", "Cabang " + cabang.getNama() + " berhasil dihapus.");
        return "redirect:/cabang";
    }
}