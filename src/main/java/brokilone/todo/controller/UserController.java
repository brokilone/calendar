package brokilone.todo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * UserController
 * created by Ksenya_Ushakova at 27.08.2020
 */
@Controller
@PreAuthorize("hasAnyRole({'ROLE_USER', 'ROLE_ADMIN'})")
@RequestMapping(value = "/home")
public class UserController {

    @GetMapping
    public String homePage(){
        return "home";
    }
}
