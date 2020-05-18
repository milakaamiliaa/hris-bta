package bta.hris.controller;

import bta.hris.model.*;
import bta.hris.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class PageController {
    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    CalonPengajarService calonPengajarService;

    @Autowired
    CabangService cabangService;

    @Autowired
    GajiService gajiService;


    @RequestMapping("/")
    public String home (Model model) {
        model.addAttribute("listRole", roleService.getAllRole());

        try{
            UserDetails loggedIn = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(userService.getByNip(loggedIn.getUsername()).getRole().getNama().equals("CALON PENGAJAR")){
                CalonPengajarModel calonPengajar = calonPengajarService.getCalonByUsername(loggedIn.getUsername());
                LocalDate deadline = calonPengajar.getTesDeadline();
                Month bulanDeadline = deadline.getMonth();

                model.addAttribute("calonPengajar", calonPengajar);
                model.addAttribute("bulanDeadline", bulanDeadline);
                return "beranda-calonPengajar";
            }
            else if(userService.getByNip(loggedIn.getUsername()).getRole().getNama().equals("STAF CABANG")){
                UserModel userModel = userService.getByNip(loggedIn.getUsername());
                Optional<CabangModel> cabangModelOpt = cabangService.getCabangByStafCabang(userModel);
                CabangModel cabangModel = cabangModelOpt.get();
                LocalDate periode = LocalDate.now().minusMonths(1);
                GajiModel gajiCabang = gajiService.getGajiCabangMonthly(cabangModel,periode);
                Optional<GajiModel> gajiCabangMonthOpt = gajiService.getGajiByIdGaji(gajiCabang.getIdGaji());
                GajiModel gajiModel = gajiCabangMonthOpt.get();
                List<GajiModel> allGajiByPengajar = gajiService.getAllGajiPengajarCabangMonthly(cabangModel, periode);

                model.addAttribute("allGajiByPengajar", allGajiByPengajar);
                model.addAttribute("cabangModel", cabangModel);
                model.addAttribute("gajiModel", gajiModel);
                model.addAttribute("userModel", userModel);
                return "beranda-stafCabang";
            }
            else{
                return "home";
            }
        }

        catch(Exception e){
            return "home fix";
        }
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }


    @RequestMapping("/tabel")
    public String table() {
        return "tables";
    }
}
