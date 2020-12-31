package com.example.android_udp_control;

import android.os.Message;
import android.widget.Toast;
import java.io.*;
import java.net.*;

import android.os.Bundle;


public class UDPClient
{
    String dstAddress;
    int dstPort;
    DatagramSocket socket;
    InetAddress address;

    public UDPClient(String addr, int port)
    {
        super();
        dstAddress = addr;
        dstPort = port;
    }

    public boolean run()
    {
        String hostname = dstAddress;
        int port = dstPort;

        try
        {
            address = InetAddress.getByName(hostname);
            socket = new DatagramSocket();

            String msg = "Hejka";
            byte[] buf = msg.getBytes();
            DatagramPacket request = new DatagramPacket(buf, buf.length, address, port);
            socket.send(request);

            byte[] buffer = new byte[512];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);

            String rxMsg = new String(buffer, 0, response.getLength());

            if (rxMsg.equals("ok"))
                return true;
            else
                return false;

        }
        catch (IOException ex)
        {
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

}
