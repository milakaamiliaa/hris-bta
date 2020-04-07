package bta.hris.controller;

import bta.hris.model.GajiModel;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.List;

@Controller
public class GajiController{


    @Autowired
    private GajiService gajiService;


    @Autowired
    private UserService userService;

    @Autowired
    private PresensiService presensiService;




    @RequestMapping(value="/gaji", method = RequestMethod.GET)
    public String daftarGaji(Model model){
        UserModel user = userService.getByNip(SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("daftarGaji", gajiService.getAllGajiByNip(user.getNip()));
        model.addAttribute("allGaji", gajiService.getAllGaji());
        model.addAttribute("isPengajar", user.getRole().getNama().equals("Pengajar"));
        model.addAttribute("isDirektur", user.getRole().getNama().equals("Direktur"));
        return "daftar-gaji";
    }

    @RequestMapping(value = "/gaji/detail/{idGaji}", method = RequestMethod.GET)
    public String detailGaji(@PathVariable Long idGaji, Model model){
        UserModel user = userService.getByNip(SecurityContextHolder.getContext().getAuthentication().getName());
        GajiModel gaji = gajiService.getGajiByIdGaji(idGaji).get();
        String month = "";
        if (String.valueOf(gaji.getPeriode().getMonthValue()).length() == 1) {
            month = "0" + gaji.getPeriode().getMonthValue();
        }
        else {
            month = String.valueOf(gaji.getPeriode().getMonthValue());
        }
        String year = String.valueOf(gaji.getPeriode().getYear()).substring(2,4);
       String kodeGaji = month+year;

        List<PresensiModel> presensi = presensiService.getAllPresensiByKodeGaji(kodeGaji);

        model.addAttribute("presensiByKodeGaji", presensi);
        model.addAttribute("gaji", gaji);
        return "detail-gaji-pengajar";
    }

    @RequestMapping(value = "/gaji/setujui/{idGaji}", method = RequestMethod.GET)
    public String getsetujuiGaji(@PathVariable Long idGaji, Model model) {
        GajiModel gaji = gajiService.getGajiByIdGaji(idGaji).get();
        System.out.println(gaji.getStatus());
        model.addAttribute("gaji", gaji);

        return "redirect:/gaji";
    }

    @RequestMapping(value = "/gaji/setujui/{idGaji}", method = RequestMethod.POST)
    public String postsetujuiGaji(@PathVariable Long idGaji, @ModelAttribute GajiModel gaji, Model model) {
        UserModel user = userService.getByNip(SecurityContextHolder.getContext().getAuthentication().getName());
        gaji = gajiService.getGajiByIdGaji(idGaji).get();
        gaji.setStatus("disetujui");
        GajiModel newGaji = gajiService.approveGaji(gaji);

        System.out.println("mASOOOOOOOk");

        model.addAttribute("gaji", newGaji);

        return "redirect:/gaji";
    }



}
