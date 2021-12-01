package edu.uw.tcss450.innerlink.ui.Contacts;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.io.RequestQueueSingleton;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;
import edu.uw.tcss450.innerlink.ui.Chat.ChatMessage;

/**
 * Retains a list of Contacts and has methods
 * to send contact requests and generate contacts.
 */
public class ContactsViewModel extends AndroidViewModel {
    private UserInfoViewModel viewModel;
    private MutableLiveData<List<ContactsModel>> mContactsList;
    private MutableLiveData<List<String>> mRequestFrom;

    private MutableLiveData<List<String>> mRequestTo;

    public ContactsViewModel(@NonNull Application application) {
        super(application);
        mContactsList = new MutableLiveData<>();
        mRequestFrom = new MutableLiveData<>();
        mRequestFrom.setValue(new ArrayList<>());
        mRequestTo = new MutableLiveData<List<String>>();
    }

    public void addContactsListObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super List<ContactsModel>> observer) {
        mContactsList.observe(owner, observer);
    }

    public void addRequestsListObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super List<String>> observer) {
        mRequestFrom.observe(owner, observer);
    }

    public void sendRequest(final String email) {
        String url = getApplication().getResources().getString(R.string.base_url)
                + "contacts";
        JSONObject body = new JSONObject();
        try {
            body.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.POST,
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

    public void deleteContact(final String email) {
        String url = getApplication().getResources().getString(R.string.base_url)
                + "contacts" + "/" + email;
        Request request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                null,
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

    public void getContacts() {
        String url = getApplication().getResources().getString(R.string.base_url)
                + "contacts";

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, // push token found in the JSONObject body
                this::generateContacts, // we get a response but do nothing with it
                this::handleError) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
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

    private void generateContacts(final JSONObject response) {
        if (!response.has("contacts")) {
            throw new IllegalStateException("Unexpected response in ContactsViewModel: " + response);
        }
        try {
            JSONArray contactsArray = response.getJSONArray("contacts");
            ArrayList<ContactsModel> listOfContacts = new ArrayList<>();
            for(int i = 0; i < contactsArray.length(); i++) {
                JSONObject contact = contactsArray.getJSONObject(i);
                ContactsModel contacts = new ContactsModel(
                        contact.getString("username"),
                        contact.getString("email"),
                        contact.getString("firstname"),
                        contact.getString("lastname")
                );
                if (!listOfContacts.contains(contacts)) {
                    // don't add a duplicate
                    listOfContacts.add(0, contacts);
                } else {
                    // this shouldn't happen but could with the asynchronous
                    // nature of the application
                    Log.wtf("Contact already added",
                            "Or duplicate contact added:" + contacts.getmEmail());
                }
            }
            mContactsList.setValue(listOfContacts);
        } catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success ContactsViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
        mContactsList.setValue(mContactsList.getValue());
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

    public void getRequests() {
        String url = getApplication().getResources().getString(R.string.base_url)
                + "contacts/requestsFrom";

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, // push token found in the JSONObject body
                this::generateRequests, // we get a response but do nothing with it
                this::handleError) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
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

    private void generateRequests(final JSONObject response) {
        if (!response.has("requestsFrom")) {
            throw new IllegalStateException("Unexpected response in ContactsViewModel: " + response);
        }
        try {
            JSONArray contactsArray = response.getJSONArray("requestsFrom");
            ArrayList<String> listOfRequests = new ArrayList<>();
            for(int i = 0; i < contactsArray.length(); i++) {
                JSONObject contact = contactsArray.getJSONObject(i);
                String email = contact.getString("email");
                if (!listOfRequests.contains(email)) {
                    // don't add a duplicate
                    listOfRequests.add(0, email);
                } else {
                    // this shouldn't happen but could with the asynchronous
                    // nature of the application
                    Log.wtf("Contact already added",
                            "Or duplicate contact added:" + email);
                }
            }
            mRequestFrom.setValue(listOfRequests);
        } catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success ContactsViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
        mRequestFrom.setValue(mRequestFrom.getValue());
    }

    public void acceptContact(final String email) {
        String url = getApplication().getResources().getString(R.string.base_url)
                + "contacts";
        JSONObject body = new JSONObject();
        try {
            body.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.PATCH,
                url,
                body,
                null,
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

    public void setUserInfoViewModel(UserInfoViewModel mViewModel) {
        viewModel = mViewModel;
    }
}