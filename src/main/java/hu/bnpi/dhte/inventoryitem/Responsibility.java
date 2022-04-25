package hu.bnpi.dhte.inventoryitem;

import javax.persistence.Embeddable;

@Embeddable
public class Responsibility {

    private Long employeeId;

    private String employeeName;

    public Responsibility() {
    }

    public Responsibility(Long employeeId, String employeeName) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
