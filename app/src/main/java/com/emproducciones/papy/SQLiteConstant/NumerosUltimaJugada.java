package com.emproducciones.papy.SQLiteConstant;

public class NumerosUltimaJugada {

    public final static String NOMBRE_TABLA = "nrosUltimaJugada";

    public final static String COLUMN_ID = "id";
    public final static String COLUMN_ID_NOCHE = "idNoche";
    public final static String COLUMN_NUM = "num";

    public final static String CREAR_TABLA =
            "CREATE TABLE " + NOMBRE_TABLA + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_ID_NOCHE + " INTEGER, " +
                    COLUMN_NUM + " INTEGER NOT NULL);";

    public final static String CLAUSULA_WHERE_ID_NOCHE="idNoche=?";

    public final static String[] COLUMNAS = new String[]{COLUMN_ID_NOCHE,COLUMN_NUM};

}
