package hu.bnpi.dhte.inventory.responsibility;

import jakarta.persistence.*;

@Entity
public class Employee extends Responsible {

    @OneToOne
    private Department departmentResponsibleFor;

    public Employee() {
    }

    public Employee(String name, Department departmentResponsibleFor) {
        super(name);
        this.departmentResponsibleFor = departmentResponsibleFor;
    }

    public Employee(Long id, String name, Department departmentResponsibleFor) {
        super(id, name);
        this.departmentResponsibleFor = departmentResponsibleFor;
    }

    public Department getDepartmentResponsibleFor() {
        return departmentResponsibleFor;
    }

    public void setDepartmentResponsibleFor(Department departmentResponsibleFor) {
        this.departmentResponsibleFor = departmentResponsibleFor;
    }
}
