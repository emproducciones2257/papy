package com.emproducciones.papy;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.*;
import android.widget.EditText;
import com.emproducciones.papy.adaptadores.adaptadorResultadosBusquedas;
import com.emproducciones.papy.impresion.ESCPOS;
import com.emproducciones.papy.modelo.GestionDeFechas.modeloNocheVigente;
import com.emproducciones.papy.modelo.jugadaVigente.metodosJugadaVigente;
import com.emproducciones.papy.modelo.modeloBusqueda;
import org.androidannotations.annotations.*;
import java.text.SimpleDateFormat;
import java.util.*;

@EFragment(R.layout.fragment_fragment_buscar_lotos)
public class fragment_buscarLotos extends Fragment {

    @ViewById
    EditText txtNombreABuscar;
    @ViewById
    RecyclerView recicladorBusquedas;
    DataSQL data;
    adaptadorResultadosBusquedas adapter;
    modeloNocheVigente fechaNoche;
    metodosJugadaVigente jugadaVigente;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    final String appPackageName = "ru.a402d.rawbtprinter";

    @AfterViews
    void init (){
        metodoEscucha();
        buscar();
        swipe();
    }

    ItemTouchHelper.SimpleCallback llamada = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            modeloBusqueda temp;
            int posicion = viewHolder.getAdapterPosition();
            adaptadorResultadosBusquedas modeloBusqueda= (adaptadorResultadosBusquedas) recicladorBusquedas.getAdapter();
            temp = modeloBusqueda.busqueda.get(posicion);
            System.out.println(temp.toString());
            confirmacionJugadaActual(temp).show();
        }
    };

    public AlertDialog confirmacionJugadaActual(final modeloBusqueda temp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        data = new DataSQL(getActivity());
        fechaNoche = new modeloNocheVigente();
        builder.setTitle("Jugar Loto")
                .setMessage("Se va a jugar el loto de " + temp.getMetodosNocheVigente().getNombreJugador()+
                        " con los numeros N° "+temp.getMetodosNocheVigente().getNumA()+" - "+
                        temp.getMetodosNocheVigente().getNumb()+" - "+
                        temp.getMetodosNocheVigente().getNumc()+" en la noche vigente")
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                .setPositiveButton("ACEPTAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                jugadaVigente = new metodosJugadaVigente();
                                jugadaVigente.setNombreJugador(temp.getMetodosNocheVigente().getNombreJugador());
                                jugadaVigente.setNumA(temp.getMetodosNocheVigente().getNumA());
                                jugadaVigente.setNumb(temp.getMetodosNocheVigente().getNumb());
                                jugadaVigente.setNumc(temp.getMetodosNocheVigente().getNumc());
                                jugadaVigente.setIdNocheFecha(MainActivity_.fechaNoche.getNumeronoche());
                                imprimir(jugadaVigente);
                                data.insertarLotoNoche(jugadaVigente);
                                buscar();
                            }
                        });

        return builder.create();
    }

    public void swipe (){
        ItemTouchHelper toque = new ItemTouchHelper(llamada);
        toque.attachToRecyclerView(recicladorBusquedas);
    }

    public void metodoEscucha(){
        txtNombreABuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText e = txtNombreABuscar;
                String texto = e.getText().toString();
                mostrar(data.buscarPorNombre(texto));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
    }

    public  void buscar(){
        data = new DataSQL(getContext());
        ArrayList<modeloBusqueda> modeloBusqueda;
        modeloBusqueda=data.obtenerLotosTodos();
        mostrar(modeloBusqueda);
    }

    void mostrar(ArrayList<modeloBusqueda> modeloBusqueda){
        adapter = new adaptadorResultadosBusquedas(modeloBusqueda,getActivity());
        recicladorBusquedas.setAdapter(adapter);
        recicladorBusquedas.setLayoutManager(new LinearLayoutManager(getActivity()));
        recicladorBusquedas.setHasFixedSize(true);
    }

    public void imprimir(metodosJugadaVigente sorteo) {
        String Titulo = "CLUB ATLETICO CASTILLA";
        String Subt = "PAPYLOTO";
        String publi = "Sistemas EMProducciones";

        String paraImprimir =  ESCPOS.EstiloPasable + ESCPOS.FuenteDobleDeALtura + ESCPOS.AligCentro + Titulo + ESCPOS.SaltoDeLinea// titulo
                + ESCPOS.EstiloPasable + ESCPOS.FuenteDobleDeALtura + ESCPOS.AligCentro + Subt + ESCPOS.SaltoDeLinea // sub
                + ESCPOS.SaltoDeLinea + ESCPOS.EstiloPasable + ESCPOS.AligCentro + sorteo.getNombreJugador() + ESCPOS.SaltoDeLinea // nombre
                + ESCPOS.SaltoDeLinea + ESCPOS.EstiloPasable + ESCPOS.AligCentro + sorteo.getNumA() + " - " + sorteo.getNumb() + " - " + sorteo.getNumc() + ESCPOS.SaltoDeLinea // numeros
                + ESCPOS.SaltoDeLinea + ESCPOS.FuenteB + ESCPOS.AligIzquierda + formatter.format(MainActivity_.fechaFormatoFecha) + ESCPOS.SaltoDeLinea // fecha
                + ESCPOS.FuenteB + ESCPOS.AligDerecha + "Noche: " + MainActivity_.fechaNoche.getNumeronoche() + ESCPOS.SaltoDeLinea // Noche
                + ESCPOS.SaltoDeLinea + ESCPOS.lineas + ESCPOS.SaltoDeLinea
                + ESCPOS.SaltoDeLinea + ESCPOS.FuenteB + ESCPOS.AligCentro + publi // publi
                + ESCPOS.CorteCompleto;

        String textToPrint = String.format(Locale.ROOT, paraImprimir, 4);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, textToPrint);
        sendToPrint(intent);
    }

    public void sendToPrint(Intent intent) {
        intent.setPackage(appPackageName);
        startActivity(intent);
    }
}
