package brokilone.todo.controller;

import brokilone.todo.dto.UserDto;
import brokilone.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;


/**
 * SecurityController
 * created by Ksenya_Ushakova at 26.08.2020
 */
@Controller
public class SecurityController {

    @Autowired
    private UserService userService;

    @GetMapping("/reg")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "reg";
    }

    @GetMapping("/activate/{code}")
    public String activateUser(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("error", "Активация успешно завершена");
        } else {
            model.addAttribute("error", "Код активации не найден!");
        }
        return "login";
    }


    @PostMapping("/reg")
    public String registerMewUser(@ModelAttribute("user") UserDto userDto, Model model) {
        if (userService.registerNewUserAccount(userDto)) {
            return "login";
        }
        model.addAttribute("error", "Пользователь с таким e-mail уже существует");
        model.addAttribute("user", userDto);
        return "reg";
    }

    @GetMapping({"/", "/login"})
    public String loginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null && error.isEmpty()) {
            model.addAttribute("error", "Использован неверный e-mail/пароль");
        }
        return "login";
    }

}
