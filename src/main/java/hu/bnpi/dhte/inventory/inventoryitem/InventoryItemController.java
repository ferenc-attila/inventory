package hu.bnpi.dhte.inventory.inventoryitem;

public class InventoryItemController {

    private final InventoryItemService inventoryItemService;

    public InventoryItemController(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }

    public String saveInventoryItem(String inventoryId, ItemType itemType, String name, int amount) {
        InventoryItem inventoryItem = new InventoryItem(inventoryId, itemType, name, amount);
        return inventoryItemService.saveInventoryItem(inventoryItem);
    }

    public String saveInventoryItem(String inventoryId, ItemType itemType, String name, String description, String serialNumber, int amount) {
        InventoryItem inventoryItem = new InventoryItem(inventoryId, itemType, name, description, serialNumber, amount);
        return inventoryItemService.saveInventoryItem(inventoryItem);
    }

    public String removeInventoryItem(String inventoryId) {
        try {
            return inventoryItemService.removeInventoryItem(inventoryId);
        } catch (IllegalArgumentException iae) {
            return iae.getMessage();
        }
    }

    public String updateInventoryItemDescription(String inventoryId, String description) {
        return inventoryItemService.updateInventoryItemDescription(inventoryId, description);
    }

    public String updateInventoryItemSerialNumber(String inventoryId, String serialNumber) {
        return inventoryItemService.updateInventoryItemSerialNumber(inventoryId, serialNumber);
    }
}
