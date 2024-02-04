

> to use Form, dont put business logic. just create listeners for callbacks.


## FAQs:

### Q: How to display images ?

A: see `ImageGalleryViewCard.java` in https://github.com/elkana/demo-vaadin-ikn/blob/master/src/main/java/com/example/demoikn/ui/imagegallery/ImageGalleryViewCard.java

intinya buat component sendiri yg menggunakan komponen `new Image()`

---
### Q: How to multiple lines in a cell within Grid ?

A: use CSS. 
see Lit Renderer
see https://vaadin.com/docs/latest/components/grid

---
### Q: How to apply Role ?

A: see `ServiceJwtUserDetails.java `& `AdminView.java`
assume we are using 1 role per user.
on a view:
```java
@Route
@RolesAllowed({"ROLE_ADM"})
public class AdminView extends Div {
    ....
}
```

`ServiceJwtUserDetails.getAuthorities` has job to concat role with convention like `ROLE_XXXX`

in table user you have column like :
```sql
INSERT INTO MC_USER(user_id, role, full_name....)
values ('elkana911', 'ADM', 'elkana 911'....)
```

As you can see, role `ADM` will be concat as `ROLE_ADM` and use it on a view.

> for multiple roles per user please seek another example.

---
### Q: How to use Grid ?

A: see `AdminView.java`

---
### Q: How to show Form in Dialog ?

A: see `AdminPagingView.java`

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

A: see `TaskForm.java`
```java
Binder<McTask> binder = new BeanValidationBinder<>(McTask.class);   // global 
DatePicker dueDate = new DatePicker("Due Date");
...
binder.forField(dueDate).withConverter(new LocalDateToDateConverter())
      .bind(McTask::getDueDate, McTask::setDueDate); // inside constructor
// because DatePicker use LocalDate, you need to convert to java.util.Date using LocalDateToDateConverter

// provide public method to assign object from external
public void setData(McTask data){
    binder.setBean(data);
}
```


---
### Q: How to set Header for all view ?

A: see `MainView.java`

to use:
```java
@Route(value ="adminPaging", layout = MainView.class)
```