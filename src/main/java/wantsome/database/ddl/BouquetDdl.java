package wantsome.database.ddl;

import wantsome.database.connection.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BouquetDdl {
    private static String createTableCommand = "CREATE TABLE IF NOT EXISTS BOUQUETS(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "ORDERS_ID INTEGER NOT NULL REFERENCES ORDERS(ID)," +
            "PRICE INTEGER NOT NULL)";

    private static String deleteTable = "DROP TABLE BOUQUETS";

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
