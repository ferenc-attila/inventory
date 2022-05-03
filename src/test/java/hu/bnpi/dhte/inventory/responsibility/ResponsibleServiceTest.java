package hu.bnpi.dhte.inventory.responsibility;

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
}