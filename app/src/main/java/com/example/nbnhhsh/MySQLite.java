package com.example.nbnhhsh;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MySQLite extends SQLiteOpenHelper {
    final static String DATABAENAME="datatext.db";
    final static String TABLENAME="datatable";
    final static String ID="id";
    final static String VALUE_ABBREVIATION="abbreviationtextTEXT";
    final static String VALUE_TXT="txttextTEXT";



    public MySQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
//            String sql="CREATE TABLE datatable (" +
//                    MySQLite.ID+ " integer PRIMARY KEY AUTOINCREMENT," +
//                    MySQLite.VALUE_ABBREVIATION+"TEXT,"+
//                    MySQLite.VALUE_TXT+"TEXT NOT NULL UNIQUE)";
//            db.execSQL(sql);
                db.execSQL("CREATE TABLE datatable ( id integer PRIMARY KEY AUTOINCREMENT, abbreviationtextTEXT TEXT, txttextTEXT TEXT NOT NULL UNIQUE)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
