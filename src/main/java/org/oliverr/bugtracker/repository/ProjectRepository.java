package org.oliverr.bugtracker.repository;

import org.oliverr.bugtracker.DB;
import org.oliverr.bugtracker.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class ProjectRepository {

    private DB db;
    @Autowired
    public void setDb(DB db) { this.db = db; }

    private BugRepository br;
    @Autowired
    public void setBr(BugRepository br) { this.br = br; }

    private ContributorRepository cr;
    @Autowired
    public void setCr(ContributorRepository cr) { this.cr = cr; }

    private TaskRepository tr;
    @Autowired
    public void setTr(TaskRepository tr) { this.tr = tr; }

    public int getProjectIdByName(String name) {
        int res = 0;
        ResultSet rs = db.executeQuery("SELECT project_id FROM projects WHERE title = '"+name+"';");
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

    public ArrayList<Project> getProjects(long userId) {
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

    public ArrayList<Project> getAllProjects(long userId) {
        ArrayList<Project> projects = new ArrayList<>();
        ResultSet rs = db.executeQuery("SELECT * FROM projects WHERE user_id = "+userId+" ORDER BY project_id DESC;");
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

    public void addToProjects(long userId, String title, String description, String readme) {
        try {
            PreparedStatement ps = db.conn.prepareStatement("INSERT INTO projects(user_id, title, description, readme) VALUES (?, ?, ?, ?);");
            ps.setLong(1, userId);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, readme);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Project getProject(long id) {
        Project project = null;
        ResultSet rs = db.executeQuery("SELECT * FROM projects WHERE project_id = "+id+";");
        try {
            while(rs.next()) {
                project = new Project();
                project.setProjectId(rs.getLong(1));
                project.setUserId(rs.getLong(2));
                project.setTitle(rs.getString(3));
                project.setDescription(rs.getString(4));
                project.setReadme(rs.getString(5));
                project.setTaskCount(tr.getTaskCount(rs.getLong(1)));
                project.setBugCount(br.getBugCount(rs.getLong(1)));
                project.setContributors(cr.contributorsCount(rs.getLong(1)));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return project;
    }

    public boolean isItTheirProject(long projectId, long userId) {
        ResultSet rs = db.executeQuery("SELECT * FROM projects WHERE project_id = "+projectId+" AND user_id = "+userId+";");
        try {
            if(rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

}
