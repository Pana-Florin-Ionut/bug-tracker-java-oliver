package org.oliverr.bugtracker.controller;

import org.oliverr.bugtracker.DB;
import org.oliverr.bugtracker.entity.Project;
import org.oliverr.bugtracker.entity.Role;
import org.oliverr.bugtracker.entity.User;
import org.oliverr.bugtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class DashboardController {

    private UserRepository ur;

    @Autowired
    public void setUr(UserRepository ur) { this.ur = ur; }

    private DB db;

    @Autowired
    public void setDb(DB db) { this.db = db; }

    @RequestMapping("/")
    public String dashboard(Model model, Principal principal) {
        User loggedUser = ur.findByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", isAdmin(loggedUser));
        model.addAttribute("pageTitle", "Dashboard | Bug Tracker");
        model.addAttribute("projectsCount", projectsCount(loggedUser.getId()));
        model.addAttribute("projects", getProjects(loggedUser.getId()));
        return "index";
    }

    private boolean isAdmin(User loggedUser) {
        for(Role r : loggedUser.getRoles()) {
            if(r.getRole().equalsIgnoreCase("admin")) return true;
        }
        return false;
    }

    private ArrayList<Project> getProjects(long userId) {
        ArrayList<Project> projects = new ArrayList<>();
        ResultSet rs = db.executeQuery("SELECT * FROM projects WHERE user_id = "+userId+" ORDER BY project_id DESC LIMIT 5;");
        try {
            while(rs.next()) {
                Project p = new Project();
                p.setProjectId(rs.getLong(1));
                p.setUserId(rs.getLong(2));
                p.setTitle(rs.getString(3));
                p.setDescription(rs.getString(4));
                p.setReadme(rs.getString(5));

                projects.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }

    private int projectsCount(long userId) {
        ResultSet rs = db.executeQuery("SELECT COUNT(project_id) FROM projects WHERE user_id = "+userId+";");
        int res = 0;
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
