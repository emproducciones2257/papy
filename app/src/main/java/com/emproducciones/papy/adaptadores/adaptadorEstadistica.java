package com.emproducciones.papy.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.emproducciones.papy.R;
import com.emproducciones.papy.modelo.NumerosUltimaJugada;
import com.emproducciones.papy.modelo.jugadaVigente.metodosJugadaVigente;
import com.emproducciones.papy.modelo.modeloEstadistica;
import java.util.ArrayList;

public class adaptadorEstadistica extends RecyclerView.Adapter {

    ArrayList<modeloEstadistica> estadisticaNoche;
    Context context;

    public adaptadorEstadistica(ArrayList<modeloEstadistica> estadisticaNoche,Context context){
        this.estadisticaNoche=estadisticaNoche;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_mostrar_estadisticas,viewGroup,false);
        modelado mod = new modelado(view);
        return mod;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ((modelado)viewHolder).onBind(i);
    }

    @Override
    public int getItemCount() {
        return estadisticaNoche.size();
    }

    class modelado extends RecyclerView.ViewHolder{
        TextView txtNumeroNocheJugada,txtNumerosUltimaJugada,txtNombreGanadores,txtEstadoResultadoNoche,txtNumerosSorteados;

        public modelado(@NonNull View itemView) {
            super(itemView);
            txtNumeroNocheJugada = itemView.findViewById(R.id.txtNumeroNocheJugada);
            txtNumerosSorteados = itemView.findViewById(R.id.txtNumerosSorteados);
            txtEstadoResultadoNoche = itemView.findViewById(R.id.txtEstadoResultadoNoche);
            txtNombreGanadores = itemView.findViewById(R.id.txtNombreGanadores);
            txtNumerosUltimaJugada = itemView.findViewById(R.id.txtNumerosUltimaJugada);
        }

        public void onBind (int Indice){
            modeloEstadistica modTemp = estadisticaNoche.get(Indice);
            metodosJugadaVigente metodosNocheVigenteTemp = modTemp.getMetodosNocheVigente();
            txtNumeroNocheJugada.setText(modTemp.getNumeroNoche()+"");
            txtNumerosSorteados.setText(metodosNocheVigenteTemp.getNumA() + " - " + metodosNocheVigenteTemp.getNumb() + " - " + metodosNocheVigenteTemp.getNumc());
            if (modTemp.getResultado()==1){// si es 1, tengo ganadores
                String temp="";
                txtEstadoResultadoNoche.setText("GANADOR");
                for (String nombre : modTemp.getNombres()) {
                    temp = temp + " "+nombre+", ";
                }
                txtNombreGanadores.setText(temp);//recupero los nombres de los ganadores y muestro
            }else {
                txtEstadoResultadoNoche.setText("VACANTE");
                txtNombreGanadores.setText("");
            }
            if(modTemp.getUltimaJugada()==1){
                String t="";
                for (NumerosUltimaJugada e : modTemp.getNrosUltimaJugada()){
                    t = txtNumerosUltimaJugada.getText().toString();
                    txtNumerosUltimaJugada.setText(t + " - " + e.getNro());
                }

            }else txtNumerosUltimaJugada.setText("");
        }
    }
}
