package org.oliverr.bugtracker.controller;

import org.oliverr.bugtracker.entity.User;
import org.oliverr.bugtracker.repository.ProjectRepository;
import org.oliverr.bugtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class ProjectsController {

    private UserRepository ur;
    @Autowired
    public void setUr(UserRepository ur) { this.ur = ur; }

    private ProjectRepository pr;
    @Autowired
    public void setPr(ProjectRepository pr) { this.pr = pr; }

    @RequestMapping("/projects")
    public String projects(Model model, Principal principal) {
        User loggedUser = ur.findByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", ur.isAdmin(loggedUser));
        model.addAttribute("pageTitle", "Projects | Bug Tracker");

        model.addAttribute("projects", pr.getAllProjects(loggedUser.getId()));

        return "projects";
    }

}
