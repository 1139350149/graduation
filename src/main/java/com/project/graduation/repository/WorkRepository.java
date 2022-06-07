package com.project.graduation.repository;

import com.project.graduation.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findAllByOrderByIdAsc();
    Work findWorkById(int id);
    List<Work> findWorksByNameContains(String name);
    List<Work> findWorksByArtistId(int id);
    List<Work> findWorksByOwnerIdAndArtistIdNot(int oid, int aid);
}
