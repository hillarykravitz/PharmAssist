package Controller;

import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import SQL.Medication;
import SQL.Patient;
import SQL.Prescription;
import User.UserService;

public class SearchHandler {
    
    // ----- Class Attributes ----- //
    public ComboBox<String> searchTypeCombo;
    public TextField searchField;
    // --- UserService Object Instantiation --- //
    private UserService userService = new UserService();
    // --- Constructor --- //
    public SearchHandler(ComboBox<String> searchTypeCombo, TextField searchField, UserService userService) {
        this.searchTypeCombo = searchTypeCombo;
        this.searchField = searchField;
        this.userService = userService;
    }
    // --- Handle Search Method --- //
    public List<?> handleSearch(String searchType, String query) {
        if (searchType == null || query.isEmpty()) {
            System.out.println("SelectedItem is NULL or rawInput is EMPTY");
            return null;
        }
        switch (searchType) {
            case ("Prescription ID") -> {
                List<Prescription> scripts = userService.prescriptionIDSearch(query);
                if (scripts != null) {
                    for (Prescription rx : scripts) {
                        System.out.println(rx);
                    }
                } else {
                    System.out.println("Search Error: Prescription not Found.");
                }
                return scripts;
            }
            case ("Patient Name") -> {
                List<Patient> pats = userService.patientNameSearch(query);
                
                if (pats != null) {
                    for (Patient p : pats) {
                        System.out.println(p);
                    }
                } else {
                    System.out.println("Search Error: Patient not found.");
                }
                return pats;
            }
            case ("Patient DOB") -> {
                List<Patient> pats2 = userService.patientDOBSearch(query);
                if (pats2 != null) {
                    for (Patient p : pats2) {
                        System.out.println(p);
                    }
                } else {
                    System.out.println("Search Error: Patient not found.");
                }
                return pats2;
            }
            case ("Patient Phone Number") -> {
                List<Patient> pats3 = userService.patientPhoneSearch(query);
                if (pats3 != null) {
                    for (Patient p : pats3) {
                        System.out.println(p);
                    }
                } else {
                    System.out.println("Search Error: Patient not found.");
                }
                return pats3;
            }
            case ("Medication Name") -> {
                List<Medication> meds = userService.medicationSearch(query);
                if (meds != null) {
                    for (Medication med : meds) {
                        System.out.println(med);
                    }
                } else {
                    System.out.println("Search Error: Medication not found.");
                }
                return meds;
            }
            case ("Generic Name") -> {
                List<Medication> gens = userService.genericSearch(query);
                if (gens != null) {
                    for (Medication gen : gens) {
                        System.out.println(gen);
                    }
                } else {
                    System.out.println("Search Error: Medication not found.");
                }
                return gens;
            }
        }
        return null;
    }
}