package edu.uw.tcss450.innerlink.ui.Location;

import java.io.Serializable;

/**
 * Encapsulates location details.
 */
public class Location implements Serializable {
    private String mCity;
    private int mZipcode;
    private float mLat;
    private float mLon;

    public Location(String city, float lat, float lon) {
        mCity = city;
        mLat = lat;
        mLon = lon;
    }

    public Location(String city, int zipcode) {
        mCity = city;
        mZipcode = zipcode;
    }

    public String getCity() {
        return mCity;
    }

    public int getZipcode() {
        return mZipcode;
    }

    public float getLat() {
        return mLat;
    }

    public float getLon() {
        return mLon;
    }
}
