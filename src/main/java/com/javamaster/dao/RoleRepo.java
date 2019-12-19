package com.javamaster.dao;

import com.javamaster.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface RoleRepo extends CrudRepository<Role, Long> {
    public Set<Role> findRoleByRole(String role);
    @Override
    public <S extends Role> S save(S s);
    @Override
    Optional<Role> findById(Long var1);
}