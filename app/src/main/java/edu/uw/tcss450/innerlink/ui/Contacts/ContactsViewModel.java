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

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.io.RequestQueueSingleton;
import edu.uw.tcss450.innerlink.ui.Chat.ChatMessage;

/**
 * A {@link ViewModel} subclass.
 */
public class ContactsViewModel extends AndroidViewModel {

    private Map<String, MutableLiveData<List<ContactsModel>>> mContactsList;

    private MutableLiveData<List<String>> mRequestTo;
    private MutableLiveData<List<String>> mRequestFrom;

    public ContactsViewModel(@NonNull Application application) {
        super(application);
        mContactsList = new HashMap<>();
        mRequestTo = new MutableLiveData<List<String>>();
        mRequestFrom = new MutableLiveData<List<String>>();
    }

//    public void addContactsListObserver(@NonNull LifecycleOwner owner,
//                                        @NonNull Observer<? super Map<String,
//                                                MutableLiveData<List<ContactsModel>>>> observer) {
//        mContactsList.observe(owner, observer);
//    }

    public void sendRequest(String receiver) {
        String url = getApplication().getResources().getString(R.string.base_url)
                + "contacts";
        JSONObject body = new JSONObject();
        try {
            body.put("receiver", receiver);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body, //push token found in the JSONObject body
                null, // we get a response but do nothing with it
                this::handleError) {
        };
    }

    public List<ContactsModel> getContactsListByEmail(final String email) {
        return getOrCreateMapEntry(email).getValue();
    }

    private MutableLiveData<List<ContactsModel>> getOrCreateMapEntry(final String email) {
        if(!mContactsList.containsKey(email)) {
            mContactsList.put(email, new MutableLiveData<>(new ArrayList<>()));
        }
        return mContactsList.get(email);
    }

    public void getContacts(String receiver, final String jwt) {
        String url = getApplication().getResources().getString(R.string.base_url)
                + "contacts";

        JSONObject body = new JSONObject();
        try {
            body.put("receiver", receiver);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                body, //push token found in the JSONObject body
                this::generateContacts, // we get a response but do nothing with it
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

        //code here will run
    }

    private void generateContacts(final JSONObject response) {
        List<ContactsModel> list;
        if (!response.has("username")) {
            throw new IllegalStateException("Unexpected response in ContactsViewModel: " + response);
        }
        try {
            list = getContactsListByEmail(response.getString("user"));
            JSONArray contactsArray = response.getJSONArray("contacts");
            for(int i = 0; i < contactsArray.length(); i++) {
                JSONObject contact = contactsArray.getJSONObject(i);
                ContactsModel contacts = new ContactsModel(
                        contact.getString("username"),
                        contact.getString("email"),
                        contact.getString("firstname"),
                        contact.getString("lastname")
                );
                if (!list.contains(contacts)) {
                    // don't add a duplicate
                    list.add(0, contacts);
                } else {
                    // this shouldn't happen but could with the asynchronous
                    // nature of the application
                    Log.wtf("Contact already added",
                            "Or duplicate contact added:" + contacts.getmEmail());
                }

            }
            //inform observers of the change (setValue)
            getOrCreateMapEntry(response.getString("user")).setValue(list);
        } catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success ContactsViewModel");
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