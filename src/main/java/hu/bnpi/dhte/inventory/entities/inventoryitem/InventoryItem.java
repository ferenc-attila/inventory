package hu.bnpi.dhte.inventory.entities.inventoryitem;

import hu.bnpi.dhte.inventory.entities.responsibility.Responsible;
import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inventory_id", unique = true, nullable = false, length = 100)
    private String inventoryId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "item_type", nullable = false)
    private ItemType itemType;

    @Column(name = "item_name", nullable = false)
    private String name;

    private String description;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(nullable = false)
    private int amount;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "responsible_id")
    private Responsible responsible;

    public InventoryItem() {
    }

    public InventoryItem(String inventoryId, ItemType itemType, String name, int amount) {
        this.inventoryId = inventoryId;
        this.itemType = itemType;
        this.name = name;
        this.amount = amount;
    }

    public InventoryItem(String inventoryId, ItemType itemType, String name, String description, String serialNumber, int amount) {
        this.inventoryId = inventoryId;
        this.itemType = itemType;
        this.name = name;
        this.description = description;
        this.serialNumber = serialNumber;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getAmount() {
        return amount;
    }

    public Responsible getResponsible() {
        return responsible;
    }

    public void setResponsible(Responsible responsible) {
        this.responsible = responsible;
    }
}
