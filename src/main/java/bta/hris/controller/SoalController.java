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

        PaketSoalModel paketSoal = paketSoalService.getPaketSoalByIdPaket(idPaketSoal).get();

        SoalModel soal = new SoalModel();
        soal.setPaketSoal(paketSoal);

        List<JawabanModel> listJawaban = new ArrayList<>();

        for (int i = 0; i<jumlahJawaban; i++) {
            JawabanModel j = new JawabanModel();
            j.setSoal(soal);
            listJawaban.add(j);
        }

        soal.setListJawaban(listJawaban);

        model.addAttribute("soal", soal);

        return "form-tambah-soal";
    }

    @RequestMapping(value = "/soal/tambah", method = RequestMethod.POST)
    public String tambahSoal(@ModelAttribute SoalModel soal, Model model) {
        System.out.println(soal.getPertanyaan());
        System.out.println(soal.isActive());
        System.out.println(soal.getPaketSoal());
        System.out.println(soal.getListJawaban().size());

        return "form-tambah-soal";
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
