package bta.hris.controller;

import bta.hris.model.GolonganModel;
import bta.hris.service.GolonganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class GolonganController {
    @Qualifier("golonganServiceImpl")
    @Autowired
    private GolonganService golonganService;

    // URL mapping yang digunakan untuk mengakses halaman add golongan.
    @RequestMapping(value="/golongan/tambah", method = RequestMethod.GET)
    public String tambahGolonganForm(Model model) {
        // Get nomor golongan terakhir, ditambah satu.
        Integer currentGolonganNumber = golonganService.getAllGolongan().size() + 1;

        GolonganModel newGolongan = new GolonganModel();
        newGolongan.setNama(currentGolonganNumber.toString());
        newGolongan.setActive(true);

        model.addAttribute("golongan", newGolongan);

        return "form-tambah-golongan";
    }

    // URL mapping yang digunakan untuk submit form dan menangkap POST -- add golongan.
    @RequestMapping(value="/golongan/tambah", method = RequestMethod.POST)
    public String tambahGolonganSubmit(@ModelAttribute GolonganModel golongan, Model model, RedirectAttributes redirect) {
        golongan.setActive(true);
        golonganService.addGolongan(golongan);

        model.addAttribute("namaGolongan", golongan.getNama());
        redirect.addFlashAttribute("alert", "Golongan " + golongan.getNama() + " Berhasil Ditambahkan. ");

        return "redirect:/golongan";
    }

    // URL mapping untuk menuju halaman form edit golongan.
    @RequestMapping(value="golongan/ubah/{idGolongan}", method = RequestMethod.GET)
    public String ubahGolonganForm(@PathVariable Long idGolongan, Model model) {
        // Mengambil existing data golongan.
        Optional<GolonganModel> existingGolongan = golonganService.getGolonganByIdGolongan(idGolongan);

        if (existingGolongan.isEmpty()) {
            return "view-error";
        }

        GolonganModel golongan = existingGolongan.get();

        model.addAttribute("golongan", golongan);

        return "form-ubah-golongan";
    }

    // URL mapping untuk POST form edit golongan.
    @RequestMapping(value="/golongan/ubah/{idGolongan}", method = RequestMethod.POST)
    public String ubahGolonganSubmit(@PathVariable Long idGolongan, @ModelAttribute GolonganModel golongan, Model model, RedirectAttributes redirect) {
        GolonganModel newGolonganData = golonganService.editGolongan(golongan);
        redirect.addFlashAttribute("alertUbah", "Golongan " + golongan.getNama() + " Berhasil Diubah. ");
        model.addAttribute("golongan", newGolonganData);

        return "redirect:/golongan";
    }

    // URL mapping untuk melihat semua golongan.
    @RequestMapping(value="/golongan", method = RequestMethod.GET)
    public String daftarGolongan(Model model) {
        List<GolonganModel> allGolongan = golonganService.getAllGolongan();
        List<GolonganModel> allActiveGolongan = new ArrayList<GolonganModel>();
        for (GolonganModel g : allGolongan) {
            if (g.isActive()) {
                allActiveGolongan.add(g);
            }
        }

        model.addAttribute("allGolongan", allActiveGolongan);

        return "daftar-golongan";
    }

    @RequestMapping(value = "/golongan/hapus/{idGolongan}", method = RequestMethod.POST)
    public String hapusGolongan(@PathVariable Long idGolongan, @ModelAttribute GolonganModel golongan, Model model) {
        GolonganModel target = golonganService.getGolonganByIdGolongan(idGolongan).get();
        golonganService.deleteGolongan(target);

        return "redirect:/golongan";
    }

}
