package com.whf.sqlite;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	 
    // All Static variables
	
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "MonitoringApp";
 
    // Contacts table name
    // private static final String TABLE_DECISION = "decision";
    private static final String TABLE_TRANSACTION="transaction";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATACENTER="dataCenter";                                                                                      
    private static final String KEY_DATATYPE="dataType";
    private static final String KEY_DATASUBTYPE="dataSubType";
    private static final String KEY_DESCRIPTION="description";
    private static final String KEY_TIME="time";
    private static final String KEY_TOTALCOUNT="totalCount";
    private static final String KEY_FAILURECOUNT="failureCount";
    private static final String KEY_FAILURERATE="failureRate";
    private static final String KEY_AVERAGE="average";
    private static final String KEY_TILE95="tile95";
    private static final String KEY_MIN="min";
    private static final String KEY_MAX="max";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + TABLE_TRANSACTION + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DATACENTER + " TEXT,"
                + KEY_DATATYPE+" TEXT,"+ KEY_DATASUBTYPE+" TEXT,"
                + KEY_DESCRIPTION+" TEXT,"+ KEY_TIME+" TEXT,"
                + KEY_TOTALCOUNT+" TEXT,"+ KEY_FAILURECOUNT+" TEXT,"
                + KEY_FAILURERATE+" TEXT,"+ KEY_AVERAGE+" TEXT,"
                + KEY_TILE95+" TEXT,"+ KEY_MIN+" TEXT,"
                + KEY_DATATYPE+" TEXT,"
                + KEY_MAX + " TEXT" + ")";
        db.execSQL(CREATE_TRANSACTION_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
     // Adding new transaction table
    public void addTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_DATACENTER, transaction.getDataCenter());
        values.put(KEY_DATATYPE, transaction.getDataType());
        values.put(KEY_DATASUBTYPE, transaction.getDataSubType());
        values.put(KEY_DESCRIPTION, transaction.getDescription());
        values.put(KEY_TIME, transaction.getTime());
        values.put(KEY_TOTALCOUNT, transaction.getTotalCount());
        values.put(KEY_FAILURECOUNT, transaction.getFailureCount());
        values.put(KEY_FAILURERATE, transaction.getFailureRate());
        values.put(KEY_AVERAGE, transaction.getAverage());
        values.put(KEY_TILE95, transaction.getTile95());
        values.put(KEY_MIN, transaction.getMin());
        values.put(KEY_MAX, transaction.getMax());
 
        // Inserting Row
        db.insert(TABLE_TRANSACTION, null, values);
        db.close(); // Closing database connection
    }
 
    
    // query by time
    public ArrayList<Transaction> getTransactionByTime(long start,long end) {
    	ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTION + "WHERE "+KEY_TIME+">? AND "+KEY_TIME+"<? ORDER BY "+KEY_TIME;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(start),String.valueOf(end)});
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Transaction transaction= new Transaction(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getString(4),cursor.getString(5), cursor.getString(6),cursor.getString(7),
                        cursor.getString(8),cursor.getString(9), cursor.getString(10),cursor.getString(11), cursor.getString(12));
                
                // Adding contact to list
            	transactionList.add(transaction);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return transactionList;
    }
    
    // query by time
    public ArrayList<Transaction> getTransactionByArgument(long start,long end,String subType) {
    	ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTION + "WHERE "+KEY_TIME+">? AND "+KEY_TIME+"<? AND "+KEY_DATASUBTYPE+"=?"+ "ORDER BY "+KEY_TIME;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(start),String.valueOf(end),subType});
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Transaction transaction= new Transaction(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getString(4),cursor.getString(5), cursor.getString(6),cursor.getString(7),
                        cursor.getString(8),cursor.getString(9), cursor.getString(10),cursor.getString(11), cursor.getString(12));
                
                // Adding contact to list
            	transactionList.add(transaction);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return transactionList;
    }
    
    
    // Getting single transaction
    public Transaction getTransaction(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_TRANSACTION, new String[] { KEY_ID,KEY_DATACENTER,KEY_DATATYPE,KEY_DATASUBTYPE,KEY_DESCRIPTION,KEY_TIME,KEY_TOTALCOUNT,KEY_FAILURECOUNT,KEY_FAILURERATE,KEY_AVERAGE,KEY_TILE95,KEY_MIN,KEY_MAX }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Transaction transaction = new Transaction(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getString(4),cursor.getString(5), cursor.getString(6),cursor.getString(7),
                cursor.getString(8),cursor.getString(9), cursor.getString(10),cursor.getString(11), cursor.getString(12));
        // return contact
        return transaction;
    }
     
    // Getting All Contacts
    public ArrayList<Transaction> getAllTransaction() {
    	ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTION;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Transaction transaction= new Transaction(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getString(4),cursor.getString(5), cursor.getString(6),cursor.getString(7),
                        cursor.getString(8),cursor.getString(9), cursor.getString(10),cursor.getString(11), cursor.getString(12));
                
                // Adding contact to list
            	transactionList.add(transaction);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return transactionList;
    }
 
    // Updating single contact
    public int updateTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATACENTER, transaction.getDataCenter());
        values.put(KEY_DATATYPE, transaction.getDataType());
        values.put(KEY_DATASUBTYPE, transaction.getDataSubType());
        values.put(KEY_DESCRIPTION, transaction.getDescription());
        values.put(KEY_TIME, transaction.getTime());
        values.put(KEY_TOTALCOUNT, transaction.getTotalCount());
        values.put(KEY_FAILURECOUNT, transaction.getFailureCount());
        values.put(KEY_FAILURERATE, transaction.getFailureRate());
        values.put(KEY_AVERAGE, transaction.getAverage());
        values.put(KEY_TILE95, transaction.getTile95());
        values.put(KEY_MIN, transaction.getMin());
        values.put(KEY_MAX, transaction.getMax());
 
        // updating row
        return db.update(TABLE_TRANSACTION, values, KEY_ID + " = ?",
                new String[] { String.valueOf(transaction.get_id()) });
    }
 
    // Deleting single contact
    public void deleteTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRANSACTION, KEY_ID + " = ?",
                new String[] { String.valueOf(transaction.get_id()) });
        db.close();
    }
 
    // Getting contacts Count
    public int getTransactionsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TRANSACTION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
 
}
