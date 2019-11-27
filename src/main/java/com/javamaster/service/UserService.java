package com.javamaster.service;

import com.javamaster.model.Role;
import com.javamaster.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

interface UserService extends UserDetailsService {
	public void save(User user);
	public List<User> getAllUsers();
	public Set<Role> getRoleByName(String role);
	public User getUserById(long id);
}