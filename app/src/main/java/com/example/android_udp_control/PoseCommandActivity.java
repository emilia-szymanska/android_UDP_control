package com.example.android_udp_control;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class PoseCommandActivity extends AppCompatActivity
{
    private ImageButton accept, previous;
    static TextView textX, textY, textTheta;
    EditText desiredX, desiredY, desiredTheta;
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
        setContentView(R.layout.pose_command_activity);

        intent      = getIntent();
        bundle      = this.getIntent().getExtras();
        udpAddress  = bundle.getString("udpAddress");
        updPort     = bundle.getInt("udpPort");

        myUdpClient = new UDPClient(udpAddress, updPort);
        myUdpClient.setSocket();

        accept          = findViewById(R.id.accept);
        previous        = findViewById(R.id.previous);

        textX = findViewById(R.id.x_pos);
        textY = findViewById(R.id.y_pos);
        textTheta = findViewById(R.id.theta_pos);


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
                        String[] positionArray = message.split(",");
                        // change that runnable
                        runOnUiThread(new UpdatePositionRunnable(positionArray[0], positionArray[1], positionArray[2]));
                    }
                    catch(Exception e)
                    {
                        System.out.println("Caught an exception while receiving message");
                    }

                }
            }


        });

        accept.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View w)
            {
                // check if x, y and theta are of proper values
                myUdpClient.sendCommand("");
            }
        });




        previous.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

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


                myUdpClient.sendCommand("Bye UDP server");
                myUdpClient.closeSocket();
                Intent changeToArrows = new Intent(getApplicationContext(), ArrowActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("udpAddress", udpAddress);
                bundle.putInt("udpPort", updPort);
                changeToArrows.putExtras(bundle);
                startActivity(changeToArrows);
            }
        });

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
