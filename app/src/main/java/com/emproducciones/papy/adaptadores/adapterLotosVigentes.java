package com.emproducciones.papy.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.emproducciones.papy.R;
import com.emproducciones.papy.modelo.GestionDeFechas.modeloNocheVigente;
import com.emproducciones.papy.modelo.jugadaVigente.metodosJugadaVigente;

import java.util.ArrayList;

public class adapterLotosVigentes extends RecyclerView.Adapter {

    public ArrayList<metodosJugadaVigente> lotos;
    Context context;
    modeloNocheVigente fechaNoche;

    public adapterLotosVigentes(ArrayList<metodosJugadaVigente> lotos, Context context, modeloNocheVigente fechaNoche){
        this.lotos=lotos;
        this.context=context;
        this.fechaNoche = fechaNoche;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_mostrar_lotos_vigentes,viewGroup,false);
        modelado model = new modelado(view);
        return model;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((modelado)viewHolder).onBind(i);
    }

    @Override
    public int getItemCount() {
        if (lotos ==null) return 0;
        else
            return lotos.size();
    }


public class modelado extends RecyclerView.ViewHolder{
    TextView nombre,numerosJugados,noche;

    public modelado(@NonNull View itemView) {
        super(itemView);
        nombre=itemView.findViewById(R.id.txtNombreJugador);
        numerosJugados=itemView.findViewById(R.id.txtNumerosSorteados);
        noche=itemView.findViewById(R.id.txtNumeroNocheJugada);
    }

    public void onBind (int Indice){
        metodosJugadaVigente metodosJugadaVigentetemp = lotos.get(Indice);
        nombre.setText(metodosJugadaVigentetemp.getNombreJugador());
        numerosJugados.setText(String.valueOf(metodosJugadaVigentetemp.getNumA())+" - " + String.valueOf(metodosJugadaVigentetemp.getNumb()) + " - "
                + String.valueOf(metodosJugadaVigentetemp.getNumc()));
        noche.setText(fechaNoche.getNumeronoche()+"");
        }
    }
}


