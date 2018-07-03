package com.leiholmes.androidipcdevelop.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Description:   服务端
 * author         xulei
 * Date           2018/7/2 16:57
 */
public class TcpServerService extends Service {
    private static final String TAG = "TestTCPServerService";

    private boolean mIsServiceDestoryed = false;
    private String[] mServiceResoponseMessage = {
            "Hello，this is server service!",
            "What's your name?",
            "It's a nice day, isn't is?",
            "I'm glad to talk with you!",
            "See you!"};

    public TcpServerService() {
    }

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed = true;
        super.onDestroy();
    }

    private class TcpServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                //监听本地8688端口
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                Log.e(TAG, "Server---Tcp server failed, port: 8688");
                e.printStackTrace();
                return;
            }
            //接收客户端请求
            while (!mIsServiceDestoryed) {
                try {
                    //接收客户端Socket
                    final Socket client = serverSocket.accept();
                    Log.e(TAG, "Server---Accept client socket");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 接收客户端消息并回应
     */
    private void responseClient(Socket client) throws IOException {
        //用于接收客户端消息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //用于给客户端发送消息
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
        out.println("Welcome to chatroom!");
        while (!mIsServiceDestoryed) {
            String clientMessage = in.readLine();
            Log.e(TAG, "Server---Msg from client: " + clientMessage);
            if (clientMessage == null) {
                break;
            }
            String serverResponse = mServiceResoponseMessage[new Random().nextInt(mServiceResoponseMessage.length)];
            out.println(serverResponse);
            Log.e(TAG, "Server---Server response msg: " + serverResponse);
        }
        Log.e(TAG, "Server---Client quit");
        out.close();
        in.close();
        client.close();
    }
}
