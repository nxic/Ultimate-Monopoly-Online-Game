package com.nullPointer.Domain.Server;

import com.nullPointer.Domain.Controller.CommunicationController;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread {
    private Thread thread;
    private String threadName;
    private Socket socket;
    private String hostName = "localhost";
    private int portNumber = 4000;
    private ObjectOutputStream outObject;
    private ObjectInputStream inObject;
    private CommunicationController communicationController = CommunicationController.getInstance();
    private ServerInfo serverInfo = ServerInfo.getInstance();

    public Client(String name) {
        threadName = name;
    }

    public Client(String IP, String name) {
        threadName = name;
        hostName = IP; //127.0.0.1
    }

    @Override
    public void run() {
        //args[0];
        try {
            socket = new Socket(hostName, portNumber);
            System.out.println("Client connected to IP: " + socket.getInetAddress() + ". Port: " + socket.getLocalPort());
            System.out.println("Client created with IP: " + InetAddress.getLocalHost().getHostAddress());


            outObject = new ObjectOutputStream(socket.getOutputStream());

            inObject = new ObjectInputStream(socket.getInputStream());

            Object fromServer;

            System.out.println("[Client]: Listening!");
            serverInfo.setClientID(InetAddress.getLocalHost().getHostAddress() + ":" + socket.getLocalPort());
            while (true) {
                if ((fromServer = inObject.readObject()) != null) {
                    System.out.println("[Client]: Server -> " + fromServer);
                    System.out.println(fromServer.getClass());

                    communicationController.processInput(fromServer);
                }
            }
        } catch (UnknownHostException er) {
            System.err.println("[Client]: Unknown host " + hostName);
            System.exit(1);
        } catch (IOException er) {
            System.err.println("[Client]: Couldn't get I/O for the connection to " +
                    hostName + ". Error : " + er);
            //System.exit(1);
            communicationController.startServer();
            hostName = "localhost";
            this.run();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            System.out.println("[Client]: Client is not listening anymore");

        }
    }

    public void sendMessage(Object msg) {
        try {
            outObject.writeObject(msg);
            outObject.reset();
        } catch (Exception e) {
            System.out.println("[Client]: Error during sendMessage" + e);
        }
    }

    @Override
    public void start() {
        System.out.println("[Client]: Thread created with name " + threadName);
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }
}
