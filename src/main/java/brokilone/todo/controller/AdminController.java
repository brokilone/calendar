package brokilone.todo.controller;

import brokilone.todo.model.Role;
import brokilone.todo.model.User;
import brokilone.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * AdminController
 * created by Ksenya_Ushakova at 10.09.2020
 */
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("userList", userService.findAll());
        return "admin";
    }

    @GetMapping("/{userId}")
    public String editForm(Model model, @PathVariable("userId") User user) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "editForm";
    }

    @PostMapping("/{userId}")
    public String saveForm(Model model, @PathVariable("userId") Long id, @ModelAttribute("user") User user) {
        User userFromDb = userService.findUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userFromDb.setEmail(user.getEmail());
        userFromDb.setRole(user.getRole());
        userFromDb.setEnabled(user.isEnabled());
        userService.save(userFromDb);
        return "redirect:/admin";
    }
}
