package brokilone.todo.controller;

import brokilone.todo.dto.TaskListDto;
import brokilone.todo.model.User;
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

    @GetMapping
    public String homePage(Model model, Authentication authentication) {
        String currentPrincipalName = authentication.getName();
        User user = userService.findUserByEmail(currentPrincipalName).orElseThrow(() ->
                new RuntimeException("Пользователь не найден"));
        model.addAttribute("user", user);
        TaskListDto taskListDto = new TaskListDto();
        taskListDto.setTasks(user.getTasks());
        model.addAttribute("taskListDto", taskListDto);
        return "home";
    }

    @PostMapping(value = "/saveChanges")
    public String saveChanges(Model model, @ModelAttribute("taskListDto") TaskListDto taskListDto, Authentication authentication) {
        String email = authentication.getName();
        User withTasks = userService.setTasks(taskListDto, email);
        model.addAttribute("user", withTasks);
        model.addAttribute("taskListDto", taskListDto);
        return "home";
    }
}
