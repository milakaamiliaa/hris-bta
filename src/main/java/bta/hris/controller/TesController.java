package bta.hris.controller;

import bta.hris.model.*;
import bta.hris.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
        HasilTesModel tes = hasilTesService.getHasilTesByCalonPengajar(calonPengajarService
                .getCalonByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        PaketSoalModel paketPsikotes = paketSoalService.getPaketSoalByMataPelajaran("Psikotes");

        // if PAKET DIDNT exists
        if (paketPsikotes == null) {
            return "soal-tes-belum-terbuat";
        }
        // if HASIL psikotes ALREADY exist
        if (tes != null && hasilTesService
                .getHasilTesByCalonPengajar(calonPengajarService
                        .getCalonByUsername(SecurityContextHolder.getContext().getAuthentication().getName()))
                .getSubmittedPaketSoal().getMataPelajaran().equalsIgnoreCase("Psikotes")) {

            SubmittedPaketSoalModel paketSoalToPost = tes.getSubmittedPaketSoal();
            Integer jumlahSoal = tes.getSubmittedPaketSoal().getListSoal().size();

            System.out.println(tes.getSubmittedPaketSoal().getNama());
            System.out.println(tes.getSubmittedPaketSoal().getNama());
            System.out.println(tes.getSubmittedPaketSoal().getNama());

            model.addAttribute("paketSoal", paketSoalToPost);
            model.addAttribute("hasilTes", tes);
            model.addAttribute("jumlahSoal", jumlahSoal);

            return "tes-psikotes";
        }
        // if HASIL psikotes DIDNT exists yet
        // duplicating master data to submitted data + save to db
        HasilTesModel hasilTes = new HasilTesModel();
        SubmittedPaketSoalModel submittedPaketSoal = new SubmittedPaketSoalModel();
        List<SubmittedSoalModel> submittedSoal = new ArrayList<>();

        hasilTes.setStartedAt(LocalDate.now());
        hasilTes.setCalonPengajar(calonPengajarService
                .getCalonByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        for (SoalModel s : paketPsikotes.getListSoal()) {
            List<SubmittedJawabanModel> submittedJawaban = new ArrayList<SubmittedJawabanModel>();

            SubmittedSoalModel ss = new SubmittedSoalModel();

            for (JawabanModel j : s.getListJawaban()) {
                if (j.isActive() == true) {
                    SubmittedJawabanModel jj = new SubmittedJawabanModel();
                    jj.setJawaban(j.getJawaban());
                    jj.setSoal(ss);
                    jj.setIsChosen(false);
                    jj.setCorrect(j.isCorrect());

                    submittedJawaban.add(jj);
                }
            }

            if (s.getIsActive() == true) {
                ss.setPaketSoal(submittedPaketSoal);
                ss.setPertanyaan(s.getPertanyaan());
                Collections.shuffle(submittedJawaban);
                ss.setListJawaban(submittedJawaban);

                submittedSoal.add(ss);
            }
        }

        submittedPaketSoal.setHasilTes(hasilTes);
        submittedPaketSoal.setMataPelajaran(paketPsikotes.getMataPelajaran());
        submittedPaketSoal.setNama(paketPsikotes.getNama());
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

        model.addAttribute("paketSoal", paketSoalToPost);
        model.addAttribute("hasilTes", hasilTes);
        model.addAttribute("jumlahSoal", hasilTes.getSubmittedPaketSoal().getListSoal().size());

        return "tes-psikotes";
    }

    @RequestMapping(value = "/calonpengajar/tes-psikotes", method = RequestMethod.POST)
    public String submitPsikotes(@ModelAttribute HasilTesModel hasilTes, Model model, RedirectAttributes redirect) {
        HasilTesModel hasil = new HasilTesModel();
        hasil.setFinishedAt(LocalDate.now());
        hasil.setCalonPengajar(hasilTes.getCalonPengajar());
        hasil.setStartedAt(hasilTes.getStartedAt());
        hasil.setSubmittedPaketSoal(hasilTes.getSubmittedPaketSoal());
        hasil.setIdHasil(hasilTes.getIdHasil());

        Float nilai = Float.parseFloat("0");
        for (SubmittedSoalModel s : hasil.getSubmittedPaketSoal().getListSoal()) {
            if (s.getSubmittedJawaban().isCorrect()) {
                nilai += 1;
            }
        }

        nilai = (nilai / hasil.getSubmittedPaketSoal().getListSoal().size()) * 100;
        hasil.setNilai(nilai);

        HasilTesModel hasilTesUpdated = hasilTesService.updateHasilTes(hasilTes);

        CalonPengajarModel calon = hasilTes.getCalonPengajar();
        calon.setNilaiPsikotes((nilai));
        calonPengajarService.updateNilaiPsikotesCalonPengajar(calon);

        return "redirect:/calonpengajar/aturan-tes-matapelajaran";
    }

    @RequestMapping(value = "/calonpengajar/aturan-tes-matapelajaran", method = RequestMethod.GET)
    public String aturanTesMatapelajaran(Model model) {
        return "aturan-tes-matpel";
    }

    @RequestMapping(value = "/calonpengajar/tes-matapelajaran", method = RequestMethod.GET)
    public String tesMataPelajaran(Model model) {
        HasilTesModel tes = hasilTesService.getHasilTesByCalonPengajar(calonPengajarService
                .getCalonByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        PaketSoalModel paketMapel = paketSoalService.getPaketSoalByMataPelajaran(calonPengajarService
                .getCalonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .getMataPelajaran());

        // if PAKET DIDNT exists
        if (paketMapel == null) {
            return "soal-tes-belum-terbuat";
        }
        // if Hasil Tes ALREADY exists
        if (tes != null && hasilTesService
                .getHasilTesByCalonPengajar(calonPengajarService
                        .getCalonByUsername(SecurityContextHolder.getContext().getAuthentication().getName()))
                .getSubmittedPaketSoal().getMataPelajaran()
                .equalsIgnoreCase(calonPengajarService
                        .getCalonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                        .getMataPelajaran())) {

            SubmittedPaketSoalModel paketSoalToPost = tes.getSubmittedPaketSoal();
            Integer jumlahSoal = tes.getSubmittedPaketSoal().getListSoal().size();

            System.out.println(tes.getSubmittedPaketSoal().getNama());
            System.out.println(tes.getSubmittedPaketSoal().getNama());
            System.out.println(tes.getSubmittedPaketSoal().getNama());

            model.addAttribute("paketSoal", paketSoalToPost);
            model.addAttribute("hasilTes", tes);
            model.addAttribute("jumlahSoal", jumlahSoal);

            return "tes-matpel";
        }
        // if Hasil Tes didnt exist yet
        HasilTesModel hasilTes = new HasilTesModel();
        SubmittedPaketSoalModel submittedPaketSoal = new SubmittedPaketSoalModel();
        List<SubmittedSoalModel> submittedSoal = new ArrayList<>();

        hasilTes.setStartedAt(LocalDate.now());
        hasilTes.setCalonPengajar(calonPengajarService
                .getCalonByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        for (SoalModel s : paketMapel.getListSoal()) {
            List<SubmittedJawabanModel> submittedJawaban = new ArrayList<SubmittedJawabanModel>();

            SubmittedSoalModel ss = new SubmittedSoalModel();

            for (JawabanModel j : s.getListJawaban()) {
                if (j.isActive() == true) {
                    SubmittedJawabanModel jj = new SubmittedJawabanModel();
                    jj.setJawaban(j.getJawaban());
                    jj.setSoal(ss);
                    jj.setIsChosen(false);
                    jj.setCorrect(j.isCorrect());

                    submittedJawaban.add(jj);
                }
            }

            if (s.getIsActive() == true) {
                ss.setPaketSoal(submittedPaketSoal);
                ss.setPertanyaan(s.getPertanyaan());
                Collections.shuffle(submittedJawaban);
                ss.setListJawaban(submittedJawaban);

                submittedSoal.add(ss);
            }
        }

        submittedPaketSoal.setHasilTes(hasilTes);
        submittedPaketSoal.setMataPelajaran(paketMapel.getMataPelajaran());
        submittedPaketSoal.setNama(paketMapel.getNama());
        submittedPaketSoal.setListSoal(submittedSoal);

        hasilTes.setSubmittedPaketSoal(submittedPaketSoal);

        hasilTesService.addHasilTes(hasilTes);

        SubmittedPaketSoalModel paketSoalToPost = submittedPaketSoalService.addSubmittedPaketSoal(submittedPaketSoal); // save+retrieve
        for (SubmittedSoalModel s : submittedPaketSoal.getListSoal()) {
            submittedSoalService.addSubmittedSoal(s);

            for (SubmittedJawabanModel j : s.getListJawaban()) {
                submittedJawabanService.addSubmittedJawaban(j);
            }
        }

        model.addAttribute("paketSoal", paketSoalToPost);
        model.addAttribute("hasilTes", hasilTes);
        model.addAttribute("jumlahSoal", hasilTes.getSubmittedPaketSoal().getListSoal().size());

        return "tes-matpel";
    }

    @RequestMapping(value = "/calonpengajar/tes-matapelajaran", method = RequestMethod.POST)
    public String submitTesMataPelajaran(@ModelAttribute HasilTesModel hasilTes, Model model,
            RedirectAttributes redirect) {
        HasilTesModel hasil = new HasilTesModel();
        hasil.setFinishedAt(LocalDate.now());
        hasil.setCalonPengajar(hasilTes.getCalonPengajar());
        hasil.setStartedAt(hasilTes.getStartedAt());
        hasil.setSubmittedPaketSoal(hasilTes.getSubmittedPaketSoal());
        hasil.setIdHasil(hasilTes.getIdHasil());

        Float nilai = Float.parseFloat("0");
        for (SubmittedSoalModel s : hasil.getSubmittedPaketSoal().getListSoal()) {
            if (s.getSubmittedJawaban().isCorrect()) {
                nilai += 1;
            }
        }
        nilai = (nilai / hasil.getSubmittedPaketSoal().getListSoal().size()) * 100;

        hasil.setNilai(nilai);
        HasilTesModel hasilTesUpdated = hasilTesService.updateHasilTes(hasilTes);
        CalonPengajarModel calon = hasilTes.getCalonPengajar();
        calon.setNilaiMataPelajaran(nilai);
        calonPengajarService.updateNilaiMapelCalonPengajar(calon);
        calon.setStatus("Sudah Mengerjakan Tes");
        calonPengajarService.updateStatusCalonPengajar(calon);

        return "redirect:/";
    }

}
