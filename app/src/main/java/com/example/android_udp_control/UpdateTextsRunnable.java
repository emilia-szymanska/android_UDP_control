package com.example.android_udp_control;

public class UpdateTextsRunnable implements Runnable
{
    private String mainState;
    private String mainInfo;

    public UpdateTextsRunnable(String state, String info)
    {
        this.mainState = state;
        this.mainInfo = info;
    }

    public void run()
    {
        MainActivity.updateState(mainState);
        MainActivity.updateInfo(mainInfo);
    }
}

