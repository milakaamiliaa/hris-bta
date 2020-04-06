package bta.hris.controller;

import bta.hris.model.GolonganModel;
import bta.hris.model.PresensiModel;
import bta.hris.model.UserModel;
import bta.hris.service.GajiService;
import bta.hris.service.GolonganService;
import bta.hris.service.PresensiService;
import bta.hris.service.UserService;
import org.postgresql.jdbc2.optional.SimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class GajiController{


    @Autowired
    private GajiService gajiService;


    @Autowired
    private UserService userService;




    @RequestMapping(value="/gaji", method = RequestMethod.GET)
    public String daftarGaji(Model model){
        UserModel user = userService.getByNip(SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("daftarGaji", gajiService.getAllGajiByNip(user.getNip()));
        model.addAttribute("allGaji", gajiService.getAllGaji());
        model.addAttribute("isPengajar", user.getRole().getNama().equals("Pengajar"));
        model.addAttribute("isDirektur", user.getRole().getNama().equals("Direktur"));
        return "daftar-gaji";
    }



}
