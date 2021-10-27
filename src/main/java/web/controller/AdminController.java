package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.service.RoleService;
import web.service.UserService;

import java.security.Principal;

@Controller
public class AdminController {

    private UserService userService;

    private RoleService roleService;

    public AdminController() { }

    @Autowired
    public AdminController (UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String listUsers(Model model, Principal principal) {
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("thisUser", userService.loadUserByUsername(principal.getName()));
        return "users";
    }


    @GetMapping("/login")
    public String get(Model model) {
        return "login";
    }


}
