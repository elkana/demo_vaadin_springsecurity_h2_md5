package com.example.demovaadin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demovaadin.model.McUser;

public interface UserRepo extends JpaRepository<McUser, String>{
    
}
