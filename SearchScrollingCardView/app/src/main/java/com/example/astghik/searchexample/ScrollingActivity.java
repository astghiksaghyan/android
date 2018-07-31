package com.example.astghik.searchexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class ScrollingActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final int position = getIntent().getIntExtra(UserAdapter.KEY, 0);
        final User user = DataProvider.users.get(position);
        setTitle(user.getName());
        final TextView descr = findViewById(R.id.descrip);
        descr.setText(user.getDescription());
        final ImageView image = findViewById(R.id.scrolling_image);
        Picasso.get().load(user.getImageUrl()).into(image);
        final ImageButton mail_but = findViewById(R.id.fab);
        ImageButton phone_but = findViewById(R.id.phone);

        call(user, phone_but);
        sendMail(user, mail_but);
    }

    private void sendMail(final User user, ImageButton mail_but) {
        mail_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = new String(user.getEmail());
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, user.getEmail());
                intent.putExtra(Intent.EXTRA_SUBJECT, "ITC-Homework");
                intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
                view.getContext().startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });
    }

    private void call(final User user, ImageButton phone_but) {
        phone_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = user.getPhoneNumber();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                view.getContext().startActivity(intent);
            }
        });
    }
}
