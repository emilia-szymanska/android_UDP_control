package com.example.android_udp_control;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoseCommandActivity extends AppCompatActivity
{
    String backToArrowMsg = "Change to arrow view";
    private ImageButton previous;
    private Button accept;
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
        try {
            getSupportActionBar().hide();
        }
        catch (Exception ex) {
            System.out.println("Exception while hiding the action bar");
        }

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        if (width >= 1080)
            setContentView(R.layout.pose_command_activity_fullhd);
        else
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
            public void run() {
                while(!Thread.currentThread().isInterrupted()) {
                    try {
                        String message = myUdpClient.receiveData();
                        String[] positionArray = message.split(",");
                        runOnUiThread(new UpdatePositionDesiredRunnable(positionArray[0], positionArray[1], positionArray[2]));
                    }
                    catch(Exception e) {
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
                if(theta < -180 || theta > 180) {
                    Toast.makeText(PoseCommandActivity.this, "\u03F4 must be from range [-180, 180]", Toast.LENGTH_SHORT).show();
                    return;
                }
                float x = Float.parseFloat(desiredX.getText().toString());
                float y = Float.parseFloat(desiredY.getText().toString());
                String command = Float.toString(x) + "," + Float.toString(y) + "," + Integer.toString(theta);
                sendCommandAndCloseTheSocket(command, false);
            }
        });

        previous.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendCommandAndCloseTheSocket(backToArrowMsg, true);
                closeThread(rxThread);

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

    public void sendCommandAndCloseTheSocket(String messageToSend, Boolean closeTheSocket)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myUdpClient.sendCommand(messageToSend);
                if(closeTheSocket)
                    myUdpClient.closeSocket();
            }
        });
    }

}
