package com.example.demovaadin;

import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(variant = Lumo.DARK)
@SpringBootApplication
public class DemoVaadinApplication implements AppShellConfigurator {
    @Autowired
    Environment env;

    public static void main(String[] args) {
        SpringApplication.run(DemoVaadinApplication.class, args);
    }

    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {
        if (Arrays.stream(env.getActiveProfiles())
                .anyMatch(env -> env.equalsIgnoreCase("dev-h2"))) {
            System.out.println("Application started ... launching browser now");
            browse("http://localhost:8080/h2-console");
        }
        browse("http://localhost:8080");
    }

    public static void browse(String url) {
        if (!SystemUtils.IS_OS_WINDOWS)
            return;
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
