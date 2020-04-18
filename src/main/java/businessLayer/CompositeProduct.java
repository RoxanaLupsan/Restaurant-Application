package businessLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompositeProduct implements MenuItem, Serializable {
    private List<MenuItem> menuItems = new ArrayList<>();
    private String name;
    private MenuItemType type;
    private double price;

    public CompositeProduct(String name, MenuItemType type, double price, List<MenuItem> menuItems) {
        this.menuItems = menuItems;
        this.name = name;
        this.type = type;
        this.price = price + computePrice();
    }

    @Override
    public double computePrice() {
        double price = this.price;
        for (MenuItem menuItem : menuItems) {
            price += menuItem.getPrice();
        }
        return price;
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }


    public void deleteMenuItem(MenuItem menuItem) {
        menuItems.remove(menuItem);
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuItemType getType() {
        return type;
    }

    public void setType(MenuItemType type) {
        this.type = type;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        String res = "CompositeProduct{%n";
        for (MenuItem menuItem : menuItems) {
            res += menuItem.toString();
        }
        res += "name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                "}%n";
        return res;
    }

    public String toString2() {
        String res = "CompositeProduct{\n";
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getClass().getSimpleName().equals("BaseProduct"))
                res += ((BaseProduct) menuItem).toString2();
            else
                res += ((CompositeProduct) menuItem).toString2();
        }
        res += "name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                "}\n";
        return res;
    }

}
