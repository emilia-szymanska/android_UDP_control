package com.example.android_udp_control;

import android.os.Message;
import android.widget.Toast;
import java.io.*;
import java.net.*;

import android.os.Bundle;
/*

import android.os.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;*/

public class UDPClient extends Thread
{
    String dstAddress;
    int dstPort;
    DatagramSocket socket;
    InetAddress address;
    MainActivity.UdpClientHandler handler;

    public UDPClient(String addr, int port, MainActivity.UdpClientHandler handler)
    {
        super();
        dstAddress = addr;
        dstPort = port;
        this.handler = handler;
    }

    private void sendState(String state)
    {
        handler.sendMessage(
                Message.obtain(handler,
                        MainActivity.UdpClientHandler.UPDATE_STATE, state));
    }

    @Override
    public void run()
    {
        sendState("connecting...");

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

            String quote = new String(buffer, 0, response.getLength());

            sendState(quote);

        }
        catch (IOException ex)
        {
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }





}
