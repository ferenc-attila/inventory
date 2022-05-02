package hu.bnpi.dhte.inventory.responsibility;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Department extends Responsible{

    @OneToOne(optional = false)
    private Employee leader;

    public Department() {
    }

    public Department(String name, Employee leader) {
        super(name);
        this.leader = leader;
    }

    public Department(Long id, String name, Employee leader) {
        super(id, name);
        this.leader = leader;
    }

    public Employee getLeader() {
        return leader;
    }

    public void setLeader(Employee leader) {
        this.leader = leader;
    }
}
