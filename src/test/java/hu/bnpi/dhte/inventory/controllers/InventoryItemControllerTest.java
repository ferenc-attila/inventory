package hu.bnpi.dhte.inventory.controllers;

import hu.bnpi.dhte.inventory.controllers.InventoryItemController;
import hu.bnpi.dhte.inventory.entities.inventoryitem.ItemType;
import hu.bnpi.dhte.inventory.services.InventoryItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryItemControllerTest {

    @Mock
    InventoryItemService inventoryItemService;

    @InjectMocks
    InventoryItemController inventoryItemController;

    public static Stream<Arguments> createInvalidUpdateParametersAndResults() {
        return Stream.of(
                arguments(null, null),
                arguments(null, "   \n"),
                arguments("   \n", null),
                arguments("678901", null),
                arguments(null, "some description"),
                arguments("   \n", "some description"),
                arguments("678901", "   \n")
        );
    }

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

    @Test
    void removeInventoryItemTest() {
        when(inventoryItemService.removeInventoryItem("678901"))
                .thenReturn("Inventory item removed successfully with inventory id: 678901");
        assertThat(inventoryItemController.removeInventoryItem("678901"))
                .isEqualTo("Inventory item removed successfully with inventory id: 678901");
        verify(inventoryItemService).removeInventoryItem("678901");
    }

    @Test
    void removeInventoryItemNotInDatabaseTest() {
        when(inventoryItemService.removeInventoryItem("678901"))
                .thenReturn("Item not found with inventory id: 678901");
        assertThat(inventoryItemController.removeInventoryItem("678901"))
                .isEqualTo("Item not found with inventory id: 678901");
        verify(inventoryItemService).removeInventoryItem("678901");
    }

    @Test
    void updateInventoryItemDescriptionTest() {
        when(inventoryItemService.updateInventoryItemDescription("678901", "some description"))
                .thenReturn("Description of inventory item 678901 updated successfully with 'some description'!");
        assertThat(inventoryItemController.updateInventoryItemDescription("678901", "some description"))
                .isEqualTo("Description of inventory item 678901 updated successfully with 'some description'!");
        verify(inventoryItemService).updateInventoryItemDescription("678901", "some description");
    }

    @Test
    void updateInventoryItemDescriptionNotInDatabaseTest() {
        when(inventoryItemService.updateInventoryItemDescription("678901", "some description"))
                .thenReturn("Item not found with inventory id: 678901");
        assertThat(inventoryItemController.updateInventoryItemDescription("678901", "some description"))
                .isEqualTo("Item not found with inventory id: 678901");
        verify(inventoryItemService).updateInventoryItemDescription("678901", "some description");
    }

    @ParameterizedTest
    @MethodSource("createInvalidUpdateParametersAndResults")
    void updateInventoryItemDescriptionWithEmptyOrNullParametersTest(String inventoryId, String description) {
        when(inventoryItemService.updateInventoryItemDescription(inventoryId, description))
                .thenReturn("Input parameters cannot be null or empty!");
        assertThat(inventoryItemController.updateInventoryItemDescription(inventoryId, description))
                .isEqualTo("Input parameters cannot be null or empty!");
    }

    @Test
    void updateInventoryItemSerialNumberTest() {
        when(inventoryItemService.updateInventoryItemSerialNumber("678901", "some serial number"))
                .thenReturn("Serial number of inventory item 678901 updated successfully with 'some serial number'!");
        assertThat(inventoryItemController.updateInventoryItemSerialNumber("678901", "some serial number"))
                .isEqualTo("Serial number of inventory item 678901 updated successfully with 'some serial number'!");
        verify(inventoryItemService).updateInventoryItemSerialNumber("678901", "some serial number");
    }

    @Test
    void updateInventoryItemSerialNumberNotInDatabaseTest() {
        when(inventoryItemService.updateInventoryItemSerialNumber("678901", "some serial number"))
                .thenReturn("Item not found with inventory id: 678901");
        assertThat(inventoryItemController.updateInventoryItemSerialNumber("678901", "some serial number"))
                .isEqualTo("Item not found with inventory id: 678901");
        verify(inventoryItemService).updateInventoryItemSerialNumber("678901", "some serial number");
    }

    @ParameterizedTest
    @MethodSource("createInvalidUpdateParametersAndResults")
    void updateInventoryItemSerialNumberWithEmptyOrNullParametersTest(String inventoryId, String serialNumber) {
        when(inventoryItemService.updateInventoryItemSerialNumber(inventoryId, serialNumber))
                .thenReturn("Input parameters cannot be null or empty!");
        assertThat(inventoryItemController.updateInventoryItemSerialNumber(inventoryId, serialNumber))
                .isEqualTo("Input parameters cannot be null or empty!");
    }
}