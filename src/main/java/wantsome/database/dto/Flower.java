package wantsome.database.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Flower {
    private int id;
    String flowerType;
    private FlowerColor color;
    private int pricePerUnit;
    protected int availableStock;
    private String name;


    public Flower(String flowerType, FlowerColor color, int pricePerUnit, int availableStock) {
        this.flowerType = flowerType.toUpperCase();
        this.color = color;
        this.pricePerUnit = pricePerUnit;
        this.availableStock = availableStock;
        this.name = color.toString().toUpperCase() + "_" + flowerType.toUpperCase();
    }

    public Flower(int id, String flowerType, FlowerColor color, int pricePerUnit, int availableStock) {
        this.id = id;
        this.flowerType = flowerType.toUpperCase();
        this.color = color;
        this.pricePerUnit = pricePerUnit;
        this.availableStock = availableStock;
        this.name = color.toString().toUpperCase() + "_" + flowerType.toUpperCase();
    }

    public String getName() {
        return name;
    }

    public String getFlowerType() {
        return flowerType;
    }

    public FlowerColor getColor() {
        return color;
    }

    public int getPricePerUnit() {
        return pricePerUnit;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void updateQuantity(int update) {
        availableStock += update;
    }

    public void setNewQuantity(int update) {
        this.availableStock = update;
    }

    public void setPricePerUnit(int newPrice) {
        this.pricePerUnit = newPrice;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", name='" + name +
                ", flowerType='" + flowerType + '\'' +
                ", color=" + color +
                ", pricePerUnit=" + pricePerUnit +
                ", availableStock=" + availableStock + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flower flower = (Flower) o;
        return id == flower.id &&
                pricePerUnit == flower.pricePerUnit &&
                availableStock == flower.availableStock &&
                Objects.equals(flowerType, flower.flowerType) &&
                color == flower.color &&
                Objects.equals(name, flower.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flowerType, color, pricePerUnit, availableStock, name);
    }
}


