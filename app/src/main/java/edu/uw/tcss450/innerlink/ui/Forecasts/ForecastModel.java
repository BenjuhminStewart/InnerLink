package edu.uw.tcss450.innerlink.ui.Forecasts;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ForecastModel implements Serializable {
    private String mConditionCity;
    private String mConditionTemp;
    private String mConditionFeels;
    private String mConditionString;

    public ForecastModel(String city, String temp,
                         String feels, String string) {
        mConditionCity = city;
        mConditionTemp = temp;
        mConditionFeels = feels;
        mConditionString = string;
    }

    /**
     * Static factory method to turn a properly formatted JSON String into a
     * ContactsModel object.
     *
     * @param cmAsJson the String to be parsed into a ContactsModel Object.
     * @return a ContactsModelObject with the details contained in the JSON String.
     * @throws JSONException when cmAsString cannot be parsed into a ContactsModel.
     */
    public static ForecastModel createFromJsonString(final String cmAsJson) throws JSONException {
        final JSONObject msg = new JSONObject(cmAsJson);
        return new ForecastModel(msg.getString("city"),
                msg.getString("temp"),
                msg.getString("feelsLike"),
                msg.getString("icon"));
    }

    public String getmConditionCity() {
        return mConditionCity;
    }

    public String getmConditionTemp() {
        return mConditionTemp;
    }

    public String getmConditionFeels() {
        return mConditionFeels;
    }

    public String getmConditionString() {
        return mConditionString;
    }
}