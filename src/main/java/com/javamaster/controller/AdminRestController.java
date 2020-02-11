package com.javamaster.controller;

import com.javamaster.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.javamaster.model.User;
import com.javamaster.service.UserService;
import java.util.List;
@RestController
@RequestMapping("/api")
public class AdminRestController {
    @Autowired
    UserService service;
    @RequestMapping(value = "/addUser",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody//01-23-35
    public User addUser(@RequestBody User user){
        String role = null;
        role = user.getRoles().iterator().next().getRole();
        user.setRoles(service.getRoleByName(role));
        service.save(user);
        return user;
    }
    @RequestMapping(value = "/openModalById/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") long id){
        User uss = service.getUserById(id);
        return uss;
    }
    @RequestMapping(value = "/deleteById/{id}", method = RequestMethod.POST)
    public User deleteUser(@PathVariable("id") long id){
        service.deleteById(id);
        return null;
    }
    @RequestMapping(value = "/openByName/{name}", method = RequestMethod.GET)
    public User getUser(@PathVariable("name") String name){
        User uss = service.getUserById(1);
        uss.setName(name);
        return uss;
    }
    @RequestMapping(value = "/editUser",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public User editUser(@RequestBody User user){
        System.out.println("begin_editUser");
        String password = user.getPassword();
        String role = user.getRoles().iterator().next().getRole();
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRoles(service.getRoleByName(role));
        service.save(user);
        return user;
    }
    @RequestMapping(value = "/getUsers",
            method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUsers(){
        return service.getAllUsers();
    }
}