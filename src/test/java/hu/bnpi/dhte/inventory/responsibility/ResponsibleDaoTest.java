package hu.bnpi.dhte.inventory.responsibility;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ResponsibleDaoTest {

    private EntityManagerFactory entityManagerFactory;

    private ResponsibleDao responsibleDao;

    @BeforeEach
    void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pu-test");
        responsibleDao = new ResponsibleDao(entityManagerFactory);
    }

    @Test
    void saveAndFindEmployeeTest() {
        Employee employee = new Employee("John Smith");
        responsibleDao.saveEmployee(employee);

        Optional<Employee> result = responsibleDao.findEmployeeById(employee.getId());

        assertThat(result.get().getName()).isEqualTo("John Smith");
    }

    @Test
    void saveAndFindDepartmentTest() {
        Employee employee = new Employee("Jane Smith");
        responsibleDao.saveEmployee(employee);
        Department department = new Department("HR Department", employee);
        responsibleDao.saveDepartment(department);

        Optional<Department> departmentResult = responsibleDao.findDepartmentById(department.getId());
        Optional<Employee> employeeResult = responsibleDao.findEmployeeById(employee.getId());

        assertThat(departmentResult.get().getLeader().getName()).isEqualTo("Jane Smith");
        assertThat(employeeResult.get().getDepartmentResponsibleFor().getName()).isEqualTo("HR Department");
    }

    @Test
    void findResponsibleByIdTest() {
        Employee employee = new Employee("Jane Smith");
        responsibleDao.saveEmployee(employee);
        Department department = new Department("HR Department", employee);
        responsibleDao.saveDepartment(department);

        Optional<Responsible> employeeResult = responsibleDao.findResponsibleById(employee.getId());
        Optional<Responsible> departmentResult = responsibleDao.findResponsibleById(department.getId());

        assertThat(employeeResult.get())
                .isExactlyInstanceOf(Employee.class)
                .extracting(Responsible::getName)
                .isEqualTo("Jane Smith");

        assertThat(departmentResult.get())
                .isExactlyInstanceOf(Department.class)
                .extracting(Responsible::getName)
                .isEqualTo("HR Department");
    }
}