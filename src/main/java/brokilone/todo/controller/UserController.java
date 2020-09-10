package brokilone.todo.controller;

import brokilone.todo.dto.TaskDto;
import brokilone.todo.dto.TaskListDto;
import brokilone.todo.model.Task;
import brokilone.todo.model.User;
import brokilone.todo.service.TaskService;
import brokilone.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * UserController
 * created by Ksenya_Ushakova at 27.08.2020
 */
@Controller
@PreAuthorize("hasAnyRole({'ROLE_USER', 'ROLE_ADMIN'})")
@RequestMapping(value = "/home/**")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    @GetMapping
    public String homePage(Model model, Authentication authentication) {
        String currentPrincipalName = authentication.getName();
        User user = userService.findUserByEmail(currentPrincipalName).orElseThrow(() ->
                new RuntimeException("Пользователь не найден"));
        model.addAttribute("user", user);
        TaskListDto taskListDto = new TaskListDto();
        System.err.println("Tasks: " + user.getTasks());
        for (Task task : user.getTasks()) {
            TaskDto taskDto = new TaskDto();
            taskDto.setId(task.getId());
            taskDto.setShortDesc(task.getShortDesc());
            taskDto.setFullDesc(task.getFullDesc());
            taskDto.setExecPeriod(task.getExecPeriod());
            taskDto.setIsExist(true);
            taskListDto.getTasks().add(taskDto);
        }
        model.addAttribute("taskListDto", taskListDto);
        model.addAttribute("taskDto", new TaskDto());
        return "home";
    }

    @PostMapping
    public String saveChanges(Model model, @ModelAttribute("taskListDto") TaskListDto taskListDto,
                              Authentication authentication) {
        taskService.setTasks(taskListDto, authentication.getName());
        return "redirect:/home";
    }
    @PostMapping(value = "/addTask")
    public String addTask(Model model, @ModelAttribute("taskDto") TaskDto taskDto,
                          Authentication authentication){
        taskService.add(taskDto, authentication.getName());
        return "redirect:/home";
    }

    @GetMapping(value = "/cabinet")
    public String showCabinet(Model model, Authentication authentication){
        String currentPrincipalName = authentication.getName();
        User user = userService.findUserByEmail(currentPrincipalName).orElseThrow(() ->
                new RuntimeException("Пользователь не найден"));
        model.addAttribute("user", user);
        return "cabinet";
    }
}
