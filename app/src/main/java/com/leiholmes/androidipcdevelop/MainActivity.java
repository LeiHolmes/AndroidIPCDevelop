package com.leiholmes.androidipcdevelop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.leiholmes.androidipcdevelop.aidl.AIDLActivity;
import com.leiholmes.androidipcdevelop.binderpool.BinderPoolActivity;
import com.leiholmes.androidipcdevelop.contentprovider.ProviderActivity;
import com.leiholmes.androidipcdevelop.messenger.MessengerActivity;
import com.leiholmes.androidipcdevelop.socket.TcpClientActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:   入口
 * author         xulei
 * Date           2018/6/8 12:05
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_messenger, R.id.btn_aidl, R.id.btn_provider, R.id.btn_socket, R.id.btn_binder_pool})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_messenger:
                startActivity(new Intent(this, MessengerActivity.class));
                break;
            case R.id.btn_aidl:
                startActivity(new Intent(this, AIDLActivity.class));
                break;
            case R.id.btn_provider:
                startActivity(new Intent(this, ProviderActivity.class));
                break;
            case R.id.btn_socket:
                startActivity(new Intent(this, TcpClientActivity.class));
                break;
            case R.id.btn_binder_pool:
                startActivity(new Intent(this, BinderPoolActivity.class));
                break;
        }
    }
}
