package hu.bnpi.dhte.inventory.entities.responsibility;

import hu.bnpi.dhte.inventory.entities.inventoryitem.InventoryItem;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "responsibles")
public abstract class Responsible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "responsible", cascade = CascadeType.PERSIST)
    @Column(name = "inventory_items")
    private Set<InventoryItem> inventoryItems;

    protected Responsible() {
    }

    protected Responsible(String name) {
        this.name = name;
    }

    protected Responsible(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<InventoryItem> getInventoryItems() {
        return Set.copyOf(inventoryItems);
    }

    public void setInventoryItems(Set<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }
}
