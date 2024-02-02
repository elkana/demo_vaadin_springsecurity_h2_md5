package com.example.demovaadin.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demovaadin.model.McTask;
import com.example.demovaadin.repo.TaskRepo;

@Service
public class ServiceTask {
    @Autowired
    TaskRepo repoTask;

    @Async
    public CompletableFuture<List<McTask>> findAsyncTasks(String stringFilter) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (stringFilter == null || stringFilter.isEmpty())
            return CompletableFuture.completedFuture(repoTask.findAll());
        return CompletableFuture.completedFuture(repoTask.findByNameContaining(stringFilter));
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
