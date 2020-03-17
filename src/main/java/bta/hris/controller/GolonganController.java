package bta.hris.controller;

import bta.hris.model.GolonganModel;
import bta.hris.service.GolonganService;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@Controller
public class GolonganController {
    @Qualifier("golonganServiceImpl")
    @Autowired
    private GolonganService golonganService;

    // URL mapping yang digunakan untuk mengakses halaman add golongan.
    @RequestMapping(value="/golongan/add", method = RequestMethod.GET)
    public String addGolonganPage(Model model) {
        // Get nomor golongan terakhir, ditambah satu.
        Integer currentGolonganNumber = golonganService.getAllGolongan().size() + 1;

        GolonganModel newGolongan = new GolonganModel();
        newGolongan.setNama(currentGolonganNumber.toString());

        model.addAttribute("golongan", newGolongan);

        return "form-add-golongan";
    }

    // URL mapping yang digunakan untuk submit form dan menangkap POST -- add golongan.
    @RequestMapping(value="/golongan/add", method = RequestMethod.POST)
    public String addGolonganSubmit(@ModelAttribute GolonganModel golongan, Model model) {
        System.out.println(golongan.getNama());
        System.out.println(golongan.getRate());
        System.out.println(golongan.getPajak());
        golonganService.addGolongan(golongan);

        model.addAttribute("namaGolongan", golongan.getNama());

        return "add-golongan";
    }

    // URL mapping untuk menuju halaman form edit golongan.
    @RequestMapping(value="golongan/edit/{idGolongan}", method = RequestMethod.GET)
    public String editGolonganPage(@PathVariable Long idGolongan, Model model) {
        // Mengambil existing data golongan.
        Optional<GolonganModel> existingGolongan = golonganService.getGolonganByIdGolongan(idGolongan);

        if (existingGolongan.isEmpty()) {
            return "view-error";
        }

        GolonganModel golongan = existingGolongan.get();

        model.addAttribute("golongan", golongan);

        return "form-edit-golongan";
    }

    // URL mapping untuk POST form edit golongan.
    @RequestMapping(value="/golongan/edit/{idGolongan}", method = RequestMethod.POST)
    public String editGolonganSubmit(@PathVariable Long idGolongan, @ModelAttribute GolonganModel golongan, Model model) {
        GolonganModel newGolonganData = golonganService.editGolongan(golongan);

        System.out.println(newGolonganData.getNama());
        System.out.println(newGolonganData.getRate());
        System.out.println(newGolonganData.getPajak());

        model.addAttribute("golongan", newGolonganData);

        return "edit-golongan";
    }

    // URL mapping untuk melihat semua golongan.
    @RequestMapping(value="/golongan", method = RequestMethod.GET)
    public String viewAllGolongan(Model model) {
        List<GolonganModel> allGolongan = golonganService.getAllGolongan();

        model.addAttribute("allGolongan", allGolongan);

        return "view-all-golongan";
    }

}
