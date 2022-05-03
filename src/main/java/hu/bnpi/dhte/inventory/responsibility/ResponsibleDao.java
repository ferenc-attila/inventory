package hu.bnpi.dhte.inventory.responsibility;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
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

    public Optional<Department> saveDepartment(Department department) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(department);
        entityManager.getTransaction().commit();
        entityManager.close();
        return Optional.of(department);
    }

    public Optional<Department> findDepartmentById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Department result = entityManager.find(Department.class, id);
        entityManager.close();
        return Optional.ofNullable(result);
    }

    public Optional<Responsible> findResponsibleById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Responsible result = entityManager.find(Responsible.class, id);
        entityManager.close();
        return Optional.ofNullable(result);
    }

    public List<Employee> findAllEmployees() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Employee> result = entityManager.createQuery(
                "select e from Employee e",
                Employee.class)
                .getResultList();
        entityManager.close();
        return result;
    }

    public List<Department> findAllDepartments() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Department> result = entityManager.createQuery(
                "select d from Department d",
                Department.class)
                .getResultList();
        entityManager.close();
        return result;
    }

    public List<Responsible> findAllResponsible() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Responsible> result = entityManager.createQuery(
                "select r from Responsible r",
                Responsible.class)
                .getResultList();
        entityManager.close();
        return result;
    }
}
