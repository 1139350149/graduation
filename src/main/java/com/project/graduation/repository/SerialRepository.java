package com.project.graduation.repository;

import com.project.graduation.entity.WorkSerial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerialRepository extends JpaRepository<WorkSerial, Long> {
    WorkSerial findWorkSerialById(int sid);
}
