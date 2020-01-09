package com.javamaster.controller;

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
    @ResponseBody
    public User addUser(@RequestBody User user){
        user.setRoles(service.getRoleByName(user.getRoles().iterator().next().getRole()));
        service.save(user);
        return user;
    }
    @RequestMapping(value = "/openModalById/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") long id){
        System.out.println("------//------");
        User uss = service.getUserById(id);
        System.out.println("------//------");
        System.out.println(uss.getName()+"--"+uss.getId());
        System.out.println("------//------");
        return uss;
    }
    @RequestMapping(value = "/openByName/{name}", method = RequestMethod.GET)
    public User getUser(@PathVariable("name") String name){
        System.out.println("------//------");
        User uss = service.getUserById(1);
        uss.setName(name);
        System.out.println("------//------");
        System.out.println(uss.getName()+"--"+uss.getId());
        System.out.println("------//------");
        System.out.println("------//------");
        return uss;
    }
    @RequestMapping(value = "/editUser",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public User editUser(@RequestBody User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRoles(service.getRoleByName(user.getRoles().iterator().next().getRole()));
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