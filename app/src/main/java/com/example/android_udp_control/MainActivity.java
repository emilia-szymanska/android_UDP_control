package com.example.android_udp_control;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Resources;
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
    static TextView textViewState, textViewRx;
    public UDPClient udpClient;
    Thread udpThread;
    String address;
    int port, state;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        if (width >= 1080)
           setContentView(R.layout.activity_main_fullhd);
        else
           setContentView(R.layout.activity_main);

        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort    = (EditText) findViewById(R.id.port);
        textViewState   = (TextView) findViewById(R.id.state);
        textViewRx      = (TextView) findViewById(R.id.received);
        buttonNext      = (Button) findViewById(R.id.next);
        buttonRetry     = (Button) findViewById(R.id.retry);
        buttonConnect   = (Button) findViewById(R.id.connect);

        buttonNext.setEnabled(false);
        buttonRetry.setEnabled(false);


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
                if (udpClient != null)
                    udpClient.closeSocket();
                if (udpThread.isAlive())
                    udpThread.interrupt();
                buttonConnect.setEnabled(true);
                buttonRetry.setEnabled(false);
                buttonNext.setEnabled(false);
                editTextPort.getText().clear();
                editTextPort.setText("192.168.", TextView.BufferType.EDITABLE);
                editTextPort.setText("20001", TextView.BufferType.EDITABLE);
                updateTexts("waiting for connection", " ");
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

                udpThread = new Thread()
                {
                    public void run()
                    {
                        try
                        {
                            address = editTextAddress.getText().toString();
                            port = Integer.parseInt(editTextPort.getText().toString());

                            udpClient = new UDPClient(address, port);
                            state = udpClient.initConnection();

                            switch (state)
                            {
                                case 1:
                                    runOnUiThread(new UpdateTextsRunnable("CONNECTED", " "));
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            buttonNext.setEnabled(true);
                                        }
                                    });
                                    break;
                                case 0:
                                    runOnUiThread(new UpdateTextsRunnable("NOT CONNECTED", "wrong server"));
                                    break;
                                case -2:
                                    runOnUiThread(new UpdateTextsRunnable("NOT CONNECTED", "timeout error"));
                                    break;
                                case -3:
                                    runOnUiThread(new UpdateTextsRunnable("NOT CONNECTED", "disturbed socket"));
                                    break;
                                case -4:
                                    runOnUiThread(new UpdateTextsRunnable("NOT CONNECTED", "wrong input values"));
                                    break;
                                default:
                                    runOnUiThread(new UpdateTextsRunnable("NOT CONNECTED", "caught an exception"));
                                    break;
                            }
                        }
                        catch (Exception ex)
                        {
                            runOnUiThread(new UpdateTextsRunnable("NOT CONNECTED", "wrong input values"));
                        }
                    }
                };
                udpThread.start();

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
