package com.example.student.network;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.student.network.api.ApiResponse;
import com.example.student.network.api.Result;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Callback;


public class AdapterModel extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Result> list;
    private Context context;
    final public static String KEY_LOCATION = "keyLoc";
    final public static String KEY_CITY = "keyCity";




    AdapterModel(Context context, List<Result> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {

        switch (list.get(position).getGender()) {
            case "male":
                return 0;
            case "female":
                return 1;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0: View viewMale = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_male, parent, false);
                    return new ViewHolderMale(viewMale);
            case 1: View viewFemale = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_female, parent, false);
                    return new ViewHolderFemale(viewFemale);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Result item = list.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(context, MapsActivity.class);
                final Double latitude = item.getLocation().getCoordinates().getLatitude();
                final Double longitude = item.getLocation().getCoordinates().getLongitude();
                final LatLng location = new LatLng(latitude, longitude );
                intent.putExtra(KEY_LOCATION, location);
                final String city = item.getLocation().getCity();
                intent.putExtra(KEY_CITY, city);
                context.startActivity(intent);
            }
        });

        switch (item.getGender()) {
            case "male":
                Picasso.get().load(item.getPicture().getThumbnail())
                        .into(((ViewHolderMale)holder).imageView);
                ((ViewHolderMale)holder).titleTextView.setText(item.getName().getFirst());
                break;
            case "female":
                Picasso.get().load(item.getPicture().getThumbnail())
                        .into(((ViewHolderFemale)holder).imageView);
                ((ViewHolderFemale)holder).titleTextView.setText(item.getName().getFirst());
                setListenerCallBut(((ViewHolderFemale)holder), position);
                setListenerEmailBut(((ViewHolderFemale)holder), position);
                break;
        }
    }

    private void setListenerEmailBut(@NonNull final ViewHolderFemale holder,
                                     @SuppressLint("RecyclerView") final int position) {
        holder.emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, list.get(position).getEmail());
                intent.putExtra(Intent.EXTRA_SUBJECT, "ITC-Homework");
                intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
                holder.emailButton.getContext()
                        .startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });
    }

    private void setListenerCallBut(@NonNull final ViewHolderFemale holder,
                                    @SuppressLint("RecyclerView") final int position) {
        holder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phone = list.get(position).getCell();
                final Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.fromParts("tel", phone, null));
                holder.callButton.getContext().startActivity(intent);
            }
        });
    }


    private class ViewHolderMale extends RecyclerView.ViewHolder {


        private ImageView imageView;
        private TextView titleTextView;

        private ViewHolderMale(final View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.small_image);
            titleTextView = itemView.findViewById(R.id.title_model);
        }
    }

    private class ViewHolderFemale extends RecyclerView.ViewHolder {

        private CircleImageView imageView;
        private TextView titleTextView;
        private ImageButton callButton;
        private ImageButton emailButton;

        private ViewHolderFemale(final View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            titleTextView = itemView.findViewById(R.id.title);
            callButton = itemView.findViewById(R.id.call_but);
            emailButton = itemView.findViewById(R.id.email_but);
        }
    }
}
