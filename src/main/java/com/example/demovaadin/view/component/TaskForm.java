package com.example.demovaadin.view.component;

import com.example.demovaadin.model.McTask;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

// https://github.com/vaadin/flow-crm-tutorial/tree/v24/src
public class TaskForm extends FormLayout {
    Binder<McTask> binder = new BeanValidationBinder<>(McTask.class);
    // krn pake binding, nama variable wajib global dan sama dgn field di mctask
    // https://vaadin.com/forum/thread/18532065/there-are-no-instance-fields-found-for-automatic-binding
    TextField name = new TextField("Task Name");
    Checkbox done = new Checkbox("Status");

    public TaskForm() {

        // binder.bindInstanceFields(this); ga bisa gara2 checkboxnya boolean vs integer
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
        save.addClickListener(e -> validateAndSave());

        var delete = new Button("Delete");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        var cancel = new Button("Cancel");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        //
    }


    public void setData(McTask data) {
        // this.data = data;
        // binder.readBean(data);
        binder.setBean(data);
    }

    public void setVisible(boolean b) {
        // throw new UnsupportedOperationException("Unimplemented method 'setVisible'");
    }

}
