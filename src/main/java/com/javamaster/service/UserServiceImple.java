package com.javamaster.service;
import com.javamaster.dao.*;
import com.javamaster.model.Role;
import com.javamaster.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Service("userService")
public class UserServiceImple implements UserService {
    private UserDAO dao;
    @Autowired
    public UserServiceImple(UserDAO dao) {
        this.dao = dao;
    }

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.getUserByName(s);
    }

    public Set<Role> getRoleByName(String role){
        return roleRepo.findRoleByRole(role);
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepo.findAll();
    }

    @Override
    public User getUserById(long id) {
        return userRepo.getUserById(id);
    }
}