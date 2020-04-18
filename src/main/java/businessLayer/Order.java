package businessLayer;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable {
    private int orderId;
    private Date date;
    private int tableNumber;

    public Order(int orderId, Date date, int tableNumber) {
        this.orderId = orderId;
        this.date = date;
        this.tableNumber = tableNumber;
    }

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return orderId == order.orderId &&
                tableNumber == order.tableNumber &&
                Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, date, tableNumber);
    }

//    System.out.println(dateFormat.format(date));

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", date=" + date +
                ", tableNumber=" + tableNumber +
                "}%n"; //in bill inloc de \n se foloseste %n
    }

    public String toString2() {
        return "Order{" +
                "orderId=" + orderId +
                ", date=" + date +
                ", tableNumber=" + tableNumber +
                "}";
    }



}
