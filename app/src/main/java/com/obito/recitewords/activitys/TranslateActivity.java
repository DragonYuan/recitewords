package com.obito.recitewords.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.obito.recitewords.R;
import com.obito.recitewords.bmobobject.Words;
import com.obito.recitewords.callbackinterface.TranlateCallBack;
import com.obito.recitewords.tools.TranslateTool;
import com.obito.recitewords.tools.UploadDataTool;

/**
 * Created by obito on 17-8-27.
 */

public class TranslateActivity extends AppCompatActivity {
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            pb.setVisibility(View.GONE);
            para.setVisibility(View.VISIBLE);
            para.setText(words.getParaphrase());
            save.setVisibility(View.VISIBLE);
        }
    };
    //控件
    public EditText query;
    public Button traslate;
    public TextView para;
    public Button save;
    //用于等待翻译结果时显示
    public ProgressBar pb;
    //工具类 用来上传数据
    public UploadDataTool tool;
    //要上传的Words对象
    public Words words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translate);
        initView();
        initInstance();
        initEvent();
    }

    private void initEvent() {
        traslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = query.getText().toString();
                if (text.equals("")) {
                    Toast.makeText(TranslateActivity.this, "请输入要翻译的单词或者汉字", Toast.LENGTH_SHORT).show();
                    return;
                }
                String format="[[a-zA-z]+\\s]*";
                boolean isToEN=text.matches(format)?false:true;
                pb.setVisibility(View.VISIBLE);
                TranslateTool.translate(text, isToEN,new TranlateCallBack() {
                    @Override
                    public void onFinish(Words word) {
                        TranslateActivity.this.words = word;
                        handler.sendEmptyMessage(0);
                    }
                    @Override
                    public void onError(Exception e) {
                        Log.e("Tranlate", e.toString());

                    }
                });
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    //用于判断返回的结果格式是否是字母
                    String format = "[[a-zA-z]+\\s]+";
                    //如果翻译结果是字母，则认为用户输入的是中文
                    if (words.getParaphrase().matches(format)) {
                        //于是把word对象中的字母和释义替换
                        String temp = words.getParaphrase();
                        words.setParaphrase(words.getWords());
                        words.setWords(temp);
                    }
                words.setImei(tool.getImei());
                tool.uploadData(words);
            }
        });
    }

    private void initInstance() {
        tool = new UploadDataTool(this, null);
    }

    private void initView() {
        query = (EditText) findViewById(R.id.et_query);
        traslate = (Button) findViewById(R.id.btn_translate);
        para = (TextView) findViewById(R.id.tv_para);
        save = (Button) findViewById(R.id.save);
        pb = (ProgressBar) findViewById(R.id.t_pb);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TranslateActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}


