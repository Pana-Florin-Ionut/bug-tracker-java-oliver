package org.oliverr.bugtracker.controller;

import org.oliverr.bugtracker.DB;
import org.oliverr.bugtracker.entity.Project;
import org.oliverr.bugtracker.entity.Role;
import org.oliverr.bugtracker.entity.User;
import org.oliverr.bugtracker.repository.*;
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

    private DB db;
    @Autowired
    public void setDb(DB db) { this.db = db; }

    private UserRepository ur;
    @Autowired
    public void setUr(UserRepository ur) { this.ur = ur; }

    private TaskRepository tr;
    @Autowired
    public void setTr(TaskRepository tr) { this.tr = tr; }

    private BugRepository br;
    @Autowired
    public void setBr(BugRepository br) { this.br = br; }

    private ContributorRepository cr;
    @Autowired
    public void setCr(ContributorRepository cr) { this.cr = cr; }

    private NotificationRepository nr;
    @Autowired
    public void setNr(NotificationRepository nr) { this.nr = nr; }

    private TodoRepository todor;
    @Autowired
    public void setTr(TodoRepository todor) { this.todor = todor; }

    @RequestMapping("/")
    public String dashboard(Model model, Principal principal) {
        User loggedUser = ur.findByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", ur.isAdmin(loggedUser));
        model.addAttribute("pageTitle", "Dashboard | Bug Tracker");

        model.addAttribute("projectsCount", projectsCount(loggedUser.getId()));
        model.addAttribute("tasksCount", tasksCount(loggedUser.getId()));
        model.addAttribute("bugsCount", bugsCount(loggedUser.getId()));
        model.addAttribute("isUnread", nr.isThereUnread(loggedUser.getId()));
        model.addAttribute("todos", todor.getTodos(loggedUser.getId()));

        model.addAttribute("projects", getProjects(loggedUser.getId()));
        model.addAttribute("unreadNotification", nr.getUnreadNotifications(loggedUser.getId()));
        return "index";
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
                p.setTaskCount(tr.getTaskCount(rs.getLong(1)));
                p.setBugCount(br.getBugCount(rs.getLong(1)));
                p.setContributors(cr.contributorsCount(rs.getLong(1)));

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

    private int tasksCount(long userId) {
        ResultSet rs = db.executeQuery("SELECT COUNT(task_id) FROM tasks WHERE user_id = "+userId+";");
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

    private int bugsCount(long userId) {
        ResultSet rs = db.executeQuery("SELECT COUNT(bug_id) FROM bugs WHERE user_id = "+userId+";");
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
