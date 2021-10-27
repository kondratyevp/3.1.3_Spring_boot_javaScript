package web.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import web.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void add(User user);

    void delete(Long id);

    boolean edit(User user);

    User findUserById(Long id);

    List<User> getAllUsers();

}
