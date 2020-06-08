package cn.net.gfan.world;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author xiayiye5
 * 2020年6月8日09:23:17
 */
public class PermissionActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        Button btConfirmLogin = findViewById(R.id.bt_confirm_login);
        btConfirmLogin.setOnClickListener(this);
        getData();
    }

    /**
     * 获取SDK传递过来的参数
     */
    private void getData() {
        //获取传递的数据
        Intent intent = getIntent();
        Uri uri = intent.getData();

        //获取参数值
        String type = uri.getQueryParameter("type");
        String userPackageName = uri.getQueryParameter("user_package_name");
    }

    @Override
    public void onClick(View v) {
        //授权登录发送广播传递参数关闭当前APP
        Intent intent1 = new Intent();
        //此处为被唤起的app包名也可以自定义action但是必须得跟要接受广播的APP的action一致
        intent1.setAction("yhsh.cn.net.gfan.world.sign");
        intent1.putExtra("token", "dhuhguidhue-3029r3092");
        intent1.putExtra("app_pkg", "com.vistateach.homework");
        sendBroadcast(intent1);
        finish();
    }
}
