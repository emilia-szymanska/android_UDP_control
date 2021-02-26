package com.example.android_udp_control;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ArrowActivity extends AppCompatActivity
{
    String changeToAutonomousRideMsg = "Change to autonomous ride view";
    String goodbyeMsg                = "Bye UDP server";
    String message                   = "none";

    private ImageButton up, down, left, right, center, previous, upleftarrow, uprightarrow, downleftarrow, downrightarrow;
    static TextView textX, textY, textTheta;
    UDPClient myUdpClient;
    Bundle bundle;
    Intent intent;
    String udpAddress;
    int updPort;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().hide();
        }
        catch (Exception ex) {
            System.out.println("Exception while hiding the action bar");
        }

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        if (width >= 1080)
            setContentView(R.layout.arrow_view_fullhd);
        else
            setContentView(R.layout.arrow_view);


        intent      = getIntent();
        bundle      = this.getIntent().getExtras();
        udpAddress  = bundle.getString("udpAddress");
        updPort     = bundle.getInt("udpPort");

        myUdpClient = new UDPClient(udpAddress, updPort);
        myUdpClient.setSocket();

        up              = findViewById(R.id.uparrow);
        down            = findViewById(R.id.downarrow);
        left            = findViewById(R.id.leftarrow);
        right           = findViewById(R.id.rightarrow);
        center          = findViewById(R.id.center);
        previous        = findViewById(R.id.previous);
        upleftarrow     = findViewById(R.id.upleftarrow);
        uprightarrow    = findViewById(R.id.uprightarrow);
        downleftarrow   = findViewById(R.id.downleftarrow);
        downrightarrow  = findViewById(R.id.downrightarrow);

        textX     = findViewById(R.id.x_pos);
        textY     = findViewById(R.id.y_pos);
        textTheta = findViewById(R.id.theta_pos);

        Thread udpThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted()) {
                    try {
                        myUdpClient.sendCommand(message);
                        System.out.println(message);
                        Thread.sleep(50);
                    }
                    catch(Exception e) {
                        System.out.println("Caught an exception while sleeping");
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted()) {
                    try {
                        String message = myUdpClient.receiveData();
                        String[] positionArray = message.split(",");
                        runOnUiThread(new UpdatePositionRunnable(positionArray[0], positionArray[1], positionArray[2]));
                    }
                    catch(Exception e) {
                        System.out.println("Caught an exception while receiving message");
                    }
                }
            }
        });


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


        upleftarrow.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        message = "upleft";
                        return true;

                    case MotionEvent.ACTION_UP:
                        message = "none";
                        return true;
                }
                return false;
            }
        });


        uprightarrow.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        message = "upright";
                        return true;

                    case MotionEvent.ACTION_UP:
                        message = "none";
                        return true;
                }
                return false;
            }
        });


        downleftarrow.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        message = "downleft";
                        return true;

                    case MotionEvent.ACTION_UP:
                        message = "none";
                        return true;
                }
                return false;
            }
        });


        downrightarrow.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        message = "downright";
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
                closeThread(udpThread);
                sendCommandAndCloseTheSocket(goodbyeMsg);
                closeThread(rxThread);

                Intent changeToMain = new Intent(ArrowActivity.this, MainActivity.class);
                startActivity(changeToMain);
            }
        });

        center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeThread(udpThread);
                sendCommandAndCloseTheSocket(changeToAutonomousRideMsg);
                closeThread(rxThread);

                Intent changeToDesired = new Intent(getApplicationContext(), PoseCommandActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("udpAddress", udpAddress);
                bundle.putInt("udpPort", updPort);
                changeToDesired.putExtras(bundle);
                startActivity(changeToDesired);
            }
        });

        udpThread.start();
        rxThread.start();
    }

    public static void updatePosition(String x, String y, String theta)
    {
        textX.setText(x);
        textY.setText(y);
        textTheta.setText(theta);
        textX.invalidate();
        textX.requestLayout();
    }

    public static void closeThread(Thread chosenThread)
    {
        if (chosenThread.isAlive())
        {
            chosenThread.interrupt();
            try
            {
                chosenThread.join();
            }
            catch (Exception ex)
            {
                System.out.println("Caught an exception while killing a thread");
            }
        }
    }

    public void sendCommandAndCloseTheSocket(String messageToSend)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myUdpClient.sendCommand(messageToSend);
                myUdpClient.closeSocket();
            }
        });
    }
}
