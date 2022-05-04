package hu.bnpi.dhte.inventory.entities.responsibility;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Employee extends Responsible {

    @Column(unique = true)
    private String email;

    @OneToOne(mappedBy = "leader")
    @JoinColumn(name = "leader_id")
    private Department departmentResponsibleFor;

    public Employee() {
    }

    public Employee(String name, String email) {
        super(name);
        this.email = email;
    }

    public Employee(Long id, String name, String email) {
        super(id, name);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartmentResponsibleFor() {
        return departmentResponsibleFor;
    }

    public void setDepartmentResponsibleFor(Department departmentResponsibleFor) {
        this.departmentResponsibleFor = departmentResponsibleFor;
    }
}
