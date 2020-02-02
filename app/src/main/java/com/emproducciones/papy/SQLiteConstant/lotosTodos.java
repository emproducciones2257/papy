package com.emproducciones.papy.SQLiteConstant;

public class lotosTodos {
    public final static String COLUMN_ID = "id";
    public final static String COLUMN_ID_NOCHE = "idNoche";
    public final static String COLUMN_NOMBRE = "nombre";
    public final static String COLUMN_NUM_A = "numA";
    public final static String COLUMN_NUM_B = "numB";
    public final static String COLUMN_NUM_C = "numC";
    public final static String COLUMN_ACIERTOS = "aciertos";

    public final static String NOMBRE_TABLA = "lotosTodos";

    public final static String CREAR_TABLA =
            "CREATE TABLE " + NOMBRE_TABLA + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_ID_NOCHE + " INTEGER, " +
                    COLUMN_NOMBRE + " TEXT NOT NULL, " +
                    COLUMN_NUM_A + " INTEGER NOT NULL," +
                    COLUMN_NUM_B + " INTEGER NOT NULL," +
                    COLUMN_NUM_C + " INTEGER NOT NULL," +
                    COLUMN_ACIERTOS + " INTEGER NOT NULL," +
                    " FOREIGN KEY ("+ COLUMN_ID_NOCHE +") REFERENCES "+GestionNocheFecha.NOMBRE_TABLA_GESTION_NOCHE_FECHA
                    +"("+GestionNocheFecha.COLUMN_ID_GESTION_NOCHE_FECHA+"));";


    //QUERI
    public final static String [] COLUMNAS_RECUPERAR_ID = new String[]{COLUMN_ID};
    public final static String [] COLUMNAS_RECUPERAR_TODAS_BUSQUEDA = new String[]{COLUMN_ID_NOCHE,COLUMN_NOMBRE,
            COLUMN_NUM_A,COLUMN_NUM_B,COLUMN_NUM_C};
    public final static String CLAUSULA_PARA_LIKE = COLUMN_NOMBRE +" LIKE ?";
    public final static String [] COLUMNAS_RECUPERAR_NOMBRES = new String[]{COLUMN_NOMBRE};
    public final static String CLAUSULA_WHERE_NOMBRES_GANADORES= COLUMN_ID + "=?";
    public final static String CLAUSULA_WHERE= COLUMN_ID_NOCHE + "=? AND " + COLUMN_ACIERTOS+"=?";
    public final static String CLAUSULA_MAX_ID_NOCHE="MAX(idNoche)";
    public final static String CLAUSULA_WHERE_ID_NOCHE="idNoche=?";

    public final static String CLAUSULA_WHERE_ACTUALIZAR_GANADOR = COLUMN_ID_NOCHE + "=? AND " + COLUMN_NOMBRE+ "=? AND "
                                    + COLUMN_NUM_A + "=? AND " + COLUMN_NUM_B+  "=? AND " + COLUMN_NUM_C+"=?";

}
