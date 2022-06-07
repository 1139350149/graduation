package com.project.graduation.repository;

import com.project.graduation.entity.WorkTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkTagRepository extends JpaRepository<WorkTag, Long> {
    List<WorkTag> findWorkTagsByWorkId(int id);
    List<WorkTag> findWorkTagsByTagContains(String tag);
}
