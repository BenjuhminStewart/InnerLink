package edu.uw.tcss450.innerlink.ui.Notification;

import java.util.Arrays;
import java.util.List;

public class NotificationGenerator {
    private static final Notification[] NOTIFICATIONS;
    private static final int COUNT = 20;

    static {
        NOTIFICATIONS = new Notification[COUNT];
        for (int i = 0; i < NOTIFICATIONS.length; i++) {
            NOTIFICATIONS[i] = new Notification("Type X, Y, Z", "Notification Message", "11/" + i + "/21");
        }
    }

    public static List<Notification> getNotificationList() {
        return Arrays.asList(NOTIFICATIONS);
    }

    public static Notification[] getNotifications() {
        return Arrays.copyOf(NOTIFICATIONS, NOTIFICATIONS.length);
    }

    private NotificationGenerator() {}
}
