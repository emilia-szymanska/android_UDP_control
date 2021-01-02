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
import android.os.Handler;

public class ArrowActivity extends AppCompatActivity
{
    private ImageButton up, down, left, right, stop, previous;
    TextView napis;
    String message;
    Handler handler;

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

        handler = new Handler();
        // Start the initial runnable task by posting through the handler
        handler.post(periodicSend);
        message = "NONE";

        up.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        //MainActivity.udpClient.sendCommand("UP");
                        message = "UP";

                        System.out.println("Clicked");
                        return true;

                    case MotionEvent.ACTION_UP:
                        //MainActivity.udpClient.sendCommand("STOP UP");
                        message = "NONE";
                        System.out.println("Unclicked");
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
                handler.removeCallbacks(periodicSend);
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

            if (message.equals("UP"))
                napis.setTextColor(Color.WHITE);
            else
                napis.setTextColor(Color.RED);

            handler.postDelayed(this, 20);
        }
    };
}
