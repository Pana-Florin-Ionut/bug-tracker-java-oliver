package org.oliverr.bugtracker.repository;

import org.oliverr.bugtracker.DB;
import org.oliverr.bugtracker.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class TodoRepository {

    private DB db;
    @Autowired
    public void setDb(DB db) { this.db = db; }

    public ArrayList<Todo> getTodos(Long userId) {
        ArrayList<Todo> todos = new ArrayList<>();

        ResultSet rs = db.executeQuery("SELECT * FROM todo WHERE user_id = "+userId+" ORDER BY todo_id DESC;");
        try {
            while(rs.next()) {
                Todo todo = new Todo();
                todo.setTodoId(rs.getLong(1));
                todo.setUserId(rs.getLong(2));
                todo.setTask(rs.getString(3));

                todos.add(todo);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return todos;
    }

}
