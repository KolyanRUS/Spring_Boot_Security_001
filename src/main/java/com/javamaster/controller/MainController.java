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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.*;

@Controller
public class MainController {
    //Мы могли бы расписать эти 2 маппинга отдельно, но смысла дублировать одинаковый код нет.
    // этот метод будет слушать запросы на "/" и "/index"
    /*@GetMapping(value = {"/", "/index"})
    public String getMainPage() {
        return "index";
    }*/
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
        try {
            System.out.println("LOGIN-POST--OUT:::::username::"+username+"::password::"+password+"::");
            /*if(password.equals(usi.getUser(username).getPassword())) {
                return "redirect:/admin";
            }*/
        } catch (Throwable throwable) {
            System.out.println("MainController_login_post::"+throwable.toString());
        }
        return "login";
    }
    @RequestMapping(value="/admin", method=RequestMethod.GET)
    public String getAdminPageGet(Model model) throws SQLException {
        List<User> userList = usi.getAllUsers();
        //
            //СПЕЦИАЛЬНЫЙ КОД ДЛЯ ВЫВОДА ПАРОЛЕЙ ЮЗЕРОВ ИЗ СПИСКА
            int lengthList = userList.size();
            for (int i = 0; i<lengthList; i++) {
                System.out.println("ADMIN-GET--OUT::userList["+Integer.toString(i)+"].password::"+userList.get(i).getPassword()+"::");//
            }
        //
        model.addAttribute("users", userList);
        User user = new User();
        model.addAttribute("tempUser", user);
        return "admin.html";
    }
    /*@RequestMapping(value="/admin", method=RequestMethod.POST)
    public String getAdminPagePost(Model model, @RequestParam(value="ButtonName") String butname, HttpServletResponse resp) throws SQLException {
        if(butname.equals("Delete_All_Users")) {
            usi.deleteAll();
        } else {
            try {
                int number = Integer.parseInt(butname);
                Long num = (long)number;
                usi.deleteById(num);
                return "redirect:/admin";
            } catch (Throwable throwable) {
                System.out.println("throwable[Admin_doPost_deleteUser]: "+throwable.toString());
            }
        }
        return "admin.html";
    }*/
/*
    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }

    @GetMapping("/user")
    public String user() {
        return "/user";
    }

    @GetMapping("/about")
    public String about() {
        return "/about";
    }
*/
    @RequestMapping(value="/index", method=RequestMethod.GET)
    public String getIndexPageGet() {
        return "index";
    }
    @PostMapping("/index")
    public String postIndex(@RequestParam(value="username") String username,@RequestParam(value="password") String password) {
        try {
            System.out.println("INDEX-POST--OUT:::::username::"+username+"::password::"+password+"::");
            /*if(password.equals(usi.getUserById(username).getPassword())) {
                return "redirect:/admin";
            }*/
        } catch (Throwable throwable) {
            System.out.println("MainController_index_post::"+throwable.toString());
        }
        return "index";
    }
    @RequestMapping(value="/createuser", method=RequestMethod.GET)
    public String getCreateuserPageGet(Model model) {
        return "createuser";
    }
    @RequestMapping(value="/createuser", method=RequestMethod.POST)
    public String getCreateuserPagePost(Model model, @RequestParam(value="role") String role, @RequestParam(value="name") String name, @RequestParam(value="password") String password, HttpServletResponse resp) throws SQLException {
        Set<Role> userRole = new HashSet<>();
        /*if(role.equals(usi.getRoleById(1).getRole())) {
            userRole = Collections.singleton(usi.getRoleById(1));
        } else if(role.equals(usi.getRoleById(2).getRole())) {
            userRole = Collections.singleton(usi.getRoleById(2));
        }*/
        User u = new User(name,password,userRole);
        usi.save(u);
        return "redirect:/admin";
    }
    @RequestMapping(value = "/updateuser", method = RequestMethod.GET)
    public String getUpdateuserPagePost(Model model, @RequestParam(value="user_id") String user_id) throws SQLException {
        int id;
        String role = null;
        try {
            id = Integer.parseInt(user_id);
            User us = usi.getUserById(id);
            role = ((Role)(us.getRoles().toArray()[0])).getRole();
            model.addAttribute("us",us);
        } catch(Throwable throwable) {
            System.out.println("ERROR::id = Integer.parseInt(user_id)::"+throwable.toString()+"::::user_id::"+user_id);
        }
        String[] rolesArray = new String[2];
        /*if(role.equals(usi.getRoleById(1).getRole())) {
            ////rolesArray[0] = usi.getRoleById(1).getRole();
            ////rolesArray[1] = usi.getRoleById(2).getRole();
        } else if(role.equals(usi.getRoleById(2).getRole())) {
            rolesArray[0] = usi.getRoleById(2).getRole();
            rolesArray[1] = usi.getRoleById(1).getRole();
        }*/
        model.addAttribute("rolesArray",rolesArray);
        return "updateuser";
    }
    @RequestMapping(value="/updateuser", method=RequestMethod.POST)
    public String getUpdateuserPagePost(Model model, @RequestParam(value="role") String role, @RequestParam(value="name") String name, @RequestParam(value="password") String password, @RequestParam(value="id") String id) throws SQLException {
        int idd;
        Set<Role> userRole = new HashSet<>();
        //Role ris = usi.getRole(role);
        //userRole.add(ris);
        try {
            idd = Integer.parseInt(id);
            User user = new User();
            user.setId(idd);
            user.setName(name);
            user.setPassword(password);
            //Set<Role> roleSet = Collections.singleton((usi.getDao()).getRole(role));
            //user.setRoles(roleSet);
            //usi.updateUser(user);
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
    @RequestMapping(method = RequestMethod.POST)
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