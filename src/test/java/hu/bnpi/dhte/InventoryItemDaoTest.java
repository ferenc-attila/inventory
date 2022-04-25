package hu.bnpi.dhte;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;

class InventoryItemDaoTest {

    private InventoryItemDao inventoryItemDao;

    @BeforeEach
    void setUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pu-test");
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