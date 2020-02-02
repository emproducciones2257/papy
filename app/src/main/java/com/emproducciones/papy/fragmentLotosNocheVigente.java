package com.emproducciones.papy;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.*;
import com.emproducciones.papy.adaptadores.adapterLotosVigentes;
import com.emproducciones.papy.modelo.GestionDeFechas.*;
import com.emproducciones.papy.modelo.jugadaVigente.metodosJugadaVigente;
import com.emproducciones.papy.modelo.modeloNochesJugadas;
import org.androidannotations.annotations.*;
import java.util.ArrayList;

@EFragment(R.layout.fragment)
public class fragmentLotosNocheVigente extends Fragment {

    @ViewById()
    RecyclerView recicladorLotosVigentes;
    @ViewById
    TextView txtTotalLotosParciales,txtPozoAcumulado,txtPorcentajeClub;

    private ArrayList<metodosJugadaVigente> lotosVigentes;
    private DataSQL d;
    private jugar jug;
    private modeloNocheVigente fechaNoche;
    private metodosNocheVigente mod;
    private adapterLotosVigentes adapter;
    private ArrayList<modeloNochesJugadas> totalLotos; //variable para almacenar la cantidad de lotos vendidos en todo el torneo
    private float totalPozo = consultas.pozoInicial;
    private float totalClub = 0;

    @AfterViews
    void init(){
        obtenerFecha();//obtengo la noche actual
        recuperarCantidadLotos();
        mostrar();
        swipe();
    }
    void obtenerFecha(){
        d = new DataSQL(getContext());
        jug= new jugar();
        mod = new metodosNocheVigente(getContext());
        fechaNoche = mod.verificarFecha(MainActivity.fechaActual());//obtengo la fecha actual
    }

    void recuperarCantidadLotos(){
        lotosVigentes = d.obtenerLotosVigente();
        totalLotos = d.getNochesJugadas();
        generarPorcentajeNoches();
    }

    void generarPorcentajeNoches(){
        for(modeloNochesJugadas e : totalLotos){
            int cant = e.getLotosVendidos()*consultas.precioLoto;
            totalPozo =  totalPozo + ((float)(cant*consultas.porcentajePozo)/100);
            totalClub = totalClub + ((float)(cant*consultas.porcentajeClub)/100);
        }
        int cant = lotosVigentes.size()*consultas.precioLoto;
        totalPozo = totalPozo + ((float)(cant*consultas.porcentajePozo)/100);
        totalClub = totalClub + ((float)(cant*consultas.porcentajeClub)/100);
    }

    void mostrar(){
        txtTotalLotosParciales.setText("Lotos Vendidos: " + lotosVigentes.size());
        txtPozoAcumulado.setText("Pozo : $ " + totalPozo);
        txtPorcentajeClub.setText("Club: $ " + totalClub);
        adapter = new adapterLotosVigentes(lotosVigentes,getActivity(),fechaNoche);
        recicladorLotosVigentes.setAdapter(adapter);
        recicladorLotosVigentes.setLayoutManager(new LinearLayoutManager(getActivity()));
        recicladorLotosVigentes.setHasFixedSize(true);
    }

    ItemTouchHelper.SimpleCallback llamada = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            metodosJugadaVigente temp;
            int posicion = viewHolder.getAdapterPosition();
            adapterLotosVigentes modeloBusqueda= (adapterLotosVigentes) recicladorLotosVigentes.getAdapter();
            temp = modeloBusqueda.lotos.get(posicion);
            System.out.println(temp.toString());
            confirmacionEliminacion(temp).show();
        }
    };

    public AlertDialog confirmacionEliminacion(final metodosJugadaVigente temp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.Theme_AppCompat_DayNight_Dialog_Alert);

        builder.setTitle("Confirmar Eliminación")
                .setMessage("Se va a eliminar el loto de " + temp.getNombreJugador() +
                        " con los numeros N° "+temp.getNumA()+" - "+
                        temp.getNumb()+" - "+
                        temp.getNumc() + " de la noche actual")
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
                                d.eliminarLotoNocheVigente(temp.getIdLoto());
                                Toast.makeText(getContext(),"Loto eliminado correctamente",Toast.LENGTH_SHORT).show();
                                totalPozo = consultas.pozoInicial;
                                totalClub = 0;
                                recuperarCantidadLotos();
                                mostrar();
                            }
                        });

        return builder.create();
    }

    public void swipe (){
        ItemTouchHelper toque = new ItemTouchHelper(llamada);
        toque.attachToRecyclerView(recicladorLotosVigentes);
    }
}
