package wantsome.database.connection.impl;


import wantsome.database.connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * - hampers the application performance as the connections are created/closed in java classes
 * - does not support connection pooling
 */
public class DriverConnection implements DatabaseConnection {

    private static DriverConnection instance;

    private DriverConnection() {}

    public static DriverConnection getInstance() {
        if (instance == null) {
            instance = new DriverConnection();
        }
        return instance;
    }

    @Override
    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
