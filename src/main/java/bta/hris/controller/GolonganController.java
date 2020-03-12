package bta.hris.controller;

import bta.hris.model.GolonganModel;
import bta.hris.service.GolonganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

}
