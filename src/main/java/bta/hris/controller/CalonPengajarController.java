package bta.hris.controller;

import bta.hris.model.CalonPengajarModel;
import bta.hris.service.CalonPengajarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class CalonPengajarController {
    @Autowired
    CalonPengajarService CalonPengajarService;

    @RequestMapping(value = "/calonpengajar/daftar", method = RequestMethod.GET)
    public String daftarCalonPengajar(Model model){
        List<CalonPengajarModel> listCalon = CalonPengajarService.getAllCalon();
        model.addAttribute("listCalon", listCalon);
        return "daftar-calonpengajar";
    }

    @RequestMapping(value = "/calonpengajar/detail/{idCalon}", method = RequestMethod.GET)
    public String detailCalonPengajar(@PathVariable String idCalon, Model model){
        CalonPengajarModel calon = CalonPengajarService.getCalonById(idCalon);
        model.addAttribute("calon", calon);
        return "detail-calonpengajar";
    }

    @RequestMapping(value = "calonpengajar/rekrut/{idCalon}", method = RequestMethod.POST)
    public String rekrutCalon(@ModelAttribute CalonPengajarModel calon, Model model){
        System.out.print(calon);
        System.out.print("HERE");
        CalonPengajarModel newPengajar = CalonPengajarService.rekrutCalon(CalonPengajarService.getCalonById(calon.getIdCalon()));
        model.addAttribute("newPengajar", newPengajar);
        return detailCalonPengajar(calon.getIdCalon(), model);
    }

    @RequestMapping(value = "calonpengajar/tolak/{idCalon}", method = RequestMethod.POST)
    public String tolakCAlon(@PathVariable String idCalon, @ModelAttribute CalonPengajarModel calon, Model model){
        CalonPengajarModel targetCalon = CalonPengajarService.tolakCalon(calon);
        model.addAttribute("calon", targetCalon);
        return detailCalonPengajar(idCalon, model);
    }

    @RequestMapping(value = "calonpengajar/undang/{idCalon}", method = RequestMethod.POST)
    public String undangCalon(@PathVariable String idCalon, @ModelAttribute CalonPengajarModel calon, Model model){
        CalonPengajarModel targetCalon = CalonPengajarService.undangCalon(calon);
        model.addAttribute("calon", targetCalon);
        return detailCalonPengajar(idCalon, model);
    }

    @RequestMapping(value = "calonpengajar/hapus/{idCalon}", method = RequestMethod.POST)
    public String hapusCalon(@PathVariable String idCalon, @ModelAttribute CalonPengajarModel calon, Model model){
        CalonPengajarModel targetCalon = CalonPengajarService.getCalonById(calon.getIdCalon());
        CalonPengajarService.hapusCalon(targetCalon);
        return daftarCalonPengajar(model);
    }

    

}