package com.emproducciones.papy;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.view.View;
import android.widget.*;
import com.emproducciones.papy.modelo.NumerosUltimaJugada;
import com.emproducciones.papy.modelo.jugada;
import com.emproducciones.papy.modelo.jugadaVigente.metodosJugadaVigente;
import com.emproducciones.papy.adaptadores.*;
import com.emproducciones.papy.modelo.modeloEstadistica;
import org.androidannotations.annotations.*;
import java.util.ArrayList;

@EActivity(R.layout.activity_sorte_actividad)
public class sorteActividad extends AppCompatActivity {

    @ViewById
    RecyclerView recicladorGanadores;
    @ViewById
    TextView txtGanadoresVacante,edtN1,edtN2,edtN3,txttNumerosSorteados;
    @ViewById
    Button btnReSorteo;

    DataSQL data;
    ArrayList<metodosJugadaVigente> arregloMetodosNocheVigente; // aca voy a tener almacenados los lotos para sortear
    ArrayList<metodosJugadaVigente> resultado;//aca voy almacenar los lotos con 3 coincidencias
    ArrayList<metodosJugadaVigente> resultado2Coincidencias;//aca voy almacenar los lotos con 2 coincidencias
    metodosJugadaVigente numSor;// en este objeto almaceno los numeros sorteados
    ArrayList<NumerosUltimaJugada> nrosUltimaJugada = new ArrayList<>();// almaceno los numeros del ultimo soerteo en caso de necesitarlo
    int idNoche,estadoGanador;
    byte numeroNoche;

    @AfterViews
    void init (){
        idNoche=getIntent().getIntExtra(jugar_.ID_NOCHE,0);//recibo el id de la noche
        numeroNoche=getIntent().getByteExtra(jugar_.NUMERO_NOCHE,(byte) 0);//recibo el numero de la noche
        data = new DataSQL(getApplicationContext());
        txtGanadoresVacante.setVisibility(View.INVISIBLE);
        arregloMetodosNocheVigente = new ArrayList<>();
        arregloMetodosNocheVigente = data.recuperarLotosParaSorteo(numeroNoche); //recupero de la base los lotos para sortear
    }

    @Click(R.id.btnRealizaSorteo)
    void eventoBoton(){
        cargarNumerosSorteados();
    }

    @Click(R.id.btnReSorteo)
    void eventoBotonReSorteo(){
        ArrayList<metodosJugadaVigente> resultadoInterno;

        // recupero el numero a buscar, si el numero es 100 no hago nada, elemento de corte
        byte nroBuscar = cargarNumeroUltimaJugada();

        if (nroBuscar != 100){

            NumerosUltimaJugada agregar = new NumerosUltimaJugada();
            agregar.setId_noche(numeroNoche);
            agregar.setNro(nroBuscar);

            nrosUltimaJugada.add(agregar);
            cargaNumerosSorteadosEnElPie(nroBuscar);
            verificarIngresoNumeroLugarIncorrecto();
            botoneraCargaNumerosFragment_.limpiarComponentes();

            // Si el objeto esta nulo, busco los lotos que tengan 2 coincidencias. ademas, como es lo que
            //primero se ejecuta, desactivo los 3 primeros numeros sorteados
            if(resultado2Coincidencias==null){
                resultado2Coincidencias = new jugada(arregloMetodosNocheVigente,null).buscar2Coincidencias();
                desactivarBotonesPrimerSorteo();
            }
            // busco si algun suertudo se saco el loto y lo guardo
            resultadoInterno = new jugada(resultado2Coincidencias,null).controlDeTerceraJugada(nroBuscar);

            //muestro en el Recycler el resultado
            mostrarResultadoRecycler(resultadoInterno);
            //evaluo el resultado para el comportamiento del mensaje de estado
            estadoMensaje(resultadoInterno);

            if(resultadoInterno.size()>=1) {
                data.actualizarRegistroJugadorGanadorUltimaFecha(resultadoInterno);// actualizo el registro del ganador en la tabla de todos los lotos
                botoneraCargaNumerosFragment_.limpiarComponentes();

                //si tengo ganadores en la noche, obtengo los id de la tabla para luego almacenarlo como ganadores
                if (estadoGanador>=1){
                    ArrayList<Integer> idGanadores = data.obtenerGanadoresLotosTodos(numeroNoche);
                    for (Integer e : idGanadores) {
                        data.cargarGanadores(numeroNoche,e);
                    }
                    //voy a recuperar el registro de la noche para cambiar el estado a 1 y para agregar
                    //que tiene numeros de la ultima jugada
                    modeloEstadistica registroNoche = data.recuperarRegistroNoche(numeroNoche);

                    if (registroNoche!=null){
                        data.actualizarRegistroUtimaJugada(registroNoche);
                        data.registrarNumerosUltimaJugada(nrosUltimaJugada);
                    }
                }
            }
        }
    }
    void cargaNumerosSorteadosEnElPie(byte n){
        if(txttNumerosSorteados.getText().toString().isEmpty()){
            txttNumerosSorteados.setText(numSor.getNumA() + " - " + numSor.getNumb() + " - " + numSor.getNumc());
        }
        String temp=txttNumerosSorteados.getText().toString();
        txttNumerosSorteados.setText(temp + " - " + n);
    }

    void desactivarBotonesPrimerSorteo(){
        if(numSor.getNumA()<10){
            botoneraCargaNumerosFragment_.desabilitarBoton("0"+numSor.getNumA());
        }else botoneraCargaNumerosFragment_.desabilitarBoton(String.valueOf(numSor.getNumA()));

        if(numSor.getNumb()<10){
            botoneraCargaNumerosFragment_.desabilitarBoton("0"+numSor.getNumb());
        }else botoneraCargaNumerosFragment_.desabilitarBoton(String.valueOf(numSor.getNumb()));

        if(numSor.getNumc()<10){
            botoneraCargaNumerosFragment_.desabilitarBoton("0"+numSor.getNumc());
        }else botoneraCargaNumerosFragment_.desabilitarBoton(String.valueOf(numSor.getNumc()));
    }

    void verificarIngresoNumeroLugarIncorrecto(){
        if(!(edtN2.getText().equals("N° 2"))) botoneraCargaNumerosFragment_.habilitarBoton(edtN2.getText().toString());
        if(!(edtN3.getText().equals("N° 3"))) botoneraCargaNumerosFragment_.habilitarBoton(edtN3.getText().toString());
    }

    void cargarNumerosSorteados(){
        if(botoneraCargaNumerosFragment_.verificacion()==true){
            numSor = new metodosJugadaVigente();
            numSor.setNumA(Integer.parseInt(edtN1.getText().toString()));
            numSor.setNumb(Integer.parseInt(edtN2.getText().toString()));
            numSor.setNumc(Integer.parseInt(edtN3.getText().toString()));
            metodoCojudoQueHaceLaJugada();
        }else {
            Toast.makeText(getApplicationContext(),"Ingresar los 3 numeros para el sorteo",Toast.LENGTH_SHORT).show();
        }
    }

    byte cargarNumeroUltimaJugada(){
        byte devolver = 100;
        if(botoneraCargaNumerosFragment_.verificacionUltimaJugada()==true){
            devolver=Byte.parseByte(edtN1.getText().toString());
            return devolver;
        }else Toast.makeText(getApplicationContext(),"Ingresar el numero a buscar",Toast.LENGTH_SHORT).show();

        return devolver;
    }

    void metodoCojudoQueHaceLaJugada(){

        // guardo las coincidencias de cada loto jugado
        arregloMetodosNocheVigente = new jugada(arregloMetodosNocheVigente,numSor).controlarPapis();
        // busco si algun suertudo se saco el loto y lo guardo
        resultado = new jugada(arregloMetodosNocheVigente,null).buscar3Coincidencias();
        //muestro en el Recycler el resultado
        mostrarResultadoRecycler(resultado);
        //evaluo el resultado para el comportamiento del mensaje de estado
        estadoMensaje(resultado);

        //habilito los componenetes
        botoneraCargaNumerosFragment.habilitarBotonesDesabilitados();
        botoneraCargaNumerosFragment.limpiarComponentes();

        //pongo la fecha en false para que se genere una nueva
        data.cerrarFecha(idNoche);

        //cargo los lotos en la base general y los borro de la base de lotos de la noche
        data.cargarLotosARegistro(arregloMetodosNocheVigente);

        //si tengo ganadores en la noche, obtengo los id de la tabla para luego almacenarlo como ganadores
        if (estadoGanador>=1){
            ArrayList<Integer> idGanadores = data.obtenerGanadoresLotosTodos(numeroNoche);
            for (Integer e : idGanadores) {
                data.cargarGanadores(numeroNoche,e);
            }
            regisrarNoche(1);
        }else {
            regisrarNoche(0);
        }
    }

    void mostrarResultadoRecycler(ArrayList<metodosJugadaVigente> resultado){
        recicladorGanadores recicler = new recicladorGanadores(resultado,getApplicationContext());
        recicladorGanadores.setAdapter(recicler);
        RecyclerView.LayoutManager manejador = new LinearLayoutManager(this);
        recicladorGanadores.setLayoutManager(manejador);
        recicladorGanadores.setHasFixedSize(true);
    }


    void estadoMensaje(ArrayList<metodosJugadaVigente> resultadoInterno){
        if (resultadoInterno.isEmpty()){
            txtGanadoresVacante.setText("Vacante");
            txtGanadoresVacante.setVisibility(View.VISIBLE);
            estadoGanador=0;
            btnReSorteo.setEnabled(true);
        }else if (resultadoInterno.size()==1){
            txtGanadoresVacante.setText("Ganador");
            txtGanadoresVacante.setVisibility(View.VISIBLE);
            btnReSorteo.setEnabled(false);
            estadoGanador=1;
        } else if(resultadoInterno.size()>1){
            txtGanadoresVacante.setText("Ganadores");
            txtGanadoresVacante.setVisibility(View.VISIBLE);
            btnReSorteo.setEnabled(false);
            estadoGanador=1;
        }
    }

    //metodo que carga la estadistica de la noche
    private void regisrarNoche(int estado){
        modeloEstadistica m = new modeloEstadistica();
        m.setNumeroNoche(numeroNoche);
        m.setMetodosNocheVigente(numSor);
        m.setResultado(estado);
        m.setUltimaJugada(0);
        data.registrarNocheEstadisticas(m);
    }
}



