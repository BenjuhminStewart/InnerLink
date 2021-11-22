package edu.uw.tcss450.innerlink.ui.Contacts;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import edu.uw.tcss450.innerlink.ui.Chat.ChatMessage;

public class ContactsModel implements Serializable {

    private String mUsername;
    private String mEmail;
    private String mFirstName;
    private String mLastName;

    public ContactsModel(String username, String email,
                            String firstName, String lastName) {
        mUsername = username;
        mEmail = email;
        mFirstName = firstName;
        mLastName = lastName;
    }

    /**
     * Static factory method to turn a properly formatted JSON String into a
     * ContactsModel object.
     * @param cmAsJson the String to be parsed into a ContactsModel Object.
     * @return a ContactsModelObject with the details contained in the JSON String.
     * @throws JSONException when cmAsString cannot be parsed into a ContactsModel.
     */
    public static ContactsModel createFromJsonString(final String cmAsJson) throws JSONException {
        final JSONObject msg = new JSONObject(cmAsJson);
        return new ContactsModel(msg.getString("username"),
                msg.getString("email"),
                msg.getString("firstname"),
                msg.getString("lastname"));
    }

    public String getmUsername() {
        return mUsername;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }


}