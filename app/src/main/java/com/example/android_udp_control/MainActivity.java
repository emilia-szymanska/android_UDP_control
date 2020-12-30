package com.example.android_udp_control;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.content.Intent;



public class MainActivity extends AppCompatActivity {

    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonNext;
    TextView textViewState, textViewRx;
    UdpClientHandler udpClientHandler;
    UDPClient udpClientThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort = (EditText) findViewById(R.id.port);
        textViewState = (TextView) findViewById(R.id.state);
        textViewRx = (TextView) findViewById(R.id.received);
        buttonNext = (Button) findViewById(R.id.next);
        buttonConnect = (Button) findViewById(R.id.connect);

        //    Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        //    Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        buttonNext.setOnClickListener(buttonNextOnClickListener);
        buttonConnect.setOnClickListener(buttonConnectOnClickListener);

        udpClientHandler = new UdpClientHandler(this);
    }
    View.OnClickListener buttonNextOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(MainActivity.this, ArrowActivity.class);
                    startActivity(intent);
                }
            }
            ;

    View.OnClickListener buttonConnectOnClickListener =
            new View.OnClickListener()
            {
                @Override
                public void onClick(View arg0)
                {
                    udpClientThread = new UDPClient(
                            editTextAddress.getText().toString(),
                            Integer.parseInt(editTextPort.getText().toString()),
                            udpClientHandler);
                    udpClientThread.run();

                    buttonConnect.setEnabled(false);
                }
            }
            ;

    private void updateState(String state)
    {
        textViewState.setText(state);
    }

    private void updateRxMsg(String rxmsg)
    {
        textViewRx.append(rxmsg + "\n");
    }

    private void clientEnd()
    {
        udpClientThread = null;
        textViewState.setText("clientEnd()");
        buttonConnect.setEnabled(true);

    }

    public static class UdpClientHandler extends Handler
    {
        public static final int UPDATE_STATE = 0;
        public static final int UPDATE_MSG = 1;
        public static final int UPDATE_END = 2;
        private MainActivity parent;

        public UdpClientHandler(MainActivity parent) {
            super();
            this.parent = parent;
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case UPDATE_STATE:
                    parent.updateState((String)msg.obj);
                    break;
                case UPDATE_MSG:
                    parent.updateRxMsg((String)msg.obj);
                    break;
                case UPDATE_END:
                    parent.clientEnd();
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }
}
