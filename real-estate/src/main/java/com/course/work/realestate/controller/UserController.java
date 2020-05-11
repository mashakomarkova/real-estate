package com.course.work.realestate.controller;

import com.course.work.realestate.entity.Role;
import com.course.work.realestate.entity.User;
import com.course.work.realestate.service.RoleService;
import com.course.work.realestate.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private UserService userService;
    private RoleService roleService;
    @Autowired
    private HttpServletRequest request;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public ModelAndView registerPage() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("roles", roleService.findAllRoles());
        return modelAndView;
    }
    @PostMapping("/register")
    public ModelAndView register(@RequestParam String username, @RequestParam String password,
                                 @RequestParam String firstName, @RequestParam String lastName,
                                 @RequestParam String phoneNumber, @RequestParam String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setRole(new Gson().fromJson(role, Role.class));
        userService.createUser(user);
        ModelAndView modelAndView = new ModelAndView("/register");
        modelAndView.addObject("successRegister", "You have successfully registered");
        return modelAndView;
    }

    @GetMapping("/logIn")
    public String loginPage() {
        return "index";
    }

    @PostMapping("/logIn")
    public ModelAndView logIn(@RequestParam String username, @RequestParam String password) {
        User user = userService.findUserByUsername(username);
        ModelAndView modelAndView = new ModelAndView("index");
        if (user != null && user.getPassword().equals(password)) {
            request.getSession().setAttribute("user", user);
            return modelAndView;
        } else {
            modelAndView.addObject("incorrectCredentials", "Incorrect credentials");
            return modelAndView;
        }
    }
}
