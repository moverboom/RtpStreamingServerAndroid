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
 * Created by matthijs on 12-5-16.
 */
public class Server {
    private Socket RTSPsocket; //socket used to send/receive RTSP messages
    private Context context;
    private static Server server;

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

    private void runServer() {
        new Thread() {
            @Override
            public void run() {
                try {
                    int RTSPport = 1234;
                    ServerSocket listenSocket = new ServerSocket(RTSPport);

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



