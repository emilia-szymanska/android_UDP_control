package com.example.android_udp_control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.graphics.Color;
import android.os.Handler;

public class ArrowActivity extends AppCompatActivity
{
    private ImageButton up, down, left, right, stop, previous;

    String message="none";
    Handler handler;
    // @SuppressLint("ClickableViewAccessibility");
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.arrow_view);

        up = findViewById(R.id.uparrow);
        down = findViewById(R.id.downarrow);
        left = findViewById(R.id.leftarrow);
        right = findViewById(R.id.rightarrow);
        stop = findViewById(R.id.stop);
        previous = findViewById(R.id.previous);

        message = "none";
        handler = new Handler();
        handler.post(periodicSend);


        up.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        message = "up";
                        return true;

                    case MotionEvent.ACTION_UP:
                        message = "none";
                        return true;
                }
                return false;
            }
        });


        down.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        message = "down";
                        return true;

                    case MotionEvent.ACTION_UP:
                        message = "none";
                        return true;
                }
                return false;
            }
        });

        left.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        message = "left";
                        return true;

                    case MotionEvent.ACTION_UP:
                        message = "none";
                        return true;
                }
                return false;
            }
        });


        right.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        message = "right";
                        return true;

                    case MotionEvent.ACTION_UP:
                        message = "none";
                        return true;
                }
                return false;
            }
        });


        stop.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        message = "stop";
                        return true;

                    case MotionEvent.ACTION_UP:
                        message = "none";
                        return true;
                }
                return false;
            }
        });


        previous.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                handler.removeCallbacks(periodicSend);
                MainActivity.udpClient.sendCommand("Bye UDP server");
                Intent changeToMain = new Intent(ArrowActivity.this, MainActivity.class);
                startActivity(changeToMain);
            }
        });
    }

    private Runnable periodicSend = new Runnable()
    {
        @Override
        public void run()
        {
            MainActivity.udpClient.sendCommand(message);
            System.out.println(message);
            handler.postDelayed(this, 50);
        }
    };
}
