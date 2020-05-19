package bta.hris.controller;

import bta.hris.model.CabangModel;
import bta.hris.model.GajiModel;
import bta.hris.model.PresensiModel;
import bta.hris.model.UserModel;
import bta.hris.service.CabangService;
import bta.hris.service.GajiService;
import bta.hris.service.PresensiService;
import bta.hris.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
public class GajiController{


    @Autowired
    private GajiService gajiService;


    @Autowired
    private UserService userService;

    @Autowired
    private PresensiService presensiService;

    @Autowired
    private CabangService cabangService;




    @RequestMapping(value="/gaji", method = RequestMethod.GET)
    public String daftarGaji(Model model){
        UserModel user = userService.getByNip(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user.getRole().getNama().equalsIgnoreCase("STAF CABANG")) {
            Optional<CabangModel> cabangModelOpt = cabangService.getCabangByStafCabang(user);
            CabangModel cabangModel = cabangModelOpt.get();
            LocalDate periode = LocalDate.now().minusMonths(1);
            List<GajiModel> allGajiByPengajar = gajiService.getAllGajiPengajarCabangMonthly(cabangModel, periode);
            model.addAttribute("allGajiByPengajar", allGajiByPengajar);
            model.addAttribute("cabangModel", cabangModel);
            model.addAttribute("periode", periode);

            return "daftar-presensi-cabang";

        }

        else {
            model.addAttribute("daftarGaji", gajiService.getAllGajiByNip(user.getNip()));
            model.addAttribute("allGaji", gajiService.getAllGaji());

            model.addAttribute("isPengajar", user.getRole().getNama().equalsIgnoreCase("Pengajar"));
            model.addAttribute("isDirektur", user.getRole().getNama().equalsIgnoreCase("Direktur"));
            return "daftar-gaji";
        }

    }

    @RequestMapping(value = "/gaji/detail/{idGaji}", method = RequestMethod.GET)
    public String detailGaji(@PathVariable Long idGaji, Model model){
        UserModel user = userService.getByNip(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user.getRole().getNama().equalsIgnoreCase("STAF CABANG")) {
            Optional<CabangModel> cabangModelOpt = cabangService.getCabangByStafCabang(user);
            CabangModel cabangModel = cabangModelOpt.get();
            GajiModel gajiModel = gajiService.getGajiByIdGaji(idGaji).get();
            UserModel pegawai = gajiModel.getPegawai();
            List<PresensiModel> presensiList = presensiService.getAllPresensiByCabangAndPegawaiAndStatus
                    (cabangModel, pegawai, "disetujui");

            for (PresensiModel presensi : presensiList){
                if (presensi.getSesiTambahan()==null){
                    presensi.setSesiTambahan(Long.valueOf(0));
                }
            }


            model.addAttribute("gajiModel", gajiModel);
            model.addAttribute("pegawai", pegawai);
            model.addAttribute("presensiList", presensiList);
            return "detail-gaji-pengajar-cabang";


        }

        else {
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

            String periode = (String.valueOf(gaji.getPeriode().getMonth().getDisplayName(TextStyle.SHORT, Locale.US))) + " "
                    + (String.valueOf(gaji.getPeriode().getYear()));

            List<PresensiModel> presensi = presensiService.getAllPresensiByKodeGaji(kodeGaji, gaji.getPegawai().getNip());
            model.addAttribute("isPengajar", user.getRole().getNama().equalsIgnoreCase("Pengajar"));
            model.addAttribute("isDirektur", user.getRole().getNama().equalsIgnoreCase("Direktur"));
            model.addAttribute("periode", periode);
            model.addAttribute("presensiByKodeGaji", presensi);
            model.addAttribute("gaji", gaji);
            return "detail-gaji-pengajar";

        }

    }


    @RequestMapping(value = "/gaji/setujui/{idGaji}", method = RequestMethod.POST)
    public String setujuiGaji(@PathVariable Long idGaji, @ModelAttribute GajiModel gaji, Model model) {
        gaji = gajiService.getGajiByIdGaji(idGaji).get();
        gaji.setStatus("disetujui");
        GajiModel newGaji = gajiService.approveGaji(gaji);

        model.addAttribute("gaji", newGaji);

        return "redirect:/gaji";
    }


    @RequestMapping(value = "/gaji/paid/{idGaji}", method = RequestMethod.POST)
    public String eksekusiGaji(@PathVariable Long idGaji, @ModelAttribute GajiModel gaji, Model model) {
        gaji = gajiService.getGajiByIdGaji(idGaji).get();
        gaji.setStatus("sudah dibayar");

        GajiModel paidGaji = gajiService.paidGaji(gaji);

        return "redirect:/gaji";
    }

    @RequestMapping(value = "/gaji/tolak/{idGaji}", method = RequestMethod.POST)
    public String tolakGaji(@PathVariable Long idGaji, @ModelAttribute GajiModel gaji, Model model) {
        gaji = gajiService.getGajiByIdGaji(idGaji).get();
        gaji.setStatus("ditolak");

        GajiModel rejectedGaji = gajiService.rejectGaji(gaji);

        return "redirect:/gaji";
    }


}