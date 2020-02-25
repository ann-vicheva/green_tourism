package com.example.proba_db;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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

public class Change_contact1 extends FragmentActivity implements OnMapReadyCallback {

    private DatabaseHelpRoute mDBHelper;
    private SQLiteDatabase mDb;
    String route_buffer="";
    int common_count;
    String[] data_number = { "Говерла за 1 день",
            "Білий слон",
            "Найвищі вершини"
    };
    String[] number = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14"};
    int value=-1;
    List list = new ArrayList();

    private GoogleMap mMap;

    EditText etName;
    EditText etSurname;
    EditText etPassword;
    Button bChange_cont;
    Button btnLocationSettings;
    Button bSave_cont;
    EditText etMessage;
    CheckBox cbAnonymity;
    CheckBox cbSos;
    Spinner spinner_map;
    Spinner spinner;

    TextView tvEnabledGPS;
    TextView tvStatusGPS;
    TextView tvLocationGPS;
    TextView tv2;
    ImageView tvShowPassword;
    View View02;
    View View03;
    View View04;
    View View05;
    View View06;
    TextView tv3;
    CheckBox cbGPS;
    TextView tv4;
    TextView tv5;
    TextView tv6;
    TextView tv7;
    TextView tv8;



    private LocationManager locationManager;
    StringBuilder sbGPS = new StringBuilder();
    Context context1;

    int id;
    String name="";
    String phone_my="";
    String second_name="";
    String password="";
    String location="";
    String route="";
    int razreshenie = 0;
    int net_polz = 0;
    int anonymity=0;
    int sos=0;
    int help=1;

    //дата рождения
    int DIALOG_TIME = 1;
    int myDay;
    int myMonth;
    int myYear;
    TextView tvDateBorn;
    //дата отправления
    int DIALOG_TIME1 = 2;
    int myDay1;
    int myMonth1;
    int myYear1;
    TextView tvDateOtpr;
    //время отправления
    int DIALOG_TIME2 = 3;
    int myHour1;
    int myMinute1;
    TextView tvTimeOtpr;
    //дата прибытия
    int DIALOG_TIME3 = 4;
    int myDay2;
    int myMonth2;
    int myYear2;
    TextView tvDatePrib;
    //время прибытия
    int DIALOG_TIME4 = 5;
    int myHour2;
    int myMinute2;
    TextView tvTimePrib;

    double latitude=0.0;
    double longitude=0.0;
    double latitudeG=0.0;
    double longitudeG=0.0;


    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_contact1);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        context1 = this;

        db = new DatabaseHandler(this);

        bSave_cont = (Button) findViewById(R.id.save_cont);
        btnLocationSettings = (Button) findViewById(R.id.btnLocationSettings);
        tvDateBorn = (TextView) findViewById(R.id.tvDateBorn);
        tvDateOtpr = (TextView) findViewById(R.id.tvDateOtpr);
        tvTimeOtpr = (TextView) findViewById(R.id.tvTimeOtpr);
        tvDatePrib = (TextView) findViewById(R.id.tvDatePrib);
        tvTimePrib = (TextView) findViewById(R.id.tvTimePrib);
        etMessage = (EditText) findViewById(R.id.etMessage);
        cbAnonymity = (CheckBox) findViewById(R.id.cbAnonymity);
        cbSos = (CheckBox) findViewById(R.id.cbSos);

        etName = (EditText) findViewById(R.id.etSearchName);
        etSurname = (EditText) findViewById(R.id.etSearchSurname);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bChange_cont = (Button) findViewById(R.id.change_cont);

        tvEnabledGPS = (TextView) findViewById(R.id.tvEnabledGPS);
        tvStatusGPS = (TextView) findViewById(R.id.tvStatusGPS);
        tvLocationGPS = (TextView) findViewById(R.id.tvLocationGPS);
        tv2 = (TextView) findViewById(R.id.tv2);
        tvShowPassword = (ImageView) findViewById(R.id.tvShowPassword);

        View02 = (View) findViewById(R.id.View02);
        View03 = (View) findViewById(R.id.View03);
        View04 = (View) findViewById(R.id.View04);
        View05 = (View) findViewById(R.id.View05);
        View06 = (View) findViewById(R.id.View06);
        cbGPS = (CheckBox) findViewById(R.id.cbGPS);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
        tv7 = (TextView) findViewById(R.id.tv7);
        tv8 = (TextView) findViewById(R.id.tv8);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

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

        // адаптер для выбора названия маршрута
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_number);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_map = (Spinner) findViewById(R.id.spinner_map);
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

        // адаптер для выбора ближайшей точки
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, number);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Title");
        // выделяем элемент
        spinner.setSelection(0);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id){
                value=position;
                setTochka();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        db.close();
        //---------------del potom
        //db.deleteAll();
        list.clear();
    }

    public void onclickShowPassword(View view){
        etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    public void setTochka(){
        String buffer_change="SELECT * FROM route_table WHERE route='"+route_buffer+"'";
        //запрос к базе данных и заполнение списка
        Cursor cursor = mDb.rawQuery(buffer_change, null);
        cursor.moveToFirst();
        int count=0;
        while (!cursor.isAfterLast()){
            if(count==value) {
                latitude = cursor.getDouble(3);
                longitude = cursor.getDouble(4);
                count++;
                cursor.moveToNext();
            }else {
                count++;
                cursor.moveToNext();
            }
        }
        cursor.close();
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
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(47.8228900, 35.1903100);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Zaporogie"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    //для gps начало
    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(context1, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context1, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener)
        ;
        checkEnabled();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();
            if (ActivityCompat.checkSelfPermission(context1, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context1, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
                tvStatusGPS.setText("Статус: " + String.valueOf(status));
            }
        }
    };

    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            tvLocationGPS.setText(formatLocation(location));
        }
    }

    private String formatLocation(Location location) {
        if (location == null)
            return "";
        latitudeG = location.getLatitude();
        longitudeG = location.getLongitude();
        return String.format(
                "Місцезнаходження: \nширота = %1$.8f, \nдовгота = %2$.8f",
                location.getLatitude(), location.getLongitude());
    }

    private void checkEnabled() {
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {btnLocationSettings.setVisibility(View.GONE);}
        else{btnLocationSettings.setVisibility(View.VISIBLE);}
        tvEnabledGPS.setText("Доступність GPS: "
                + locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER));
    }

    public void onClickLocationSettings(View view) {
        startActivity(new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    };
    //для gps конец

    public void onclickDateBorn(View view){showDialog(DIALOG_TIME);}
    public void onclickDateOtpr(View view){showDialog(DIALOG_TIME1);}
    public void onclickTimeOtpr(View view){showDialog(DIALOG_TIME2);}
    public void onclickDatePrib(View view){showDialog(DIALOG_TIME3);}
    public void onclickTimePrib(View view){showDialog(DIALOG_TIME4);}

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_TIME) {
            DatePickerDialog dpd = new DatePickerDialog(this, myCallBack, myYear, myMonth, myDay);
            return dpd;
        }
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
        }
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
    DatePickerDialog.OnDateSetListener myCallBack1 = new DatePickerDialog.OnDateSetListener() {
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
    };

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

            tv3.setVisibility(View.VISIBLE);
            tv4.setVisibility(View.VISIBLE);
            tv5.setVisibility(View.VISIBLE);
            tv6.setVisibility(View.VISIBLE);
            tv7.setVisibility(View.VISIBLE);
            tv8.setVisibility(View.VISIBLE);
            cbGPS.setVisibility(View.VISIBLE);
            View02.setVisibility(View.VISIBLE);
            View03.setVisibility(View.VISIBLE);
            View04.setVisibility(View.VISIBLE);
            View05.setVisibility(View.VISIBLE);
            View06.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);
            spinner_map.setVisibility(View.VISIBLE);
            tvEnabledGPS.setVisibility(View.VISIBLE);
            tvStatusGPS.setVisibility(View.VISIBLE);
            tvLocationGPS.setVisibility(View.VISIBLE);
            bSave_cont.setVisibility(View.VISIBLE);
            tvDateBorn.setVisibility(View.VISIBLE);
            tvDateOtpr.setVisibility(View.VISIBLE);
            tvTimeOtpr.setVisibility(View.VISIBLE);
            tvDatePrib.setVisibility(View.VISIBLE);
            tvTimePrib.setVisibility(View.VISIBLE);
            etMessage.setVisibility(View.VISIBLE);
            cbAnonymity.setVisibility(View.VISIBLE);
            cbSos.setVisibility(View.VISIBLE);
            Contact ct = db.getContact(id);
            myDay = ct.getDay_n(); myMonth = ct.getMonth_n(); myYear = ct.getYear_n();
            tvDateBorn.setText("Дата народження: " + myDay + "." + myMonth + "." + myYear);
            myDay1 = ct.getDay(); myMonth1 = ct.getMonth(); myYear1 = ct.getYear();
            tvDateOtpr.setText("Дата відправлення: " + myDay1 + "." + myMonth1 + "." + myYear1);
            myHour1 = ct.getHour(); myMinute1 = ct.getMinute();
            tvTimeOtpr.setText("Час відправлення: " + myHour1 + ":" + myMinute1);
            myDay2 = ct.getDay_p(); myMonth2 = ct.getMonth_p(); myYear2 = ct.getYear_p();
            tvDatePrib.setText("Дата прибуття: " + myDay2 + "." + myMonth2 + "." + myYear2);
            myHour2 = ct.getHour_p(); myMinute2 = ct.getMinute_p();
            tvTimePrib.setText("Час прибуття: " + myHour2 + ":" + myMinute2);
            if(ct.getAnonymity()==1){cbAnonymity.setChecked(true);}
            if(ct.getSos()==1){cbSos.setChecked(true);}
            etMessage.setText(ct.getMessage());

            // Add a marker in Sydney and move the camera
            if((ct.getLatitude()!=0)&(ct.getLongitude()!=0)) {
                LatLng sydney = new LatLng(ct.getLatitude(), ct.getLongitude());
                mMap.addMarker(new MarkerOptions().position(sydney).title("I am here"));
            }
        }
    }

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

    public void save(View view) {
        String date_born = convert_date(myDay, myMonth, myYear+18);
        String date_otpr = convert_date(myDay1, myMonth1, myYear1);
        String date_prib = convert_date(myDay2, myMonth2, myYear2);
        String time_otpr = convert_time(myMinute1, myHour1);
        String time_prib = convert_time(myMinute2, myHour2);
        int razreshenie = date_to_date(date_born, date_otpr);//18-летие
        int razreshenie_o_p = date_to_date(date_otpr, date_prib);//дата отпр<=даты прибытия

        if (((latitudeG == 0.0) && (longitudeG == 0.0))&&((latitude == 0.0) && (longitude == 0.0))) {
            Toast toast = Toast.makeText(this, "Дочекайтесь визначення місцерозрашування або вкажіть найближчу точку маршруту.", Toast.LENGTH_LONG);
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
        else {
            Contact ct = db.getContact(id);

            if (cbAnonymity.isChecked()) {
                anonymity = 1;
            }
            if (cbSos.isChecked()) {
                sos = 1;
            } else {
                sos = 0;
            }
            if ((sos == 0) & (ct.getHelp() == 0)) {
                sos = 0;
                help = 0;
            }
            if ((sos == 0) & (ct.getHelp() == 1)) {
                sos = 0;
                help = 0;
            }
            if ((sos == 1) & (ct.getHelp() == 0)) {
                sos = 1;
                help = 0;
            }
            if ((sos == 1) & (ct.getHelp() == 1)) {
                sos = 1;
                help = 1;
            }
            double lat=0.0;
            double lon=0.0;
            if (cbGPS.isChecked()){lat=latitudeG;lon=longitudeG;}
            else{lat=latitude;lon=longitude;}



            db.addContact(new Contact(ct.getName(), ct.getPhoneNumber(), ct.getPhoneNumberMy(), ct.getSurname(),
                    ct.getSecondName(), myMinute1, myHour1, myDay1, myMonth1, myYear1,
                    myMinute2, myHour2, myDay2, myMonth2, myYear2, location,
                    route, ct.getDay_n(), ct.getMonth_n(), ct.getYear_n(),
                    ct.getAddress(), etMessage.getText().toString(), sos,
                    ct.getPassword(), anonymity, help, lat, lon));

            db.deleteContact(ct);

            tvShowPassword.setVisibility(View.VISIBLE);
            etSurname.setVisibility(View.VISIBLE);
            etPassword.setVisibility(View.VISIBLE);
            etName.setVisibility(View.VISIBLE);
            bChange_cont.setVisibility(View.VISIBLE);

            etSurname.setText("");
            etPassword.setText("");
            etName.setText("");

            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            tv5.setVisibility(View.GONE);
            tv6.setVisibility(View.GONE);
            tv7.setVisibility(View.GONE);
            tv8.setVisibility(View.GONE);
            cbGPS.setVisibility(View.GONE);
            View02.setVisibility(View.GONE);
            View03.setVisibility(View.GONE);
            View04.setVisibility(View.GONE);
            View05.setVisibility(View.GONE);
            View06.setVisibility(View.GONE);
            tv2.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            spinner_map.setVisibility(View.GONE);
            tvEnabledGPS.setVisibility(View.GONE);
            tvStatusGPS.setVisibility(View.GONE);
            tvLocationGPS.setVisibility(View.GONE);
            bSave_cont.setVisibility(View.GONE);
            tvDateBorn.setVisibility(View.GONE);
            tvDateOtpr.setVisibility(View.GONE);
            tvTimeOtpr.setVisibility(View.GONE);
            tvDatePrib.setVisibility(View.GONE);
            tvTimePrib.setVisibility(View.GONE);
            etMessage.setVisibility(View.GONE);
            cbAnonymity.setVisibility(View.GONE);
            cbSos.setVisibility(View.GONE);
            mMap.clear();
            Toast toast = Toast.makeText(this, "Внесені зміни збережені.", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
