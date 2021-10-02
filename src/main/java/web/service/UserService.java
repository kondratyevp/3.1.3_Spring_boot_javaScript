package web.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.model.User;
import web.repository.UserRepository;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void add(User user){
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.delete(findUserById(id));
    }

    public void edit(User user) {
        userRepository.saveAndFlush(user);
    }

    public User findUserById(Long id){
        return userRepository.findUserById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByName(s);
        if (user != null)
            return user;
        return userRepository.findUserByEmail(s);
    }
}
