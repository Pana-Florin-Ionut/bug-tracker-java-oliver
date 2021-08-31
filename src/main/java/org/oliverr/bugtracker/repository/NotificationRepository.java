package org.oliverr.bugtracker.repository;

import org.oliverr.bugtracker.DB;
import org.oliverr.bugtracker.entity.Notification;
import org.oliverr.bugtracker.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class NotificationRepository {

    private DB db;

    @Autowired
    public void setDb(DB db) { this.db = db; }

    public boolean isThereUnread(Long userId) {
        ResultSet rs = db.executeQuery("SELECT COUNT(notification_id) FROM notifications WHERE user_id = "+userId+" AND isOpened = 0;");

        boolean res = false;
        try {
            while(rs.next()) {
                if(rs.getInt(1) <= 0) {
                    res = false;
                } else {
                    res = true;
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public ArrayList<Notification> getUnreadNotifications(long userId) {
        ArrayList<Notification> notifications = new ArrayList<>();

        ResultSet rs = db.executeQuery("SELECT * FROM notifications WHERE user_id = "+userId+" AND isOpened = 0 ORDER BY notification_id DESC;");
        try {
            while(rs.next()) {
                Notification p = new Notification();
                p.setNotificationId(rs.getLong(1));
                p.setUserId(rs.getLong(2));
                p.setMessage(rs.getString(3));
                p.setDatetime(rs.getString(4));
                p.setOpened(rs.getInt(5) == 1);

                notifications.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notifications;
    }

    public ArrayList<Notification> getAllNotifications(long userId) {
        ArrayList<Notification> notifications = new ArrayList<>();

        ResultSet rs = db.executeQuery("SELECT * FROM notifications WHERE user_id = "+userId+" ORDER BY notification_id DESC;");
        try {
            while(rs.next()) {
                Notification p = new Notification();
                p.setNotificationId(rs.getLong(1));
                p.setUserId(rs.getLong(2));
                p.setMessage(rs.getString(3));
                p.setDatetime(rs.getString(4));
                p.setOpened(rs.getInt(5) == 1);

                notifications.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notifications;
    }

}
