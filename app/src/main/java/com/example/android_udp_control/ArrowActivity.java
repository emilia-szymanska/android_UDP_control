package com.example.android_udp_control;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.os.Handler;
import android.widget.TextView;


public class ArrowActivity extends AppCompatActivity
{
    private ImageButton up, down, left, right, center, previous, upleftarrow, uprightarrow, downleftarrow, downrightarrow;
    static TextView textX;
    String message="none";
    UDPClient myUdpClient;
    Bundle bundle;
    Intent intent;
    String udpAddress;
    int updPort;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
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

        textX = findViewById(R.id.x_pos);

        message    = "none";

        Thread udpThread = new Thread(new Runnable() {
            //private volatile boolean flag = true;
            @Override
            public void run()
            {
                while(!Thread.currentThread().isInterrupted())
                {
                    try
                    {
                        myUdpClient.sendCommand(message);
                        System.out.println(message);
                        Thread.sleep(50);
                    }
                    catch(Exception e)
                    {
                        System.out.println("Caught an exception while sleeping");
                        Thread.currentThread().interrupt();
                        //e.printStackTrace();
                    }

                }
            }

            /*public void stopRunning()
            {
                flag = false;
            }*/
        });


        Thread rxThread = new Thread(new Runnable() {

            @Override
            public void run()
            {
                while(!Thread.currentThread().isInterrupted())
                {
                    try
                    {
                        String message = myUdpClient.receiveData();
                        System.out.println(message);
                        runOnUiThread(new UpdatePositionRunnable(message, " ", " "));
                    }
                    catch(Exception e)
                    {
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


        center.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        message = "center";
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

                if (udpThread.isAlive())
                {
                    udpThread.interrupt();
                    try
                    {
                        udpThread.join();
                    }
                    catch (Exception ex)
                    {
                        System.out.println("Caught an exception while killing a thread");
                    }

                }

                if (rxThread.isAlive())
                {
                    rxThread.interrupt();
                    /*try
                    {
                        rxThread.join();
                    }
                    catch (Exception ex)
                    {
                        System.out.println("Caught an exception while killing a thread");
                    }*/

                }
                System.out.println("zamkniety rx");


                myUdpClient.sendCommand("Bye UDP server");
                myUdpClient.closeSocket();
                Intent changeToMain = new Intent(ArrowActivity.this, MainActivity.class);
                startActivity(changeToMain);
            }
        });

        udpThread.start();
        rxThread.start();
    }

    public static void updatePosition(String x, String y, String theta)
    {
        textX.setText(x);
        textX.invalidate();
        textX.requestLayout();
    }

}
