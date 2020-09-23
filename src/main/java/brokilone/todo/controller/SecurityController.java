package brokilone.todo.controller;

import brokilone.todo.dto.CaptchaResponseDto;
import brokilone.todo.dto.UserDto;
import brokilone.todo.model.User;
import brokilone.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;


/**
 * SecurityController
 * created by Ksenya_Ushakova at 26.08.2020
 */
@Controller
public class SecurityController {

    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${recaptcha.secret}")
    private String secret;

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
    public String registerMewUser(@ModelAttribute("user") UserDto userDto,
                                  @RequestParam ("g-recaptcha-response") String captchaResponse,
                                  Model model) {
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        if(!response.isSuccess()){
            model.addAttribute("captchaError", "Заполните reCaptcha!");
            model.addAttribute("user", userDto);
            return "reg";
        }
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

    @GetMapping ({"/reset"})
    public String showResetPage(){
        return "reset";
    }
    @PostMapping({"/reset"})
    public String resetPassword(@RequestParam ("email") String email, Model model){
        Optional<User> user = userService.findUserByEmail(email);
        if (user.isPresent()) {
            userService.resetUserPassword(user.get());
        }
        model.addAttribute("message", "Если email введен корректно, вы получите письмо со ссылкой " +
                "для сброса пароля.");
        return "reset";
    }

    @GetMapping("/resetPass/{code}")
    public String showResetUserPass(Model model, @PathVariable String code) {
       User user = userService.validateUserCode(code);
        if (user != null) {
            model.addAttribute("email", user.getEmail());
            return "resetPassForm";
        } else {
            model.addAttribute("error", "Ссылка недействительна!");
            return "login";
        }
    }

    @PostMapping("/resetPassForm")
    public String doReset(Model model, @RequestParam ("email") String email,
                          @RequestParam ("password") String password) {
        boolean updatePassword = userService.updatePassword(email, password);
        if (!updatePassword){
            model.addAttribute("error", "Введены некорректные данные");

        }
        model.addAttribute("error", "Пароль успешно изменен");
        return "login";
    }

}
