package edu.SeattleU.team2;

import java.sql.*;

public class AddressSQLQuery {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database_name_here", "your_username_here", "your_password_here");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM addresses");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
