package bta.hris.controller;

import bta.hris.model.CalonPengajarModel;
import bta.hris.model.UserModel;
import bta.hris.service.CalonPengajarService;
import bta.hris.service.RoleService;
import bta.hris.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.Month;

@Controller
public class PageController {
    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    CalonPengajarService calonPengajarService;



    @RequestMapping("/")
    public String home (Model model) {
        model.addAttribute("listRole", roleService.getAllRole());

        try{
            UserDetails loggedIn = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(userService.getByNip(loggedIn.getUsername()).getRole().getNama().equals("CALON PENGAJAR")){
                CalonPengajarModel calonPengajar = calonPengajarService.getCalonByUsername(loggedIn.getUsername());
                LocalDate deadline = calonPengajar.getTesDeadline();
                Month bulanDeadline = deadline.getMonth();

                model.addAttribute("calonPengajar", calonPengajar);
                model.addAttribute("bulanDeadline", bulanDeadline);
                return "beranda-calonPengajar";
            }
            else{
                return "home";
            }
        }

        catch(Exception e){
            return "Home fix";
        }
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }


    @RequestMapping("/tabel")
    public String table() {
        return "tables";
    }
}
