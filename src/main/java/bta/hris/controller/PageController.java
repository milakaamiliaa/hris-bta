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
import java.time.format.TextStyle;
import java.util.*;

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

    @Autowired
    CabangDataService cabangDataService;


    @RequestMapping("/")
    public String home (Model model) {
        model.addAttribute("listRole", roleService.getAllRole());


        try{
            UserDetails loggedIn = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println(userService.getByNip(loggedIn.getUsername()).getRole().getNama());
            System.out.println(userService.getByNip(loggedIn.getUsername()).getRole().getNama());
            System.out.println(userService.getByNip(loggedIn.getUsername()).getRole().getNama());
            System.out.println(userService.getByNip(loggedIn.getUsername()).getRole().getNama());
            System.out.println(userService.getByNip(loggedIn.getUsername()).getRole().getNama());
            System.out.println(userService.getByNip(loggedIn.getUsername()).getRole().getNama());
            System.out.println(userService.getByNip(loggedIn.getUsername()).getRole().getNama());

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

                // ==== Keperluan Models Attribute untuk grafik ====
                    // List CabangData yang akan dipakai (5 bulan ke belakang dari bulan sekarang).
                List<CabangDataModel> listCabangData = cabangDataService.getCabangDatasForXPeriodBeforeNow(5, cabangModel);

                    // Periode dan Rasio (utk label dan data grafik): attempt for "aaa,bbb" format to be later parsed in front-end
                String periodeString = "";
                String rasioString = "";

                for (CabangDataModel c : listCabangData) {
                    periodeString += periodParser(c.getCreatedAt());
                    periodeString += ",";

                    rasioString += Float.toString(c.getRasio());
                    rasioString += ",";
                }

                periodeString = periodeString.substring(0, periodeString.length()-1);
                rasioString = rasioString.substring(0, rasioString.length()-1);

                model.addAttribute("periodeString", periodeString);
                model.addAttribute("rasioString", rasioString);

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

    public String periodParser(LocalDate date) {
        String month = date.getMonth().getDisplayName(TextStyle.SHORT, Locale.US);
        String year = Integer.toString(date.getYear());

        return month + " " + year;
    }
}
