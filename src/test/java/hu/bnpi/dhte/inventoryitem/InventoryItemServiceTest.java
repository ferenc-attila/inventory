package hu.bnpi.dhte.inventoryitem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryItemServiceTest {

    @Mock
    InventoryItemDao inventoryItemDao;

    @InjectMocks
    InventoryItemService inventoryItemService;

    @Test
    void saveInventoryItemTest() {
        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
                .thenReturn(Optional.empty());
        inventoryItemService.saveInventoryItem(new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100));
        verify(inventoryItemDao).findInventoryItemByInventoryId("678901");
        verify(inventoryItemDao).saveInventoryItem(argThat(item -> item.getName().equals("Toll")));
    }

    @Test
    void saveInventoryItemAlreadyInTheDatabaseTest() {
        InventoryItem result = new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100);
        result.setId(1L);
        when(inventoryItemService.findInventoryItemByInventoryId("678901"))
                .thenReturn(Optional.of(result));
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> inventoryItemService.saveInventoryItem(new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100)));
        assertThat(iae.getMessage()).isEqualTo("There is already an item in the database with this inventory id: 678901");
        verify(inventoryItemDao).findInventoryItemByInventoryId("678901");
        verify(inventoryItemDao, never()).saveInventoryItem(any(InventoryItem.class));
    }

    @Test
    void findInventoryItemByInventoryIdTest() {
        InventoryItem result = new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100);
        result.setId(1L);
        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
                .thenReturn(Optional.of(result));
        assertThat(inventoryItemService.findInventoryItemByInventoryId("678901").get().getAmount()).isEqualTo(100);
        verify(inventoryItemDao).findInventoryItemByInventoryId("678901");
    }

    @Test
    void findInventoryItemByInventoryIdNotInDatabaseTest() {
        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
                .thenReturn(Optional.empty());
        assertThat(inventoryItemService.findInventoryItemByInventoryId("678901")).isEmpty();
        verify(inventoryItemDao).findInventoryItemByInventoryId("678901");
    }

    @Test
    void updateInventoryItemDescriptionTest() {
        InventoryItem result = new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100);
        result.setId(1L);
        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
                .thenReturn(Optional.of(result));
        inventoryItemService.updateInventoryItemDescription("678901", "Some description");
        verify(inventoryItemDao).updateInventoryItemDescription(1, "Some description");
    }

    @Test
    void updateInventoryItemDescriptionWhenItemNotInDatabaseTest() {
        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
                .thenReturn(Optional.empty());
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> inventoryItemService.updateInventoryItemDescription("678901", "Some description"));
        assertThat(iae.getMessage()).isEqualTo("Item not found with inventory id: 678901");
        verify(inventoryItemDao, never()).updateInventoryItemDescription(anyLong(), anyString());
    }

    @Test
    void updateInventoryItemDescriptionWithNullId() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> inventoryItemService.updateInventoryItemDescription(null, "Some description"));
        assertThat(iae.getMessage()).isEqualTo("Input string cannot be null or empty");
    }

    @Test
    void updateInventoryItemDescriptionWithNullDescription() {
        InventoryItem result = new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100);
        result.setId(1L);
        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
                .thenReturn(Optional.of(result));
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> inventoryItemService.updateInventoryItemDescription("678901", null));
        assertThat(iae.getMessage()).isEqualTo("Input string cannot be null or empty");
    }

    @Test
    void updateInventoryItemDescriptionWithEmptyDescription() {
        InventoryItem result = new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100);
        result.setId(1L);
        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
                .thenReturn(Optional.of(result));
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> inventoryItemService.updateInventoryItemDescription("678901", "    "));
        assertThat(iae.getMessage()).isEqualTo("Input string cannot be null or empty");
    }

    @Test
    void updateInventoryItemSerialNumberTest() {
        InventoryItem result = new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100);
        result.setId(1L);
        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
                .thenReturn(Optional.of(result));
        inventoryItemService.updateInventoryItemSerialNumber("678901", "Some serial number");
        verify(inventoryItemDao).updateInventoryItemSerialNumber(1, "Some serial number");
    }

    @Test
    void updateInventoryItemSerialNumberWhenItemNotInDatabaseTest() {
        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
                .thenReturn(Optional.empty());
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> inventoryItemService.updateInventoryItemSerialNumber("678901", "Some serial number"));
        assertThat(iae.getMessage()).isEqualTo("Item not found with inventory id: 678901");
        verify(inventoryItemDao, never()).updateInventoryItemSerialNumber(anyLong(), anyString());
    }

    @Test
    void updateInventoryItemSerialNumberWithNullId() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> inventoryItemService.updateInventoryItemSerialNumber(null, "Some description"));
        assertThat(iae.getMessage()).isEqualTo("Input string cannot be null or empty");
    }

    @Test
    void updateInventoryItemSerialNumberWithNullDescription() {
        InventoryItem result = new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100);
        result.setId(1L);
        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
                .thenReturn(Optional.of(result));
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> inventoryItemService.updateInventoryItemSerialNumber("678901", null));
        assertThat(iae.getMessage()).isEqualTo("Input string cannot be null or empty");
    }

    @Test
    void updateInventoryItemSerialNumberWithEmptyDescription() {
        InventoryItem result = new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100);
        result.setId(1L);
        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
                .thenReturn(Optional.of(result));
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> inventoryItemService.updateInventoryItemSerialNumber("678901", "    "));
        assertThat(iae.getMessage()).isEqualTo("Input string cannot be null or empty");
    }
}