package com.example.demovaadin.view;

import java.util.Set;
import com.example.demovaadin.MainView;
import com.example.demovaadin.model.McTask;
import com.example.demovaadin.repo.TaskRepo;
import com.example.demovaadin.view.component.TaskForm;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

// https://www.youtube.com/watch?v=bxy2JgqqKDU
@Route(value = "admin", layout = MainView.class)
@PageTitle("A view only for admins")
// see ServiceJwtUserDetails#getAuthorities
@RolesAllowed({"ROLE_ADM"})
public class AdminView extends VerticalLayout {

    TaskForm form;
    
    public AdminView(TaskRepo repoTask) {
        setSizeFull();  // must first
        
        // configure grid
        var grid = new Grid<McTask>();
        grid.setItems(repoTask.findAll());
        grid.addColumn(McTask::getId).setHeader("Id").setComparator(McTask::getId);
        grid.addColumn(McTask::getName).setHeader("Name").setComparator(McTask::getName);
        grid.addColumn(McTask::getDone).setHeader("Is Done").setComparator(McTask::getDone);
        grid.addColumn(McTask::getCreatedDate).setHeader("Created Date").setTextAlign(ColumnTextAlign.END).setComparator(McTask::getCreatedDate);
        grid.addSelectionListener(event -> {
            Set<McTask> selected = event.getAllSelectedItems();
            Notification.show(selected.size() + " items selected");
        });
        grid.asSingleSelect().addValueChangeListener(e -> editForm(e.getValue()));
        grid.setSizeFull(); // must to be able to use flex

        // configure form
        form = new TaskForm();
        form.setWidth("25em");

        var sideBySide = new HorizontalLayout(grid, form);
        sideBySide.setFlexGrow(2, grid);
        sideBySide.setFlexGrow(1, form);
        sideBySide.addClassName("content");
        sideBySide.setSizeFull();

        add(new H1("Admin Page"), sideBySide);

        closeForm();
    }

    private void editForm(McTask data) {
        if (data == null) {
            closeForm();
        } else {
            form.setData(data);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeForm() {
        form.setData(null);
        form.setVisible(false);
        removeClassName("editing");
    }

}
