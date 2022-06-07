package com.project.graduation.controller;

import com.project.graduation.Util.Base64Util;
import com.project.graduation.entity.*;
import com.project.graduation.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WorkController {
    private final static String IMAGEPATH = "src/main/resources/static/uploadImage/";

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    SerialRepository serialRepository;

    @Autowired
    WorkRepository workRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkTagRepository tagRepository;

    @Autowired
    HistoryRepository historyRepository;

    @RequestMapping("/works")
    public String works(Model model) {
        System.out.println("list works");
        List<Work> workList = workRepository.findAllByOrderByIdAsc();
        List<ComprehensiveWork> works = new ArrayList<>();
        for (Work work : workList) {
            ComprehensiveWork temp = null;
            User artist = userRepository.findUserById(work.getArtistId());
            String base64Img = "data:image/png;base64," + Base64Util.imageToBase64Str(IMAGEPATH, work, work.getArtistId());
            temp = new ComprehensiveWork(work, artist, null, base64Img);
            works.add(temp);
        }
        model.addAttribute("works", works);
        return "works.html";
    }

    @RequestMapping("/upload")
    public String upload() {
        return "upload.html";
    }

    @RequestMapping(value = "/upload/work", method = RequestMethod.POST)
    public String uploadWork(@RequestParam(value = "picture") MultipartFile file, @RequestParam(value = "tag") String tag
            , @RequestParam(value = "width") String width, @RequestParam(value = "tag-surface") String tagSurface
            , @RequestParam(value = "height") String height, @RequestParam(value = "year") String year
            , @RequestParam(value = "month") String month, @RequestParam(value = "day") String day
            , @RequestParam(value = "remark") String remark, @RequestParam(value = "name") String name
            , @RequestParam(value = "material") String material, HttpSession session) {
        System.out.println("uploadWork");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date timeParse = null;

        WorkSerial workSerial = serialRepository.findWorkSerialById(1);

        String[] tags = tag.split(" ");
        try {
            String realPath = IMAGEPATH + session.getAttribute("userId") + File.separator + "WORK-" + workSerial.getWorkSerial();
            File fileDirectory = new File(realPath);
            if (!fileDirectory.isDirectory()) {
                System.out.println(fileDirectory.mkdirs());
            }
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(realPath + "/" + fileName)));
                out.write(file.getBytes());
                out.flush();
                out.close();
            }

            timeParse = new Date(simpleDateFormat.parse(year + "-" + month + "-" + day).getTime());
            int userId = (Integer) session.getAttribute("userId");
            Work work = new Work("WORK-" + workSerial.getWorkSerial(), userId, Double.valueOf(width)
                    , Double.valueOf(height), material, userId, name, timeParse, remark);
            System.out.println(workSerial.getWorkSerial());
            workSerial.setWorkSerial(workSerial.getWorkSerial() + 1);
            serialRepository.save(workSerial);
            work = workRepository.saveAndFlush(work);
            for (String i : tags) {
                WorkTag workTag = new WorkTag(i, work.getId());
                tagRepository.save(workTag);
            }
            String artistName = userRepository.findUserById(work.getArtistId()).getName();
            System.out.println("forward:/addWork?key=" + work.getKey() + "&time=" + work.getTime() + "&name=" + name
                    + "&artist=" + artistName + "&owner=" + artistName + "&width=" + width + "&height=" + height
                    + "&material=" + material);
            return "redirect:/addWork?key=" + work.getKey() + "&time=" + work.getTime() + "&name=" + name
                    + "&artist=" + artistName + "&owner=" + artistName + "&width=" + width + "&height=" + height
                    + "&material=" + material;
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
            return "IOError";
        }
        return "redirect:/works";
    }

    @RequestMapping(value = "detail/work")
    public String detailWork(@RequestParam(value = "id") String id, Model model, HttpSession session) {
        ComprehensiveWork comprehensiveWork = null;
        Work work = workRepository.findWorkById(Integer.valueOf(id));
        User artist = userRepository.findUserById(work.getArtistId());
        String base64Img = "data:image/png;base64," + Base64Util.imageToBase64Str(IMAGEPATH, work, work.getArtistId());
        List<WorkTag> workTags = tagRepository.findWorkTagsByWorkId(work.getId());
        if (work.getArtistId() == work.getOwnerId()) {
            comprehensiveWork = new ComprehensiveWork(work, artist, artist, base64Img);
        } else {
            User owner = userRepository.findUserById(work.getOwnerId());
            comprehensiveWork = new ComprehensiveWork(work, artist, owner, base64Img);
        }
        List<Trade> trades = tradeRepository.findTradesByWorkId(work.getId());
        List<ComprehensiveTrade> tradeList = new ArrayList<>();
        for (int i = 0; i < trades.size(); i++) {
            User fromUser = userRepository.findUserById(trades.get(i).getFromUserId());
            User toUser = userRepository.findUserById(trades.get(i).getToUserId());
            ComprehensiveTrade temp = new ComprehensiveTrade(trades.get(i), fromUser.getName(), toUser.getName());
            tradeList.add(temp);
        }
        if (tradeList.size() > 0) {
            model.addAttribute("trades", tradeList);
        }
        List<History> histories = historyRepository.findHistoriesByWordId(work.getId());
        boolean isOwner = comprehensiveWork.getOwnerId() == (Integer) session.getAttribute("userId");
        boolean isArtist = comprehensiveWork.getArtistId() == (Integer) session.getAttribute("userId");
        if (!isArtist){
            History history = new History((Integer) session.getAttribute("userId"), work.getId());
            if (!historyRepository.existsHistoryByUserIdAndViewDate(history.getUserId(), history.getViewDate())) {
                historyRepository.save(history);
            }
        }

        List<HistoryUser> historyUsers = new ArrayList<>();
        for (History h : histories) {
            User temp = userRepository.findUserById(h.getUserId());
            HistoryUser historyUser = new HistoryUser(temp, h);
            historyUsers.add(historyUser);
        }
        model.addAttribute("history", isArtist);
        if (historyUsers.size() > 0) {
            model.addAttribute("histories", historyUsers);
        }
        model.addAttribute("work", comprehensiveWork);
        model.addAttribute("buy", !isOwner && comprehensiveWork.isForSale());
        model.addAttribute("tags", workTags);
        return "detail.html";
    }

    @RequestMapping(value = "/pricing", method = RequestMethod.POST)
    public String pricing(@RequestParam(value = "id") String workId, @RequestParam(value = "sale") String sale
            , @RequestParam(value = "price") String price, HttpSession session) {
        Work work = workRepository.findWorkById(Integer.valueOf(workId));
        work.setForSale(Boolean.valueOf(sale));
        work.setPrice(Double.valueOf(price));
        workRepository.save(work);
        return "redirect:/user?id=" + session.getAttribute("userId");
    }
}
