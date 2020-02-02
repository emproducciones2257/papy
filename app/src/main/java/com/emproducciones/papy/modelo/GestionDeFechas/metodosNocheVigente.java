package com.emproducciones.papy.modelo.GestionDeFechas;

import android.content.Context;

import com.emproducciones.papy.DataSQL;

import java.sql.Date;

public class metodosNocheVigente {
    Context context;
    DataSQL data;

    public metodosNocheVigente(Context context){
        this.context= context;
    }

    public modeloNocheVigente verificarFecha(Date hoy) {
        data = new DataSQL(context);
        data.open();
        modeloNocheVigente noche = data.consultarFechaVigente();

        //consulto en este punto si la ultima fecha registrada en la base esta
        //disponible aun y ademas, si la fecha recbida es igual a la del ultimo
        //registro. Si se cumple, devuelvo el numero de la noche del ultimo registro

        if(noche==null){
            return null;
        }else if ((noche.getFecha().equals(hoy.toString()))&&(noche.isEstado()==1)){
            System.out.println("La ultima fecha esta disponible");
            return noche;

            //en este punto evaluo, si la fecha es distinta pero la ultima jugada esta disponible.
            //si se cumple, registro la fecha nueva

        }else if (!(noche.getFecha().equals(hoy.toString()))&&(noche.isEstado()==1)) {
                noche.setFecha(hoy.toString());
                noche.setIdNocheFecha(null);
                data.insertFecha(noche);
                noche=data.consultarFechaVigente();
                System.out.println("Se cambio la fecha pero sigue vigente la noche");
            return noche;
            //aca contemplo el caso que la noche ya no esta disponible, de ser asi,
            //habilito una nueva noche
        }else if (noche.isEstado()==0) {
            noche.setIdNocheFecha(null);
            noche.setFecha(hoy.toString());
            noche.setNumeronoche((byte)(noche.getNumeronoche()+1));
            noche.setEstado((byte)1);
            data.insertFecha(noche);
            System.out.println("Carga nueva");
            noche=data.consultarFechaVigente();
            return noche;
        }
        return noche;
    }

}
