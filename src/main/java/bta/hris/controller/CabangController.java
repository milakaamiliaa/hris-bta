package bta.hris.controller;

import bta.hris.model.CabangModel;
import bta.hris.model.UserModel;
import bta.hris.service.CabangService;
import bta.hris.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CabangController {
    @Autowired
    private CabangService cabangService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/cabang")
    public String daftarCabang(Model model){
        List<CabangModel> listCabang = cabangService.getCabangList();
        List<CabangModel> listActiveCabang = new ArrayList<CabangModel>();
        for (CabangModel cabang : listCabang){
            if (cabang.isActive()){
                listActiveCabang.add(cabang);
            }
        }
        model.addAttribute("listCabang", listActiveCabang);
        return "daftar-cabang";
    }

    @RequestMapping(value = "/cabang/detail/{idCabang}", method = RequestMethod.GET)
    public String detailCabang(
            @PathVariable Long idCabang, Model model
    ) {
        CabangModel cabang = cabangService.getCabangByIdCabang(idCabang).get();
        cabangService.detailCabang(cabang);

        model.addAttribute("cabang", cabang);
        return "detail-cabang";
    }

    @RequestMapping(value = "/cabang/tambah", method = RequestMethod.GET)
    public String createCabangForm(Model model){
        CabangModel newCabang = new CabangModel();
        model.addAttribute("cabang", newCabang);
        return "form-tambah-cabang";
    }

    @RequestMapping(value = "/cabang/tambah", method = RequestMethod.POST)
    public String createCabangSubmit(@ModelAttribute CabangModel cabang, Model model){
        cabangService.createCabang(cabang);
        return "redirect:/cabang";
    }

    @RequestMapping(value = "cabang/ubah/{idCabang}", method = RequestMethod.GET)
    public String updateCabangForm(@PathVariable Long idCabang, Model model) {
        CabangModel existingCabang = cabangService.getCabangByIdCabang(idCabang).get();
        ArrayList<UserModel> listCalonStaf = new ArrayList<>();
        List<UserModel> listUser = userService.getAllUser();
        ArrayList<UserModel> listStafCabang = new ArrayList<>();
        List<CabangModel> listCabang = cabangService.getCabangList();

        for (CabangModel cabang : listCabang){
            if (cabang.getStafCabang() != null){
                listStafCabang.add(cabang.getStafCabang());
            }
        }

        for (UserModel calonStaf : listUser) {
            if (calonStaf.getRole().getNama().equalsIgnoreCase("Staf Cabang")){
                if (listStafCabang.contains(calonStaf) == false){
                    if (calonStaf.isActive()){
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
    public String updateCabangSubmit(@PathVariable Long idCabang, @ModelAttribute CabangModel cabang, Model model) {
        CabangModel newCabang = cabangService.updateCabang(cabang);
        model.addAttribute("cabang", newCabang);
        return "redirect:/cabang";
    }

    @RequestMapping(value="/cabang/hapus/{idCabang}", method = RequestMethod.POST)
    public String deleteCabang(@PathVariable Long idCabang, Model model){
        CabangModel cabang = cabangService.getCabangByIdCabang(idCabang).get();
//        if (cabang == null){
//            return "Maaf, cabang tidak di"

        model.addAttribute("cabang", cabang);
        cabangService.deleteCabang(cabang);
         return "redirect:/cabang";
    }
}


