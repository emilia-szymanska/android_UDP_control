package com.example.android_udp_control;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.os.Handler;
import android.widget.TextView;


public class ArrowActivity extends AppCompatActivity
{
    private ImageButton up, down, left, right, center, previous, upleftarrow, uprightarrow, downleftarrow, downrightarrow;
    static TextView textX, textY, textTheta;
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
        try
        {
            getSupportActionBar().hide();
        }
        catch (Exception ex)
        {
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

        textX = findViewById(R.id.x_pos);
        textY = findViewById(R.id.y_pos);
        textTheta = findViewById(R.id.theta_pos);

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
                        e.printStackTrace();
                    }

                }
            }
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
                        //System.out.println(message);
                        String[] positionArray = message.split(",");
                        runOnUiThread(new UpdatePositionRunnable(positionArray[0], positionArray[1], positionArray[2]));
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


                Thread tempThread = new Thread(new Runnable() {
                    //private volatile boolean flag = true;
                    @Override
                    public void run()
                    {
                        myUdpClient.sendCommand("Bye UDP server");
                        myUdpClient.closeSocket();
                    }
                });

                tempThread.start();

                if (rxThread.isAlive())
                {
                    rxThread.interrupt();
                    try
                    {
                        rxThread.join();
                    }
                    catch (Exception ex)
                    {
                        System.out.println("Caught an exception while killing a thread");
                    }

                }

                Intent changeToMain = new Intent(ArrowActivity.this, MainActivity.class);
                startActivity(changeToMain);
            }
        });

        center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (udpThread.isAlive())
                {
                    System.out.println("przed interrupt");
                    udpThread.interrupt();
                    try
                    {
                        System.out.println("po interrupt, przed join");
                        udpThread.join();
                        System.out.println("po interrupt join");
                    }
                    catch (Exception ex)
                    {
                        System.out.println("Caught an exception while killing a thread");
                    }

                }

                System.out.println("Vasia");


                Thread tmpThread = new Thread(new Runnable() {
                    //private volatile boolean flag = true;
                    @Override
                    public void run()
                    {
                        myUdpClient.sendCommand("Change to desired pose view");
                        myUdpClient.closeSocket();
                    }
                });

                tmpThread.start();


                System.out.println("Piotrek");

                System.out.println("przed zamknieciem rx");
                if (rxThread.isAlive())
                {
                    System.out.println("przed interrupt rx");
                    rxThread.interrupt();
                    try
                    {
                        System.out.println("przed join rx");
                        rxThread.join();
                        System.out.println("po join rx");
                    }
                    catch (Exception ex)
                    {
                        System.out.println("Caught an exception while killing a thread");
                    }
                }

                if(!rxThread.isAlive()) System.out.println("Closed rx");
                if(!udpThread.isAlive()) System.out.println("Closed udp");

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

}
