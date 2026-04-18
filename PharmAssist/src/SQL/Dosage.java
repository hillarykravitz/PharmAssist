package SQL;

public class Dosage {
    
    // -- Class Attributes -- //
    private int dosageID;
    private int medID;
    private String strength;
    private String form;
    private String appearance;
    private String frequency;
    private String quantity;

    // -- Constructors -- //
    public Dosage(int dosageID, int medID, String strength, String form, String appearance, String frequency, String quantity) {
        this.dosageID = dosageID;
        this.medID = medID;
        this.strength = strength;
        this.form = form;
        this.appearance = appearance;
        this.frequency = frequency;
        this.quantity = quantity;
    }

    public Dosage() {

    }

    // -- Getters and Setters -- //
    public int getDosageID() {
        return this.dosageID;
    }

    public void setDosageID(int dosageID) {
        this.dosageID = dosageID;
    }

    public int getMedID() {
        return this.medID;
    }

    public void setMedID(int medID) {
        this.medID = medID;
    }

    public String getStrength() {
        return this.strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getForm() {
        return this.form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getAppearance() {
        return this.appearance;
    }

    public void setApperance(String appearance) {
        this.appearance = appearance;
    }

    public String getFrequency() {
        return this.frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    // -- toString Override for Testing -- //
    @Override
    public String toString() {
        return "Dosage{" +
                "dosageID=" + dosageID +
                ", medID='" + medID + '\'' +
                ", strength='" + strength + '\'' +
                ", form='" + form + '\'' +
                ", appearance='" + appearance + '\'' +
                ", frequency='" + frequency + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }


}
