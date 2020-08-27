package brokilone.todo.controller;

import brokilone.todo.dto.UserDto;
import brokilone.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


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


    @PostMapping("/reg")
    public String registerMewUser(@ModelAttribute("userForm")UserDto userDto, Model model) {
        try{
            userService.registerNewUserAccount(userDto);
            return "home";
        } catch (Exception e) {
            model.addAttribute("error", "Пользователь с таким e-mail уже существует");
            return "reg";
        }

    }

    @GetMapping({ "/", "/login" })
    public String loginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null && error.isEmpty()) {
            model.addAttribute("error", "Использован неверный e-mail/пароль");
        }
        return "login";
    }

}
