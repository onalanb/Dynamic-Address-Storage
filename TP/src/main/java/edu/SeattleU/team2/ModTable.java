// Baran Onalan, James Robinson, Swetha Gupta, Troy Ying-Chu Chen
// CPSC 5200 - Team 2
// 2/13/2023
package edu.SeattleU.team2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class ModTable {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/TeamProject?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String userName = "root";
        String password = "password";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("Connected to database!");
            stmt = conn.createStatement();

            // Drop the addresses table if it exists
            stmt.executeUpdate("DROP TABLE IF EXISTS `addresses`");

            // Create the addresses table with full_address column
            String createTableSQL = "CREATE TABLE `addresses` (" +
                    "`id` int NOT NULL AUTO_INCREMENT," +
                    "`country` varchar(255)," +
                    "`state_province` varchar(255)," +
                    "`recipient` varchar(255)," +
                    "`street_number` varchar(255)," +
                    "`street_address` varchar(255)," +
                    "`postal_code` varchar(255)," +
                    "`city_town_locality` varchar(255)," +
                    "`full_address` varchar(255)," +
                    "PRIMARY KEY (`id`)" +
                    ")";
            stmt.executeUpdate(createTableSQL);


            // Execute the SQL script file
            String sqlScriptFilePath = "/Users/troy8chen/Desktop/2023 Winter/CPSC5200/Dynamic-Address-Storage/TP/src/main/java/edu/SeattleU/team2/addressesWITH_DATA.sql";
            BufferedReader br = new BufferedReader(new FileReader(sqlScriptFilePath));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                System.out.println(line);
            }
            br.close();
            String[] sqlStatements = sb.toString().split(";");
            for (String sqlStatement : sqlStatements) {
                System.out.println(sqlStatement);
                if (sqlStatement.trim().toLowerCase().startsWith("select")) {
                    rs = stmt.executeQuery(sqlStatement);
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    while (rs.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            String columnName = metaData.getColumnName(i);
                            Object value = rs.getObject(i);
                            System.out.println(columnName + ": " + value);
                        }
                        System.out.println();
                    }
                } else {
                    stmt.executeUpdate(sqlStatement);
                }
            }
            System.out.println("SQL script file executed successfully!");
            // Print all rows in the addresses table
            rs = stmt.executeQuery("SELECT `id`, `country`, `state_province`, `recipient`, `street_number`, `street_address`, `postal_code`, `city_town_locality` FROM `addresses`");
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rs.getObject(i);
                    System.out.println(columnName + ": " + value);
                }
                System.out.println();
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
