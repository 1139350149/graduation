package com.project.graduation.controller;

import com.project.graduation.Util.Base64Util;
import com.project.graduation.entity.ComprehensiveWork;
import com.project.graduation.entity.User;
import com.project.graduation.entity.Work;
import com.project.graduation.repository.UserRepository;
import com.project.graduation.repository.WorkRepository;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    private final static String IMAGEPATH = "src/main/resources/static/uploadImage/";

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkRepository workRepository;

    @RequestMapping("/login_data")
    public String login(@RequestParam(value = "name") String name, @RequestParam(value = "password") String password
            , @RequestParam(value = "validationCode") String validationCode
            , HttpSession session, Model model, RedirectAttributes redirect) {
        if (validationCode == null || !validationCode.toLowerCase().equals(session.getAttribute("JCCODE").toString().toLowerCase())) {
            return "login";
        }
        if (name.equals("") || password.equals("")) {
            return "login";
        }
        System.out.println("登录嗷@" + name);
        if (!userRepository.existsByName(name)) {
            System.out.println("登录个锤子@" + name + "根本没有");
            model.addAttribute("hint", "用户不存在");
            return "login";
        } else {
            User user = userRepository.findUserByName(name);
            System.out.println(user);
            System.out.println(password);
            if (user.getPassword().equals(password)) {
                System.out.println("登录成功@" + name + "   " + user.getId());
                session.setAttribute("loginUser", name);
                session.setAttribute("userId", user.getId());
                System.out.println(session.getAttribute("loginUser"));
                System.out.println(session.getAttribute("userId"));
                redirect.addFlashAttribute("hint","欢迎， "  + name);
                return "redirect:/works";
            } else {
                System.out.println("登录个锤子@" + name + "密码不对啊");
                model.addAttribute("hint", "密码错误");
                return "login";
            }
        }
    }

    @RequestMapping("/register_data")
    public String register(@RequestParam String name, @RequestParam String password, @RequestParam String address
            , RedirectAttributes redirect) {
        User user = new User(name, password, address);
        System.out.println(user);
        userRepository.save(user);
        redirect.addFlashAttribute("hint", "注册成功");
        return "redirect:/login";
    }

    @RequestMapping(value = "/usernameExistence", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> usernameVerification(@RequestParam String name) {
        HashMap<String, Integer> res = new HashMap<>();
        System.out.println("name verifying 。。。");
        if (userRepository.existsByName(name)) {
            res.put("flag", -2);
        } else {
            res.put("flag", 0);
        }
        return res;
    }

    @RequestMapping(value = "/user")
    public String user(@RequestParam(value = "id") String id, Model model, HttpSession session) {
        User user = userRepository.findUserById(Integer.valueOf(id));
        ComprehensiveWork comprehensiveWork = null;
        boolean self = session.getAttribute("userId") == Integer.valueOf(id);
        model.addAttribute("self", self);
        model.addAttribute("userName", user.getName());
        List<Work> workList = workRepository.findWorksByArtistId(Integer.valueOf(id));
//        int size = Math.min(workList.size(), 3);
        if (workList.size() > 0) {
            List<ComprehensiveWork> artistWorkList = new ArrayList<>();
            for (int i = 0; i < workList.size(); i++) {
                comprehensiveWork = new ComprehensiveWork(workList.get(i), "data:image/png;base64," +
                        Base64Util.imageToBase64Str(IMAGEPATH, workList.get(i), workList.get(i).getArtistId()));
                artistWorkList.add(comprehensiveWork);
            }
            model.addAttribute("artistWorks", artistWorkList);
        }
        workList = workRepository.findWorksByOwnerIdAndArtistIdNot(Integer.valueOf(id), Integer.valueOf(id));
//        size = Math.min(workList.size(), 3);
        if (workList.size() > 0) {
            List<ComprehensiveWork> ownerWorkList = new ArrayList<>();
            for (int i = 0; i < workList.size(); i++) {
                comprehensiveWork = new ComprehensiveWork(workList.get(i), "data:image/png;base64,"
                        + Base64Util.imageToBase64Str(IMAGEPATH, workList.get(i), workList.get(i).getArtistId()));
                ownerWorkList.add(comprehensiveWork);
            }
            model.addAttribute("ownerWorks", ownerWorkList);
        }
        return "user.html";
    }

}
