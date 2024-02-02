package com.example.demovaadin.ui;

import com.example.demovaadin.MainView;
import com.example.demovaadin.model.McTask;
import com.example.demovaadin.repo.TaskRepo;
import com.example.demovaadin.ui.component.TaskForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.security.RolesAllowed;

// https://www.youtube.com/watch?v=9jhB9vL7KMM&t=7s
@Route(value = "adminPaging", layout = MainView.class)
@PageTitle("A view only for admins using paging")
@RolesAllowed({"ROLE_ADM"})
public class AdminPagingView extends VerticalLayout {
    TaskForm form = new TaskForm();
    private String name;

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

        // gila, cuma gini doank bikin infinite scrollnya
        grid.setItems(VaadinSpringDataHelpers.fromPagingRepository(repoTask));

        grid.asSingleSelect().addValueChangeListener(e -> editForm(e.getValue()));

        add(new H1("Admin w/Paging"), grid);
    }

    // show form in a dialog
    private void editForm(McTask data) {
        if (data == null)
            return;

        var dialog = new Dialog();
        dialog.setHeaderTitle("Task #" + data.getId());
        // var nameTextField = new TextField("Enter name", e -> name = e.getValue());
        // var closeButton = new Button("close", e -> {
        // dialog.close();
        // });
        form.hideButtons();
        form.setData(data);
        dialog.add(form);
        // dialog.add(nameTextField, closeButton);
        dialog.addOpenedChangeListener(e -> {
            // if dialog was closed
            if (!e.isOpened()) {
                // do something with the `name`
                System.out.println("Value entered in Dialog: " + name);
            }
        });

        // header
        var xButton = new Button(new Icon("lumo", "cross"), click -> dialog.close());
        xButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.getHeader().add(xButton);

        // footer
        var deleteButton = new Button("Delete", click -> dialog.close());
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        deleteButton.getStyle().set("margin-right", "auto");

        var cancelButton = new Button("Cancel", click -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        dialog.getFooter().add(deleteButton, cancelButton);

        dialog.open();

    }
}
