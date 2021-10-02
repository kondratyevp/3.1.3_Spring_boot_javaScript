package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    public AdminController() { }

    @GetMapping("/users")
    public String listUsers(Model model, Principal principal) {
        Iterable <User> allUsers = userService.getAllUsers();
        model.addAttribute("allusers", allUsers);
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("thisUser", userService.loadUserByUsername(principal.getName()));
        return "users";
    }

    @GetMapping ("/admin/new")
    public String newUser (Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/admin/new")
    public String create (@ModelAttribute("newUser") User user,
                          @RequestParam(required=false) String roleAdmin){
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByRole("ROLE_USER"));
        if (roleAdmin != null && roleAdmin.equals("ROLE_ADMIN")) {
            roles.add(roleService.getRoleByRole("ROLE_ADMIN"));
        }
        user.setRoles(roles);

        userService.add(user);
        return "redirect:/users";
    }

    @GetMapping ("/admin/{id}/edit")
    public String edit(Model model, @PathVariable ("id") int id){
        User user = userService.findUserById((long) id);
        Set<Role> roles = user.getRoles();
        for (Role role: roles) {
            if (role.equals(roleService.getRoleByRole("ROLE_ADMIN"))) {
                model.addAttribute("roleAdmin", true);
            }
        }
        model.addAttribute("user", user);
        return "edit";
    }

    @PatchMapping ("/admin/users/{id}")
    public String update (@ModelAttribute ("user") User user, @PathVariable ("id") int id,
                          @RequestParam(value = "select_roles", required = false) String[] role){
        Set <Role> roles = new HashSet<>();
        roles.add (roleService.getRoleByRole("ROLE_USER"));
        if (role!=null) {
            for (String s : role) {
                if (s.equals("ROLE_ADMIN")) {
                    roles.add(roleService.getRoleByRole("ROLE_ADMIN"));
                }
            }
        }
        user.setRoles(roles);
        userService.edit(user);
        return "redirect:/users";
    }


    @DeleteMapping ("/admin/users/{id}")
    public String delete (@PathVariable ("id") int id) {
        userService.delete((long) id);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String get(Model model) {
        return "login";
    }

}
