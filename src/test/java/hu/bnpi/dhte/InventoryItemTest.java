package hu.bnpi.dhte;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

        assertAll(
                () -> assertThat(inventoryItem.getId()).isEqualTo(1L),
                () -> assertThat(inventoryItem.getName()).isEqualTo("DVD lemez"),
                () -> assertThat(inventoryItem.getItemType()).isEqualTo(ItemType.CONSUMABLE)
        );
    }
}