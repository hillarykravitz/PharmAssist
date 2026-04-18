package Controller;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import User.UserService;
import SQL.Patient;
import SQL.Prescription;
import SQL.Medication;

public class SearchHandler { //implements EventHandler<ActionEvent>{
    
    // -- Class Attributes -- //
    public ComboBox<String> searchTypeCombo;
    public TextField searchField;

    private UserService userService = new UserService();

    // -- Constructor -- //
    public SearchHandler(ComboBox<String> searchTypeCombo, TextField searchField) {
        this.searchTypeCombo = searchTypeCombo;
        this.searchField = searchField;
    }

    public void handleSearch(String searchType, String query) {

        if (searchType == null || query.isEmpty()) {
            System.out.println("SelectedItem is NULL or rawInput is EMPTY");
            return;
        }
        switch (searchType) {
            case ("Prescription ID") -> {
                Prescription script = userService.prescriptionIDSearch(query);
                if (script != null) {
                    System.out.println(script.toString());
                } else {
                    System.out.println("Search Error: Prescription not Found.");
                }
            }
            case ("Patient Name") -> {
                Patient pat = userService.patientNameSearch(query);
                if (pat != null) {
                    System.out.println(pat.toString());
                } else {
                    System.out.println("Search Error: Patient not found.");
                }
                
            }
            case ("Patient DOB") -> {
                Patient pat2 = userService.patientDOBSearch(query);
                if (pat2 != null) {
                    System.out.println(pat2.toString());
                } else {
                    System.out.println("Search Error: Patient not found.");
                }
            }
            case ("Patient Phone Number") -> {
                Patient pat3 = userService.patientPhoneSearch(query);
                if (pat3 != null) {
                    System.out.println(pat3.toString());
                } else {
                    System.out.println("Search Error: Patient not found.");
                }
            }
            case ("Medication Name") -> {
                Medication med = userService.medicationSearch(query);
                if (med != null) {
                    System.out.println(med.toString());
                } else {
                    System.out.println("Search Error: Medication not found.");
                }
            }
            case ("Generic Name") -> {
                Medication gen = userService.genericSearch(query);
                if (gen != null) {
                    System.out.println(gen.toString());
                } else {
                    System.out.println("Search Error: Medication not found.");
                }
            }
        }
    }
}
