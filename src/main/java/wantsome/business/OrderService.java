package wantsome.business;

import wantsome.business.exceptions.FlowerNotFound;
import wantsome.business.exceptions.OrderNotFound;
import wantsome.database.dao.OrderDao;
import wantsome.database.dto.Order;

import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private OrderDao orderDao;

    private static OrderService instance;

    private OrderService() {
        orderDao = new OrderDao();
    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    public void insert(Order order) {
        try {
            orderDao.insert(order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Order findByID(Integer ID) {
        try {
            return orderDao.findByID(ID);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FlowerNotFound();
        }
    }

    public void deleteOrderByID(Integer ID) {
        try {
            orderDao.deleteOrderByID(ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int getLastId() {
        int res = 0;
        try {
            res = orderDao.getLastId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void setPrice(int price) {
        try {
            orderDao.setPrice(price);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPriceToSpecificOrder(int price, int id) {
        try {
            orderDao.setPriceToSpecificOrder(price, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> findByName(String name) {
        try {
            return orderDao.findByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderNotFound();
        }
    }
}
