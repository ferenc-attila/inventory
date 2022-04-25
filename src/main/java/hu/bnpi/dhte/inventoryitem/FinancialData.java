package hu.bnpi.dhte.inventoryitem;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class FinancialData {

    private LocalDate dateOfPurchase;

    private double priceOfPurchase;

    private String resource;

    private double amortizedCost;

    public FinancialData() {
    }

    public FinancialData(double purchasePrice) {
        this.priceOfPurchase = purchasePrice;
    }

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(LocalDate dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public double getPriceOfPurchase() {
        return priceOfPurchase;
    }

    public void setPriceOfPurchase(double priceOfPurchase) {
        this.priceOfPurchase = priceOfPurchase;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public double getAmortizedCost() {
        return amortizedCost;
    }

    public void setAmortizedCost(double amortizedCost) {
        this.amortizedCost = amortizedCost;
    }
}
