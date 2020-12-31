package com.example.android_udp_control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

public class ArrowActivity extends AppCompatActivity
{
    private ImageButton up;
    private ImageButton down;
    private ImageButton left;
    private ImageButton right;
    private ImageButton stop;
    private ImageButton previous;
    TextView napis;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.arrow_view);

        napis = findViewById(R.id.napis);
        up = findViewById(R.id.uparrow);
        down = findViewById(R.id.downarrow);
        left = findViewById(R.id.leftarrow);
        right = findViewById(R.id.rightarrow);
        stop = findViewById(R.id.stop);
        previous = findViewById(R.id.previous);


        up.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        napis.setTextColor(Color.WHITE);
                        System.out.println("Clicked");
                        return true;

                    case MotionEvent.ACTION_UP:
                        System.out.println("Unclicked");
                        napis.setTextColor(Color.RED);
                        return true;

                }
                return false;
            }
        });


        down.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(ArrowActivity.this, "Down arrow pressed", Toast.LENGTH_SHORT).show();
            }
        });


        left.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(ArrowActivity.this, "Left arrow pressed", Toast.LENGTH_SHORT).show();
            }
        });


        right.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(ArrowActivity.this, "Right arrow pressed", Toast.LENGTH_SHORT).show();
            }
        });


        stop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(ArrowActivity.this, "Stop pressed", Toast.LENGTH_SHORT).show();
            }
        });


        previous.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent changeToMain = new Intent(ArrowActivity.this, MainActivity.class);
                startActivity(changeToMain);
            }
        });
    }
}
