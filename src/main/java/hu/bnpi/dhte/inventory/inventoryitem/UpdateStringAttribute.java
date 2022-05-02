package hu.bnpi.dhte.inventory.inventoryitem;

@FunctionalInterface
public interface UpdateStringAttribute {

    void updateStringAttribute(InventoryItem inventoryItem, String value);
}
