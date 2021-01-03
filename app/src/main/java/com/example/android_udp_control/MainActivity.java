package com.example.android_udp_control;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;


public class MainActivity extends AppCompatActivity
{
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonNext, buttonRetry;
    TextView textViewState, textViewRx;
    public static UDPClient udpClient;

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

        buttonNext.setOnClickListener(buttonNextOnClickListener);
        buttonRetry.setOnClickListener(buttonRetryOnClickListener);
        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
        //TODO: why it doesn't display like this?
        editTextPort.setText("192.168.137.231", TextView.BufferType.EDITABLE);
        editTextPort.setText("20001", TextView.BufferType.EDITABLE);

    }

    View.OnClickListener buttonNextOnClickListener =
            new View.OnClickListener()
            {
                @Override
                public void onClick(View arg0)
                {
                    Intent changeToArrows = new Intent(MainActivity.this, ArrowActivity.class);
                    startActivity(changeToArrows);
                }
            };

    View.OnClickListener buttonRetryOnClickListener =
            new View.OnClickListener()
            {
                @Override
                public void onClick(View arg0)
                {
                    updateState("waiting for connection");
                    buttonConnect.setEnabled(true);
                    buttonRetry.setEnabled(false);
                    buttonNext.setEnabled(false);
                    editTextPort.getText().clear();
                    editTextPort.setText("192.168.000.000", TextView.BufferType.EDITABLE);
                    editTextPort.setText("10000", TextView.BufferType.EDITABLE);
                }
            };

    View.OnClickListener buttonConnectOnClickListener =
            new View.OnClickListener()
            {
                @Override
                public void onClick(View arg0)
                {
                    updateState("CONNECTING...");
                    buttonConnect.setEnabled(false);

                    udpClient = new UDPClient(
                            editTextAddress.getText().toString(),
                            Integer.parseInt(editTextPort.getText().toString()));
                    int state = udpClient.initConnection();
                    buttonRetry.setEnabled(true);
                    if(state == 1)
                    {
                        updateState("CONNECTED!");
                        buttonNext.setEnabled(true);
                    }
                    else
                    {
                        updateState("NOT CONNECTED");
                        if (state == 0)
                        {
                            updateInfo("wrong server");
                        }
                        else
                        {
                            updateInfo("caught an exception");
                        }
                    }
                }
            };

    public void updateState(String state)
    {
        textViewState.setText(state);
    }

    public void updateInfo(String additionalInfo)
    {
        textViewRx.setText(additionalInfo);
    }

}
