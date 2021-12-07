package edu.uw.tcss450.innerlink.ui.Location;

import java.io.Serializable;

/**
 * Encapsulates location details.
 */
public class Location implements Serializable {
    private int mKey;
    private String mCity;
    private int mZipcode;
    private float mLat;
    private float mLon;

    public Location(String city, float lat, float lon, int key) {
        mCity = city;
        mLat = lat;
        mLon = lon;
        mKey = key;
    }

    public Location(String city, int zipcode, int key) {
        mCity = city;
        mZipcode = zipcode;
        mKey = key;
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

    public int getKey() { return mKey; }
}
