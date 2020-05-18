package bta.hris.controller;

import bta.hris.model.*;
import bta.hris.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TesController {
    @Autowired
    PaketSoalService paketSoalService;

    @Autowired
    SoalService soalService;

    @Autowired
    JawabanService jawabanService;

    @Autowired
    SubmittedPaketSoalService submittedPaketSoalService;

    @Autowired
    SubmittedSoalService submittedSoalService;

    @Autowired
    SubmittedJawabanService submittedJawabanService;

    @Autowired
    HasilTesService hasilTesService;

    @Autowired
    CalonPengajarService calonPengajarService;

    @RequestMapping(value = "/calonpengajar/aturan-psikotes", method = RequestMethod.GET)
    public String aturanPsikotes(Model model) {
        return "aturan-tes-psikotes";
    }

    @RequestMapping(value = "/calonpengajar/tes-psikotes", method = RequestMethod.GET)
    public String tesPsikotes(Model model) {
        // Prevent duplicate insert of submitted models
        if (hasilTesService.getHasilTesByCalonPengajar(calonPengajarService.getCalonByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName())) != null) {
            HasilTesModel hasilTes = hasilTesService.getHasilTesByCalonPengajar(calonPengajarService.getCalonByUsername(
                    SecurityContextHolder.getContext().getAuthentication().getName()));
            SubmittedPaketSoalModel paketSoalToPost = hasilTes.getSubmittedPaketSoal();

            // POST
            model.addAttribute("paketSoal", paketSoalToPost);
            model.addAttribute("hasilTes", hasilTes);
            model.addAttribute("jumlahSoal", hasilTes.getSubmittedPaketSoal().getListSoal().size());

            return "tes-psikotes";
        }

        // BIG PICTURE:
        // retrieve all data from master
        // duplicate data to submitted
        // pas data udah siap semua, save ke db (ini biar gak ilang datanya kalo close tab atau gmn? need opinion on this)
        // retrieve dulu dr db yg submitted
        // post

        // IN ACTION:
        // retrieving master PaketSoal with mapel ("TPA")
        PaketSoalModel paketSoal = paketSoalService.getPaketSoalByMataPelajaran("Psikotes");

        // duplicating master data to submitted data + save to db
        HasilTesModel hasilTes = new HasilTesModel();
        hasilTes.setStartedAt(LocalDate.now());
        hasilTes.setCalonPengajar(calonPengajarService.getCalonByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()));

        SubmittedPaketSoalModel submittedPaketSoal = new SubmittedPaketSoalModel();

        List<SubmittedSoalModel> submittedSoal = new ArrayList<>();

        for (SoalModel s : paketSoal.getListSoal()) {
            List<SubmittedJawabanModel> submittedJawaban = new ArrayList<SubmittedJawabanModel>();

            SubmittedSoalModel ss = new SubmittedSoalModel();

            for (JawabanModel j : s.getListJawaban()) {
                SubmittedJawabanModel jj = new SubmittedJawabanModel();
                jj.setJawaban(j.getJawaban());
                jj.setSoal(ss);
                jj.setChosen(false);
                jj.setCorrect(j.isCorrect());

                submittedJawaban.add(jj);
            }

            ss.setPaketSoal(submittedPaketSoal);
            ss.setPertanyaan(s.getPertanyaan());
            ss.setListJawaban(submittedJawaban);

            submittedSoal.add(ss);
        }

        submittedPaketSoal.setHasilTes(hasilTes);
        submittedPaketSoal.setMataPelajaran(paketSoal.getMataPelajaran());
        submittedPaketSoal.setNama(paketSoal.getNama());
        submittedPaketSoal.setListSoal(submittedSoal);

        hasilTes.setSubmittedPaketSoal(submittedPaketSoal);

        // save to db + retrieve
        hasilTesService.addHasilTes(hasilTes);

        SubmittedPaketSoalModel paketSoalToPost = submittedPaketSoalService.addSubmittedPaketSoal(submittedPaketSoal); // save+retrieve
        for (SubmittedSoalModel s : submittedPaketSoal.getListSoal()) {
            submittedSoalService.addSubmittedSoal(s); // save to db

            for (SubmittedJawabanModel j : s.getListJawaban()) {
                submittedJawabanService.addSubmittedJawaban(j); // save to db
            }
        }

        // POST
        model.addAttribute("paketSoal", paketSoalToPost);
        model.addAttribute("hasilTes", hasilTes);
        model.addAttribute("jumlahSoal", hasilTes.getSubmittedPaketSoal().getListSoal().size());

//        List<SoalModel> listSoal = PaketSoalService.getPaketSoalByIdPaket(Long.valueOf(1)).getListSoal();
//        HasilTesModel hasilTes = new HasilTesModel();
//        hasilTes.setStartedAt(LocalDate.now());
//        model.addAttribute("listSoal", listSoal);
//        model.addAttribute("hasilTes", hasilTes);
        return "tes-psikotes";
    }

    @RequestMapping(value = "/calonpengajar/tes-psikotes", method = RequestMethod.POST)
    public String submitTes(@ModelAttribute HasilTesModel hasilTes, Model model, RedirectAttributes redirect) {
        HasilTesModel hasil = new HasilTesModel();
        hasil.setFinishedAt(LocalDate.now());
        hasil.setCalonPengajar(hasilTes.getCalonPengajar());
//        hasil.setListJawaban(hasilTes.getListJawaban());
        hasil.setStartedAt(hasilTes.getStartedAt());

//        Integer nilai = 0;
//        for (JawabanModel jawaban : hasilTes.getListJawaban()){
//            if (jawaban.getIsCorrect()){
//                nilai += 1;
//            }
//        }nilai = (nilai/(hasilTes.getListJawaban().size())) * 100;
//        hasil.setNilai(nilai);

        return "aturan-tes-matpel";
    }

    @RequestMapping(value = "/calonpengajar/aturan-mata-pelajaran", method = RequestMethod.GET)
    public String aturanMataPelajaran(Model model) {
        return "aturan-tes-matpel";
    }
}


