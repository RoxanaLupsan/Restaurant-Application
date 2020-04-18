package presentationLayer;

import businessLayer.*;
import dataLayer.RestaurantSerializator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashSet;

public class WaiterGraphicalUserInterface extends Application {
    private TableView<Order> tableView = new TableView();
    private HashSet<MenuItem> menuItems = new HashSet<MenuItem>();

    @Override
    public void start(Stage primaryStage) {
        Restaurant restaurant = new Restaurant();
        if (RestaurantSerializator.readItems("restaurant.srz") != null) {
            restaurant.setMenuItems(RestaurantSerializator.readItems("restaurant.srz"));
        }

        primaryStage.setTitle("Waiter's View");
        tableView.setPrefHeight(300);
        tableView.setPrefWidth(900);

        TableColumn idColumn = new TableColumn("Id");
        idColumn.setPrefWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn dateColumn = new TableColumn("Date");
        dateColumn.setPrefWidth(200);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn tableNumberColumn = new TableColumn("Table Number");
        tableNumberColumn.setPrefWidth(150);
        tableNumberColumn.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));

        tableView.getColumns().addAll(idColumn, dateColumn, tableNumberColumn);

        TextField idInput = new TextField();
        idInput.setPromptText("id");

        TextField tableNumberInput = new TextField();
        tableNumberInput.setPromptText("table number");

        TextField menuItemInput = new TextField();
        menuItemInput.setPromptText("menu items");

        Button nextButton = new Button("next item");
        nextButton.setOnAction((event) -> {
            for (MenuItem menuItem : restaurant.getMenuItems()) {
                if (menuItem.getClass().getSimpleName().equals("BaseProduct")) {
                    if (((BaseProduct) menuItem).getName().equals(menuItemInput.getText())) {
                        menuItems.add(menuItem);
                        break;
                    }
                } else if (((CompositeProduct) menuItem).getName().equals(menuItemInput.getText())) {
                    menuItems.add(menuItem);
                    break;
                }
            }
            menuItemInput.clear();
        });

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(idInput, tableNumberInput, menuItemInput, nextButton);

        Button addButton = new Button("Add");
        addButton.setPrefWidth(100);
        addButton.setOnAction((event) -> {
            Date currentDate = new Date(Calendar.getInstance().getTime().getTime());
            Order order = new Order(
                    Integer.parseInt(idInput.getText()),
                    currentDate,
                    Integer.parseInt(tableNumberInput.getText()));
            restaurant.createOrder(order, menuItems);
            menuItems = new HashSet<MenuItem>();
            tableView.getItems().add(order);
        });

        Button generateBillButton = new Button("Generate Bill");
        generateBillButton.setPrefWidth(100);
        generateBillButton.setOnAction((event) -> {
            restaurant.generateBill(tableView.getSelectionModel().getSelectedItem());
        });

        VBox vbox = new VBox(8);
        vbox.getChildren().addAll(tableView, hBox, addButton, generateBillButton);

        Scene scene = new Scene(vbox, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
