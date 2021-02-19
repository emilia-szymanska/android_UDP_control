package com.example.android_udp_control;

public class UpdatePositionDesiredRunnable implements Runnable
{
    private String xPos;
    private String yPos;
    private String thetaPos;

    public UpdatePositionDesiredRunnable(String x, String y, String theta)
    {
        this.xPos = x;
        this.yPos = y;
        this.thetaPos = theta + "\u00B0" ;
    }

    public void run()
    {
        PoseCommandActivity.updatePosition(xPos, yPos, thetaPos);
    }
}
