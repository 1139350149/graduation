package com.project.graduation.controller;

import com.project.graduation.entity.Trade;
import com.project.graduation.entity.User;
import com.project.graduation.entity.Work;
import com.project.graduation.repository.TradeRepository;
import com.project.graduation.repository.UserRepository;
import com.project.graduation.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.Date;

@Controller
public class TradeController {
    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    WorkRepository workRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public String buy(@RequestParam(value = "wid") String workId, @RequestParam(value = "aid") String artistId
            , @RequestParam(value = "price") String price, HttpSession session) {
        Date now = new Date(new java.util.Date().getTime());
        if (session.getAttribute("userId") != null) {
            Trade trade = new Trade((Integer) session.getAttribute("userId"), Integer.valueOf(artistId),
                    Integer.valueOf(workId), Double.valueOf(price), now);
            tradeRepository.save(trade);
            Work work = workRepository.findWorkById(Integer.valueOf(workId));
            work.setOwnerId((Integer) session.getAttribute("userId"));
            work.setForSale(false);
            work.setPrice(-1.0);
            workRepository.save(work);
            User user = userRepository.findUserById((Integer) session.getAttribute("userId"));
            System.out.println("redirect:/changeOwner?key=" + work.getKey() + "&newOwner=" + user.getName() + "&workId=" + workId);
            return "redirect:/changeOwner?key=" + work.getKey() + "&newOwner=" + user.getName() + "&workId=" + workId;
        }
        return "redirect:/detail/work?id=" + workId;
    }
}
