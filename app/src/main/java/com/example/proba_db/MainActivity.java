package com.example.proba_db;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 111;

    //
    AlertDialog.Builder ad;
    Context context;

    EditText etSurname;
    EditText etPassword;
    Button bChange_cont;
    Button save_sos;
    CheckBox cbSos;

    String password;
    String phone_my;
    int razreshenie = 0;
    int net_polz = 0;
    int id;

    Button btSos;
    ImageView tvShowPassword;

    int ID;

    //
    int hr = 0;//hr=hr*60*60*1000;
    int min = 1*60*1000;//min = min*60*1000;
    int sec = 0;//sec = sec * 1000;
    int result = min;//result = hr+min+sec;

    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    BroadcastReceiver mReceiver;

    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);

        btSos = (Button) findViewById(R.id.btSos);
        cbSos = (CheckBox) findViewById(R.id.cbSos);
        save_sos = (Button) findViewById(R.id.save_sos);

        etSurname = (EditText) findViewById(R.id.etSearchSurname);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bChange_cont = (Button) findViewById(R.id.change_cont);
        tvShowPassword = (ImageView) findViewById(R.id.tvShowPassword);

        //db.deleteAll();
        RegisterAlarmBroadcast();
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), result , pendingIntent);

        //начало смс диалог

        if(checkPermission(Manifest.permission.SEND_SMS)) {
            //mSendMessageBtn.setEnabled(true);
        }else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS},
                    SEND_SMS_PERMISSION_REQUEST_CODE);
        }
        //
        context = MainActivity.this;
        String title = "Тривога, ви можете допомогти";
        String message = "Надіслати повідомлення?";
        String button1String = "так";
        String button2String = "ні";

        ad = new AlertDialog.Builder(context);
        ad.setTitle(title);  // заголовок
        ad.setMessage(message); // сообщение
        ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(context, "Вы прийняли правильне рішення!",
                        Toast.LENGTH_LONG).show();

                Contact cn = db.getContact(ID);
                String message = "Це сигнал тривоги!!!!!! \nІм'я: " + cn.getName() + ", Прізвище: "
                        + cn.getSurname() + ", Повідомлення: " + cn.getMessage();
                String phoneNo = cn.getPhoneNumber();
                if(!TextUtils.isEmpty(message) && !TextUtils.isEmpty(phoneNo)) {
                    if(checkPermission(Manifest.permission.SEND_SMS)) {
                        sendSMS(phoneNo, message);
                    }else {
                        Toast.makeText(MainActivity.this, "Доступ заборонено", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(context, "Можливо, ви маєте рацію", Toast.LENGTH_LONG)
                        .show();
            }
        });
        ad.setCancelable(true);
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(context, "Ви нічого не обрали",
                        Toast.LENGTH_LONG).show();
            }
        });
        //конец смс диалог
        db.close();
        //-----------------zakomm potom
        //db.deleteAll();
    }

    public void checked_help(int id){
        Contact ct = db.getContact(id);
        db.addContact(new Contact(ct.getName(), ct.getPhoneNumber(),ct.getPhoneNumberMy(), ct.getSurname(),
                ct.getSecondName(), ct.getMinute(), ct.getHour(), ct.getDay(),
                ct.getMonth(), ct.getYear(), ct.getMinute_p(), ct.getHour_p(),
                ct.getDay_p(), ct.getMonth_p(), ct.getYear_p(), ct.getLocation(),
                ct.getRoute(), ct.getDay_n(), ct.getMonth_n(), ct.getYear_n(),
                ct.getAddress(), ct.getMessage(), ct.getSos(), ct.getPassword(),
                ct.getAnonymity(), 1, ct.getLatitude(), ct.getLongitude()));
        db.deleteContact(ct);
    }

    public void onclickShowPassword(View view){
        etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    public void change(View view){
        net_polz=0;
        phone_my = etSurname.getText().toString();
        password = etPassword.getText().toString();
        if((phone_my.equals(""))|(password.equals(""))){
            Toast toast = Toast.makeText(this, "Заповніть всі поля.",Toast.LENGTH_LONG);
            toast.show();
        }
        List<Contact> contacts = db.getAllContacts();
        for (Contact cn : contacts) {
            if (phone_my.equals(cn.getPhoneNumberMy())){
                //если фио совпало

                if (password.equals(cn.getPassword())){razreshenie=1;net_polz=1;id=cn.getID();}
                else {
                    Toast toast = Toast.makeText(this, "Невірний пароль",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
        if (net_polz==0){
            Toast toast = Toast.makeText(this, "Немає такого користувача",Toast.LENGTH_LONG);
            toast.show();
        }else {

            //переход к форме редактирования профиля
            etSurname.setVisibility(View.GONE);
            etPassword.setVisibility(View.GONE);
            bChange_cont.setVisibility(View.GONE);
            tvShowPassword.setVisibility(View.GONE);

            cbSos.setVisibility(View.VISIBLE);
            save_sos.setVisibility(View.VISIBLE);

           // Contact ct = db.getContact(id);

        }
    }

    //------------------------------------------------------------------------
    private void sendSMS(String phoneNumber, String message)
    {
        String SENT="SMS відправлено";
        String DELIVERED="SMS доставлено";

        PendingIntent sentPI= PendingIntent.getBroadcast(this,0,
                new Intent(SENT),0);

        PendingIntent deliveredPI= PendingIntent.getBroadcast(this,0,
                new Intent(DELIVERED),0);

//---когда SMS отправлено---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1){
                switch(getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(),"SMS відправлено",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(),"Загальний збій",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(),"Не обслуговується",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(),"Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(),"Немає зв'язку)",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        },new IntentFilter(SENT));

//---когда SMS доставлено---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1){
                switch(getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(),"SMS доставлено",
                                Toast.LENGTH_SHORT).show();
                        checked_help(ID);
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(),"SMS не доставлено",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        },new IntentFilter(DELIVERED));

        SmsManager sms= SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber,null, message, sentPI, deliveredPI);
    }

//-------------------------------------------------------------------


    //дату в строку для сравнения
    public String convert_date(int d, int m, int y){
        String buffer="";
        String mon=""; String day="";
        if (d<10){day="0"+String.valueOf(d);}else {day=String.valueOf(d);}
        if (m<10){mon="0"+String.valueOf(m);}else {mon=String.valueOf(m);}
        buffer=day+"/"+mon+"/"+String.valueOf(y);
        return buffer;
    }

    //время в строку для сравнения
    public String convert_time(int m, int h){
        String buffer="";
        String min=""; String hour="";
        if (m<10){min="0"+String.valueOf(m);}else {min=String.valueOf(m);}
        if (h<10){hour="0"+String.valueOf(h);}else {hour=String.valueOf(h);}
        buffer=hour+":"+min;
        return buffer;
    }

    //проверка на даты(сравнивает даты, 1-д2>д1)
    public int date_to_date(String s1, String s2){
        int reshenie=0;
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = formatter.parse(s1);
            Date date2 = formatter.parse(s2);
            if (date1.compareTo(date2)<0)
            {reshenie=1;}
            else {
                if(date1.compareTo(date2)==0)
                {reshenie=1;}
                else
                {reshenie=0;}
            }
        }catch (ParseException e1){e1.printStackTrace();}
        return reshenie;
    }

    //сравнивает время, 1-в2>в1)
    public int time_to_time(String s1, String s2){
        int reshenie=0;
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            Date time1 = formatter.parse(s1);
            Date time2 = formatter.parse(s2);
            if (time1.compareTo(time2)<0)
            {reshenie=1;}
            else {
                if(time1.compareTo(time2)==0)
                {reshenie=0;}
                else
                {reshenie=0;}
            }
        }catch (ParseException e1){e1.printStackTrace();}
        return reshenie;
    }


    public  void  control_date_time(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.format(new Date());
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
        String currentTime = stf.format(new Date());
        List<Contact> contacts = db.getAllContacts();
        for (Contact cn : contacts) {
            String date_prib = convert_date(cn.getDay_p(), cn.getMonth_p(), cn.getYear_p());
            String time_prib = convert_time(cn.getMinute_p(), cn.getHour_p());
            int razr_d = date_to_date(date_prib, currentDate);
            int razr_t = time_to_time(time_prib, currentTime);
            if(razr_d==1){if(razr_t==1){
                //скинуть sos
                checked_sos(cn.getID());
            }}
        }
    }

    public void checked_sos(int id){
        Contact ct = db.getContact(id);
        db.addContact(new Contact(ct.getName(), ct.getPhoneNumber(),ct.getPhoneNumberMy(), ct.getSurname(),
                ct.getSecondName(), ct.getMinute(), ct.getHour(), ct.getDay(),
                ct.getMonth(), ct.getYear(), ct.getMinute_p(), ct.getHour_p(),
                ct.getDay_p(), ct.getMonth_p(), ct.getYear_p(), ct.getLocation(),
                ct.getRoute(), ct.getDay_n(), ct.getMonth_n(), ct.getYear_n(),
                ct.getAddress(), ct.getMessage(), 1, ct.getPassword(),
                ct.getAnonymity(), ct.getHelp(), ct.getLatitude(), ct.getLongitude()));
        db.deleteContact(ct);
    }

    public void control_sos(){
        List<Contact> contacts = db.getAllContacts();
        for (Contact cn : contacts) {
            if(cn.getSos()==1){
                if(cn.getHelp()==0){
                    //trevoga
                    ID= cn.getID();
                    ad.show();
                }
            }
        }
    }

    //для смс
    private boolean checkPermission(String permission) {
        int checkPermission = ContextCompat.checkSelfPermission(this, permission);
        return (checkPermission == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                   // mSendMessageBtn.setEnabled(true);
                }
                return;
            }
        }
    }
    //конец смс

    //для таймера
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private void RegisterAlarmBroadcast() {
        mReceiver = new BroadcastReceiver() {
            // private static final String TAG = "Alarm Example Receiver";
            @Override
            public void onReceive(Context context, Intent intent) {
                //здесь происходит действие по достижению времени таймера
                //Toast.makeText(context, "Alarm time has been reached", Toast.LENGTH_LONG).show();
                control_date_time();
                control_sos();

            }
        };

        registerReceiver(mReceiver, new IntentFilter("sample"));
        pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("sample"), 0);
        alarmManager = (AlarmManager)(this.getSystemService(Context.ALARM_SERVICE));
    }

    private void UnregisterAlarmBroadcast() {
        alarmManager.cancel(pendingIntent);
        getBaseContext().unregisterReceiver(mReceiver);
    }
    //конец для таймера


    //Intent intent = new Intent(this, DisplayMessageActivity.class);
    public void add_contact_to_db(View view){
        Intent intent = new Intent(this, Add_contact1.class);
        startActivity(intent);
    }

    public void search_to_db(View view){
        Intent intent = new Intent(this, Current_db1.class);
        startActivity(intent);
    }

    public void change_contact_to_db(View view){
        Intent intent = new Intent(this, Change_contact1.class);
        startActivity(intent);
    }

    public void sos_click(View view){
        etSurname.setVisibility(View.VISIBLE);
        etPassword.setVisibility(View.VISIBLE);
        bChange_cont.setVisibility(View.VISIBLE);
        tvShowPassword.setVisibility(View.VISIBLE);
    }

    public void save(View view){
        int sos;
        if (cbSos.isChecked()) {
            sos = 1;
        } else {
            sos = 0;
        }
        Contact ct = db.getContact(id);
        db.addContact(new Contact(ct.getName(), ct.getPhoneNumber(), ct.getPhoneNumberMy(), ct.getSurname(),
                ct.getSecondName(),ct.getMinute(), ct.getHour(), ct.getDay(), ct.getMonth(), ct.getYear(),
                ct.getMinute_p(), ct.getHour_p(), ct.getDay_p(), ct.getMonth_p(), ct.getYear_p(), ct.getLocation(),
                ct.getRoute(), ct.getDay_n(), ct.getMonth_n(), ct.getYear_n(),
                ct.getAddress(), ct.getMessage(), sos,
                ct.getPassword(), ct.getAnonymity(), ct.getHelp(), ct.getLatitude(), ct.getLongitude()));



        db.deleteContact(ct);

        cbSos.setVisibility(View.GONE);
        save_sos.setVisibility(View.GONE);

        if(sos==1) {
            Toast toast = Toast.makeText(this, "Сигнал тривоги подано.", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void about_contact(View view){
        Intent intent = new Intent(this, About_contact.class);
        startActivity(intent);
    }
}
