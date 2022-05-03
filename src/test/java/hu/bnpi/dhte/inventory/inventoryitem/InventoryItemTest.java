package hu.bnpi.dhte.inventory.inventoryitem;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class InventoryItemTest {

    @Test
    void createItemTest() {
        InventoryItem inventoryItem = new InventoryItem("12345", ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", 1);
        assertAll(
                () -> assertThat(inventoryItem.getInventoryId()).isEqualTo("12345"),
                () -> assertThat(inventoryItem.getName()).isEqualTo("Fényképezőgép"),
                () -> assertThat(inventoryItem.getItemType()).isEqualTo(ItemType.HIGH_VALUE_ASSET),
                () -> assertThat(inventoryItem.getAmount()).isEqualTo(1)
        );
    }

    @Test
    void createItemWithEveryAttributesExpectIdTest() {
        InventoryItem inventoryItem =
                new InventoryItem("12345", ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", "tükörreflexes", "ABC-123-456", 1);

        assertAll(
                () -> assertThat(inventoryItem.getDescription()).isEqualTo("tükörreflexes"),
                () -> assertThat(inventoryItem.getSerialNumber()).isEqualTo("ABC-123-456")
        );
    }
}