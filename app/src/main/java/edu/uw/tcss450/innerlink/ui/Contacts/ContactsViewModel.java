package edu.uw.tcss450.innerlink.ui.Contacts;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.ui.Chat.ChatMessage;

/**
 * A {@link ViewModel} subclass.
 */
public class ContactsViewModel extends AndroidViewModel {

    private MutableLiveData<List<String>> mContactsList;
    private MutableLiveData<List<String>> mRequestTo;
    private MutableLiveData<List<String>> mRequestFrom;
    private String mEmail;

    public ContactsViewModel(@NonNull Application application, String email) {
        super(application);
        mContactsList = new MutableLiveData<List<String>>();
        mRequestTo = new MutableLiveData<List<String>>();
        mRequestFrom = new MutableLiveData<List<String>>();
        mEmail = email;
    }

    public void sendRequest(String receiver) {
        String url = getApplication().getResources().getString(R.string.base_url)
                + "contacts";

        JSONObject body = new JSONObject();
        try {
            body.put("sender", mEmail);
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

    public MutableLiveData<List<String>> getContactsList() {
        return mContactsList;
    }

//    public List<ChatMessage> getMessageListByChatId(final String username) {
//        return getOrCreateMapEntry(username).getValue();
//    }

//    private MutableLiveData<List<ChatMessage>> getOrCreateMapEntry(final String username) {
//        if(!mContactsList.containsKey(chatId)) {
//            mMessages.put(chatId, new MutableLiveData<>(new ArrayList<>()));
//            mChatRoomList.getValue().add(chatId);
//        }
//        return mMessages.get(chatId);
//    }

    public void getContacts() {
        String url = getApplication().getResources().getString(R.string.base_url)
                + "contacts";

        JSONObject body = new JSONObject();
        try {
            body.put("sender", mEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                body, //push token found in the JSONObject body
                null, // we get a response but do nothing with it
                this::handleError) {
        };
    }

//    private void handleSuccess(final JSONObject response) {
//        MutableLiveData<List<String>> list;
//        if (!response.has("username")) {
//            throw new IllegalStateException("Unexpected response in ContactsViewModel: " + response);
//        }
//        try {
//            list = getContacts(response.getString("username"));
//            JSONArray messages = response.getJSONArray("rows");
//            for(int i = 0; i < messages.length(); i++) {
//                JSONObject message = messages.getJSONObject(i);
//                ChatMessage cMessage = new ChatMessage(
//                        message.getInt("messageid"),
//                        message.getString("message"),
//                        message.getString("email"),
//                        message.getString("timestamp")
//                );
//                if (!list.contains(cMessage)) {
//                    // don't add a duplicate
//                    list.add(0, cMessage);
//                } else {
//                    // this shouldn't happen but could with the asynchronous
//                    // nature of the application
//                    Log.wtf("Chat message already received",
//                            "Or duplicate id:" + cMessage.getMessageId());
//                }
//
//            }
//            //inform observers of the change (setValue)
//            getOrCreateMapEntry(response.getInt("chatId")).setValue(list);
//        }catch (JSONException e) {
//            Log.e("JSON PARSE ERROR", "Found in handle Success ChatRoomViewModel");
//            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
//        }
//    }

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