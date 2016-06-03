package com.matthijs.rtpandroid;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Multithreaded Server
 * For each incoming TCP request
 * a new ServerThread is created upon accepting the request
 *
 * Created by Matthijs Overboom on 12-5-16.
 */
public class Server {
    private Socket RTSPsocket; //socket used to send/receive RTSP messages
    private Context context;
    private static Server server;
    public static int RTPS_PORT = 5568;

    private Server(Context context){
        this.context = context;
        runServer();
    }

    /**
     * Get Server singleton object
     * @param context Context
     * @return Server singleton object
     */
    public static Server getNewInstance(Context context) {
        if(server == null) {
            server = new Server(context);
        }
        return server;
    }

    /**
     * Listens for incoming requests to setup and RTSP (via TCP) connection
     * Once a request is accepted, a new ServerThread is created and the RTSP conenction is passed to it.
     */
    private void runServer() {
        new Thread() {
            @Override
            public void run() {
                try {
                    ServerSocket listenSocket = new ServerSocket(RTPS_PORT);

                    while(true) {
                        RTSPsocket = listenSocket.accept();
                        new ServerThread(RTSPsocket, context).start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}



