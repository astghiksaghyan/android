package com.example.student.network;



import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageFragment extends DialogFragment {



    private ImageView imageView;


    public ImageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment, container,
                false);
        getDialog().setTitle("DialogFragment Tutorial");


        imageView = rootView.findViewById(R.id.big_image);

        int position = getArguments().getInt("edttext");
        Picasso.get().load(MainActivity.listItems.get(position).getImageUrl()).into(imageView);


        return rootView;
    }
}