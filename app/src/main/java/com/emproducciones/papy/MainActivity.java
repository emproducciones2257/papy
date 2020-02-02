package com.emproducciones.papy;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.emproducciones.papy.modelo.GestionDeFechas.metodosNocheVigente;
import com.emproducciones.papy.modelo.GestionDeFechas.modeloNocheVigente;
import org.androidannotations.annotations.*;
import java.util.Date;

@EActivity(R.layout.activity_main)

public class MainActivity extends AppCompatActivity {

    public static modeloNocheVigente fechaNoche;
    private DataSQL dataSQL;
    public static java.sql.Date fechaFormatoFecha;


    @AfterViews
        void after(){fechaFormatoFecha=fechaActual(); creacionBase(); }

    @Click(R.id.btnjugar)
        void evtJugar(){
        Intent intencion = new Intent(getApplicationContext(),jugar_.class);
        startActivity(intencion);
    }

    @Click(R.id.btnconsulta)
     void evtConsulta(){
        Intent intencion = new Intent(getApplicationContext(),consultas_.class);
        startActivity(intencion);
    }

    public static java.sql.Date fechaActual(){
        Date h = new Date();
        java.sql.Date fechaActual = new java.sql.Date(h.getTime());
        return fechaActual;
    }

    // este metodo va ser el encargado de agregar el primer registro de la fecha en caso de que la bd no exista
    void creacionBase(){
        dataSQL = new DataSQL(this);
        metodosNocheVigente mod=new metodosNocheVigente(getApplicationContext());
        fechaNoche=mod.verificarFecha(fechaFormatoFecha);
        if(fechaNoche==null){
            modeloNocheVigente noche = new modeloNocheVigente();
            noche.setIdNocheFecha(null);
            noche.setFecha(fechaFormatoFecha.toString());
            noche.setNumeronoche((byte)1);
            noche.setEstado((byte)1);
            dataSQL.insertFecha(noche);
        }
    }
}
