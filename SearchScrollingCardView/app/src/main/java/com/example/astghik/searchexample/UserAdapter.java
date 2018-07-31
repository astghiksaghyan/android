package com.example.astghik.searchexample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> implements Filterable{

    private final Context context;
    private List<User> users;
    private List<User> filteredUsers;
    public static String KEY = "key";
    private Intent intent;

    UserAdapter(List<User> users, Context context) {
        this.users = users;
        filteredUsers = users;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        User user = filteredUsers.get(position);
        holder.tvName.setText(user.getName());
        holder.description.setText(user.getDescription());
        Glide.with(context)
                .load(user.getImageUrl())
                .into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(context, ScrollingActivity.class);
                intent.putExtra(KEY, position);
                context.startActivity(intent);
            }
        });
        holder.delBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filteredUsers.remove(holder.getAdapterPosition());
                notifyItemRemoved(position);
            }
        });
        holder.callBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = users.get(position).getPhoneNumber();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                holder.callBut.getContext().startActivity(intent);
            }
        });
        holder.emailBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = new String(users.get(position).getEmail());
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, users.get(position).getEmail());
                intent.putExtra(Intent.EXTRA_SUBJECT, "ITC-Homework");
                intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
                holder.emailBut.getContext().startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredUsers.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredUsers = users;
                } else {
                    List<User> filteredList = new ArrayList<>();
                    for (User row : users) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    filteredUsers = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUsers;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredUsers = (ArrayList<User>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView image;
        private TextView tvName;
        private TextView description;
        private ImageButton callBut;
        private ImageButton emailBut;
        private ImageButton delBut;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            tvName = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);

            callBut = itemView.findViewById(R.id.call_but);
            emailBut = itemView.findViewById(R.id.email_but);
            delBut = itemView.findViewById(R.id.delete_but);
        }
    }


}
