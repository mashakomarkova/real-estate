package com.course.work.realestate.filter;

import com.course.work.realestate.constant.Constant;
import com.course.work.realestate.entity.Role;
import com.course.work.realestate.entity.User;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Configuration
public class AuthozizationFilter implements Filter {

    private Map<String, List<String>> accessMap = new HashMap<>();
    private List<String> commons = new ArrayList<>();
    private List<String> outOfControl = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        accessMap.put("client", Arrays.asList(Constant.CLIENT));
        accessMap.put("realtor", Arrays.asList(Constant.REALTOR));
        commons = Arrays.asList(Constant.COMMON);
        outOfControl = Arrays.asList(Constant.OUT_OF_CONTROL);

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (accessAllowed(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletRequest.getRequestDispatcher("/")
                    .forward(servletRequest, servletResponse);
        }

    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String commandName = ((HttpServletRequest) request).getRequestURI();

        if (commandName == null || commandName.isEmpty()) {
            return false;
        }
        int count = 0;
        for (int i = 0; i < commandName.length(); i++) {
            if (commandName.charAt(i) == '/') {
                count++;
            }
        }
        if (count >= 2) {
            commandName = commandName.substring(commandName.indexOf("/"), commandName.lastIndexOf("/"));
        }

        if (outOfControl.contains(commandName)) {
            return true;
        }
        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return false;
        }
        User user = (User) session.getAttribute("user");
        Role userRole = user != null ? user.getRole() : null;
        if (userRole == null) {
            return false;
        }

        System.out.println(accessMap.get(userRole.getName()).contains(commandName));
        System.out.println(commons.contains(commandName));
        System.out.println(accessMap);
        System.out.println(commandName);
        return accessMap.get(userRole.getName()).contains(commandName)
                || commons.contains(commandName);

    }

}
