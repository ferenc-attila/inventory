package hu.bnpi.dhte.inventoryitem;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryItemServiceTest {

    @Mock
    InventoryItemDao inventoryItemDao;

    @InjectMocks
    InventoryItemService inventoryItemService;

    public static Stream<Arguments> createInvalidStringsAndExceptionMessagesForItemTypeSearch() {
        return Stream.of(
                arguments("HIGH_VALUE-ASSET", "Invalid type of item: HIGH_VALUE-ASSET"),
                arguments("consumables", "Invalid type of item: CONSUMABLES")
        );
    }

    @Test
    void saveInventoryItemTest() {
        InventoryItem inventoryItem = new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100);
        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
                .thenReturn(Optional.empty());
        when(inventoryItemDao.saveInventoryItem(inventoryItem)).thenReturn(Optional.of(inventoryItem));
        assertThat(inventoryItemService.saveInventoryItem(inventoryItem))
                .isEqualTo("Inventory item saved successfully with inventory id: 678901");
        verify(inventoryItemDao).findInventoryItemByInventoryId("678901");
        verify(inventoryItemDao).saveInventoryItem(argThat(item -> item.getName().equals("Toll")));
    }

    @Test
    void saveInventoryItemAlreadyInTheDatabaseTest() {
        InventoryItem result = new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100);
        result.setId(1L);
        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
                .thenReturn(Optional.of(result));
        assertThat(inventoryItemService.saveInventoryItem(result)).isEqualTo("There is already an item in the database with this inventory id: 678901");
        verify(inventoryItemDao).findInventoryItemByInventoryId("678901");
        verify(inventoryItemDao, never()).saveInventoryItem(any(InventoryItem.class));
    }

    @Test
    void removeInventoryItemTest() {
        InventoryItem result = new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100);
        result.setId(1L);
        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
                .thenReturn(Optional.of(result));
        assertThat(inventoryItemService.removeInventoryItem("678901"))
                .isEqualTo("Inventory item removed successfully with inventory id: 678901");
        verify(inventoryItemDao).removeInventoryItem(1L);
    }

    @Test
    void removeInventoryItemNotInTheDatabaseTest() {
        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
                .thenReturn(Optional.empty());
        assertThat(inventoryItemService.removeInventoryItem("678901")).isEqualTo("Item not found with inventory id: 678901");
        verify(inventoryItemDao, never()).removeInventoryItem(anyLong());
    }

//    @Test
//    void updateInventoryItemDescriptionTest() {
//        InventoryItem result = new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100);
//        result.setId(1L);
//        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
//                .thenReturn(Optional.of(result));
//        inventoryItemService.updateInventoryItemDescription("678901", "Some description");
//        verify(inventoryItemDao).findInventoryItemByInventoryId("678901");
//        verify(inventoryItemDao).updateInventoryItemDescription(1, "Some description");
//    }
//
//    @Test
//    void updateInventoryItemDescriptionWhenItemNotInDatabaseTest() {
//        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
//                .thenReturn(Optional.empty());
//        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
//                () -> inventoryItemService.updateInventoryItemDescription("678901", "Some description"));
//        assertThat(iae.getMessage()).isEqualTo("Item not found with inventory id: 678901");
//        verify(inventoryItemDao, never()).updateInventoryItemDescription(anyLong(), anyString());
//    }
//
//    @Test
//    void updateInventoryItemDescriptionWithNullId() {
//        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
//                () -> inventoryItemService.updateInventoryItemDescription(null, "Some description"));
//        assertThat(iae.getMessage()).isEqualTo("Input string cannot be null or empty");
//        verify(inventoryItemDao, never()).updateInventoryItemDescription(anyLong(), anyString());
//    }
//
//    @Test
//    void updateInventoryItemDescriptionWithNullDescription() {
//        InventoryItem result = new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100);
//        result.setId(1L);
//        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
//                .thenReturn(Optional.of(result));
//        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
//                () -> inventoryItemService.updateInventoryItemDescription("678901", null));
//        assertThat(iae.getMessage()).isEqualTo("Input string cannot be null or empty");
//        verify(inventoryItemDao, never()).updateInventoryItemDescription(anyLong(), anyString());
//    }
//
//    @Test
//    void updateInventoryItemDescriptionWithEmptyDescription() {
//        InventoryItem result = new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100);
//        result.setId(1L);
//        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
//                .thenReturn(Optional.of(result));
//        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
//                () -> inventoryItemService.updateInventoryItemDescription("678901", "    "));
//        assertThat(iae.getMessage()).isEqualTo("Input string cannot be null or empty");
//        verify(inventoryItemDao, never()).updateInventoryItemDescription(anyLong(), anyString());
//    }

    @Test()
    void updateInventoryItemSerialNumberTest() {
        InventoryItem item = new InventoryItem("678901", ItemType.HIGH_VALUE_ASSET, "Laptop", 1);
        item.setId(1L);
        UpdateStringAttribute update = InventoryItem::setSerialNumber;
        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
                .thenReturn(Optional.of(item));
        when(inventoryItemDao.updateInventoryItem(eq(1L), eq("Some serial number"), any(UpdateStringAttribute.class)))
                .thenReturn(Optional.of(item));
        assertThat(inventoryItemService.updateInventoryItemSerialNumber("678901", "Some serial number"))
                .isEqualTo("Serial number of inventory item 678901 updated successfully with 'Some serial number'!");
        verify(inventoryItemDao).updateInventoryItem(eq(1L), eq("Some serial number"), any(UpdateStringAttribute.class));
    }

//    @Test
//    void updateInventoryItemSerialNumberWhenItemNotInDatabaseTest() {
//        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
//                .thenReturn(Optional.empty());
//        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
//                () -> inventoryItemService.updateInventoryItemSerialNumber("678901", "Some serial number"));
//        assertThat(iae.getMessage()).isEqualTo("Item not found with inventory id: 678901");
//        verify(inventoryItemDao, never()).updateInventoryItemSerialNumber(anyLong(), anyString());
//    }
//
//    @Test
//    void updateInventoryItemSerialNumberWithNullId() {
//        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
//                () -> inventoryItemService.updateInventoryItemSerialNumber(null, "Some description"));
//        assertThat(iae.getMessage()).isEqualTo("Input string cannot be null or empty");
//        verify(inventoryItemDao, never()).updateInventoryItemSerialNumber(anyLong(), anyString());
//    }
//
//    @Test
//    void updateInventoryItemSerialNumberWithNullDescription() {
//        InventoryItem result = new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100);
//        result.setId(1L);
//        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
//                .thenReturn(Optional.of(result));
//        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
//                () -> inventoryItemService.updateInventoryItemSerialNumber("678901", null));
//        assertThat(iae.getMessage()).isEqualTo("Input string cannot be null or empty");
//        verify(inventoryItemDao, never()).updateInventoryItemSerialNumber(anyLong(), anyString());
//    }
//
//    @Test
//    void updateInventoryItemSerialNumberWithEmptyDescription() {
//        InventoryItem result = new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100);
//        result.setId(1L);
//        when(inventoryItemDao.findInventoryItemByInventoryId("678901"))
//                .thenReturn(Optional.of(result));
//        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
//                () -> inventoryItemService.updateInventoryItemSerialNumber("678901", "    "));
//        assertThat(iae.getMessage()).isEqualTo("Input string cannot be null or empty");
//        verify(inventoryItemDao, never()).updateInventoryItemSerialNumber(anyLong(), anyString());
//    }

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
    void listAllInventoryItems() {
        InventoryItem firstItem = new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100);
        InventoryItem secondItem = new InventoryItem("345678", ItemType.HIGH_VALUE_ASSET, "Laptop", 1);
        InventoryItem thirdItem = new InventoryItem("123456", ItemType.CONSUMABLE, "Irattartó", 50);
        InventoryItem fourthItem = new InventoryItem("543210", ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", 1);
        firstItem.setId(1L);
        secondItem.setId(2L);
        thirdItem.setId(3L);
        fourthItem.setId(4L);
        when(inventoryItemDao.listAllInventoryItems())
                .thenReturn(List.of(thirdItem, secondItem, fourthItem, firstItem));
        List<InventoryItem> result = inventoryItemService.listAllInventoryItems();
        assertThat(result)
                .hasSize(4)
                .extracting(InventoryItem::getName)
                .containsExactly("Irattartó", "Laptop", "Fényképezőgép", "Toll");
        verify(inventoryItemDao).listAllInventoryItems();
    }

    @Test
    void listInventoryItemsByName() {
        InventoryItem firstItem = new InventoryItem("678901", ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", 1);
        InventoryItem secondItem = new InventoryItem("123456", ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", 1);
        InventoryItem thirdItem = new InventoryItem("543210", ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", 1);
        when(inventoryItemDao.listInventoryItemByName("Fényképezőgép"))
                .thenReturn(List.of(secondItem, thirdItem, firstItem));
        assertThat(inventoryItemService.listInventoryItemsByName("Fényképezőgép"))
                .hasSize(3)
                .extracting(InventoryItem::getInventoryId)
                .containsExactly("123456", "543210", "678901");
        verify(inventoryItemDao).listInventoryItemByName("Fényképezőgép");
    }

    @Test
    void listInventoryItemsByNameNotInDatabase() {
        when(inventoryItemDao.listInventoryItemByName("Fényképezőgép"))
                .thenReturn(List.of());
        assertThat(inventoryItemService.listInventoryItemsByName("Fényképezőgép"))
                .isEmpty();
        verify(inventoryItemDao).listInventoryItemByName("Fényképezőgép");
    }

    @ParameterizedTest(name = "Test invalid parameter for description substring: {0}")
    @NullSource
    @EmptySource
    void listInventoryItemsWhenNameIsInvalid(String invalidName) {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> inventoryItemService.listInventoryItemsByName(invalidName));
        assertThat(iae.getMessage()).isEqualTo("Input parameters cannot be null or empty!");
        verify(inventoryItemDao, never()).listInventoryItemByName(anyString());
    }

    @Test
    void listInventoryItemsByDescriptionSubstring() {
        InventoryItem firstItem = new InventoryItem("678901", ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", 1);
        InventoryItem secondItem = new InventoryItem("123456", ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", 1);
        InventoryItem thirdItem = new InventoryItem("543210", ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", 1);
        when(inventoryItemDao.listInventoryItemByDescriptionSubstring("Fényképezőgép"))
                .thenReturn(List.of(secondItem, thirdItem, firstItem));
        assertThat(inventoryItemService.listInventoryItemsByDescriptionSubstring("Fényképezőgép"))
                .hasSize(3)
                .extracting(InventoryItem::getInventoryId)
                .containsExactly("123456", "543210", "678901");
        verify(inventoryItemDao).listInventoryItemByDescriptionSubstring("Fényképezőgép");
    }

    @Test
    void listInventoryItemsByDescriptionSubstringNotInDatabase() {
        when(inventoryItemDao.listInventoryItemByDescriptionSubstring("Fényképezőgép"))
                .thenReturn(List.of());
        assertThat(inventoryItemService.listInventoryItemsByDescriptionSubstring("Fényképezőgép"))
                .isEmpty();
        verify(inventoryItemDao).listInventoryItemByDescriptionSubstring("Fényképezőgép");
    }

    @ParameterizedTest(name = "Test invalid parameter for description substring: {0}")
    @NullSource
    @EmptySource
    void listInventoryItemsWhenDescriptionSubstringIsInvalid(String invalidSubstring) {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> inventoryItemService.listInventoryItemsByDescriptionSubstring(invalidSubstring));
        assertThat(iae.getMessage()).isEqualTo("Input parameters cannot be null or empty!");
        verify(inventoryItemDao, never()).listInventoryItemByDescriptionSubstring(anyString());
    }

    @Test
    void listInventoryItemsByItemTypeString() {
        InventoryItem firstItem = new InventoryItem("678901", ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", 2);
        InventoryItem thirdItem = new InventoryItem("543210", ItemType.HIGH_VALUE_ASSET, "Laptop", 1);
        when(inventoryItemDao.listInventoryItemByItemType(ItemType.HIGH_VALUE_ASSET))
                .thenReturn(List.of(thirdItem, firstItem));
        inventoryItemService.listInventoryItemsByItemType("HIGH_VALUE_ASSET");
        verify(inventoryItemDao).listInventoryItemByItemType(ItemType.HIGH_VALUE_ASSET);
    }

    @Test
    void listInventoryItemsByItemTypeNotInDatabase() {
        when(inventoryItemDao.listInventoryItemByItemType(ItemType.HIGH_VALUE_ASSET))
                .thenReturn(List.of());
        assertThat(inventoryItemService.listInventoryItemsByItemType("HIGH_VALUE_ASSET"))
                .isEmpty();
        verify(inventoryItemDao).listInventoryItemByItemType(ItemType.HIGH_VALUE_ASSET);
    }

    @ParameterizedTest(name = "Test invalid parameter for item type string: {0}")
    @MethodSource("createInvalidStringsAndExceptionMessagesForItemTypeSearch")
    void listInventoryItemsWhenItemTypeStringIsInvalid(String parameter, String exceptionMessage) {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> inventoryItemService.listInventoryItemsByItemType(parameter));
        assertThat(iae.getMessage()).isEqualTo(exceptionMessage);
        verify(inventoryItemDao, never()).listInventoryItemByItemType(any(ItemType.class));
    }
}