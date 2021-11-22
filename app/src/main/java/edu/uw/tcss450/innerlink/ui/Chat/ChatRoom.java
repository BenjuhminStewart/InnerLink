package edu.uw.tcss450.innerlink.ui.Chat;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Encapsulates Chat Room details.
 */
public class ChatRoom implements Serializable {
    private final String mChatRoomName;
    private final int mChatId;
    private final String mLastSender;
    private final String mLastMessage;
    private final String mTimeStamp;

    public ChatRoom(String chatRoomName, int chatId, String lastSender, String lastMessage, String timeStamp) {
        mChatRoomName = chatRoomName;
        mChatId = chatId;
        mLastSender = lastSender;
        mLastMessage = lastMessage;
        mTimeStamp = timeStamp;
    }

    /**
     * Static factory method to turn a properly formatted JSON String into a
     * ChatMessage object.
     * @param chatRoomAsJson the String to be parsed into a ChatRoom Object.
     * @return a ChatRoom Object with the details contained in the JSON String.
     * @throws JSONException when chatRoomAsJson cannot be parsed into a ChatRoom.
     */
    public static ChatRoom createFromJsonString(final String chatRoomAsJson) throws JSONException {
        final JSONObject msg = new JSONObject(chatRoomAsJson);
        return new ChatRoom(
                msg.getString("name"),
                msg.getInt("chatid"),
                msg.getString("user"),
                msg.getString("message"),
                msg.getString("timestamp"));
    }

    public String getChatRoomName() {
        return mChatRoomName;
    }

    public int getChatId() {
        return mChatId;
    }

    public String getLastSender() {
        return mLastSender;
    }

    public String getLastMessage() {
        return mLastMessage;
    }

    public String getTimeStamp() {
        return mTimeStamp;
    }

    /**
     * Provides equality solely based on chatId.
     * @param other the other object to check for equality
     * @return true if other chat ID matches this chat ID, false otherwise
     */
    @Override
    public boolean equals(@Nullable Object other) {
        boolean result = false;
        if (other instanceof ChatRoom) {
            result = mChatId == ((ChatRoom) other).mChatId;
        }
        return result;
    }
}
