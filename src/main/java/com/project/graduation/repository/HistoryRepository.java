package com.project.graduation.repository;

import com.project.graduation.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findHistoriesByWordId(int workId);
    boolean existsHistoryByUserIdAndViewDate(int userId, Date viewDate);
}
