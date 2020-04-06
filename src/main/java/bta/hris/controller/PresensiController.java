package bta.hris.controller;

import bta.hris.model.*;
import bta.hris.service.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.List;

@Controller
public class PresensiController {

    @Autowired
    private PresensiService presensiService;

    @Autowired
    private UserService userService;

    @Autowired
    private CabangService cabangService;

    @Autowired
    private GolonganService golonganService;

    @Autowired
    private GajiService gajiService;


    @RequestMapping(value="/presensi", method = RequestMethod.GET)
    public String daftarPresensi(Model model){
        UserModel user = userService.getByNip(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("allPresensi", presensiService.getAllPresensi());
        model.addAttribute("daftarPresensi", presensiService.getAllPresensiByNip(user.getNip()));
        return "daftar-presensi";
    }

    @RequestMapping(value = "/presensi/tambah", method = RequestMethod.GET)
    public String createPresensiForm(Model model){
        model.addAttribute("presensi", new PresensiModel());
        model.addAttribute("cabangList", cabangService.getCabangList());
        model.addAttribute("localDate", LocalDate.now());

        return "form-tambah-presensi";
    }

    @RequestMapping(value = "/presensi/tambah", method = RequestMethod.POST)
    public String createPresensiSubmit(@ModelAttribute PresensiModel presensi, Model model){


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String nip = ((UserDetails) principal).getUsername();

        presensi.setTanggalPresensi(LocalDate.now());
        PresensiModel addedPresensi = presensiService.addPresensi(presensi,nip);
        List<PresensiModel> allPresensi = presensiService.getAllPresensiByNip(nip);


        model.addAttribute("addedPresensi", addedPresensi);
        model.addAttribute("presensi", new PresensiModel());
        model.addAttribute("cabangList", cabangService.getCabangList());
        model.addAttribute("localDate", LocalDate.now());
        return "redirect:/presensi";
    }

    @RequestMapping(value = "presensi/ubah/{idPresensi}", method = RequestMethod.GET)
    public String updatePresensiForm(@PathVariable Long idPresensi, Model model) {
        PresensiModel existingPresensi = presensiService.getPresensiById(idPresensi);

        List<CabangModel> listCabang = cabangService.getCabangList();

        model.addAttribute("presensi", existingPresensi);
        model.addAttribute("listCabang", listCabang);

        return "form-ubah-presensi";
    }

    @RequestMapping(value = "presensi/ubah/{idPresensi}", method = RequestMethod.POST)
    public String updatePresensiSubmit(@PathVariable Long idPresensi, @ModelAttribute PresensiModel presensi, Model model) {
        PresensiModel newPresensi = presensiService.updatePresensi(presensi);
        model.addAttribute("presensi", newPresensi);
        return "redirect:/presensi";
    }

    @RequestMapping(value = "presensi/kelola", method = RequestMethod.GET)
    public String daftarPengajuanPresensi(Model model) {
        UserModel user = userService.getByNip(SecurityContextHolder.getContext().getAuthentication().getName());
        CabangModel cabang = cabangService.getCabangByStafCabang(user).get();

        List<PresensiModel> daftarPresensi = presensiService.getAllPresensiByCabang(cabang);

        model.addAttribute("daftarPresensi", daftarPresensi);

        return "daftar-presensi-kelola";
    }

    @RequestMapping(value = "presensi/setujui/{idPresensi}", method = RequestMethod.GET)
    public String formSetujuiPresensi(@PathVariable Long idPresensi, Model model) {
        PresensiModel presensi = presensiService.getPresensiById(idPresensi);

        model.addAttribute("presensi", presensi);

        return "form-setujui-presensi";
    }

    @RequestMapping(value = "presensi/setujui/{idPresensi}", method = RequestMethod.POST)
    public String setujuiPresensi(@PathVariable Long idPresensi, @ModelAttribute PresensiModel presensi, Model model) {
        UserModel user = userService.getByNip(SecurityContextHolder.getContext().getAuthentication().getName());

        String month = "";
        if (String.valueOf(presensi.getTanggalPresensi().getMonthValue()).length() == 1) {
            month = "0" + presensi.getTanggalPresensi().getMonthValue();
        }

        else {
            month = String.valueOf(presensi.getTanggalPresensi().getMonthValue());
        }
        String year = String.valueOf(presensi.getTanggalPresensi().getYear()).substring(2,4);
        presensi.setKodeGaji(month + year);
        presensi.setStatus("disetujui");

        PresensiModel newPresensi = presensiService.approvePresensi(presensi);
        Long totalSesi = newPresensi.getSesiMengajar() + newPresensi.getSesiTambahan();
        List<GajiModel> listGaji = gajiService.getAllGajiByNip(presensi.getPegawai().getNip());

       float hitungGaji = newPresensi.getPegawai().getGolongan().getRate() * newPresensi.getPegawai().getGolongan().getPajak() * totalSesi;
       float totalGaji = hitungGaji + newPresensi.getUangKonsum();
       System.out.println(newPresensi.getUangKonsum());
       System.out.println(totalGaji);

        if(listGaji.size() == 0) {
            GajiModel newGaji = new GajiModel();
            newGaji.setPeriode(LocalDate.now());
            newGaji.setPajakGaji(newPresensi.getPegawai().getGolongan().getPajak());
            newGaji.setRateGaji(newPresensi.getPegawai().getGolongan().getRate());
            newGaji.setTotalSesi(totalSesi);
            newGaji.setTotalGaji(totalGaji);
            newGaji.setStatus("pending");
            newGaji.setPegawai(newPresensi.getPegawai());
            gajiService.addGaji(newGaji);
        }
        else {
            for (GajiModel gaji : listGaji) {
                if (gaji.getPeriode().getMonthValue() == presensi.getTanggalPresensi().getMonthValue()) {
                    gaji.setTotalSesi(gaji.getTotalSesi() + totalSesi);
                    gaji.setTotalGaji(gaji.getTotalGaji() + totalGaji);
                    gajiService.updateGaji(gaji);
                    break;
                }
                else{
                    GajiModel newPeriode = new GajiModel();
                    newPeriode.setPeriode(LocalDate.now());
                    newPeriode.setPajakGaji(newPresensi.getPegawai().getGolongan().getPajak());
                    newPeriode.setRateGaji(newPresensi.getPegawai().getGolongan().getRate());
                    newPeriode.setTotalSesi(totalSesi);
                    newPeriode.setTotalGaji(totalGaji);
                    newPeriode.setStatus("pending");
                    newPeriode.setPegawai(newPresensi.getPegawai());
                    gajiService.addGaji(newPeriode);
                }
            }
        }
        model.addAttribute("presensi", newPresensi);

        return "redirect:/presensi/kelola";
    }


}
