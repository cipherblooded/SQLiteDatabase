package com.cipherblooded.mysqldatabase.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context) {

        super(context, Params.DATABASE_NAME, null, Params.DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE "+
                        Params.TABLE_NAME +"("+
                        Params.COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Params.COL_NAME+ " TEXT, " +
                        Params.COL_SURNAME+" TEXT, " +
                        Params.COL_MARKS+" INTEGER" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+Params.TABLE_NAME);
        onCreate(db);

    }


    public Long insertData(String name,String surname,String marks){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(Params.COL_NAME,name);
        contentValues.put(Params.COL_SURNAME,surname);
        contentValues.put(Params.COL_MARKS,marks);

        return db.insert(Params.TABLE_NAME, null, contentValues);
    }

    public Cursor getData(){

        SQLiteDatabase db=this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+Params.TABLE_NAME,null);
    }

    public Integer updateData(String id,String name,String surname,String marks){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(Params.COL_ID,id);
        contentValues.put(Params.COL_NAME,name);
        contentValues.put(Params.COL_SURNAME,surname);
        contentValues.put(Params.COL_MARKS,marks);

        return db.update(Params.TABLE_NAME,contentValues ,"ID = ?", new String[]{ id });

    }

    public Integer deleteData(String id){

        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(Params.TABLE_NAME,"ID = ?",new String[]{ id });

    }
}
