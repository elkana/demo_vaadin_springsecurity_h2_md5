package com.example.demovaadin.ui.component;

import com.example.demovaadin.model.McTask;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

// https://github.com/vaadin/flow-crm-tutorial/tree/v24/src
// jadi form ini tidak ada business logicnya, cuma link event ke external saja, dalam hal ini AdminView.java
public class TaskForm extends FormLayout {
    Binder<McTask> binder = new BeanValidationBinder<>(McTask.class);
    // krn pake binding, nama variable wajib global dan sama dgn field di mctask
    // https://vaadin.com/forum/thread/18532065/there-are-no-instance-fields-found-for-automatic-binding
    TextField name = new TextField("Task Name");
    Checkbox done = new Checkbox("Status");

    public TaskForm() {
        // binder.bindInstanceFields(this); ga bisa pake bindInstanceFields gara2
        // checkboxnya beda tipe (boolean
        // vs integer)
        binder.forField(name)
                // Explicit validator instance
                // .withValidator(new EmailValidator("This doesn't look like a valid email
                // address"))
                .bind(McTask::getName, McTask::setName);

        binder.forField(done)
                // .withConverter(new IntegerTo)
                .bind(McTask::getDone, McTask::setDone);

        add(new VerticalLayout(name, done), createButtons());
    }

    private Component createButtons() {
        var save = new Button("Save");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e -> {
            if (binder.isValid()) {
                fireEvent(new SaveEvent(this, binder.getBean())); // <6>
            }
        });

        var delete = new Button("Delete");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(e -> {
            // TODO delete via binder, krn utk CRUD dilakukan di AdminView via Event bukan
            // disini
            fireEvent(new DeleteEvent(this, binder.getBean()));
        });

        // var cancel = new Button("Cancel");
        // cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return new HorizontalLayout(save, delete);
    }

    public void setData(McTask data) {
        binder.setBean(data);
    }

    // provide listeners for caller using specific request (save/delete...etc)
    // https://vaadin.com/docs/latest/create-ui/creating-components/events#defining-an-event-listener
    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) { // <1>
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) { // <2>
        return addListener(SaveEvent.class, listener);
    }

    // MUST create custom events INSIDE TaskForm
    // https://vaadin.com/docs/latest/create-ui/creating-components/events#defining-an-event-listener
    public abstract class TaskFormEvent extends ComponentEvent<TaskForm> {
        private McTask data;

        TaskFormEvent(TaskForm form, McTask data) {
            super(form, false);
            this.data = data;
        }

        public McTask get() {
            return data;
        }
    }

    public class DeleteEvent extends TaskFormEvent {
        DeleteEvent(TaskForm form, McTask data) {
            super(form, data);
        }

    }

    public class SaveEvent extends TaskFormEvent {
        SaveEvent(TaskForm form, McTask data) {
            super(form, data);
        }

    }

}
