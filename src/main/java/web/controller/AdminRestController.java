package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.RoleServiceImp;
import web.service.UserService;
import web.service.UserServiceImp;

import java.util.List;

@RestController
public class AdminRestController {

    private final UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

//    @Autowired
//    private RoleServiceImp roleService;

    @GetMapping("/usersRest")
        public List <User> getAllUsers() {
        return userService.getAllUsers();
        }

    @PostMapping(value = "/admin/new")
    public ResponseEntity<?> addNewUser(@RequestBody User user) {
        userService.add(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping ("/users")
    public ResponseEntity<?> updateUser (@RequestBody User user){
        boolean edited = userService.edit(user);
        return edited
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @DeleteMapping ("/admin/users/{id}")
    public ResponseEntity<?> deleteUser (@PathVariable long id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping ("/{id}")
    public ResponseEntity<?> getOne (@PathVariable long id){
        final User user = userService.findUserById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
