package hu.bnpi.dhte.inventoryitem;

import java.util.List;
import java.util.Optional;

public class InventoryItemService {

    private static final String ITEM_NOT_FOUND_EXCEPTION_MESSAGE = "Item not found with inventory id: ";

    private InventoryItemDao inventoryItemDao;

    public InventoryItemService(InventoryItemDao inventoryItemDao) {
        this.inventoryItemDao = inventoryItemDao;
    }

    public void saveInventoryItem(InventoryItem inventoryItem) {
        if (findInventoryItemByInventoryId(inventoryItem.getInventoryId()).isEmpty()) {
            inventoryItemDao.saveInventoryItem(inventoryItem);
        } else {
            throw new IllegalArgumentException("There is already an item in the database with this inventory id: " + inventoryItem.getInventoryId());
        }
    }

    public void removeInventoryItem(String inventoryId) {
        InventoryItem inventoryItem = findInventoryItemByInventoryId(inventoryId)
                .orElseThrow(() -> new IllegalArgumentException(ITEM_NOT_FOUND_EXCEPTION_MESSAGE + inventoryId));
        inventoryItemDao.removeInventoryItem(inventoryItem.getId());
    }

    public void updateInventoryItemDescription(String inventoryId, String description) {
        validateInputString(inventoryId);
        InventoryItem inventoryItem = findInventoryItemByInventoryId(inventoryId)
                .orElseThrow(() -> new IllegalArgumentException(ITEM_NOT_FOUND_EXCEPTION_MESSAGE + inventoryId));
        validateInputString(description);
        inventoryItemDao.updateInventoryItemDescription(inventoryItem.getId(), description);
    }

    public void updateInventoryItemSerialNumber(String inventoryId, String serialNumber) {
        validateInputString(inventoryId);
        InventoryItem inventoryItem = findInventoryItemByInventoryId(inventoryId)
                .orElseThrow(() -> new IllegalArgumentException(ITEM_NOT_FOUND_EXCEPTION_MESSAGE + inventoryId));
        validateInputString(serialNumber);
        inventoryItemDao.updateInventoryItemSerialNumber(inventoryItem.getId(), serialNumber);
    }

    public Optional<InventoryItem> findInventoryItemByInventoryId(String inventoryId) {
        return inventoryItemDao.findInventoryItemByInventoryId(inventoryId);
    }

    public List<InventoryItem> listAllInventoryItems() {
        return inventoryItemDao.listAllInventoryItems();
    }

    public List<InventoryItem> listInventoryItemsByName(String name) {
        validateInputString(name);
        return inventoryItemDao.listInventoryItemByName(name);
    }

    public List<InventoryItem> listInventoryItemsByDescriptionSubstring(String subString) {
        validateInputString(subString);
        return inventoryItemDao.listInventoryItemByDescriptionSubstring(subString);
    }

    public List<InventoryItem> listInventoryItemsByItemType(String itemType) {
        createItemTypeByString(itemType);
        return inventoryItemDao.listInventoryItemByItemType(createItemTypeByString(itemType));
    }

    private ItemType createItemTypeByString(String itemType) {
        validateInputString(itemType);
        try {
            return ItemType.valueOf(itemType.toUpperCase());
        } catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException("Invalid type of item: " + itemType.toUpperCase(), iae);
        }
    }

    private void validateInputString(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }
    }
}
