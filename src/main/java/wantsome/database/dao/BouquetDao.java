package wantsome.database.dao;

import wantsome.database.connection.DatabaseManager;
import wantsome.database.dto.Bouquet;
import wantsome.database.dto.Flower;
import wantsome.database.dto.FlowerColor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BouquetDao {
    private static final String NEW_BOUQUETS_SQL = "INSERT INTO BOUQUETS(ORDERS_ID, PRICE) VALUES(?, ?)";
    private static final String DELETE_BOUQUETS_BYID_SQL = "DELETE FROM BOUQUETS WHERE ID = ?";
    private static final String GET_LAST_ID_SQL = "SELECT MAX(ID) from BOUQUETS";
    private static final String UPDATE_BOUQUET_PRICE_SQL = "UPDATE BOUQUETS SET PRICE = ?  WHERE ID = ?;";

    public void insert(Bouquet bouquet) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(NEW_BOUQUETS_SQL);

            preparedStatement.setInt(1, bouquet.getOrderId());
            preparedStatement.setInt(2, bouquet.getPrice());

            preparedStatement.executeUpdate();
        }
    }

    public void deleteBouquetsByid(Integer ID) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOUQUETS_BYID_SQL);

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

    public void updatePrice(int bouquetId, int price) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOUQUET_PRICE_SQL);
            preparedStatement.setInt(2, bouquetId);
            preparedStatement.setInt(1, price);

            preparedStatement.executeUpdate();
        }
    }

    public List<Bouquet> seeBouquetListByOrderId(int orderId) throws SQLException {
        List<Bouquet> bouquets = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM BOUQUETS WHERE ORDERS_ID=?");
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Bouquet b =
                        new Bouquet(resultSet.getInt("ID"),
                                resultSet.getInt("ORDERS_ID"),
                                resultSet.getInt("PRICE"));
                bouquets.add(b);
            }
        }
        return bouquets;
    }
}
