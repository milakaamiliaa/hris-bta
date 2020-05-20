package bta.hris.controller;


import bta.hris.model.*;
import bta.hris.service.*;

import bta.hris.model.CalonPengajarModel;
import bta.hris.service.CalonPengajarService;
import bta.hris.service.RoleService;
import bta.hris.service.UserService;

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


        //try{
            UserDetails loggedIn = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(userService.getByNip(loggedIn.getUsername()).getRole().getNama().equals("CALON PENGAJAR")){
                CalonPengajarModel calonPengajar = calonPengajarService.getCalonByUsername(loggedIn.getUsername());
                LocalDate deadline = calonPengajar.getTesDeadline();
                Month bulanDeadline = deadline.getMonth();
                LocalDate currentDate = LocalDate.now();

                model.addAttribute("deadlineDate", deadline);
                model.addAttribute("currentDate", currentDate);
                model.addAttribute("calonPengajar", calonPengajar);
                model.addAttribute("bulanDeadline", bulanDeadline);
                if(calonPengajar.getNilaiPsikotes() != null && calonPengajar.getNilaiMataPelajaran() != null){
                    return "beranda-calon-setelah-tes";
                }return "beranda-calonPengajar"; 
            }
            else if(userService.getByNip(loggedIn.getUsername()).getRole().getNama().equals("STAF CABANG")){
                UserModel userModel = userService.getByNip(loggedIn.getUsername());
                Optional<CabangModel> cabangModelOpt = cabangService.getCabangByStafCabang(userModel);
                CabangModel cabangModel = cabangModelOpt.get();
                LocalDate periode = LocalDate.now().minusMonths(1);
                CabangDataModel cabangData = cabangDataService.getCabangDataByCabangAndCreatedAt(cabangModel, periode);
                int totalPayroll = cabangDataService.calculateTotalPayroll(cabangData);
                GajiModel gajiCabang = gajiService.getGajiCabangMonthly(cabangModel,periode);
                Optional<GajiModel> gajiCabangMonthOpt = gajiService.getGajiByIdGaji(gajiCabang.getIdGaji());
                GajiModel gajiModel = gajiCabangMonthOpt.get();

                /* Mengambil daftar pengajar beserta Total Sesi Mengajar */
                List<PresensiModel> presensiByCabangData = cabangData.getListPresensi();
                ArrayList<UserModel> pegawaiList = new ArrayList<>();
                ArrayList<Long> totalsesiPegawai = new ArrayList<>();
                ArrayList<UserModel> pegawaiListSorted = new ArrayList<>();
                ArrayList<Long> totalsesiPegawaiSorted = new ArrayList<>();

                for (PresensiModel presensi : presensiByCabangData){
                    if (pegawaiList.contains(presensi.getPegawai()) == false){
                        pegawaiList.add(presensi.getPegawai());
                        if (presensi.getSesiTambahan() != null){
                            totalsesiPegawai.add(presensi.getSesiMengajar()+presensi.getSesiTambahan());
                        } else {
                            totalsesiPegawai.add(presensi.getSesiMengajar());
                        }

                    } else {
                        int index = pegawaiList.indexOf(presensi.getPegawai());
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

                Long pertama = totalsesiPegawai.get(0);
                for (int i=1; i<totalsesiPegawai.size();i++){
                    if (totalsesiPegawai.get(i)>pertama){
                        totalsesiPegawaiSorted.add(totalsesiPegawai.get(i));
                        pegawaiListSorted.add(pegawaiList.get(i));
                    }
                    else {
                        int index = totalsesiPegawai.indexOf(pertama);
                        UserModel pegawaiAtIndex = pegawaiList.get(index);
                        totalsesiPegawaiSorted.add(pertama);
                        pegawaiListSorted.add(pegawaiAtIndex);
                        pertama = totalsesiPegawai.get(i);
                    }
                }
                totalsesiPegawaiSorted.add(pertama);
                int index = totalsesiPegawai.indexOf(pertama);
                UserModel pegawaiAtIndex = pegawaiList.get(index);
                pegawaiListSorted.add(pegawaiAtIndex);

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

               /* if (listCabangData.size()<5){
                    model.addAttribute("dataNotEnough", "Data belum mencukupi untuk memunculkan grafik " +
                            "rasio efisiensi cabang");
                }*/
                model.addAttribute("pegawaiList", pegawaiListSorted);
                model.addAttribute("totalSesiPegawai", totalsesiPegawaiSorted);
                model.addAttribute("cabangData", cabangData);
                model.addAttribute("totalPayroll", totalPayroll);
                model.addAttribute("gajiModel", gajiModel);
                model.addAttribute("userModel", userModel);
                model.addAttribute("periodeString", periodeString);
                model.addAttribute("rasioString", rasioString);

                return "beranda-stafCabang";
            }
            else{
                return "home";
            }
        //}

/*        catch(Exception e){
            return "Home fix";
        }*/
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
