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
        System.out.println("begin_addUser");
        int nik = 0;
        String role = null;
        nik = 2;
        System.out.println("addUser_name=="+user.getName());
        System.out.println("addUser_password=="+user.getPassword());
        System.out.println("addUser_email=="+user.getEmail());
        System.out.println("addUser_getRole()=="+user.getRole());
        role = user.getRoles().iterator().next().getRole();
        System.out.println("addUser_role=="+role);

        nik = 4;
        user.setRoles(service.getRoleByName(role));
        nik = 3;
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
        System.out.println("begin_editUser");
        String password = null;
        String role = null;
        password = user.getPassword();
        role = user.getRoles().iterator().next().getRole();
        System.out.println("editUser_role=="+role);
        System.out.println("editUser_password=="+password);
        //System.out.println("addUser_role=="+role);
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