package com.alejo_zr.exceldb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alejo_zr.exceldb.utilidades.Utilidades;

/**
 * Created by Alejo on 26/09/2017.
 */

public class BaseDatos extends SQLiteOpenHelper {

    private static final int VERSION_BASEDATOS = 1;

    // Nombre de nuestro archivo de base de datos
    private static final String NOMBRE_BASEDATOS = "InspeccionPavimentos.db";




    // CONSTRUCTOR de la clase
    public BaseDatos(Context context) {
        super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CARRETERA.CREAR_TABLA_CARRETERA);
        db.execSQL(Utilidades.SEGMENTOFLEX.CREAR_TABLA_SEGMENTO);
        db.execSQL(Utilidades.PATOLOGIAFLEX.CREAR_TABLA_PATOLOGIA);
        db.execSQL(Utilidades.PATOLOGIARIGI.CREAR_TABLA_PATOLOGIA);
        db.execSQL (Utilidades.SEGMENTORIGI.CREAR_TABLA_SEGMENTO);
        db.execSQL(Utilidades.FOTOFLEX.CREAR_TABLA_FOTO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.CARRETERA.TABLA_CARRETERA);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.SEGMENTOFLEX.TABLA_SEGMENTO);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.PATOLOGIAFLEX.TABLA_PATOLOGIA);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.PATOLOGIARIGI.TABLA_PATOLOGIA);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.SEGMENTORIGI.TABLA_SEGMENTO);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.FOTOFLEX.TABLA_FOTO);
        onCreate(db);
    }

    public Cursor getroad() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + Utilidades.CARRETERA.TABLA_CARRETERA + " ",
                null);
        return res;
    }

    public Cursor getSegmentoFlex() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + Utilidades.SEGMENTOFLEX.TABLA_SEGMENTO + " ",
                null);
        return res;
    }
    public Cursor getSegmentoRigi() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + Utilidades.SEGMENTORIGI.TABLA_SEGMENTO + " ",
                null);
        return res;
    }

    public Cursor getPatoFlex() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + Utilidades.PATOLOGIAFLEX.TABLA_PATOLOGIA + " ",
                null);
        return res;
    }

    public Cursor getfotoFLex() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + Utilidades.FOTOFLEX.TABLA_FOTO + " ",
                null);
        return res;
    }



}