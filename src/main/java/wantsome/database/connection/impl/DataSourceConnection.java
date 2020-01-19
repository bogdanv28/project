package wantsome.database.connection.impl;

import org.apache.commons.dbcp2.BasicDataSource;
import org.sqlite.SQLiteDataSource;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;
import wantsome.database.connection.DatabaseConnection;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static java.util.Collections.singleton;

/**
 * - improves application performance as connections are not created/closed within a class,
 * they are managed by the application server and can be fetched while at runtime
 * - it provides a facility creating a pool of connections
 * - helpful for enterprise applications
 */
public class DataSourceConnection implements DatabaseConnection {

    private static DataSourceConnection instance;

    private DataSourceConnection() {}

    public static DataSourceConnection getInstance() {
        if (instance == null) {
            instance = new DataSourceConnection();
        }
        return instance;
    }

    @Override
    public Connection createConnection() throws SQLException {
        DataSource dataSource = getApacheCommonsDBCP();
        return dataSource.getConnection();
    }

    private DataSource getApacheCommonsDBCP() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setConnectionInitSqls(singleton("PRAGMA foreign_keys=ON")); //enable FK support (disabled by default)
        return dataSource;
    }
}
