package com.gopher.medicalwaste;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


/**
 * Created by Administrator on 2017/10/14.
 */

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ResultActivity.class.getSimpleName();
    private EditText bet0;
    private EditText bet1;
    private EditText bet2;
    private EditText bet3;
    private EditText bet4;
    private EditText bet5;
    private EditText bet6;
    private EditText bet7;
    private EditText bet8;
    private EditText bet9;
    private EditText bet10;
    private EditText bet11;
    private EditText bet12;
    private Button btn_back;
    private String barcode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().addActivity(this);
        setContentView(R.layout.activity_result);
        bet0 = (EditText) findViewById(R.id.bet_0);
        bet1 = (EditText) findViewById(R.id.bet_1);
        bet2 = (EditText) findViewById(R.id.bet_2);
        bet3 = (EditText) findViewById(R.id.bet_3);
        bet4 = (EditText) findViewById(R.id.bet_4);
        bet5 = (EditText) findViewById(R.id.bet_5);
        bet6 = (EditText) findViewById(R.id.bet_6);
        bet7 = (EditText) findViewById(R.id.bet_7);
        bet8 = (EditText) findViewById(R.id.bet_8);
        bet9 = (EditText) findViewById(R.id.bet_9);
        bet10 = (EditText) findViewById(R.id.bet_10);
        bet11 = (EditText) findViewById(R.id.bet_11);
        bet12 = (EditText) findViewById(R.id.bet_12);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        barcode = intent.getStringExtra("barcode");
        if (TextUtils.isEmpty(barcode)) {
            return;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!NetHelper.IsHaveInternet(this)) {
            ToastUtils.showCenter(this, "请连接网络后再重试！");
            return;
        }
        ProgressUtil.show(this, "查询中...");
//        getData(barcode);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

//        if (singleDoublecheck(this)) {
//            ProgressUtil.cancel();
//            ToastUtils.showCenter(this, "服务器异常了，请稍后重试！");
//            return;
//        }
        getQueryData(barcode);
    }

    private boolean singleDoublecheck(ResultActivity resultActivity) {
        Random random = new Random();
        int i = random.nextInt(10);
        if (i % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }

    private void getQueryData(String barcode) {
        try {
            String nowIP = PreferenceUtil.getString(Constans.IP_KEY, null);
            String nowPORT = PreferenceUtil.getString(Constans.PORT_KEY, null);

            if (nowIP == null || nowPORT == null) {
                return;
            }

            String queryURL = "http://" +
                    nowIP +
                    ":" +
                    nowPORT +
                    "/yf/rest/dataInterface/getTransferRecord.rest?labelid=" + barcode;
            OkHttpUtils.get().url(queryURL).build().execute(new StringCallback() {
                @Override
                public void onError(okhttp3.Call call, Exception e, int i) {
                    Log.e(TAG, e.toString());
                    ProgressUtil.cancel();
                    ToastUtils.showCenter(ResultActivity.this, "系统异常了！");
                }

                @Override
                public void onResponse(String s, int i) {
                    ProgressUtil.cancel();
                    if (s.equals("null")) {
                        ToastUtils.showCenter(ResultActivity.this, "没有此条码记录，请稍后再重试！");
                    } else {
                        TransferRecord result = new Gson().fromJson(s, TransferRecord.class);
                        refreshUI(result);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showCenter(this, "请稍后重试！");
        }


    }

//    private void getData(String code) {
//        Net build = new Net.Builder(this).code(code).build();
//        build.getData(new Net.OnCallback() {
//            @Override
//            public void onSuccess(Object value) {
//                TransferRecordResult transferRecordResult = (TransferRecordResult) value;
//                refreshUI(transferRecordResult);
//            }
//
//            @Override
//            public void onFailed(Throwable e) {
//                ProgressUtil.cancel();
//                ToastUtils.showCenter(ResultActivity.this, "服务器出错了！");
//            }
//
//            @Override
//            public void onFinally() {
//                ProgressUtil.cancel();
//            }
//        });
//    }

    private void refreshUI(TransferRecord transferRecordResult) {
        bet0.setText(transferRecordResult.getHosName());
        bet1.setText(transferRecordResult.getHandoverdep());
        bet2.setText(transferRecordResult.getHandoverman());
        bet3.setText(transferRecordResult.getHandovermanname());
        bet4.setText(transferRecordResult.getOperatordep());
        bet5.setText(transferRecordResult.getOperatorman());
        bet6.setText(transferRecordResult.getOperatormanname());
        bet7.setText(transferRecordResult.getStaName());
        bet8.setText(transferRecordResult.getStatus());
        bet9.setText(String.valueOf(transferRecordResult.getTypeId()));
        bet10.setText(transferRecordResult.getTypename());
        bet11.setText(transferRecordResult.getWeight());
        bet12.setText(millisecond2Date(transferRecordResult.getDataTime()));
    }

    private String millisecond2Date(long millisecond) {
        Date date;
        SimpleDateFormat format;
        try {
            date = new Date(millisecond);
            format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss a");
//            format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss SSS a");
        } catch (Exception e) {
            return "0";
        }
        return format.format(date);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        release();
    }

    private void release() {
        this.setResult(Activity.RESULT_OK);
        App.getInstance().finishActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                App.getInstance().finishActivity(this);
                break;
        }
    }
}
