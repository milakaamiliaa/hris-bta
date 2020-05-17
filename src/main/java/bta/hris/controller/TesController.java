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

import java.time.LocalDate;
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
        List<SoalModel> listSoal = PaketSoalService.getPaketSoalByIdPaket(Long.valueOf(1)).getListSoal();
        HasilTesModel hasilTes = new HasilTesModel();
        hasilTes.setStartedAt(LocalDate.now());
        model.addAttribute("listSoal", listSoal);
        model.addAttribute("hasilTes", hasilTes);
        return "tes-psikotes";
    }

    @RequestMapping(value = "/calonpengajar/tes-psikotes", method = RequestMethod.POST)
    public String submitTes(@ModelAttribute HasilTesModel hasilTes, Model model, RedirectAttributes redirect) {
        HasilTesModel hasil = new HasilTesModel();
        hasil.setFinishedAt(LocalDate.now());
        hasil.setCalonPengajar(hasilTes.getCalonPengajar());
        hasil.setListJawaban(hasilTes.getListJawaban());
        hasil.setStartedAt(hasilTes.getStartedAt());

        Integer nilai = 0;
        for (JawabanModel jawaban : hasilTes.getListJawaban()){
            if (jawaban.getIsCorrect()){
                nilai += 1;
            }
        }nilai = (nilai/(hasilTes.getListJawaban().size())) * 100;
        hasil.setNilai(nilai);

        return "redirect:/rekrutmen/paketsoal/detail/";
    }
}


