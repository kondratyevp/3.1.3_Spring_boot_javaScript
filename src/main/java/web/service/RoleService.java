package web.service;

import web.model.Role;

import java.util.List;

public interface RoleService {

    Role getRoleByRole(String role);

    List<Role> getAllRoles();
}

