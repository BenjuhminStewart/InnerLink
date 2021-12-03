package edu.uw.tcss450.innerlink.ui.Forecasts;

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
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.Objects;

import edu.uw.tcss450.innerlink.io.RequestQueueSingleton;

/**
 * Retains a list of Forecasts and their hourly, daily, and weekly conditions.
 */
public class ForecastCurrentViewModel extends AndroidViewModel {
    private MutableLiveData<Forecast> mForecast;

    public ForecastCurrentViewModel(@NonNull Application application) {
        super(application);
        mForecast = new MutableLiveData<>();
    }

    public void addForecastObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super Forecast> observer) {
        mForecast.observe(owner, observer);
    }

    public void getCurrConditions(int zipcode) {
        String url = "https://tcss450-innerlink.herokuapp.com/forecast/conditions/" + zipcode;
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleCurrentSuccess,
                this::handleError) {

        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }

    private void handleCurrentSuccess(final JSONObject response){
        if (!response.has("city")) {
            throw new IllegalStateException("Unexpected response in ForecastCurrentViewModel: " + response);
        }

        try {
            mForecast.setValue(
                    new Forecast(
                            response.getString("city"),
                            response.getString("time"),
                            response.getString("temp"),
                            response.getString("conditions")

            ));
        } catch (JSONException e){
            Log.e("JSON PARSE ERROR", "Found in handle Success ForecastCurrentViewModel");
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
}