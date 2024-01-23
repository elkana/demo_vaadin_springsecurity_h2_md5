package com.example.demovaadin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demovaadin.model.McTask;

public interface TaskRepo extends JpaRepository<McTask, Long>{
    
}
