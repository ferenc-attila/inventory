package hu.bnpi.dhte.inventoryitem;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class InventoryItemTest {

    @Test
    void createItemTest() {
        InventoryItem inventoryItem = new InventoryItem("Fényképezőgép", ItemType.HIGH_VALUE_ASSET);
        assertAll(
                () -> assertThat(inventoryItem.getName()).isEqualTo("Fényképezőgép"),
                () -> assertThat(inventoryItem.getItemType()).isEqualTo(ItemType.HIGH_VALUE_ASSET)
        );
    }

    @Test
    void setterTest() {
        InventoryItem inventoryItem = new InventoryItem("Fényképezőgép", ItemType.HIGH_VALUE_ASSET);
        inventoryItem.setId(1L);
        inventoryItem.setName("DVD lemez");
        inventoryItem.setItemType(ItemType.CONSUMABLE);
        inventoryItem.setDescription("adattárolásra");
        inventoryItem.setSerialNumber("ABC-123");
        inventoryItem.setFinancialData(new FinancialData(500.0));
        inventoryItem.setAmount(10);
        inventoryItem.setResponsibility(new Responsibility(1L, "Kovács József"));
        inventoryItem.setLocation(new Location("Iroda", "konyha"));

        assertAll(
                () -> assertThat(inventoryItem.getId()).isEqualTo(1L),
                () -> assertThat(inventoryItem.getName()).isEqualTo("DVD lemez"),
                () -> assertThat(inventoryItem.getItemType()).isEqualTo(ItemType.CONSUMABLE),
                () -> assertThat(inventoryItem.getDescription()).isEqualTo("adattárolásra"),
                () -> assertThat(inventoryItem.getSerialNumber()).isEqualTo("ABC-123"),
                () -> assertThat(inventoryItem.getFinancialData().getPriceOfPurchase()).isEqualTo(500.0),
                () -> assertThat(inventoryItem.getAmount()).isEqualTo(10),
                () -> assertThat(inventoryItem.getResponsibility().getEmployeeName()).isEqualTo("Kovács József"),
                () -> assertThat(inventoryItem.getLocation().getLocationDescription()).isEqualTo("konyha")
        );
    }
}