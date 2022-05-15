package hu.bnpi.dhte.inventory.services;

import hu.bnpi.dhte.inventory.entities.responsibility.Department;
import hu.bnpi.dhte.inventory.entities.responsibility.Employee;
import hu.bnpi.dhte.inventory.repositories.ResponsibleDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResponsibleServiceTest {

    @Mock
    private ResponsibleDao responsibleDao;

    @InjectMocks
    private ResponsibleService responsibleService;

    @Test
    void saveEmployeeTest() {
        Employee employee = new Employee("John Smith", "johnsmith@mail.com");
        when(responsibleDao.saveEmployee(employee))
                .thenReturn(Optional.of(employee));
        assertThat(responsibleService.saveEmployee(employee)).isEqualTo("Employee John Smith saved successfully.");
        verify(responsibleDao).saveEmployee(employee);
    }

    @Test
    void saveDepartmentTest() {
        Employee employee = new Employee("John Smith", "johnsmith@mail.com");
        Department department = new Department("HR Department", employee);
        when(responsibleDao.saveDepartment(department))
                .thenReturn(Optional.of(department));
        assertThat(responsibleService.saveDepartment(department)).isEqualTo("Department HR Department saved successfully.");
        verify(responsibleDao).saveDepartment(department);
    }
}