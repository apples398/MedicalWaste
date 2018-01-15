package com.gopher.medicalwaste;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2017/12/8.
 */

public class SettingActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = SettingActivity.class.getSimpleName();
    EditText et_download_port, et_download_ip;
    Button btnSubmit;

    private static final String[] READ_AND_WRITE =
            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int RC_READ_AND_WRITE_PERM = 124;

    private boolean hasReadPermission() {
        return EasyPermissions.hasPermissions(this, READ_AND_WRITE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().addActivity(this);
        setContentView(R.layout.activity_setting);

        et_download_ip = (EditText) findViewById(R.id.et_download_ip);
        et_download_port = (EditText) findViewById(R.id.et_down_point);
        try {
            String ip = PreferenceUtil.getString(Constans.IP_KEY, null);
            String port = PreferenceUtil.getString(Constans.PORT_KEY, null);
            Log.e("ip",ip+":"+port);
            et_download_ip.setText(ip);
            et_download_port.setText(port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    @AfterPermissionGranted(RC_READ_AND_WRITE_PERM)
    private void submit() {
        if (!hasReadPermission()) {
            String saveIP = et_download_ip.getText().toString();
            String savePort = et_download_port.getText().toString();
            if (saveIP.equals("") || savePort.equals("")) {
                Toast.makeText(SettingActivity.this, "服务器IP或端口不能为空", Toast.LENGTH_SHORT).show();
            } else {
                PreferenceUtil.remove(Constans.IP_KEY, Constans.PORT_KEY);
                PreferenceUtil.put(Constans.IP_KEY, saveIP);
                PreferenceUtil.put(Constans.PORT_KEY, savePort);

                Toast.makeText(SettingActivity.this, "输入成功，即将退回主界面...", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        } else {
//            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.read_write),
                    RC_READ_AND_WRITE_PERM,
                    READ_AND_WRITE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        App.getInstance().finishActivity(this);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            String yes = getString(R.string.yes);
            String no = getString(R.string.no);

            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(
                    this,
                    getString(R.string.returned_from_app_settings_to_activity,
                            hasReadPermission() ? yes : no),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
        // ...
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, list)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
