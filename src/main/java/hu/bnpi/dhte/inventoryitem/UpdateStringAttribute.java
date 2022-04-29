package hu.bnpi.dhte.inventoryitem;

@FunctionalInterface
public interface UpdateStringAttribute {

    void updateStringAttribute(InventoryItem inventoryItem, String value);
}
