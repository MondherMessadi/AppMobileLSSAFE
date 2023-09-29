package tn.esprit.lssafe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import tn.esprit.lssafe.ChatModel;
import tn.esprit.lssafe.Messages;
import tn.esprit.lssafe.R;
import tn.esprit.lssafe.User;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_Left =0 ;
    public static final int MSG_TYPE_RIGHT=1 ;
    private Context mContext ;
    private List<ChatModel> mChat ;
    private String imageurl ;

    FirebaseUser fuser ;

    public  MessageAdapter(Context mContext ,List<ChatModel> mChat,String imageurl ){
        this.mChat =mChat ;
        this.mContext = mContext ;
        this.imageurl = imageurl ;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_TYPE_RIGHT) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right,parent,false) ;
            return new MessageAdapter.ViewHolder(view);

        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left,parent,false) ;
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }

        ChatModel chat = mChat.get(position);
        TextView messageTextView = holder.show_message;
        if (messageTextView == null) {
            return;
        }

        messageTextView.setText(chat.getMessage());

        if (imageurl.equals("default")) {
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(imageurl).into(holder.profile_image);
        }

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_message;
        public ImageView profile_image ;

        public ViewHolder(View itemView) {
            super(itemView);
            show_message= itemView.findViewById(R.id.show_message) ;
            profile_image = itemView.findViewById(R.id.profile_image) ;
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT ;
        }else {
            return  MSG_TYPE_Left ;
        }
    }
}