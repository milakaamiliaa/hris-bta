package bta.hris.controller;

import bta.hris.model.*;
import bta.hris.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TesController {
    @Autowired
    PaketSoalService PaketSoalService;

    @Autowired
    SoalService soalService;

    @Autowired
    JawabanService jawabanService;

    @RequestMapping(value = "/calonpengajar/aturan-psikotes", method = RequestMethod.GET)
    public String aturanPsikotes(Model model) {
        return "aturan-tes-psikotes";
    }

    @RequestMapping(value = "/calonpengajar/tes-psikotes", method = RequestMethod.GET)
    public String tesPsikotes(Model model) {
        List<SoalModel> listSoal = PaketSoalService.getPaketById(Long.valueOf(1)).getListSoal();
        model.addAttribute("jumlahSoal", listSoal.size());
        model.addAttribute("listSoal", listSoal);
        model.addAttribute("soal", new SoalModel());
        model.addAttribute("jawaban", new JawabanModel());

        return "tes-psikotes";
    }

    @RequestMapping(value = "/calonpengajar/aturan-mata-pelajaran", method = RequestMethod.GET)
    public String aturanMataPelajaran(Model model) {
        return "aturan-tes-matpel";
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

