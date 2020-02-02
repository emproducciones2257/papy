package com.emproducciones.papy.SQLiteConstant;

public class Estadisticas {

    public final static String NOMBRE_TABLA = "estadisticas";

    public final static String COLUMN_ID = "id";
    public final static String COLUMN_ID_NOCHE = "idNoche";
    public final static String COLUMN_NUM_A = "numA";
    public final static String COLUMN_NUM_B = "numB";
    public final static String COLUMN_NUM_C = "numC";
    public final static String ESTADO = "estado";
    public final static String ULTIMA_JUGADA="ultimaJugada";

    public final static String CREAR_TABLA =
            "CREATE TABLE " + NOMBRE_TABLA + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_ID_NOCHE + " INTEGER, " +
                    COLUMN_NUM_A + " INTEGER NOT NULL," +
                    COLUMN_NUM_B + " INTEGER NOT NULL," +
                    COLUMN_NUM_C + " INTEGER NOT NULL," +
                    ESTADO + " INTEGER NOT NULL," +
                    ULTIMA_JUGADA + " INTEGER NOT NULL," +
                    " FOREIGN KEY ("+ COLUMN_ID_NOCHE +") REFERENCES "+GestionNocheFecha.NOMBRE_TABLA_GESTION_NOCHE_FECHA
                    +"("+GestionNocheFecha.COLUMN_ID_GESTION_NOCHE_FECHA+"));";

    public final static String CLAUSULA_WHERE_ID_NOCHE="id=?";

    public final static String[] COLUMNAS_CONSULTA = new String[]{COLUMN_ID,COLUMN_ID_NOCHE,COLUMN_NUM_A,COLUMN_NUM_B,
    COLUMN_NUM_C,ESTADO,ULTIMA_JUGADA};

    public final static String[] COLUMNAS_CONSULTA_AGREGAR_ULTIMA_NOCHE = new String[]{COLUMN_ID,COLUMN_ID_NOCHE,COLUMN_NUM_A,COLUMN_NUM_B,
            COLUMN_NUM_C,ESTADO,ULTIMA_JUGADA};

}
