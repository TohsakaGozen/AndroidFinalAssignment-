package com.exmple.communityepidemicassistant.information;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exmple.communityepidemicassistant.R;

import java.util.List;

public class InfoAdapter extends BaseAdapter {
    Context context;
    List<Info> mDatas;

    public InfoAdapter(Context context, List<Info> mDatas) {
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
        InfoAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_task, null);
            holder = new InfoAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (InfoAdapter.ViewHolder) convertView.getTag();
        }
        Info info = mDatas.get(position);
        System.out.println(position);
        holder.infoNameTv.setText(info.getName());
        holder.infoDateTv.setText(info.getDate());
        holder.infoIfFeverTv.setText(info.getIfFever());
        holder.infoIfTouchTv.setText(info.getIfTouch());
        holder.infoIDTv.setText(info.getIdCard());
        holder.infoPhoneTv.setText(info.getPhone());
        holder.infoAddressTv.setText(info.getAddress());
        return convertView;
    }

    class ViewHolder {
        TextView infoNameTv, infoDateTv,infoIfFeverTv,infoIfTouchTv,infoIDTv,infoPhoneTv,infoAddressTv;
        LinearLayout infoLayout;

        public ViewHolder(View itemView) {
            infoNameTv = itemView.findViewById(R.id.tv_listitem_infoName);
            infoDateTv = itemView.findViewById(R.id.tv_listitem_infoDate);
            infoIfFeverTv = itemView.findViewById(R.id.tv_listitem_infoIfFever);
            infoIfTouchTv = itemView.findViewById(R.id.tv_listitem_infoIfTouch);
            infoIDTv = itemView.findViewById(R.id.tv_listitem_infoID );
            infoPhoneTv = itemView.findViewById(R.id.tv_listitem_infoPhone);
            infoAddressTv = itemView.findViewById(R.id.tv_listitem_infoAddress );
            infoLayout = itemView.findViewById(R.id.info_line);
        }
    }

}
