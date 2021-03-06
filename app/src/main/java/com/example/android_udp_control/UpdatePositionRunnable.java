package com.example.android_udp_control;

public class UpdatePositionRunnable implements Runnable
{
    private String xPos;
    private String yPos;
    private String thetaPos;

    public UpdatePositionRunnable(String x, String y, String theta)
    {
        this.xPos = x;
        this.yPos = y;
        this.thetaPos = theta + "\u00B0" ;
    }

    public void run()
    {
        ArrowActivity.updatePosition(xPos, yPos, thetaPos);
    }
}
