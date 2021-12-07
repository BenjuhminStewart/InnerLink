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
    private final String mUserCount;

    public ChatRoom(String chatRoomName, int chatId, String lastSender, String lastMessage, String timeStamp, String userCount) {
        mChatRoomName = chatRoomName;
        mChatId = chatId;
        mLastSender = lastSender;
        mLastMessage = lastMessage;
        mTimeStamp = timeStamp;
        mUserCount = userCount;
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

    public String getUserCount(){
        return mUserCount;
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
