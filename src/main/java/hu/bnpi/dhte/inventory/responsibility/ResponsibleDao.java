package hu.bnpi.dhte.inventory.responsibility;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.Optional;

public class ResponsibleDao {

    private EntityManagerFactory entityManagerFactory;

    public ResponsibleDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Optional<Employee> saveEmployee(Employee employee) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
        entityManager.close();
        return Optional.of(employee);
    }

    public Optional<Employee> findEmployeeById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Employee result = entityManager.find(Employee.class, id);
        entityManager.close();
        return Optional.ofNullable(result);
    }
}
