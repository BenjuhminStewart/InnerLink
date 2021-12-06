package edu.uw.tcss450.innerlink.ui.Chat;

import android.location.LocationRequest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentChatRoomCardBinding;

/**
 * Allows the user to cycle/scroll through the list of active Chat Rooms on the Chat screen.
 */
public class ChatRoomRecyclerViewAdapter extends RecyclerView.Adapter<ChatRoomRecyclerViewAdapter.ChatRoomViewHolder> {
    //Store all of the Chat Rooms to present
    private final List<ChatRoom> mChatRooms;

    public ChatRoomRecyclerViewAdapter(List<ChatRoom> chatRooms) {
        mChatRooms = chatRooms;
    }

    @NonNull
    @Override
    public ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatRoomViewHolder(LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.fragment_chat_room_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomViewHolder holder, int position) {
        holder.setChatRoom(mChatRooms.get(position));

        holder.itemView.setOnClickListener(v -> {
            Navigation.findNavController(holder.mView).navigate(
                    ChatRoomListFragmentDirections.actionNavigationChatsToChatRoomFragment(holder.mChatRoom)
            );
        });
        holder.binding.buttonManage.setOnClickListener(v -> {
            Navigation.findNavController(holder.mView).navigate(
                    ChatRoomListFragmentDirections.actionNavigationChatsToChatManageFragment(holder.mChatRoom.getChatId()));
        });
    }

    @Override
    public int getItemCount() {
        return mChatRooms.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Chat Recycler View.
     */
    public class ChatRoomViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentChatRoomCardBinding binding;
        private ChatRoom mChatRoom;

        public ChatRoomViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatRoomCardBinding.bind(view);
        }

        void setChatRoom(final ChatRoom chatRoom) {
            mChatRoom = chatRoom;
            if (chatRoom.getChatRoomName().length() > 15) {
                String newChatRoomName = "";
                for (int i = 0; i < 17; i++) {
                        newChatRoomName += chatRoom.getChatRoomName().charAt(i);
                }
                newChatRoomName += " ...";
                binding.textChatRoomName.setText(newChatRoomName);
            } else {
                binding.textChatRoomName.setText(chatRoom.getChatRoomName());
            }
            binding.textLastMessage.setText(chatRoom.getLastSender() + ": "+chatRoom.getLastMessage());
            binding.textTimestamp.setText(getTime(chatRoom.getTimeStamp()));
        }
    }

    public String getTime(String time) {

        String date = "";
        String unformattedTime = "";
        String[] dateTime = time.split("T");
        date=dateTime[0];
        unformattedTime=dateTime[1];
        String[] times= unformattedTime.split(":");
        String[] mainTime = unformattedTime.split("\\.");

        String hours = times[0];
        String minutes = times[1];

        String meridiem = "";
        if (Integer.parseInt(hours) > 11) {
            meridiem = "pm";
        } else {
            meridiem = "am";
        }
        int hourFormatted = ((Integer.parseInt(hours) % 12));
        if(hourFormatted == 0) {
            hourFormatted = 12;
        }
        String finalTime = hourFormatted + ":" + minutes + meridiem;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String currDate = sdf.format(new Date());
        String[] currDateArr = currDate.split("-");
        String[] textDateArr = date.split("-");
        if(Integer.parseInt(currDateArr[0]) == Integer.parseInt(textDateArr[0])) {
            if(Integer.parseInt(currDateArr[1]) == Integer.parseInt(textDateArr[1])) {
                if(Integer.parseInt(currDateArr[2]) == Integer.parseInt(textDateArr[2])){
                    return finalTime;
                }
            }
        }
        return date;

    }


    }


