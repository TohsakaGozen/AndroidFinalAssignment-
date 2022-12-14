package com.exmple.communityepidemicassistant.BookingPackage;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.exmple.communityepidemicassistant.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingResultFragment  extends Fragment {

    Button reservation_btn;
    SQLiteDatabase db;
    ListView reservation_nucleic;
    BookDBOpenHelper bookDBOpenHelper;
    private List mData;
    private List<Map<String, Object>> list;
    BookingAdapter bookingAdapter;
    SimpleAdapter mSimpleAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment2_result,container,false);
        reservation_nucleic=(ListView)view.findViewById(R.id.reservation_nucleic);
        reservation_btn = view.findViewById(R.id.reservation_btn);
        init();
        return view;
    }

    private void init() {
        bookDBOpenHelper = new BookDBOpenHelper(getContext());
        db = bookDBOpenHelper.getReadableDatabase();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
        db = bookDBOpenHelper.getReadableDatabase();
        mData=getData();
        mSimpleAdapter= new SimpleAdapter(getContext(),
                mData,
                R.layout.listitem_booking,
                new String[]{"name","time","site"},
                new int[]{R.id.appointment_name,R.id.appointment_time,R.id.appointment_site});
        reservation_nucleic.setAdapter(mSimpleAdapter);
        reservation_nucleic.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                HashMap<Integer,Integer> map = (HashMap<Integer,Integer>) parent.getItemAtPosition(position);
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setIcon(R.drawable.warns);
                builder.setTitle("????????????");
                String name = String.valueOf(list.get(position).get("name"));
                String time = String.valueOf(list.get(position).get("time"));
                String site = String.valueOf(list.get(position).get("site"));
                builder.setMessage("?????????"+name+"\r\n"+
                                    "??????????????????"+time+"\r\n"+
                                    "????????????"+site);
                builder.setPositiveButton("??????",new DialogInterface.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    public void onClick(DialogInterface dialog, int which) {
                        }
                }).setNegativeButton("????????????",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //?????????????????????????????????
                        db = bookDBOpenHelper.getWritableDatabase();
                        String sql = "delete from book where name = '"+name+"'and time = '"+time+"' and site = '"+site+"' ";
                        db.execSQL(sql);
                        list.remove(position);
                        mSimpleAdapter.notifyDataSetChanged();
                    }
                });
                builder.create().show();
                return true;//return true????????????click??????
            }
        });

    }

    private List<Map<String,Object>> getData() {
        bookDBOpenHelper =new BookDBOpenHelper(getContext());
        db = bookDBOpenHelper.getWritableDatabase();
        list = new ArrayList<>();
        Cursor c = db.query("book", //query????????????????????????c
                null,
                null,  //????????????
                null,  //?????????
                null,
                null,
                null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            System.out.println("?????????");
            for (int i = 0; i < c.getCount(); i++) {
                String name = c.getString(c.getColumnIndexOrThrow("name"));
                System.out.println(name);
                String time = c.getString(c.getColumnIndexOrThrow("time"));
                System.out.println(time);
                String site = c.getString(c.getColumnIndexOrThrow("site"));
                System.out.println(site);
                //???????????????listview???????????????
                Map<String, Object> map = new HashMap<>();
                map.put("name", "??????: "+name);
                map.put("time", "??????????????????: "+time);
                map.put("site", "?????????: "+site);
                list.add(map);
                c.moveToNext();
            }
        }
        c.close();
//        db.close();
        return list;
    }

    private void refresh(){
        onCreate(null);
    }

}

