package com.leiholmes.androidipcdevelop.socket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.leiholmes.androidipcdevelop.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:   客户端
 * author         xulei
 * Date           2018/7/2 17:31
 */
public class TcpClientActivity extends AppCompatActivity {
    private static final String TAG = "TestTCPClientActivity";
    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;

    @BindView(R.id.tv_message_container)
    TextView tvMessageContainer;
    @BindView(R.id.et_send_message)
    EditText etSendMessage;
    @BindView(R.id.btn_send_message)
    Button btnSendMessage;

    private PrintWriter mPrintWriter;
    private Socket mClientSocket;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_RECEIVE_NEW_MSG:
                    tvMessageContainer.setText(tvMessageContainer.getText() + msg.obj.toString());
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    btnSendMessage.setEnabled(true);
                    break;

            }
        }
    };
 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_client);
        ButterKnife.bind(this);
        //开启服务
        Intent serviceIntent = new Intent(this, TcpServerService.class);
        startService(serviceIntent);
    }

    /**
     * 连接服务端
     */
    private void connectTcpServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("localhost", 8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                Log.e(TAG, "Client---Connect server success!");
            } catch (IOException e) {
                e.printStackTrace();
                SystemClock.sleep(1000);
                Log.e(TAG, "Client---Connect server failed! Retry...");
            }

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (!TcpClientActivity.this.isFinishing()) {
                    String serverResponse = br.readLine();
                    Log.e(TAG, "Client---Receive server msg: " + serverResponse);
                    if (serverResponse != null) {
                        String time = formatDateTime();
                        final String showedMsg = "server " + time + ":" + serverResponse + "\n";
                        mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, showedMsg).sendToTarget();
                    }
                }
                Log.e(TAG, "Client---Quit...");
                mPrintWriter.close();
                br.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @OnClick({R.id.btn_connect, R.id.btn_send_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_connect: //连接
                //开子线程连接TCP服务
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        connectTcpServer();
                    }
                }).start();
                break;
            case R.id.btn_send_message: //发送
                final String sendMsg = etSendMessage.getText().toString();
                if (!TextUtils.isEmpty(sendMsg) && mPrintWriter != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mPrintWriter.println(sendMsg);
                        }
                    }).start();
                    etSendMessage.setText("");
                    String time = formatDateTime();
                    final String showedMsg = "client " + time + ":" + sendMsg + "\n";
                    tvMessageContainer.setText(tvMessageContainer.getText() + showedMsg);
                }
                break;
        }
    }

    @SuppressLint("SimpleDateFormat")
    private String formatDateTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()));
    }

    @Override
    protected void onDestroy() {
        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}
