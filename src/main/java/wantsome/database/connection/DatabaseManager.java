package wantsome.database.connection;

import wantsome.database.connection.impl.DataSourceConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    public static Connection getConnection() throws DatabaseConnectionException {
        try {
            return DataSourceConnection.getInstance().createConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException();
        }
    }
}
