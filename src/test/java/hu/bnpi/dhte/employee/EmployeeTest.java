package hu.bnpi.dhte.employee;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeTest {

    @Test
    void createTest() {
        Employee employee = new Employee("Szabó József", "szaboj@example.com");
        assertThat(employee.getName()).isEqualTo("Szabó József");
    }

    @Test
    void setIdTest() {
        Employee employee = new Employee();
        employee.setId(1L);
        assertThat(employee.getId()).isEqualTo(1);
    }

    @Test
    void setNameTest() {
        Employee employee = new Employee();
        employee.setName("Szabó József");
        assertThat(employee.getName()).isEqualTo("Szabó József");
    }

    @Test
    void setEmailTest() {
        Employee employee = new Employee();
        employee.setEmail("szaboj@example.com");
        assertThat(employee.getEmail()).isEqualTo("szaboj@example.com");
    }
}