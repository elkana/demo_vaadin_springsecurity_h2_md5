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

    private TextField taskName;
    private Button addButton;

    public ToDoView() {
        var todosList = new VerticalLayout();
        taskName = new TextField();
        addButton = new Button("Add", e -> {
            todosList.add(new Checkbox(taskName.getValue()));
            //reset
            taskName.setValue("");
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

}
