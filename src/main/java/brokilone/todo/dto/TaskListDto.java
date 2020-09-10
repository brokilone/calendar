package brokilone.todo.dto;

import brokilone.todo.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * TaskListDto
 * created by Ksenya_Ushakova at 29.08.2020
 */
public class TaskListDto {
    private List<TaskDto> tasks = new ArrayList<>();


    public List<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }
}
