package com.example.android_udp_control;

import java.io.IOException;
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

    // 1 - correct connection; 0 - wrong server, -1 - exception, -2 - timeout, -3 - failed while waiting for a message, -4 - wrong values
    public int initConnection()
    {
        String msg = "Hello UDP server";
        String rxCorrectMsg = "OK";

        try
        {
            address = InetAddress.getByName(hostname);
            socket = new DatagramSocket();

            byte[] buf = msg.getBytes();
            DatagramPacket request = new DatagramPacket(buf, buf.length, address, port);
            socket.send(request);

            System.out.println("wyslal wiadomosc");

            socket.setSoTimeout(5000);

            byte[] buffer = new byte[512];
            try
            {
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                socket.receive(response);
                String rxMsg = new String(buffer, 0, response.getLength());

                if (rxMsg.equals(rxCorrectMsg))
                    return 1;
                else
                    return 0;
            }
            catch (SocketTimeoutException ex)
            {
                System.out.println("Timeout reached!!! " + ex);
                socket.close();
                return -2;
            }
            catch (SocketException ex)
            {
                System.out.println("Socket exception ");
                return -3;
            }
        }
        catch(IOException ex)
        {
            System.out.println("Input exception");
            return -4;
        }
        catch (Exception ex)
        {
            System.out.println("Caught an exception!");
            ex.printStackTrace();
            socket.close();
            return -1;
        }
    }

    public void closeSocket()
    {
        if (!socket.isClosed())
            socket.close();
    }

    public void setSocket()
    {
        try
        {
            address = InetAddress.getByName(hostname);
            socket = new DatagramSocket();
        }
        catch (Exception ex)
        {
            System.out.println("Caught an exception!");
            ex.printStackTrace();
        }
    }

    public void sendCommand(String msg)
    {
        try
        {
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
