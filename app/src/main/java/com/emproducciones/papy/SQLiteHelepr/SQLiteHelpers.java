package com.emproducciones.papy.SQLiteHelepr;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.emproducciones.papy.SQLiteConstant.*;

public class SQLiteHelpers extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;

    public SQLiteHelpers (Context context){
        super(context, LotoSQL.NOMBRE_DB,null,DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GestionNocheFecha.CREAR_TABLA_GESTION_NOCHE);
        db.execSQL(NocheVigente.CREAR_TABLA_NOCHE_VIGENTE);
        db.execSQL(lotosTodos.CREAR_TABLA);
        db.execSQL(Estadisticas.CREAR_TABLA);
        db.execSQL(ganadores.CREAR_TABLA);
        db.execSQL(NumerosUltimaJugada.CREAR_TABLA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(GestionNocheFecha.ELIMINAR_TABLA_GESTION_NOCHE);
        db.execSQL(NocheVigente.ELIMINAR_TABLA_NOCHE_VIGENTE);
        onCreate(db);
    }
}
