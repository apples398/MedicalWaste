package com.gopher.medicalwaste;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    public final static int SCANNING_REQUEST_CODE = 6969;

    public final static int ACT_REQUEST_CODE = 711;

    private EditText et_barcode;

    private TextView tv_result;
    public ImageButton ib_scan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        App.getInstance().addActivity(this);
        setContentView(R.layout.activity_main);
        et_barcode = (EditText) findViewById(R.id.et_barcode);
        tv_result = (TextView) findViewById(R.id.tv_result);
        ib_scan = (ImageButton) findViewById(R.id.ib_scan);
        ib_scan.setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);
        Intent intent = getIntent();
        if (intent != null) {
            String extra = intent.getStringExtra(Constans.ITEM);
            Log.e(TAG, "extra = " + extra);
            if (TextUtils.isEmpty(extra)) {
                return;
            }
            if (extra.equals(Constans.ITEM_1)) {

            }
            if (extra.equals(Constans.ITEM_2)) {
                et_barcode.setHint("请输入条码！");
                ib_scan.setVisibility(View.GONE);

            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_scan:
                launchCamera();
                break;
            case R.id.btn_query:
                String code = et_barcode.getText().toString().trim();
                Log.i(TAG, "code = " + code);
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.showCenter(this, "请扫码！");
                    return;
                }
                if (14 != code.length()) {
                    ToastUtils.showCenter(this, "条码不规范！");
                    return;
                }
                int index1 = Integer.valueOf(code.substring(0, 1));
                int index2 = Integer.valueOf(code.substring(1, 2));
                if (index1 < 9 && index1 > 0 && index1 == index2) {
                    Intent intent = new Intent(this, ResultActivity.class);
                    intent.putExtra("barcode", code);
                    startActivityForResult(intent, ACT_REQUEST_CODE);
                } else {
                    ToastUtils.showCenter(this, "条码不规范！");
                    return;
                }
                break;
        }
    }

    /**
     * Launch the camera
     */
    private void launchCamera() {
        try {
            Intent intent = new Intent(this, SimpleCaptureActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, SCANNING_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNING_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    if (null != bundle) {
                        String barcode = bundle.getString("codedContent");
                        if (TextUtils.isEmpty(barcode)) {
                            ToastUtils.showCenter(this, "请重新扫码！");
                            return;
                        }
                        et_barcode.setText(barcode);
                    }
                }
                break;
            case ACT_REQUEST_CODE:
                et_barcode.setText("");
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().finishActivity(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        App.getInstance().finishActivity(this);
    }
}
