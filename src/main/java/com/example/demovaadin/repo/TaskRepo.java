package com.example.demovaadin.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demovaadin.model.McTask;

public interface TaskRepo extends JpaRepository<McTask, Long> {
    List<McTask> findByNameContaining(String text);
}
