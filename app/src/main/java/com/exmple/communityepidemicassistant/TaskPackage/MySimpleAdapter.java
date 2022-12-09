package com.exmple.communityepidemicassistant.TaskPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exmple.communityepidemicassistant.R;
import com.exmple.communityepidemicassistant.TaskPackage.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySimpleAdapter extends BaseAdapter {
    private ArrayList<Map<String, String>> mData;
    Context context;
    List<Task> mDatas;
    public MySimpleAdapter(Context context, List<Task> mDatas) {
        System.out.println("运行adapter");
        this.context = context;
        System.out.println(context);
        this.mDatas = mDatas;
        System.out.println(mDatas);
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_task,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Task task = mDatas.get(position);
        System.out.println(position);
        holder.titleTv.setText(task.getTask_title());
        holder.dateTv.setText(task.getTask_date());
        if(task.getTask_iffinished().equals("no")){
            holder.imgV.setImageResource(R.drawable.unfinished);
        }else {
            holder.imgV.setImageResource(R.drawable.finished);
        }
        return convertView;
    }
    class ViewHolder {
        TextView titleTv,dateTv;
        ImageView imgV;
        LinearLayout taskLayout;
        public  ViewHolder(View itemView){
            titleTv = itemView.findViewById(R.id.tv_listitem_title);
            imgV = itemView.findViewById(R.id.iv_iffinished);
            taskLayout = itemView.findViewById(R.id.task_line);
            dateTv =itemView.findViewById(R.id.tv_listitem_date);
        }
    }
}

