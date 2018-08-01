package com.example.itc.databaze;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = findViewById(R.id.textView);
        final EditText editText = findViewById(R.id.editText);
        final String input = editText.getText().toString().trim();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("message");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
//                textView.setText(value);
                Log.d("TAG", "Value is: " + value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
                Toast.makeText(MainActivity.this, "Error uncured!",
                        Toast.LENGTH_LONG).show();

            }
        });
        Button button = findViewById(R.id.send_but);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 //               if(!TextUtils.isEmpty(input)) {
                    myRef.setValue(input);
                    textView.setText(editText.getText());

                    Toast.makeText(MainActivity.this, editText.getText(),
                            Toast.LENGTH_LONG).show();
   //             } else {
     //               Toast.makeText(MainActivity.this, "Enter text!",
       //                     Toast.LENGTH_LONG).show();
         //       }
            }
        });
    }
}
