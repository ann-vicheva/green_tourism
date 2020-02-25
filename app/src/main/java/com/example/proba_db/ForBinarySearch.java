package com.example.proba_db;

/**
 * Created by HP on 13.12.2018.
 */

public class ForBinarySearch {
    public Contact contact;
    public Integer numFSymbol;

    public ForBinarySearch(int id, String name, String phone_number, String phone_number_my,
                   String surname, String second_name, int minute,
                   int hour, int day, int month, int year, int minute_p,
                   int hour_p, int day_p, int month_p, int year_p,
                   String location, String route, int day_n,
                   int month_n, int year_n, String address,
                   String message, int sos, String password,
                   int anonymity, int help, double latitude,
                   double longitude){
        contact = new Contact(id, name, phone_number, phone_number_my, surname, second_name, minute,
                              hour, day, month, year, minute_p, hour_p, day_p, month_p, year_p,
                              location, route, day_n, month_n, year_n, address, message,  sos,
                              password, anonymity, help, latitude, longitude);
        String x_str = contact.getName();
        char ch1=x_str.charAt(0);
        int x = (int) ch1;
        //int x = (int) ch1;
        numFSymbol=x;
        /*
        contact._id = id;
        contact._name = name;
        contact._phone_number = phone_number;
        contact._phone_number_my = phone_number_my;
        contact._surname = surname;
        contact._second_name = second_name;
        contact._minute = minute;
        contact._hour = hour;
        contact._day = day;
        contact._month = month;
        contact._year = year;
        contact._minute_p = minute_p;
        contact._hour_p = hour_p;
        contact._day_p = day_p;
        contact._month_p = month_p;
        contact._year_p = year_p;
        contact._location = location;
        contact._route = route;
        contact._day_n = day_n;
        contact._month_n = month_n;
        contact._year_n = year_n;
        contact._address = address;
        contact._message = message;
        contact._sos = sos;
        contact._password = password;
        contact._anonymity = anonymity;
        contact._help = help;
        contact._latitude = latitude;
        contact._longitude = longitude;
        */
    }
    public Contact getContact(){return contact;}
    public Integer getNumFSymbol(){return numFSymbol;}

}
