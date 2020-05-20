package bta.hris.controller;

import bta.hris.model.CabangModel;
import bta.hris.model.PaketSoalModel;
import bta.hris.model.SoalModel;
import bta.hris.service.PaketSoalService;
import bta.hris.service.SoalService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PaketSoalController {
    @Autowired
    private PaketSoalService paketSoalService;

    @Autowired
    private SoalService soalService;


    @RequestMapping(value = "/rekrutmen/paketsoal")
    public String daftarPaketSoal(Model model){
        List<PaketSoalModel> listPaketsoal = paketSoalService.getAllPaketsoal();
        List<PaketSoalModel> listActivePaketSoal = new ArrayList<PaketSoalModel>();
        for (PaketSoalModel paketSoal : listPaketsoal){
            if (paketSoal.isActive()){
                listActivePaketSoal.add(paketSoal);
            }
        }
        model.addAttribute("listPaketSoal", listActivePaketSoal);
        return "daftar-paket-soal";
    }

    @RequestMapping(value = "/rekrutmen/paketsoal/tambah", method = RequestMethod.GET)
    public String tambahPaketSoalForm(Model model){
        PaketSoalModel newPaketSoal = new PaketSoalModel();

        List<String> mataPelajaran = new ArrayList<String>();
        mataPelajaran.add("Psikotes");
        mataPelajaran.add("Biologi");
        mataPelajaran.add("Ekonomi");
        mataPelajaran.add("Matematika");
        mataPelajaran.add("Kimia");
        mataPelajaran.add("Fisika");
        mataPelajaran.add("Sosiologi");
        mataPelajaran.add("Geografi");
        mataPelajaran.add("TPA");
        mataPelajaran.add("Bahasa Inggris");
        mataPelajaran.add("Bahasa Indonesia");

        model.addAttribute("paketSoal", newPaketSoal);
        model.addAttribute("mataPelajaran", mataPelajaran);

        return "form-tambah-paket-soal";
    }

    @RequestMapping(value = "/rekrutmen/paketsoal/tambah", method = RequestMethod.POST)
    public String tambahPaketSoalSubmit(@ModelAttribute PaketSoalModel paketSoal, Model model, RedirectAttributes redirect){
        paketSoal.setCreatedAt(LocalDate.now());

        paketSoalService.addPaketSoal(paketSoal);
        redirect.addFlashAttribute("alert", "Paket Soal " + paketSoal.getNama() + " Berhasil Ditambahkan. ");

        return "redirect:/rekrutmen/paketsoal";
    }

    @RequestMapping(value = "/rekrutmen/paketsoal/detail/{idPaket}", method = RequestMethod.GET)
    public String detailPaketSoal(@PathVariable Long idPaket, Model model) {
        PaketSoalModel paketSoal = paketSoalService.getPaketSoalByIdPaket(idPaket).get();
        model.addAttribute("paketSoal", paketSoal);

        List<SoalModel> listSoal = soalService.getSoalByPaketSoal(paketSoal);
        model.addAttribute("soal", listSoal);

        return "detail-paket-soal";
    }
    @RequestMapping(value = "/rekrutmen/paketsoal/ubah/{idPaket}", method = RequestMethod.GET)
    public String ubahPaketSoalForm(@PathVariable Long idPaket, Model model) {
        PaketSoalModel existingPaket = paketSoalService.getPaketSoalByIdPaket(idPaket).get();

        List<String> mataPelajaran = new ArrayList<String>();
        mataPelajaran.add("Psikotes");
        mataPelajaran.add("Biologi");
        mataPelajaran.add("Ekonomi");
        mataPelajaran.add("Matematika");
        mataPelajaran.add("Kimia");
        mataPelajaran.add("Fisika");
        mataPelajaran.add("Sosiologi");
        mataPelajaran.add("Geografi");
        mataPelajaran.add("TPA");
        mataPelajaran.add("Bahasa Inggris");
        mataPelajaran.add("Bahasa Indonesia");
        mataPelajaran.add("Psikotes");



        model.addAttribute("paketSoal", existingPaket);
        model.addAttribute("mataPelajaran", mataPelajaran);

        return "form-ubah-paket-soal";
    }

    @RequestMapping(value = "/rekrutmen/paketsoal/ubah/{idPaket}", method = RequestMethod.POST)
    public String ubahPaketSoalSubmit(@PathVariable Long idPaket, @ModelAttribute PaketSoalModel paketSoal, Model model, RedirectAttributes redirect) {
        PaketSoalModel newPaket = paketSoalService.updatePaketSoal(paketSoal);

        model.addAttribute("paketSoal", newPaket);
        redirect.addFlashAttribute("alert", "Paket Soal " + paketSoal.getNama() + " Berhasil Diubah. ");

        return "redirect:/rekrutmen/paketsoal";
    }
    @RequestMapping(value="/rekrutmen/paketsoal/hapus/{idPaket}", method = RequestMethod.POST)
    public String hapusPaketSoal(@PathVariable Long idPaket, Model model, RedirectAttributes redirect){
        PaketSoalModel paketSoal = paketSoalService.getPaketSoalByIdPaket(idPaket); // !!! get was here
        model.addAttribute("paketSoal", paketSoal);
        paketSoalService.deletePaketSoal(paketSoal);
        redirect.addFlashAttribute("alertHapus", "Paket Soal " + paketSoal.getNama() + " berhasil dihapus.");

        return "redirect:/rekrutmen/paketsoal";
    }



}
