package com.project.graduation.controller;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.sdk.Peer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.concurrent.TimeoutException;

@Controller
public class FabricController {
    @Resource
    private Contract contract;

    @Resource
    private Network network;

    @RequestMapping("/getWork")
    @ResponseBody
    public String getWork(String key) throws ContractException {
        byte[] queryAResultBefore = contract.evaluateTransaction("queryWork", key);
        return new String(queryAResultBefore, StandardCharsets.UTF_8);
    }

    @RequestMapping("/addWork")
    public String addWork(String key, String time, String name, String artist, String owner,
                          String width, String height, String material) throws ContractException,
            InterruptedException, TimeoutException {
        byte[] invokeResult = contract.createTransaction("createWork")
                .setEndorsingPeers(network.getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER)))
                .submit(key, time, name, artist, owner, width, height, material);
        String txId = new String(invokeResult, StandardCharsets.UTF_8);
        return "redirect:/works";
    }

    @RequestMapping("/deleteWork")
    @ResponseBody
    public String deleteWork(String key) throws ContractException,
            InterruptedException, TimeoutException {
        byte[] invokeResult = contract.createTransaction("deleteWork")
                .setEndorsingPeers(network.getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER)))
                .submit(key);
        String txId = new String(invokeResult, StandardCharsets.UTF_8);
        return txId;
    }

    @RequestMapping("/changeOwner")
    public String changeOwner(String key, String newOwner, String workId) throws ContractException,
            InterruptedException, TimeoutException {
        byte[] invokeResult = contract.createTransaction("changeOwner")
                .setEndorsingPeers(network.getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER)))
                .submit(key, newOwner);
        String txId = new String(invokeResult, StandardCharsets.UTF_8);
        return "redirect:/detail/work?id=" + workId;
    }

    @RequestMapping("/queryAllWorks")
    @ResponseBody
    public String queryAllWorks() throws ContractException {
        byte[] queryAResultBefore = contract.evaluateTransaction("queryAllWorks");
        return new String(queryAResultBefore, StandardCharsets.UTF_8);
    }
}
