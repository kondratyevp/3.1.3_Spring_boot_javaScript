package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import web.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user/lk")
    public String getUserPage (Model model, Principal principal) {
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        return "user";
    }

    @GetMapping("/{id}")
        public String showUser (@PathVariable("id") long id, Model model){
        model.addAttribute("user", userService.findUserById(id));
        return "user";
    }

}
