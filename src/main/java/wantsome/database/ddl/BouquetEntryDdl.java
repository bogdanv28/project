package wantsome.database.ddl;

import wantsome.database.connection.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BouquetEntryDdl {
    private static String createTableCommand = "CREATE TABLE IF NOT EXISTS BOUQUET_ENTRY(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "FLOWER_ID INTEGER NOT NULL REFERENCES FLOWER(ID)," +
            "BOUQUET_ID INTEGER NOT NULL REFERENCES BOUQUETS(ID)," +
            "QUANTITY INTEGER NOT NULL)";

    private static String deleteTable = "DROP TABLE BOUQUET_ENTRY";

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
