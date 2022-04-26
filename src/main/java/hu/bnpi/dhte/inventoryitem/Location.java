package hu.bnpi.dhte.inventoryitem;

import jakarta.persistence.Embeddable;

@Embeddable
public class Location {

    private String locationName;

    private String locationDescription;

    public Location() {
    }

    public Location(String locationName, String locationDescription) {
        this.locationName = locationName;
        this.locationDescription = locationDescription;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }
}
