package com.example.demovaadin.view;

import java.util.Set;

import com.example.demovaadin.MainView;
import com.example.demovaadin.model.McTask;
import com.example.demovaadin.repo.TaskRepo;
import com.example.demovaadin.service.ServiceTask;
import com.example.demovaadin.view.component.TaskForm;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
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
// see ServiceJwtUserDetails#getAuthorities
@Route(value = "admin", layout = MainView.class)
@PageTitle("A view only for admins")
@RolesAllowed({ "ROLE_ADM" })
public class AdminView extends VerticalLayout {
    ServiceTask svcTask;
    TaskForm form;
    Grid<McTask> grid = new Grid<McTask>();

    public AdminView(ServiceTask svcTask) {
        this.svcTask = svcTask;
        addClassName("list-view");
        setSizeFull(); // must first before css setup

        // configure grid
        grid.addClassNames("contact-grid");
        refreshList();
        grid.addColumn(McTask::getId).setHeader("Id").setComparator(McTask::getId);
        grid.addColumn(McTask::getName).setHeader("Name").setComparator(McTask::getName);
        grid.addColumn(McTask::getDone).setHeader("Is Done").setComparator(McTask::getDone);
        grid.addColumn(McTask::getCreatedDate).setHeader("Created Date").setTextAlign(ColumnTextAlign.END)
                .setComparator(McTask::getCreatedDate);
        grid.addSelectionListener(event -> {
            // Set<McTask> selected = event.getAllSelectedItems();
            // Notification.show(selected.size() + " items selected");
        });
        grid.asSingleSelect().addValueChangeListener(e -> editForm(e.getValue()));
        grid.setSizeFull(); // must to be able to use flex

        // configure form
        form = new TaskForm();
        form.setWidth("25em");
        form.addDeleteListener((e1) -> {
            var d = new ConfirmDialog("Confirmation", "Hapus " + e1.get().getName() + " ?", "Ya", e2 -> {
                svcTask.deleteTask(e1.get());
                Notification.show(e1.get().getName() + " deleted");
                refreshList();
                closeForm();
            }, "Batal", e2 -> {
            });
            d.open();
        });

        form.addSaveListener((e1) -> {
            svcTask.saveTask(e1.get());
            refreshList();
        });

        // create panels
        var sideBySide = new HorizontalLayout(grid, form);
        sideBySide.setFlexGrow(2, grid);
        sideBySide.setFlexGrow(1, form);
        sideBySide.setSizeFull();

        add(new H1("Admin Page"), sideBySide);

        // krn kondisi awal blm terselect, tutup formnya
        closeForm();
    }

    // https://vaadin.com/docs/v23/tutorial/handling-view-state
    private void editForm(McTask data) {
        if (data == null) {
            closeForm();
        } else {
            form.setData(data);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    // utk bisa ngumpetin form, cuma bisa via CSS ternyata :(
    private void closeForm() {
        form.setData(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void refreshList() {
        grid.setItems(svcTask.findTasks(null));
    }
}
