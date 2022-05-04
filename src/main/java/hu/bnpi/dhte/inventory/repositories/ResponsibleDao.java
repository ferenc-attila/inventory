package hu.bnpi.dhte.inventory.repositories;

import hu.bnpi.dhte.inventory.entities.responsibility.Department;
import hu.bnpi.dhte.inventory.entities.responsibility.Employee;
import hu.bnpi.dhte.inventory.entities.responsibility.Responsible;
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
        Optional<Employee> result;
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(employee);
            entityManager.getTransaction().commit();
            result = Optional.of(employee);
        } finally {
            entityManager.close();
        }
        return result;
    }

    public Optional<Employee> findEmployeeById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Optional<Employee> result;
        try {
            Employee employee = entityManager.find(Employee.class, id);
            result = Optional.ofNullable(employee);
        } finally {
            entityManager.close();
        }
        return result;
    }

    public Optional<Department> saveDepartment(Department department) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Optional<Department> result;
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(department);
            entityManager.getTransaction().commit();
            result = Optional.of(department);
        } finally {
            entityManager.close();
        }
        return result;
    }

    public Optional<Department> findDepartmentById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Optional<Department> result;
        try {
            Department department = entityManager.find(Department.class, id);
            result = Optional.ofNullable(department);
        } finally {
            entityManager.close();
        }
        return result;
    }

    public Optional<Responsible> findResponsibleById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Optional<Responsible> result;
        try {
            Responsible responsible = entityManager.find(Responsible.class, id);
            result = Optional.ofNullable(responsible);
        } finally {
            entityManager.close();
        }
        return result;
    }

    public List<Employee> findAllEmployees() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery(
                            "select e from Employee e",
                            Employee.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<Department> findAllDepartments() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("select d from Department d",
                            Department.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<Responsible> findAllResponsible() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("select r from Responsible r",
                            Responsible.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public Optional<Employee> findEmployeeByEmail(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery(
                            "select e from Employee e where e.email = :email",
                            Employee.class)
                    .setParameter("email", email)
                    .getResultList()
                    .stream()
                    .findFirst();
        } finally {
            entityManager.close();
        }
    }
}
