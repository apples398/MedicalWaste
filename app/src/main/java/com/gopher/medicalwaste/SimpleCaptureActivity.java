package com.gopher.medicalwaste;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import io.github.xudaojie.qrcodelib.CaptureActivity;

/**
 * Created by Administrator on 2017/11/13.
 */

public class SimpleCaptureActivity extends CaptureActivity {
    protected Activity mActivity = this;

//    private AlertDialog mDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().addActivity(this);
    }

    @Override
    public void onBackPressed() {
        App.getInstance().finishActivity(this);
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        App.getInstance().finishActivity(this);
        super.onDestroy();
    }


    @Override
    protected void handleResult(String resultString) {
        if (TextUtils.isEmpty(resultString)) {
            Toast.makeText(mActivity, io.github.xudaojie.qrcodelib.R.string.scan_failed, Toast.LENGTH_SHORT).show();
            restartPreview();
        } else {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("codedContent", resultString);
            intent.putExtras(bundle);
            this.setResult(Activity.RESULT_OK, intent);
            this.finish();
//            if (mDialog == null) {
//                mDialog = new AlertDialog.Builder(mActivity)
//                        .setMessage(resultString)
//                        .setPositiveButton("确定", null)
//                        .create();
//                mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        restartPreview();
//                    }
//                });
//            }
//            if (!mDialog.isShowing()) {
//                mDialog.setMessage(resultString);
//                mDialog.show();
//            }
        }
    }
}
