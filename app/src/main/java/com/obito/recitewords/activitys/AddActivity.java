package com.obito.recitewords.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.obito.recitewords.R;
import com.obito.recitewords.bmobobject.Words;
import com.obito.recitewords.tools.UploadDataTool;

/**
 * Created by obito on 17-8-23.
 */

public class AddActivity extends AppCompatActivity {
    //控件
    public EditText word;
    public EditText para;
    public EditText note;
    public Button add;
    //工具类
    public UploadDataTool tool;
    //标识当前是点击listview子项启动的还是点击的底部按钮
    public boolean isFromFab;
    //用于更新数据
    public String objId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_word);
        initView();
        initEvent();
        initInstance();
        isFromFab();

    }

    private void isFromFab() {
        Intent intent = getIntent();
        if (intent.getStringExtra("word") == null) {
            isFromFab = true;
        } else {
            String sword = intent.getStringExtra("word");
            String spara = intent.getStringExtra("para");
            String snote = intent.getStringExtra("note");
            word.setText(sword);
            para.setText(spara);
            note.setText(snote);
            objId = intent.getStringExtra("id");
            add.setText("保存更改");


        }
    }

    private void initInstance() {
        tool = new UploadDataTool(this, null);
    }

    private void initEvent() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Words words = new Words();
                String strWord = word.getText().toString();
                String strPara = para.getText().toString();
                String strNote = note.getText().toString();
                words.setImei(tool.getImei());
                words.setNote(strNote);
                words.setWords(strWord);
                words.setParaphrase(strPara);
                if (isFromFab) {
                    tool.uploadData(words);
                } else {
                    words.setObjectId(objId);
                    tool.updateData(words);
                }
            }
        });
    }

    private void initView() {
        word = (EditText) findViewById(R.id.et_word);
        para = (EditText) findViewById(R.id.et_para);
        note = (EditText) findViewById(R.id.et_note);
        add = (Button) findViewById(R.id.add);
    }

}
