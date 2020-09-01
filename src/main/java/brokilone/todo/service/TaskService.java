package brokilone.todo.service;

import brokilone.todo.dto.TaskListDto;
import brokilone.todo.model.Task;
import brokilone.todo.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TaskService
 * created by Ksenya_Ushakova at 01.09.2020
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepo taskRepo;

    public void setTasks(TaskListDto taskListDto){
        List<Task> tasks = taskListDto.getTasks();
        for (Task task : tasks) {
            if (task.getAuthor() == null) continue;
            taskRepo.save(task);
        }
    }
}
