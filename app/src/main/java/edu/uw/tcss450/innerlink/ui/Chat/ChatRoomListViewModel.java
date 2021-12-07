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

/**
 * Retains a list of Chat Rooms (chatIds) that the user is active in
 */
public class ChatRoomListViewModel extends AndroidViewModel {
    private MutableLiveData<List<ChatRoom>> mChatRoomList;

    private String mJwt;

    public ChatRoomListViewModel (@NonNull Application application) {
        super(application);
        mChatRoomList = new MutableLiveData<>();
        mChatRoomList.setValue(new ArrayList<>());
    }

    public void addChatRoomListObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super List<ChatRoom>> observer) {
        mChatRoomList.observe(owner, observer);
    }

    /**
     * Makes a request to the web service to get the list of Chat Rooms (chatIds) associated
     * with this user email.
     */
    public void connectGet(final String jwt) {
        String url = getApplication().getResources().getString(R.string.base_url) +
                "chats/";

        Request request = new JsonObjectRequest(
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
     * Adds a Chat Room to the list
     *
     * @param response
     */
    private void handleSuccess(final JSONObject response) {
        List<ChatRoom> chatRoomList = new ArrayList<>();

        if (!response.has("chats")) {
            throw new IllegalStateException("Unexpected response in ChatRoomListViewModel: " + response);
        }

        try {
            JSONArray chatRooms = response.getJSONArray("chats");

            for(int i = 0; i < chatRooms.length(); i++) {
                JSONObject chatRoom = chatRooms.getJSONObject(i);

                chatRoomList.add(
                    new ChatRoom(
                        chatRoom.getString("name"),
                        chatRoom.getInt("chatid"),
                        chatRoom.getString("user"),
                        chatRoom.getString("message"),
                        chatRoom.getString("timestamp"), chatRoom.getString("users")
                    )
                );
            }

            mChatRoomList.setValue(chatRoomList);

        } catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success ChatViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }

    public void createChatRoom(final String chatName, final String jwt) {
        mJwt = jwt;
        String url = getApplication().getResources().getString(R.string.base_url) +
                "chats";
        JSONObject body = new JSONObject();
        try {
            body.put("name", chatName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body, //no body for this get request
                this::handleSuccessChatRoom,
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

    private void handleSuccessChatRoom(final JSONObject response) {
        int chatId;
        if (!response.has("chatID")) {
            throw new IllegalStateException("Unexpected response in ChatRoomViewModel: " + response);
        }
        try {
            chatId = response.getInt("chatID");

        } catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success ChatRoomViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }
}
