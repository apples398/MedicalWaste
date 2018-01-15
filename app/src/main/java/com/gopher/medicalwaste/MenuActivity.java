package com.gopher.medicalwaste;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/12/8.
 */

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_menu);
        findViewById(R.id.btn_item1).setOnClickListener(this);
        findViewById(R.id.btn_item2).setOnClickListener(this);
        findViewById(R.id.btn_item3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, SettingActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        switch (v.getId()) {
            case R.id.btn_item1:
                intent.putExtra(Constans.ITEM, Constans.ITEM_1);
                break;
            case R.id.btn_item2:
                intent.putExtra(Constans.ITEM, Constans.ITEM_2);
                break;
        }
        startActivity(intent);
    }
}
