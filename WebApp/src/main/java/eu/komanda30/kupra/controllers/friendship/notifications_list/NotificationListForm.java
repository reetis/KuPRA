package eu.komanda30.kupra.controllers.friendship.notifications_list;

import java.util.ArrayList;

public class NotificationListForm {
    private ArrayList<NotificationListUnit> notificationListUnits = new ArrayList<NotificationListUnit>();

    public ArrayList<NotificationListUnit> getNotificationListUnits() {
        return notificationListUnits;
    }

    public void setNotificationListUnits(ArrayList<NotificationListUnit> notificationListUnits) {
        this.notificationListUnits = notificationListUnits;
    }

    public void addNotificationListUnit(NotificationListUnit notificationListUnit){
        notificationListUnits.add(notificationListUnit);
    }
}
