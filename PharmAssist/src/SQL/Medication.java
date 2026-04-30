package SQL;

public class Medication {
    
    // -- Class Attributes -- //
    private int medID;
    private String medName;
    private String genericName;
    private boolean isInStock;
    private int pharmacyQuantity;

    // -- Constructors -- //
    public Medication(int medID, String medName, String genericName, boolean isInStock, int pharmacyQuantity) {
        this.medID = medID;
        this.medName = medName;
        this.genericName = genericName;
        this.isInStock = isInStock;
        this.pharmacyQuantity = pharmacyQuantity;
    }

    public Medication() {};

    // -- Getters and Setters -- //
    public int getMedID() { return this.medID; }
    public void setMedID(int medID) { this.medID = medID; }

    public String getMedName() { return this.medName; }
    public void setMedName(String medName) { this.medName = medName; }

    public String getGenericName() { return this.genericName; }
    public void setGenericName(String genericName) { this.genericName = genericName; }

    public boolean getIsInStock() { return this.isInStock; }
    public void setIsInStock(boolean isInStock) { this.isInStock = isInStock; }

    public int getPharmacyQuantity() { return this.pharmacyQuantity; }
    public void setPharmacyQuantity(int pharmacyQuantity) { this.pharmacyQuantity = pharmacyQuantity; }

    // -- toString Override for Testing -- //
    @Override
    public String toString() {
        return "Medication{" +
                "medID=" + medID +
                ", medName='" + medName + '\'' +
                ", genericName='" + genericName + '\'' +
                ", isInStock='" + isInStock + '\'' +
                ", pharmacyQuantity='" + pharmacyQuantity + '\'' +
                '}';
    }

}
