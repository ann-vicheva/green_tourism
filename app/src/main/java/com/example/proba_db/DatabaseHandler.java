package com.example.proba_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 07.09.2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 15;
    private static final String DATABASE_NAME = "contactsManager.db";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_PH_NO_MY = "phone_number_my";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_SECOND_NAME = "second_name";
    private static final String KEY_MINUTE = "minute";
    private static final String KEY_HOUR = "hour";
    private static final String KEY_DAY = "day";
    private static final String KEY_MONTH = "month";
    private static final String KEY_YEAR = "year";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_ROUTE = "route";
    private static final String KEY_MINUTE_P = "minute_p";
    private static final String KEY_HOUR_P = "hour_p";
    private static final String KEY_DAY_P = "day_p";
    private static final String KEY_MONTH_P = "month_p";
    private static final String KEY_YEAR_P = "year_p";
    private static final String KEY_DAY_N = "day_n";
    private static final String KEY_MONTH_N = "month_n";
    private static final String KEY_YEAR_N = "year_n";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_SOS = "sos";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ANONYMITY = "anonymity";
    private static final String KEY_HELP = "help";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT,"+ KEY_PH_NO_MY + " TEXT," + KEY_SURNAME + " TEXT,"
                + KEY_SECOND_NAME + " TEXT," + KEY_MINUTE + " INTEGER,"
                + KEY_HOUR + " INTEGER," + KEY_DAY + " INTEGER,"
                + KEY_MONTH + " INTEGER," + KEY_YEAR + " INTEGER,"
                + KEY_MINUTE_P + " INTEGER," + KEY_HOUR_P + " INTEGER,"
                + KEY_DAY_P + " INTEGER," + KEY_MONTH_P + " INTEGER,"
                + KEY_YEAR_P + " INTEGER," + KEY_LOCATION + " TEXT,"
                + KEY_ROUTE + " TEXT,"+ KEY_DAY_N + " INTEGER,"
                + KEY_MONTH_N + " INTEGER," + KEY_YEAR_N + " INTEGER,"
                + KEY_ADDRESS + " TEXT," + KEY_MESSAGE + " TEXT,"
                + KEY_SOS + " INTEGER," + KEY_PASSWORD + " TEXT,"
                + KEY_ANONYMITY + " INTEGER, " + KEY_HELP + " INTEGER,"
                + KEY_LATITUDE + " REAL," + KEY_LONGITUDE + " REAL" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        onCreate(db);
    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());
        values.put(KEY_PH_NO_MY, contact.getPhoneNumberMy());
        values.put(KEY_SURNAME, contact.getSurname());
        values.put(KEY_SECOND_NAME, contact.getSecondName());
        values.put(KEY_MINUTE, contact.getMinute());
        values.put(KEY_HOUR, contact.getHour());
        values.put(KEY_DAY, contact.getDay());
        values.put(KEY_MONTH, contact.getMonth());
        values.put(KEY_YEAR, contact.getYear());
        values.put(KEY_MINUTE_P, contact.getMinute_p());
        values.put(KEY_HOUR_P, contact.getHour_p());
        values.put(KEY_DAY_P, contact.getDay_p());
        values.put(KEY_MONTH_P, contact.getMonth_p());
        values.put(KEY_YEAR_P, contact.getYear_p());
        values.put(KEY_LOCATION, contact.getLocation());
        values.put(KEY_ROUTE, contact.getRoute());
        values.put(KEY_DAY_N, contact.getDay_n());
        values.put(KEY_MONTH_N, contact.getMonth_n());
        values.put(KEY_YEAR_N, contact.getYear_n());
        values.put(KEY_ADDRESS, contact.getAddress());
        values.put(KEY_MESSAGE, contact.getMessage());
        values.put(KEY_SOS, contact.getSos());
        values.put(KEY_PASSWORD, contact.getPassword());
        values.put(KEY_ANONYMITY, contact.getAnonymity());
        values.put(KEY_HELP, contact.getHelp());
        values.put(KEY_LATITUDE, contact.getLatitude());
        values.put(KEY_LONGITUDE, contact.getLongitude());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }


    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO, KEY_PH_NO_MY, KEY_SURNAME, KEY_SECOND_NAME,
                        KEY_MINUTE, KEY_HOUR, KEY_DAY, KEY_MONTH, KEY_YEAR,
                        KEY_MINUTE_P, KEY_HOUR_P, KEY_DAY_P, KEY_MONTH_P,
                        KEY_YEAR_P, KEY_LOCATION, KEY_ROUTE, KEY_DAY_N,
                        KEY_MONTH_N, KEY_YEAR_N, KEY_ADDRESS, KEY_MESSAGE,
                        KEY_SOS, KEY_PASSWORD, KEY_ANONYMITY, KEY_HELP,
                        KEY_LATITUDE, KEY_LONGITUDE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5), Integer.parseInt(cursor.getString(6)),
                Integer.parseInt(cursor.getString(7)), Integer.parseInt(cursor.getString(8)),
                Integer.parseInt(cursor.getString(9)), Integer.parseInt(cursor.getString(10)),
                Integer.parseInt(cursor.getString(11)), Integer.parseInt(cursor.getString(12)),
                Integer.parseInt(cursor.getString(13)), Integer.parseInt(cursor.getString(14)),
                Integer.parseInt(cursor.getString(15)), cursor.getString(16), cursor.getString(17),
                Integer.parseInt(cursor.getString(18)), Integer.parseInt(cursor.getString(19)),
                Integer.parseInt(cursor.getString(20)), cursor.getString(21), cursor.getString(22),
                Integer.parseInt(cursor.getString(23)), cursor.getString(24),
                Integer.parseInt(cursor.getString(25)), Integer.parseInt(cursor.getString(26)),
                Double.parseDouble(cursor.getString(27)), Double.parseDouble(cursor.getString(28)));

        return contact;
    }


    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contact.setPhoneNumberMy(cursor.getString(3));
                contact.setSurname(cursor.getString(4));
                contact.setSecondName(cursor.getString(5));
                contact.setMinute(Integer.parseInt(cursor.getString(6)));
                contact.setHour(Integer.parseInt(cursor.getString(7)));
                contact.setDay(Integer.parseInt(cursor.getString(8)));
                contact.setMonth(Integer.parseInt(cursor.getString(9)));
                contact.setYear(Integer.parseInt(cursor.getString(10)));
                contact.setMinute_p(Integer.parseInt(cursor.getString(11)));
                contact.setHour_p(Integer.parseInt(cursor.getString(12)));
                contact.setDay_p(Integer.parseInt(cursor.getString(13)));
                contact.setMonth_p(Integer.parseInt(cursor.getString(14)));
                contact.setYear_p(Integer.parseInt(cursor.getString(15)));
                contact.setLocation(cursor.getString(16));
                contact.setRoute(cursor.getString(17));
                contact.setDay_n(Integer.parseInt(cursor.getString(18)));
                contact.setMonth_n(Integer.parseInt(cursor.getString(19)));
                contact.setYear_n(Integer.parseInt(cursor.getString(20)));
                contact.setAddress(cursor.getString(21));
                contact.setMessage(cursor.getString(22));
                contact.setSos(Integer.parseInt(cursor.getString(23)));
                contact.setPassword(cursor.getString(24));
                contact.setAnonymity(Integer.parseInt(cursor.getString(25)));
                contact.setHelp(Integer.parseInt(cursor.getString(26)));
                contact.setLatitude(Double.parseDouble(cursor.getString(27)));
                contact.setLongitude(Double.parseDouble(cursor.getString(28)));

                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList;
    }


    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());
        values.put(KEY_PH_NO_MY, contact.getPhoneNumberMy());
        values.put(KEY_SURNAME, contact.getSurname());
        values.put(KEY_SECOND_NAME, contact.getSecondName());
        values.put(KEY_MINUTE, contact.getMinute());
        values.put(KEY_HOUR, contact.getHour());
        values.put(KEY_DAY, contact.getDay());
        values.put(KEY_MONTH, contact.getMonth());
        values.put(KEY_YEAR, contact.getYear());
        values.put(KEY_MINUTE_P, contact.getMinute_p());
        values.put(KEY_HOUR_P, contact.getHour_p());
        values.put(KEY_DAY_P, contact.getDay_p());
        values.put(KEY_MONTH_P, contact.getMonth_p());
        values.put(KEY_YEAR_P, contact.getYear_p());
        values.put(KEY_LOCATION, contact.getLocation());
        values.put(KEY_ROUTE, contact.getRoute());
        values.put(KEY_DAY_N, contact.getDay_n());
        values.put(KEY_MONTH_N, contact.getMonth_n());
        values.put(KEY_YEAR_N, contact.getYear_n());
        values.put(KEY_ADDRESS, contact.getAddress());
        values.put(KEY_MESSAGE, contact.getMessage());
        values.put(KEY_SOS, contact.getSos());
        values.put(KEY_PASSWORD, contact.getPassword());
        values.put(KEY_ANONYMITY, contact.getAnonymity());
        values.put(KEY_HELP, contact.getHelp());
        values.put(KEY_LATITUDE, contact.getLatitude());
        values.put(KEY_LONGITUDE, contact.getLongitude());

        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }


    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[] { String.valueOf(contact.getID()) });
        db.close();
    }


    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, null, null);
        db.close();
    }


    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}
