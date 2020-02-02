package com.emproducciones.papy.SQLiteConstant;

public class NocheVigente {

    //columnas para la tabla de lotos noche vigente
    public final static String COLUMN_ID_NOCHE_VIGENTE = "idNocheVigente";
    public final static String COLUMN_ID_NOCHE = "idNocheFecha";
    public final static String COLUMN_NOMBRE_NOCHE_VIGENTE = "nombre";
    public final static String COLUMN_NUM_A_NOCHE_VIGENTE = "numA";
    public final static String COLUMN_NUM_B_NOCHE_VIGENTE = "numB";
    public final static String COLUMN_NUM_C_NOCHE_VIGENTE = "numC";
    public final static String COLUMN_ACIERTOS_NOCHE_VIGENTE = "aciertos";


    public final static String NOMBRE_TABLA_NOCHE_VIGENTE = "nocheVigente";


    public final static String CREAR_TABLA_NOCHE_VIGENTE =
            "CREATE TABLE " + NOMBRE_TABLA_NOCHE_VIGENTE + "(" +
                    COLUMN_ID_NOCHE_VIGENTE + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_ID_NOCHE + " INTEGER, " +
                    COLUMN_NOMBRE_NOCHE_VIGENTE + " TEXT NOT NULL, " +
                    COLUMN_NUM_A_NOCHE_VIGENTE + " INTEGER NOT NULL," +
                    COLUMN_NUM_B_NOCHE_VIGENTE + " INTEGER NOT NULL," +
                    COLUMN_NUM_C_NOCHE_VIGENTE + " INTEGER NOT NULL," +
                    COLUMN_ACIERTOS_NOCHE_VIGENTE + " INTEGER NOT NULL," +
                    " FOREIGN KEY ("+ COLUMN_ID_NOCHE +") REFERENCES "+GestionNocheFecha.NOMBRE_TABLA_GESTION_NOCHE_FECHA
                        +"("+GestionNocheFecha.COLUMN_ID_GESTION_NOCHE_FECHA+"));";

    //QUERIS
    public final static String [] COLUMNAS_RECUPERAR_CONSULTA = new String[]{COLUMN_ID_NOCHE_VIGENTE,COLUMN_ID_NOCHE,
            COLUMN_NOMBRE_NOCHE_VIGENTE,COLUMN_NUM_A_NOCHE_VIGENTE,COLUMN_NUM_B_NOCHE_VIGENTE,COLUMN_NUM_C_NOCHE_VIGENTE,
    COLUMN_ACIERTOS_NOCHE_VIGENTE};

    public final static String [] COLUMNAS_RECUPERAR_CONSULTA_CONTROL = new String[]{COLUMN_ID_NOCHE_VIGENTE,
            COLUMN_NOMBRE_NOCHE_VIGENTE,COLUMN_NUM_A_NOCHE_VIGENTE,COLUMN_NUM_B_NOCHE_VIGENTE,COLUMN_NUM_C_NOCHE_VIGENTE};

    public final static String CLAUSULA_WHERE_ELIMINAR_LOTO= COLUMN_ID_NOCHE_VIGENTE + "=?";

    //DROP TABLE
    public final static String ELIMINAR_TABLA_NOCHE_VIGENTE= "DROP TABLE " + NOMBRE_TABLA_NOCHE_VIGENTE;
}
