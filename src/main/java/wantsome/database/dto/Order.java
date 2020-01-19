package wantsome.database.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private int id;
    private List<Bouquet> bouquets = new ArrayList<>();
    private int totalPrice = 0;
    private String clientFirstName;
    private String clientLastName;
    private String phoneNumber;
    private String address;
    private String date;


    public Order(String clientFirstName, String clientLastName, String phoneNumber, String address) {
        this.clientFirstName = clientFirstName.toUpperCase();
        this.clientLastName = clientLastName.toUpperCase();
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Order(int id, String clientFirstName, String clientLastName, String phoneNumber, String address) {
        this.id = id;
        this.clientFirstName = clientFirstName.toUpperCase();
        this.clientLastName = clientLastName.toUpperCase();
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Order(int id, String clientFirstName, String clientLastName, String phoneNumber, String address, int totalPrice, String date) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.clientFirstName = clientFirstName.toUpperCase();
        this.clientLastName = clientLastName.toUpperCase();
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.date = date;
    }

    public List<Bouquet> getBouquets() {
        return bouquets;
    }

    public void addBouquets(Bouquet bouquet) {
        bouquets.add(bouquet);
    }

    public int getId() {
        return this.id;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }


    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", clientFirstName='" + clientFirstName + '\'' +
                ", clientLastName='" + clientLastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", totalPrice=" + totalPrice +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return totalPrice == order.totalPrice &&
                Objects.equals(bouquets, order.bouquets) &&
                Objects.equals(clientFirstName, order.clientFirstName) &&
                Objects.equals(clientLastName, order.clientLastName) &&
                Objects.equals(phoneNumber, order.phoneNumber) &&
                Objects.equals(address, order.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bouquets, totalPrice, clientFirstName, clientLastName, phoneNumber, address);
    }

    public void setId(int id) {
        this.id = id;
    }
}
