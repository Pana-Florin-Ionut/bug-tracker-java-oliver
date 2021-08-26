package org.oliverr.bugtracker.controller;

import org.oliverr.bugtracker.DB;
import org.oliverr.bugtracker.entity.Bug;
import org.oliverr.bugtracker.entity.User;
import org.oliverr.bugtracker.repository.BugRepository;
import org.oliverr.bugtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class BugsController {

    private DB db;
    @Autowired
    public void setDb(DB db) { this.db = db; }

    private UserRepository ur;
    @Autowired
    public void setUr(UserRepository ur) { this.ur = ur; }

    private BugRepository br;
    @Autowired
    public void setBr(BugRepository br) { this.br = br; }

    @RequestMapping("/bugs")
    public String tasks(Model model, Principal principal) {
        User loggedUser = ur.findByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", ur.isAdmin(loggedUser));
        model.addAttribute("pageTitle", "Bugs | Bug Tracker");

        model.addAttribute("allBug", br.getAllBugs(loggedUser.getId()));
        return "bugs";
    }

}