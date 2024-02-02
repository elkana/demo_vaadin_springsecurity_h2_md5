package com.example.demovaadin.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Login | Vaadin CRM")
@AnonymousAllowed
// public class LoginView extends VerticalLayout {
// public LoginView() {
// var login = new LoginForm();
// login.setAction("login");
// add(new H1("Please Login"), login);
// }
// }
public class LoginView extends Composite<LoginOverlay> {
    public LoginView() {
        getContent().setDescription("Deskripsi disini");
        getContent().setTitle("Judul");

        getContent().setOpened(true);
        getContent().setAction("login");
    }
}
