package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.List;

@RestController
public class AdminRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/usersRest")
        public List <User> getAllUsers() {
        return userService.getAllUsers();
//        return "users";
        }

    @PostMapping ("/admin/new")
    public User addNewUser (@RequestBody User user){
        userService.add(user);
        return user;
    }

    @PutMapping ("/users")
    public User updateUser (@RequestBody User user){
        userService.edit(user);
        return user;
    }

    @DeleteMapping ("/admin/users/{id}")
    public void deleteUser (@PathVariable long id){
        userService.delete(id);
//        return "User with id=" + id +" was delete";
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable long id) {
        return userService.findUserById(id);
    }
}
