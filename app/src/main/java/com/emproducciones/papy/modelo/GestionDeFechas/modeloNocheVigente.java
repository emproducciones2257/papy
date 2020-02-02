package com.emproducciones.papy.modelo.GestionDeFechas;

import android.content.ContentValues;
import com.emproducciones.papy.SQLiteConstant.GestionNocheFecha;


public class modeloNocheVigente {

    private Integer idNocheFecha = null;
    private String fecha;
    private byte numeronoche;
    private byte estado;


    public int getIdNocheFecha() {
        return idNocheFecha;
    }

    public void setIdNocheFecha(Integer idNocheFecha) {
        this.idNocheFecha = idNocheFecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public byte getNumeronoche() {
        return numeronoche;
    }

    public void setNumeronoche(byte numeronoche) {
        this.numeronoche = numeronoche;
    }

    public byte isEstado() {
        return estado;
    }

    public void setEstado(byte estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "modeloNocheVigente{" +
                "idNocheFecha=" + idNocheFecha +
                ", fecha='" + fecha + '\'' +
                ", numeronoche=" + numeronoche +
                ", estado=" + estado +
                '}';
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues(4);
        contentValues.put(GestionNocheFecha.COLUMN_ID_GESTION_NOCHE_FECHA,idNocheFecha);
        contentValues.put(GestionNocheFecha.COLUMN_FECHA_GESTION_NOCHE_FECHA,fecha);
        contentValues.put(GestionNocheFecha.COLUMN_NRO_NOCHE_GESTION_NOCHE_FECHA,numeronoche);
        contentValues.put(GestionNocheFecha.COLUMN_ESTADO_NOCHE_GESTION_NOCHE_FECHA,estado);
        return contentValues;
    }
}
