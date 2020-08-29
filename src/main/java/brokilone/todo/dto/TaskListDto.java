package brokilone.todo.dto;

import brokilone.todo.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * TaskListDto
 * created by Ksenya_Ushakova at 29.08.2020
 */
public class TaskListDto {
    private List<Task> tasks = new ArrayList<>();


    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
