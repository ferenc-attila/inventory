package hu.bnpi.dhte.inventoryitem;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InventoryItemService {

    private static final String ITEM_NOT_FOUND_MESSAGE = "Item not found with inventory id: ";
    private static final String NOT_NULL_OR_EMPTY_MESSAGE = "Input parameters cannot be null or empty!";

    private InventoryItemDao inventoryItemDao;

    public InventoryItemService(InventoryItemDao inventoryItemDao) {
        this.inventoryItemDao = inventoryItemDao;
    }

    public String saveInventoryItem(InventoryItem inventoryItem) {
        if (findInventoryItemByInventoryId(inventoryItem.getInventoryId()).isPresent()) {
            return "There is already an item in the database with this inventory id: " + inventoryItem.getInventoryId();
        }
        if (inventoryItemDao.saveInventoryItem(inventoryItem).isEmpty()) {
            return "Unable to save item to the database!";
        }
        return "Inventory item saved successfully with inventory id: " + inventoryItem.getInventoryId();
    }

    public String removeInventoryItem(String inventoryId) {
        Optional<InventoryItem> inventoryItem = findInventoryItemByInventoryId(inventoryId);
        if (inventoryItem.isEmpty()) {
            return ITEM_NOT_FOUND_MESSAGE + inventoryId;
        }
        inventoryItemDao.removeInventoryItem(inventoryItem.get().getId());
        return "Inventory item removed successfully with inventory id: " + inventoryId;
    }

    public String updateInventoryItemDescription(String inventoryId, String description) {
        if (inputStringsAreNotValid(inventoryId, description)) {
            return NOT_NULL_OR_EMPTY_MESSAGE;
        }
        Optional<InventoryItem> inventoryItem = inventoryItemDao.findInventoryItemByInventoryId(inventoryId);
        if (inventoryItem.isEmpty()) {
            return ITEM_NOT_FOUND_MESSAGE + inventoryId;
        }
        UpdateStringAttribute updateDescription = InventoryItem::setDescription;
        if (inventoryItemDao.updateInventoryItem(inventoryItem.get().getId(), description, updateDescription).isEmpty()) {
            return "Unable to update item in the database!";
        }
        return "Description of inventory item " + inventoryId + " updated successfully with '" + description + "'!";
    }

    public String updateInventoryItemSerialNumber(String inventoryId, String serialNumber) {
        if (inputStringsAreNotValid(inventoryId, serialNumber)) {
            return NOT_NULL_OR_EMPTY_MESSAGE;
        }
        Optional<InventoryItem> inventoryItem = findInventoryItemByInventoryId(inventoryId);
        if (inventoryItem.isEmpty()) {
            return ITEM_NOT_FOUND_MESSAGE + inventoryId;
        }
        UpdateStringAttribute updateSerialNumber = InventoryItem::setSerialNumber;
        if (inventoryItemDao.updateInventoryItem(inventoryItem.get().getId(), serialNumber, updateSerialNumber).isEmpty()) {
            return "Unable to update item in the database!";
        }
        return "Serial number of inventory item " + inventoryId + " updated successfully with '" + serialNumber + "'!";
    }

    public Optional<InventoryItem> findInventoryItemByInventoryId(String inventoryId) {
        return inventoryItemDao.findInventoryItemByInventoryId(inventoryId);
    }

    public List<InventoryItem> listAllInventoryItems() {
        return inventoryItemDao.listAllInventoryItems();
    }

    public List<InventoryItem> listInventoryItemsByName(String name) {
        if (inputStringsAreNotValid(name)) {
            throw new IllegalArgumentException(NOT_NULL_OR_EMPTY_MESSAGE);
        }
        return inventoryItemDao.listInventoryItemByName(name);
    }

    public List<InventoryItem> listInventoryItemsByDescriptionSubstring(String subString) {
        if (inputStringsAreNotValid(subString)) {
            throw new IllegalArgumentException(NOT_NULL_OR_EMPTY_MESSAGE);
        }
        return inventoryItemDao.listInventoryItemByDescriptionSubstring(subString);
    }

    public List<InventoryItem> listInventoryItemsByItemType(String itemType) {
        ItemType type = createItemTypeByString(itemType);
        return inventoryItemDao.listInventoryItemByItemType(type);
    }

    private ItemType createItemTypeByString(String itemType) {
        if (inputStringsAreNotValid(itemType)) {
            throw new IllegalArgumentException(NOT_NULL_OR_EMPTY_MESSAGE);
        }
        try {
            return ItemType.valueOf(itemType.toUpperCase());
        } catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException("Invalid type of item: " + itemType.toUpperCase(), iae);
        }
    }

    private boolean inputStringsAreNotValid(String... input) {
        return Arrays.stream(input).anyMatch(Objects::isNull)
                || Arrays.stream(input).anyMatch(String::isBlank);
    }
}
