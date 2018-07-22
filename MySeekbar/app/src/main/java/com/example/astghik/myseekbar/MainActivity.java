package com.example.astghik.myseekbar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int seekR, seekG, seekB;

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.seekRed:
                    seekR = progress;
                    TextView textViewRed = findViewById(R.id.numberRed);
                    String textRed = Integer.toString(seekR);
                    textViewRed.setText(textRed);
                    break;
                case R.id.seekGreen:
                    seekG = progress;
                    TextView textViewGreen = findViewById(R.id.numberGreen);
                    String textGreen = Integer.toString(seekG);
                    textViewGreen.setText(textGreen);
                    break;
                case R.id.seekBlue:
                    seekB = progress;
                    TextView textViewBlue = findViewById(R.id.numberBlue);
                    String textBlue = Integer.toString(seekB);
                    textViewBlue.setText(textBlue);
                    break;
            }

            doSomethingWithColor();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar sbR = findViewById(R.id.seekRed);
        SeekBar sbG = findViewById(R.id.seekGreen);
        SeekBar sbB = findViewById(R.id.seekBlue);

        sbR.setOnSeekBarChangeListener(onSeekBarChangeListener);
        sbG.setOnSeekBarChangeListener(onSeekBarChangeListener);
        sbB.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }

    private void doSomethingWithColor() {
        int color = Color.rgb(seekR, seekG, seekB);
        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        mainLayout.setBackgroundColor(color);
    }
}
