package hu.bnpi.dhte;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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

    public InventoryItem findInventoryItemById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        InventoryItem inventoryItem = entityManager.find(InventoryItem.class, id);
        entityManager.close();
        return inventoryItem;
    }
}
