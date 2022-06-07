package com.project.graduation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class SurfaceController {
    @RequestMapping("/")
    public String home(){
        return "backHome.html";
    }

    @RequestMapping("/login")
    public String login() {
        return "login.html";
    }

    @RequestMapping("/register")
    public String register(){
        return "register.html";
    }

    @RequestMapping("/exit")
    String exit(HttpSession session) {
        session.removeAttribute("loginUser");
        System.out.println(session.getAttribute("loginUser"));
        return "redirect:/";
    }
}
