package hu.bnpi.dhte.inventory.controllers;

import hu.bnpi.dhte.inventory.entities.inventoryitem.InventoryItem;
import hu.bnpi.dhte.inventory.entities.inventoryitem.ItemType;
import hu.bnpi.dhte.inventory.services.InventoryItemService;

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
            return inventoryItemService.removeInventoryItem(inventoryId);
    }

    public String updateInventoryItemDescription(String inventoryId, String description) {
        return inventoryItemService.updateInventoryItemDescription(inventoryId, description);
    }

    public String updateInventoryItemSerialNumber(String inventoryId, String serialNumber) {
        return inventoryItemService.updateInventoryItemSerialNumber(inventoryId, serialNumber);
    }
}
