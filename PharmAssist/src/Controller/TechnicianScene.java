package Controller;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import User.User;

public class TechnicianScene {
    
    public Scene getScene(Stage stage, User user, SceneController sceneController) {

        // -- Set Title: "PharmAssist" -- //
        stage.setTitle("PharmAssist");

        // -- Layout Component Creation -- //
        BorderPane borderPane = new BorderPane();
        HBox hboxTop = new HBox();
        GridPane gridPane = new GridPane();
        HBox hboxBottom = new HBox();

        // ----- TOP BAR ----- //
        // -- hboxTop Components -- //
        Label employee = new Label("Welcome, " + user.getFirstName() + " " + user.getLastName());
        Label roleDashboard = new Label(user.getRole() + " Dashboard");
        Label clockSpaceHolder = new Label("4/16/2026 1:32 PM");
        roleDashboard.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        employee.setPadding(new Insets(10, 50, 50, 10));
        roleDashboard.setPadding(new Insets(10, 10, 10, 10));
        clockSpaceHolder.setPadding(new Insets(10, 10, 50, 50));
        employee.setAlignment(Pos.TOP_LEFT);
        roleDashboard.setAlignment(Pos.BOTTOM_CENTER);
        clockSpaceHolder.setAlignment(Pos.TOP_RIGHT);
        hboxTop.setPadding(new Insets(10));
        hboxTop.setAlignment(Pos.CENTER);
        hboxTop.getChildren().addAll(employee, roleDashboard, clockSpaceHolder);

        // ----- CENTER ----- //
        // -- gridPane Components -- //
        Button queueButton = new Button("Queue");
        Button pendingButton = new Button("Pending");
        Button readyForPickupButton = new Button("Ready For Pickup");
        Button completedButton = new Button("Completed");
        queueButton.setPadding(new Insets(15, 15, 15, 15));
        pendingButton.setPadding(new Insets(15, 15, 15, 15));
        readyForPickupButton.setPadding(new Insets(15, 15, 15, 15));
        completedButton.setPadding(new Insets(15, 15, 15, 15));
        queueButton.setMinWidth(125);
        pendingButton.setMinWidth(125);
        readyForPickupButton.setMinWidth(125);
        completedButton.setMinWidth(125);
        GridPane.setHalignment(queueButton, HPos.RIGHT);
        GridPane.setHalignment(pendingButton, HPos.LEFT);
        GridPane.setMargin(queueButton, new Insets(10, 10, 10, 10));
        GridPane.setMargin(pendingButton, new Insets(10, 10, 10, 10));
        GridPane.setMargin(readyForPickupButton, new Insets(10, 10, 10, 10));
        GridPane.setMargin(completedButton, new Insets(10, 10, 10, 10));
        gridPane.add(queueButton, 0, 0);
        gridPane.add(pendingButton, 1, 0);
        gridPane.add(readyForPickupButton, 0, 1);
        gridPane.add(completedButton, 1, 1);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        // ----- BOTTOM BAR ----- //
        // -- hboxBottom Components -- //
        TextField searchField = new TextField();
        searchField.setPromptText("Search");
        ComboBox<String> searchTypeCombo = new ComboBox<>();
        searchTypeCombo.getItems().addAll("Prescription ID", "Patient Name", "Patient DOB", "Patient Phone Number", "Medication Name", "Generic Name");
        searchTypeCombo.setValue("Prescription ID");
        Button searchButton = new Button("Search");

        SearchHandler searchHandler = new SearchHandler(searchTypeCombo, searchField);

        searchButton.setOnAction(event -> {
            String query = searchField.getText();
            String type = searchTypeCombo.getValue();
            searchHandler.handleSearch(type, query);
        });

        hboxBottom.setPadding(new Insets(10));
        hboxBottom.setAlignment(Pos.CENTER);
        hboxBottom.getChildren().addAll(searchTypeCombo, searchField, searchButton);

        // -- BORDERPANE -- //
        borderPane.setTop(hboxTop);
        borderPane.setCenter(gridPane);
        borderPane.setBottom(hboxBottom);

        return new Scene(borderPane, 600, 500);
    }
}
