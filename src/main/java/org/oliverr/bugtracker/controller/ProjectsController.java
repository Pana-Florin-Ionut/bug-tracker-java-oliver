package org.oliverr.bugtracker.controller;

import org.oliverr.bugtracker.entity.Bug;
import org.oliverr.bugtracker.entity.Project;
import org.oliverr.bugtracker.entity.User;
import org.oliverr.bugtracker.repository.ProjectRepository;
import org.oliverr.bugtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        model.addAttribute("project", new Project());

        return "projects";
    }

    @RequestMapping(value = "/projects/add", method = RequestMethod.POST)
    public String addProject(@ModelAttribute Project project, Principal principal) {
        User sender = ur.findByEmail(principal.getName());
        pr.addToProjects(sender.getId(), project.getTitle(), project.getDescription(), project.getReadme());

        return "redirect:/projects";
    }

    @RequestMapping("/project/{projectid}")
    public String project(Model model, @PathVariable(value="projectid") String projectid, Principal principal) {
        User user = ur.findByEmail(principal.getName());
        try {
            if(pr.isItTheirProject(Long.parseLong(projectid), user.getId())) {
                Project project = pr.getProject(Long.parseLong(projectid));

                if(project != null) {
                    model.addAttribute("foundProject", project);

                    model.addAttribute("user", user);
                    model.addAttribute("isAdmin", ur.isAdmin(user));
                    model.addAttribute("pageTitle", "Project #"+projectid+" | Bug Tracker");
                    return "project";
                }
                return "error";
            }
        } catch (Exception e) {
            return "error";
        }
        return "error";
    }

}
