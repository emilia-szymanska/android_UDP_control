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
    Button buttonConnect, buttonNext;
    TextView textViewState, textViewRx;
    UDPClient udpClient;

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
        buttonConnect = (Button) findViewById(R.id.connect);

        buttonNext.setEnabled(false);

        buttonNext.setOnClickListener(buttonNextOnClickListener);
        buttonConnect.setOnClickListener(buttonConnectOnClickListener);

    }

    View.OnClickListener buttonNextOnClickListener =
            new View.OnClickListener()
            {
                @Override
                public void onClick(View arg0)
                {
                    Intent intent = new Intent(MainActivity.this, ArrowActivity.class);
                    startActivity(intent);
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
                    boolean state = udpClient.run();
                    if (state == true)
                    {
                        updateState("CONNECTED!");
                        buttonNext.setEnabled(true);
                    }
                    else
                    {
                        updateState("NOT CONNECTED!");
                        buttonConnect.setEnabled(true);
                    }
                }
            };

    public void updateState(String state)
    {
        textViewState.setText(state);
    }

}
