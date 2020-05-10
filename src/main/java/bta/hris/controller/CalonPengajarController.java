package bta.hris.controller;

import bta.hris.model.*;
import bta.hris.repository.JawabanDB;
import bta.hris.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CalonPengajarController {
    @Autowired
    CalonPengajarService calonPengajarService;

    @Autowired
    GolonganService golonganService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    SoalService soalService;

    @Autowired
    JawabanService jawabanService;

    @RequestMapping(value = "/calonpengajar", method = RequestMethod.GET)
    public String daftarCalonPengajar(Model model) {
        List<CalonPengajarModel> listCalon = calonPengajarService.getAllCalon();
        model.addAttribute("listCalon", listCalon);
        return "daftar-calonpengajar";
    }

    @RequestMapping(value = "/calonpengajar/detail/{idCalon}", method = RequestMethod.GET)
    public String detailCalonPengajar(@PathVariable String idCalon, Model model) {
        CalonPengajarModel calon = calonPengajarService.getCalonById(idCalon);
        model.addAttribute("calon", calon);
        return "detail-calonpengajar";
    }

    @RequestMapping(value = "calonpengajar/rekrut/{idCalon}", method = RequestMethod.POST)
    public String terimaPelamar(@ModelAttribute CalonPengajarModel calon, Model model) {
        CalonPengajarModel newPengajar = calonPengajarService.rekrutCalon(calonPengajarService.getCalonById(calon.getIdCalon()));
        model.addAttribute("newPengajar", newPengajar);
        return detailCalonPengajar(calon.getIdCalon(), model);
    }

    @RequestMapping(value = "calonpengajar/tolak/{idCalon}", method = RequestMethod.POST)
    public String tolakPelamar(@PathVariable String idCalon, @ModelAttribute CalonPengajarModel calon, Model model) {
        CalonPengajarModel targetCalon = calonPengajarService.tolakCalon(calon);
        model.addAttribute("calon", targetCalon);
        return detailCalonPengajar(idCalon, model);
    }

    @RequestMapping(value = "calonpengajar/undang/{idCalon}", method = RequestMethod.POST)
    public String undangCalon(@PathVariable String idCalon, @ModelAttribute CalonPengajarModel calon, Model model) {
        CalonPengajarModel targetCalon = calonPengajarService.undangCalon(calon);
        model.addAttribute("calon", targetCalon);
        return detailCalonPengajar(idCalon, model);
    }

    @RequestMapping(value = "calonpengajar/hapus/{idCalon}", method = RequestMethod.POST)
    public String hapusCalon(@PathVariable String idCalon, @ModelAttribute CalonPengajarModel calon, Model model, RedirectAttributes redirect) {
        CalonPengajarModel targetCalon = calonPengajarService.getCalonById(calon.getIdCalon());
        calonPengajarService.hapusCalon(targetCalon);
        redirect.addFlashAttribute("alertHapus", "Calon pengajar bernama " + targetCalon.getNama() + " berhasil dihapus.");
        return daftarCalonPengajar(model);
    }

    @RequestMapping(value = "/registrasi", method = RequestMethod.GET)
    public String tambahCalonPengajarForm(Model model) {
        CalonPengajarModel newCalonPengajar = new CalonPengajarModel();
        model.addAttribute("newCalonPengajar", newCalonPengajar);
        return "form-registrasi-pengajar";
    }

    @RequestMapping(value = "/registrasi", method = RequestMethod.POST)
    public String tambahCalonPengajarSubmit(CalonPengajarModel calonPengajar, Model model, RedirectAttributes redirect) {

        if (usernameisValid(calonPengajar)) {
            if (calonPengajar.getTglLahir().compareTo(LocalDate.now().minusYears(15)) <= 0) {
                RoleModel role = roleService.getRoleById(Long.valueOf(5));

                calonPengajar.setStatus("Belum Mengerjakan Tes");
                calonPengajar.setCreatedAt(LocalDate.now());
                calonPengajar.setTesDeadline(LocalDate.now().plusDays(7));
                calonPengajar.setUpdatedAt(LocalDate.now());
                calonPengajarService.createCalonPengajar(calonPengajar);

                UserModel user = new UserModel();
                user.setNip(calonPengajar.getUsername());
                user.setPassword(calonPengajar.getPassword());
                user.setMataPelajaran(calonPengajar.getMataPelajaran());
                user.setTglLahir(calonPengajar.getTglLahir());
                user.setAlamat(calonPengajar.getAlamat());
                user.setNama(calonPengajar.getNama());
                user.setNoTelp(calonPengajar.getNoTelp());
                user.setPassword(calonPengajar.getPassword());
                user.setEmail(calonPengajar.getEmail());
                user.setCreatedAt(LocalDate.now());
                user.setRole(role);
                user.setActive(true);

                userService.addUser(user);

                return "redirect:/beranda/" + calonPengajar.getIdCalon();
            } else {
                redirect.addFlashAttribute("tglLahirTidakValid", "Tanggal lahir tidak valid, silahkan isi sesuai tanggal lahir Anda");
                return "redirect:/registrasi";
            }

        } else {
            redirect.addFlashAttribute("usernameTidakValid", "Username tidak tersedia, silahkan isi dengan username yang lain");
            return "redirect:/registrasi";
        }

    }


    public boolean usernameisValid(CalonPengajarModel calonPengajarCheck) {
        List<CalonPengajarModel> calonPengajarList = calonPengajarService.getAllCalon();
        boolean status = true;
        for (CalonPengajarModel calonPengajar : calonPengajarList) {
            if ((calonPengajar.getUsername()).equalsIgnoreCase(calonPengajarCheck.getUsername())) {
                status = false;
                break;
            }
        }
        return status;
    }

    @RequestMapping(value = "/calonpengajar/aturan-psikotes", method = RequestMethod.GET)
    public String aturanPsikotes(Model model) {
        return "aturan-tes-psikotes";
    }

    @RequestMapping(value = "/calonpengajar/tes-psikotes", method = RequestMethod.GET)
    public String tesPsikotes(Model model) {
        SoalModel soal = new SoalModel();
        List<SoalModel> listSoal = soalService.getAllSoalByNamaPaketSoal("psikotes");
        List<JawabanModel> listJawaban = jawabanService.getAllJawabanByIdSoal(soal.getIdSoal());
        model.addAttribute("listSoal", listSoal);
        model.addAttribute("listJawaban", listJawaban);
        model.addAttribute("soal", soal);

        return "tes-psikotes";
    }
}


//
//    @RequestMapping(value = "/beranda/{idCalon}", method = RequestMethod.GET)
//    public String berandaCalonPengajar (@PathVariable String idCalon, Model model) {
//        CalonPengajarModel calonPengajar = calonPengajarService.getCalonById(idCalon);
//        LocalDate deadline = calonPengajar.getTesDeadline();
//        Month bulanDeadline = deadline.getMonth();
//
//        model.addAttribute("calonPengajar", calonPengajar);
//        model.addAttribute("bulanDeadline", bulanDeadline);
//        return "beranda-calonPengajar";
//    }

