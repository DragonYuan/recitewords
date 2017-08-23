package com.obito.recitewords.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.obito.recitewords.R;
import com.obito.recitewords.bmobobject.Words;

import java.util.List;

/**
 * Created by obito on 17-8-23.
 */

public class ListAdapter extends BaseAdapter {
    //数据源
    private List<Words> dataList;
    //用来加载布局文件
    private LayoutInflater inflater;

    public ListAdapter(List dataList, Context context) {
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Words words = dataList.get(i);
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            view = inflater.inflate(R.layout.item_layout, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.word = view.findViewById(R.id.word);
            viewHolder.note = view.findViewById(R.id.note);
            viewHolder.para = view.findViewById(R.id.para);
            viewHolder.date = view.findViewById(R.id.date);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.word.setText(words.getWords());
        viewHolder.note.setText(words.getNote());
        viewHolder.para.setText(words.getParaphrase());
        viewHolder.date.setText(words.getCreatedAt());
        return view;
    }

    class ViewHolder {
        public TextView word;
        public TextView note;
        public TextView para;
        public TextView date;
    }
}
