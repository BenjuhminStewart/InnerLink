package edu.uw.tcss450.innerlink.ui.Forecasts;

import java.io.Serializable;

/**
 * Encapsulates forecast details.
 */
public class Forecast implements Serializable{
    private final String mCity;
    private final String mTime;
    private final String mTemperature;
    private final String mCondition;

    public Forecast(String city, String time, String temperature, String condition) {
        mCity = city;
        mTime = time;
        mTemperature = temperature;
        mCondition = condition;
    }

    public String getCity() {
        return mCity;
    }

    public String getTime() {
        return mTime;
    }

    public String getTemperature() {
        return mTemperature;
    }

    public String getCondition() {
        return mCondition;
    }
}
