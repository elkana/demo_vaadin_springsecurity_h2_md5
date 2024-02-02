


> to use Form, dont put business logic. just create listeners for callbacks.


## FAQs:

### Q: How to show Form in Dialog ?

A: see AdminPagingView.java

```java
public class TaskForm extends FormLayout {
    ...
}

TaskForm form = new TaskForm();
var dialog = new Dialog();
form.setData(data); // setup data from external
dialog.add(form);
dialog.open();
```

but how to stretch the form ? Better use `FormLayout` because it's automatically responsive.
to stretch a component, just do `setColspan(description, 2)`

---
### Q: How to use binding in form ?

A: see TaskForm.java
```java
Binder<McTask> binder = new BeanValidationBinder<>(McTask.class);   // global 
...
binder.forField(dueDate).withConverter(new LocalDateToDateConverter())
      .bind(McTask::getDueDate, McTask::setDueDate); // inside constructor
// because Vaadin use LocalDate, you need to convert to java.util.Date using LocalDateToDateConverter

// provide public method to assign object from external
public void setData(McTask data){
    binder.setBean(data);
}
```


---
### Q: How to set Header for all view ?

A: see MainView.java

to use:
```java
@Route(value ="adminPaging", layout = MainView.class)
```