package wantsome.database.ddl;

import wantsome.database.connection.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class FlowerDdl {
    private static String createTableCommand = "CREATE TABLE IF NOT EXISTS FLOWER(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "FLOWER_TYPE TEXT NOT NULL," +
            "COLOR TEXT NOT NULL," +
            "FLOWER_NAME TEXT NOT NULL UNIQUE," +
            "PRICE_PER_UNIT INTEGER NOT NULL," +
            "AVAILABLE_STOCK INTEGER NOT NULL)";

    private static String deleteTable = "DROP TABLE FLOWER";

    public static void createTable() throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            Statement createTable = connection.createStatement();

            createTable.execute(createTableCommand);

        }
    }

    public static void deleteTable() throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            Statement createTable = connection.createStatement();

            createTable.execute(deleteTable);
        }
    }
}
