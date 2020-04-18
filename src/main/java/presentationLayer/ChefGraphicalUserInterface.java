package presentationLayer;


import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class ChefGraphicalUserInterface implements Observer {
    private TextArea orderTextArea;

    public ChefGraphicalUserInterface() {
        Stage chefStage = new Stage();
        chefStage.setTitle("Chef's Window");
        VBox vBox = new VBox(8);

        Label label = new Label("Orders to be cooked:");
        label.setFont(Font.font("Georgia", FontWeight.NORMAL, 14));

        orderTextArea = new TextArea();
        orderTextArea.setPrefWidth(300);
        orderTextArea.setPrefHeight(300);
        orderTextArea.setEditable(false);
        vBox.getChildren().addAll(label, orderTextArea);

        Scene scene = new Scene(vBox, 800, 450);
        chefStage.setScene(scene);
        chefStage.setResizable(true);
        chefStage.show();
    }


    public void update(Observable o, Object message) {
        synchronized (orderTextArea) {
            String text = orderTextArea.getText();
            text += message.toString() + "\n";
            orderTextArea.setText(text);

        }
    }
}
