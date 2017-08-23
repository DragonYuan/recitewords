package com.obito.recitewords.tools;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.obito.recitewords.R;
import com.obito.recitewords.activitys.AddActivity;
import com.obito.recitewords.activitys.MainActivity;
import com.obito.recitewords.bmobobject.Words;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by obito on 17-8-23.
 */

public class UploadDataTool {
    public Activity mContext;
    public Handler handler;


    public UploadDataTool(Activity mContext, Handler handler) {
        this.mContext = mContext;
        this.handler = handler;
    }

    //上传数据
    public void uploadData(Words words) {
        if (words != null)
            words.save(new MySaveListener());
        else
            Toast.makeText(mContext, "NullPointer!!!", Toast.LENGTH_SHORT).show();


    }

    //删除数据
    public void deleteData(Words words) {
        if (words != null)
            words.delete(new MyDeleteListener());
        else
            Toast.makeText(mContext, "NullPointer!!!", Toast.LENGTH_SHORT).show();
    }

    //更新数据
    public void updateData(Words words) {
        if (words != null)
            words.update(new MyUpdateListener());
        else
            Toast.makeText(mContext, "NullPointer!!!", Toast.LENGTH_SHORT).show();
    }

    //查询数据
    public void queryData() {
        String imei = getImei();


        BmobQuery<Words> bq = new BmobQuery<>();
        // 添加查询条件 查询表中imei为这台设备imei的所有数据
        if (imei == null || imei.equals("")) {
            bq.setLimit(0);
        } else {
            bq.setLimit(100);
        }
        bq.addWhereEqualTo("imei", imei);
        bq.findObjects(new QueryListener());
    }

    //上传数据用到的回调
    class MySaveListener extends SaveListener {

        @Override
        public void done(Object o, BmobException e) {
            if (e == null) {
                Toast.makeText(mContext, "添加成功！", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(mContext, MainActivity.class);
                mContext.startActivity(in);
                mContext.finish();

            }
            if (e != null) {
                Toast.makeText(mContext, "添加失败！请稍后重试。", Toast.LENGTH_SHORT).show();
                Log.e("Tool", e.toString());
            }
        }

    }

    //删除数据用到的回调
    class MyDeleteListener extends UpdateListener {
        @Override
        public void done(BmobException e) {
            if (e == null) {
                Toast.makeText(mContext, "删除成功！", Toast.LENGTH_SHORT).show();
            }
            if (e != null) {
                Toast.makeText(mContext, "删除失败！请稍后重试。", Toast.LENGTH_SHORT).show();
                Log.e("Tool", e.toString());
            }
        }
    }

    //更新数据的回调
    class MyUpdateListener extends UpdateListener {
        @Override
        public void done(BmobException e) {
            if (e == null) {
                Toast.makeText(mContext, "更改成功！", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(mContext, MainActivity.class);
                mContext.startActivity(in);
                mContext.finish();
            }
            if (e != null) {
                Toast.makeText(mContext, "更改失败！请稍后重试。", Toast.LENGTH_SHORT).show();
                Log.e("Tool", e.toString());
            }
        }
    }

    //获取数据的回调
    class QueryListener extends FindListener<Words> {
        @Override
        public void done(List<Words> list, BmobException e) {
            if (e == null) {
                //发送Message告知数据获取完成
                Message message = handler.obtainMessage();
                message.what = MainActivity.DATA_GET_FINISH;
                message.obj = list;
                handler.sendMessage(message);
            }
            if (e != null) {
                Toast.makeText(mContext, "查询数据失败！请稍后重试。", Toast.LENGTH_SHORT).show();
                Log.e("Tool", e.toString());
            }
        }
    }

    public String getImei() {
        String imei = null;
        //获取手机串号
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);

        try {
            imei = tm.getDeviceId();
        } catch (Exception e) {
            Snackbar.make(mContext.findViewById(R.id.pb), "缺少权限，软件无法运行，如果你是6.0以上用户，请在设置中给予软件权限。", Snackbar.LENGTH_INDEFINITE).setAction("退出软件", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "请务必设置权限!", Toast.LENGTH_SHORT).show();
                    mContext.finish();
                }
            }).show();

        }
        return imei;
    }
}
