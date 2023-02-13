// Baran Onalan, James Robinson, Swetha Gupta, Troy Ying-Chu Chen
// CPSC 5200 - Team 2
// 2/13/2023
// Professor Daugherty

package edu.SeattleU.team2;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.HashMap;

@Path("/address")
public class AddressResource {

    // TO-DO
    // Put this in a database.
    private static int ID = 1;
    private static HashMap<Integer, Address> addressTable = new HashMap<Integer, Address>();

    // CREATE
    // curl -X POST http://localhost:8080/TP-1.0-SNAPSHOT/api/address -H "Content-Type: application/json" -d "{\"country\":\"USA\",\"recipient\":\"Michelle Malone\",\"streetAddress\":\"123 Troll Ave NE\",\"postalCode\":\"98008\",\"city_town_locality\":\"Seattle\",\"state\":\"WA\"}"
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewAddress(Address address) {
        address.setID(ID);
        addressTable.put(ID, address);
        // System.out.println(address.getRecipient());
        ID++;
        return Response.ok(address).build();
    }

    // READ
    // curl  -X GET http://localhost:8080/TP-1.0-SNAPSHOT/api/address/1 -H "Accept: application/json"
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{intID}")
    public Response getAddress(@PathParam("intID") Integer id) {
        Address address = addressTable.get(id);
        // System.out.println(address.getRecipient());
        return Response.ok(address).build();
    }

    // UPDATE
    // curl -X PUT http://localhost:8080/TP-1.0-SNAPSHOT/api/address/1 -H "Content-Type: application/json" -d "{\"country\":\"USA\",\"recipient\":\"John Smith\",\"streetAddress\":\"123 Troll Ave NE\",\"postalCode\":\"98008\",\"city_town_locality\":\"Seattle\",\"state\":\"WA\"}"
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{intID}")
    public Response updateAddress(@PathParam("intID") Integer id, Address address) {
        Address oldAddress = addressTable.get(id);
        if (oldAddress == null) {
            return Response.status(404).build();
        }
        address.setID(id);
        addressTable.put(id, address);
        // System.out.println(address.getRecipient());
        return Response.ok(address).build();
    }

    // DELETE
    // curl -X DELETE http://localhost:8080/TP-1.0-SNAPSHOT/api/address/1
    @DELETE
    @Path("/{intID}")
    public Response deleteAddress(@PathParam("intID") Integer id) {
        Address address = addressTable.get(id);
        if (address == null) {
            return Response.status(404).build();
        }
        addressTable.remove(id);
        // System.out.println(address.getRecipient());
        return Response.ok().build();
    }

    // SEARCH
    // curl -X GET "http://localhost:8080/TP-1.0-SNAPSHOT/api/address?country=USA&recipient=Michelle%20Malone" -H "Accept: application/json"
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    // TO-DO
    // Add more query parameters for more search filters.
    public Response searchAddress(@QueryParam("country") String country,
                                  @QueryParam("recipient") String recipient) {
        ArrayList<Address> result = new ArrayList<Address>();
        for (Address address : addressTable.values()) {
            boolean countryMatch = country == null || country.isEmpty() || country.equals(address.getCountry());
            boolean recipientMatch = recipient == null || recipient.isEmpty() || recipient.equals(address.getRecipient());
            if (countryMatch && recipientMatch) {
                result.add(address);
            }
        }

        return Response.ok(result).build();
    }

}