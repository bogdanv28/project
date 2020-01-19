package wantsome.database.dao;

import wantsome.database.connection.DatabaseManager;
import wantsome.database.dto.Flower;
import wantsome.database.dto.FlowerColor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlowerDao {
    private static final String INSERT_FLOWER_SQL = "INSERT INTO FLOWER(FLOWER_TYPE, COLOR, FLOWER_NAME, PRICE_PER_UNIT, AVAILABLE_STOCK) VALUES(?,?,?,?,?)";
    private static final String SELECT_FLOWER_BYID_SQL = "SELECT * FROM FLOWER WHERE ID = ?";
    private static final String SELECT_FLOWER_BYNAME_SQL = "SELECT * FROM FLOWER WHERE FLOWER_NAME = ?";
    private static final String DELETE_FLOWER_BYID_SQL = "DELETE FROM FLOWER WHERE ID = ?";
    private static final String UPDATE_ADD_TO_FLOWER_STOCK_BYID_SQL = "UPDATE FLOWER SET AVAILABLE_STOCK = AVAILABLE_STOCK + ?  WHERE ID = ?;";
    private static final String UPDATE_SET_NEW_FLOWER_STOCK_BYID_SQL = "UPDATE FLOWER SET AVAILABLE_STOCK = ?  WHERE ID = ?;";
    private static final String UPDATE_FLOWER_PRICE_BYID_SQL = "UPDATE FLOWER SET PRICE_PER_UNIT = ?  WHERE ID = ?;";
    private static final String GET_FLOWER_PRICE_BYID_SQL = "SELECT PRICE_PER_UNIT FROM FLOWER WHERE ID = ?;";
    private static final String GET_FLOWER_STOCK_BYID_SQL = "SELECT AVAILABLE_STOCK FROM FLOWER WHERE ID = ?;";


    public void insert(Flower flower) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FLOWER_SQL);
            preparedStatement.setString(1, flower.getFlowerType());
            preparedStatement.setString(2, flower.getColor().toString());
            preparedStatement.setString(3, flower.getName());
            preparedStatement.setInt(4, flower.getPricePerUnit());
            preparedStatement.setInt(5, flower.getAvailableStock());

            preparedStatement.executeUpdate();
        }
    }

    public void deleteItemByID(Integer Id) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FLOWER_BYID_SQL);

            preparedStatement.setInt(1, Id);
            preparedStatement.executeUpdate();
        }
    }

    public Flower findByName(String name) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FLOWER_BYNAME_SQL);

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Integer rsId = resultSet.getInt("ID");
                String flowerType = resultSet.getString("FLOWER_TYPE");
                Integer price_per_unit = resultSet.getInt("PRICE_PER_UNIT");
                Integer available_stock = resultSet.getInt("AVAILABLE_STOCK");

                Flower res =
                        new Flower
                                (rsId, flowerType, FlowerColor.valueOf(resultSet.getString("COLOR")), price_per_unit, available_stock);

                return res;
            }
        }
        return null;
    }

    public Flower findByID(Integer ID) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FLOWER_BYID_SQL);

            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Integer rsId = resultSet.getInt("ID");
                String flowerType = resultSet.getString("FLOWER_TYPE");
                Integer price_per_unit = resultSet.getInt("PRICE_PER_UNIT");
                Integer available_stock = resultSet.getInt("AVAILABLE_STOCK");

                Flower res =
                        new Flower
                                (rsId, flowerType, FlowerColor.valueOf(resultSet.getString("COLOR")), price_per_unit, available_stock);

                return res;
            }
        }
        return null;
    }

    public List<Flower> seeFlowerList() throws SQLException {
        List<Flower> flowers = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM FLOWER");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Flower flower =
                        new Flower(resultSet.getInt("ID"),
                                resultSet.getString("FLOWER_TYPE"),
                                FlowerColor.valueOf(resultSet.getString("COLOR")),
                                resultSet.getInt("PRICE_PER_UNIT"),
                                resultSet.getInt("AVAILABLE_STOCK"));

                flowers.add(flower);
            }
        }
        return flowers;
    }

    public void addToStock(Integer Id, Integer addToStock) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ADD_TO_FLOWER_STOCK_BYID_SQL);
            preparedStatement.setInt(2, Id);
            preparedStatement.setInt(1, addToStock);

            preparedStatement.executeUpdate();
        }
    }

    public void setNewStock(Integer Id, Integer newStock) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SET_NEW_FLOWER_STOCK_BYID_SQL);
            preparedStatement.setInt(2, Id);
            preparedStatement.setInt(1, newStock);

            preparedStatement.executeUpdate();
        }
    }

    public void updatePrice(Integer Id, Integer newPrice) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FLOWER_PRICE_BYID_SQL);
            preparedStatement.setInt(2, Id);
            preparedStatement.setInt(1, newPrice);

            preparedStatement.executeUpdate();
        }
    }

    public int getFlowerPrice(Integer Id) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_FLOWER_PRICE_BYID_SQL);

            preparedStatement.setInt(1, Id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Integer price_per_unit = resultSet.getInt("PRICE_PER_UNIT");

                return price_per_unit;
            }
            return 0;
        }
    }

    public List<Flower> seeFlowerListByPriceAsc() throws SQLException {
        List<Flower> flowers = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM FLOWER ORDER BY PRICE_PER_UNIT ASC;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Flower flower =
                        new Flower(resultSet.getInt("ID"),
                                resultSet.getString("FLOWER_TYPE"),
                                FlowerColor.valueOf(resultSet.getString("COLOR")),
                                resultSet.getInt("PRICE_PER_UNIT"),
                                resultSet.getInt("AVAILABLE_STOCK"));

                flowers.add(flower);
            }
        }
        return flowers;
    }

    public List<Flower> seeFlowerListByStockAsc() throws SQLException {
        List<Flower> flowers = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM FLOWER ORDER BY AVAILABLE_STOCK ASC;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Flower flower =
                        new Flower(resultSet.getInt("ID"),
                                resultSet.getString("FLOWER_TYPE"),
                                FlowerColor.valueOf(resultSet.getString("COLOR")),
                                resultSet.getInt("PRICE_PER_UNIT"),
                                resultSet.getInt("AVAILABLE_STOCK"));

                flowers.add(flower);
            }
        }
        return flowers;
    }

    public int getFlowerStock(int id) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_FLOWER_STOCK_BYID_SQL);

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int stock = resultSet.getInt("AVAILABLE_STOCK");

                return stock;
            }
            return 0;
        }
    }
}
