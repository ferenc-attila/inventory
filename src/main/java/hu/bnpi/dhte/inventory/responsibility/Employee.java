package hu.bnpi.dhte.inventory.responsibility;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Employee extends Responsible {

    @OneToOne(mappedBy = "leader")
    @JoinColumn(name = "leader_id")
    private Department departmentResponsibleFor;

    public Employee() {
    }

    public Employee(String name) {
        super(name);
    }

    public Employee(Long id, String name) {
        super(id, name);
    }

    public Department getDepartmentResponsibleFor() {
        return departmentResponsibleFor;
    }

    public void setDepartmentResponsibleFor(Department departmentResponsibleFor) {
        this.departmentResponsibleFor = departmentResponsibleFor;
    }
}
