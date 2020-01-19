package wantsome.database.dao;

import wantsome.database.connection.DatabaseManager;
import wantsome.database.dto.Order;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private static final String NEW_ORDERS_SQL = "INSERT INTO ORDERS" +
            "(CLIENT_FIRST_NAME, CLIENT_LAST_NAME, CLIENT_PHONE_NUMBER, CLIENT_ADDRESS, PRICE, DATE) VALUES(?,?,?,?,?,?)";

    private static final String SELECT_ORDERS_BYID_SQL = "SELECT * FROM ORDERS WHERE ID = ?";
    private static final String DELETE_ORDERS_BYID_SQL = "DELETE FROM ORDERS WHERE ID = ?";
    private static final String GET_LAST_ID_SQL = "SELECT MAX(ID) from ORDERS";
    private static final String UPDATE_PRICE_SQL = "UPDATE ORDERS SET PRICE = ?  WHERE ID = ?";
    private static final String SEARCH_CLIENT_SQL = "SELECT * FROM ORDERS WHERE CLIENT_LAST_NAME LIKE ?";


    public void insert(Order order) throws SQLException {
        LocalDateTime date = LocalDateTime.now();
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(NEW_ORDERS_SQL);

            preparedStatement.setString(1, order.getClientFirstName().toUpperCase());
            preparedStatement.setString(2, order.getClientLastName().toUpperCase());
            preparedStatement.setString(3, order.getPhoneNumber());
            preparedStatement.setString(4, order.getAddress());
            preparedStatement.setInt(5, order.getTotalPrice());
            preparedStatement.setString(6, date.toString());

            preparedStatement.executeUpdate();
        }
    }

    public List<Order> findByName(String name) throws SQLException {
        List<Order> retList = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_CLIENT_SQL);

            preparedStatement.setString(1, name.toUpperCase());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int rsId = resultSet.getInt("ID");
                String firstName = resultSet.getString("CLIENT_FIRST_NAME");
                String lastName = resultSet.getString("CLIENT_LAST_NAME");
                String phoneNumber = resultSet.getString("CLIENT_PHONE_NUMBER");
                String address = resultSet.getString("CLIENT_ADDRESS");
                int totalPrice = resultSet.getInt("PRICE");
                String date = resultSet.getString("DATE");

                Order res =
                        new Order(rsId, firstName, lastName, phoneNumber, address, totalPrice, date);

                retList.add(res);

            }
        }
        return retList;
    }

    public Order findByID(Integer ID) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERS_BYID_SQL);

            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int rsId = resultSet.getInt("ID");
                String firstName = resultSet.getString("CLIENT_FIRST_NAME");
                String lastName = resultSet.getString("CLIENT_LAST_NAME");
                String phoneNumber = resultSet.getString("CLIENT_PHONE_NUMBER");
                String address = resultSet.getString("CLIENT_ADDRESS");
                int totalPrice = resultSet.getInt("PRICE");
                String date = resultSet.getString("DATE");

                Order res =
                        new Order(rsId, firstName, lastName, phoneNumber, address, totalPrice, date);
                res.setTotalPrice(totalPrice);

                return res;
            }
        }
        return null;
    }

    public void deleteOrderByID(Integer ID) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDERS_BYID_SQL);

            preparedStatement.setInt(1, ID);
            preparedStatement.executeUpdate();
        }
    }

    public int getLastId() throws SQLException {

        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_LAST_ID_SQL);

            ResultSet resultSet = preparedStatement.executeQuery();

            int res = resultSet.getInt("MAX(ID)");
            return res;
        }
    }

    public void setPrice(int price) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRICE_SQL);
            preparedStatement.setInt(2, getLastId());
            preparedStatement.setInt(1, price);

            preparedStatement.executeUpdate();
        }
    }

    public void setPriceToSpecificOrder(int price, int id) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRICE_SQL);
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(1, price);

            preparedStatement.executeUpdate();
        }
    }
}
