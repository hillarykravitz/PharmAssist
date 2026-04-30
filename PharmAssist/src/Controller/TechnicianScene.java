package Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import User.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import SQL.Patient;
import SQL.Prescription;
import SQL.PatientRxDetails;
import SQL.Medication;

public class TechnicianScene {

    //private List<Patient> patients;
    
    
    public Scene getScene(Stage stage, User user, SceneController sceneController) {

        // -- Set Title: "PharmAssist" -- //
        stage.setTitle("PharmAssist - Dashboard");

        // -- Layout Component Creation -- //
        BorderPane borderPane = new BorderPane();
        HBox hboxTop = new HBox();
        GridPane gridPane = new GridPane();
        HBox hboxBottom = new HBox();

        // ----- TOP BAR ----- //
        // -- hboxTop Components -- //
        Label employee = new Label("Welcome, " + user.getFirstName() + " " + user.getLastName());
        Label roleDashboard = new Label(user.getRole() + " Dashboard");
        // Label clockSpaceHolder = new Label("4/16/2026 1:32 PM");
        Label clockLabel = new Label();
        clockLabel.setStyle("-fx-font-size: 14px;");
        clockLabel.setPadding(new Insets(10, 10, 50, 50));
        clockLabel.setAlignment(Pos.TOP_RIGHT);
        clockLabel.setMinWidth(200);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a");
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            clockLabel.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        
        
        
        roleDashboard.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        employee.setPadding(new Insets(10, 50, 50, 10));
        roleDashboard.setPadding(new Insets(10, 10, 10, 10));
        //clockSpaceHolder.setPadding(new Insets(10, 10, 50, 50));
        employee.setAlignment(Pos.TOP_LEFT);
        roleDashboard.setAlignment(Pos.BOTTOM_CENTER);
        //clockSpaceHolder.setAlignment(Pos.TOP_RIGHT);
        hboxTop.setPadding(new Insets(10));
        hboxTop.setAlignment(Pos.CENTER);
        hboxTop.getChildren().addAll(employee, roleDashboard, clockLabel);

        // ----- CENTER ----- //
        // -- gridPane Components -- //
        Button queueButton = new Button("Queue");
        Button pendingButton = new Button("Pending");
        Button readyForPickupButton = new Button("Ready For Pickup");
        Button completedButton = new Button("Completed");
        Button addPatientBtn = new Button("Add New Patient");
        queueButton.setPadding(new Insets(15, 15, 15, 15));
        pendingButton.setPadding(new Insets(15, 15, 15, 15));
        readyForPickupButton.setPadding(new Insets(15, 15, 15, 15));
        completedButton.setPadding(new Insets(15, 15, 15, 15));
        addPatientBtn.setPadding(new Insets(15, 15, 15, 15));
        queueButton.setMinWidth(155);
        pendingButton.setMinWidth(155);
        readyForPickupButton.setMinWidth(155);
        completedButton.setMinWidth(155);
        addPatientBtn.setMinWidth(155);
        GridPane.setHalignment(queueButton, HPos.RIGHT);
        GridPane.setHalignment(pendingButton, HPos.LEFT);
        GridPane.setMargin(queueButton, new Insets(10, 10, 10, 10));
        GridPane.setMargin(pendingButton, new Insets(10, 10, 10, 10));
        GridPane.setMargin(readyForPickupButton, new Insets(10, 10, 10, 10));
        GridPane.setMargin(completedButton, new Insets(10, 10, 10, 10));
        GridPane.setMargin(addPatientBtn, new Insets(10, 10, 10, 10));
        gridPane.add(queueButton, 0, 0);
        gridPane.add(pendingButton, 1, 0);
        gridPane.add(readyForPickupButton, 0, 1);
        gridPane.add(completedButton, 1, 1);
        gridPane.add(addPatientBtn, 0, 2);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        // ----- Queue Stages Event Component(s) ----- //
        // --- Queue Button (Shows everything) --- //
        queueButton.setOnAction(e -> {
            List<Prescription> allRx = sceneController.userService.getDetailedPrescriptions("All");
            showStatusPopup("Pharmacy Queue", allRx, sceneController, user);
        });

        // --- Pending Button (Shows only Pending) --- //
        pendingButton.setOnAction(e -> {
            List<Prescription> list = sceneController.userService.getDetailedPrescriptions("Pending");
            showStatusPopup("Pending", list, sceneController, user);
        });

        // --- Ready For Pickup Button (Shows only Ready for Pickup) --- //
        readyForPickupButton.setOnAction(e -> {
            List<Prescription> list = sceneController.userService.getDetailedPrescriptions("Ready For Pickup");
            showStatusPopup("Ready For Pickup", list, sceneController, user);
        });

        // --- Completed Button (Shows only Completed) --- //
        completedButton.setOnAction(e -> {
            List<Prescription> list = sceneController.userService.getDetailedPrescriptions("Complete");
            showStatusPopup("Complete", list, sceneController, user);
        });

        addPatientBtn.setOnAction(e -> {
            Stage popup = new Stage();
            VBox layout = new VBox(10);
            layout.setPadding(new Insets(20));

            TextField fName = new TextField();
            fName.setPromptText("First Name");
            TextField lName = new TextField();
            lName.setPromptText("Last Name");
            TextField phone = new TextField(); 
            phone.setPromptText("Phone (XXX-XXX-XXXX");
            DatePicker dobPicker = new DatePicker();

            Button saveBtn = new Button("Save Patient");
            saveBtn.setOnAction(event -> {
                boolean success = sceneController.userService.addPatient(
                    fName.getText(), lName.getText(), java.sql.Date.valueOf(dobPicker.getValue()), phone.getText()
                );
                if (success) popup.close();
            });
            layout.getChildren().addAll(new Label("New Patient Entry"), fName, lName, dobPicker, phone, saveBtn);
            popup.setScene(new Scene(layout));
            popup.show();
        });

        // ----- BOTTOM BAR ----- //
        // --- hboxBottom Components --- //
        TextField searchField = new TextField();
        searchField.setPromptText("Search");
        ComboBox<String> searchTypeCombo = new ComboBox<>();
        searchTypeCombo.getItems().addAll("Prescription ID", "Patient Name", "Patient DOB", "Patient Phone Number", "Medication Name", "Generic Name");
        searchTypeCombo.setValue("Prescription ID");
        Button searchButton = new Button("Search");

        SearchHandler searchHandler = new SearchHandler(searchTypeCombo, searchField, sceneController.userService);

        searchButton.setOnAction(event -> {
            String query = searchField.getText();
            String type = searchTypeCombo.getValue();
            List<?> results = searchHandler.handleSearch(type, query);
            if (results != null && !results.isEmpty()) {
                showResultsPopup(results, type, sceneController, user);
            } else {
                System.out.println("No matching records found.");
            }
        });

        hboxBottom.setPadding(new Insets(10));
        hboxBottom.setAlignment(Pos.CENTER);
        hboxBottom.getChildren().addAll(searchTypeCombo, searchField, searchButton);

        // -- BORDERPANE -- //
        borderPane.setTop(hboxTop);
        borderPane.setCenter(gridPane);
        borderPane.setBottom(hboxBottom);

        return new Scene(borderPane, 700, 500);
    }

    // ----- showResultsPopup Method Implementation ----- //
    public void showResultsPopup(List<?> results, String type, SceneController sceneController, User user) {
        if (results == null || results.isEmpty()) {
            System.out.println("No results found for: " + type);
            return;
        }

        Stage resultsStage = new Stage();
        resultsStage.setTitle("Search Results: " + type);
        resultsStage.initModality(Modality.APPLICATION_MODAL);

        TableView<Object> table = new TableView<>();
        ObservableList<Object> data = FXCollections.observableArrayList(results);
        table.setItems(data);
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showExpandedDetails(newSelection, sceneController, user);
            }
        });

        Object firstItem = results.get(0);

        if (firstItem instanceof Patient) {

            TableColumn<Object, String> idCol = new TableColumn<>("ID");
            idCol.setCellValueFactory(new PropertyValueFactory<>("patientID"));

            TableColumn<Object, String> fnCol = new TableColumn<>("First Name");
            fnCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

            TableColumn<Object, String> lnCol = new TableColumn<>("Last Name");
            lnCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

            TableColumn<Object, java.sql.Date> dobCol = new TableColumn<>("Birth Date");
            dobCol.setCellValueFactory(new PropertyValueFactory<>("birthdate"));

            table.getColumns().addAll(idCol, fnCol, lnCol, dobCol);

        } else if (firstItem instanceof Prescription) {

            TableColumn<Object, Integer> idCol = new TableColumn<>("Prescription ID");
            idCol.setCellValueFactory(new PropertyValueFactory<>("prescriptionID"));

            TableColumn<Object, Integer> pNameCol = new TableColumn<>("Patient Name");
            pNameCol.setCellValueFactory(new PropertyValueFactory<>("patientName"));

            TableColumn<Object, Integer> qtyCol = new TableColumn<>("Quantity");
            qtyCol.setCellValueFactory(new PropertyValueFactory<>("dosageQuantity"));

            TableColumn<Object, String> statusCol = new TableColumn<>("Status");
            statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

            table.getColumns().addAll(idCol, pNameCol, qtyCol, statusCol);

        } else if (firstItem instanceof Medication) {

            TableColumn<Object, String> nameCol = new TableColumn<>("Medication");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("medName"));

            TableColumn<Object, Integer> qtyCol = new TableColumn<>("Stock");
            qtyCol.setCellValueFactory(new PropertyValueFactory<>("pharmacyQuantity"));

            table.getColumns().addAll(nameCol, qtyCol);

        }

        VBox layout = new VBox(10, table);
        layout.setPadding(new Insets(10));
        resultsStage.setScene(new Scene(layout, 650, 500));
        resultsStage.show();
    }

    // ----- showExandedDetails ----- //
    public void showExpandedDetails(Object item, SceneController sceneController, User user) {
        Stage detailStage = new Stage();
        detailStage.initModality(Modality.APPLICATION_MODAL);

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f4f7f6;");
        layout.setAlignment(Pos.TOP_LEFT);

        if (item instanceof Patient p) {
            sceneController.userService.logPatientAccess(user.getEmployeeID(), p.getPatientID());

            detailStage.setTitle("Patient Profile: " + p.getFirstName() + " " + p.getLastName());
        
            Button historyBtn = new Button("View Prescription History");
            historyBtn.setPrefWidth(200);
            historyBtn.setOnAction(e -> {
                List<Prescription> history = sceneController.userService.getPatientHistory(p.getPatientID());
                showStatusPopup("History: " + p.getLastName(), history, sceneController, user);
            });

            Button editBtn = new Button("Edit Patient Info");
            editBtn.setPrefWidth(200);
            editBtn.setOnAction(e -> {
                Stage editStage = new Stage();
                editStage.initModality(Modality.APPLICATION_MODAL);
                editStage.setTitle("Edit Patient Details");

                VBox editLayout = new VBox(10);
                editLayout.setPadding(new Insets(20));

                TextField fName = new TextField(p.getFirstName());
                TextField lName = new TextField(p.getLastName());
                TextField phone = new TextField(p.getPhoneNumber());
                DatePicker dobPicker = new DatePicker(p.getBirthdate().toLocalDate());

                Button saveBtn = new Button("Save Changes");
                saveBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");

                saveBtn.setOnAction(saveEvent -> {
                    boolean success = sceneController.userService.updatePatient(
                        p.getPatientID(),
                        fName.getText(),
                        lName.getText(),
                        java.sql.Date.valueOf(dobPicker.getValue()),
                        phone.getText()
                    );
                    if (success) {
                        p.setFirstName(fName.getText());
                        p.setLastName(lName.getText());
                        p.setPhoneNumber(phone.getText());
                        p.setBirthdate(java.sql.Date.valueOf(dobPicker.getValue()));

                        editStage.close();
                        detailStage.close();
                        System.out.println("Patient updated successfully.");
                    }
                });

                editLayout.getChildren().addAll(
                    new Label("First Name: "), fName,
                    new Label("Last Name: "), lName,
                    new Label("Birth Date: "), dobPicker,
                    new Label("Phone Number: "), phone,
                    saveBtn
                );
                editStage.setScene(new Scene(editLayout));
                editStage.show();
            });
            layout.getChildren().addAll(
                new Label("Patient ID: " + p.getPatientID()),
                new Label("Full Name: " + p.getFirstName() + " " + p.getLastName()),
                new Label("Date of Birth: " + p.getBirthdate()),
                new Label("Phone Number: " + p.getPhoneNumber()),
                new Separator(),
                historyBtn,
                editBtn
            );
        } else if (item instanceof Prescription rx) {
            if ("Pharmacist".equalsIgnoreCase(user.getRole()) && "Waiting For Verification".equalsIgnoreCase(rx.getStatus())) {
                PatientRxDetails details = sceneController.userService.getRxDetails(rx.getPrescriptionID());

                if (details != null) {
                    String warningData = sceneController.userService.getInteractionWarning(details.getPatientID(), details.getMedID());

                    if (warningData != null) {
                        Label warningLabel = new Label("⚠️ INTERACTION ALERT ⚠️\n" + warningData.toUpperCase());
                        warningLabel.setWrapText(true);
                        warningLabel.setPrefWidth(250);

                        if (warningData.startsWith("Significant")) {
                            warningLabel.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10; -fx-border-radius: 5;");
                        } else if (warningData.startsWith("Moderate")) {
                            warningLabel.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10; -fx-border-radius: 5;");
                        } else {
                            warningLabel.setStyle("-fx-background-color: #f1c40f; -fx-text-fill: black; -fx-padding: 10; -fx-border-radius: 5;");
                        }

                        layout.getChildren().add(warningLabel);
                        layout.getChildren().add(new Separator());
                    }
                }
            }

            final Label statusLabel = new Label("Status: " + rx.getStatus());
            statusLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            if ("Waiting For Verification".equalsIgnoreCase(rx.getStatus())) {
                if ("Pharmacist".equalsIgnoreCase(user.getRole())) {
                    Button verifyBtn = new Button("Verify & Finalize Fill");
                    verifyBtn.setPrefWidth(250);
                    verifyBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold;");

                    verifyBtn.setOnAction(e -> {
                        boolean success = sceneController.userService.updatePrescriptionStatus(rx.getPrescriptionID(), "Ready For Pickup");
                        if (success) {
                            statusLabel.setText("Status: Ready For Pickup");
                            verifyBtn.setDisable(true);
                            verifyBtn.setText("Verified");
                            rx.setStatus("Ready For Pickup");

                            System.out.println("Pharmacist " + user.getLastName() + " verified RX #" + rx.getPrescriptionID());
                        }
                    });
                    layout.getChildren().add(verifyBtn);
                } else {
                    Label waitingLabel = new Label("⏳ Pending Pharmacist Verification");
                    waitingLabel.setStyle("-fx-text-fill: #e67e22; -fx-font-weight: bold;");
                    layout.getChildren().add(waitingLabel);
                }
            }
            sceneController.userService.logRxAccess(user.getEmployeeID(), rx.getPrescriptionID());

            PatientRxDetails details = sceneController.userService.getRxDetails(rx.getPrescriptionID());
            detailStage.setTitle("Prescription Profile: " + rx.getPrescriptionID());

            statusLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

            layout.getChildren().addAll(
                new Label("Prescription ID --- " + rx.getPrescriptionID()) {{ setStyle("-fx-font-weight: bold;"); }},
                new Label("Patient Name --- " + rx.getPatientName()),
                new Label("Patient DOB --- " + (rx.getBirthdate() != null ? rx.getBirthdate().toString() : "N/A")),
                new Separator(),
                new Label("Medication --- " + rx.getMedName()) {{ setStyle("-fx-font-weight: bold;"); }},
                new Label("Quantity --- " + rx.getDosageQuantity() + " " + rx.getUnit()),
                new Label("Strength --- " + rx.getStrength()),
                new Label("Form --- " + rx.getForm()),
                new Label("Frequency --- " + rx.getFrequency()),
                new Separator(),
                new Label("Date Created --- " + rx.getCreationDate()),
                new Label("Date Filled --- " + rx.getCompletionDate()),
                new Separator(),
                statusLabel
            );

            // --- OPTION 1: Prescription is PENDING ---
            if (Prescription.STATUS_PENDING.equalsIgnoreCase(rx.getStatus()) && details != null) {
                int amountNeeded = sceneController.userService.getPrescriptionQuantity(details.getDosageID());
                int medID = details.getMedID();
                int rxID = rx.getPrescriptionID();
                boolean isStockAvailable = sceneController.userService.hasEnoughStock(medID, amountNeeded);

                Button fillButton = new Button("Process & Fill Prescription");
                fillButton.setPrefWidth(250);

                if (amountNeeded <= 0) {
                    fillButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white;");
                    fillButton.setDisable(true);
                    fillButton.setText("Error: Invalid Dosage Amount");
                } else if (!isStockAvailable) {
                    fillButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
                    fillButton.setDisable(true);
                    Label warningLabel = new Label("⚠️ INSUFFICIENT STOCK (Needs " + amountNeeded + ")");
                    warningLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                    layout.getChildren().add(warningLabel);
                } else {
                    fillButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
                }

                fillButton.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Prescription Fill");
                    alert.setHeaderText("Filling RX #" + rxID);
                    alert.setContentText("Deduct " + amountNeeded + " " + rx.getUnit() + " from inventory and send to Pharmacist for review?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        boolean success = sceneController.userService.fillPrescription(rxID, medID, amountNeeded);
                        if (success) {
                            statusLabel.setText("Status: Waiting For Verification");
                            fillButton.setDisable(true);
                            fillButton.setText("Sent for Verification");
                            rx.setStatus("Waiting For Verification");
                            System.out.println("RX " + rxID + " moved to Verification Queue.");
                        } else {
                            System.err.println("DB Update Failed for RX ID: " + rxID);
                        }
                    }
                });
                layout.getChildren().add(fillButton);

            // --- OPTION 2: Prescription is READY FOR PICKUP ---
            } else if (Prescription.STATUS_READY_FOR_PICKUP.equalsIgnoreCase(rx.getStatus())) {
                Button payButton = new Button("Process Payment & Complete");
                payButton.setPrefWidth(250);
                payButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");

                payButton.setOnAction(e -> {
                    Alert payAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    payAlert.setTitle("Checkout");
                    payAlert.setHeaderText("Finalize Transaction for " + rx.getPatientName());
                    payAlert.setContentText("This will mark the Prescription as Complete. Proceed?");

                    Optional<ButtonType> payResult = payAlert.showAndWait();
                    if (payResult.isPresent() && payResult.get() == ButtonType.OK) {
                        boolean success = sceneController.userService.completePrescription(rx.getPrescriptionID());
                        if (success) {
                            statusLabel.setText("Status: Complete");
                            payButton.setDisable(true);
                            payButton.setText("Transaction Finished");
                            rx.setStatus("Complete");
                        } else {
                            System.err.println("Failed to complete transaction for RX: " + rx.getPrescriptionID());
                        }
                    }
                });
                layout.getChildren().add(payButton);
        
            // --- OPTION 3: Error fallback ---
            } else if (details == null && Prescription.STATUS_PENDING.equalsIgnoreCase(rx.getStatus())) {
                layout.getChildren().add(new Label("Notice: No medication details found."));
            }
        } else if (item instanceof Medication m) {
            sceneController.userService.logMedAccess(user.getEmployeeID(), m.getMedID());

            detailStage.setTitle("Medication Inventory: " + m.getMedName());
            Label stockLabel = new Label("Current Stock: " + m.getPharmacyQuantity());
            stockLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");
            if (m.getPharmacyQuantity() < 50) {
                stockLabel.setStyle(stockLabel.getStyle() + "-fx-text-fill: red;");
            }
            layout.getChildren().addAll(
                new Label("Medication ID: " + m.getMedID()),
                new Label("Name: " + m.getMedName()),
                new Label("Generic: " + m.getGenericName()),
                new Label("Pharmacy Quantity: " + m.getPharmacyQuantity()),
                new Separator(),
                stockLabel
            );

            if ("Pharmacist".equalsIgnoreCase(user.getRole())) {
                TextField amountField = new TextField();
                amountField.setPromptText("Enter shipment amount (e.g. 100)");
                amountField.setMaxWidth(200);

                Button restockBtn = new Button("Confirm Restock");
                restockBtn.setStyle("-fx-background-color: #8e44ad; -fx-text-fill: white; -fx-font-weight: bold;");
                restockBtn.setOnAction(e -> {
                    try {
                        int addAmount = Integer.parseInt(amountField.getText());
                        if (addAmount > 0) {
                            sceneController.userService.updateMedicationStock(m.getMedID(), addAmount);
                            int newTotal = m.getPharmacyQuantity() + addAmount;
                            m.setPharmacyQuantity(newTotal);
                            stockLabel.setText("Current Stock: " + newTotal);
                            amountField.clear();

                            String originalStyle = restockBtn.getStyle();
                            restockBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
                            restockBtn.setText("✔️ Stock Updated!");

                            PauseTransition pause = new PauseTransition(Duration.seconds(2));
                            pause.setOnFinished(event -> {
                                restockBtn.setStyle(originalStyle);
                                restockBtn.setText("Confirm Restock");
                            });
                            pause.play();
                            System.out.println("Pharmacist " + user.getLastName() + " added " + addAmount + " to " + m.getMedName());
                        }
                    } catch (NumberFormatException ex) {
                        amountField.setStyle("-fx-border-color: red;");
                        System.err.println("Invalid number entered for restock.");
                    }
                });
                layout.getChildren().addAll(new Label("Restock Medication:"), amountField, restockBtn);
            }
        }
        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        detailStage.setScene(new Scene(scrollPane, 500, 500));
        detailStage.show();
    }

    // ----- showStatusPopup Method ----- //
    public void showStatusPopup(String title, List<Prescription> dataList, SceneController sceneController, User user) {
        Stage popupStage = new Stage();
        popupStage.setTitle(title);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        // Fetch the filtered data
        TableView<Prescription> table = new TableView<>();
        ObservableList<Prescription> data = FXCollections.observableArrayList(dataList);
        table.setItems(data);
        // --- Column 1: ID --- //
        TableColumn<Prescription, Integer> idCol = new TableColumn<>("RX ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("prescriptionID"));
        // --- Column 2: Patient Name --- //
        TableColumn<Prescription, String> patientCol = new TableColumn<>("Patient");
        patientCol.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        // --- Column 3: Dosage Quantity --- //
        TableColumn<Prescription, Integer> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("dosageQuantity"));
        // --- Column 4: Unit(s) --- //
        TableColumn<Prescription, String> unitCol = new TableColumn<>("Unit");
        unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
        // --- Column 5: Medication --- //
        TableColumn<Prescription, String> medCol = new TableColumn<>("Medication");
        medCol.setCellValueFactory(new PropertyValueFactory<>("medName"));
        // --- Column 6: Status --- //
        TableColumn<Prescription, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        // --- Optional Column 7: Completion Date --- //
        if (!dataList.isEmpty() && "Complete".equalsIgnoreCase(dataList.get(0).getStatus())) {
            TableColumn<Prescription, java.sql.Date> dateCol = new TableColumn<>("Date Filled");
            dateCol.setCellValueFactory(new PropertyValueFactory<>("completionDate"));
            table.getColumns().add(dateCol);
        }
        table.getColumns().addAll(idCol, patientCol, qtyCol, unitCol, medCol, statusCol);
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                showExpandedDetails(newVal, sceneController, user);
            }
        });

        VBox layout = new VBox(10, new Label("Viewing: " + title), table);
        layout.setPadding(new Insets(15));
        popupStage.setScene(new Scene(layout, 650, 500));
        popupStage.show();
    }
}
