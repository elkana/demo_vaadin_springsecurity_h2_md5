package com.example.demovaadin;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demovaadin.service.SecurityService;
import com.example.demovaadin.view.AdminView;
import com.example.demovaadin.view.DashboardView;
import com.example.demovaadin.view.ToDoView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;

// https://vaadin.com/docs/latest/application/main-view
public class MainView extends AppLayout {
    SecurityService serviceSecurity;
    Avatar avatar = new Avatar();

    // https://vaadin.com/docs/latest/tutorial/login-and-authentication
    public MainView(SecurityService serviceSecurity) {
        if (serviceSecurity.getAuthenticatedUser() == null)
            return;
        this.serviceSecurity = serviceSecurity;
        H2 logo = new H2("Mobile Collection Monitoring");
        logo.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("line-height", "var(--lumo-size-l)").set("margin", "0 var(--lumo-space-m)");

        // configure popup menu on avatar
        var avatarMenu = new ContextMenu();
        avatarMenu.setTarget(avatar);
        avatarMenu.setOpenOnClick(true);
        var subMenuTheme = avatarMenu.addItem("Theme");
        subMenuTheme.getSubMenu().addItem("Light", click -> changeTheme(false));
        subMenuTheme.getSubMenu().addItem("Dark", click -> changeTheme(true));
        avatarMenu.add(new Hr());
        avatarMenu.addItem("Logout", click -> serviceSecurity.logout());

        // configureheader
        var headerLayout = new HorizontalLayout(new DrawerToggle(), logo,
                new Text(serviceSecurity.getUserDetail().getFullName()), avatar);
        headerLayout.setWidthFull();
        headerLayout.expand(logo); // stretch the logo area
        addToNavbar(headerLayout);

        // configure drawer
        var nav = getSideNav();
        var scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);
        addToDrawer(scroller);

        // List<Component> menu = new ArrayList<Component>();
        // menu.add(new RouterLink("Dashboard", DashboardView.class));
        // menu.add(new RouterLink("To Do", ToDoView.class));
        // if (serviceSecurity.isAdmin())
        // menu.add(new RouterLink("Admin", AdminView.class));
        // addToDrawer(new VerticalLayout(menu.toArray(Component[]::new)));
    }

    private SideNav getSideNav() {
        var sideNav = new SideNav();
        sideNav.addItem(new SideNavItem("Dashboard", "/", VaadinIcon.DASHBOARD.create()),
                new SideNavItem("Tasks", "/todo", VaadinIcon.LIST.create()));
        if (serviceSecurity.isAdmin())
            sideNav.addItem(new SideNavItem("Admin", "/admin", VaadinIcon.RECORDS.create()));
        // new SideNavItem("Orders", "/orders", VaadinIcon.CART.create()),
        // new SideNavItem("Customers", "/customers", VaadinIcon.USER_HEART.create()),
        // new SideNavItem("Products", "/products", VaadinIcon.PACKAGE.create()),
        // new SideNavItem("Analytics", "/analytics", VaadinIcon.CHART.create()),
        // );
        return sideNav;
    }

    // https://vaadin.com/blog/toggle-dark-lumo-theme-variant-dynamically
    private void changeTheme(boolean dark) {
        // e.getSource().setLabel(!isDark?"Switch to light": "Switch to dark");
        var js = "document.documentElement.setAttribute('theme', $0)";
        getElement().executeJs(js, dark ? Lumo.DARK : Lumo.LIGHT);
    }

}
