package hu.bnpi.dhte.inventoryitem;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Optional;

public class InventoryItemDao {

    private EntityManagerFactory entityManagerFactory;

    public InventoryItemDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void saveInventoryItem(InventoryItem inventoryItem) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(inventoryItem);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateInventoryItemDescription(long id, String description) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        InventoryItem inventoryItem = entityManager.find(InventoryItem.class, id);
        inventoryItem.setDescription(description);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateInventoryItemSerialNumber(long id, String serialNumber) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        InventoryItem inventoryItem = entityManager.find(InventoryItem.class, id);
        inventoryItem.setSerialNumber(serialNumber);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Optional<InventoryItem> findInventoryItemById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        InventoryItem inventoryItem = entityManager.find(InventoryItem.class, id);
        entityManager.close();
        return Optional.ofNullable(inventoryItem);
    }

    public List<InventoryItem> listAllInventoryItems() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createQuery("select i from InventoryItem i order by i.inventoryId",
                        InventoryItem.class)
                .getResultList();
    }

    public List<InventoryItem> listInventoryItemByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createQuery("select i from InventoryItem i where i.name = :name order by i.inventoryId",
                        InventoryItem.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<InventoryItem> listInventoryItemByDescriptionSubstring(String substring) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createQuery("select i from InventoryItem i where i.description like :substring  order by i.inventoryId",
                        InventoryItem.class)
                .setParameter("substring", "%" + substring + "%")
                .getResultList();
    }

    public List<InventoryItem> listInventoryItemByItemType(ItemType itemType) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createQuery("select i from InventoryItem i where i.itemType = :itemType order by i.inventoryId",
                        InventoryItem.class)
                .setParameter("itemType", itemType)
                .getResultList();
    }
}
