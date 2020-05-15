package bta.hris.controller;

import bta.hris.model.SoalModel;
import bta.hris.service.SoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class SoalController {

    @Autowired
    private SoalService soalService;

    @RequestMapping(value="/soal/tambah", method = RequestMethod.GET)
    public String tambahSoal(Model model) {
        List<SoalModel> allSoal = soalService.getAllSoal();

        model.addAttribute("allSoal", allSoal);

        return "form-tambah-soal";
    }

    @RequestMapping(value="/soal/tambah", method = RequestMethod.POST)
    public String tambahSoalSubmit(@ModelAttribute SoalModel soal, Model model) {
        soalService.addSoal(soal);

        return "redirect:/soal/tambah";
    }
}
