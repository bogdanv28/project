package wantsome.database.dao;

import wantsome.business.FlowerService;
import wantsome.database.connection.DatabaseManager;
import wantsome.database.dto.BouquetEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BouquetEntryDao {
    private static final String NEW_BOUQUET_ENTRY_SQL = "INSERT INTO BOUQUET_ENTRY(FLOWER_ID, BOUQUET_ID, QUANTITY) VALUES(?,?,?)";
    private static final String DELETE_BOUQUET_ENTRY_SQL = "DELETE FROM BOUQUET_ENTRY WHERE ID = ?;";
    private static final String DELETE_BOUQUET_ENTRY_ByBoqId_SQL = "DELETE FROM BOUQUET_ENTRY WHERE BOUQUET_ID = ?;";
    private static final String SELECT_BOUQUET_ID_SQL = "SELECT ID, FLOWER_ID, BOUQUET_ID, QUANTITY FROM BOUQUET_ENTRY WHERE BOUQUET_ID = ?;";
    private static final String GET_LAST_ID_SQL = "SELECT MAX(ID) from BOUQUET_ENTRY";
    private static final String SELECT_BOUQUET_BYID_SQL = "SELECT * FROM BOUQUET_ENTRY WHERE ID = ?";
    private static final String SELECT_BOUQUET_BYBOQID_SQL = "SELECT * FROM BOUQUET_ENTRY WHERE ID = ?";

    public BouquetEntry findByID(int id) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOUQUET_BYID_SQL);

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Integer rsId = resultSet.getInt("ID");
                Integer flowerId = resultSet.getInt("FLOWER_ID");
                Integer bouquetId = resultSet.getInt("BOUQUET_ID");
                Integer quantity = resultSet.getInt("QUANTITY");

                BouquetEntry be =
                        new BouquetEntry(rsId, flowerId, bouquetId, quantity);

                return be;
            }
        }
        return null;
    }

    public void insert(BouquetEntry bouquetEntry) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(NEW_BOUQUET_ENTRY_SQL);
            preparedStatement.setInt(1, bouquetEntry.getFlowerId());
            preparedStatement.setInt(2, bouquetEntry.getBouquetId());
            preparedStatement.setInt(3, bouquetEntry.getQuantity());

            preparedStatement.executeUpdate();
        }
    }

    public void deleteBouquetEntryByid(Integer ID) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOUQUET_ENTRY_SQL);

            preparedStatement.setInt(1, ID);
            preparedStatement.executeUpdate();
        }
    }


    public List<BouquetEntry> entryListByBouquetId(int bouquetId) throws SQLException {
        List<BouquetEntry> entries = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOUQUET_ID_SQL);
            preparedStatement.setInt(1, bouquetId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BouquetEntry bouquetEntry =
                        new BouquetEntry(resultSet.getInt("ID"),
                                resultSet.getInt("FLOWER_ID"),
                                resultSet.getInt("BOUQUET_ID"),
                                resultSet.getInt("QUANTITY"));

                entries.add(bouquetEntry);
            }
        }
        return entries;
    }

    public int getLastId() throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_LAST_ID_SQL);

            ResultSet resultSet = preparedStatement.executeQuery();

            int res = resultSet.getInt("MAX(ID)");
            return res;
        }

    }

    public List<BouquetEntry> entryListByBouquetId2(int bouquetId) throws SQLException {
        List<BouquetEntry> entries = new ArrayList<>();
        FlowerService flowerService = FlowerService.getInstance();
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOUQUET_ID_SQL);
            preparedStatement.setInt(1, bouquetId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int flowerId = resultSet.getInt("FLOWER_ID");
                int bId = resultSet.getInt("BOUQUET_ID");
                int quantity = resultSet.getInt("QUANTITY");
                BouquetEntry bouquetEntry =
                        new BouquetEntry(id, flowerService.findByID(flowerId).getName(), bId, quantity);

                entries.add(bouquetEntry);
            }
        }
        return entries;
    }

    public void deleteByBouquetId(int boqId) throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOUQUET_ENTRY_ByBoqId_SQL);

            preparedStatement.setInt(1, boqId);
            preparedStatement.executeUpdate();
        }
    }
}

