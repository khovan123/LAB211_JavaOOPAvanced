package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationService {

    private static Map<Integer, List<String>> notifications = new HashMap<>();

    public static void addMessage(int coachID, String message) {
        if (!notifications.containsKey(coachID)) {
            notifications.put(coachID, new ArrayList<>());
        }
        notifications.get(coachID).add(message);
    }

    public static void display(int coachID) {
        if (!notifications.containsKey(coachID)) {
            return;
        }
        for (int key : notifications.keySet()) {
            if (key == coachID) {
                for (String message : notifications.get(key)) {
                    System.out.println(message);
                }
                notifications.remove(key);
            }
        }
    }

}
