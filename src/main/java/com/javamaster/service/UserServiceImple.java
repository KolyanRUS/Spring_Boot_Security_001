package com.javamaster.service;

import com.javamaster.dao.*;
import com.javamaster.model.Role;
import com.javamaster.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.*;

import java.util.List;
import java.util.Set;

@Service("userService")
public class UserServiceImple implements UserService {
    private UserRepo dao;

    @Autowired
    public UserServiceImple(UserRepo dao) {
        this.dao = dao;
    }

    @Autowired
    RoleRepo roleRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return dao.getUserByName(s);
    }

    public Set<Role> getRoleByName(String role) {
        return roleRepo.findRoleByRole(role);
    }

    @Override
    public void deleteById(Long l) {
        dao.deleteById(l);
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }

    @Override
    public void save(User user) {
        dao.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) dao.findAll();
    }

    @Override
    public User getUserById(long id) {
        return dao.getUserById(id);
    }
}