package edu.uw.tcss450.innerlink.ui.Location;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.io.RequestQueueSingleton;

/**
 * Retains a list of locations that the user would like forecasts for.
 */
public class LocationListViewModel extends AndroidViewModel {
    private MutableLiveData<List<Location>> mLocationList;

    public LocationListViewModel (@NonNull Application application) {
        super(application);
        mLocationList = new MutableLiveData<>();
        mLocationList.setValue(new ArrayList<>());
    }

    public void addLocationListObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super List<Location>> observer) {
        mLocationList.observe(owner, observer);
    }

    /**
     * Makes a request to the web service to get the list of Locations associated
     * with this user.
     */
    public void connectGet(final String jwt) {
        String url = getApplication().getResources().getString(R.string.base_url) +
                "location/";

        Request request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                this::handleSuccess,
                this::handleError) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", jwt);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }

    /**
     * Adds a Location to the list
     *
     * @param response
     */
    private void handleSuccess(final JSONArray response) {
        List<Location> locationList = new ArrayList<>();
        System.out.println(response.toString());
        try {
            for(int i = 0; i < response.length(); i++) {
                JSONObject location = response.getJSONObject(i);
                String zip = "zip";
                if (location.has("zip") && !location.isNull("zip")) {
                    locationList.add(
                            new Location(
                                    location.getString("nickname"),
                                    location.getInt("zip"),
                                    location.getInt("primarykey")
                            )
                    );
                } else {
                    locationList.add(
                            new Location(
                                    location.getString("nickname"),
                                    Float.parseFloat(location.getString("lat")),
                                    Float.parseFloat(location.getString("long")),
                                    location.getInt("primarykey")
                            )
                    );
                }


            }
            System.out.println(locationList.toString());

            mLocationList.setValue(locationList);

        } catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success LocationListViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }

    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            Log.e("NETWORK ERROR", error.getMessage());
        }
        else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset());
            Log.e("CLIENT ERROR",
                    error.networkResponse.statusCode +
                            " " +
                            data);
        }
    }

    public List<Location> getLocationLIst() {
        return mLocationList.getValue();
    }
}
