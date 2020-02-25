package com.example.proba_db;

/**
 * Created by HP on 07.09.2018.
 */

public class Contact {

    int _id;
    String _name;
    String _phone_number;
    String _phone_number_my;
    //
    String _surname;
    String _second_name;
    int _minute;
    int _hour;
    int _day;
    int _month;
    int _year;
    int _minute_p;
    int _hour_p;
    int _day_p;
    int _month_p;
    int _year_p;
    String _location;
    String _route;
    //
    int _day_n;
    int _month_n;
    int _year_n;
    String _address;
    String _message;
    int _sos;
    String _password;
    int _anonymity;
    int _help;
    //
    double _latitude;
    double _longitude;



    public Contact(){
    }

    public Contact(int id, String name, String phone_number, String phone_number_my,
            String surname, String second_name, int minute,
            int hour, int day, int month, int year, int minute_p,
                   int hour_p, int day_p, int month_p, int year_p,
                   String location, String route, int day_n,
                   int month_n, int year_n, String address,
                   String message, int sos, String password,
                   int anonymity, int help, double latitude,
                           double longitude){
        this._id = id;
        this._name = name;
        this._phone_number = phone_number;
        this._phone_number_my = phone_number_my;
        this._surname = surname;
        this._second_name = second_name;
        this._minute = minute;
        this._hour = hour;
        this._day = day;
        this._month = month;
        this._year = year;
        this._minute_p = minute_p;
        this._hour_p = hour_p;
        this._day_p = day_p;
        this._month_p = month_p;
        this._year_p = year_p;
        this._location = location;
        this._route = route;
        this._day_n = day_n;
        this._month_n = month_n;
        this._year_n = year_n;
        this._address = address;
        this._message = message;
        this._sos = sos;
        this._password = password;
        this._anonymity = anonymity;
        this._help = help;
        this._latitude = latitude;
        this._longitude = longitude;
    }

    public Contact(String name, String phone_number, String phone_number_my,
                   String surname, String second_name, int minute,
                   int hour, int day, int month, int year, int minute_p,
                   int hour_p, int day_p, int month_p, int year_p,
                   String location, String route, int day_n,
                   int month_n, int year_n, String address,
                   String message, int sos, String password,
                   int anonymity, int help, double latitude,
                   double longitude){
        this._name = name;
        this._phone_number = phone_number;
        this._phone_number_my = phone_number_my;
        this._surname = surname;
        this._second_name = second_name;
        this._minute = minute;
        this._hour = hour;
        this._day = day;
        this._month = month;
        this._year = year;
        this._minute_p = minute_p;
        this._hour_p = hour_p;
        this._day_p = day_p;
        this._month_p = month_p;
        this._year_p = year_p;
        this._location = location;
        this._route = route;
        this._day_n = day_n;
        this._month_n = month_n;
        this._year_n = year_n;
        this._address = address;
        this._message = message;
        this._sos = sos;
        this._password = password;
        this._anonymity = anonymity;
        this._help = help;
        this._latitude = latitude;
        this._longitude = longitude;
    }

    public int getID(){
        return this._id;
    }
    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this._name;
    }
    public void setName(String name){
        this._name = name;
    }

    public String getPhoneNumber(){
        return this._phone_number;
    }
    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }

    public String getPhoneNumberMy(){
        return this._phone_number_my;
    }
    public void setPhoneNumberMy(String phone_number_my){
        this._phone_number_my = phone_number_my;
    }

    public String getSurname(){
        return this._surname;
    }
    public void setSurname(String surname){
        this._surname = surname;
    }

    public String getSecondName(){
        return this._second_name;
    }
    public void setSecondName(String second_name){
        this._second_name = second_name;
    }

    public int getMinute(){
        return this._minute;
    }
    public void setMinute(int minute){
        this._minute = minute;
    }

    public int getHour(){
        return this._hour;
    }
    public void setHour(int hour){
        this._hour = hour;
    }

    public int getDay(){
        return this._day;
    }
    public void setDay(int day){
        this._day = day;
    }

    public int getMonth(){
        return this._month;
    }
    public void setMonth(int month){
        this._month = month;
    }

    public int getYear(){
        return this._year;
    }
    public void setYear(int year){
        this._year = year;
    }
    //
    public int getMinute_p(){
        return this._minute_p;
    }
    public void setMinute_p(int minute_p){
        this._minute_p = minute_p;
    }

    public int getHour_p(){
        return this._hour_p;
    }
    public void setHour_p(int hour_p){
        this._hour_p = hour_p;
    }

    public int getDay_p(){
        return this._day_p;
    }
    public void setDay_p(int day_p){
        this._day_p = day_p;
    }

    public int getMonth_p(){
        return this._month_p;
    }
    public void setMonth_p(int month_p){
        this._month_p = month_p;
    }

    public int getYear_p(){
        return this._year_p;
    }
    public void setYear_p(int year_p){
        this._year_p = year_p;
    }

    public String getLocation(){
        return this._location;
    }
    public void setLocation(String location){
        this._location = location;
    }

    public String getRoute(){
        return this._route;
    }
    public void setRoute(String route){
        this._route = route;
    }
    //
    public int getDay_n(){
        return this._day_n;
    }
    public void setDay_n(int day_n){
        this._day_n = day_n;
    }

    public int getMonth_n(){
        return this._month_n;
    }
    public void setMonth_n(int month_n){
        this._month_n = month_n;
    }

    public int getYear_n(){
        return this._year_n;
    }
    public void setYear_n(int year_n){
        this._year_n = year_n;
    }

    public String getAddress(){
        return this._address;
    }
    public void setAddress(String address){
        this._address = address;
    }

    public String getMessage(){
        return this._message;
    }
    public void setMessage(String message){
        this._message = message;
    }

    public int getSos(){
        return this._sos;
    }
    public void setSos(int sos){
        this._sos = sos;
    }

    public String getPassword(){
        return this._password;
    }
    public void setPassword(String password){this._password = password;}

    public int getAnonymity(){
        return this._anonymity;
    }
    public void setAnonymity(int anonymity){
        this._anonymity = anonymity;
    }

    public int getHelp(){
        return this._help;
    }
    public void setHelp(int help){
        this._help = help;
    }

    public double getLatitude(){
        return this._latitude;
    }
    public void setLatitude(double latitude){
        this._latitude = latitude;
    }

    public double getLongitude(){
        return this._longitude;
    }
    public void setLongitude(double longitude){ this._longitude = longitude;}
}
