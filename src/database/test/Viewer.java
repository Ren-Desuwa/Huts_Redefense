package database.test;

import java.util.List;
import java.util.Map;

import javax.swing.JList;

import model.Reading;
import model.User;

public class Viewer {

    //====================================================================
    // Primitive Functions
    //====================================================================

    public static void print(int value) {
        System.out.println("Integer Value: " + value);
    }

    public static void print(boolean value) {
        System.out.println("Boolean Value: " + value);
    }

    public static void print(double value) {
        System.out.println("Double Value: " + value);
    }

    public static void print(String value) {
        if (value == null || value.isEmpty()) {
            System.out.println("String is null or empty.");
            return;
        }
        System.out.println("String Value: " + value);
    }

    //====================================================================
    // User Manager Return Functions
    //====================================================================

    public static void print(User user) {
        if (user == null) {
            System.out.println("User is null.");
            return;
        }

        System.out.println("User Details:");
        System.out.println("  - ID: " + user.getUser_Id());
        System.out.println("  - Username: " + user.getUsername());
        System.out.println("  - Password: " + user.getPassword());
        System.out.println("  - Email: " + user.getEmail());
    }

    //====================================================================
    // Reading Manager Functions
    //====================================================================

    public static void print(Map<?, ?> map) {
        if (!map.isEmpty()) {
            System.out.println("Detailed Data:");
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                System.out.println("Key: " + key + " (Type: " + key.getClass().getName() + ")");
                System.out.println("Value: " + value + " (Type: " + value.getClass().getName() + ")");
            }
        } else {
            System.out.println("The map is empty. No data to display.");
        }
    }

    public static void print(JList<String> list) {
        System.out.println("JList Contents:");
        for (int i = 0; i < list.getModel().getSize(); i++) {
            System.out.println("  - " + list.getModel().getElementAt(i));
        }
    }

    public static void print(List<Reading> readings) {
        if (readings == null || readings.isEmpty()) {
            System.out.println("No readings available.");
            return;
        }

        System.out.println("Reading List:");
        for (Reading reading : readings) {
            System.out.println("  - ID: " + reading.getReading_Id() +
                               ", Date: " + reading.getDate() +
                               ", Type: " + reading.getType() +
                               ", Reading: " + reading.getReading() +
                               ", Rate: " + reading.getRate() +
                               ", Total Price: " + reading.getTotal_Price());
        }
    }
}
