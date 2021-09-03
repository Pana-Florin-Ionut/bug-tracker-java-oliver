package org.oliverr.bugtracker.controller;

import org.oliverr.bugtracker.entity.User;
import org.oliverr.bugtracker.repository.NotificationRepository;
import org.oliverr.bugtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class EmployeeController {

    private UserRepository ur;
    @Autowired
    public void setUr(UserRepository ur) { this.ur = ur; }

    private NotificationRepository nr;
    @Autowired
    public void setNr(NotificationRepository nr) { this.nr = nr; }

    @RequestMapping("/employees")
    public String employees(Model model, Principal principal) {
        User loggedUser = ur.findByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", ur.isAdmin(loggedUser));
        model.addAttribute("pageTitle", "Employees | Bug Tracker");
        model.addAttribute("isUnread", nr.isThereUnread(loggedUser.getId()));

        model.addAttribute("employee", new User());
        model.addAttribute("allUsers", ur.getAllUser());

        return "employees";
    }

    @RequestMapping(value = "/admin/employee/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute User user) {
        User found = ur.findByEmail(user.getEmail());
        if(found != null) {
            return "redirect:/employees?emailTaken";
        }

        ur.addUser(user.getFname(), user.getLname(), user.getEmail(), user.getPassword());
        ur.addRole(ur.findByEmail(user.getEmail()).getId(), Long.parseLong("1"));

        return "redirect:/employees";
    }

}
