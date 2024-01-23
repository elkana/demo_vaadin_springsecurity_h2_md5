package com.example.demovaadin;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demovaadin.service.SecurityService;
import com.example.demovaadin.view.AdminView;
import com.example.demovaadin.view.DashboardView;
import com.example.demovaadin.view.ToDoView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainView extends AppLayout {

    // https://vaadin.com/docs/latest/tutorial/login-and-authentication
    public MainView(@Autowired SecurityService securityService) {
        if (securityService.getAuthenticatedUser() == null)
            return;
        H2 logo = new H2("Mobile Collection Monitoring");
        // logo.setClassName(null);

        var header = new HorizontalLayout(new DrawerToggle(), logo,
                // logout
                new Button("LogOut " + securityService.getAuthenticatedUser().getUsername(),
                        click -> securityService.logout()));
        header.setWidthFull();
        header.expand(logo);
        addToNavbar(header);

        List<Component> menu = new ArrayList<Component>();
        menu.add(new RouterLink("Dashboard", DashboardView.class));
        menu.add(new RouterLink("To Do", ToDoView.class));
        if (securityService.isAdmin())
        menu.add(new RouterLink("Admin", AdminView.class));

        addToDrawer(new VerticalLayout(menu.toArray(Component[]::new)));

    }

}
