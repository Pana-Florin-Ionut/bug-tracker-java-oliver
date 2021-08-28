package org.oliverr.bugtracker.controller;

import org.oliverr.bugtracker.DB;
import org.oliverr.bugtracker.entity.Bug;
import org.oliverr.bugtracker.entity.User;
import org.oliverr.bugtracker.repository.BugRepository;
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

    private ProjectRepository pr;
    @Autowired
    public void setPr(ProjectRepository pr) { this.pr = pr; }

    @RequestMapping("/bugs")
    public String bugs(Model model, Principal principal) {
        User loggedUser = ur.findByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", ur.isAdmin(loggedUser));
        model.addAttribute("pageTitle", "Bugs | Bug Tracker");

        model.addAttribute("allBug", br.getAllBugs(loggedUser.getId()));
        model.addAttribute("projects", pr.getProjects(loggedUser.getId()));

        model.addAttribute("bug", new Bug());

        return "bugs";
    }

    @RequestMapping(value = "/bugs/add", method = RequestMethod.POST)
    public String addBug(@ModelAttribute Bug bug, Principal principal) {
        User sender = ur.findByEmail(principal.getName());

        br.addBug(pr.getProjectIdByName(bug.getProjectName()), sender.getId(), bug.getTitle(), bug.getDescription(), bug.getStatus());

        return "redirect:/bugs";
    }

    @RequestMapping("/bug/{bugid}")
    public String product(Model model, @PathVariable(value="bugid") String bugid, Principal principal) {
        User user = ur.findByEmail(principal.getName());
        try {
            if(br.isItTheirBug(Long.parseLong(bugid), user.getId())) {
                Bug bug = br.getBugById(Long.parseLong(bugid));

                if(bug != null) {
                    model.addAttribute("foundBug", bug);

                    model.addAttribute("user", user);
                    model.addAttribute("isAdmin", ur.isAdmin(user));
                    model.addAttribute("pageTitle", "Bug #"+bugid+" | Bug Tracker");
                    return "bug";
                }
                return "error";
            }
        } catch (Exception e) {
            return "error";
        }
        return "error";
    }

}
