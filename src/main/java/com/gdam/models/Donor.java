package com.gdam.models;

public class Donor {
    private String firstName;
    private String lastName;
    private String email;
    private double amountDonated;

    // Constructor
    public Donor(String firstName, String lastName, String email, double amountDonated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.amountDonated = amountDonated;
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getAmountDonated() {
        return amountDonated;
    }

    public void setAmountDonated(double amountDonated) {
        this.amountDonated = amountDonated;
    }

    // toString method for easy representation
    @Override
    public String toString() {
        return "Donor{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", amountDonated=" + amountDonated +
                '}';
    }
}