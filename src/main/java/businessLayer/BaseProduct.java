package businessLayer;

import java.io.Serializable;

public class BaseProduct implements MenuItem, Serializable {
    private String name;
    private MenuItemType type;
    private double price;

    public BaseProduct(String name, MenuItemType type, double price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }


    @Override
    public double computePrice() {
        return price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "BaseProduct{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                "}%n";
    }

    public String toString2() {
        return "BaseProduct{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                "}\n";
    }
}
