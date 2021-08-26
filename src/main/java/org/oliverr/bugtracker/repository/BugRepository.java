package org.oliverr.bugtracker.repository;

import org.oliverr.bugtracker.DB;
import org.oliverr.bugtracker.entity.Bug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class BugRepository {

    private DB db;

    @Autowired
    public void setDb(DB db) { this.db = db; }

    public int getBugCount(long projectId) {
        ResultSet rs = db.executeQuery("SELECT COUNT(bug_id) FROM bugs WHERE project_id = "+projectId+";");

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

    public ArrayList<Bug> getAllBugs(long userId) {
        ArrayList<Bug> bugs = new ArrayList<>();
        ResultSet rs = db.executeQuery("SELECT * FROM bugs WHERE user_id = "+userId+" ORDER BY bug_id DESC;");

        try {
          while(rs.next()) {
              Bug bug = new Bug();
              bug.setBugId(rs.getLong(1));
              bug.setProjectId(rs.getLong(2));
              bug.setUserId(rs.getLong(3));
              bug.setTitle(rs.getString(4));
              bug.setDescription(rs.getString(5));
              bug.setStatus(rs.getString(6));

              bugs.add(bug);
          }
          rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!bugs.isEmpty()) {
            for(Bug bug : bugs) {
                ResultSet rs2 = db.executeQuery("SELECT title FROM projects WHERE project_id = "+bug.getProjectId()+";");
                try {
                    while (rs2.next()) {
                        bug.setProjectName(rs2.getString(1));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return bugs;
    }

}