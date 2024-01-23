package com.example.demovaadin.view;

import com.example.demovaadin.MainView;
import com.example.demovaadin.model.McTask;
import com.example.demovaadin.repo.TaskRepo;
import com.example.demovaadin.service.SecurityService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

// https://www.youtube.com/watch?v=4Dm1308EwL8   Build a full-stack Spring Boot web app in 10 minutes (tutorial) Vaadin Flow
@Route(value = "todo", layout = MainView.class)
@PageTitle("TODO List| Vaadin CRM")
@PermitAll
public class ToDoView extends VerticalLayout {

    private TaskRepo repoTask;

    public ToDoView(TaskRepo repoTask, SecurityService securityService) {
        this.repoTask = repoTask;

        var todosList = new VerticalLayout();
        repoTask.findAll().forEach(d -> todosList.add(createCheckbox(d)));

        var taskName = new TextField();
        var addButton = new Button("Add", e -> {
            var newTodo = repoTask.save(McTask.builder().name(taskName.getValue())
                    .createdBy(securityService.getAuthenticatedUser().getUsername()).build());
            todosList.add(createCheckbox(newTodo));
            // reset
            taskName.clear();
        });
        addButton.addClickShortcut(Key.ENTER);

        add(
                // header
                new HorizontalLayout(new H1("Vaadin Todo")),
                // content
                todosList,
                // form
                new HorizontalLayout(taskName, addButton));

    }

    private Component createCheckbox(McTask task) {
        return new Checkbox(task.getName(), task.isDone(), e -> {
            task.setDone(task.isDone() ? 0 : 1);
            repoTask.save(task);
        });
    }

}
