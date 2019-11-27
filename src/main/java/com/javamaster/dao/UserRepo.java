package com.javamaster.dao;

import com.javamaster.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
    public User getUserByName(String name);
    public User getUserById(long id);
}