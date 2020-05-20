package bta.hris.controller;

import bta.hris.model.JawabanModel;
import bta.hris.model.PaketSoalModel;
import bta.hris.model.SoalModel;
import bta.hris.service.JawabanService;
import bta.hris.service.PaketSoalService;
import bta.hris.service.SoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class SoalController {

    @Autowired
    private SoalService soalService;

    @Autowired
    private PaketSoalService paketSoalService;

    @Autowired
    private JawabanService jawabanService;

    @RequestMapping(value = "/soal/tambah/{idPaketSoal}", method = RequestMethod.GET)
    public String tambahSoalInitial(@PathVariable Long idPaketSoal, Model model) {
        model.addAttribute("idPaketSoal", idPaketSoal);

        return "form-tambah-soal-initial";
    }

    @RequestMapping(value = "/soal/tambah/{idPaketSoal}", method = RequestMethod.POST)
    public String tambahSoalInitialSubmit(@PathVariable Long idPaketSoal, @RequestParam("jumlahJawaban") String jumlahJawaban, RedirectAttributes redir) {
//        RedirectView redirectView = new RedirectView("/soal/tambah", true);
        redir.addFlashAttribute("jumlahJawaban", jumlahJawaban);
        redir.addFlashAttribute("idPaketSoal", idPaketSoal);

        return "redirect:/soal/tambah";
    }

    @RequestMapping(value = "/soal/tambah", method = RequestMethod.GET)
    public String tambahSoal(HttpServletRequest req, Model model) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(req);
        int jumlahJawaban = Integer.parseInt((String) inputFlashMap.get("jumlahJawaban"));
        Long idPaketSoal = (Long) inputFlashMap.get("idPaketSoal");

        PaketSoalModel paketSoal = paketSoalService.getPaketSoalByIdPaket(idPaketSoal);

        SoalModel soal = new SoalModel();
        soal.setPaketSoal(paketSoal);

        List<JawabanModel> listJawaban = new ArrayList<>();

        for (int i = 0; i<jumlahJawaban; i++) {
            JawabanModel j = new JawabanModel();
            if (i == 0) {
                j.setCorrect(true);
            }
            j.setSoal(soal);
            listJawaban.add(j);
        }

        soal.setListJawaban(listJawaban);

        model.addAttribute("soal", soal);

        return "form-tambah-soal";
    }

    @RequestMapping(value = "/soal/tambah", method = RequestMethod.POST)
    public String tambahSoal(@ModelAttribute SoalModel soal, Model model, RedirectAttributes redirect) {
        SoalModel soalToAdd = new SoalModel();
        soalToAdd.setPaketSoal(soal.getPaketSoal());
        soalToAdd.setPertanyaan(soal.getPertanyaan());
        soalToAdd.setActive(true);
        soalToAdd.setListJawaban(new ArrayList<>());

        soalService.addSoal(soalToAdd);

        for (int i = 0; i < soal.getListJawaban().size(); i++) {
            if (i == 0) {
                soal.getListJawaban().get(i).setCorrect(true);
            }
            soal.getListJawaban().get(i).setSoal(soalToAdd);
            jawabanService.addJawaban(soal.getListJawaban().get(i));
        }

//        System.out.println(soal.getPertanyaan());
//        System.out.println(soal.isActive());
//        System.out.println(soal.getPaketSoal());
//        System.out.println(soal.getListJawaban().size());
//        for (int i = 0; i < soal.getListJawaban().size(); i++) {
//            System.out.println("Jawaban ke " + i + ": " + soal.getListJawaban().get(i).getJawaban());
//            System.out.println("Is correct? " + soal.getListJawaban().get(i).isCorrect());
//        }

        redirect.addFlashAttribute("alert", "Soal berhasil ditambahkan. ");

        return "redirect:/rekrutmen/paketsoal/detail/" + soal.getPaketSoal().getIdPaket();
    }

    @RequestMapping(value = "/soal/detail/{idSoal}", method = RequestMethod.GET)
    public String detailSoal(@PathVariable Long idSoal, Model model) {
        SoalModel soal = soalService.getSoalById(idSoal);
        JawabanModel correctAnswer = new JawabanModel();
        List<JawabanModel> listActiveJawaban = new ArrayList<JawabanModel>();

        for (JawabanModel j : soal.getListJawaban()) {
            if (j.isCorrect()) {
                correctAnswer = j;
            }
        }

        List<JawabanModel> wrongAnswers = soal.getListJawaban();
        wrongAnswers.removeIf(n -> n.isCorrect());
        for(JawabanModel jwbn :wrongAnswers){
            if(jwbn.isActive()){
                listActiveJawaban.add(jwbn);
            }
        }

        model.addAttribute("soal", soal);
        model.addAttribute("correctAnswer", correctAnswer);
        model.addAttribute("jawaban", listActiveJawaban);

        return "detail-soal";
    }

    @RequestMapping(value = "/soal/ubah/{idSoal}", method = RequestMethod.GET)
    public String ubahSoal(@PathVariable Long idSoal, Model model) {
        SoalModel existingSoal = soalService.getSoalById(idSoal);

        model.addAttribute("soal", existingSoal);

        return "form-ubah-soal";
    }

    @RequestMapping(value = "/soal/ubah", method = RequestMethod.POST)
    public String ubahSoalSubmit(@ModelAttribute SoalModel soal, Model model, RedirectAttributes redirect) {
        soal.setActive(true);
        SoalModel updatedSoal = soalService.editSoal(soal);

        redirect.addFlashAttribute("alert", "Soal berhasil diubah.");

        return "redirect:/soal/detail/" + updatedSoal.getIdSoal();
    }

    @RequestMapping(value = "/soal/hapus", method = RequestMethod.POST)
    public String hapusSoal(@ModelAttribute SoalModel soal, Model model, RedirectAttributes redirect) {
        SoalModel targetSoal = soalService.deleteSoal(soal);

        redirect.addFlashAttribute("alertHapus", "Soal berhasil dihapus.");

        return "redirect:/rekrutmen/paketsoal/detail/" + targetSoal.getPaketSoal().getIdPaket();
    }

//    @RequestMapping(value = "/soal/tambah", method = RequestMethod.GET)
//    public String tambahSoal(HttpServletRequest req, Model model) {
//        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(req);
//        String jumlahJawaban = (String) inputFlashMap.get("jumlahJawaban");
//
//        System.out.println(jumlahJawaban);
//
//        model.addAttribute("jumlahJawaban", jumlahJawaban);
//
//        return "form-tambah-soal";
//    }

//    @RequestMapping(value = "/soal/tambah", method = RequestMethod.GET)
//    public String tambahSoal(HttpServletRequest req, Model model) {
//        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(req);
//        String jumlahJawaban = (String) inputFlashMap.get("jumlahJawaban");
//        String idPaketSoal = Long.toString((Long) inputFlashMap.get("idPaketSoal"));
//
//        SoalAndJawaban soalAndJawaban = new SoalAndJawaban(Integer.parseInt(jumlahJawaban));
//
//        model.addAttribute("jumlahJawaban", jumlahJawaban);
//        model.addAttribute("soalAndJawaban", soalAndJawaban);
//        model.addAttribute("idPaketSoal", idPaketSoal);
//
//        return "form-tambah-soal";
//    }

//    @RequestMapping(value = "/soal/tambah", method = RequestMethod.POST)
//    public String tambahSoal(@RequestParam("soalAndJawaban") SoalAndJawaban soalAndJawaban, Model model) {
//        System.out.println(soalAndJawaban.getSoal().getPertanyaan());
//        System.out.println(soalAndJawaban.getSoal().isActive());
//        System.out.println(soalAndJawaban.getSoal().getPaketSoal());
//
//        return "form-tambah-soal";
//    }
}
