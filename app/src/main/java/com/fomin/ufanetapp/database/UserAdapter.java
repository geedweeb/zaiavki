package com.fomin.ufanetapp.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fomin.ufanetapp.R;
import java.util.List;
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context context;
    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }
    private List<User> userList;
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_layout, parent, false);
        return new UserViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.userName.setText(user.getName());
        holder.userLastname.setText(user.getLastname());
        holder.userSurname.setText(user.getSurname());
        holder.userPhone.setText(user.getPhone());
        holder.userAddress.setText(user.getAddress());
        holder.userTariff.setText(user.getTariff());
        holder.userCabel.setText(user.getCabel());
    }
    @Override
    public int getItemCount() {
        if (userList != null) {
            return userList.size();
        } else {
            return 0;
        }
    }
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public TextView userLastname;
        public TextView userSurname;
        public TextView userPhone;
        public TextView userAddress;
        public TextView userTariff;
        public TextView userCabel;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            userLastname = itemView.findViewById(R.id.user_lastname);
            userSurname = itemView.findViewById(R.id.user_surname);
            userPhone = itemView.findViewById(R.id.user_phone);
            userAddress = itemView.findViewById(R.id.user_address);
            userTariff = itemView.findViewById(R.id.user_tariff);
            userCabel = itemView.findViewById(R.id.user_cabel);
        }
    }
}
