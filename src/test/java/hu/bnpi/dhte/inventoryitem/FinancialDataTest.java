package hu.bnpi.dhte.inventoryitem;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class FinancialDataTest {

    @Test
    void createTest() {
        FinancialData financialData = new FinancialData(1547.0);
        assertThat(financialData.getPriceOfPurchase()).isEqualTo(1547.0);
    }

    @Test
    void setDateOfPurchaseTest() {
        FinancialData financialData = new FinancialData();
        financialData.setDateOfPurchase(LocalDate.of(2022, 4, 1));
        assertThat(financialData.getDateOfPurchase()).isEqualTo(LocalDate.parse("2022-04-01"));
    }

    @Test
    void setPriceOfPurchaseTest() {
        FinancialData financialData = new FinancialData();
        financialData.setPriceOfPurchase(1547.0);
        assertThat(financialData.getPriceOfPurchase()).isEqualTo(1547.0);
    }

    @Test
    void setResourceTest() {
        FinancialData financialData = new FinancialData();
        financialData.setResource("KEOP-2022-01-4567");
        assertThat(financialData.getResource()).isEqualTo("KEOP-2022-01-4567");
    }

    @Test
    void setAmortizedCostTest() {
        FinancialData financialData = new FinancialData();
        financialData.setAmortizedCost(0);
        assertThat(financialData.getAmortizedCost()).isZero();
    }
}