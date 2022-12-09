package com.exmple.communityepidemicassistant.TaskPackage;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.exmple.communityepidemicassistant.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//二级Fragment，用于显示任务列表的TaskFragment，实现从本地数据库加载任务信息摘要到列表中和点击任务列表的一项跳转到任务明细activity的功能
public class TaskFragment extends Fragment {
    SimpleAdapter mSimpleAdapter;
    MySimpleAdapter mySimpleAdapter;
    private ListView listView_task;
    private TaskDBOpenHelper mTaskDBOpenHelper;     //声明TaskDBOpenHelper对象，这玩意儿用来创建数据表
    SQLiteDatabase dbr = null;
    private List mData;
    private ImageView iv_iffinished;
    private List<Map<String, Object>> list;
    private Integer imgId;

//    写一次就可以了
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mTaskDBOpenHelper = new TaskDBOpenHelper(getContext());
//        mTaskDBOpenHelper.add("图书预约服务规则","2022-12-2","图书预约分为在架图书预约和已外借图书预约",
//                "     在架图书预约即“图书通借”，是指读者可以通过网上预约的方式借阅图书馆其他校区图书馆的外借图书（保存本在架图书不可预约）。已外借图书预约是指对已被读者借出的图书，其他读者可以通过网上预约的方式进行顺序借阅。对于已外借图书，当该书被其他读者预约后，系统将根据预约申请对该书进行催还，详见本网页《预约图书催还规则》。","no", R.drawable.unfinished);
//        mTaskDBOpenHelper.add("预约图书催还规则","2022-012-15","归还图书将优先借给预约者",
//                "       当前持书读者在图书没有被预约时，可以在“应还日期”前归还图书，也可在“应还日期”前续借图书。但是一旦所借图书被其他读者预约，则“外借图书”的借期自动缩短为预约日之后的10天，“保存本图书”的借期自动缩短为预约日之后的3天，且不能续借。系统会根据预约申请, 通过邮件和手机短信提醒持书读者按期还书。","no",
//                R.drawable.unfinished);//
//    }

    //重写onCreateview方法，将fragment2_task动态加载进来
    //在此方法中初始化页面
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment2_task,container,false);
        iv_iffinished=(ImageView)view.findViewById(R.id.iv_iffinished);
        listView_task=(ListView)view.findViewById(R.id.listView_task);
        init();
        return view;
    }
    private void init(){
        mTaskDBOpenHelper=new TaskDBOpenHelper(getContext());
        mData=getData();
    }
    @Override
    public void onResume() {
        super.onResume();
        refresh();
        dbr = mTaskDBOpenHelper.getReadableDatabase();
        mData=getData();
        mSimpleAdapter= new SimpleAdapter(getContext(),
                mData,
                R.layout.listitem_task,
                new String[]{"title","date","abstract","img"},
                new int[]{R.id.tv_listitem_title,R.id.tv_listitem_date,R.id.tv_listitem_abstract,R.id.iv_iffinished}
        );
        listView_task.setAdapter(mSimpleAdapter);
        listView_task.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(TaskDetailActivity.EXTRA_ID,(int)id);
                intent.setClass(getContext(),TaskDetailActivity.class);
                startActivity(intent);
            }
        });

        listView_task.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                HashMap<Integer,Integer> map = (HashMap<Integer,Integer>) parent.getItemAtPosition(position);
                imgId=map.get("img");

                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setIcon(R.drawable.warns);
                builder.setTitle("删除任务");
                builder.setMessage("确认删除任务？");
                builder.setPositiveButton("确认",new DialogInterface.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    public void onClick(DialogInterface dialog, int which) {
                        //点确定时判断任务是否已确认完成，若完成则删除并隐藏此对话框；若未完成则提示先确认完成任务
                        int img=2131165326;  //未完成的图片id
                        if(imgId==img){
                            Toast.makeText(getContext(),"该任务未确认完成，不可删除！",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else{
                            list.remove(position);   //删除item
                            mSimpleAdapter.notifyDataSetChanged();
                            Toast.makeText(getContext(),"删除成功！",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //点击取消时只隐藏对话框
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                return true;//return true才不会和click冲突
            }
        });

    }

    public List<Map<String,Object>> getData() {
        dbr = mTaskDBOpenHelper.getWritableDatabase();
        list = new ArrayList<>();
        //查询数据
        Cursor c = dbr.query("task", //query函数返回一个游标c
                null,
                null,  //筛选条件
                null,  //筛选值
                null,
                null,
                "iffinished,date DESC");

        if (c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                String title = c.getString(c.getColumnIndexOrThrow("title"));
                String date = c.getString(c.getColumnIndexOrThrow("date"));
                String task_abstract = c.getString(c.getColumnIndexOrThrow("abstract"));
                String task_iffinished=c.getString(c.getColumnIndexOrThrow("iffinished"));
                String task_img=c.getString(c.getColumnIndexOrThrow("img"));
                int img=Integer.parseInt(task_img);
                //把值添加到listview的数据集中
                Map<String, Object> map = new HashMap<>();
                map.put("title", title);
                map.put("date", date);
                map.put("abstract", task_abstract);
                map.put("iffinished",task_iffinished);
                map.put("img",img);
                list.add(map);
                c.moveToNext();
            }
        }
        c.close();
        dbr.close();
        return list;
    }

    private void refresh(){
        onCreate(null);
    }
}
