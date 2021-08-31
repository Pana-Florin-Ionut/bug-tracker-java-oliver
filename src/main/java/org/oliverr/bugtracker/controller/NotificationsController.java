package org.oliverr.bugtracker.controller;

import org.oliverr.bugtracker.entity.User;
import org.oliverr.bugtracker.repository.NotificationRepository;
import org.oliverr.bugtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class NotificationsController {

    private UserRepository ur;
    @Autowired
    public void setUr(UserRepository ur) {this.ur = ur;}

    private NotificationRepository nr;
    @Autowired
    public void setNr(NotificationRepository nr) {this.nr = nr; }

    @RequestMapping("/notifications")
    public String notifications(Model model, Principal principal) {
        User loggedUser = ur.findByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", ur.isAdmin(loggedUser));
        model.addAttribute("pageTitle", "Notifications | Bug Tracker");
        model.addAttribute("isUnread", nr.isThereUnread(loggedUser.getId()));

        model.addAttribute("notifications", nr.getAllNotifications(loggedUser.getId()));

        return "notifications";
    }

}