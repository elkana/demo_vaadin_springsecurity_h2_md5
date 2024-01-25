package com.example.demovaadin.view;

import com.example.demovaadin.MainView;
import com.example.demovaadin.model.McTask;
import com.example.demovaadin.service.ServiceTask;
import com.example.demovaadin.view.component.TaskForm;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.communication.PushMode;

import jakarta.annotation.security.RolesAllowed;

// https://www.youtube.com/watch?v=bxy2JgqqKDU
// https://vaadin.com/docs/latest/advanced/long-running-tasks
// see ServiceJwtUserDetails#getAuthorities
@Route(value = "admin", layout = MainView.class)
@PageTitle("A view only for admins")
@RolesAllowed({ "ROLE_ADM" })
public class AdminView extends VerticalLayout {
    ServiceTask svcTask;
    TaskForm form = new TaskForm();
    Grid<McTask> grid = new Grid<McTask>();
    ProgressBar progressBar = new ProgressBar();
    private Button btnRefresh = new Button("Say hello", e -> refreshList());

    public AdminView(ServiceTask svcTask) {
        this.svcTask = svcTask;
        // https://github.com/vaadin/flow/issues/7365
        UI.getCurrent().getPushConfiguration().setPushMode(PushMode.AUTOMATIC); // must to enable async

        addClassName("list-view");
        setSizeFull(); // must first before css setup

        // configure progressbar
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);
        progressBar.setWidth("200px");

        // configure grid
        grid.addClassNames("contact-grid");
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
        refreshList();

        // configure form
        form.setWidth("25em");
        // setup form callbacks
        form.addDeleteListener((deleteEvent) -> {
            // https://vaadin.com/docs/latest/components/confirm-dialog
            new ConfirmDialog("Confirmation", "Hapus " + deleteEvent.get().getName() + " ?", "Ya", e2 -> {
                svcTask.deleteTask(deleteEvent.get());
                var notify = new Notification(deleteEvent.get().getName() + " deleted");
                notify.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notify.open();
                refreshList();
                closeForm();
            }, "Batal", e2 -> {
            }).open();
        });
        form.addSaveListener((saveEvent) -> {
            svcTask.saveTask(saveEvent.get());
            refreshList();
        });

        // create panels
        var sideBySide = new HorizontalLayout(grid, form);
        sideBySide.setFlexGrow(2, grid);
        sideBySide.setFlexGrow(1, form);
        sideBySide.setSizeFull();

        add(new HorizontalLayout(new H1("Admin Page"), btnRefresh), progressBar,
                sideBySide);

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
        progressBar.setVisible(true);
        btnRefresh.setEnabled(false);

        var ui = UI.getCurrent();
        svcTask.findAsyncTasks(null).thenAccept(result -> {
            ui.access(() -> {
                progressBar.setVisible(false);
                btnRefresh.setEnabled(true);
                grid.setItems(result);
            });
        });
    }
}
