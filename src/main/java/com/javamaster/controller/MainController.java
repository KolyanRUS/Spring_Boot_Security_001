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
    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login";
    }
    @PostMapping("/login")
    public String postLogin(@RequestParam(value="username") String username,@RequestParam(value="password") String password) {
        return "login";
    }
    @RequestMapping(value="/admin", method=RequestMethod.GET)
    public String getAdminPageGet(Model model) throws SQLException {
        List<User> userList = usi.getAllUsers();
        model.addAttribute("users", userList);
        User user = new User();
        model.addAttribute("tempUser", user);
        return "admin.html";
    }
    @RequestMapping(value="/rest_admin", method=RequestMethod.GET)
    public String getRestAdminPageGet(Model model) throws SQLException {
        List<User> userList = usi.getAllUsers();
        model.addAttribute("users", userList);
        User user = new User();
        model.addAttribute("tempUser", user);
        return "rest_admin.html";
    }
    @RequestMapping(value="/index", method=RequestMethod.GET)
    public String getIndexPageGet() {
        return "index";
    }
    @PostMapping("/index")
    public String postIndex(@RequestParam(value="username") String username,@RequestParam(value="password") String password) {
        return "index";
    }
    @RequestMapping(value="/getuserrs", method=RequestMethod.GET)
    public String getGetusersPageGet(Model model) {
        return "getuserrs";
    }
    @RequestMapping(value="/createuser", method=RequestMethod.GET)
    public String getCreateuserPageGet(Model model) {
        return "createuser";
    }
    @RequestMapping(value="/createuser", method=RequestMethod.POST)
    public String getCreateuserPagePost(Model model, @RequestParam(value="role") String role, @RequestParam(value="name") String name, @RequestParam(value="password") String password, HttpServletResponse resp) throws SQLException {
        Set<Role> userRole = new HashSet<>();
        User u = new User(name,password,userRole);
        usi.save(u);
        return "redirect:/admin";
    }
    @RequestMapping(value = "/updateuser", method = RequestMethod.GET)
    public String getUpdateuserPagePost(Model model, @RequestParam(value="user_id") String user_id) throws SQLException {
        int id;
        try {
            id = Integer.parseInt(user_id);
            User us = usi.getUserById(id);
            model.addAttribute("us",us);
        } catch(Throwable throwable) {
            System.out.println("ERROR::id = Integer.parseInt(user_id)::"+throwable.toString()+"::::user_id::"+user_id);
        }
        String[] rolesArray = new String[2];
        model.addAttribute("rolesArray",rolesArray);
        return "updateuser";
    }
    @RequestMapping(value="/updateuser", method=RequestMethod.POST)
    public String getUpdateuserPagePost(Model model, @RequestParam(value="role") String role, @RequestParam(value="name") String name, @RequestParam(value="password") String password, @RequestParam(value="id") String id) throws SQLException {
        int idd;
        try {
            idd = Integer.parseInt(id);
            User user = new User();
            user.setId(idd);
            user.setName(name);
            user.setPassword(password);
            return "redirect:/admin";
        } catch(Throwable throwable) {
            System.out.println("ERROR::id = Integer.parseInt(req.getParameter(\"idd\"))::"+throwable.toString());
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
                System.out.println("MainController::403-GET--::userDetail::::"+userDetail+"::");
            String username = userDetail.getUsername();
                System.out.println("MainController::403-GET--::username::::"+username+"::");
            boolean nameNotExist = username.equals(null);
                System.out.println("MainController::403-GET--::nameExist::::"+nameNotExist+"::");
            model.addObject("nameExist", nameNotExist);
            model.addObject("username", userDetail.getUsername());
        }
        model.setViewName("403");
        return model;
    }
    @RequestMapping(path = {"/admin/addUser"}, method = RequestMethod.POST)
    String addUser(@ModelAttribute User user,
                   @RequestParam String role,
                   Model model) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRoles(usi.getRoleByName(role.toUpperCase()));
        usi.save(user);
        model.addAttribute("users", usi.getAllUsers());
        return "admin.html";
    }
    @RequestMapping(path = {"/user"}, method = RequestMethod.GET)
    String userPage(Model model){
        User user = (User) usi.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", user);
        return "userPage";
    }
    @RequestMapping(path = {"/admin"}, method = RequestMethod.POST)
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