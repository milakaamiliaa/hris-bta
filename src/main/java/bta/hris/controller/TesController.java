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
        HasilTesModel tes = hasilTesService.getHasilTesByCalonPengajar(calonPengajarService.getCalonByUsername(
            SecurityContextHolder.getContext().getAuthentication().getName()));
        PaketSoalModel paketPsikotes = paketSoalService.getPaketSoalByMataPelajaran("Psikotes");


        //if PAKET DIDNT exists
        if (paketPsikotes.equals(null)){
            return "soal-tes-belum-terbuat";
        }
        // if HASIL psikotes ALREADY exists
        if (tes != null && hasilTesService.getHasilTesByCalonPengajar(calonPengajarService.getCalonByUsername(
            SecurityContextHolder.getContext().getAuthentication().getName())).getSubmittedPaketSoal().getMataPelajaran() == "Psikotes") {
            
            SubmittedPaketSoalModel paketSoalToPost = tes.getSubmittedPaketSoal();
            Integer jumlahSoal = tes.getSubmittedPaketSoal().getListSoal().size();

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
        hasilTes.setCalonPengajar(calonPengajarService.getCalonByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()));

        for (SoalModel s : paketPsikotes.getListSoal()) {
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
            Collections.shuffle(submittedJawaban);
            ss.setListJawaban(submittedJawaban);

            submittedSoal.add(ss);
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

       Integer nilai = 0;
       List<SubmittedSoalModel> listSoal = hasilTes.getSubmittedPaketSoal().getListSoal();
       for (SubmittedSoalModel soal : listSoal){
           for(SubmittedJawabanModel jawaban : soal.getListJawaban()){
               if(jawaban.isChosen() && jawaban.isCorrect()){
                   nilai += 1;
               }
           }
       }
       
       nilai = (nilai/listSoal.size()) * 100;
       hasil.setNilai(nilai);
       CalonPengajarModel calon = hasilTes.getCalonPengajar();
       calon.setNilaiPsikotes(Long.valueOf(nilai));
        return "redirect:/calonpengajar/aturan-tes-matapelajaran";
    }

    @RequestMapping(value = "/calonpengajar/aturan-tes-matapelajaran", method = RequestMethod.GET)
    public String aturanTesMatapelajaran(Model model) {
        return "aturan-tes-matpel";
    }

    @RequestMapping(value = "/calonpengajar/tes-matapelajaran", method = RequestMethod.GET)
    public String tesMataPelajaran(Model model) {
        String matpel = calonPengajarService.getCalonByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getMataPelajaran();
        PaketSoalModel paketMatpel = paketSoalService.getPaketSoalByMataPelajaran(matpel);

        //if PAKET DIDNT exists
        if (paketMatpel.equals(null)){
            return "soal-tes-belum-terbuat";
        }
        // duplicating master data to submitted data + save to db
        HasilTesModel hasilTes = new HasilTesModel();
        SubmittedPaketSoalModel submittedPaketSoal = new SubmittedPaketSoalModel();
        List<SubmittedSoalModel> submittedSoal = new ArrayList<>();

        hasilTes.setStartedAt(LocalDate.now());
        hasilTes.setCalonPengajar(calonPengajarService.getCalonByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()));

        for (SoalModel s : paketMatpel.getListSoal()) {
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
            Collections.shuffle(submittedJawaban);
            ss.setListJawaban(submittedJawaban);

            submittedSoal.add(ss);
        }

        submittedPaketSoal.setHasilTes(hasilTes);
        submittedPaketSoal.setMataPelajaran(paketMatpel.getMataPelajaran());
        submittedPaketSoal.setNama(paketMatpel.getNama());
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

        return "tes-matpel";
    }

    @RequestMapping(value = "/calonpengajar/tes-matapelajaran", method = RequestMethod.POST)
    public String submitTesMataPelajaran(@ModelAttribute HasilTesModel hasilTes, Model model, RedirectAttributes redirect) {
        HasilTesModel hasil = new HasilTesModel();
        hasil.setFinishedAt(LocalDate.now());
        hasil.setCalonPengajar(hasilTes.getCalonPengajar());
        hasil.setStartedAt(hasilTes.getStartedAt());

       Integer nilai = 0;
       List<SubmittedSoalModel> listSoal = hasilTes.getSubmittedPaketSoal().getListSoal();
       for (SubmittedSoalModel soal : listSoal){
           for(SubmittedJawabanModel jawaban : soal.getListJawaban()){
               if(jawaban.isChosen() && jawaban.isCorrect()){
                   nilai += 1;
               }
           }
       }
       
       nilai = (nilai/listSoal.size()) * 100;
       hasil.setNilai(nilai);
       CalonPengajarModel calon = hasilTes.getCalonPengajar();
       calon.setStatus("Sudah Mengerjakan Tes");
       calon.setNilaiMataPelajaran(Long.valueOf(nilai));
        return "redirect:/";
    }
//
//    @RequestMapping(value = "/calonpengajar/aturan-mata-pelajaran", method = RequestMethod.GET)
//    public String aturanMataPelajaran(Model model) {
//        return "aturan-tes-matpel";
//    }
}


