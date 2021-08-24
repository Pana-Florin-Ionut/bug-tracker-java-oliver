package org.oliverr.bugtracker.controller;

import org.oliverr.bugtracker.entity.User;
import org.oliverr.bugtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class DashboardController {

    UserRepository ur;

    @Autowired
    public void setUr(UserRepository ur) { this.ur = ur; }

    @RequestMapping("/")
    public String dashboard(Model model, Principal principal) {
        User loggedUser = ur.findByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        return "index";
    }

}
