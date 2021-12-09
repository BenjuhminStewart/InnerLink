package edu.uw.tcss450.innerlink.ui.Chat;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.io.RequestQueueSingleton;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;
import edu.uw.tcss450.innerlink.ui.Contacts.ContactsModel;

/**
 * View model for chat management endpoints that include getting the users in a chat room,
 * adding a user to a chat room, leaving a chat room and editing a chat rooms name.
 */
public class ChatManageViewModel extends AndroidViewModel {
    private MutableLiveData<List<String>> mUserEmails;

    public ChatManageViewModel(@NonNull Application application) {
        super(application);
        mUserEmails = new MutableLiveData<>();
        mUserEmails.setValue(new ArrayList<>());
    }

    public void addUsersListObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super List<String>> observer) {
        mUserEmails.observe(owner, observer);
    }

    public void getUsers(final int chatId, final String jwt) {
        String url = getApplication().getResources().getString(R.string.base_url) +
                "chats/" + chatId;
        JSONObject body = new JSONObject();
        try {
            body.put("chatId", chatId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //push token found in the JSONObject body
                this::generateUsers, // we get a response but do nothing with it
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

    private void generateUsers(final JSONObject response) {
        if (!response.has("rows")) {
            throw new IllegalStateException("Unexpected response in ChatManageViewModel: " + response);
        }
        try {
            JSONArray contactsArray = response.getJSONArray("rows");
            ArrayList<String> listOfUsers = new ArrayList<>();
            for(int i = 0; i < contactsArray.length(); i++) {
                JSONObject contact = contactsArray.getJSONObject(i);
                String email = contact.getString("email");

                if (!listOfUsers.contains(email)) {
                    // don't add a duplicate
                    listOfUsers.add(0, email);
                } else {
                    // this shouldn't happen but could with the asynchronous
                    // nature of the application
                    Log.wtf("Contact already added",
                            "Or duplicate contact added:" + email);
                }
            }
            mUserEmails.setValue(listOfUsers);
        } catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success ContactsViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
        mUserEmails.setValue(mUserEmails.getValue());
    }

    public void addUser(final int chatId, final String email, final String jwt) {
        String url = getApplication().getResources().getString(R.string.base_url) + "chats/" +
                chatId + "/" + email;

        Request request = new JsonObjectRequest(
                Request.Method.PATCH,
                url,
                null, //push token found in the JSONObject body
                null, // we get a response but do nothing with it
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

    public void leaveChat(final int chatId, final String jwt) {
        String url = getApplication().getResources().getString(R.string.base_url) + "chats/" +
                chatId;

        Request request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null, //push token found in the JSONObject body
                null, // we get a response but do nothing with it
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

    public void changeChatName(final int chatId, final String chatName, final String jwt) {
        String url = getApplication().getResources().getString(R.string.base_url) + "chats/chatName";

        JSONObject body = new JSONObject();
        try {
            body.put("chatId", chatId);
            body.put("name", chatName);
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
