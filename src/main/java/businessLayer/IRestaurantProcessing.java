package businessLayer;

import java.util.HashSet;

public interface IRestaurantProcessing {

    /**
     * adds a new menu item to the restaurant
     *
     * @pre restaurant is not empty
     * @post item was added
     * @post restaurant is not empty
     */
    void createMenuItem(MenuItem menuItem);

    /**
     * removes menu item from the restaurant
     *
     * @pre restaurant is well formed
     * @pre item exists
     * @post item was deleted
     * @post restaurant is well formed
     */
    void deleteMenuItem(MenuItem menuItem);

    /**
     * edit menu item from the restaurant
     *
     * @pre restaurant is well formed
     * @post restaurant is well formed
     */
    void editMenuItem(MenuItem oldMenuItem, MenuItem newMenuItem);

    /**
     * adds a new order to the restaurant
     *
     * @pre restaurant is not empty
     * @post restaurant is not empty
     */
    void createOrder(Order order, HashSet<MenuItem> menuItem);

    /**
     * computes price for the order
     *
     * @pre restaurant is well formed
     * @pre order != null
     * @pre price > 0
     * @post restaurant is well formed
     */
    double computePriceForOrder(Order order);

    /**
     * generates bill for the order
     *
     * @pre restaurant is well formed
     * @pre order != null
     * @post restaurant is well formed
     */
    void generateBill(Order order);
}
