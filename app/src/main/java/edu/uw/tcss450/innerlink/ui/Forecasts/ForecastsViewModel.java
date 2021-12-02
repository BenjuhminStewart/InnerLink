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
public class ForecastsViewModel extends AndroidViewModel {
    private MutableLiveData<Forecast> mForecast;

    public ForecastsViewModel(@NonNull Application application) {
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
                this::handleSuccess,
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

    public void getHourlyConditions(int zipcode) {

        String url = "https://tcss450-innerlink.herokuapp.com/forecast/24hour/" + zipcode;
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleSuccess,
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
    public void getDailyConditions(int zipcode) {

        String url = "https://tcss450-innerlink.herokuapp.com/forecast/5day/" + zipcode;
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleSuccess,
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

    private void handleSuccess(final JSONObject response){
        if (!response.has("city")) {
            throw new IllegalStateException("Unexpected response in ForecastsViewModel: " + response);
        }

        try {
            JSONObject responseMain = response.getJSONObject("main");
            JSONObject condition = response.getJSONArray("conditions").getJSONObject(0);

            mForecast.setValue(
                    new Forecast(
                            response.getString("city"),
                            responseMain.getString("temp"),
                            condition.getString("main")

            ));
        } catch (JSONException e){
            Log.e("JSON PARSE ERROR", "Found in handle Success ForecastsViewModel");
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

//    public JSONObject getmResponse(){
//        return mForecast;
//    }

}