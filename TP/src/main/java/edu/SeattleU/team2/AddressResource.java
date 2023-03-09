// Baran Onalan, James Robinson, Swetha Gupta, Troy Ying-Chu Chen
// CPSC 5200 - Team 2
// 2/13/2023

package edu.SeattleU.team2;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

@Path("/address")
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
                    "INSERT INTO Address (country, recipient, streetAddress, postalCode, city_town_locality, state, full_address, street_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getRecipient());
            statement.setString(3, address.getStreetAddress());
            statement.setString(4, address.getPostalCode());
            statement.setString(5, address.getCity_town_locality());
            statement.setString(6, address.getState());
            statement.setString(7, address.getFull_address());
            statement.setString(8, address.getStreet_number());
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
                String full_address = rs.getString("full_address");
                String street_number = rs.getString("street_number");
                address = new Address(id, country, recipient, streetAddress, postalCode, city_town_locality, state, full_address, street_number);
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
    public Response searchAddress(@QueryParam("country") String country,
                                  @QueryParam("recipient") String recipient) {
        ArrayList<Address> result = new ArrayList<Address>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT ID, country, recipient, streetAddress, postalCode, city_town_locality, state, full_address, street_number FROM Address WHERE country LIKE ? AND recipient LIKE ?"); // add where and like clause for the rest of parameters
            pstmt.setString(1, "%" + (country == null ? "" : country) + "%");
            pstmt.setString(2, "%" + (recipient == null ? "" : recipient) + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String retrievedCountry = rs.getString("country");
                String retrievedRecipient = rs.getString("recipient");
                String retrievedStreetAddress = rs.getString("streetAddress");
                String retrievedPostalCode = rs.getString("postalCode");
                String retrievedCityTownLocality = rs.getString("city_town_locality");
                String retrievedState = rs.getString("state");
                String retrievedFullAddress = rs.getString("full_address");
                String retrievedStreetNumber = rs.getString("street_number");
                result.add(new Address(id, retrievedCountry, retrievedRecipient, retrievedStreetAddress, retrievedPostalCode, retrievedCityTownLocality, retrievedState, retrievedFullAddress, retrievedStreetNumber));
            }
            return Response.ok(result).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

}
