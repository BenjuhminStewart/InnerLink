package edu.uw.tcss450.innerlink.ui.Forecasts;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Forecast implements Serializable{
    private final String mCity;
    private final String mTemperature;
    private final String mCondition;

    public Forecast(String city, String temperature, String condition) {
        mCity = city;
        mTemperature = temperature;
        mCondition = condition;
    }

    /**
     * Static factory method to turn a properly formatted JSON String into a
     * Forecast object.
     * @param forecastAsJson the String to be parsed into a Forecast Object.
     * @return a Forecast Object with the details contained in the JSON String.
     * @throws JSONException when forecastAsJson cannot be parsed into a Forecast.
     */
    public static Forecast createFromJsonString(final String forecastAsJson) throws
            JSONException {
        final JSONObject msg = new JSONObject(forecastAsJson);
        return new Forecast(msg.getString("city"),
                msg.getString("temp"),
                msg.getString("main"));
    }

    public String getCity() {
        return mCity;
    }

    public String getTemperature() {
        return mTemperature;
    }

    public String getCondition() {
        return mCondition;
    }
}
