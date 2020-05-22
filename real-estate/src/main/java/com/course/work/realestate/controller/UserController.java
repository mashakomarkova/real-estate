package com.course.work.realestate.controller;

import com.course.work.realestate.entity.Role;
import com.course.work.realestate.entity.User;
import com.course.work.realestate.service.RoleService;
import com.course.work.realestate.service.UserService;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    private UserService userService;
    private RoleService roleService;

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
    public ModelAndView loginPage() {

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/logIn")
    public ModelAndView logIn(@RequestParam String username, @RequestParam String password,
                              HttpServletRequest request) {
        User user = userService.findUserByUsername(username);
        ModelAndView modelAndView = new ModelAndView("redirect:/logIn");
        if (user != null && user.getPassword().equals(password)) {
            request.getSession().setAttribute("user", user);
            //Cookie cookie = new Cookie("user2", "kkkjm");
            //response.addCookie(cookie);
            System.out.println(request.getSession().getAttribute("user"));
            request.getSession().setMaxInactiveInterval(60 * 10);
            return modelAndView;
        } else {
            modelAndView.addObject("incorrectCredentials", "Incorrect credentials");
            return modelAndView;
        }
    }
}
