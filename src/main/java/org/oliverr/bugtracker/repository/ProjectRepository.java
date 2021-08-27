package org.oliverr.bugtracker.repository;

import org.oliverr.bugtracker.DB;
import org.oliverr.bugtracker.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

}
