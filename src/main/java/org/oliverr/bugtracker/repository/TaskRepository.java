package org.oliverr.bugtracker.repository;

import org.oliverr.bugtracker.DB;
import org.oliverr.bugtracker.entity.Bug;
import org.oliverr.bugtracker.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class TaskRepository {

    private DB db;

    @Autowired
    public void setDb(DB db) { this.db = db; }

    public int getTaskCount(long projectId) {
        ResultSet rs = db.executeQuery("SELECT COUNT(task_id) FROM tasks WHERE project_id = "+projectId+";");

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

    public ArrayList<Task> getAllTask(long userId) {
        ArrayList<Task> tasks = new ArrayList<>();
        ResultSet rs = db.executeQuery("SELECT * FROM tasks WHERE user_id = "+userId+" ORDER BY task_id DESC;");

        try {
            while(rs.next()) {
                Task task = new Task();
                task.setTaskId(rs.getLong(1));
                task.setProjectId(rs.getLong(2));
                task.setUserId(rs.getLong(3));
                task.setTitle(rs.getString(4));
                task.setDescription(rs.getString(5));
                task.setStatus(rs.getString(6));

                tasks.add(task);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!tasks.isEmpty()) {
            for(Task task : tasks) {
                ResultSet rs2 = db.executeQuery("SELECT title FROM projects WHERE project_id = "+task.getProjectId()+";");
                try {
                    while (rs2.next()) {
                        task.setProjectName(rs2.getString(1));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return tasks;
    }

}
