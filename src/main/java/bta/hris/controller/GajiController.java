package bta.hris.controller;

import bta.hris.model.GolonganModel;
import bta.hris.model.PresensiModel;
import bta.hris.model.UserModel;
import bta.hris.service.GolonganService;
import bta.hris.service.PresensiService;
import bta.hris.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class GajiController{

    @Autowired
    private PresensiService presensiService;

    @Autowired
    private UserService userService;

    @Autowired
    private GolonganService golonganService;



    @RequestMapping(value="/gaji", method = RequestMethod.GET)
    public String daftarGaji(Model model){
        UserModel user = userService.getByNip(SecurityContextHolder.getContext().getAuthentication().getName());
        List<PresensiModel> listPresensi = presensiService.getAllPresensiByNip(user.getNip());
        GolonganModel golongan = user.getGolongan();
        float pajak = golongan.getPajak();
        long rate = golongan.getRate();

        long totalSesi = 0;
        for(PresensiModel presensi : listPresensi){
            long sesiMengajar = presensi.getSesiMengajar();
            long sesiTambahan = presensi.getSesiTambahan();
            totalSesi = sesiMengajar + sesiTambahan;
        }
        model.addAttribute("totalSesi", totalSesi);
        model.addAttribute("golongan", golongan.getNama());
        model.addAttribute("pajak", pajak);
        model.addAttribute("rate", rate);
        model.addAttribute("gaji", totalSesi*rate);
        return "daftar-gaji";
    }



}
