package com.exmple.communityepidemicassistant.BookingPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exmple.communityepidemicassistant.R;
import com.exmple.communityepidemicassistant.TaskPackage.MySimpleAdapter;
import com.exmple.communityepidemicassistant.TaskPackage.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookingAdapter extends BaseAdapter {

    Context context;
    List<Book> mDatas;
    public BookingAdapter(Context context, List<Book> mDatas) {
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
        BookingAdapter.ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_booking,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Book book = mDatas.get(position);
        System.out.println(position);
        holder.appi_name.setText(book.getName());
        holder.appi_time.setText(book.getTime());
        holder.appi_site.setText(book.getSite());
        return convertView;
    }
    class ViewHolder {
        TextView appi_name,appi_time,appi_site;
        LinearLayout resultLayout;
        public  ViewHolder(View itemView){
            appi_name = itemView.findViewById(R.id.appointment_name);
            appi_time = itemView.findViewById(R.id.appointment_time);
            appi_site = itemView.findViewById(R.id.appointment_site);
            resultLayout = itemView.findViewById(R.id.resultLayout);
        }
    }
}
