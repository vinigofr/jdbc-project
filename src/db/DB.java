package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

    String driverClassName = "com.mysql.cj.jdbc.Driver";

//    private static DB connectionFactory = null;

    private DB() {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Properties props = loadProperties();

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(
                    props.getProperty("dburl"),
                    props.getProperty("username"),
                    props.getProperty("password")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

//    public static DB getInstance() {
//        if (connectionFactory == null) {
//            connectionFactory = new DB();
//        }
//        return connectionFactory;
//    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        }
        catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }
}
