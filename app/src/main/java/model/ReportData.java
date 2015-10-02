package model;

/**
 * Created by ashok on 9/29/15.
 */
public class ReportData {
    private String type;
    private double amount;

    public ReportData() {
    }

    public ReportData(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
