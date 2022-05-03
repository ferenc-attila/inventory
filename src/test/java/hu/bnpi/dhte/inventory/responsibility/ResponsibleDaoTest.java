package hu.bnpi.dhte.inventory.responsibility;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
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

    @Test
    void findAllEmployeesTest() {
        Employee firstEmployee = new Employee("John Smith");
        Employee secondEmployee = new Employee("Jack Smith");
        Employee thirdEmployee = new Employee("Jane Smith");
        Department department = new Department("HR Department", thirdEmployee);
        responsibleDao.saveEmployee(firstEmployee);
        responsibleDao.saveEmployee(secondEmployee);
        responsibleDao.saveEmployee(thirdEmployee);
        responsibleDao.saveDepartment(department);

        List<Employee> employees = responsibleDao.findAllEmployees();

        assertThat(employees).hasSize(3)
                .extracting(Responsible::getName)
                .containsExactlyInAnyOrder("John Smith", "Jack Smith", "Jane Smith");
    }

    @Test
    void findAllDepartmentsTest() {
        Employee firstEmployee = new Employee("John Smith");
        Employee secondEmployee = new Employee("Jack Smith");
        Employee thirdEmployee = new Employee("Jane Smith");
        Department firstDepartment = new Department("HR Department", thirdEmployee);
        Department secondDepartment = new Department("Legal Department", secondEmployee);
        Department thirdDepartment = new Department("PR Department", firstEmployee);

        responsibleDao.saveEmployee(firstEmployee);
        responsibleDao.saveEmployee(secondEmployee);
        responsibleDao.saveEmployee(thirdEmployee);
        responsibleDao.saveDepartment(firstDepartment);
        responsibleDao.saveDepartment(secondDepartment);
        responsibleDao.saveDepartment(thirdDepartment);

        List<Department> result = responsibleDao.findAllDepartments();

        assertThat(result).hasSize(3)
                .extracting(Responsible::getName)
                .containsExactlyInAnyOrder("HR Department", "Legal Department", "PR Department");
    }

    @Test
    void findAllResponsibleTest() {
        Employee firstEmployee = new Employee("John Smith");
        Employee secondEmployee = new Employee("Jack Smith");
        Employee thirdEmployee = new Employee("Jane Smith");
        Department firstDepartment = new Department("HR Department", thirdEmployee);
        Department secondDepartment = new Department("Legal Department", secondEmployee);
        Department thirdDepartment = new Department("PR Department", firstEmployee);

        responsibleDao.saveEmployee(firstEmployee);
        responsibleDao.saveEmployee(secondEmployee);
        responsibleDao.saveEmployee(thirdEmployee);
        responsibleDao.saveDepartment(firstDepartment);
        responsibleDao.saveDepartment(secondDepartment);
        responsibleDao.saveDepartment(thirdDepartment);

        List<Responsible> result = responsibleDao.findAllResponsible();

        assertThat(result).hasSize(6)
                .extracting(Responsible::getName)
                .contains("Jane Smith", "Legal Department");
    }
}