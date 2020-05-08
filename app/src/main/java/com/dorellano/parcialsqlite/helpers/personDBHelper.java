package com.dorellano.parcialsqlite.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class personDBHelper extends SQLiteOpenHelper {

    public personDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(personContract.CREAR_TABLA_PERSONA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versionAntigua, int versionNueva) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS personas");
    onCreate(sqLiteDatabase);
    }
}
