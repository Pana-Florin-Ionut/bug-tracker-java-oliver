package org.oliverr.bugtracker.entity;

public class Todo {

    private Long todoId;
    private Long userId;
    private String task;

    public Long getTodoId() { return todoId; }
    public void setTodoId(Long todoId) { this.todoId = todoId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }

}
