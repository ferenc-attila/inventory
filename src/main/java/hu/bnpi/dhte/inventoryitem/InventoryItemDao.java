package hu.bnpi.dhte.inventoryitem;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;

public class InventoryItemDao {

    private EntityManagerFactory entityManagerFactory;

    public InventoryItemDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public boolean isInventoryIdExists(String inventoryId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Long count = entityManager.createQuery("select count(i) from InventoryItem i where i.inventoryId =:inventoryId",
                Long.class)
                .setParameter("inventoryId", inventoryId)
                .getSingleResult();
        return !count.equals(0L);
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

    public boolean removeInventoryItem(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        boolean success = false;
        try {
            entityManager.getTransaction().begin();
            InventoryItem inventoryItem = entityManager.find(InventoryItem.class, id);
            entityManager.remove(inventoryItem);
            entityManager.getTransaction().commit();
            success = true;
        } finally {
            entityManager.close();
        }
        return success;
    }

    public Optional<InventoryItem> findInventoryItemById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return Optional.ofNullable(entityManager.find(InventoryItem.class, id));
        } finally {
            entityManager.close();
        }
    }

    public Optional<InventoryItem> findInventoryItemByInventoryId(String inventoryId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return Optional.of(entityManager.createQuery("select i from InventoryItem i where i.inventoryId = :inventoryId",
                            InventoryItem.class)
                    .setParameter("inventoryId", inventoryId)
                    .getSingleResult());
        } catch (NoResultException nre) {
            return Optional.empty();
        } finally {
            entityManager.close();
        }
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
