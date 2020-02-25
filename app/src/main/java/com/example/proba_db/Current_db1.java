package com.example.proba_db;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import static com.example.proba_db.R.id.tv;

public class Current_db1 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Search searchList;

    int anonymity;
    int res_search=0;

    String name="";
    String surname="";
    String common_string="";

    EditText etSearchName;
    EditText etSearchSurname;

    DatabaseHandler db;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_db1);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        db = new DatabaseHandler(this);

        textView = (TextView)findViewById(tv);

        etSearchName = (EditText) findViewById(R.id.etSearchName);
        etSearchSurname = (EditText) findViewById(R.id.etSearchSurname);
        open();

        //db.deleteAll();

        db.close();

    }

    public void open(){//автозаполнение последним запросом
        searchList = JSONHelper.importFromJSON(this);
        if(searchList!=null){
            etSearchName.setText(searchList.getName());
            etSearchSurname.setText(searchList.getSurname());
        }
    }

    public void save(String name, String surname){
        //сохранение последнего запроса для автозаполнения в последующем
        if(name.equals("")&&surname.equals("")){}
        else {
            searchList = new Search(name, surname);
            boolean result = JSONHelper.exportToJSON(this, searchList);
            if (!result) {
                Toast.makeText(this, "Не вдалось зберегти дані", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(47.8228900, 35.1903100);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Zaporogie"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void search(View view){
        mMap.clear();
        name=""; surname="";
        name = etSearchName.getText().toString();
        surname = etSearchSurname.getText().toString();
        int buffer_for_search=0;
        if ((name.equals(""))&(surname.equals(""))){buffer_for_search=1;
        }else if (surname.equals("")){buffer_for_search=3;}
        else if (name.equals("")){buffer_for_search=2;}

        switch (buffer_for_search){
            case 0:
                //поиск по имени и фамилии
                search_name_surname();
                save(name, surname);
                break;
            case 1:
                Toast toast = Toast.makeText(this, "Введіть ім'я або прізвище",Toast.LENGTH_LONG);
                toast.show();
                break;
            case 2:
                search_surname();
                save(name, surname);
                break;
            case 3:
                search_name();
                save(name, surname);
                break;
        }
    }

    public void search_name_surname(){
        textView.setText("");
        common_string="";
        res_search=0;
        List<Contact> contacts = db.getAllContacts();
        for (Contact cn : contacts) {
            if ((name.equals(cn.getName()))& (surname.equals(cn.getSurname()))){
                anonymity = cn.getAnonymity();
                if(anonymity==1) {
                    String log = "Прізвище: "+ cn.getSurname() + ", Ім'я: " + cn.getName()
                            + ", По батькові: " + cn.getSecondName() + ", \nКонтактний номер: "
                            + cn.getPhoneNumber()
                            + ", \nДата відправлення: " + cn.getDay() + "." + cn.getMonth() + "." + cn.getYear()
                            + ", \nЧас відправлення: " + cn.getHour() + ":" + cn.getMinute()
                            + ", \nДата прибуття: " + cn.getDay_p() + "." + cn.getMonth_p() + "." + cn.getYear_p()
                            + ", \nЧас прибуття: " + cn.getHour_p() + ":" + cn.getMinute_p()
                            + ", \nЛокація: " + cn.getLocation()+ ", \nМаршрут: "+ cn.getRoute()
                            + ", \nПовідомлення: " + cn.getMessage() + ", \nСигнал тривоги:" + cn.getSos()
                            + ", Надана допомога: " + cn.getHelp() + "\n";
                    if((cn.getLatitude()!=0)&(cn.getLongitude()!=0)) {
                        LatLng sydney = new LatLng(cn.getLatitude(), cn.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(sydney).title(cn.getName()+" "+cn.getSurname()));
                    }
                    common_string += log + "\n";
                    res_search=1;//что-то найдено
                }else {
                    String log = "Прізвище: "+ cn.getSurname() + ", Ім'я: " + cn.getName()
                            + ", По батькові: " + cn.getSecondName() + ", \nКонтактний номер: "
                            + cn.getPhoneNumber() + ", Адреса: " + cn.getAddress()
                            + ", \nДата народження: " + cn.getDay_n() + "." + cn.getMonth_n() + "." + cn.getYear_n()
                            + ", \nДата відправлення: " + cn.getDay() + "." + cn.getMonth() + "." + cn.getYear()
                            + ", \nЧас відправлення: " + cn.getHour() + ":" + cn.getMinute()
                            + ", \nДата прибуття: " + cn.getDay_p() + "." + cn.getMonth_p() + "." + cn.getYear_p()
                            + ", \nЧас прибуття: " + cn.getHour_p() + ":" + cn.getMinute_p()
                            + ", \nЛокація: " + cn.getLocation()+ ", \nМаршрут: "+ cn.getRoute()
                            + ", \nМісцезнаходження: широта-" + cn.getLatitude()
                            + ", довгота-" + cn.getLongitude()
                            + ", \nПовідомлення: " + cn.getMessage() + ", \nСигнал тривоги:" + cn.getSos()
                            + ", Анонімність:" + cn.getAnonymity()
                            + ", Надана допомога: " + cn.getHelp() + "\n";
                    if((cn.getLatitude()!=0)&(cn.getLongitude()!=0)) {
                        LatLng sydney = new LatLng(cn.getLatitude(), cn.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(sydney).title(cn.getName()+" "+cn.getSurname()));
                    }
                    common_string+=log + "\n";
                    res_search=1;//что-то найдено
                }
            }
        }
        if (res_search==1){
            textView.setText(common_string);
            etSearchName.setText("");
            etSearchSurname.setText("");
        }
        else {
            Toast toast = Toast.makeText(this, "Такого туриста не зареєстровано",Toast.LENGTH_LONG);
            toast.show();
            etSearchName.setText("");
            etSearchSurname.setText("");
        }
    }

    public int binarySearch(Integer[] array, int first, int last, int item) {
        int position=0;
        int comparisonCount = 1;    // для подсчета количества сравнений
        String s="";

        // для начала найдем индекс среднего элемента массива
        position = (first + last) / 2;

        while ((array[position] != item) && (first <= last)) {
            comparisonCount++;
            if (array[position] > item) {  // если число заданного для поиска
                last = position - 1; // уменьшаем позицию на 1.
            } else {
                first = position + 1;    // иначе увеличиваем на 1
            }
            position = (first + last) / 2;
        }
        if (first <= last) {return position;
        } else {return -1;}
    }

    //для творчества(с бинарным поиском)
    public void search_name(){
        textView.setText("");
        common_string="";
        res_search=0;
        List<Contact> contacts = db.getAllContacts();
        Contact[] arrayContact = new Contact[contacts.size()];
        int i=0; //из списка в массив
        for(Contact cn:contacts){
            arrayContact[i]= new Contact(cn.getID(), cn.getName(), cn.getPhoneNumber(),
                    cn.getPhoneNumberMy(), cn.getSurname(), cn.getSecondName(),
                    cn.getMinute(), cn.getHour(), cn.getDay(), cn.getMonth(),
                    cn.getYear(), cn.getMinute_p(), cn.getHour_p(), cn.getDay_p(),
                    cn.getMonth_p(), cn.getYear_p(), cn.getLocation(), cn.getRoute(),
                    cn.getDay_n(), cn.getMonth_n(), cn.getYear_n(), cn.getAddress(),
                    cn.getMessage(), cn.getSos(), cn.getPassword(), cn.getAnonymity(),
                    cn.getHelp(), cn.getLatitude(), cn.getLongitude());
            i++;
        }
        arrayContact = bubbleSort(arrayContact,1);//1-значит сортировка по имени, 0-по фамилии

        ForBinarySearch[] arrayContactS = new ForBinarySearch[contacts.size()];
        //из массива Contact в массив ForBinarySearch
        for(int i2=0;i2<arrayContact.length;i2++){
            arrayContactS[i2]= new ForBinarySearch(arrayContact[i2].getID(), arrayContact[i2].getName(), arrayContact[i2].getPhoneNumber(),
                    arrayContact[i2].getPhoneNumberMy(), arrayContact[i2].getSurname(), arrayContact[i2].getSecondName(),
                    arrayContact[i2].getMinute(), arrayContact[i2].getHour(), arrayContact[i2].getDay(), arrayContact[i2].getMonth(),
                    arrayContact[i2].getYear(), arrayContact[i2].getMinute_p(), arrayContact[i2].getHour_p(), arrayContact[i2].getDay_p(),
                    arrayContact[i2].getMonth_p(), arrayContact[i2].getYear_p(), arrayContact[i2].getLocation(), arrayContact[i2].getRoute(),
                    arrayContact[i2].getDay_n(), arrayContact[i2].getMonth_n(), arrayContact[i2].getYear_n(), arrayContact[i2].getAddress(),
                    arrayContact[i2].getMessage(), arrayContact[i2].getSos(), arrayContact[i2].getPassword(), arrayContact[i2].getAnonymity(),
                    arrayContact[i2].getHelp(), arrayContact[i2].getLatitude(), arrayContact[i2].getLongitude());
        }
        Integer[] arrayInteger = new Integer[contacts.size()];
        //из массива ForBinarySearch в массив Integer
        for(int i3=0;i3<arrayContactS.length;i3++){
            arrayInteger[i3] = arrayContactS[i3].numFSymbol;
        }

        int first1, last, num, item;
        num=arrayInteger.length;//количество елементов массива
        first1 = 0;//перв.ел-т
        last = num - 1;//посл.ел-т
        String x_str = name;
        char ch1=x_str.charAt(0);
        int x = (int) ch1;
        item=x;//искомый ел-т
        int result = binarySearch(arrayInteger, first1, last, item);
        if(result==-1){
            Toast toast = Toast.makeText(this, "Такого туриста не зареєстровано",Toast.LENGTH_LONG);
            toast.show();
        }else {

            for (ForBinarySearch cn : arrayContactS) {
                if (cn.numFSymbol == arrayInteger[result]) {
                    if (name.equals(cn.contact.getName())) {
                        anonymity = cn.contact.getAnonymity();
                        if (anonymity == 1) {
                            String log = "Прізвище: " + cn.contact.getSurname() + ", Ім'я: " + cn.contact.getName()
                                    + ", По батькові: " + cn.contact.getSecondName() + ", \nКонтактний номер: "
                                    + cn.contact.getPhoneNumber()
                                    + ", \nДата відправлення: " + cn.contact.getDay() + "." + cn.contact.getMonth() + "." + cn.contact.getYear()
                                    + ", \nЧас відправлення: " + cn.contact.getHour() + ":" + cn.contact.getMinute()
                                    + ", \nДата прибуття: " + cn.contact.getDay_p() + "." + cn.contact.getMonth_p() + "." + cn.contact.getYear_p()
                                    + ", \nЧас прибуття: " + cn.contact.getHour_p() + ":" + cn.contact.getMinute_p()
                                    + ", \nЛокація: " + cn.contact.getLocation() + ", \nМаршрут: " + cn.contact.getRoute()
                                    + ", \nПовідомлення: " + cn.contact.getMessage() + ", \nСигнал тривоги:" + cn.contact.getSos()
                                    + ", Надана допомога: " + cn.contact.getHelp() + "\n";
                            if ((cn.contact.getLatitude() != 0) & (cn.contact.getLongitude() != 0)) {
                                LatLng sydney = new LatLng(cn.contact.getLatitude(), cn.contact.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(sydney).title(cn.contact.getName() + " " + cn.contact.getSurname()));
                            }
                            common_string += log + "\n";
                            res_search = 1;//что-то найдено
                        } else {
                            String log = "Прізвище: " + cn.contact.getSurname() + ", Ім'я: " + cn.contact.getName()
                                    + ", По батькові: " + cn.contact.getSecondName() + ", \nКонтактний номер: "
                                    + cn.contact.getPhoneNumber() + ", Адреса: " + cn.contact.getAddress()
                                    + ", \nДата народження: " + cn.contact.getDay_n() + "." + cn.contact.getMonth_n() + "." + cn.contact.getYear_n()
                                    + ", \nДата відправлення: " + cn.contact.getDay() + "." + cn.contact.getMonth() + "." + cn.contact.getYear()
                                    + ", \nЧас відправлення: " + cn.contact.getHour() + ":" + cn.contact.getMinute()
                                    + ", \nДата прибуття: " + cn.contact.getDay_p() + "." + cn.contact.getMonth_p() + "." + cn.contact.getYear_p()
                                    + ", \nЧас прибуття: " + cn.contact.getHour_p() + ":" + cn.contact.getMinute_p()
                                    + ", \nЛокація: " + cn.contact.getLocation() + ", \nМаршрут: " + cn.contact.getRoute()
                                    + ", \nМісцезнаходження: широта-" + cn.contact.getLatitude()
                                    + ", довгота-" + cn.contact.getLongitude()
                                    + ", \nПовідомлення: " + cn.contact.getMessage() + ", \nСигнал тривоги:" + cn.contact.getSos()
                                    + ", Анонімність:" + cn.contact.getAnonymity()
                                    + ", Надана допомога: " + cn.contact.getHelp() + "\n";
                            if ((cn.contact.getLatitude() != 0) & (cn.contact.getLongitude() != 0)) {
                                LatLng sydney = new LatLng(cn.contact.getLatitude(), cn.contact.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(sydney).title(cn.contact.getName() + " " + cn.contact.getSurname()));
                            }
                            common_string += log + "\n";
                            res_search = 1;//что-то найдено
                        }
                    }
                }

            }
            if (res_search == 1) {
                textView.setText(common_string);
                etSearchName.setText("");
            } else {
                Toast toast = Toast.makeText(this, "Такого туриста не зареєстровано", Toast.LENGTH_LONG);
                toast.show();
                etSearchName.setText("");
            }

        }
    }


    /*
    //правильно, работает(без бинарного поиска)
    public void search_name(){
        textView.setText("");
        common_string="";
        res_search=0;
        List<Contact> contacts = db.getAllContacts();
        for (Contact cn : contacts) {
            if (name.equals(cn.getName())) {
                anonymity = cn.getAnonymity();
                if(anonymity==1) {
                    String log = "Прізвище: "+ cn.getSurname() + ", Ім'я: " + cn.getName()
                            + ", По батькові: " + cn.getSecondName() + ", \nКонтактний номер: "
                            + cn.getPhoneNumber()
                            + ", \nДата відправлення: " + cn.getDay() + "." + cn.getMonth() + "." + cn.getYear()
                            + ", \nЧас відправлення: " + cn.getHour() + ":" + cn.getMinute()
                            + ", \nДата прибуття: " + cn.getDay_p() + "." + cn.getMonth_p() + "." + cn.getYear_p()
                            + ", \nЧас прибуття: " + cn.getHour_p() + ":" + cn.getMinute_p()
                            + ", \nЛокація: " + cn.getLocation()+ ", \nМаршрут: "+ cn.getRoute()
                            + ", \nПовідомлення: " + cn.getMessage() + ", \nСигнал тривоги:" + cn.getSos()
                            + ", Надана допомога: " + cn.getHelp() + "\n";
                    if((cn.getLatitude()!=0)&(cn.getLongitude()!=0)) {
                        LatLng sydney = new LatLng(cn.getLatitude(), cn.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(sydney).title(cn.getName()+" "+cn.getSurname()));
                    }
                    common_string += log + "\n";
                    res_search=1;//что-то найдено
                }else {
                    String log = "Прізвище: "+ cn.getSurname() + ", Ім'я: " + cn.getName()
                            + ", По батькові: " + cn.getSecondName() + ", \nКонтактний номер: "
                            + cn.getPhoneNumber() + ", Адреса: " + cn.getAddress()
                            + ", \nДата народження: " + cn.getDay_n() + "." + cn.getMonth_n() + "." + cn.getYear_n()
                            + ", \nДата відправлення: " + cn.getDay() + "." + cn.getMonth() + "." + cn.getYear()
                            + ", \nЧас відправлення: " + cn.getHour() + ":" + cn.getMinute()
                            + ", \nДата прибуття: " + cn.getDay_p() + "." + cn.getMonth_p() + "." + cn.getYear_p()
                            + ", \nЧас прибуття: " + cn.getHour_p() + ":" + cn.getMinute_p()
                            + ", \nЛокація: " + cn.getLocation()+ ", \nМаршрут: "+ cn.getRoute()
                            + ", \nМісцезнаходження: широта-" + cn.getLatitude()
                            + ", довгота-" + cn.getLongitude()
                            + ", \nПовідомлення: " + cn.getMessage() + ", \nСигнал тривоги:" + cn.getSos()
                            + ", Анонімність:" + cn.getAnonymity()
                            + ", Надана допомога: " + cn.getHelp() + "\n";
                    if((cn.getLatitude()!=0)&(cn.getLongitude()!=0)) {
                        LatLng sydney = new LatLng(cn.getLatitude(), cn.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(sydney).title(cn.getName()+" "+cn.getSurname()));
                    }
                    common_string+=log + "\n";
                    res_search=1;//что-то найдено
                }
            }
        }
        if (res_search==1){
            textView.setText(common_string);
            etSearchName.setText("");
        }
        else {
            Toast toast = Toast.makeText(this, "Такого туриста не зареєстровано",Toast.LENGTH_LONG);
            toast.show();
            etSearchName.setText("");
        }
    }
    */


    public void search_surname(){
        textView.setText("");
        common_string="";
        res_search=0;
        List<Contact> contacts = db.getAllContacts();
        for (Contact cn : contacts) {
            if (surname.equals(cn.getSurname())) {
                anonymity = cn.getAnonymity();
                if(anonymity==1) {
                    String log = "Прізвище: "+ cn.getSurname() + ", Ім'я: " + cn.getName()
                            + ", По батькові: " + cn.getSecondName() + ", \nКонтактний номер: "
                            + cn.getPhoneNumber()
                            + ", \nДата відправлення: " + cn.getDay() + "." + cn.getMonth() + "." + cn.getYear()
                            + ", \nЧас відправлення: " + cn.getHour() + ":" + cn.getMinute()
                            + ", \nДата прибуття: " + cn.getDay_p() + "." + cn.getMonth_p() + "." + cn.getYear_p()
                            + ", \nЧас прибуття: " + cn.getHour_p() + ":" + cn.getMinute_p()
                            + ", \nЛокація: " + cn.getLocation()+ ", \nМаршрут: "+ cn.getRoute()
                            + ", \nПовідомлення: " + cn.getMessage() + ", \nСигнал тривоги:" + cn.getSos()
                            + ", Надана допомога: " + cn.getHelp() + "\n";
                    if((cn.getLatitude()!=0)&(cn.getLongitude()!=0)) {
                        LatLng sydney = new LatLng(cn.getLatitude(), cn.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(sydney).title(cn.getName()+" "+cn.getSurname()));
                    }
                    common_string += log + "\n";
                    res_search=1;//что-то найдено
                }else {
                    String log = "Прізвище: "+ cn.getSurname() + ", Ім'я: " + cn.getName()
                            + ", По батькові: " + cn.getSecondName() + ", \nКонтактний номер: "
                            + cn.getPhoneNumber() + ", Адреса: " + cn.getAddress()
                            + ", \nДата народження: " + cn.getDay_n() + "." + cn.getMonth_n() + "." + cn.getYear_n()
                            + ", \nДата відправлення: " + cn.getDay() + "." + cn.getMonth() + "." + cn.getYear()
                            + ", \nЧас відправлення: " + cn.getHour() + ":" + cn.getMinute()
                            + ", \nДата прибуття: " + cn.getDay_p() + "." + cn.getMonth_p() + "." + cn.getYear_p()
                            + ", \nЧас прибуття: " + cn.getHour_p() + ":" + cn.getMinute_p()
                            + ", \nЛокація: " + cn.getLocation()+ ", \nМаршрут: "+ cn.getRoute()
                            + ", \nМісцезнаходження: широта-" + cn.getLatitude()
                            + ", довгота-" + cn.getLongitude()
                            + ", \nПовідомлення: " + cn.getMessage() + ", \nСигнал тривоги:" + cn.getSos()
                            + ", Анонімність:" + cn.getAnonymity()
                            + ", Надана допомога: " + cn.getHelp() + "\n";
                    if((cn.getLatitude()!=0)&(cn.getLongitude()!=0)) {
                        LatLng sydney = new LatLng(cn.getLatitude(), cn.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(sydney).title(cn.getName()+" "+cn.getSurname()));
                    }
                    common_string+=log + "\n";
                    res_search=1;//что-то найдено
                }
            }
        }
        if (res_search==1){
            textView.setText(common_string);
            etSearchSurname.setText("");
        }
        else {
            Toast toast = Toast.makeText(this, "Такого туриста не зареєстровано",Toast.LENGTH_LONG);
            toast.show();
            etSearchSurname.setText("");
        }
    }

    public void show_missing(View view){
        mMap.clear();
        textView.setText("");
        common_string="";
        List<Contact> contacts = db.getAllContacts();
        for (Contact cn : contacts) {
            if(cn.getSos()==1) {
                if(anonymity==1) {
                    String log = "Прізвище: "+ cn.getSurname() + ", Ім'я: " + cn.getName()
                            + ", По батькові: " + cn.getSecondName() + ", \nКонтактний номер: "
                            + cn.getPhoneNumber()
                            + ", \nДата відправлення: " + cn.getDay() + "." + cn.getMonth() + "." + cn.getYear()
                            + ", \nЧас відправлення: " + cn.getHour() + ":" + cn.getMinute()
                            + ", \nДата прибуття: " + cn.getDay_p() + "." + cn.getMonth_p() + "." + cn.getYear_p()
                            + ", \nЧас прибуття: " + cn.getHour_p() + ":" + cn.getMinute_p()
                            + ", \nЛокація: " + cn.getLocation()+ ", \nМаршрут: "+ cn.getRoute()
                            + ", \nПовідомлення: " + cn.getMessage() + ", \nСигнал тривоги:" + cn.getSos()
                            + ", Надана допомога: " + cn.getHelp() + "\n";
                    if((cn.getLatitude()!=0)&(cn.getLongitude()!=0)) {
                        LatLng sydney = new LatLng(cn.getLatitude(), cn.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(sydney).title(cn.getName()+" "+cn.getSurname()));
                    }
                    common_string += log + "\n";
                    res_search=1;//что-то найдено
                }else {
                    String log = "Прізвище: "+ cn.getSurname() + ", Ім'я: " + cn.getName()
                            + ", По батькові: " + cn.getSecondName() + ", \nКонтактний номер: "
                            + cn.getPhoneNumber() + ", Адреса: " + cn.getAddress()
                            + ", \nДата народження: " + cn.getDay_n() + "." + cn.getMonth_n() + "." + cn.getYear_n()
                            + ", \nДата відправлення: " + cn.getDay() + "." + cn.getMonth() + "." + cn.getYear()
                            + ", \nЧас відправлення: " + cn.getHour() + ":" + cn.getMinute()
                            + ", \nДата прибуття: " + cn.getDay_p() + "." + cn.getMonth_p() + "." + cn.getYear_p()
                            + ", \nЧас прибуття: " + cn.getHour_p() + ":" + cn.getMinute_p()
                            + ", \nЛокація: " + cn.getLocation()+ ", \nМаршрут: "+ cn.getRoute()
                            + ", \nМісцезнаходження: широта-" + cn.getLatitude()
                            + ", довгота-" + cn.getLongitude()
                            + ", \nПовідомлення: " + cn.getMessage() + ", \nСигнал тривоги:" + cn.getSos()
                            + ", Анонімність:" + cn.getAnonymity()
                            + ", Надана допомога: " + cn.getHelp() + "\n";
                    if((cn.getLatitude()!=0)&(cn.getLongitude()!=0)) {
                        LatLng sydney = new LatLng(cn.getLatitude(), cn.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(sydney).title(cn.getName()+" "+cn.getSurname()));
                    }
                    common_string+=log + "\n";
                    res_search=1;//что-то найдено
                }
            }
        }
        if (res_search==1){
            textView.setText(common_string);
        }
        else {
            Toast toast = Toast.makeText(this, "Немає зниклих",Toast.LENGTH_LONG);
            toast.show();
        }
    }

/*
    //правильно, без сортировки
    public void show_all(View view){
        mMap.clear();
        textView.setText("");
        common_string="";
        List<Contact> contacts = db.getAllContacts();
        for (Contact cn : contacts) {
            String log = "Прізвище: "+ cn.getSurname() + ", Ім'я: " + cn.getName()
                    + ", По батькові: " + cn.getSecondName() + ", \nКонтактний номер: "
                    + cn.getPhoneNumber() +
                    ", \nМій номер: " + cn.getPhoneNumberMy() + ", Адреса: " + cn.getAddress()
                    + ", \nДата народження: " + cn.getDay_n() + "." + cn.getMonth_n() + "." + cn.getYear_n()
                    + ", \nДата відправлення: " + cn.getDay() + "." + cn.getMonth() + "." + cn.getYear()
                    + ", \nЧас відправлення: " + cn.getHour() + ":" + cn.getMinute()
                    + ", \nДата прибуття: " + cn.getDay_p() + "." + cn.getMonth_p() + "." + cn.getYear_p()
                    + ", \nЧас прибуття: " + cn.getHour_p() + ":" + cn.getMinute_p()
                    + ", \nЛокація: " + cn.getLocation()+ ", \nМаршрут: "+ cn.getRoute()
                    + ", \nМісцезнаходження: широта-" + cn.getLatitude()
                    + ", довгота-" + cn.getLongitude()
                    + ", \nПовідомлення: " + cn.getMessage() + ", \nСигнал тривоги:" + cn.getSos()
                    //+ ", Пароль: " + cn.getPassword()
                    + ", Анонімність:" + cn.getAnonymity()
                    + ", Надана допомога: " + cn.getHelp() + "\n";
            if((cn.getLatitude()!=0)&(cn.getLongitude()!=0)) {
                LatLng sydney = new LatLng(cn.getLatitude(), cn.getLongitude());
                mMap.addMarker(new MarkerOptions().position(sydney).title(cn.getName()+" "+cn.getSurname()));
            }
            common_string+=log + "\n";
            res_search=1;//что-то найдено
        }
        if (res_search==1){
            textView.setText(common_string);
        }
        else {
            Toast toast = Toast.makeText(this, "Немає зареєстрованих",Toast.LENGTH_LONG);
            toast.show();
        }
    }*/

    //сортировка пузырьком(0-surname, 1-name)
    public Contact[] bubbleSort(Contact[] array,int relay) {
        Contact buffer = new Contact();
        int i = 0;
        int goodPairsCounter = 0;
        while (true) {
            String x_str,y_str;
            if(relay==0){
            x_str = array[i].getSurname();
            y_str = array[i + 1].getSurname();
            }else {
                x_str = array[i].getName();
                y_str = array[i + 1].getName();
            }
            char ch1=x_str.charAt(0);
            char ch2=y_str.charAt(0);
            int x = (int) ch1;
            int y = (int) ch2;
            if (x > y) {
                buffer=assign(buffer,array[i]);
                array[i]=assign(array[i],array[i+1]);
                array[i+1]=assign(array[i+1],buffer);
                goodPairsCounter = 0;
            } else {
                goodPairsCounter++;
            }
            i++;
            if (i == array.length - 1) {
                i = 0;
            }
            if (goodPairsCounter == array.length - 1) break;
        }
        return array;
    }

    //переприсваивание
    public Contact assign(Contact c1, Contact c2){
        c1.setID(c2.getID());
        c1.setName(c2.getName());
        c1.setPhoneNumber(c2.getPhoneNumber());
        c1.setPhoneNumberMy(c2.getPhoneNumberMy());
        c1.setSurname(c2.getSurname());
        c1.setSecondName(c2.getSecondName());
        c1.setMinute(c2.getMinute());
        c1.setHour(c2.getHour());
        c1.setDay(c2.getDay());
        c1.setMonth(c2.getMonth());
        c1.setYear(c2.getYear());
        c1.setMinute_p(c2.getMinute_p());
        c1.setHour_p(c2.getHour_p());
        c1.setDay_p(c2.getDay_p());
        c1.setMonth_p(c2.getMonth_p());
        c1.setYear_p(c2.getYear_p());
        c1.setLocation(c2.getLocation());
        c1.setRoute(c2.getRoute());
        c1.setDay_n(c2.getDay_n());
        c1.setMonth_n(c2.getMonth_n());
        c1.setYear_n(c2.getYear_n());
        c1.setAddress(c2.getAddress());
        c1.setMessage(c2.getMessage());
        c1.setSos(c2.getSos());
        c1.setPassword(c2.getPassword());
        c1.setAnonymity(c2.getAnonymity());
        c1.setHelp(c2.getHelp());
        c1.setLatitude(c2.getLatitude());
        c1.setLongitude(c2.getLongitude());
        return c1;
    }

    //для творчества(с сортировкой)
    public void show_all(View view){
        mMap.clear();
        textView.setText("");
        common_string="";
        List<Contact> contacts = db.getAllContacts();
        Contact[] arrayContact = new Contact[contacts.size()];
        int i=0; //из списка в массив
        for(Contact cn:contacts){
            arrayContact[i]= new Contact(cn.getID(), cn.getName(), cn.getPhoneNumber(),
                    cn.getPhoneNumberMy(), cn.getSurname(), cn.getSecondName(),
                    cn.getMinute(), cn.getHour(), cn.getDay(), cn.getMonth(),
                    cn.getYear(), cn.getMinute_p(), cn.getHour_p(), cn.getDay_p(),
                    cn.getMonth_p(), cn.getYear_p(), cn.getLocation(), cn.getRoute(),
                    cn.getDay_n(), cn.getMonth_n(), cn.getYear_n(), cn.getAddress(),
                    cn.getMessage(), cn.getSos(), cn.getPassword(), cn.getAnonymity(),
                    cn.getHelp(), cn.getLatitude(), cn.getLongitude());
            i++;
        }
        arrayContact = bubbleSort(arrayContact,0);
        //проход по массиву
        for (int i1=0;i1<arrayContact.length;i1++) {
            String log = "Прізвище: "+ arrayContact[i1].getSurname() + ", Ім'я: " + arrayContact[i1].getName()
                    + ", По батькові: " + arrayContact[i1].getSecondName() + ", \nКонтактний номер: "
                    + arrayContact[i1].getPhoneNumber() +
                    ", \nМій номер: " + arrayContact[i1].getPhoneNumberMy() + ", Адреса: " + arrayContact[i1].getAddress()
                    + ", \nДата народження: " + arrayContact[i1].getDay_n() + "." + arrayContact[i1].getMonth_n() + "." + arrayContact[i1].getYear_n()
                    + ", \nДата відправлення: " + arrayContact[i1].getDay() + "." + arrayContact[i1].getMonth() + "." + arrayContact[i1].getYear()
                    + ", \nЧас відправлення: " + arrayContact[i1].getHour() + ":" + arrayContact[i1].getMinute()
                    + ", \nДата прибуття: " + arrayContact[i1].getDay_p() + "." + arrayContact[i1].getMonth_p() + "." + arrayContact[i1].getYear_p()
                    + ", \nЧас прибуття: " + arrayContact[i1].getHour_p() + ":" + arrayContact[i1].getMinute_p()
                    + ", \nЛокація: " + arrayContact[i1].getLocation()+ ", \nМаршрут: "+ arrayContact[i1].getRoute()
                    + ", \nМісцезнаходження: широта-" + arrayContact[i1].getLatitude()
                    + ", довгота-" + arrayContact[i1].getLongitude()
                    + ", \nПовідомлення: " + arrayContact[i1].getMessage() + ", \nСигнал тривоги:" + arrayContact[i1].getSos()
                    //+ ", Пароль: " + cn.getPassword()
                    + ", Анонімність:" + arrayContact[i1].getAnonymity()
                    + ", Надана допомога: " + arrayContact[i1].getHelp() + "\n";
            if((arrayContact[i1].getLatitude()!=0)&(arrayContact[i1].getLongitude()!=0)) {
                LatLng sydney = new LatLng(arrayContact[i1].getLatitude(), arrayContact[i1].getLongitude());
                mMap.addMarker(new MarkerOptions().position(sydney).title(arrayContact[i1].getName()+" "+arrayContact[i1].getSurname()));
            }
            common_string+=log + "\n";
            res_search=1;//что-то найдено
        }
        if (res_search==1){
            textView.setText(common_string);
        }
        else {
            Toast toast = Toast.makeText(this, "Немає зареєстрованих",Toast.LENGTH_LONG);
            toast.show();
        }
        Toast toast = Toast.makeText(this, "Відсортовано за алфавітом(прізвища)",Toast.LENGTH_LONG);
        toast.show();

    }
}
