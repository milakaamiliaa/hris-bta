package bta.hris.controller;

import bta.hris.model.CabangModel;
import bta.hris.service.CabangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
