package com.example.proba_db;

/**
 * Created by HP on 11.12.2018.
 */

public class Search {
    private String name;
    private String surname;

    Search(String name, String surname){
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public  String toString(){
        return name + " " + surname;
    }

}
