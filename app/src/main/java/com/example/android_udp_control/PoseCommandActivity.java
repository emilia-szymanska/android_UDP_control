package com.example.android_udp_control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

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
        try
        {
            getSupportActionBar().hide();
        }
        catch (Exception ex)
        {
            System.out.println("Exception while hiding the action bar");
        }
        setContentView(R.layout.pose_command_activity);

        intent      = getIntent();
        bundle      = this.getIntent().getExtras();
        udpAddress  = bundle.getString("udpAddress");
        updPort     = bundle.getInt("udpPort");

        myUdpClient = new UDPClient(udpAddress, updPort);
        myUdpClient.setSocket();

        accept       = findViewById(R.id.accept);
        previous     = findViewById(R.id.previous);
        desiredX     = findViewById(R.id.desired_x);
        desiredY     = findViewById(R.id.desired_y);
        desiredTheta = findViewById(R.id.desired_theta);

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
                        //System.out.println(message);
                        String[] positionArray = message.split(",");
                        // TODO: change that runnable
                        runOnUiThread(new UpdatePositionDesiredRunnable(positionArray[0], positionArray[1], positionArray[2]));
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
                int theta = Integer.parseInt(desiredTheta.getText().toString());
                if(theta < -180 || theta > 180)
                {
                    Toast.makeText(PoseCommandActivity.this, "\u03F4 must be from range [-180, 180]", Toast.LENGTH_SHORT).show();
                    return;
                }
                float x = Float.parseFloat(desiredX.getText().toString());
                float y = Float.parseFloat(desiredY.getText().toString());
                String command = Float.toString(x) + "," + Float.toString(y) + "," + Integer.toString(theta);
                myUdpClient.sendCommand(command);
            }
        });


        previous.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                myUdpClient.sendCommand("Change to arrow view");
                myUdpClient.closeSocket();

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
