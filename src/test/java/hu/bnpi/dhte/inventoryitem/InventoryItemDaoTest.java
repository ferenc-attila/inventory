package hu.bnpi.dhte.inventoryitem;

import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InventoryItemDaoTest {

    private InventoryItemDao inventoryItemDao;

    @BeforeEach
    void setUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pu-test");
        inventoryItemDao = new InventoryItemDao(entityManagerFactory);
    }

    @Test
    void saveAndFindInventoryItemTest() {
        InventoryItem inventoryItem = new InventoryItem("12345", ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", 1);
        inventoryItemDao.saveInventoryItem(inventoryItem);

        InventoryItem anotherItem = inventoryItemDao.findInventoryItemById(inventoryItem.getId()).get();
        assertThat(anotherItem.getName()).isEqualTo("Fényképezőgép");
    }

    @Test
    void removeInventoryItemTest() {
        InventoryItem inventoryItem = new InventoryItem("12345", ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", 1);
        inventoryItemDao.saveInventoryItem(inventoryItem);

        inventoryItemDao.removeInventoryItem(inventoryItem.getId());
        assertThat(inventoryItemDao.listAllInventoryItems()).isEmpty();
    }

    @Test
    void removeInventoryItemNotInDatabaseTest() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> inventoryItemDao.removeInventoryItem(1L));
        assertThat(iae.getMessage()).isEqualTo("attempt to create delete event with null entity");
    }

    @Test
    void updateInventoryItemDescriptionTest() {
        InventoryItem inventoryItem = new InventoryItem("12345", ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", 1);
        inventoryItemDao.saveInventoryItem(inventoryItem);

        inventoryItemDao.updateInventoryItemDescription(inventoryItem.getId(), "tükörreflexes");
        InventoryItem anotherItem = inventoryItemDao.findInventoryItemById(inventoryItem.getId()).get();
        assertThat(anotherItem.getDescription()).isEqualTo("tükörreflexes");
    }

    @Test
    void updateInventoryItemSerialNumberTest() {
        InventoryItem inventoryItem = new InventoryItem("12345", ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", 1);
        inventoryItemDao.saveInventoryItem(inventoryItem);

        inventoryItemDao.updateInventoryItemSerialNumber(inventoryItem.getId(), "ABC-123-456");
        InventoryItem anotherItem = inventoryItemDao.findInventoryItemById(inventoryItem.getId()).get();
        assertThat(anotherItem.getSerialNumber()).isEqualTo("ABC-123-456");
    }

    @Test
    void findInventoryItemByIdNotInDatabase() {
        NoSuchElementException nse = assertThrows(NoSuchElementException.class,
                () -> inventoryItemDao.findInventoryItemById(1).get());
        assertThat(nse.getMessage()).isEqualTo("No value present");
    }

    @Test
    void findInventoryItemByInventoryId() {
        inventoryItemDao.saveInventoryItem(new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100));
        inventoryItemDao.saveInventoryItem(new InventoryItem("345678", ItemType.HIGH_VALUE_ASSET, "Laptop", 1));

        InventoryItem item = inventoryItemDao.findInventoryItemByInventoryId("345678").get();
        assertThat(item.getName()).isEqualTo("Laptop");
    }

    @Test
    void findInventoryItemByInventoryIdNotInDatabase() {
        NoResultException nre = assertThrows(NoResultException.class,
                () -> inventoryItemDao.findInventoryItemByInventoryId("No such id").get());
        assertThat(nre.getMessage()).isEqualTo("No result found for query");
    }

    @Test
    void listAllInventoryItems() {
            InventoryItem firstItem = new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100);
            InventoryItem secondItem = new InventoryItem("345678", ItemType.HIGH_VALUE_ASSET, "Laptop", 1);
            InventoryItem thirdItem = new InventoryItem("123456", ItemType.CONSUMABLE, "Irattartó", 50);
            InventoryItem fourthItem = new InventoryItem("543210", ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", 1);
            inventoryItemDao.saveInventoryItem(firstItem);
            inventoryItemDao.saveInventoryItem(secondItem);
            inventoryItemDao.saveInventoryItem(thirdItem);
            inventoryItemDao.saveInventoryItem(fourthItem);

            List<InventoryItem> items = inventoryItemDao.listAllInventoryItems();
            assertThat(items).hasSize(4)
                    .extracting(InventoryItem::getName)
                    .containsExactly("Irattartó", "Laptop", "Fényképezőgép", "Toll");
    }

    @Test
    void listInventoryItemsWhenTableIsEmpty() {
        assertThat(inventoryItemDao.listAllInventoryItems()).isEmpty();
    }

    @Test
    void listInventoryItemsByName() {
        InventoryItem firstItem = new InventoryItem("678901", ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", 1);
        InventoryItem secondItem = new InventoryItem("345678", ItemType.HIGH_VALUE_ASSET, "Laptop", 1);
        InventoryItem thirdItem = new InventoryItem("123456", ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", 1);
        InventoryItem fourthItem = new InventoryItem("543210", ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", 1);
        inventoryItemDao.saveInventoryItem(firstItem);
        inventoryItemDao.saveInventoryItem(secondItem);
        inventoryItemDao.saveInventoryItem(thirdItem);
        inventoryItemDao.saveInventoryItem(fourthItem);

        List<InventoryItem> items = inventoryItemDao.listInventoryItemByName("Fényképezőgép");
        assertThat(items).hasSize(3)
                .extracting(InventoryItem::getInventoryId)
                .containsExactly("123456", "543210", "678901");
    }

    @Test
    void listInventoryItemByNameNotInDatabase() {
        assertThat(inventoryItemDao.listInventoryItemByName("nothing")).isEmpty();
    }

    @Test
    void listInventoryItemsByDescriptionSubstring() {
        inventoryItemDao.saveInventoryItem(
                new InventoryItem("678901", ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", "LCD kijelzős", "ABC-123-456", 1));
        inventoryItemDao.saveInventoryItem(
                new InventoryItem("345678", ItemType.HIGH_VALUE_ASSET, "Laptop", 1));
        inventoryItemDao.saveInventoryItem(
                new InventoryItem("123456", ItemType.HIGH_VALUE_ASSET, "Asztali számítógép", "2 LCD monitorral", "DEF-123-456", 1));
        inventoryItemDao.saveInventoryItem(
                new InventoryItem("543210", ItemType.HIGH_VALUE_ASSET, "Mobiltelefon", "Az LCD kijelzőt védő fóliával", "GHI-123456", 1));

        List<InventoryItem> items = inventoryItemDao.listInventoryItemByDescriptionSubstring("LCD");
        assertThat(items).hasSize(3)
                .extracting(InventoryItem::getInventoryId)
                .containsExactly("123456", "543210", "678901");
    }

    @Test
    void listInventoryItemsByDescriptionSubstringNotInDatabase() {
        assertThat(inventoryItemDao.listInventoryItemByDescriptionSubstring("nothing")).isEmpty();
    }

    @Test
    void listItemsByItemTypeTest() {
        InventoryItem firstItem = new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100);
        InventoryItem secondItem = new InventoryItem("345678", ItemType.HIGH_VALUE_ASSET, "Laptop", 1);
        InventoryItem thirdItem = new InventoryItem("123456", ItemType.CONSUMABLE, "Irattartó", 50);
        InventoryItem fourthItem = new InventoryItem("543210", ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", 1);
        inventoryItemDao.saveInventoryItem(firstItem);
        inventoryItemDao.saveInventoryItem(secondItem);
        inventoryItemDao.saveInventoryItem(thirdItem);
        inventoryItemDao.saveInventoryItem(fourthItem);

        List<InventoryItem> items = inventoryItemDao.listInventoryItemByItemType(ItemType.CONSUMABLE);
        assertThat(items).hasSize(2)
                .extracting(InventoryItem::getAmount)
                .containsExactly(50, 100);
    }

    @Test
    void listItemsByNoSuchItemTypeTest() {
        assertThat(inventoryItemDao.listInventoryItemByItemType(ItemType.HIGH_VALUE_ASSET)).isEmpty();
    }

    @Test
    void saveWhenInventoryIdNotUnique() {
        InventoryItem firstItem = new InventoryItem("678901", ItemType.CONSUMABLE, "Toll", 100);
        InventoryItem secondItem = new InventoryItem("345678", ItemType.HIGH_VALUE_ASSET, "Laptop", 1);
        InventoryItem thirdItem = new InventoryItem("678901", ItemType.CONSUMABLE, "Irattartó", 50);
        inventoryItemDao.saveInventoryItem(firstItem);
        inventoryItemDao.saveInventoryItem(secondItem);

        PersistenceException pe = assertThrows(PersistenceException.class,
                () -> inventoryItemDao.saveInventoryItem(thirdItem));
        assertThat(pe.getCause())
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void saveWhenInventoryIdIsNullTest() {
        InventoryItem inventoryItem = new InventoryItem(null, ItemType.HIGH_VALUE_ASSET, "Fényképezőgép", 1);
        PersistenceException pe = assertThrows(PersistenceException.class,
                () -> inventoryItemDao.saveInventoryItem(inventoryItem));
        assertThat(pe.getCause()).isInstanceOf(PropertyValueException.class);
    }

    @Test
    void saveWhenItemTypeIsNullTest() {
        InventoryItem inventoryItem = new InventoryItem("12345", null, "Fényképezőgép", 1);
        PersistenceException pe = assertThrows(PersistenceException.class,
                () -> inventoryItemDao.saveInventoryItem(inventoryItem));
        assertThat(pe.getCause()).isInstanceOf(PropertyValueException.class);
    }

    @Test
    void saveWhenNameIsNullTest() {
        InventoryItem inventoryItem = new InventoryItem("12345", ItemType.HIGH_VALUE_ASSET, null, 1);
        PersistenceException pe = assertThrows(PersistenceException.class,
                () -> inventoryItemDao.saveInventoryItem(inventoryItem));
        assertThat(pe.getCause()).isInstanceOf(PropertyValueException.class);
    }
}