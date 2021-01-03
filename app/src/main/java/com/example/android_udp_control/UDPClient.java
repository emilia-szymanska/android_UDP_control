package com.example.android_udp_control;

import java.net.*;


public class UDPClient
{
    String hostname;
    int port;
    DatagramSocket socket;
    InetAddress address;

    public UDPClient(String addr, int prt)
    {
        super();
        hostname = addr;
        port = prt;
    }

    // 1 - correct connection; 0 - wrong server, -1 - exception
    public int initConnection()
    {
        String msg = "Hello UDP server";
        String rxCorrectMsg = "OK";

        //TODO: what to do if a port is incorrecct? - it doesn't catch an exception then

        try
        {
            address = InetAddress.getByName(hostname);
            socket = new DatagramSocket();

            byte[] buf = msg.getBytes();
            DatagramPacket request = new DatagramPacket(buf, buf.length, address, port);
            socket.send(request);

            System.out.println("wyslal wiadomosc");

            byte[] buffer = new byte[512];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);

            System.out.println("dostal wiadomosc");

            String rxMsg = new String(buffer, 0, response.getLength());

            if (rxMsg.equals(rxCorrectMsg))
                return 1;
            else
                return 0;
        }
        catch (Exception ex)
        {
            System.out.println("Caught an exception!");
            ex.printStackTrace();
            return -1;
        }
    }

    public void sendCommand(String msg)
    {
        try
        {
            address = InetAddress.getByName(hostname);
            socket = new DatagramSocket();

            byte[] buf = msg.getBytes();
            DatagramPacket request = new DatagramPacket(buf, buf.length, address, port);
            socket.send(request);
        }
        catch (Exception ex)
        {
            System.out.println("Caught an exception!");
            ex.printStackTrace();
        }
    }

}
