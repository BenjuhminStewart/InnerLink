package edu.uw.tcss450.innerlink.ui.Chat;

import java.io.Serializable;

public class Chat implements Serializable {
    private final String mSender;
    private final String mMessage;
    private final String mDate;

    public Chat(String mSender, String mMessage, String mDate) {
        this.mSender = mSender;
        this.mMessage = mMessage;
        this.mDate = mDate;
    }

    public String getSender() {
        return mSender;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getDate() {
        return mDate;
    }
}
