package com.example.android_udp_control;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class ArrowActivity extends AppCompatActivity
{
    private ImageButton up;
    private ImageButton down;
    private ImageButton left;
    private ImageButton right;
    private ImageButton stop;
    private ImageButton previous;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.arrow_view);

        up = findViewById(R.id.uparrow);
        down = findViewById(R.id.downarrow);
        left = findViewById(R.id.leftarrow);
        right = findViewById(R.id.rightarrow);
        stop = findViewById(R.id.stop);
        previous = findViewById(R.id.previous);


        up.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(ArrowActivity.this, "Up arrow pressed", Toast.LENGTH_SHORT).show();
            }
        });


        down.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(ArrowActivity.this, "Down arrow pressed", Toast.LENGTH_SHORT).show();
            }
        });


        left.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(ArrowActivity.this, "Left arrow pressed", Toast.LENGTH_SHORT).show();
            }
        });


        right.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(ArrowActivity.this, "Right arrow pressed", Toast.LENGTH_SHORT).show();
            }
        });


        stop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(ArrowActivity.this, "Stop pressed", Toast.LENGTH_SHORT).show();
            }
        });


        previous.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent changeToMain = new Intent(ArrowActivity.this, MainActivity.class);
                startActivity(changeToMain);
            }
        });
    }
}
