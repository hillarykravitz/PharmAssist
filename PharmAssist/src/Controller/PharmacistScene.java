package Controller;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import User.User;

public class PharmacistScene {
    
    private TechnicianScene techBase = new TechnicianScene();

    public Scene getScene(Stage stage, User user, SceneController sceneController) {

        Scene scene = techBase.getScene(stage, user, sceneController);

        javafx.scene.layout.BorderPane root = (javafx.scene.layout.BorderPane) scene.getRoot();
        GridPane grid = (GridPane) root.getCenter();

        Button verifyFillBtn = new Button("Final Verification");
        Button inventoryEditBtn = new Button("Manage Inventory");
        Button addRxBtn = new Button("Write New Prescription");
        verifyFillBtn.setPadding(new Insets(15, 15, 15, 15));
        inventoryEditBtn.setPadding(new Insets(15, 15, 15, 15));
        addRxBtn.setPadding(new Insets(15, 15, 15, 15));
        verifyFillBtn.setMinWidth(155);
        inventoryEditBtn.setMinWidth(155);
        addRxBtn.setMinWidth(155);
        GridPane.setHalignment(verifyFillBtn, HPos.LEFT);
        GridPane.setHalignment(inventoryEditBtn, HPos.RIGHT);
        GridPane.setMargin(verifyFillBtn, new Insets(10, 10, 10, 10));
        GridPane.setMargin(inventoryEditBtn, new Insets(10, 10, 10, 10));
        GridPane.setMargin(addRxBtn, new Insets(10, 10, 10, 10));

        String pharmacistStyle = "-fx-background-color: #8e44ad; -fx-text-fill: white; -fx-padding: 15;";
        verifyFillBtn.setStyle(pharmacistStyle);
        inventoryEditBtn.setStyle(pharmacistStyle);
        addRxBtn.setStyle(pharmacistStyle);
        
        verifyFillBtn.setOnAction(e -> {
            techBase.showStatusPopup("Verification Queue",
                sceneController.userService.getDetailedPrescriptions("Waiting For Verification"),
                sceneController, user);
        });

        inventoryEditBtn.setOnAction(e -> {
            techBase.showResultsPopup(sceneController.userService.getAllMedications(),
            "Inventory Management",
            sceneController, user);
        });

        addRxBtn.setOnAction(e -> {
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            VBox rxForm = new VBox(10);
            rxForm.setPadding(new Insets(20));

            TextField pIDField = new TextField();
            pIDField.setPromptText("Patient ID");
            TextField mIDField = new TextField();
            mIDField.setPromptText("Medication ID");
            TextField dIDField = new TextField();
            dIDField.setPromptText("Dosage ID");
            Button submit = new Button("Add to Database");

            submit.setOnAction(ev -> {
                try {
                    int pID = Integer.parseInt(pIDField.getText());
                    int mID = Integer.parseInt(mIDField.getText());
                    int dID = Integer.parseInt(dIDField.getText());

                    boolean success = sceneController.userService.addNewPrescription(pID, mID, dID);
                    if (success) {
                        popup.close();
                        System.out.println("Prescription linked successfully.");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid ID format entered.");
                }
            });
            rxForm.getChildren().addAll(new Label("Create Prescription Record"), pIDField, mIDField, dIDField, submit);
            popup.setScene(new Scene(rxForm));
            popup.show();
        });

        grid.add(verifyFillBtn, 1, 2);
        grid.add(inventoryEditBtn, 0, 3);
        grid.add(addRxBtn, 1, 3);

        return scene;
    }
}
