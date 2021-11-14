package edu.uw.tcss450.innerlink.ui.Chat;

import java.io.Serializable;

/**
 * Encapsulates the Chat Room details
 */
public class ChatRoom implements Serializable {
    private final int mChatId;

    public ChatRoom(int chatId) {
        mChatId = chatId;
    }

    public int getChatId() {
        return mChatId;
    }
}
