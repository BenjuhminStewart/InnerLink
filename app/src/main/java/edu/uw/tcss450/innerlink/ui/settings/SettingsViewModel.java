package edu.uw.tcss450.innerlink.ui.settings;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.io.RequestQueueSingleton;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;
import edu.uw.tcss450.innerlink.ui.Contacts.ContactsViewModel;

public class SettingsViewModel extends AndroidViewModel {
    private UserInfoViewModel viewModel;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
    }

    public void changePassword(final String currentPass, final String newPass) {
        System.out.println(currentPass);
        System.out.println(newPass);
        String url = getApplication().getResources().getString(R.string.base_url);
        JSONObject body = new JSONObject();
        try {
            body.put("current", currentPass);
            body.put("new", newPass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.PATCH,
                url,
                body, //push token found in the JSONObject body
                null, // we get a response but do nothing with it
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", viewModel.getmJwt());
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
    public void setUserInfoViewModel(UserInfoViewModel mViewModel) {
        viewModel = mViewModel;
    }
}
