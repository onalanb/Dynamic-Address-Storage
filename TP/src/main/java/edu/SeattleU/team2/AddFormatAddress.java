package edu.SeattleU.team2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class AddFormatAddress {

    public static void main(String[] args) {
        String filePath = "/path/to/address/file.csv";
        AddFormatAddress.insertAddresses(filePath);
    }

    public static void insertAddresses(String filePath) {
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
            String alterTableSql = "ALTER TABLE addresses MODIFY COLUMN id INT AUTO_INCREMENT";
            stmt.executeUpdate(alterTableSql);

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

            // Read the addresses from the CSV file and insert them into the database
            br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String recipient = parts[0].trim();
                String street_number = parts[1].trim();
                String street_address = parts[2].trim();
                String city_town_locality = parts[3].trim();
                String state_province = parts[4].trim();
                String postal_code = parts[5].trim();
                String country = parts[6].trim();

                Address address = new Address(recipient, street_number, street_address, city_town_locality, state_province, postal_code, country);
                String insertSQL = "INSERT INTO `addresses` (`recipient`, `street_number`, `street_address`, `city_town_locality`, `state_province`, `postal_code`, `country`, `address_format`, `full_address`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(insertSQL);
                statement.setString(1, recipient);
                statement.setString(2, street_number);
                statement.setString(3, street_address);
                statement.setString(4, city_town_locality);
                statement.setString(5, state_province);
                statement.setString(6, postal_code);
                statement.setString(7, country);
                AddFormatAddress.formatAddress(address, statement);
                statement.executeUpdate();
            }
            br.close();
            System.out.println("Addresses inserted into database successfully!");

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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
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

    public static void formatAddress(Address address, PreparedStatement statement) throws SQLException {
        String recipient = address.getRecipient();
        String street_number = address.getStreet_number();
        String street_address = address.getStreetAddress();
        String city_town_locality = address.getCity_town_locality();
        String state_province = address.getState();
        String postal_code = address.getPostalCode();
        String country = address.getCountry();

        String address_format;
        String full_address;

        switch (address.getCountry()) {
            case "United States":
            case "Canada":
            case "Australia":
            case "China":
                address_format = "%s %s %s %s %s %s %s";
                full_address = String.format(address_format, address.getRecipient(), address.getStreet_number(), address.getStreetAddress(), address.getCity_town_locality(), address.getState(), address.getPostalCode(), address.getCountry());
                break;
            case "France":
                address_format = "%s %s %s %s %s %s";
                full_address = String.format(address_format, address.getRecipient(), address.getStreetAddress(), address.getStreet_number(), address.getPostalCode(), address.getCity_town_locality(), address.getCountry());
                break;
            case "Spain":
            case "Mexico":
            case "Germany":
                address_format = "%s %s %s %s %s %s %s";
                full_address = String.format(address_format, address.getRecipient(), address.getStreetAddress(), address.getStreet_number(), address.getPostalCode(), address.getCity_town_locality(), address.getState(), address.getCountry());
                break;
            case "United Kingdom":
                address_format = "%s %s %s %s %s %s";
                full_address = String.format(address_format, address.getRecipient(), address.getStreet_number(), address.getStreetAddress(), address.getCity_town_locality(), address.getCountry(), address.getPostalCode());
                break;
            default:
                address_format = "%s, %s, %s, %s, %s, %s, %s";
                full_address = String.format(address_format, address.getRecipient(), address.getStreet_number(), address.getStreetAddress(), address.getCity_town_locality(), address.getState(), address.getPostalCode(), address.getCountry());
                break;
        }

        statement.setString(8, address_format);
        statement.setString(9, full_address);
    }
}

