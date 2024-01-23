package com.example.demovaadin.view;

import com.example.demovaadin.MainView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "admin", layout = MainView.class)
@PageTitle("A view only for admins")
@RolesAllowed("ADMINROLE")
public class AdminView extends VerticalLayout {
    // ...
}