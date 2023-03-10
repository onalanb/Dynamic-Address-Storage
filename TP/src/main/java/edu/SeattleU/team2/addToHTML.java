// Baran Onalan, James Robinson, Swetha Gupta, Troy Ying-Chu Chen
// CPSC 5200 - Team 2
// 2/13/2023
package edu.SeattleU.team2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class addToHTML {

    public static void main(String[] args) {
        try {
            // Establish a connection to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/TeamProject?user=root&password=password&useUnicode=true&characterEncoding=UTF-8");
            // Create an SQL statement to retrieve data from the "addresses" table
            String sql = "SELECT * FROM addresses";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // Loop through the result set and create an HTML table
            StringBuilder html = new StringBuilder();
            html.append("<table>");
            html.append("<tr><th>ID</th><th>Recipient</th><th>Country</th><th>State</th><th>City/Town/Locality</th><th>Postal Code</th><th>Street/Address/House Number</th><th>Street Name</th><th>Full Address</th></tr>");
            while (rs.next()) {
                html.append("<tr>");
                html.append("<td>").append(rs.getString("id")).append("</td>");
                html.append("<td>").append(rs.getString("recipient")).append("</td>");
                html.append("<td>").append(rs.getString("country")).append("</td>");
                html.append("<td>").append(rs.getString("state_province")).append("</td>");
                html.append("<td>").append(rs.getString("city_town_locality")).append("</td>");
                html.append("<td>").append(rs.getString("postal_code")).append("</td>");
                html.append("<td>").append(rs.getString("street_address")).append("</td>");
                html.append("<td>").append(rs.getString("street_number")).append("</td>");
                html.append("<td>").append(rs.getString("full_address")).append("</td>");
                html.append("</tr>");
            }
            html.append("</table>");
            // Print the HTML table to the console
            System.out.println(html.toString());
            // Write the HTML table to a file
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("output.html"), StandardCharsets.UTF_8))) {
                writer.write(html.toString());
            }
            // Close the database connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
