package com.example.demovaadin.config;

import org.springframework.stereotype.Component;
import com.vaadin.flow.server.CustomizedSystemMessages;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.SystemMessages;
import com.vaadin.flow.server.SystemMessagesInfo;
import com.vaadin.flow.server.SystemMessagesProvider;
import com.vaadin.flow.server.VaadinServiceInitListener;

// https://stackoverflow.com/questions/75792738/vaadin-14-9-redirect-on-session-destroy-event
@Component
public class ApplicationServiceInitListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().setSystemMessagesProvider(new SystemMessagesProvider() {
            @Override
            public SystemMessages getSystemMessages(SystemMessagesInfo systemMessagesInfo) {
                CustomizedSystemMessages messages = new CustomizedSystemMessages();
                messages.setSessionExpiredCaption("Session expired");
                messages.setSessionExpiredMessage(
                        "Take note of any unsaved data, and click here or press ESC key to continue.");
                messages.setSessionExpiredURL("/login");
                // messages.setSessionExpiredURL("session-expired.html");
                messages.setSessionExpiredNotificationEnabled(true);
                return messages;
            }
        });

    }

}
