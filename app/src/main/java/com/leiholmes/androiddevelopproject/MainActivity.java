package com.leiholmes.androiddevelopproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.leiholmes.androiddevelopproject.aidl.AIDLActivity;
import com.leiholmes.androiddevelopproject.messenger.MessengerActivity;

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

    @OnClick({R.id.btn_messenger, R.id.btn_aidl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_messenger:
                startActivity(new Intent(this, MessengerActivity.class));
                break;
            case R.id.btn_aidl:
                startActivity(new Intent(this, AIDLActivity.class));
                break;
        }
    }
}
