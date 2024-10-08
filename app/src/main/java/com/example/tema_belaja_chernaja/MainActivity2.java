package com.example.tema_belaja_chernaja;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity2 extends AppCompatActivity {
    private boolean isDarkMode;
    private int clickCount;
    private TextView clickCountTextView;
    private ConstraintLayout layout;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        preferences = getSharedPreferences("activity2Prefs", MODE_PRIVATE);
        isDarkMode = preferences.getBoolean("isDarkMode", true);
        clickCount = preferences.getInt("clickCount", 0);

        layout = findViewById(R.id.main);
        ImageButton imageButton = findViewById(R.id.imageButtonId);
        ImageButton notCoinButton = findViewById(R.id.notCoinButton);
        clickCountTextView = findViewById(R.id.clickCountTextView);


        applyTheme();

        clickCountTextView.setText("Clicks: " + clickCount);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDarkMode = !isDarkMode;
                applyTheme();
                savePreferences();
            }
        });


        notCoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount++;
                clickCountTextView.setText("Clicks: " + clickCount);
                savePreferences();
            }
        });
    }


    private void applyTheme() {
        if (isDarkMode) {
            layout.setBackgroundColor(Color.DKGRAY);
        } else {
            layout.setBackgroundColor(Color.WHITE);
        }
    }


    private void savePreferences() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isDarkMode", isDarkMode);
        editor.putInt("clickCount", clickCount);
        editor.apply();
    }
}
