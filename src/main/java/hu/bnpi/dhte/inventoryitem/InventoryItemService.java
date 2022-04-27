package hu.bnpi.dhte.inventoryitem;

import java.util.Optional;

public class InventoryItemService {

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

    public Optional<InventoryItem> findInventoryItemByInventoryId(String inventoryId) {
        return inventoryItemDao.findInventoryItemByInventoryId(inventoryId);
    }

    public void updateInventoryItemDescription(String inventoryId, String description) {
        validateInputString(inventoryId);
        InventoryItem inventoryItem = findInventoryItemByInventoryId(inventoryId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with inventory id: " + inventoryId));
        validateInputString(description);
        inventoryItemDao.updateInventoryItemDescription(inventoryItem.getId(), description);
    }

    public void updateInventoryItemSerialNumber(String inventoryId, String serialNumber) {
        validateInputString(inventoryId);
        InventoryItem inventoryItem = findInventoryItemByInventoryId(inventoryId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with inventory id: " + inventoryId));
        validateInputString(serialNumber);
        inventoryItemDao.updateInventoryItemSerialNumber(inventoryItem.getId(), serialNumber);
    }

    private void validateInputString(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }
    }
}
