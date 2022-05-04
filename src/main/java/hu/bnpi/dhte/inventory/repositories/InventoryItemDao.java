package hu.bnpi.dhte.inventory.repositories;

import hu.bnpi.dhte.inventory.entities.inventoryitem.InventoryItem;
import hu.bnpi.dhte.inventory.entities.inventoryitem.ItemType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Optional;

public class InventoryItemDao {

    private final EntityManagerFactory entityManagerFactory;

    public InventoryItemDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Optional<InventoryItem> saveInventoryItem(InventoryItem inventoryItem) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Optional<InventoryItem> result;
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(inventoryItem);
            result = Optional.of(inventoryItem);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
        return result;
    }

    public Optional<InventoryItem> updateInventoryItem(long id, String value, UpdateStringAttribute updateStringAttribute) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Optional<InventoryItem> result;
        try {
            entityManager.getTransaction().begin();
            InventoryItem inventoryItem = entityManager.find(InventoryItem.class, id);
            updateStringAttribute.updateStringAttribute(inventoryItem, value);
            entityManager.getTransaction().commit();
            result = Optional.of(inventoryItem);
        } finally {
            entityManager.close();
        }
        return result;
    }

    public void removeInventoryItem(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            InventoryItem inventoryItem = entityManager.find(InventoryItem.class, id);
            entityManager.remove(inventoryItem);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public Optional<InventoryItem> findInventoryItemById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Optional<InventoryItem> result;
        try {
            result = Optional.ofNullable(entityManager.find(InventoryItem.class, id));
        } finally {
            entityManager.close();
        }
        return result;
    }

    public Optional<InventoryItem> findInventoryItemByInventoryId(String inventoryId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Optional<InventoryItem> result;
        try {
            result = entityManager.createQuery("select i from InventoryItem i where i.inventoryId = :inventoryId",
                            InventoryItem.class)
                    .setParameter("inventoryId", inventoryId)
                    .getResultList()
                    .stream()
                    .findFirst();
        } finally {
            entityManager.close();
        }
        return result;
    }

    public List<InventoryItem> listAllInventoryItems() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("select i from InventoryItem i order by i.inventoryId",
                            InventoryItem.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<InventoryItem> listInventoryItemByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("select i from InventoryItem i where i.name = :name order by i.inventoryId",
                            InventoryItem.class)
                    .setParameter("name", name)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<InventoryItem> listInventoryItemByDescriptionSubstring(String substring) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("select i from InventoryItem i where i.description like :substring  order by i.inventoryId",
                            InventoryItem.class)
                    .setParameter("substring", "%" + substring + "%")
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<InventoryItem> listInventoryItemByItemType(ItemType itemType) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("select i from InventoryItem i where i.itemType = :itemType order by i.inventoryId",
                            InventoryItem.class)
                    .setParameter("itemType", itemType)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }
}
