package bta.hris.controller;

import bta.hris.model.CalonPengajarModel;
import bta.hris.model.GolonganModel;
import bta.hris.model.RoleModel;
import bta.hris.model.UserModel;
import bta.hris.service.CalonPengajarService;
import bta.hris.service.GolonganService;
import bta.hris.service.RoleService;
import bta.hris.service.UserService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

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

 
    @RequestMapping(value = "/calonpengajar/daftar", method = RequestMethod.GET)
    public String daftarCalonPengajar(Model model){
        List<CalonPengajarModel> listCalon = calonPengajarService.getAllCalon();
        model.addAttribute("listCalon", listCalon);
        return "daftar-calonpengajar";
    }

    @RequestMapping(value = "/calonpengajar/detail/{idCalon}", method = RequestMethod.GET)
    public String detailCalonPengajar(@PathVariable String idCalon, Model model){
        CalonPengajarModel calon = calonPengajarService.getCalonById(idCalon);
        model.addAttribute("calon", calon);
        return "detail-calonpengajar";
    }

    @RequestMapping(value = "calonpengajar/rekrut/{idCalon}", method = RequestMethod.POST)
    public String rekrutCalon(@ModelAttribute CalonPengajarModel calon, Model model){
        System.out.print(calon);
        System.out.print("HERE");
        CalonPengajarModel newPengajar = calonPengajarService.rekrutCalon(calonPengajarService.getCalonById(calon.getIdCalon()));
        model.addAttribute("newPengajar", newPengajar);
        return detailCalonPengajar(calon.getIdCalon(), model);
    }

    @RequestMapping(value = "calonpengajar/tolak/{idCalon}", method = RequestMethod.POST)
    public String tolakCAlon(@PathVariable String idCalon, @ModelAttribute CalonPengajarModel calon, Model model){
        CalonPengajarModel targetCalon = calonPengajarService.tolakCalon(calon);
        model.addAttribute("calon", targetCalon);
        return detailCalonPengajar(idCalon, model);
    }

    @RequestMapping(value = "calonpengajar/undang/{idCalon}", method = RequestMethod.POST)
    public String undangCalon(@PathVariable String idCalon, @ModelAttribute CalonPengajarModel calon, Model model){
        CalonPengajarModel targetCalon = calonPengajarService.undangCalon(calon);
        model.addAttribute("calon", targetCalon);
        return detailCalonPengajar(idCalon, model);
    }

    @RequestMapping(value = "calonpengajar/hapus/{idCalon}", method = RequestMethod.POST)
    public String hapusCalon(@PathVariable String idCalon, @ModelAttribute CalonPengajarModel calon, Model model){
        CalonPengajarModel targetCalon = calonPengajarService.getCalonById(calon.getIdCalon());
        calonPengajarService.hapusCalon(targetCalon);
        return daftarCalonPengajar(model);
    }

    @RequestMapping(value = "/registrasi", method = RequestMethod.GET)
    public String addCalonPengajarForm (Model model) {
        CalonPengajarModel newCalonPengajar = new CalonPengajarModel();
        model.addAttribute("newCalonPengajar", newCalonPengajar);
        return "form-registrasi-pengajar";
    }

    @RequestMapping(value = "/registrasi", method = RequestMethod.POST)
    public String addCalonPengajarSubmit (CalonPengajarModel calonPengajar, Model model, RedirectAttributes redirect) {

        if (usernameisValid(calonPengajar)){
            if (calonPengajar.getTglLahir().compareTo(LocalDate.now())<=0){
                LocalDate now = LocalDate.now();
                RoleModel role = roleService.getRoleById(Long.valueOf(4));
                Optional<GolonganModel> golonganOpt = golonganService.getGolonganByIdGolongan(Long.valueOf(99));
                GolonganModel golongan = golonganOpt.get();

                calonPengajar.setStatus("Belum Mengerjakan Tes");
                calonPengajar.setCreatedAt(LocalDate.now());
                calonPengajar.setTesDeadline(LocalDate.now().plusDays(7));
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
                user.setGolongan(golongan);
                userService.addUser(user);

                return "redirect:/beranda/" + calonPengajar.getIdCalon();
            }
            else {
                redirect.addFlashAttribute("tglLahirTidakValid", "Tanggal lahir tidak valid, silahkan isi sesuai tanggal lahir Anda");
                return "redirect:/registrasi";
            }

        }
        else {
            redirect.addFlashAttribute("usernameTidakValid", "Username tidak tersedia, silahkan isi dengan username yang lain");
            return "redirect:/registrasi";
        }

    }


    public boolean usernameisValid(CalonPengajarModel calonPengajarCheck){
        List<CalonPengajarModel> calonPengajarList = calonPengajarService.findAllCalonPengajar();
        boolean status = true;
        for (CalonPengajarModel calonPengajar : calonPengajarList) {
            if ((calonPengajar.getUsername()).equalsIgnoreCase(calonPengajarCheck.getUsername())){
                status = false;
                break;
            }
        }
        return status;
    }


    @RequestMapping(value = "/beranda/{idCalon}", method = RequestMethod.GET)
    public String BerandaCalonPengajar (@PathVariable Long idCalon, Model model) {
        Optional<CalonPengajarModel> calonPengajarModelnOption = calonPengajarService.findById(idCalon);
        CalonPengajarModel calonPengajar = calonPengajarModelnOption.get();
        LocalDate deadline = calonPengajar.getTesDeadline();
        Month bulanDeadline = deadline.getMonth();

        model.addAttribute("calonPengajar", calonPengajar);
        model.addAttribute("bulanDeadline", bulanDeadline);
        return "beranda-calonPengajar";
    }

}