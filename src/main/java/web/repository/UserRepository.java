package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserById (long id);

    User findUserByEmail (String name);

    User findUserByName (String name);

    void deleteById (long id);
}
