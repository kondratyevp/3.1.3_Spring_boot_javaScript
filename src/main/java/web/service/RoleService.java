package web.service;

import org.springframework.stereotype.Service;
import web.model.Role;
import web.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByRole (String role){
        return roleRepository.getRoleByRole(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

}
