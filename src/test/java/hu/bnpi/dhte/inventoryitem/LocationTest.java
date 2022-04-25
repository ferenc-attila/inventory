package hu.bnpi.dhte.inventoryitem;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocationTest {

    @Test
    void createTest() {
        Location location = new Location("Office", "Kitchen in the office");
        assertThat(location.getLocationName()).isEqualTo("Office");
    }

    @Test
    void setLocationNameTest() {
        Location location = new Location();
        location.setLocationName("Store nr. 3");
        assertThat(location.getLocationName()).isEqualTo("Store nr. 3");
    }

    @Test
    void setLocationDescriptionTest() {
        Location location = new Location();
        location.setLocationDescription("Store nr. 3");
        assertThat(location.getLocationDescription()).isEqualTo("Store nr. 3");
    }
}