package com.obito.recitewords.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.obito.recitewords.R;
import com.obito.recitewords.adapter.ListAdapter;
import com.obito.recitewords.bmobobject.Words;
import com.obito.recitewords.callbackinterface.TranlateCallBack;
import com.obito.recitewords.tools.TranslateTool;
import com.obito.recitewords.tools.UploadDataTool;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {
    //数据获取完成 用于在Handler做出一些操作
    public static final int DATA_GET_FINISH = 0;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DATA_GET_FINISH:
                    initData(msg);
                    break;
                default:
            }
        }
    };
    //添加数据的按钮
    public FloatingActionButton fab;
    //显示数据的List
    public ListView listView;
    //无数据时显示的text
    public TextView textView;
    //在线翻译的布局按钮
    public LinearLayout online;
    //数据源
    public List<Words> dataList;
    //适配器
    public ListAdapter listAdapter;
    //工具类
    public UploadDataTool tool;
    //查询数据时显示
    public ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "412b50fcd0b9d5c6fb33a982ae4b6d93");
        initView();
        initEvent();
        initInstance();
        downloadData();
    }

    private void initEvent() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, AddActivity.class);
                startActivity(in);
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String word = dataList.get(i).getWords();
                String note = dataList.get(i).getNote();
                String para = dataList.get(i).getParaphrase();
                String id = dataList.get(i).getObjectId();
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra("word", word);
                intent.putExtra("note", note);
                intent.putExtra("para", para);
                intent.putExtra("id", id);
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int p = i;
                Snackbar.make(view, "确实删除该项？", Snackbar.LENGTH_LONG).setAction("啊。恩", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tool.deleteData(dataList.get(p));
                        dataList.remove(p);
                        listAdapter.notifyDataSetChanged();
                        listView.setAdapter(listAdapter);
                    }
                }).show();
                return true;
            }
        });
        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,TranslateActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }

    private void downloadData() {
        tool.queryData();
    }

    private void initInstance() {
        tool = new UploadDataTool(this, handler);
        dataList=new ArrayList<>();
    }

    private void initData(Message msg) {
        List<Words> list = (List<Words>) msg.obj;
        //将dataList以倒序时间顺序排列
        for (int i = list.size()-1 ; i >= 0; i--) {
            dataList.add(list.get(i));
        }
        if (dataList.size() == 0) {
            textView.setVisibility(View.VISIBLE);
        }
        listAdapter = new ListAdapter(dataList, this);
        listView.setAdapter(listAdapter);
        pb.setVisibility(View.GONE);
    }

    private void initView() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        listView = (ListView) findViewById(R.id.listview);
        textView = (TextView) findViewById(R.id.is_show);
        online = (LinearLayout) findViewById(R.id.lin_online);
        pb = (ProgressBar) findViewById(R.id.pb);
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.m1:
                Toast.makeText(this, "暂未实现，尽请期待。。。", Toast.LENGTH_SHORT).show();
                break;
            case R.id.m2:
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                adb.setTitle("啦啦啦");
                adb.setMessage("欢迎使用本软件，以下为正确的使用姿势：\n1.点击右下角的圆形按钮来添加一个单词。\n2.长按已经添加的单词可删除。\n3.点击已经添加的单词可重新编辑。\n4.软件需要联网，数据储存在服务器，即使卸载软件，数据也不会丢失。\n5.求给个好评");
                adb.setNegativeButton("哦。。。", null);
                adb.show();
                break;
            case R.id.m3:
                finish();
                break;


        }
        return true;
    }
}

