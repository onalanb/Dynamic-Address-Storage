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
    private String full_address;
    private String street_number;

    // Default constructor is required for JSON serialization and deserialization.
    public Address () { }

    public Address (String country, String recipient, String streetAddress, String postalCode, String city_town_locality, String state, String full_address, String street_number) {
        this.country = country;
        this.recipient = recipient;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.city_town_locality = city_town_locality;
        this.state = state;
        this.full_address = " ";
        this.street_number = " ";
    }


    Address (int ID, String country, String recipient, String streetAddress, String postalCode, String city_town_locality, String state, String full_address, String street_number) {
        this.ID = ID;
        this.country = country;
        this.recipient = recipient;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.city_town_locality = city_town_locality;
        this.state = state;
        this.full_address = " ";
        this.street_number = " ";
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

    public String getFull_address(){
        return full_address;
    }

    public String getStreet_number(){
        return street_number;
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

    public void setFull_address(String full_address){
        this.full_address = full_address;
    }

    public void setStreet_number(String street_number){
        this.street_number = street_number;
    }
}