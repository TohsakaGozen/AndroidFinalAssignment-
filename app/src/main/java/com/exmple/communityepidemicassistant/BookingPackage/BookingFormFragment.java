package com.exmple.communityepidemicassistant.BookingPackage;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.exmple.communityepidemicassistant.R;

import java.util.Calendar;

public class BookingFormFragment extends Fragment {
    String appointment_name,appointment_time,appointment_site;
    EditText name_et,time_et;
    SQLiteDatabase db;
    Spinner spinner;
    Button submit;
    RadioGroup book_iffever;
    RadioButton iffever_yes,iffever_no;
    private BookDBOpenHelper bookDBOpenHelper;
    Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment2_booking, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        init();
        bookDBOpenHelper = new BookDBOpenHelper(getContext());
        db = bookDBOpenHelper.getWritableDatabase();
        time_et.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerDialog(time_et);
                }
            }
        });
        time_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(time_et);
            }
        });
        book_iffever.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case  R.id.book_iffever_yes:

                        break;
                    case R.id.book_iffever_no:
                        break;
                }
            }
        });
        name_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        final String[] site = new String[]{"?????????","????????????","??????",
                "????????????","?????????","?????????","????????????????????????","?????????","??????","????????????","?????????","??????"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,site);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                appointment_site=site[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView){

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointment_name = name_et.getText().toString();
                appointment_time = time_et.getText().toString();
                insertData(db , appointment_name,appointment_time,appointment_site);
            }
        });
    }
    private void insertData(SQLiteDatabase db, String appointment_name,String appointment_time,String appointment_site ){
        if(appointment_time.equals("")||appointment_time.equals("")||appointment_site.equals("")){
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                    .setTitle("?????????")
                    .setMessage("????????????????????????")
                    .setIcon(R.drawable.warns)
                    .setPositiveButton("??????", new DialogInterface.OnClickListener() {//??????"Yes"??????
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .setNegativeButton("??????", new DialogInterface.OnClickListener() {//????????????
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .create();
            alertDialog.show();
        }else {

            Cursor cursor = db.rawQuery("select * from book", null);
            int flag = 0;
            while(cursor.moveToNext()){
                if(appointment_name.equals(cursor.getString(2))){
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                            .setTitle("????????????")
                            .setMessage(cursor.getString(2)+"??????????????????????????????")
                            .setIcon(R.drawable.warns)
                            .setPositiveButton("??????", new DialogInterface.OnClickListener() {//??????"Yes"??????
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    clearAll();
                                }
                            })
                            .setNegativeButton("????????????", new DialogInterface.OnClickListener() {//????????????
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    name_et.setText("");
                                    time_et.setText("");
                                    clearAll();
                                }
                            })
                            .create();
                    alertDialog.show();
                    flag = 1;
                    break;
                }
            }
            if(flag==0){
                db.execSQL("insert into book values(null , ?, ?, ?)", new String[]{appointment_name,appointment_time,appointment_site});
                Toast.makeText(getActivity(),"????????????",Toast.LENGTH_SHORT).show();
                name_et.setText("");
                time_et.setText("");
                clearAll();
            }
        }
    }

    public void onclick(View v){
        Calendar calendar=Calendar.getInstance();
        final int now_year = calendar.get(Calendar.YEAR);
        final int now_month = calendar.get(Calendar.MONTH)+1;
        final int now_day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog( getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(year<now_year||(year==now_year&&month+1<now_month)||(year==now_year&&month+1==now_month&&dayOfMonth<now_day)){
                    Toast.makeText( getActivity(), "??????????????????????????????", Toast.LENGTH_SHORT ).show();
                }else{
                    String text = "???????????????" + year + "???" + (month + 1) + "???" + dayOfMonth + "???";
                    time_et.setText(year + "???" + (month + 1) + "???" + dayOfMonth + "???");
                    Toast.makeText( getActivity(), text, Toast.LENGTH_SHORT ).show();
                }

            }
        }
                ,calendar.get(Calendar.YEAR)
                ,calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH)).show();
    }



    private void init(){
        name_et = getView().findViewById(R.id.name_et);
        time_et = getView().findViewById(R.id.time_et);
        spinner = getView().findViewById(R.id.spinner);
        book_iffever =getView().findViewById(R.id.book_iffever);
        iffever_no = getView().findViewById(R.id.book_iffever_no);
        iffever_yes = getView().findViewById(R.id.book_iffever_yes);
        submit = getView().findViewById(R.id.submit_btn);
    }
    private void showDatePickerDialog(final EditText et) {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                et.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }
    private void clearAll(){
        name_et.setText("");
        time_et.setText("");
        spinner.clearFocus();
        book_iffever.clearCheck();
    }
}
