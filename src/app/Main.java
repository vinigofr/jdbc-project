package app;

import db.DB;
import entities.Queries;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        DB connectionInstance = DB.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionInstance.getConnection();
            statement = connection.prepareStatement(Queries.INSERT_INTO_SELLERS, Statement.RETURN_GENERATED_KEYS);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionInstance.closeStatement(statement);
            connectionInstance.closeConnection(connection);
        }
    }
}
