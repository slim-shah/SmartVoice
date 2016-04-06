package com.example.slim_shah.smartvoice;
/**
 * Created by Slim_Shah on 28-Mar-16.
 */
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;

public class MyDbHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DataDB.db";
    public static final String TABLE_DATA = "data";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATANAME = "dataname";

    //We need to pass database information along to superclass
    public MyDbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_DATA + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATANAME + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        onCreate(db);
    }

    //Add a new row to the database
    public void addProduct(Data data){
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATANAME, data.get_dataname());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_DATA, null, values);
        db.close();
    }

    //Delete a product from the database
    public void deleteProduct(String productName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_DATA + " WHERE " + COLUMN_DATANAME + "=\"" + productName + "\";");
    }

    public ArrayList<String> databaseToString(){
        String dbString = "";
        ArrayList<String> ar = new ArrayList<String>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_DATA + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("dataname")) != null) {
                dbString = c.getString(c.getColumnIndex("dataname"));
                //dbString += "\n";
                ar.add(dbString);
            }
            c.moveToNext();
        }
        db.close();
        return ar;
    }
}
