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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

import edu.uw.tcss450.innerlink.R;

public class ChatRoomListViewModel extends AndroidViewModel {
    private MutableLiveData<List<ChatRoom>> mChatRoomList;

    public ChatRoomListViewModel (@NonNull Application application) {
        super(application);
        mChatRoomList = new MutableLiveData<>();
        mChatRoomList.setValue(new ArrayList<>());
    }

    public void addChatRoomListObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super List<ChatRoom>> observer) {
        mChatRoomList.observe(owner, observer);
    }

    private void handleError(final VolleyError error) {
        //you should add much better error handling in a production release.
        //i.e. YOUR PROJECT
        Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());
    }

    /**
     * Adds a Chat Room to the list if it is not already present
     *
     * @param result
     */
    private void handleResult(final JSONObject result) {
        IntFunction<String> getString =
                getApplication().getResources()::getString;
        try {
            JSONObject root = result;
            if (root.has(getString.apply(R.string.keys_json_response))) {
                JSONObject response = root.getJSONObject(getString.apply(
                        R.string.keys_json_response));
                if (response.has(getString.apply(R.string.keys_json_data))) {
                    JSONArray data = response.getJSONArray(
                            getString.apply(R.string.keys_json_data));
                    for(int i = 0; i < data.length(); i++) {
                        JSONObject jsonChatRoom = data.getJSONObject(i);
                        ChatRoom chatRoom = new ChatRoom(jsonChatRoom.getInt(
                                getString.apply(R.string.keys_json_chatId)
                        ));
                        if (!mChatRoomList.getValue().contains(chatRoom)) {
                            mChatRoomList.getValue().add(chatRoom);
                        }
                    }
                } else {
                    Log.e("ERROR!", "No data array");
                }
            } else {
                Log.e("ERROR!", "No response");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
        mChatRoomList.setValue(mChatRoomList.getValue());
    }

    /**
     * Makes a request to the web service to get the list of Chat Rooms (chatIds) associated
     * with this user email.
     */
    public void connectGet() {
        // TODO: RN THERES AN ENDPOINT TO GET THE LIST OF USERS IN A CHAT - NEED ANOTHER "GET" FOR THE LIST OF CHATS
        String url = getApplication().getResources().getString(R.string.base_url) + "get";
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleResult, this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", "");
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }
}
