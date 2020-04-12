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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public String tambahPresensiForm(Model model){
        LocalDate localDate = LocalDate.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String formattedString = localDate.format(formatter);
        List<CabangModel> listCabang = cabangService.getCabangList();
        List<CabangModel> listActiveCabang = new ArrayList <CabangModel>();
        for (CabangModel cabang : listCabang){
            if (cabang.isActive()){
                listActiveCabang.add(cabang);
            }
        }

        model.addAttribute("presensi", new PresensiModel());
        model.addAttribute("cabangList", listActiveCabang);
        model.addAttribute("localDate", formattedString);

        return "form-tambah-presensi";
    }

    @RequestMapping(value = "/presensi/tambah", method = RequestMethod.POST)
    public String tambahPresensiSubmit(@ModelAttribute PresensiModel presensi, Model model){


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String nip = ((UserDetails) principal).getUsername();

        presensi.setTanggalPresensi(LocalDate.now());
        PresensiModel addedPresensi = presensiService.addPresensi(presensi,nip);
        List<CabangModel> listActiveCabang = new ArrayList <CabangModel>();
        List<CabangModel> listCabang = cabangService.getCabangList();
        for (CabangModel cabang : listCabang){
            if (cabang.isActive()){
                listActiveCabang.add(cabang);
            }
        }

        model.addAttribute("addedPresensi", addedPresensi);
        model.addAttribute("presensi", new PresensiModel());

        model.addAttribute("cabangList", listActiveCabang);
        model.addAttribute("localDate", LocalDate.now());
        return "redirect:/presensi";
    }

    @RequestMapping(value = "presensi/ubah/{idPresensi}", method = RequestMethod.GET)
    public String ubahPresensiForm(@PathVariable Long idPresensi, Model model) {
        PresensiModel existingPresensi = presensiService.getPresensiById(idPresensi);

        List<CabangModel> listCabang = cabangService.getCabangList();

        LocalDate localDate = LocalDate.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String formattedString = localDate.format(formatter);

        model.addAttribute("localDate", formattedString);
        model.addAttribute("presensi", existingPresensi);
        model.addAttribute("listCabang", listCabang);

        return "form-ubah-presensi";
    }

    @RequestMapping(value = "presensi/ubah/{idPresensi}", method = RequestMethod.POST)
    public String ubahPresensiSubmit(@PathVariable Long idPresensi, @ModelAttribute PresensiModel presensi, Model model) {

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
    public String setujuiPresensiForm(@PathVariable Long idPresensi, Model model) {
        PresensiModel presensi = presensiService.getPresensiById(idPresensi);

        model.addAttribute("presensi", presensi);

        return "form-setujui-presensi";
    }

    @RequestMapping(value = "presensi/setujui/{idPresensi}", method = RequestMethod.POST)
    public String setujuiPresensi(@PathVariable Long idPresensi, @ModelAttribute PresensiModel presensi, Model model, RedirectAttributes redirect) {
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
        Long sesiTambahan;
        if(newPresensi.getSesiTambahan() == null){
            sesiTambahan = (long)0;
        }
        else{
            sesiTambahan = newPresensi.getSesiTambahan();

        }
        Long totalSesi = newPresensi.getSesiMengajar() + sesiTambahan;
        List<GajiModel> listGaji = gajiService.getAllGajiByNip(presensi.getPegawai().getNip());
        float pajak = newPresensi.getPegawai().getGolongan().getPajak() / 100;

       float hitungGaji = newPresensi.getPegawai().getGolongan().getRate() * pajak * totalSesi;
       float totalGaji = hitungGaji + newPresensi.getUangKonsum();

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String tanggalPresensi = presensi.getTanggalPresensi().format(formatter);
        redirect.addFlashAttribute("alert", "Presensi dari " + presensi.getPegawai().getNama() + " pada tanggal " +
               tanggalPresensi + " berhasil disetujui.");

        return "redirect:/presensi/kelola";
    }

    @RequestMapping(value = "/presensi/tolak/{idPresensi}", method = RequestMethod.POST)
    public String tolakPresensi(@PathVariable Long idPresensi, @ModelAttribute PresensiModel presensi, Model model) {
        PresensiModel target = presensiService.getPresensiById(idPresensi);

        target.setStatus("ditolak");
        PresensiModel rejectedPresensi = presensiService.rejectPresensi(target);

        return "redirect:/presensi/kelola";
    }
}
