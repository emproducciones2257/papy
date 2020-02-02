package com.emproducciones.papy.modelo;

import android.content.ContentValues;

public class NumerosUltimaJugada {

    private int id;
    private int id_noche;
    private Byte nro;

    public NumerosUltimaJugada(byte numeroNoche, byte nroBuscar) {
        this.id_noche=numeroNoche;
        this.nro=nroBuscar;
    }

    public NumerosUltimaJugada(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_noche() {
        return id_noche;
    }

    public void setId_noche(int id_noche) {
        this.id_noche = id_noche;
    }

    public Byte getNro() {
        return nro;
    }

    public void setNro(Byte nro) {
        this.nro = nro;
    }

    public ContentValues toValues(){
        ContentValues c = new ContentValues(2);
        c.put(com.emproducciones.papy.SQLiteConstant.NumerosUltimaJugada.COLUMN_ID_NOCHE,id_noche);
        c.put(com.emproducciones.papy.SQLiteConstant.NumerosUltimaJugada.COLUMN_NUM,nro);
        return c;
    }
}
