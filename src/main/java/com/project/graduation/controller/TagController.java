package com.project.graduation.controller;


import com.alibaba.fastjson.JSONObject;
import com.project.graduation.Util.Base64Util;
import com.project.graduation.entity.ComprehensiveWork;
import com.project.graduation.entity.User;
import com.project.graduation.entity.Work;
import com.project.graduation.entity.WorkTag;
import com.project.graduation.repository.UserRepository;
import com.project.graduation.repository.WorkRepository;
import com.project.graduation.repository.WorkTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class TagController {
    private final static String IMAGEPATH = "src/main/resources/static/uploadImage/";

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkRepository workRepository;

    @Autowired
    WorkTagRepository tagRepository;

    @RequestMapping("/search")
    public String search(@RequestParam(value = "search") String search, Model model) {
        if ("".equals(search)) {
            return "redirect:/works";
        }
        Set<ComprehensiveWork> works = new HashSet<>();
        List<Work> workList = workRepository.findWorksByNameContains(search);
        for (Work work : workList) {
            ComprehensiveWork temp = null;
            User artist = userRepository.findUserById(work.getArtistId());
            String base64Img = "data:image/png;base64," + Base64Util.imageToBase64Str(IMAGEPATH, work, work.getArtistId());
            temp = new ComprehensiveWork(work, artist, null, base64Img);
            works.add(temp);
        }
        List<WorkTag> tagList = tagRepository.findWorkTagsByTagContains(search);
        for (WorkTag tag : tagList) {
            ComprehensiveWork temp = null;
            Work work = workRepository.findWorkById(tag.getWorkId());
            User artist = userRepository.findUserById(work.getArtistId());
            String base64Img = "data:image/png;base64," + Base64Util.imageToBase64Str(IMAGEPATH, work, work.getArtistId());
            temp = new ComprehensiveWork(work, artist, null, base64Img);
            works.add(temp);
        }
        if (works.size() > 0) {
            model.addAttribute("works", works);
        }
        return "works.html";
    }

    @RequestMapping("/search/tag")
    public String tag(@RequestParam(value = "tag") String tag, Model model) {
        Set<ComprehensiveWork> works = new HashSet<>();
        List<WorkTag> tagList = tagRepository.findWorkTagsByTagContains(tag);
        for (WorkTag workTag : tagList) {
            ComprehensiveWork temp = null;
            Work work = workRepository.findWorkById(workTag.getWorkId());
            User artist = userRepository.findUserById(work.getArtistId());
            String base64Img = "data:image/png;base64," + Base64Util.imageToBase64Str(IMAGEPATH, work, work.getArtistId());
            temp = new ComprehensiveWork(work, artist, null, base64Img);
            works.add(temp);
        }
        if (works.size() > 0) {
            model.addAttribute("works", works);
        }
        return "works.html";
    }
}
