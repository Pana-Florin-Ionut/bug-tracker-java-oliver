package org.oliverr.bugtracker.controller;

import org.oliverr.bugtracker.DB;
import org.oliverr.bugtracker.entity.User;
import org.oliverr.bugtracker.repository.NotificationRepository;
import org.oliverr.bugtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class ProfileController {

    private DB db;
    @Autowired
    public void setDb(DB db) { this.db = db; }

    private UserRepository ur;
    @Autowired
    public void setUr(UserRepository ur) { this.ur = ur; }

    private NotificationRepository nr;
    @Autowired
    public void setNr(NotificationRepository nr) { this.nr = nr; }

    @RequestMapping("/profile")
    public String profile(Model model, Principal principal) {
        User loggedUser = ur.findByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", ur.isAdmin(loggedUser));
        model.addAttribute("pageTitle", "Profile | Bug Tracker");
        model.addAttribute("isUnread", nr.isThereUnread(loggedUser.getId()));

        model.addAttribute("completedBugs", completedBugsCount(loggedUser.getId()));
        model.addAttribute("completedTasks", completedTasksCount(loggedUser.getId()));

        return "profile";
    }

    private int completedBugsCount(long userId) {
        int res = 0;
        ResultSet rs = db.executeQuery("SELECT COUNT(bug_id) FROM bugs WHERE user_id = "+userId+" AND status = 'completed';");
        try {
            while(rs.next()) {
                res = rs.getInt(1);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    private int completedTasksCount(long userId) {
        int res = 0;
        ResultSet rs = db.executeQuery("SELECT COUNT(task_id) FROM tasks WHERE user_id = "+userId+" AND status = 'completed';");
        try {
            while(rs.next()) {
                res = rs.getInt(1);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

}
