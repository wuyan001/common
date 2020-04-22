package com.lanshi.utils.base.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lanshi.utils.R;

import androidx.appcompat.app.AlertDialog;


/**
 * Created by admin on 2019/1/23.
 */

public class ProgressDialogUtils {

    private static AlertDialog dialog;

    //提交进度条
    public static  void showDowningDialog(Context context, String text) {
        AlertDialog.Builder buidler = new AlertDialog.Builder(context);
        dialog = buidler.create();
        View view = View.inflate(context, R.layout.loading_progress, null);
        TextView tv_msg = view.findViewById(R.id.tv_msg);
        dialog.setView(view,0,0,0,0);
      //  dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        tv_msg.setText(text);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

    }
    public  static void dismiss(){
        if (dialog != null)
            dialog.dismiss();
    }

}
