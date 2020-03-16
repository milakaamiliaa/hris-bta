package bta.hris.controller;

import bta.hris.model.PresensiModel;
import bta.hris.service.PresensiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class PresensiController {
    @Autowired
    private PresensiService presensiService;

    @RequestMapping(value = "/presensi/view-all", method = RequestMethod.GET)
    public String viewAllPresensi(Model model){
        List<PresensiModel> listPresensi = presensiService.getPresensiList();
        model.addAttribute("listPresensi", listPresensi);
        return "view-all-presensi";
    }

}
