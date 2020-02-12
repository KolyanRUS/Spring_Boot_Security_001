package com.javamaster.controller;

import com.javamaster.model.Role;
import com.javamaster.model.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.javamaster.service.UserServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;//20:38_11.02.2020-в коммите на это время проект полностью готов, если закрыть глаза на лишние строки (комментарии и пр.)

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.*;

@RequestMapping
@Controller
public class MainController {
    @Autowired
    private UserServiceImple usi;

    @GetMapping(value = "/login")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        return "login";
    }

    @GetMapping(value = "/admin")
    public String getAdminPageGet(Model model) throws SQLException {
        List<User> userList = usi.getAllUsers();
        model.addAttribute("users", userList);
        User user = new User();
        model.addAttribute("tempUser", user);
        return "admin.html";
    }

    @GetMapping(value = "/rest_admin")
    public String getRestAdminPageGet(Model model) throws SQLException {
        List<User> userList = usi.getAllUsers();
        model.addAttribute("users", userList);
        User user = new User();
        model.addAttribute("tempUser", user);
        return "rest_admin.html";
    }

    @GetMapping(value = "/index")
    public String getIndexPageGet() {
        return "index";
    }

    @PostMapping("/index")
    public String postIndex(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        return "index";
    }

    @GetMapping(value = "/getuserrs")
    public String getGetusersPageGet(Model model) {
        return "getuserrs";
    }

    @GetMapping(value = "/createuser")
    public String getCreateuserPageGet(Model model) {
        return "createuser";
    }

    @PostMapping(value = "/createuser")
    public String getCreateuserPagePost(Model model, @RequestParam(value = "role") String role, @RequestParam(value = "name") String name, @RequestParam(value = "password") String password, HttpServletResponse resp) throws SQLException {
        Set<Role> userRole = new HashSet<>();
        User u = new User(name, password, userRole);
        usi.save(u);
        return "redirect:/admin";
    }

    @GetMapping(value = "/updateuser")
    public String getUpdateuserPagePost(Model model, @RequestParam(value = "user_id") String user_id) throws SQLException {
        int id;
        try {
            id = Integer.parseInt(user_id);
            User us = usi.getUserById(id);
            model.addAttribute("us", us);
        } catch (Throwable throwable) {
            System.out.println("ERROR::id = Integer.parseInt(user_id)::" + throwable.toString() + "::::user_id::" + user_id);
        }
        String[] rolesArray = new String[2];
        model.addAttribute("rolesArray", rolesArray);
        return "updateuser";
    }

    @PostMapping(value = "/updateuser")
    public String getUpdateuserPagePost(Model model, @RequestParam(value = "role") String role, @RequestParam(value = "name") String name, @RequestParam(value = "password") String password, @RequestParam(value = "id") String id) throws SQLException {
        int newId;
        try {
            newId = Integer.parseInt(id);
            User user = new User();
            user.setId(newId);
            user.setName(name);
            user.setPassword(password);
            return "redirect:/admin";
        } catch (Throwable throwable) {
            System.out.println("ERROR::id = Integer.parseInt(req.getParameter(\"idd\"))::" + throwable.toString());
        }
        return null;
    }

    @GetMapping("/403")
    public ModelAndView accesssDenied() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            System.out.println("MainController::403-GET--::userDetail::::" + userDetail + "::");
            String username = userDetail.getUsername();
            System.out.println("MainController::403-GET--::username::::" + username + "::");
            boolean nameNotExist = username.equals(null);
            System.out.println("MainController::403-GET--::nameExist::::" + nameNotExist + "::");
            model.addObject("nameExist", nameNotExist);
            model.addObject("username", userDetail.getUsername());
        }
        model.setViewName("403");
        return model;
    }

    @PostMapping(path = {"/admin/addUser"})
    String addUser(@ModelAttribute User user,
                   @RequestParam String role,
                   Model model) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRoles(usi.getRoleByName(role.toUpperCase()));
        usi.save(user);
        model.addAttribute("users", usi.getAllUsers());
        return "admin.html";
    }

    @GetMapping(path = {"/user"})
    String userPage(Model model) {
        User user = (User) usi.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", user);
        return "userPage";
    }

    @PostMapping(path = {"/admin"})
    String editUser(@ModelAttribute User user,
                    @RequestParam String role,
                    Model model) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRoles(usi.getRoleByName(role.toUpperCase()));
        usi.save(user);
        model.addAttribute("users", usi.getAllUsers());
        return "admin";
    }
}