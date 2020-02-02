package com.emproducciones.papy.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.emproducciones.papy.R;
import com.emproducciones.papy.modelo.jugadaVigente.metodosJugadaVigente;

import java.util.ArrayList;

public class recicladorGanadores extends RecyclerView.Adapter {

    ArrayList<metodosJugadaVigente> ganadores;
    Context contexto;

    public recicladorGanadores(ArrayList<metodosJugadaVigente> ganadores, Context contexto){
        this.ganadores=ganadores;
        this.contexto=contexto;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(contexto).inflate(R.layout.activity_ganadores,viewGroup,false);

        modelado model = new modelado(view);

        return model;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((modelado)viewHolder).onBind(i);
    }

    @Override
    public int getItemCount() {
        if (ganadores ==null) return 0;
        else
        return ganadores.size();
    }

    public class modelado extends RecyclerView.ViewHolder{
        TextView nombre,numerosGanadores;

        public modelado(@NonNull View itemView) {
            super(itemView);
            nombre=itemView.findViewById(R.id.txtNombreJugadorGanadores);
            numerosGanadores=itemView.findViewById(R.id.txtNumerosJugadorGanador);
        }

        public void onBind (int Indice){
            metodosJugadaVigente metodosNocheVigenteTemp = ganadores.get(Indice);
            nombre.setText("Nombre: "+ metodosNocheVigenteTemp.getNombreJugador());
            numerosGanadores.setText("Numeros: " +String.valueOf(metodosNocheVigenteTemp.getNumA())+" - " + String.valueOf(metodosNocheVigenteTemp.getNumb()) + " - "
             + String.valueOf(metodosNocheVigenteTemp.getNumc()));
        }
    }
}
