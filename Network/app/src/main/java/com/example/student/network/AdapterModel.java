package com.example.student.network;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterModel extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private final List<RetroUser> list;
    FragmentManager fm;


    public AdapterModel(MainActivity mainActivity, List<RetroUser> list, FragmentManager fragMan) {
        this.list = list;
        fm = fragMan;
    }

    @Override
    public int getItemViewType(int position) {

        switch (list.get(position).getType()) {
            case 0:
                return RetroUser.MALE_TYPE;
            case 2:
                return RetroUser.FEMALE_TYPE;
            default:
                return -1;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0: View viewMale = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_male, parent, false);
                    return new ViewHolderMale(viewMale);
            case 2: View viewFemale = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_female, parent, false);
                    return new ViewHolderFemale(viewFemale);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final RetroUser item = list.get(position);
        if (item != null) {
            switch (item.getType()) {
                case RetroUser.MALE_TYPE:
                    Picasso.get().load(item.getThumbnail()).into(holder.smallImageView);
                    holder.titleTextView.setText(item.getTitle());
                    break;
                case RetroUser.FEMALE_TYPE:
                    ((ImageTypeViewHolder) holder).txtType.setText(object.text);
                    ((ImageTypeViewHolder) holder).image.setImageResource(object.data);
                    break;

            }
        }



        Picasso.get().load(item.getSmallImgUrl()).into(holder.smallImageView);

        holder.titleTextView.setText(item.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle bundle = new Bundle();
                bundle.putInt("edttext", position);
                ImageFragment dFragment = new ImageFragment();
                dFragment.setArguments(bundle);
                dFragment.show(fm, "Dialog Fragment");
            }
        });


        
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolderMale extends RecyclerView.ViewHolder {


        private ImageView smallImageView;
        private TextView titleTextView;

        public ViewHolderMale(final View itemView) {
            super(itemView);

            smallImageView = itemView.findViewById(R.id.small_image);
            titleTextView = itemView.findViewById(R.id.title_model);

        }
    }

    public class ViewHolderFemale extends RecyclerView.ViewHolder {


        private CircleImageView imageView;
        private TextView titleTextView;
        private ImageButton callImageButton;
        private ImageButton emailImageButton;



        public ViewHolderFemale(final View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            titleTextView = itemView.findViewById(R.id.title);
            callImageButton = itemView.findViewById(R.id.call_but);
            emailImageButton = itemView.findViewById(R.id.email_but);
        }
    }
}
