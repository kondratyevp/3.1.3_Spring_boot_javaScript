package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.repository.RoleRepository;
import web.repository.UserRepository;
import web.service.UserDetailsServiceImp;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class ControllerSpringBoot {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    public ControllerSpringBoot() { }

    @GetMapping("/users")
    public String listUsers(Model model) {
        Iterable <User> allUsers = userRepository.findAll();
        model.addAttribute("allusers", allUsers);
        return "users";
    }

    @GetMapping("/{id}")
        public String showUser (@PathVariable("id") long id, Model model){
        model.addAttribute("user", userRepository.findUserById(id));
        return "user";
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
        roles.add(roleRepository.getRoleByRole("ROLE_USER"));
        if (roleAdmin != null && roleAdmin.equals("ROLE_ADMIN")) {
            roles.add(roleRepository.getRoleByRole("ROLE_ADMIN"));
        }
        user.setRoles(roles);

        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping ("/admin/{id}/edit")
    public String edit(Model model, @PathVariable ("id") int id){
        User user = userRepository.findUserById(id);
        Set<Role> roles = user.getRoles();
        for (Role role: roles) {
            if (role.equals(roleRepository.getRoleByRole("ROLE_ADMIN"))) {
                model.addAttribute("roleAdmin", true);
            }
        }
        model.addAttribute("user", user);
        return "edit";
    }

    @PatchMapping ("/admin/users/{id}")
    public String update (@ModelAttribute ("user") User user, @PathVariable ("id") int id,
                          @RequestParam(required=false) String roleAdmin){
        Set <Role> roles = new HashSet<>();
        roles.add (roleRepository.getRoleByRole("ROLE_USER"));
        if (roleAdmin!=null && roleAdmin.equals("ROLE_ADMIN")){
            roles.add (roleRepository.getRoleByRole("ROLE_ADMIN"));
        }
        user.setRoles(roles);
        userRepository.save(user);
        return "redirect:/users";
    }

    @DeleteMapping ("/admin/users/{id}")
    public String delete (@PathVariable ("id") int id) {
        userRepository.delete(userRepository.findUserById(id));
        return "redirect:/users";
    }

    @GetMapping(value = "/user/lk")
    public String getUserPage (Model model, Principal principal) {
        model.addAttribute("user", userDetailsServiceImp.loadUserByUsername(principal.getName()));
        return "user";
    }

    @GetMapping("/login")
    public String get(Model model) {
        return "login";
    }

}
