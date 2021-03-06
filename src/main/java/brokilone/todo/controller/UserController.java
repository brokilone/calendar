package brokilone.todo.controller;

import brokilone.todo.dto.TaskDto;
import brokilone.todo.dto.TaskListDto;
import brokilone.todo.model.Task;
import brokilone.todo.model.User;
import brokilone.todo.service.TaskService;
import brokilone.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;


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

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${theme.path}")
    private String themePath;

    @GetMapping
    public String homePage(Model model, Authentication authentication) {
        addHomeAttributes(model, authentication);
        return "home";
    }

    private void addHomeAttributes(Model model, Authentication authentication) {
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
            taskDto.setFileName(task.getFileName());
            taskDto.setIsExist(true);
            taskDto.setHasFile(task.getFileName()!=null);
            taskListDto.getTasks().add(taskDto);
        }
        model.addAttribute("taskListDto", taskListDto);
        model.addAttribute("taskDto", new TaskDto());
    }

    @PostMapping
    public String saveChanges(Model model, @ModelAttribute("taskListDto") TaskListDto taskListDto,
                              @RequestParam("file") MultipartFile file,
                              @RequestParam("id") String id) throws IOException {
        Task task = taskService.getById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (file != null && !file.isEmpty()) {
            String fullName = getFullName(file);
            file.transferTo(new File(uploadPath + "/" + fullName));
            task.setFileName(fullName);
            taskService.save(task);
            return "redirect:/home";
        }
        taskService.setTasks(taskListDto);
        return "redirect:/home";
    }


    @PostMapping(value = "/addTask")
    public String addTask(Authentication authentication, @Valid Task task,
                           BindingResult result, Model model,
                          @RequestParam("file") MultipartFile file) throws IOException {
        if (result.hasErrors()){
            Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                    fieldError ->
                            fieldError.getField() + "Error",
                    FieldError::getDefaultMessage);
            Map<String, String> errorsMap = result.getFieldErrors().stream().collect(collector);
            model.mergeAttributes(errorsMap);
            model.addAttribute("newTask", task);
            addHomeAttributes(model, authentication);
            return "home";
        } else {
            if (file != null && !file.isEmpty()) {
                String fullName = getFullName(file);
                file.transferTo(new File(uploadPath + "/" + fullName));
                task.setFileName(fullName);
            }
            taskService.add(task, authentication.getName());

        }
        return "redirect:/home";
    }

    @GetMapping(value = "/cabinet")
    public String showCabinet(Model model, Authentication authentication) {
        String currentPrincipalName = authentication.getName();
        User user = userService.findUserByEmail(currentPrincipalName).orElseThrow(() ->
                new RuntimeException("Пользователь не найден"));
        model.addAttribute("user", user);

        return "cabinet";
    }
    @PostMapping(value = "/cabinet")
    public String savePersonalData(Model model, Authentication authentication,
                                   @ModelAttribute ("user") User user) {
        String currentPrincipalName = authentication.getName();
        userService.updateUserData(currentPrincipalName, user);
        return "redirect:/home/cabinet";
    }

    @PostMapping(value = "/changePass")
    public String changePass(Model model, Authentication authentication,
                             @RequestParam("password") String password,
                             @RequestParam("oldpassword") String oldPassword) {
        String email = authentication.getName();
        User user = userService.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException
                        (String.format("Пациент с параметром email='%s' не найден", email)));

        if (!userService.validateOldPassword(user, oldPassword)) {
            model.addAttribute("message", "Введен недействительный пароль!");
        } else {
            userService.saveWithNewPassword(user, password);
            model.addAttribute("message", "Изменения успешно сохранены!");
        }
        model.addAttribute("user", userService.findUserByEmail(authentication.getName()).get());

        return "cabinet";
    }

    private String getFullName(@RequestParam("file") MultipartFile file) {
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String uuidName = UUID.randomUUID().toString();
        return uuidName + "." + file.getOriginalFilename();
    }

    @PostMapping(value = "/changeTheme")
    public String changeTheme(Authentication authentication,
                              @RequestParam("theme") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {

            String fullName = getFullName(file);
            file.transferTo(new File(themePath + "/" + fullName));
            userService.customizeUsersTheme(authentication.getName(), fullName);
        }

        return "redirect:home";
    }
}
