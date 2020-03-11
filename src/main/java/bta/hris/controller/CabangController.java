package bta.hris.controller;

import bta.hris.model.CabangModel;
import bta.hris.service.CabangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CabangController {
    @Autowired
    private CabangService cabangService;

    @RequestMapping(value = "/cabang/daftar")
    public String daftarCabang(Model model){
        List<CabangModel> listCabang = cabangService.getCabangList();
        model.addAttribute("listCabang", listCabang);

        return "daftar-cabang";
    }

    @RequestMapping(value = "/cabang/detail/{idCabang}", method = RequestMethod.GET)
    public String detailCabang(
            @PathVariable Long idCabang, Model model
    ) {
        CabangModel cabang = cabangService.getCabangByIdCabang(idCabang).get();
        model.addAttribute("cabang", cabang);

        return "detail-cabang";
    }

    @RequestMapping(value = "/cabang/tambah", method = RequestMethod.GET)
    public String createCabangForm(Model model){
        CabangModel newCabang = new CabangModel();
        model.addAttribute("restoran", newCabang);
        return "form-create-cabang";
    }

    @RequestMapping(value = "/fasilitas/pengadaan/tambah", method = RequestMethod.POST)
    public String createCabangSubmit(@ModelAttribute CabangModel cabang, Model model){
        cabangService.createCabang(cabang);
        model.addAttribute("namaCabang", cabang.getNama());
        return "create-cabang";
    }
}
