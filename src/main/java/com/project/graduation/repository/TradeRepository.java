package com.project.graduation.repository;

import com.project.graduation.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findTradesByWorkId(int id);
}
