package brokilone.todo.service;

import brokilone.todo.dto.TaskDto;
import brokilone.todo.dto.TaskListDto;
import brokilone.todo.model.Task;
import brokilone.todo.model.User;
import brokilone.todo.repository.TaskRepo;
import brokilone.todo.repository.UserRepo;
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

    @Autowired
    private UserRepo userRepo;

    public void setTasks(TaskListDto taskListDto, String email){
        User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        List<TaskDto> tasks = taskListDto.getTasks();
        for (TaskDto task : tasks) {
            if (task.getId() == null) continue;
            Task taskFromDb = taskRepo.findById(task.getId()).get();
            if (!task.getIsExist()) {
                taskRepo.delete(taskFromDb);
                continue;
            }
            taskFromDb.setExecPeriod(task.getExecPeriod());
            taskFromDb.setFullDesc(task.getFullDesc());
            taskFromDb.setShortDesc(task.getShortDesc());
            taskRepo.save(taskFromDb);
        }
    }

    public void add(TaskDto taskDto, String email) {
        Task task = new Task();
        User user = userRepo.findByEmail(email).get();
        task.setAuthor(user);
        task.setShortDesc(taskDto.getShortDesc());
        task.setFullDesc(taskDto.getFullDesc());
        task.setExecPeriod(taskDto.getExecPeriod());
        taskRepo.save(task);
    }
}
