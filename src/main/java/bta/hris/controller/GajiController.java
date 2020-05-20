package bta.hris.controller;

import bta.hris.model.*;
import bta.hris.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
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

    @Autowired
    private CabangDataService cabangDataService;




    @RequestMapping(value="/gaji", method = RequestMethod.GET)
    public String daftarGaji(Model model){
        UserModel user = userService.getByNip(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user.getRole().getNama().equalsIgnoreCase("STAF CABANG")) {
            Optional<CabangModel> cabangModelOpt = cabangService.getCabangByStafCabang(user);
            CabangModel cabangModel = cabangModelOpt.get();
            LocalDate periode = LocalDate.now().minusMonths(1);
            CabangDataModel cabangData = cabangDataService.getCabangDataByCabangAndCreatedAt(cabangModel, periode);

            /* Mengambil daftar pengajar beserta Total Sesi Mengajar */
            List<PresensiModel> presensiByCabangData = cabangData.getListPresensi();
            ArrayList<UserModel> pegawaiList = new ArrayList<>();
            ArrayList<Long> totalsesiPegawai = new ArrayList<>();
            ArrayList<Integer> totalGajiList = new ArrayList<>();

            for (PresensiModel presensi : presensiByCabangData){
                if (pegawaiList.contains(presensi.getPegawai()) == false){
                    Float nominal = presensi.getNominal();
                    Integer nominalInt = Math.round(nominal);
                    pegawaiList.add(presensi.getPegawai());
                    totalGajiList.add(nominalInt);
                    if (presensi.getSesiTambahan() != null){
                        totalsesiPegawai.add(presensi.getSesiMengajar()+presensi.getSesiTambahan());
                    } else {
                        totalsesiPegawai.add(presensi.getSesiMengajar());
                    }

                } else {
                    int index = pegawaiList.indexOf(presensi.getPegawai());

                    Float nominal = presensi.getNominal();
                    Integer nominalInt = Math.round(nominal);
                    Integer gaji = totalGajiList.get(index);
                    gaji += nominalInt;
                    totalGajiList.set(index, gaji);

                    Long sesi = totalsesiPegawai.get(index);
                    if (presensi.getSesiTambahan() != null){
                        sesi += presensi.getSesiMengajar()+presensi.getSesiTambahan();
                    }
                    else {
                        sesi += presensi.getSesiMengajar();
                    }
                    totalsesiPegawai.set(index, sesi);
                }
            }

            model.addAttribute("pegawaiList", pegawaiList);
            model.addAttribute("totalSesiPegawai", totalsesiPegawai);
            model.addAttribute("totalGajiList", totalGajiList);
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

    @RequestMapping(value = "/gaji/{nip}", method = RequestMethod.GET)
    public String detailGajiCabang(@PathVariable String nip, Model model) {
        UserDetails loggedIn = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel user = userService.getByNip(loggedIn.getUsername());
        UserModel pegawai = userService.getByNip(nip);
        Optional<CabangModel> cabangModelOpt = cabangService.getCabangByStafCabang(user);
        CabangModel cabangModel = cabangModelOpt.get();
        LocalDate periode = LocalDate.now().minusMonths(1);
        CabangDataModel cabangData = cabangDataService.getCabangDataByCabangAndCreatedAt(cabangModel, periode);

        /* Mengambil data pengajar beserta Presensi */
        List<PresensiModel> presensiByCabangData = cabangData.getListPresensi();
        List<PresensiModel> presensiPengajarList = new ArrayList<>();
        Long jumlahSesi = Long.valueOf(0);

        for (PresensiModel presensi : presensiByCabangData){
            if (presensi.getPegawai().getNip().equalsIgnoreCase(nip)){
                presensiPengajarList.add(presensi);
            }
        }

        for (PresensiModel presensi : presensiPengajarList){
            if (presensi.getSesiTambahan()==null){
                jumlahSesi += presensi.getSesiMengajar();
                presensi.setSesiTambahan(Long.valueOf(0));
            }
            else {
                jumlahSesi += presensi.getSesiMengajar()+presensi.getSesiTambahan();
            }
        }

        model.addAttribute("cabangData", cabangData);
        model.addAttribute("jumlahSesi", jumlahSesi);
        model.addAttribute("pegawai", pegawai);
        model.addAttribute("presensiPengajarList", presensiPengajarList);
        return "detail-gaji-pengajar-cabang";

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