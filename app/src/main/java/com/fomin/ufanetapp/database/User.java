package com.fomin.ufanetapp.database;

public class User {
    private String name;
    private String lastname;
    private String surname;
    private String phone;
    private String address;
    private String tariff;
    private String cabel;

    public User() {
    }
    public User(String name, String lastname, String surname, String phone, String address, String tariff, String cabel) {
        this.name = name;
        this.lastname = lastname;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
        this.tariff = tariff;
        this.cabel = cabel;
    }
    public String getName() {
        return name;
    }
    public String getLastname() {
        return lastname;
    }
    public String getSurname() {
        return surname;
    }
    public String getPhone() {
        return phone;
    }
    public String getAddress() {return address;}
    public String getTariff() {return tariff;}
    public String getCabel() {return cabel;}
}