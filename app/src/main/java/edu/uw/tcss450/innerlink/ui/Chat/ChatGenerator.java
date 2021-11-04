package edu.uw.tcss450.innerlink.ui.Chat;

import java.util.Arrays;
import java.util.List;

/**
 * Generates a list of Chats.
 */
public class ChatGenerator {
    private static final Chat[] CHATS;
    private static final int COUNT = 20;

    static {
        CHATS = new Chat[COUNT];
        for (int i = 0; i < CHATS.length; i++) {
            CHATS[i] = new Chat("Sender " + i, "Latest Message Preview", "11/" + i + "/21");
        }
    }

    public static List<Chat> getChatList() {
        return Arrays.asList(CHATS);
    }

    public static Chat[] getChats() {
        return Arrays.copyOf(CHATS, CHATS.length);
    }

    private ChatGenerator() {}
}
