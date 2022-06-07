package com.project.graduation.repository;


import com.project.graduation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByName(String name);
    boolean existsByName(String name);
    User findUserById(int id);
}
