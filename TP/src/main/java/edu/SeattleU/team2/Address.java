// Baran Onalan, James Robinson, Swetha Gupta, Troy Ying-Chu Chen
// CPSC 5200 - Team 2
// 2/13/2023

package edu.SeattleU.team2;

public class Address {

    private int ID;
    private String country;
    private String recipient;
    private String streetAddress;
    private String postalCode;
    private String city_town_locality;
    private String state;

    // Default constructor is required for JSON serialization and deserialization.
    public Address () { }

    public Address (String country, String recipient, String streetAddress, String postalCode, String city_town_locality, String state) {
        this.country = country;
        this.recipient = recipient;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.city_town_locality = city_town_locality;
        this.state = state;
    }


    Address (int ID, String country, String recipient, String streetAddress, String postalCode, String city_town_locality, String state) {
        this.ID = ID;
        this.country = country;
        this.recipient = recipient;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.city_town_locality = city_town_locality;
        this.state = state;
    }

    // GETTERS

    public int getID() {
        return ID;
    }

    public String getCountry() {
        return country;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity_town_locality() {
        return city_town_locality;
    }

    public String getState() {
        return state;
    }

    // SETTERS

    void setID(int ID) {
        this.ID = ID;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCity_town_locality(String city_town_locality) {
        this.city_town_locality = city_town_locality;
    }

    public void setState(String state) {
        this.state = state;
    }
}