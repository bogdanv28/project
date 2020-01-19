package wantsome.database.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnection {

    String url = "jdbc:sqlite:flowerProject.db";

    Connection createConnection() throws SQLException;
}
