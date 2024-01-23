package com.example.demovaadin.view;

import com.example.demovaadin.MainView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "admin", layout = MainView.class)
@PageTitle("A view only for admins")
// see ServiceJwtUserDetails#getAuthorities
@RolesAllowed({"ROLE_ADM"})
public class AdminView extends VerticalLayout {
    public AdminView() {
        add(new H1("Admin Page"));
    }
}