package bta.hris.controller;

import bta.hris.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @Autowired
    RoleService roleService;

    @RequestMapping("/")
    public String home (Model model) {
        model.addAttribute("listRole", roleService.getAllRole());
        try{
            UserDetails loggedIn = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                return "home";
            }

        catch(Exception e){
            return "home fix";
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
