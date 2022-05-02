package hu.bnpi.dhte.inventoryitem;

import hu.bnpi.dhte.inventory.inventoryitem.InventoryItemController;
import hu.bnpi.dhte.inventory.inventoryitem.InventoryItemService;
import hu.bnpi.dhte.inventory.inventoryitem.ItemType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryItemControllerTest {

    @Mock
    InventoryItemService inventoryItemService;

    @InjectMocks
    InventoryItemController inventoryItemController;

    @Test
    void saveInventoryItemWithMinimalParametersTest() {
        when(inventoryItemService.saveInventoryItem(argThat(inventoryItem -> inventoryItem.getInventoryId().equals("678901"))))
                .thenReturn("Inventory item saved successfully with inventory id: 678901");
        assertThat(inventoryItemController.saveInventoryItem("678901", ItemType.HIGH_VALUE_ASSET, "Laptop", 1))
                .isEqualTo("Inventory item saved successfully with inventory id: 678901");
        verify(inventoryItemService).saveInventoryItem(argThat(inventoryItem -> inventoryItem.getInventoryId().equals("678901")));
    }

    @Test
    void testSaveInventoryItemWithMoreParametersTest() {
        when(inventoryItemService.saveInventoryItem(argThat(inventoryItem -> inventoryItem.getInventoryId().equals("678901"))))
                .thenReturn("Inventory item saved successfully with inventory id: 678901");
        assertThat(inventoryItemController.saveInventoryItem(
                "678901", ItemType.HIGH_VALUE_ASSET, "Laptop", "some description", "some serial number",1))
                .isEqualTo("Inventory item saved successfully with inventory id: 678901");
        verify(inventoryItemService).saveInventoryItem(argThat(inventoryItem -> inventoryItem.getInventoryId().equals("678901")));
    }

//    @Test
//    void removeInventoryItemTest() {
//    }
//
//    @Test
//    void updateInventoryItemDescriptionTest() {
//    }
//
//    @Test
//    void updateInventoryItemSerialNumberTest() {
//    }
}