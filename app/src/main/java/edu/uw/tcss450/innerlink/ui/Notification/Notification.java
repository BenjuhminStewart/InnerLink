package edu.uw.tcss450.innerlink.ui.Notification;

import java.io.Serializable;

/**
 * Represents a user Notification.
 */
public class Notification implements Serializable {
    private final String mType;
    private final String mMessage;
    private final String mDate;

    public Notification(String mType, String mMessage, String mDate) {
        this.mType = mType;
        this.mMessage = mMessage;
        this.mDate = mDate;
    }

    public String getType() {
        return mType;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getDate() {
        return mDate;
    }
}