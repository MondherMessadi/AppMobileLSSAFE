package tn.esprit.lssafe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import tn.esprit.lssafe.Chat;
import tn.esprit.lssafe.ChatModel;
import tn.esprit.lssafe.Messages;
import tn.esprit.lssafe.R;
import tn.esprit.lssafe.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private final boolean ischat;
    private Context mContext ;
    private List<User> mUsers ;
    private String userID ;
    private DatabaseReference reference ;
    private FirebaseUser user ;

    String theLastMessage ;
    public  UserAdapter(Context mContext ,List<User> mUsers ,boolean ischat){

        this.mUsers =mUsers ;
        this.mContext = mContext ;
        this.ischat = ischat ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false) ;
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = mUsers.get(position);
        holder.name.setText(user.getFullName());
        if(user.getImageURL().equals("default")) {
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(mContext).load(user.getImageURL()).into(holder.profile_image) ;
        }

        if(ischat) {
            lastMessage(user.getId(),holder.last_msg);

        }else  {
            holder.last_msg.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Messages.class);



                intent.putExtra("userid", user.getId());

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public ImageView profile_image ;

        private TextView last_msg ;



        public ViewHolder(View itemView) {
            super(itemView);

            name= itemView.findViewById(R.id.name) ;
            profile_image =itemView.findViewById(R.id.profile_image) ;
            last_msg = itemView.findViewById(R.id.last_msg) ;
        }
    }
    // check last message
    private void lastMessage (String userid , TextView last_msg) {
        theLastMessage ="default" ;
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                    ChatModel chat =snapshot.getValue(ChatModel.class) ;
                    if(chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())
                    ) {
                        theLastMessage = chat.getMessage() ;
                    }
                }
                switch (theLastMessage) {
                    case "default" :
                        last_msg.setText("No Message");
                        break;
                    default:
                        last_msg.setText(theLastMessage);
                        break;
                }
                theLastMessage="default" ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;
    }

}