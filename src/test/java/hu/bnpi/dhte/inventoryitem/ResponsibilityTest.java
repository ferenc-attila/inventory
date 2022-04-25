package hu.bnpi.dhte.inventoryitem;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponsibilityTest {

    @Test
    void createTest() {
        Responsibility responsibility = new Responsibility(1L, "Kovács József");
        assertThat(responsibility.getEmployeeName()).isEqualTo("Kovács József");
    }

    @Test
    void setEmployeeIdTest() {
        Responsibility responsibility = new Responsibility();
        responsibility.setEmployeeId(1L);
        assertThat(responsibility.getEmployeeId()).isEqualTo(1);
    }

    @Test
    void setEmployeeNameTest() {
        Responsibility responsibility = new Responsibility();
        responsibility.setEmployeeName("Kovács József");
        assertThat(responsibility.getEmployeeName()).isEqualTo("Kovács József");
    }
}