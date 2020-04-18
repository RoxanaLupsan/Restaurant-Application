package dataLayer;

import businessLayer.MenuItem;
import presentationLayer.AdministratorGraphicalUserInterface;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;

public class RestaurantSerializator {
    public static HashSet<MenuItem> readItems(String filename) {
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream objects = new ObjectInputStream(file);
            HashSet<MenuItem> items = (HashSet<MenuItem>) objects.readObject();
            return items;
        } catch (Exception e) {
            AdministratorGraphicalUserInterface.showError("Cannot read from restaurant.");
            return null;
        }
    }

    public static void writeItems(HashSet<MenuItem> menuItems, String filename) {
        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream objects = new ObjectOutputStream(file);
            objects.writeObject(menuItems);
        } catch (Exception e) {
            AdministratorGraphicalUserInterface.showError("Cannot write into restaurant.");
        }
    }
}
