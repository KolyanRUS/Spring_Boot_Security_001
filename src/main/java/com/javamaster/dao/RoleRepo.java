package com.javamaster.dao;

import com.javamaster.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RoleRepo extends CrudRepository<Role, Long> {
    public Set<Role> findRoleByRole(String role);//
}