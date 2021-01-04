package com.example.android_udp_control;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.os.Handler;

public class ArrowActivity extends AppCompatActivity
{
    private ImageButton up, down, left, right, stop, previous;

    String message="none";
    Handler handler;
    Thread thread;
    UDPClient myUdpClient;
    Bundle bundle;
    Intent intent;
    String udpAddress;
    int updPort;

    // @SuppressLint("ClickableViewAccessibility");
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.arrow_view);

        intent = getIntent();
        bundle = this.getIntent().getExtras();
        udpAddress = bundle.getString("udpAddress");
        updPort = bundle.getInt("udpPort");


        myUdpClient = new UDPClient(udpAddress, updPort);
        myUdpClient.setSocket();

        up = findViewById(R.id.uparrow);
        down = findViewById(R.id.downarrow);
        left = findViewById(R.id.leftarrow);
        right = findViewById(R.id.rightarrow);
        stop = findViewById(R.id.stop);
        previous = findViewById(R.id.previous);

        message = "none";
        handler = new Handler();
        thread = new Thread(periodicSend);
        thread.start();


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
                System.out.println("Hmmmmm");
                myUdpClient.sendCommand("Bye UDP server");
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
            myUdpClient.sendCommand(message);
            System.out.println(message);
            handler.postDelayed(this, 50);
        }
    };
}
