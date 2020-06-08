package bta.hris.controller;

import bta.hris.model.JawabanModel;
import bta.hris.model.SoalModel;
import bta.hris.service.JawabanService;
import bta.hris.service.SoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class JawabanController {

    @Autowired
    private JawabanService jawabanService;

    @Autowired
    private SoalService soalService;

    @RequestMapping(value = "/jawaban/tambah/{idSoal}", method = RequestMethod.GET)
    public String tambahJawaban(@PathVariable Long idSoal, Model model) {
        SoalModel soal = soalService.getSoalById(idSoal);

        JawabanModel jawaban = new JawabanModel();
        jawaban.setSoal(soal);

        model.addAttribute("jawaban", jawaban);
        return "form-tambah-jawaban";
    }

    @RequestMapping(value = "/jawaban/tambah", method = RequestMethod.POST)
    public String tambahJawabanSubmit(@ModelAttribute JawabanModel jawaban, Model model, RedirectAttributes redirect) {
        JawabanModel target = new JawabanModel();
        target.setSoal(jawaban.getSoal());
        target.setActive(true);
        target.setJawaban(jawaban.getJawaban());
        target.setCorrect(false);

        jawabanService.addJawaban(target);

        redirect.addFlashAttribute("alertHapus", "Jawaban berhasil ditambahkan.");
        return "redirect:/soal/detail/" + target.getSoal().getIdSoal();
    }

    @RequestMapping(value = "/jawaban/ubah/{idJawaban}", method = RequestMethod.GET)
    public String ubahJawaban(@PathVariable Long idJawaban, Model model) {
        JawabanModel targetJawaban = jawabanService.getJawabanById(idJawaban);

        model.addAttribute("jawaban", targetJawaban);
        return "form-ubah-jawaban";
    }

    @RequestMapping(value = "/jawaban/ubah", method = RequestMethod.POST)
    public String ubahJawabanSubmit(@ModelAttribute JawabanModel jawaban, Model model, RedirectAttributes redirect) {
        jawaban.setActive(true);
        JawabanModel updatedJawaban = jawabanService.editJawaban(jawaban);

        redirect.addFlashAttribute("alert", "Jawaban berhasil diubah.");
        return "redirect:/soal/detail/" + updatedJawaban.getSoal().getIdSoal();
    }

    @RequestMapping(value = "/jawaban/hapus/{idJawaban}", method = RequestMethod.POST)
    public String hapusGolongan(@PathVariable Long idJawaban, @ModelAttribute("j") JawabanModel jawaban, Model model,
            RedirectAttributes redirect) {
        JawabanModel targetJawaban = jawabanService.deleteJawaban(jawaban);

        redirect.addFlashAttribute("alertHapus", "Jawaban berhasil dihapus.");
        return "redirect:/soal/detail/" + targetJawaban.getSoal().getIdSoal();
    }
}
