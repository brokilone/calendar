package brokilone.todo.controller;

import brokilone.todo.dto.UserDto;
import brokilone.todo.model.User;
import brokilone.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid UserDto userDto,
            HttpServletRequest request, Errors errors, ModelAndView mav) {
        try {
            User registered = userService.registerNewUserAccount(userDto);
        } catch (Exception e) {
            System.out.println("already exists");
            mav.addObject("message", "An account for that username/email already exists.");
            return mav;
        }
        if (errors.hasErrors()) {
            System.out.println("err");
            mav.addObject("error", errors);
            return new ModelAndView("reg", "user", userDto);
        }
        System.out.println("home");
        return new ModelAndView("home");

    }

    @GetMapping({ "/", "/login" })
    public String loginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null && error.isEmpty()) {
            model.addAttribute("error", "Использован неверный e-mail/пароль");
        }
        return "login";
    }

}
