package com.example.android_udp_control;

public class UpdatePositionRunnable implements Runnable
{
    private String x_pos;
    private String y_pos;
    private String theta_pos;

    public UpdatePositionRunnable(String x, String y, String theta)
    {
        this.x_pos = x;
        this.y_pos = y;
        this.theta_pos = theta + "\u00B0" ;
    }

    public void run()
    {
        ArrowActivity.updatePosition(x_pos, y_pos, theta_pos);
    }
}
