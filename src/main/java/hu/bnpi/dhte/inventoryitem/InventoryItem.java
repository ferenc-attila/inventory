package hu.bnpi.dhte.inventoryitem;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemId;

    @Enumerated(value = EnumType.STRING)
    private ItemType itemType;

    private String name;

    private String description;

    @Column(name = "serial_number")
    private String serialNumber;

    @Embedded
    @AttributeOverride(name = "dateOfPurchase", column = @Column(name = "purchase_date"))
    @AttributeOverride(name = "priceOfPurchase", column = @Column(name = "purchase_price"))
    @AttributeOverride(name = "amortizedCost", column = @Column(name = "amortized_cost"))
    private FinancialData financialData;

    private int amount;

    @Embedded
    private Responsibility responsibility;

    @Embedded
    private Location location;

    public InventoryItem() {
    }

    public InventoryItem(String name, ItemType itemType) {
        this.name = name;
        this.itemType = itemType;
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

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public FinancialData getFinancialData() {
        return financialData;
    }

    public void setFinancialData(FinancialData financialData) {
        this.financialData = financialData;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Responsibility getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(Responsibility responsibility) {
        this.responsibility = responsibility;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
