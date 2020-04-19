package com.example.assignment5_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLDatabase extends SQLiteOpenHelper {

    public static final String COL_2 = "ITEM";
    public static final String COL_3 = "DATE";
    public static final String COL_4 = "PRICE";

    public static final String DATABASE_NAME = "database4.db";

    public static final String TABLE_NAME = "Spending";

    public SQLDatabase(Context context){
        super(context, DATABASE_NAME, null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, ITEM varchar(250), DATE varchar(250), PRICE DOUBLE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int NewVersion){
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    public Cursor retrieveAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return result;
    }

    public boolean addRow(rowData model){
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, model.mItem);
        contentValues.put(COL_3, model.mDate);
        contentValues.put(COL_4, model.mPrice);
        long result = database.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public static class rowData {
        public String mDate, mItem;
        public double mPrice;
    }

    public Cursor filterDB(String priceFloor, String priceCeil, String dateFloor, String dateCeil){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor result = null;

        Double priceFloor2 = null;
        Double priceCeil2 = null;

        if (!priceCeil.isEmpty())
        {
            priceCeil2 = Double.parseDouble(priceCeil);
        }
        if (!priceFloor.isEmpty())
        {
            priceFloor2 = Double.parseDouble(priceFloor);
        }

        if (priceFloor2 != null && priceCeil2 == null && dateFloor.isEmpty() && dateCeil.isEmpty())
        {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + priceFloor2, null);
        }

        else if (priceFloor2 == null && priceCeil2 != null && dateFloor.isEmpty() && dateCeil.isEmpty())
        {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price <= " + priceCeil2, null);
        }

        else if (priceFloor2 != null && priceCeil2 == null && !dateFloor.isEmpty() && dateCeil.isEmpty())
        {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + priceFloor2 + " AND Date >= '" + dateFloor + "'", null);
        }

        else if (priceFloor2 != null && priceCeil2 == null && dateFloor.isEmpty() && !dateCeil.isEmpty())
        {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + priceFloor2 + " AND Date <= '" + dateCeil + "'", null);
        }

        else if (priceFloor2 == null && priceCeil2 != null && !dateFloor.isEmpty() && dateCeil.isEmpty())
        {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price <= " + priceCeil2 + " AND Date >= '" + dateFloor + "'", null);
        }

        else if (priceFloor2 == null && priceCeil2 != null && dateFloor.isEmpty() &&!dateCeil.isEmpty())
        {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price <= " + priceCeil2 + " AND Date <= '" + dateCeil + "'", null);
        }

        else if (priceFloor2 != null && priceCeil2 != null && !dateFloor.isEmpty() && dateCeil.isEmpty())
        {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + priceFloor2 + " AND Price <= " + priceCeil2 + " AND Date >= '" + dateFloor + "'", null);
        }

        else if (priceFloor2 == null && priceCeil2 == null && !dateFloor.isEmpty() && dateCeil.isEmpty())
        {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Date >= '" + dateFloor + "'", null);
        }

        else if (priceFloor2 == null && priceCeil2 == null && dateFloor.isEmpty() && !dateCeil.isEmpty())
        {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Date <= '" + dateCeil + "'", null);
        }

        else if (priceFloor2 != null && priceCeil2 != null && dateFloor.isEmpty() && dateCeil.isEmpty())
        {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + priceFloor2 + " AND Price <= " + priceCeil2, null);
        }

        else if (priceFloor2 == null && priceCeil2 == null && !dateFloor.isEmpty() && !dateCeil.isEmpty())
        {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Date >= '" + dateFloor + "' AND Date <= '" + dateCeil + "'", null);
        }

        else if (priceFloor2 != null && priceCeil2 != null && dateFloor.isEmpty() && !dateCeil.isEmpty())
        {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + priceFloor2 + " AND Price <= " + priceCeil2 + " AND Date <= '" + dateCeil + "'", null);
        }

        else if (priceFloor2 == null && priceCeil2 != null && !dateFloor.isEmpty() && !dateCeil.isEmpty())
        {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price <= " + priceCeil2 + " AND Date >= '" + dateFloor + "' AND Date <= '" + dateCeil + "'", null);
        }

        else if (priceFloor2 != null && priceCeil2 == null && !dateFloor.isEmpty() && !dateCeil.isEmpty())
        {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + priceFloor2 + " AND Date >= '" + dateFloor + "' AND Date <= '" + dateCeil +"'", null);
        }

        else if (priceFloor2 != null && priceCeil2 != null && dateFloor != null && dateCeil != null)
        {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + priceFloor2 + " AND Price <= " + priceCeil2 + " AND Date >= '" + dateFloor + "' AND Date <= '" + dateCeil + "'", null);
        }

        return result;
    }
}