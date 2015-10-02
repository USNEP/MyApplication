package Db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import model.History;
import model.ReportData;
import model.Status;
import model.Type;

/**
 * Created by ashok on 9/13/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 10;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_TYPE = "types";
    private static final String TABLE_STATUS = "status";



    //common table column names
    private static final String KEY_ID = "id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_IN = "in";
    private static final String KEY_CASH = "cash";
    private static final String KEY_BANK = "bank";
    private static final String KEY_LOAN = "loan";
    private static final String KEY_IVST = "ivst";

    //history table  columns names
    private static final String KEY_SUB_TYPE = "sub_type";
    private static final String KEY_IO = "io";
    private static final String KEY_CB = "cb";
    private static final String KEY_AMOUNT = "amt";
    private static final String KEY_DATE = "date";
    private static final String KEY_DESCRIPTION = "dcpn";
    private static final String KEY_HEAD = "head";





    //boolean string references





    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TYPE + " TEXT,"
                + KEY_SUB_TYPE + " TEXT,"+ KEY_HEAD + " TEXT," +KEY_IO + " TEXT,"+KEY_CB + " TEXT,"
                +KEY_AMOUNT + " TEXT,"+KEY_DATE + " TEXT,"+KEY_DESCRIPTION + " TEXT"+ ")";
        String CREATE_TYPE_TABLE = "CREATE TABLE " + TABLE_TYPE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_HEAD + " TEXT,"
                + KEY_TYPE + " TEXT," +KEY_SUB_TYPE + "  TEXT NOT NULL UNIQUE)";
        String CREATE_STATUS_TABLE = "CREATE TABLE " + TABLE_STATUS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CASH + " TEXT,"
                + KEY_BANK + " TEXT," +KEY_LOAN + " TEXT,"+KEY_IVST +  ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_TYPE_TABLE);
        db.execSQL(CREATE_STATUS_TABLE);
//        System.out.println("Database created ............................................................");
//

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUS);
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void onStart(){
        if(getStatus().get_id()==0)
        {   SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_CASH, "0"); // History Name
            values.put(KEY_BANK, "0"); // History Phone
            values.put(KEY_LOAN, "0");
            values.put(KEY_IVST, "0");
            db.insert(TABLE_STATUS, null, values);
            db.close();
        }
    }
  public  void addHistory(History contact) {
      System.out.println("adding history    .....................");
      System.out.println(contact.get_head());
        long value= getType(contact.get_sub_type()).get_id();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, contact.get_type()); // History Name
        values.put(KEY_SUB_TYPE,String.valueOf(value)); // History Phone
         values.put(KEY_HEAD,contact.get_head()); // History Phone
        values.put(KEY_IO, contact.get_io());
        values.put(KEY_CB, contact.get_cb());
        values.put(KEY_AMOUNT, contact.get_amount());
        values.put(KEY_DATE, contact.get_date());
        values.put(KEY_DESCRIPTION, contact.get_description());
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }
    public  boolean addType(Type contact) {
        SQLiteDatabase db = this.getWritableDatabase();
    try {
    ContentValues values = new ContentValues();
    values.put(KEY_HEAD, contact.get_head()); // History Name
    values.put(KEY_TYPE, contact.get_type());
    values.put(KEY_SUB_TYPE, contact.get_sub_type());
    db.insert(TABLE_TYPE, null, values);
    System.out.println("Type added >>>>>>>>>>");
    db.close();
    }catch(Exception e) {
    e.printStackTrace();return false;
        }
    return true;}
    public Type getType(String type) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TYPE, new String[]{KEY_ID,KEY_HEAD,KEY_TYPE,KEY_SUB_TYPE}, KEY_SUB_TYPE + "=?",
                new String[]{type}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
            Type t=new Type(Long.parseLong(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));

        return t;
    }

    // Getting single contact
   public History getContact(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_TYPE, KEY_SUB_TYPE,KEY_HEAD, KEY_IO, KEY_CB, KEY_AMOUNT, KEY_DATE, KEY_DESCRIPTION}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        History contact = new History(Long.parseLong(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(2),cursor.getString(3)==KEY_IN?true:false,cursor.getString(4)==KEY_CASH?true:false,
                Double.valueOf(cursor.getString(5)),cursor.getString(6),cursor.getString(7));
        // return contact
        return contact;
    }
    public List<Type> getTypes(String head, String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Type> contactList = new ArrayList<Type>();
        // Select All Query
        String whereClause = KEY_TYPE+"='"+type+"'";
        System.out.println("WHERE Cluse   " + whereClause);
        Cursor cursor = db.query(TABLE_TYPE, new String[]{KEY_ID,KEY_SUB_TYPE},whereClause , null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
            Type t=new Type();
                t.set_id(Long.parseLong(cursor.getString(0)));
                t.set_sub_type(cursor.getString(1));
                contactList.add(t);
            } while (cursor.moveToNext());
        }

    return contactList;
    }
//    public List<Type> getAllTypes() {
//        List<Type> contactList = new ArrayList<Type>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_TYPE;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Type contact = new Type();
//                contact.set_id(Long.parseLong(cursor.getString(0)));
//                contact.set_head(cursor.getString(1));
//                contact.set_sub_type(cursor.getString(2));
//                contact.set_type(cursor.getString(3));
//              System.out.println("......................................................");
//                System.out.println(contact.get_head()+"    "+contact.get_type()+"  "+contact.get_sub_type());
//
//                // Adding contact to list
//                contactList.add(contact);
//            } while (cursor.moveToNext());
//        }
//
//        // return contact list
//        return contactList;
//    }
    // Getting All Contacts
    public List<History> getAllContacts() {
        List<History> contactList = new ArrayList<History>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                History contact = new History();
                contact.set_id(Long.parseLong(cursor.getString(0)));
                contact.set_type(cursor.getString(1));
                contact.set_sub_type(cursor.getString(2));
                contact.set_head(cursor.getString(3));

                contact.set_io(cursor.getString(4) == KEY_IN ? true : false);

                contact.set_cb(cursor.getString(5) == KEY_CASH ? true : false);
                contact.set_amount(Double.valueOf(cursor.getString(6)));
                contact.set_date(cursor.getString(7));
                contact.set_description(cursor.getString(8));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
    public Status getStatus() {
        String selectQuery = "SELECT  * FROM " + TABLE_STATUS;
        Status contact = new Status();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                contact.set_id(Long.parseLong(cursor.getString(0)));
                contact.set_cash(cursor.getString(1));
                contact.set_bank(cursor.getString(2));
                contact.set_loan(cursor.getString(3));
                contact.set_investment(cursor.getString(4));
                // Adding contact to list
            } while (cursor.moveToNext());
        }
//        Cursor cursor = db.query(TABLE_STATUS, new String[] { KEY_ID,
//                        KEY_CASH, KEY_BANK, KEY_LOAN, KEY_CB, KEY_IVST}, KEY_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();

//        Status contact = new Status(Long.parseLong(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(3));
        // return contact
        return contact;
    }
    public int updateStatus(Status st) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CASH, st.get_cash()); // History Name
        values.put(KEY_BANK, st.get_bank()); // History Phone
        values.put(KEY_LOAN, st.get_loan());
        values.put(KEY_IVST, st.get_investment());
        return db.update(TABLE_STATUS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(st.get_id()) });
    }
    // Updating single contact
    public int updateContact(History contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, contact.get_type()); // History Name
        values.put(KEY_SUB_TYPE, contact.get_sub_type()); // History Phone
        values.put(KEY_HEAD, contact.get_head()); // History Phone

        values.put(KEY_IO, contact.get_io());
        values.put(KEY_CB, contact.get_cb());
        values.put(KEY_AMOUNT, contact.get_amount());
        values.put(KEY_DATE, contact.get_date());
        values.put(KEY_DESCRIPTION, contact.get_description());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.get_id()) });
    }

    // Deleting single contact
    public void deleteContact(History contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.get_id())});
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


    public void deleteType(String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TYPE, KEY_SUB_TYPE + " = ?",
                new String[]{type});
        db.close();
    }
    public int updateType(String contact,String updt) {
       Type t= getType(contact);
        t.set_sub_type(updt);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_HEAD, t.get_head()); // History Name
        values.put(KEY_TYPE, t.get_type()); // History Name
        values.put(KEY_SUB_TYPE, t.get_sub_type()); // History Phone
        // updating row
        return db.update(TABLE_TYPE, values, KEY_SUB_TYPE + " = ?",
                new String[] { contact });
    }

    public ReportData getIndvReport(String type,String startDate,String endDate){
        ReportData rpData=new ReportData();
        String qry="SELECT SUM("+KEY_AMOUNT+") FROM "+TABLE_CONTACTS+" WHERE "+KEY_TYPE+
                " = '"+type+"' AND "+KEY_DATE+" >= '"+startDate+"' AND "+KEY_DATE+" <='"+endDate+"';";
        System.out.println(qry);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()) {
            do {try {
                rpData.setAmount(Double.parseDouble(cursor.getString(0)));
            }catch(Exception e){
               e.printStackTrace();
                rpData.setAmount(0);

            }
                rpData.setType(type);
            } while (cursor.moveToNext());
        }
        System.out.println("Amount::::::::::" + rpData.getAmount());
        return rpData;
    }
    public ReportData getReportBySubType(String type,String subType,String startDate,String endDate){
        ReportData rpData=new ReportData();
        String type_id = String.valueOf(getType(subType).get_id());
        String qry="SELECT SUM("+KEY_AMOUNT+") FROM "+TABLE_CONTACTS+" WHERE "+KEY_SUB_TYPE+" = '"+type_id+"' AND "+KEY_TYPE+" = '"+type+
                "' AND "+KEY_DATE+" >= '"+startDate+"' AND "+KEY_DATE+" <='"+endDate+"';";;
        System.out.println(qry);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()) {
            do {try {
                rpData.setAmount(Double.parseDouble(cursor.getString(0)));
            }catch(Exception e){
                e.printStackTrace();
                rpData.setAmount(0);

            }
                rpData.setType(subType);
            } while (cursor.moveToNext());
        }
        System.out.println("Amount::::::::::"+rpData.getAmount());
        return rpData;
    }
    public List<History> getistoryByDate(String startDate,String endDate,String type,String head) {
        List<History> contactList = new ArrayList<History>();
        String type_id = String.valueOf(getType(type).get_id());
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS+" WHERE "+KEY_SUB_TYPE+" = '"+type_id+"' AND "+KEY_TYPE+" = '"+
                head+"' AND "+KEY_DATE+" >= '"+startDate+"' AND "+KEY_DATE+" <='"+endDate+"';";
        System.out.println(selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                History contact = new History();
                contact.set_id(Long.parseLong(cursor.getString(0)));
                contact.set_type(cursor.getString(1));
                contact.set_sub_type(cursor.getString(2));
                contact.set_head(cursor.getString(3));

                contact.set_io(cursor.getString(4) == KEY_IN ? true : false);

                contact.set_cb(cursor.getString(5) == KEY_CASH ? true : false);
                contact.set_amount(Double.valueOf(cursor.getString(6)));
                contact.set_date(cursor.getString(7));
                contact.set_description(cursor.getString(8));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


//    public boolean getContactByPhNo(String phNo){
//        boolean response=false;
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
//                        KEY_NAME, KEY_PH_NO }, KEY_PH_NO + "=?",
//                new String[] { String.valueOf(phNo) }, null, null, null, null);
//        if (cursor != null)
//            response=true;
//        return response;
//
//    }

}