package com.exmple.communityepidemicassistant.information;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.exmple.communityepidemicassistant.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InfoReportFragment extends Fragment {
    private EditText et_InfoReport_Date;
    private EditText et_InfoReport_Name;
    private EditText et_InfoReport_Id;
    private EditText et_InfoReport_Phone;
    private EditText et_InfoReport_Address;
    private RadioGroup rg_iffever;
    private RadioButton rb_iffever_yes;
    private RadioButton rb_iffever_no;
    private EditText et_InfoReport_Tem;
    private RadioGroup rg_iftouch;
    private RadioButton rb_iftouch_yes;
    private RadioButton rb_iftouch_no;
    private EditText et_InfoReport_TouchName;
    private EditText et_InfoReport_TouchPhone;
    private EditText et_InfoReport_TouchDate;
    private TextView tv_InfoReport_TouchName;
    private TextView tv_InfoReport_TouchPhone;
    private TextView tv_InfoReport_TouchDate;
    private Button bt_InfoReport_cancel;
    private Button bt_InfoReport_submit;

    Handler handler = new Handler();
    Runnable runnable;
    private Drawable drawable_phone;
    private InfoDBOpenHelper infoDBOpenHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment2_info_report, container, false);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        init();

        infoDBOpenHelper = new InfoDBOpenHelper(getContext());

        //???EditText????????????????????????????????????setOnClickListener()???setOnFocusChangeListener()
        // ???????????????setOnFocusChangeListener()??????????????????EditText????????????????????????????????????????????????????????????setOnClickListener()
        // ??????????????????????????????????????????????????????????????????setOnFocusChangeListener()
        et_InfoReport_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(et_InfoReport_Date);
            }
        });

        et_InfoReport_TouchDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerDialog(et_InfoReport_TouchDate);
                }
            }
        });

        et_InfoReport_TouchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(et_InfoReport_TouchDate);
            }
        });

        rb_iftouch_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_InfoReport_TouchName.setVisibility(View.VISIBLE);
                tv_InfoReport_TouchPhone.setVisibility(View.VISIBLE);
                tv_InfoReport_TouchDate.setVisibility(View.VISIBLE);
                et_InfoReport_TouchName.setVisibility(View.VISIBLE);
                et_InfoReport_TouchPhone.setVisibility(View.VISIBLE);
                et_InfoReport_TouchDate.setVisibility(View.VISIBLE);
            }
        });
        rb_iftouch_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_InfoReport_TouchName.setVisibility(View.GONE);
                tv_InfoReport_TouchPhone.setVisibility(View.GONE);
                tv_InfoReport_TouchDate.setVisibility(View.GONE);
                et_InfoReport_TouchName.setVisibility(View.GONE);
                et_InfoReport_TouchPhone.setVisibility(View.GONE);
                et_InfoReport_TouchDate.setVisibility(View.GONE);
            }
        });

        et_InfoReport_Phone.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        //?????????????????????
                        String phone=et_InfoReport_Phone.getText().toString().trim();
                        Pattern p= Pattern.compile("^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$");
                        Matcher m=p.matcher(phone);
                        if(m.matches()){
                            et_InfoReport_Phone.setCompoundDrawables(null,null, drawable_phone,null);
                        }else{
                            Toast.makeText(getContext(), "??????????????????????????????", Toast.LENGTH_SHORT).show();
                            et_InfoReport_Phone.setCompoundDrawables(null,null,null,null);
                        }
                    }
                };
                handler.postDelayed(runnable, 500);
            }
        });

        et_InfoReport_TouchPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        //?????????????????????
                        String phone=et_InfoReport_TouchPhone.getText().toString().trim();
                        Pattern p= Pattern.compile("^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$");
                        Matcher m=p.matcher(phone);
                        if(m.matches()){
                            et_InfoReport_TouchPhone.setCompoundDrawables(null,null, drawable_phone,null);
                        }else{
                            Toast.makeText(getContext(), "?????????????????????", Toast.LENGTH_SHORT).show();
                            et_InfoReport_TouchPhone.setCompoundDrawables(null,null,null,null);
                        }
                    }
                };
                handler.postDelayed(runnable, 500);
            }
        });

        et_InfoReport_Id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        String idCard=et_InfoReport_Id.getText().toString().trim();
                        if (IDCardValidate(idCard)==true){
                            et_InfoReport_Id.setCompoundDrawables(null,null, drawable_phone,null);
                        }else {
                            Toast.makeText(getContext(), "?????????????????????", Toast.LENGTH_SHORT).show();
                            et_InfoReport_Id.setCompoundDrawables(null,null,null,null);
                        }
                    }
                };
                handler.postDelayed(runnable, 1000);
            }
        });

        bt_InfoReport_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_InfoReport_Date.setText("");
                et_InfoReport_Name.setText("");
                et_InfoReport_Id.setText("");
                et_InfoReport_Phone.setText(null);
                et_InfoReport_Address.setText("");
                rg_iffever.clearCheck();
                et_InfoReport_Tem.setText("");
                rg_iftouch.clearCheck();
                et_InfoReport_TouchName.setText("");
                et_InfoReport_TouchPhone.setText(null);
                et_InfoReport_TouchDate.setText("");
            }
        });

        bt_InfoReport_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rdate=et_InfoReport_Date.getText().toString().trim();
                String rname=et_InfoReport_Name.getText().toString().trim();
                String ridCard=et_InfoReport_Id.getText().toString().trim();
                String rphone=et_InfoReport_Phone.getText().toString().trim();
                String raddress=et_InfoReport_Address.getText().toString().trim();

                String rtem=et_InfoReport_Tem.getText().toString().trim();
                String rifFever;
                if(rb_iffever_no.isChecked()&&!TextUtils.isEmpty(rtem)){
                    if(Double.parseDouble(rtem)>=36.1&&Double.parseDouble(rtem)<=37.3){
                        rifFever="???";
                    }else if((Double.parseDouble(rtem)<=36.1&&Double.parseDouble(rtem)>=14.2)|(Double.parseDouble(rtem)>=37.3&&Double.parseDouble(rtem)<=46.5)){
                        rifFever="???";
                    }
                    else {
                        rifFever="error";
                    }
//                    rifFever="???";
                }else if(rb_iffever_yes.isChecked()&&!TextUtils.isEmpty(rtem)){
                    if(Double.parseDouble(rtem)>=36.1&&Double.parseDouble(rtem)<=37.3){
                        rifFever="???";
                    }else if((Double.parseDouble(rtem)<=36.1&&Double.parseDouble(rtem)>=14.2)|(Double.parseDouble(rtem)>=37.3&&Double.parseDouble(rtem)<=46.5)){
                        rifFever="???";
                    }else {
                        rifFever="???";
                    }
//                    rifFever="???";
                }else {
                    rifFever="???";
                }

                String rifTouch;
                if(rb_iftouch_no.isChecked()){
                    rifTouch="???";
                }else if(rb_iftouch_yes.isChecked()){
                    rifTouch ="???";
                }else {
                    rifTouch="";
                }

                String rtouchName=et_InfoReport_TouchName.getText().toString().trim();
                String rtouchPhone=et_InfoReport_TouchPhone.getText().toString().trim();
                String rtouchDate=et_InfoReport_TouchDate.getText().toString().trim();

                if(rifTouch=="???"){
                    if (!TextUtils.isEmpty(rdate) && !TextUtils.isEmpty(rname)&&
                            !TextUtils.isEmpty(ridCard)&& !TextUtils.isEmpty(rphone)&&
                            !TextUtils.isEmpty(raddress)&& !TextUtils.isEmpty(rifFever)&&
                            !TextUtils.isEmpty(rtem)&& !TextUtils.isEmpty(rifTouch)&&
                            !TextUtils.isEmpty(rtouchDate)&& !TextUtils.isEmpty(rtouchPhone)&&
                            !TextUtils.isEmpty(rtouchDate) && rifFever!="???"){
                        if(rifFever=="error"){
                            Toast.makeText(getContext(),  "?????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                        }else{
                            infoDBOpenHelper.infoAdd(rdate,rname,ridCard,rphone,raddress,rifFever,rtem,rifTouch,
                                    rtouchName,rtouchPhone,rtouchDate);
                            Toast.makeText(getContext(),  "????????????", Toast.LENGTH_SHORT).show();
                            setDrawableRightGone();
                            clearAll();
                        }
                    }else {
                        Toast.makeText(getContext(), "??????????????????????????????", Toast.LENGTH_SHORT).show();
                        setDrawableRightGone();
                    }
                }else if(rifTouch=="???"){
                    if (!TextUtils.isEmpty(rdate) && !TextUtils.isEmpty(rname)&&
                            !TextUtils.isEmpty(ridCard)&& !TextUtils.isEmpty(rphone)&&
                            !TextUtils.isEmpty(raddress)&& !TextUtils.isEmpty(rifFever)&&
                            !TextUtils.isEmpty(rtem)&&rifFever!="???"){
                        if(rifFever=="error"){
                            Toast.makeText(getContext(),  "?????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                        }else{
                            infoDBOpenHelper.infoAdd(rdate,rname,ridCard,rphone,raddress,rifFever,rtem,rifTouch,
                                    rtouchName,rtouchPhone,rtouchDate);
                            Toast.makeText(getContext(),  "????????????", Toast.LENGTH_SHORT).show();
                            setDrawableRightGone();
                            clearAll();
                        }
                    }else {
                        Toast.makeText(getContext(), "??????????????????????????????", Toast.LENGTH_SHORT).show();
                        setDrawableRightGone();
                    }
                }
                else {
                    Toast.makeText(getContext(), "??????????????????????????????", Toast.LENGTH_SHORT).show();
                    setDrawableRightGone();
                }
            }
        });
    }
    private void setDrawableRightGone(){
        et_InfoReport_Id.setCompoundDrawables(null,null,null,null);
        et_InfoReport_Phone.setCompoundDrawables(null,null,null,null);
        et_InfoReport_TouchPhone.setCompoundDrawables(null,null,null,null);

    }

    //?????????
    private void init() {
        et_InfoReport_Date = (EditText) getView().findViewById(R.id.et_InfoReport_Date);
        et_InfoReport_Name = (EditText) getView().findViewById(R.id.et_InfoReport_Name);
        et_InfoReport_Id = (EditText) getView().findViewById(R.id.et_InfoReport_Id);
        et_InfoReport_Phone = (EditText) getView().findViewById(R.id.et_InfoReport_Phone);
        et_InfoReport_Address = (EditText) getView().findViewById(R.id.et_InfoReport_Address);
        rg_iffever = (RadioGroup) getView().findViewById(R.id.rg_iffever);
        rb_iffever_yes = (RadioButton) getView().findViewById(R.id.rb_iffever_yes);
        rb_iffever_no = (RadioButton) getView().findViewById(R.id.rb_iffever_no);
        et_InfoReport_Tem = (EditText) getView().findViewById(R.id.et_InfoReport_Tem);
        rg_iftouch = (RadioGroup) getView().findViewById(R.id.rg_iftouch);
        rb_iftouch_yes = (RadioButton) getView().findViewById(R.id.rb_iftouch_yes);
        rb_iftouch_no = (RadioButton) getView().findViewById(R.id.rb_iftouch_no);
        et_InfoReport_TouchName = (EditText) getView().findViewById(R.id.et_InfoReport_TouchName);
        et_InfoReport_TouchPhone = (EditText) getView().findViewById(R.id.et_InfoReport_TouchPhone);
        et_InfoReport_TouchDate = (EditText) getView().findViewById(R.id.et_InfoReport_TouchDate);
        tv_InfoReport_TouchName = (TextView) getView().findViewById(R.id.tv_InfoReport_TouchName);
        tv_InfoReport_TouchPhone = (TextView) getView().findViewById(R.id.tv_InfoReport_TouchPhone);
        tv_InfoReport_TouchDate = (TextView) getView().findViewById(R.id.tv_InfoReport_TouchDate);
        bt_InfoReport_cancel = (Button) getView().findViewById(R.id.bt_InfoReport_cancel);
        bt_InfoReport_submit = (Button) getView().findViewById(R.id.bt_InfoReport_submit);

        drawable_phone = getResources().getDrawable(R.drawable.yesok);
        drawable_phone.setBounds(0, 0, 55, 55);
        et_InfoReport_Phone.setCompoundDrawables(null,null, null,null);
        et_InfoReport_TouchPhone.setCompoundDrawables(null,null, null,null);
        et_InfoReport_Id.setCompoundDrawables(null,null,null,null);

        et_InfoReport_Date.setInputType(InputType.TYPE_NULL);//?????????????????????EditText?????????????????????
        et_InfoReport_TouchDate.setInputType(InputType.TYPE_NULL);
        tv_InfoReport_TouchName.setVisibility(View.GONE);
        tv_InfoReport_TouchPhone.setVisibility(View.GONE);
        tv_InfoReport_TouchDate.setVisibility(View.GONE);
        et_InfoReport_TouchName.setVisibility(View.GONE);
        et_InfoReport_TouchPhone.setVisibility(View.GONE);
        et_InfoReport_TouchDate.setVisibility(View.GONE);

        String num = "0123456789";
        et_InfoReport_Phone.setInputType(InputType.TYPE_CLASS_NUMBER);//????????????????????????
        et_InfoReport_TouchPhone.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_InfoReport_Phone.setKeyListener(DigitsKeyListener.getInstance(num));
        et_InfoReport_TouchPhone.setKeyListener(DigitsKeyListener.getInstance(num));

        et_InfoReport_Phone.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_InfoReport_Phone.setKeyListener(DigitsKeyListener.getInstance(num));
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
        et_InfoReport_Date.setText("");
        et_InfoReport_Name.setText("");
        et_InfoReport_Id.setText("");
        et_InfoReport_Phone.setText("");
        et_InfoReport_Address.setText("");
        rg_iffever.clearCheck();
        et_InfoReport_Tem.setText("");
        rg_iftouch.clearCheck();
        et_InfoReport_TouchName.setText("");
        et_InfoReport_TouchPhone.setText("");
        et_InfoReport_TouchDate.setText("");
    }


    /**
     * ?????????????????????????????????
     *
     * @param IDStr
     *            ???????????? [url=home.php?mod=space&uid=7300]@return[/url] ???????????????""
     *            ???????????????String??????
     */
    public static boolean IDCardValidate(String IDStr) {
        @SuppressWarnings("unused")
        String errorInfo = "";// ??????????????????
        String[] ValCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4",
                "3", "2" };
        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2" };
        String Ai = "";
        // ================ ??????????????? 15??????18??? ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "??????????????????????????????15??????18??????";
            return false;
        }
        // =======================(end)========================
        // ================ ?????? ??????????????????????????? ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            errorInfo = "?????????15???????????????????????? ; 18????????????????????????????????????????????????";
            return false;
        }
        // =======================(end)========================
        // ================ ???????????????????????? ================
        String strYear = Ai.substring(6, 10);// ??????
        String strMonth = Ai.substring(10, 12);// ??????
        String strDay = Ai.substring(12, 14);// ??????
        if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
            errorInfo = "????????????????????????";
            return false;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                    strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                errorInfo = "????????????????????????????????????";
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "?????????????????????";
            return false;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "?????????????????????";
            return false;
        }
        // =====================(end)=====================
        // ================ ????????????????????? ================
        Map<String, String> h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "??????????????????????????????";
            return false;
        }
        // ==============================================
        // ================ ???????????????????????? ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;
        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr) == false) {
                errorInfo = "????????????????????????????????????????????????";
                return false;
            }
        }
        // =====================(end)=====================
        return true;
    }
    /**
     * ???????????????????????????
     *
     * @return Hashtable ??????
     */
    private static Map<String, String> GetAreaCode() {
        Map<String, String> hashtable = new HashMap<String, String>();
        hashtable.put("11", "??????");
        hashtable.put("12", "??????");
        hashtable.put("13", "??????");
        hashtable.put("14", "??????");
        hashtable.put("15", "?????????");
        hashtable.put("21", "??????");
        hashtable.put("22", "??????");
        hashtable.put("23", "?????????");
        hashtable.put("31", "??????");
        hashtable.put("32", "??????");
        hashtable.put("33", "??????");
        hashtable.put("34", "??????");
        hashtable.put("35", "??????");
        hashtable.put("36", "??????");
        hashtable.put("37", "??????");
        hashtable.put("41", "??????");
        hashtable.put("42", "??????");
        hashtable.put("43", "??????");
        hashtable.put("44", "??????");
        hashtable.put("45", "??????");
        hashtable.put("46", "??????");
        hashtable.put("50", "??????");
        hashtable.put("51", "??????");
        hashtable.put("52", "??????");
        hashtable.put("53", "??????");
        hashtable.put("54", "??????");
        hashtable.put("61", "??????");
        hashtable.put("62", "??????");
        hashtable.put("63", "??????");
        hashtable.put("64", "??????");
        hashtable.put("65", "??????");
        hashtable.put("71", "??????");
        hashtable.put("81", "??????");
        hashtable.put("82", "??????");
        hashtable.put("91", "??????");
        return hashtable;
    }
    /**
     * ???????????????????????????????????????
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }
    //?????????????????????????????????????????????//
    public static boolean isDate(String strDate) {
        String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))" +
                "[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])" +
                "|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])" +
                "|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))" +
                "|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?" +
                "((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern pattern = Pattern.compile(regxStr);
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

}
