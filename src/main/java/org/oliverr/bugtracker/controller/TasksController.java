package org.oliverr.bugtracker.controller;

import org.oliverr.bugtracker.DB;
import org.oliverr.bugtracker.entity.Task;
import org.oliverr.bugtracker.entity.User;
import org.oliverr.bugtracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class TasksController {

    private DB db;
    @Autowired
    public void setDb(DB db) { this.db = db; }

    private UserRepository ur;
    @Autowired
    public void setUr(UserRepository ur) { this.ur = ur; }

    private TaskRepository tr;
    @Autowired
    public void setTr(TaskRepository tr) { this.tr = tr; }

    private ProjectRepository pr;
    @Autowired
    public void setPr(ProjectRepository pr) { this.pr = pr; }

    @RequestMapping("/tasks")
    public String tasks(Model model, Principal principal) {
        User loggedUser = ur.findByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", ur.isAdmin(loggedUser));
        model.addAttribute("pageTitle", "Tasks | Bug Tracker");

        model.addAttribute("allTask", tr.getAllTask(loggedUser.getId()));
        model.addAttribute("projects", pr.getProjects(loggedUser.getId()));

        model.addAttribute("task", new Task());

        return "tasks";
    }

    @RequestMapping(value = "/tasks/add", method = RequestMethod.POST)
    public String addTask(@ModelAttribute Task task, Principal principal) {
        User sender = ur.findByEmail(principal.getName());

        tr.addTask(pr.getProjectIdByName(task.getProjectName()), sender.getId(), task.getTitle(), task.getDescription(), task.getStatus());

        return "redirect:/tasks";
    }

    @RequestMapping("/task/{taskid}")
    public String task(Model model, @PathVariable(value="taskid") String taskid, Principal principal) {
        User user = ur.findByEmail(principal.getName());
        try {
            if(tr.isItTheirTask(Long.parseLong(taskid), user.getId())) {
                Task task = tr.getTaskById(Long.parseLong(taskid));

                if(task != null) {
                    model.addAttribute("foundTask", task);

                    model.addAttribute("user", user);
                    model.addAttribute("isAdmin", ur.isAdmin(user));
                    model.addAttribute("pageTitle", "Task #"+taskid+" | Bug Tracker");
                    return "task";
                }
                return "error";
            }
        } catch (Exception e) {
            return "error";
        }
        return "error";
    }

}
