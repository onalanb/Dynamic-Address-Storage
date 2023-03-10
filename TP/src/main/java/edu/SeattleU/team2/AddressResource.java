// Baran Onalan, James Robinson, Swetha Gupta, Troy Ying-Chu Chen
// CPSC 5200 - Team 2
// 2/13/2023

package edu.SeattleU.team2;

import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.IOException;


@Path("/addresses")
public class AddressResource {

    // TO-DO
    // Put this in a database.
    private static int ID = 1;
    private static HashMap<Integer, Address> addressTable = new HashMap<Integer, Address>();

    private Connection connection = null;

    // Making sure the JDBC driver is loaded.
    private AddressResource() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //connection = DriverManager.getConnection("jdbc:mysql://localhost/TeamProject?useSSL=false", "debian-sys-maint", "M9DmVTikBU90gpn9");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/TeamProject?useSSL=false", "root", "password");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // CREATE
    // curl -X POST http://localhost:8080/TP-1.0-SNAPSHOT/api/address -H "Content-Type: application/json" -d "{\"country\":\"USA\",\"recipient\":\"Michelle Malone\",\"streetAddress\":\"123 Troll Ave NE\",\"postalCode\":\"98008\",\"city_town_locality\":\"Seattle\",\"state\":\"WA\"}"
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewAddress(Address address) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Address (country, state_province, recipient, street_number, street_address, postal_code, city_town_locality, address_format, full_address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            String country = address.getCountry();
            String state = address.getState();
            String recipient = address.getRecipient();
            String street_number = address.getStreet_number();
            String street_address = address.getStreetAddress();
            String postal_code = address.getPostalCode();
            String city_town_locality = address.getCity_town_locality();

            statement.setString(1, country);
            statement.setString(2, state);
            statement.setString(3, recipient);
            statement.setString(4, street_number);
            statement.setString(5, street_address);
            statement.setString(6, postal_code);
            statement.setString(7, city_town_locality);

            if(address.getCountry() == "United States")
            {
                String address_format = "%s %s %s %s %s %s %s";
                String full_address = String.format(address_format, recipient, street_number, street_address, city_town_locality, state, postal_code, country);
                statement.setString(8, address_format);
                statement.setString(9, full_address);

            }
            else if(address.getCountry() == "Canada")
            {
                String address_format = "%s %s %s %s %s %s %s";
                String full_address = String.format(address_format, recipient, street_number, street_address, city_town_locality, state, postal_code, country);
                statement.setString(8, address_format);
                statement.setString(9, full_address);
            }
            else if(address.getCountry() == "Australia")
            {
                String address_format = "%s %s %s %s %s %s %s";
                String full_address = String.format(address_format, recipient, street_number, street_address, city_town_locality, state, postal_code, country);
                statement.setString(8, address_format);
                statement.setString(9, full_address);
            }
            else if(address.getCountry() == "China")
            {
                String address_format = "%s %s %s %s %s %s %s";
                String full_address = String.format(address_format, recipient, street_number, street_address, city_town_locality, state, postal_code, country);
                statement.setString(8, address_format);
                statement.setString(9, full_address);
            }
            else if(address.getCountry() == "France")
            {
                String address_format = "%s %s %s %s %s %s";
                String full_address = String.format(address_format, recipient, street_number, street_address, postal_code, city_town_locality, country);
                statement.setString(8, address_format);
                statement.setString(9, full_address);
            }
            else if(address.getCountry() == "Spain")
            {
                String address_format = "%s %s %s %s %s %s %s";
                String full_address = String.format(address_format, recipient, street_address, street_number, postal_code, city_town_locality, state, country);
                statement.setString(8, address_format);
                statement.setString(9, full_address);
            }
            else if(address.getCountry() == "New Zealand")
            {
                String address_format = "%s %s %s %s %s %s";
                String full_address = String.format(address_format, recipient, street_number, street_address, city_town_locality, postal_code, country);
                statement.setString(8, address_format);
                statement.setString(9, full_address);
            }
            else if(address.getCountry() == "Germany")
            {
                String address_format = "%s %s %s %s %s %s";
                String full_address = String.format(address_format, recipient, street_address, street_number, postal_code, city_town_locality, country);
                statement.setString(8, address_format);
                statement.setString(9, full_address);
            }
            else if(address.getCountry() == "United Kingdom")
            {
                String address_format = "%s %s %s %s %s %s";
                String full_address = String.format(address_format, recipient, street_number, street_address, city_town_locality, country, postal_code);
                statement.setString(8, address_format);
                statement.setString(9, full_address);
            }
            else if(address.getCountry() == "Mexico")
            {
                String address_format = "%s %s %s %s %s %s";
                String full_address = String.format(address_format, recipient, street_address, street_number, postal_code, city_town_locality, state);
                statement.setString(8, address_format);
                statement.setString(9, full_address);
            }
            else
            {
                // default format: street_address, city, state/province/region, postal_code, country
                String address_format = "%s, %s, %s, %s, %s, %s, %s";
                String full_address = String.format(address_format, recipient, street_number, street_address, city_town_locality, state, postal_code, country);
                statement.setString(8, address_format);
                statement.setString(9, full_address);

            }
            statement.setString(8, address.getAddress_format());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    address.setID(id);
                    return Response.ok(address).build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Response.serverError().build();
    }

    // READ - DONE
    // curl  -X GET http://localhost:8080/TP-1.0-SNAPSHOT/api/address/1 -H "Accept: application/json"
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{intID}")
    public Response getAddress(@PathParam("intID") Integer id) {
        Address address = null;

        try {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT ID, country, recipient, streetAddress, postalCode, city_town_locality, state, full_address, street_number FROM Address WHERE ID = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String country = rs.getString("country");
                String recipient = rs.getString("recipient");
                String streetAddress = rs.getString("streetAddress");
                String postalCode = rs.getString("postalCode");
                String city_town_locality = rs.getString("city_town_locality");
                String state = rs.getString("state");
                String street_number = rs.getString("street_number");
                address = new Address(country, recipient, streetAddress, postalCode, city_town_locality, state, street_number);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // System.out.println(address.getRecipient());
        return Response.ok(address).build();
    }

    // UPDATE
    // curl -X PUT http://localhost:8080/TP-1.0-SNAPSHOT/api/address/1 -H "Content-Type: application/json" -d "{\"country\":\"USA\",\"recipient\":\"John Smith\",\"streetAddress\":\"123 Troll Ave NE\",\"postalCode\":\"98008\",\"city_town_locality\":\"Seattle\",\"state\":\"WA\"}"
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{intID}")
    public Response updateAddress(@PathParam("intID") Integer id, Address address) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(
                    "UPDATE Address SET country = ?, recipient = ?, streetAddress = ?, postalCode = ?, city_town_locality = ?, state = ?, full_address = ?, street_number = ?, WHERE ID = ?");
            pstmt.setString(1, address.getCountry());
            pstmt.setString(2, address.getRecipient());
            pstmt.setString(3, address.getStreetAddress());
            pstmt.setString(4, address.getPostalCode());
            pstmt.setString(5, address.getCity_town_locality());
            pstmt.setString(6, address.getState());
            pstmt.setString(7, address.getFull_address());
            pstmt.setString(8, address.getStreet_number());
            pstmt.setInt(9, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                address.setID(id); // check if this fix worked
                return Response.ok(address).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Response.status(404).entity("Client entered wrong value.").type("text/plain").build();
    }

    // DELETE
    // curl -X DELETE http://localhost:8080/TP-1.0-SNAPSHOT/api/address/1
    @DELETE
    @Path("/{intID}")
    public Response deleteAddress(@PathParam("intID") Integer id) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM Address WHERE ID = ?");
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted == 0) {
                return Response.status(404).build();
            }
            return Response.ok().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

    // SEARCH
    // curl -X GET "http://localhost:8080/TP-1.0-SNAPSHOT/api/address?country=USA&recipient=Michelle%20Malone" -H "Accept: application/json"
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    // TO-DO
    // Add more query parameters for more search filters. Also edit the Prepared Statement.
    public Response searchAddress(@QueryParam("queryString") String queryString,
                                  @QueryParam("recipient") String recipient) {
        ArrayList<Address> result = new ArrayList<Address>();
        try {
            /*
            PreparedStatement pstmt = connection.prepareStatement(

                    "SELECT ID, country, recipient, streetAddress, postalCode, city_town_locality, state, full_address, street_number FROM Address WHERE country LIKE ? AND recipient LIKE ? AND streetAddress LIKE ? AND postalCode LIKE ? AND city_town_locality LIKE ? AND state LIKE ? AND full_address LIKE ? AND street_number LIKE ?"); // add where and like clause for the rest of parameters
            pstmt.setString(1, "%" + (country == null ? "" : country) + "%");
            pstmt.setString(2, "%" + (recipient == null ? "" : recipient) + "%");
             */

            PreparedStatement pstmt = connection.prepareStatement( "SELECT * FROM addresses WHERE CONCAT(country, state_province,recipient, street_number,street_address,postal_code, city_town_locality,full_address) LIKE ?");
            pstmt.setString(1, "%" + (queryString == null ? "" : queryString) + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String retrievedCountry = rs.getString("country");
                String retrievedStateProvince = rs.getString("state_province");
                String retrievedRecipient = rs.getString("recipient");
                String retrievedStreetNumber = rs.getString("street_number");
                String retrievedStreetAddress = rs.getString("street_address");
                String retrievedPostalCode = rs.getString("postal_code");
                String retrievedCityTownLocality = rs.getString("city_town_locality");
                String retrievedFullAddress = rs.getString("full_address");
                result.add(new Address(id, retrievedCountry,retrievedRecipient,retrievedStreetAddress, retrievedPostalCode,retrievedCityTownLocality,retrievedStateProvince, retrievedStreetNumber));
            }
            String ans = "<table>\n" +
                    "  <tr>\n" +
                    "    <th>Company</th>\n" +
                    "    <th>Contact</th>\n" +
                    "    <th>Country</th>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>Alfreds Futterkiste</td>\n" +
                    "    <td>Maria Anders</td>\n" +
                    "    <td>Germany</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>Centro comercial Moctezuma</td>\n" +
                    "    <td>Francisco Chang</td>\n" +
                    "    <td>Mexico</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>Ernst Handel</td>\n" +
                    "    <td>Roland Mendel</td>\n" +
                    "    <td>Austria</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>Island Trading</td>\n" +
                    "    <td>Helen Bennett</td>\n" +
                    "    <td>UK</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>Laughing Bacchus Winecellars</td>\n" +
                    "    <td>Yoshi Tannamuri</td>\n" +
                    "    <td>Canada</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>Magazzini Alimentari Riuniti</td>\n" +
                    "    <td>Giovanni Rovelli</td>\n" +
                    "    <td>Italy"+queryString+"</td>\n" +
                    "  </tr>\n" +
                    "</table>";
            return Response.ok(result)
//                    .header("Access-Control-Allow-Origin", "*")
//                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
//                    .header("Access-Control-Allow-Credentials", "true")
//                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
//                    .header("Access-Control-Max-Age", "1209600")
            .build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

}
