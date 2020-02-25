package com.example.proba_db;

/**
 * Created by HP on 01.10.2018.
 */

public class Route {
    public int id;
    public String location;
    public String route_name;
    public double latitude;
    public double longitude;

    public Route(int id1, String location1, String route_name1,
            double latitude1, double longitude1){
        this.id=id1;
        this.location=location1;
        this.route_name=route_name1;
        this.latitude=latitude1;
        this.longitude=longitude1;
    }

}
