package com.emproducciones.papy.SQLiteConstant;

public class ganadores {

    public final static String NOMBRE_TABLA = "ganadores";

    public final static String COLUMN_ID = "id";
    public final static String COLUMN_ID_NOCHE = "idNoche";
    public final static String COLUMN_ID_GANADOR = "idJugador";

    public final static String CREAR_TABLA =
            "CREATE TABLE " + NOMBRE_TABLA + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_ID_NOCHE + " INTEGER, " +
                    COLUMN_ID_GANADOR + " INTEGER NOT NULL );";


    public final static String [] COLUMNAS_RECUPERAR_ID_GANADORES = new String[]{COLUMN_ID_GANADOR};

    public final static String CLAUSULA_WHERE_ID_GANADORES= COLUMN_ID_NOCHE + "=?";
}
