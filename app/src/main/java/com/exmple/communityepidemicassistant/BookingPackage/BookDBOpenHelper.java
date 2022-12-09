package com.exmple.communityepidemicassistant.BookingPackage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.exmple.communityepidemicassistant.information.Info;

import java.util.ArrayList;

public class BookDBOpenHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    public BookDBOpenHelper(Context context) {
        super(context,"db_booking",null,1);
        db = context.openOrCreateDatabase("db_booking", Context.MODE_PRIVATE, null);
        String  ct="CREATE TABLE  IF NOT EXISTS  book("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "time DATE," +
                "site TEXT )";
        db.execSQL(ct);
    }

    public void bookAdd(String name,String time,String site){
        db.execSQL("INSERT INTO book(name,time,site)VALUES(?,?,?)",
                new Object[]{name,time,site});
    }
    @SuppressLint("Range")
    public ArrayList<Book> getBookData(){
        ArrayList<Book> list = new ArrayList<Book>();
        Cursor cursor = db.query("book",null,null,null,null,null,"time DESC");
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String time= cursor.getString(cursor.getColumnIndex("time"));
            String site = cursor.getString(cursor.getColumnIndex("site"));
            list.add(new Book(name,time,site));
        }
        return list;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
