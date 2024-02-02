package com.example.demovaadin.ui.component;

import com.example.demovaadin.model.McTask;
import com.example.demovaadin.model.TaskEnum;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.shared.Registration;

// https://github.com/vaadin/flow-crm-tutorial/tree/v24/src
// jadi form ini tidak ada business logicnya, cuma link event ke external saja, dalam hal ini
// AdminView.java
public class TaskForm extends FormLayout {
    Binder<McTask> binder = new BeanValidationBinder<>(McTask.class);
    // krn pake binding, nama variable wajib global dan sama dgn field di mctask
    // https://vaadin.com/forum/thread/18532065/there-are-no-instance-fields-found-for-automatic-binding
    TextField name = new TextField("Task Name");
    TextArea description = new TextArea("Description", e -> {
        e.getSource().setHelperText(e.getValue().length() + "/" + 150);
    });
    Checkbox done = new Checkbox("Done ?");
    ComboBox<TaskEnum> comboBox = new ComboBox<TaskEnum>("Number", TaskEnum.values());
    DatePicker dueDate = new DatePicker("Due Date");

    Button saveBtn;
    Button deleteBtn;

    public TaskForm() {
        name.setMaxLength(50);
        description.setMaxLength(150);
        // binder.bindInstanceFields(this); ga bisa pake bindInstanceFields gara2
        // checkboxnya beda tipe (boolean
        // vs integer)
        binder.forField(name)
                // Explicit validator instance
                // .withValidator(new EmailValidator("This doesn't look like a valid email
                // address"))
                .bind(McTask::getName, McTask::setName);
        binder.forField(description).bind(McTask::getDescription, McTask::setDescription);

        binder.forField(done).bind(McTask::getDone, McTask::setDone);

        // please also check LocalDate parsedAtServer = LocalDate.parse(dateString,
        // DateTimeFormatter.ISO_DATE);
        binder.forField(dueDate).withConverter(new LocalDateToDateConverter())
                .bind(McTask::getDueDate, McTask::setDueDate);

        binder.forField(comboBox).bind(McTask::getFlag, McTask::setFlag);

        // stretch name
        setColspan(description, 2);
        
        add(name, done, description, comboBox, dueDate, createButtons());
    }

    private HorizontalLayout createButtons() {
        saveBtn = new Button("Save", click -> {
            if (binder.isValid()) {
                fireEvent(new SaveEvent(this, binder.getBean())); // <6>
            }
        });
        saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        deleteBtn = new Button("Delete", click -> {
            // TODO delete via binder, krn utk CRUD dilakukan di AdminView via Event bukan
            // disini
            fireEvent(new DeleteEvent(this, binder.getBean()));
        });
        deleteBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);

        // var cancel = new Button("Cancel");
        // cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return new HorizontalLayout(saveBtn, deleteBtn);
    }

    public void setData(McTask data) {
        binder.setBean(data);
    }

    public void hideButtons() {
        saveBtn.setVisible(false);
        deleteBtn.setVisible(false);
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
