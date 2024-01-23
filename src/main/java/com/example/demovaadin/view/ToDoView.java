package com.example.demovaadin.view;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demovaadin.MainView;
import com.example.demovaadin.service.SecurityService;
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

@Route(value = "todo", layout = MainView.class)
@PageTitle("TODO List| Vaadin CRM")
@PermitAll
public class ToDoView extends VerticalLayout {

    public ToDoView() {
        var todosList = new VerticalLayout();
        var taskField = new TextField();
        var addButton = new Button("Add", e -> {
            var checkbox = new Checkbox(taskField.getValue());
            todosList.add(checkbox);
            taskField.setValue("");

        });
        addButton.addClickShortcut(Key.ENTER);

        add(new HorizontalLayout(new H1("Vaadin Todo")), todosList,
                new HorizontalLayout(taskField, addButton));

    }

}
