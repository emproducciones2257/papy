package com.emproducciones.papy;

import android.net.Uri;
import android.support.v4.app.*;
import android.support.v7.app.AppCompatActivity;
import org.androidannotations.annotations.*;

@EActivity(R.layout.activity_consultas)
public class consultas extends AppCompatActivity implements fragmentEstadisticas.OnFragmentInteractionListener,
        fragmentNochesJugadas.OnFragmentInteractionListener{

    public static byte precioLoto = 25;
    public static byte porcentajeClub = 30;
    public static byte porcentajePozo = 70;
    public static int pozoInicial = 1000;

    FragmentManager manejadorFragmentos = getSupportFragmentManager();

    @Click(R.id.btnNochesJugadas)
    void nochesJugadas(){
        FragmentTransaction fragmentTransaction = manejadorFragmentos.beginTransaction();
        fragmentTransaction.replace(R.id.contenedorFragment,new fragmentNochesJugadas());
        fragmentTransaction.commit();
    }

    @Click(R.id.btnEstadisticas)
    void estadistica(){
        FragmentTransaction fragmentTransaction = manejadorFragmentos.beginTransaction();
        fragmentTransaction.replace(R.id.contenedorFragment,new fragmentEstadisticas());
        fragmentTransaction.commit();
    }

    @Click(R.id.btnBuscarLotos)
    void buscarLotos(){
        FragmentTransaction fragmentTransaction = manejadorFragmentos.beginTransaction();
        fragmentTransaction.replace(R.id.contenedorFragment,new fragment_buscarLotos_());
        fragmentTransaction.commit();
    }

    @Click(R.id.btnLotosNocheActual)
    void lotosVigente(){
        FragmentTransaction fragmentTransaction = manejadorFragmentos.beginTransaction();
        fragmentTransaction.replace(R.id.contenedorFragment,new fragmentLotosNocheVigente_());
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
