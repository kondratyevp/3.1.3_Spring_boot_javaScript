package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.model.Role;
import web.model.User;

public interface RoleRepository extends JpaRepository <Role, Integer> {
    Role getRoleByRole (String role);
}
