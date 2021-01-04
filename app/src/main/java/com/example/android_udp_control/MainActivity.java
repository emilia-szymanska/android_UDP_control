package com.example.android_udp_control;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity
{
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonNext, buttonRetry;
    static TextView textViewState, textViewRx;
    public UDPClient udpClient;
    Thread udp_thread;
    String address;
    int port, state;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);


        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort = (EditText) findViewById(R.id.port);
        textViewState = (TextView) findViewById(R.id.state);
        textViewRx = (TextView) findViewById(R.id.received);
        buttonNext = (Button) findViewById(R.id.next);
        buttonRetry = (Button) findViewById(R.id.retry);
        buttonConnect = (Button) findViewById(R.id.connect);

        buttonNext.setEnabled(false);
        buttonRetry.setEnabled(false);

        //TODO: why it doesn't display like this?
        editTextPort.setText("192.168.137.231", TextView.BufferType.EDITABLE);
        editTextPort.setText("20001", TextView.BufferType.EDITABLE);


        buttonNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent changeToArrows = new Intent(getApplicationContext(), ArrowActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("udpAddress", address);
                bundle.putInt("udpPort", port);
                changeToArrows.putExtras(bundle);
                startActivity(changeToArrows);
            }
        });

        buttonRetry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (udp_thread.isAlive())
                    udp_thread.interrupt();
                updateTexts("waiting for connection", " ");
                buttonConnect.setEnabled(true);
                buttonRetry.setEnabled(false);
                buttonNext.setEnabled(false);
                editTextPort.getText().clear();
                editTextPort.setText("192.168.000.000", TextView.BufferType.EDITABLE);
                editTextPort.setText("00000", TextView.BufferType.EDITABLE);
            }
        });

        buttonConnect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                buttonConnect.setEnabled(false);
                buttonRetry.setEnabled(true);
                updateTexts("CONNECTING...", " ");

                udp_thread = new Thread()
                {
                    public void run()
                    {
                        address = editTextAddress.getText().toString();
                        port = Integer.parseInt(editTextPort.getText().toString());

                        udpClient = new UDPClient(address, port);
                        state = udpClient.initConnection();

                        if(state == 1)
                        {
                            runOnUiThread(new UpdateTextsRunnable("CONNECTED", " "));
                        }
                        else
                        {
                            if (state == 0)
                            {
                                runOnUiThread(new UpdateTextsRunnable("NOT CONNECTED", "wrong server"));
                            }
                            else
                            {
                                if (state == -2)
                                    runOnUiThread(new UpdateTextsRunnable("NOT CONNECTED", "timeout error"));
                                else
                                    runOnUiThread(new UpdateTextsRunnable("NOT CONNECTED", "caught an exception"));
                            }
                        }
                    }
                };
                udp_thread.start();
            }
        });

    }


    public static void updateTexts(String state, String additionalInfo)
    {
        textViewRx.setText(additionalInfo);
        textViewState.setText(state);
        textViewState.invalidate();
        textViewState.requestLayout();
    }

}
