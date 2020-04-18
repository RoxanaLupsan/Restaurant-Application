package presentationLayer;

import businessLayer.MenuItem;
import businessLayer.*;
import dataLayer.RestaurantSerializator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AdministratorGraphicalUserInterface extends Application {
    private TableView<MenuItem> tableView = new TableView();
    private TextField nameInput, priceInput;
    private ComboBox<String> typeInput;

    @Override
    public void start(Stage primaryStage) {
        Restaurant restaurant = new Restaurant();
        if (RestaurantSerializator.readItems("restaurant.srz") != null) {
            restaurant.setMenuItems(RestaurantSerializator.readItems("restaurant.srz"));
        }

        primaryStage.setTitle("Administrator's View");
        tableView.setPrefHeight(300);
        tableView.setPrefWidth(500);

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(150);
        TableColumn typeColumn = new TableColumn("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeColumn.setPrefWidth(150);
        TableColumn priceColumn = new TableColumn("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setPrefWidth(150);

        tableView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        tableView.getColumns().addAll(nameColumn, typeColumn, priceColumn);

        for (MenuItem menuItem : restaurant.getMenuItems()) {
            tableView.getItems().add(menuItem);
        }

        nameInput = new TextField();
        nameInput.setPromptText("name");

        typeInput = new ComboBox<>();
        typeInput.setPromptText("TYPE");
        typeInput.getItems().addAll("DESSERT", "SOUP", "CHEESE_SPECIALITY", "BREAD_SPECIALITY", "MEET", "GARNITURE");

        priceInput = new TextField();
        priceInput.setPromptText("price");

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(nameInput, typeInput, priceInput);

        Button addButton = new Button("Add");
        addButton.setPrefWidth(100);
        addButton.setOnAction((event) -> {
            MenuItem product = null;
            if (tableView.getSelectionModel().getSelectedItems().isEmpty()) {
                product = new BaseProduct(
                        nameInput.getText(),
                        MenuItemType.valueOf(typeInput.getValue()),
                        Integer.parseInt(priceInput.getText()));
                restaurant.createMenuItem(product);
            } else {
                List<MenuItem> menuItems = new ArrayList<>();
                for (MenuItem menuItem : tableView.getSelectionModel().getSelectedItems())
                    menuItems.add(menuItem);
                product = new CompositeProduct(
                        nameInput.getText(),
                        MenuItemType.valueOf(typeInput.getValue()),
                        Integer.parseInt(priceInput.getText()), menuItems);
                restaurant.createMenuItem(product);
            }
            tableView.getItems().add(product);
            tableView.refresh();
            clearFields();
        });

        Button editButton = new Button("Edit");
        editButton.setPrefWidth(100);
        editButton.setOnAction((event) -> {
            MenuItem product = tableView.getSelectionModel().getSelectedItem();
            MenuItem oldProduct = tableView.getSelectionModel().getSelectedItem();
            System.out.println(product.getClass().getSimpleName());
            if (product.getClass().getSimpleName().equals("BaseProduct")) {
                ((BaseProduct) product).setName(nameInput.getText());
                ((BaseProduct) product).setType(MenuItemType.valueOf(typeInput.getValue()));
                ((BaseProduct) product).setPrice(Integer.parseInt(priceInput.getText()));
            }
            if (product.getClass().getSimpleName().equals("CompositeProduct")) {
                ((CompositeProduct) product).setName(nameInput.getText());
                ((CompositeProduct) product).setType(MenuItemType.valueOf(typeInput.getValue()));
                ((CompositeProduct) product).setPrice(Integer.parseInt(priceInput.getText()));
            }
            restaurant.editMenuItem(oldProduct, product);
            tableView.getItems().set(tableView.getSelectionModel().getSelectedIndex(), product);
            tableView.refresh();
            clearFields();
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setPrefWidth(100);
        deleteButton.setOnAction((event) -> {
            MenuItem product = tableView.getSelectionModel().getSelectedItem();
            restaurant.deleteMenuItem(product);
            tableView.getItems().remove(product);
            tableView.refresh();
        });

        hBox.getChildren().addAll(addButton, editButton, deleteButton);

        VBox vbox = new VBox(8);
        vbox.getChildren().addAll(tableView, hBox);

        Scene scene = new Scene(vbox, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void clearFields() {
        nameInput.clear();
        typeInput.getSelectionModel().clearSelection();
        priceInput.clear();
    }

    public static String showError(String s) {
        return s;
    }



}
