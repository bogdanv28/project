package wantsome.database.dto;

import java.util.Objects;

public class BouquetEntry {
    private int id;
    private Flower flower;
    private int quantity;
    private int flowerId;
    private int bouquetId;
    private String flowerName;

    public BouquetEntry(Flower flower, int quantity) {
        this.flower = flower;
        this.quantity = quantity;
    }

    public BouquetEntry(int flowerId, int bouquetId, int quantity) {
        this.quantity = quantity;
        this.flowerId = flowerId;
        this.bouquetId = bouquetId;
    }

    public BouquetEntry(int id, int flowerId, int bouquetId, int quantity) {
        this.id = id;
        this.quantity = quantity;
        this.flowerId = flowerId;
        this.bouquetId = bouquetId;
    }

    public BouquetEntry(int id, String flowerName, int bouquetId, int quantity) {
        this.id = id;
        this.quantity = quantity;
        this.flowerName = flowerName;
        this.bouquetId = bouquetId;
    }

    public int getBouquetEntryPrice() {
        return this.flower.getPricePerUnit() * this.quantity;
    }

    public int getId() {
        return id;
    }

    public int getFlowerId() {
        return flowerId;
    }

    public int getBouquetId() {
        return bouquetId;
    }

    public int getQuantity() {
        return quantity;
    }

//    @Override
//    public String toString() {
//        return "BouquetEntry{" +
//                "id=" + id +
//                ", quantity=" + quantity +
//                ", flowerId=" + flowerId +
//                ", bouquetId=" + bouquetId +
//                '}';
//    }
    @Override
    public String toString() {
        return "BouquetEntry: " +
                "quantity=" + quantity +
                ", flowerName=" + flowerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BouquetEntry that = (BouquetEntry) o;
        return id == that.id &&
                quantity == that.quantity &&
                flowerId == that.flowerId &&
                bouquetId == that.bouquetId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, flowerId, bouquetId);
    }
}
