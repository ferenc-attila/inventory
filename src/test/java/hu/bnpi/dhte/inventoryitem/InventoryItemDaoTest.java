package hu.bnpi.dhte.inventoryitem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;

class InventoryItemDaoTest {

    private InventoryItemDao inventoryItemDao;

    @BeforeEach
    void setUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pu-ci");
        inventoryItemDao = new InventoryItemDao(entityManagerFactory);
    }

    @Test
    void saveAndFindInventoryItemTest() {
        InventoryItem inventoryItem = new InventoryItem("Fényképezőgép", ItemType.HIGH_VALUE_ASSET);
        inventoryItemDao.saveInventoryItem(inventoryItem);

        InventoryItem anotherItem = inventoryItemDao.findInventoryItemById(inventoryItem.getId());
        assertThat(anotherItem.getName()).isEqualTo("Fényképezőgép");
    }
}