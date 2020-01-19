package wantsome.database.ddl;

import wantsome.database.connection.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class OrdersDdl {
    private static String createTableCommand = "CREATE TABLE IF NOT EXISTS ORDERS(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "CLIENT_FIRST_NAME TEXT NOT NULL,"+
            "CLIENT_LAST_NAME TEXT NOT NULL," +
            "CLIENT_PHONE_NUMBER TEXT NOT NULL,"+
            "CLIENT_ADDRESS TEXT NOT NULL,"+
            "PRICE INTEGER NOT NULL," +
            "DATE TEXT)";

    private static String deleteTable = "DROP TABLE ORDERS";

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
