package com.example.demovaadin.view;

import com.example.demovaadin.MainView;
import com.example.demovaadin.model.McTask;
import com.example.demovaadin.repo.TaskRepo;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.security.RolesAllowed;

// https://www.youtube.com/watch?v=9jhB9vL7KMM&t=7s
@Route(value = "adminPaging", layout = MainView.class)
@PageTitle("A view only for admins using paging")
@RolesAllowed({"ROLE_ADM"})
public class AdminPagingView extends VerticalLayout {

    public AdminPagingView(TaskRepo repoTask) {
        setSizeFull(); // must first before css setup

        // configure grid
        var grid = new Grid<McTask>();
        grid.addColumn(McTask::getId).setHeader("Id").setComparator(McTask::getId);
        grid.addColumn(McTask::getName).setHeader("Name").setComparator(McTask::getName);
        grid.addColumn(McTask::getDone).setHeader("Is Done").setComparator(McTask::getDone);
        grid.addColumn(McTask::getCreatedDate).setHeader("Created Date")
                .setTextAlign(ColumnTextAlign.END).setComparator(McTask::getCreatedDate);
        grid.setSizeFull(); // must to be able to use flex

        grid.setItems(VaadinSpringDataHelpers.fromPagingRepository(repoTask));

        add(new H1("Admin w/Paging"), grid);
    }

}
