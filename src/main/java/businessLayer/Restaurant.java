package businessLayer;

import dataLayer.FileWriter;
import dataLayer.RestaurantSerializator;
import presentationLayer.ChefGraphicalUserInterface;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Observable;

public class Restaurant extends Observable implements IRestaurantProcessing {

    private HashMap<Order, HashSet<MenuItem>> orders;
    private HashSet<MenuItem> menuItems;
    private ChefGraphicalUserInterface chef = null;

    public Restaurant() {
        orders = new HashMap<Order, HashSet<MenuItem>>();
        menuItems = new HashSet<MenuItem>();
    }

    public HashSet<MenuItem> getMenuItems() {
        assert isWellFormed() : "The restaurant is not well formed";
        return menuItems;
    }

    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }

    public void setMenuItems(HashSet<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @Override
    public void createMenuItem(MenuItem menuItem) {
        assert isWellFormed() : "Restaurant is not well formed";
        menuItems.add(menuItem);
        assert menuItems.contains(menuItem) : "Could not add menu item";
        assert isWellFormed() : "Restaurant is not well formed";
        RestaurantSerializator.writeItems(menuItems, "restaurant.srz");
    }

    @Override
    public void deleteMenuItem(MenuItem menuItem) {
        assert isWellFormed() : "Restaurant is not well formed";
        assert menuItems.contains(menuItem) : "Menu Item could not be found";
        menuItems.remove(menuItem);
        assert !menuItems.contains(menuItem) : "Was not deleted";
        assert isWellFormed() : "Restaurant is not well formed";
        RestaurantSerializator.writeItems(menuItems, "restaurant.srz");
    }

    @Override
    public void editMenuItem(MenuItem oldMenuItem, MenuItem newMenuItem) {
        assert isWellFormed() : "Restaurant is not well formed";
        assert menuItems.contains(oldMenuItem);
        menuItems.remove(oldMenuItem);
        menuItems.add(newMenuItem);
        assert menuItems.contains(newMenuItem);
        assert isWellFormed() : "Restaurant is not well formed";
        RestaurantSerializator.writeItems(menuItems, "restaurant.srz");
    }

    @Override
    public void createOrder(Order order, HashSet<MenuItem> menuItems1) {
        assert isWellFormed() : "Restaurant is not well formed";
        assert order == null : "Order is null";
        orders.put(order, menuItems1);
        if (chef == null) {
            chef = new ChefGraphicalUserInterface();
            this.addObserver(chef);
        }
        notifyObservers(order.toString2());
        for (MenuItem menuItem : orders.get(order)) {
            if (menuItem.getClass().getSimpleName().equals("BaseProduct"))
                notifyObservers(((BaseProduct) menuItem).toString2());
            else
                notifyObservers(((CompositeProduct) menuItem).toString2());
        }
        assert orders.containsKey(order);
        assert isWellFormed() : "Restaurant is not well formed";
    }

    @Override
    public double computePriceForOrder(Order order) {
        double price = 0;
        for (MenuItem menuItem : orders.get(order)) {
            price += menuItem.getPrice();
        }
        assert price > 0 : "Price is lower than 0";
        return price;
    }

    @Override
    public void generateBill(Order order) {
        assert order == null : "Order is null";
        FileWriter fileWriter = new FileWriter();
        fileWriter.openFile();
        fileWriter.addRecords(order);
        for (MenuItem menuItem : orders.get(order)) {
            fileWriter.addRecords(menuItem);
        }
        fileWriter.addRecords(computePriceForOrder(order));
        fileWriter.closeFile();
    }

    /**
     * @invariant isWellFormed()
     */
    private boolean isWellFormed() {
        return orders.keySet() != null && menuItems != null;
    }
}
