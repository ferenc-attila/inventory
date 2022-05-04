package hu.bnpi.dhte.inventory.repositories;

import hu.bnpi.dhte.inventory.entities.inventoryitem.InventoryItem;

@FunctionalInterface
public interface UpdateStringAttribute {

    void updateStringAttribute(InventoryItem inventoryItem, String value);
}
