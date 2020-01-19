package wantsome.database.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bouquet {
    private int id;
    private List<BouquetEntry> flowers = new ArrayList<>();
    private int orderId;
    private int price;

    public Bouquet() {
    }

    public Bouquet(int orderId) {
        this.orderId = orderId;
    }

    public Bouquet(int id, int orderId, int price) {
        this.orderId = orderId;
        this.id = id;
        this.price = price;
    }

    public void addToBouquet(BouquetEntry bouquetEntry) {
        flowers.add(bouquetEntry);
    }


    public int getId() {
        return this.id;
    }

    public int getOrderId() {
        return orderId;
    }

    public List<BouquetEntry> getFlowers() {
        return flowers;
    }

    @Override
    public String toString() {
        return "Bouquet{" +
                "bouquetID=" + id +
                ", flowers=" + flowers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bouquet bouquet = (Bouquet) o;
        return id == bouquet.id &&
                Objects.equals(flowers, bouquet.flowers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flowers);
    }

    public int getPrice() {

        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}