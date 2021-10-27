package web.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.model.User;
import web.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void add(User user){
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.delete(findUserById(id));
    }

    public boolean edit(User user) {
        return userRepository.saveAndFlush(user) == user;
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
