package com.gopher.medicalwaste;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/10/13.
 */

public class ProgressUtil {
    private static Dialog progressDialog = null;
    private static OnKeyListener mListener = null;

    private ProgressUtil() {
    }

    public static void show(Context context, String message) {
        if (progressDialog == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.dialog_loading, null);//
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view);

            TextView tipTextView = (TextView) view.findViewById(R.id.tipTextView);//
            tipTextView.setText(message);//


            progressDialog = new Dialog(context, R.style.loading_dialog);//
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(430, 215);
            progressDialog.setContentView(layout, lp);//
            progressDialog.show();
            // setOnKeyCancel();
            progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_CAMERA) {
                        return true;
                    }
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        if (KeyEvent.KEYCODE_BACK == event.getKeyCode()) {
                            if (mListener != null) {
                                mListener.onCancel();
                                return true;
                            }
                        }
                    }
                    return false;
                }
            });
            return;
        }
        if (progressDialog.isShowing()) {
            update(message);
        } else {
            update(message);
            progressDialog.show();
        }
    }

    public static void update(final String message) {
        if (progressDialog == null)
            return;
        View view = progressDialog.getWindow().getDecorView();
        final TextView tvUpdate = (TextView) view.findViewById(R.id.tipTextView);
        if (tvUpdate != null) {
            view.post(new Runnable() {

                @Override
                public void run() {
                    tvUpdate.setText(message);
                }
            });

        }
    }

    public static void update(Activity context, final String message) {
        if (progressDialog == null)
            return;
        View view = progressDialog.getWindow().getDecorView();
        final TextView tvUpdate = (TextView) view.findViewById(R.id.tipTextView);
        context.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                tvUpdate.setText(message);
            }
        });
    }

    public static void cancel() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog = null;
        }
        mListener = null;
    }

    public static void setOnKeyListener(OnKeyListener listener) {
        mListener = listener;

    }

    public interface OnKeyListener {
        public void onCancel();
    }
}
