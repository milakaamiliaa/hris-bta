package bta.hris.controller;

import bta.hris.model.CabangModel;
import bta.hris.model.UserModel;
import bta.hris.service.CabangService;
import bta.hris.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    @RequestMapping(value = "/cabang/daftar")
    public String daftarCabang(Model model){
        List<CabangModel> listCabang = cabangService.getCabangList();
        model.addAttribute("listCabang", listCabang);

        return "daftar-cabang";
    }

    @RequestMapping(value = "/cabang/detail/{idCabang}", method = RequestMethod.GET)
    public String detailCabang(
            @PathVariable Long idCabang, Model model
    ) {
        CabangModel cabang = cabangService.getCabangByIdCabang(idCabang).get();
        model.addAttribute("cabang", cabang);
//        model.addAttribute("stafCabang", cabang.getStafCabang().getNama());
        return "detail-cabang";
    }

    @RequestMapping(value = "/cabang/tambah", method = RequestMethod.GET)
    public String createCabangForm(Model model){
//        UserModel user = userService.findByNama(authentication.getName());
        CabangModel newCabang = new CabangModel();
//        List<UserModel> listStafCabang = new ArrayList<>();
//
//        List<UserModel> listStaf = userService.getStafList();
//        for (UserModel staf : listStaf) {
//            if (staf.getRole().equals id nya staf cabang){
//
//            }
//        }
//        model.addAttribute("listStaf", listStaf;
//
//        model.addAttribute("user", user);
        model.addAttribute("cabang", newCabang);
        return "form-create-cabang";
    }

    @RequestMapping(value = "/cabang/tambah", method = RequestMethod.POST)
    public String createCabangSubmit(@ModelAttribute CabangModel cabang, Model model){
//        UserModel user = userService.findByNama(authentication.getName());
//        cabang.setStafCabang(user);
        cabangService.createCabang(cabang);
        return "redirect:/cabang/daftar";
    }

    @RequestMapping(value = "cabang/ubah/{idCabang}", method = RequestMethod.GET)
    public String updateCabangForm(@PathVariable Long idCabang, Model model) {
        //mengambil existing data restoran
        CabangModel existingCabang = cabangService.getCabangByIdCabang(idCabang).get();
        model.addAttribute("cabang", existingCabang);
        return "form-update-cabang";
    }

    @RequestMapping(value = "cabang/ubah/{idCabang}", method = RequestMethod.POST)
    public String updateCabangFormSubmit(@PathVariable Long idCabang, @ModelAttribute CabangModel cabang, Model model) {
        CabangModel newCabang = cabangService.updateCabang(cabang);
        model.addAttribute("cabang", newCabang);
        return "redirect:/cabang/daftar";
    }

    
}
