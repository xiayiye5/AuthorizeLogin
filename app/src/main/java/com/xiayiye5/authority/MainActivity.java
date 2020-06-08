package com.xiayiye5.authority;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button mBt;
    /**
     * cn.net.gfan.world 被调起应用包名  type为类型 1 授权登陆    user_package_name:使用者包名
     */
    private String mUri = "yhsh://cn.net.gfan.world/sign?type=1&user_package_name=com.xiayiye5.authority";

    private MyBroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //操作1 按钮发起
        mBt = findViewById(R.id.bt);
        mBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setData(Uri.parse(mUri));//参数拼接在URI后面 type=1是授权页面,user_package_name使用者包名,后续参数可自行添加
                intent.putExtra("xiayiye5", "下一页5");//这里Intent也可传递参数,但是一般情况下都会放到上面的URL中进行传递
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //操作2
        //注册广播接受者,接收授权成功返回广播信息
        mReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        //yhsh.com.yhsh.b.sign 自行定义action 即可,定义为被唤起的app包名
        intentFilter.addAction("yhsh.cn.net.gfan.world.sign");
        registerReceiver(mReceiver, intentFilter);
    }


    /**
     * 操作3
     * BroadcastReceiver 接收授权成功返回广播信息
     */
    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 授权成功  返回token,app_pkg
            Toast.makeText(MainActivity.this, "授权成功!", Toast.LENGTH_SHORT).show();
            final String token = intent.getStringExtra("token");
            final String app_pkg = intent.getStringExtra("app_pkg");
            mBt.setText("token=" + token + "\n" + "app_pkg=" + app_pkg);

            //todo 调起方登录操作 做你需要的需求
        }
    }

    /**
     * 别忘了回收广播  不然会报error,这里是一个内存回收的知识点,第二个有时间博主会补上的知识点
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
