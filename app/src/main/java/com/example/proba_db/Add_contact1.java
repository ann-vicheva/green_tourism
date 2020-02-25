package com.example.proba_db;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Add_contact1 extends FragmentActivity implements OnMapReadyCallback {

    private DatabaseHelpRoute mDBHelper;
    private SQLiteDatabase mDb;

    String route_buffer="";
    int common_count;
    String[] data_number = { "Говерла за 1 день",
                              "Білий слон",
                              "Найвищі вершини"
                           };
    List list = new ArrayList();
    private GoogleMap mMap;

    //дата рождения
    int DIALOG_TIME = 1;

    int myDay = 15;
    int myMonth = 6;
    int myYear = 1998;
    TextView tvDateBorn;
    //дата отправления
    int DIALOG_TIME1 = 2;
    int myDay1 = 15;
    int myMonth1 = 6;
    int myYear1 = 2018;
    TextView tvDateOtpr;
    //время отправления
    int DIALOG_TIME2 = 3;
    int myHour1 = 00;
    int myMinute1 = 00;
    TextView tvTimeOtpr;
    //дата прибытия
    int DIALOG_TIME3 = 4;
    int myDay2 = 16;
    int myMonth2 = 6;
    int myYear2 = 2018;
    TextView tvDatePrib;
    //время прибытия
    int DIALOG_TIME4 = 5;
    int myHour2 = 00;
    int myMinute2 = 00;
    TextView tvTimePrib;


    DatabaseHandler db;

    EditText etSurname;
    EditText etName;
    EditText etSecond_name;
    EditText etPhone_number;
    EditText etPhoneNumberMy;
    EditText etAddress;
    EditText etPassword;
    CheckBox cbAnonymity;
    //
    String name;
    String phone_number;
    String phone_number_my;
    String surname;
    String second_name;
    String location="";
    String route="";
    String address;
    String message="";
    int sos=0;
    int help=0;
    String password;
    int anonymity=0;
    //
    double latitude=0.0;
    double longitude=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact1);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final Context context=this;
        TextView tv = (TextView) findViewById(R.id.tv);
        tvDateBorn = (TextView) findViewById(R.id.tvDateBorn);
        tvDateOtpr = (TextView) findViewById(R.id.tvDateOtpr);
        tvTimeOtpr = (TextView) findViewById(R.id.tvTimeOtpr);
        tvDatePrib = (TextView) findViewById(R.id.tvDatePrib);
        tvTimePrib = (TextView) findViewById(R.id.tvTimePrib);

        etSurname = (EditText) findViewById(R.id.etSurname);
        etName = (EditText) findViewById(R.id.etName);
        etSecond_name = (EditText) findViewById(R.id.etSecond_name);
        etPhone_number = (EditText) findViewById(R.id.etPhone_number);
        etPhoneNumberMy = (EditText) findViewById(R.id.etPhoneNumberMy);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etPassword = (EditText) findViewById(R.id.etPassword);
        cbAnonymity = (CheckBox) findViewById(R.id.cbAnonymity);

        db = new DatabaseHandler(this);
        //для бд с маршрутами
        mDBHelper = new DatabaseHelpRoute(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        //--------del potom
        //db.deleteAll();
        //db.close();


        //------------------------------------------
        //********************************************************************************
        // адаптер для выбора названия маршрута
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_number);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner_map = (Spinner) findViewById(R.id.spinner_map);
        spinner_map.setAdapter(adapter1);
        // заголовок
        spinner_map.setPrompt("Title");
        // выделяем элемент
        spinner_map.setSelection(0);
        // устанавливаем обработчик нажатия
        spinner_map.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                route_buffer = data_number[position];
                zap_structur();
                paint_route();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        //------------------------------------------
        db.close();
        list.clear();

    }



    public void zap_structur(){
        String t="",f="",g="";
        int id=0, la=0, lo=0;
        String location1="";
        String routeName1="";
        double latitude=0.0;
        double longitude=0.0;
        list.clear();
        common_count=0;
        String buffer_change="SELECT * FROM route_table WHERE route='"+route_buffer+"'";
        //запрос к базе данных и заполнение списка
        Cursor cursor = mDb.rawQuery(buffer_change, null);
        cursor.moveToFirst();
        location = cursor.getString(1);
        route = cursor.getString(2);
        while (!cursor.isAfterLast()){
            id=cursor.getInt(0);
            location1=cursor.getString(1);
            routeName1=cursor.getString(2);
            latitude=cursor.getDouble(3);
            longitude=cursor.getDouble(4);
            Route route= new Route(id, location1, routeName1,
                                   latitude, longitude);
            list.add(route);
            common_count+=1;
            cursor.moveToNext();
        }
        cursor.close();
    }


    public void paint_route(){
        mMap.clear();//для удачной перерисовки карты
        PolylineOptions rectOptions = new PolylineOptions();
        int i = 0;
        while (i < common_count) {
            Route route2 = (Route) list.get(i);
            LatLng buffer = new LatLng(route2.latitude, route2.longitude);       //-выведение из списка
            mMap.addMarker(new MarkerOptions().position(buffer).title(String.valueOf(i+1)));
            rectOptions.add(new LatLng(route2.latitude, route2.longitude));
            i++;
        }
        mMap.addPolyline(rectOptions);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(47.8228900, 35.1903100);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Zaporogie"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void onclickDateBorn(View view){showDialog(DIALOG_TIME);}
   // public void onclickDateOtpr(View view){showDialog(DIALOG_TIME1);}
   // public void onclickTimeOtpr(View view){showDialog(DIALOG_TIME2);}
   // public void onclickDatePrib(View view){showDialog(DIALOG_TIME3);}
   // public void onclickTimePrib(View view){showDialog(DIALOG_TIME4);}

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_TIME) {
            DatePickerDialog dpd = new DatePickerDialog(this, myCallBack, myYear, myMonth, myDay);
            return dpd;
        }/*
        if (id == DIALOG_TIME1){
            DatePickerDialog dpd = new DatePickerDialog(this, myCallBack1, myYear1, myMonth1, myDay1);
            return dpd;
        }
        if (id == DIALOG_TIME2){
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBack2, myHour1, myMinute1, true);
            return tpd;
        }
        if (id == DIALOG_TIME3){
            DatePickerDialog dpd = new DatePickerDialog(this, myCallBack3, myYear2, myMonth2, myDay2);
            return dpd;
        }
        if (id == DIALOG_TIME4){
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBack4, myHour2, myMinute2, true);
            return tpd;
        }*/
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myDay = dayOfMonth;
            myMonth = month+1;
            myYear = year;
            tvDateBorn.setText("Дата народження: " + myDay + "." + myMonth + "." + myYear);
        }
    };
   /* DatePickerDialog.OnDateSetListener myCallBack1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myDay1 = dayOfMonth;
            myMonth1 = month+1;
            myYear1 = year;
            tvDateOtpr.setText("Дата відправлення: " + myDay1 + "." + myMonth1 + "." + myYear1);
        }
    };
    TimePickerDialog.OnTimeSetListener myCallBack2 = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour1 = hourOfDay;
            myMinute1 = minute;
            tvTimeOtpr.setText("Час відправлення: " + myHour1 + ":" + myMinute1);
        }
    };
    DatePickerDialog.OnDateSetListener myCallBack3 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myDay2 = dayOfMonth;
            myMonth2 = month+1;
            myYear2 = year;
            tvDatePrib.setText("Дата прибуття: " + myDay2 + "." + myMonth2 + "." + myYear2);
        }
    };
    TimePickerDialog.OnTimeSetListener myCallBack4 = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour2 = hourOfDay;
            myMinute2 = minute;
            tvTimePrib.setText("Час прибуття: " + myHour2 + ":" + myMinute2);
        }
    };*/

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

    //проверка на 18-летие(сравнивает даты, 1-д2>д1)
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
                {reshenie=2;}
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

    public void onclickShowPassword(View view){
        etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    public void add_contact(View view){
        name = etName.getText().toString();
        surname = etSurname.getText().toString();
        second_name = etSecond_name.getText().toString();
        phone_number = etPhone_number.getText().toString();
        phone_number_my = etPhoneNumberMy.getText().toString();
        address = etAddress.getText().toString();
        password = etPassword.getText().toString();
        boolean buf = cbAnonymity.isChecked();
        if (buf){anonymity=1;}else{anonymity=0;}

        String date_born = convert_date(myDay, myMonth, myYear+18);
        String date_otpr = convert_date(myDay1, myMonth1, myYear1);
        String date_prib = convert_date(myDay2, myMonth2, myYear2);
        String time_otpr = convert_time(myMinute1, myHour1);
        String time_prib = convert_time(myMinute2, myHour2);
        int razreshenie = date_to_date(date_born, date_otpr);//18-летие
        int razreshenie_o_p = date_to_date(date_otpr, date_prib);//дата отпр<=даты прибытия


        //проверка на пустоту полей
        if((name.equals(""))|(surname.equals(""))
                |(second_name.equals(""))|(phone_number.equals(""))|(phone_number_my.equals(""))
                |(address.equals(""))|(password.equals(""))){
            Toast toast = Toast.makeText(this, "Необхідно заповнити всі поля",Toast.LENGTH_LONG);
            toast.show();
        }
        else if(razreshenie==0){
            Toast toast = Toast.makeText(this, "Реєстрація не можлива, ви не досягли повноліття",Toast.LENGTH_LONG);
            toast.show();
        } else if (razreshenie_o_p==0){
            Toast toast = Toast.makeText(this, "Дата відправлення>дати прибуття",Toast.LENGTH_LONG);
            toast.show();
        } else if (razreshenie_o_p==2){//д.приб=д.отпр
            int razreshenie_time = time_to_time(time_otpr, time_prib);//время отправления<время прибытия
            if (razreshenie_time==0){
                Toast toast = Toast.makeText(this, "Час відправлення>=часу прибуття",Toast.LENGTH_LONG);
                toast.show();}
        }

        else{
            db.addContact(new Contact(name, phone_number, phone_number_my, surname, second_name,
                    myMinute1, myHour1, myDay1, myMonth1, myYear1,
                    myMinute2, myHour2, myDay2, myMonth2, myYear2,
                    location, route, myDay, myMonth, myYear, address,
                    message, sos, password, anonymity, help, latitude, longitude));
            Toast toast = Toast.makeText(this, "Користувача зареєстровано",Toast.LENGTH_LONG);
            toast.show();
            etName.setText("");
            etSurname.setText("");
            etSecond_name.setText("");
            etPhone_number.setText("");
            etPhoneNumberMy.setText("");
            etAddress.setText("");
            etPassword.setText("");
            mMap.clear();
        }
    }
}
