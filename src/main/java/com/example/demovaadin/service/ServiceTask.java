package com.example.demovaadin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demovaadin.model.McTask;
import com.example.demovaadin.repo.TaskRepo;

@Service
public class ServiceTask {
    @Autowired TaskRepo repoTask;

    public List<McTask> findTasks(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return repoTask.findAll();
        } else {
            return repoTask.findByNameContaining(stringFilter);
        }
    }

    public void deleteTask(McTask task) {
        repoTask.delete(task);
    }
    
    public void saveTask(McTask task) {
        if (task == null) {
            System.err.println("Task is null. Are you sure you have connected your form to the application?");
            return;
        }
        repoTask.save(task);
    }
}
