package com.emproducciones.papy.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.emproducciones.papy.R;
import com.emproducciones.papy.modelo.modeloBusqueda;

import java.util.ArrayList;

public class adaptadorResultadosBusquedas extends RecyclerView.Adapter {

    public ArrayList<modeloBusqueda> busqueda;
    Context context;

    public adaptadorResultadosBusquedas(ArrayList<modeloBusqueda> busqueda,Context context){
        this.busqueda=busqueda;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.actividad_mostrar_busqueda,viewGroup,false);

        modelo mod = new modelo(view);

        return mod;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ((modelo)viewHolder).onBind(i);

    }

    @Override
    public int getItemCount() {
        return busqueda.size();
    }

    class modelo extends RecyclerView.ViewHolder{

        TextView txtNombreJugadorBusqueda;
        TextView txtNumerosSorteados;
        TextView txtNumeroNocheBusqueda;

        public modelo(@NonNull View itemView) {
            super(itemView);
            txtNombreJugadorBusqueda=itemView.findViewById(R.id.txtNombreJugadorBusqueda);
            txtNumerosSorteados = itemView.findViewById(R.id.txtNumerosSorteados);
            txtNumeroNocheBusqueda = itemView.findViewById(R.id.txtNumeroNocheBusqueda);
        }

        public void onBind (int indice){
            modeloBusqueda modTemp = busqueda.get(indice);

            txtNombreJugadorBusqueda.setText(modTemp.getMetodosNocheVigente().getNombreJugador());
            txtNumerosSorteados.setText(modTemp.getMetodosNocheVigente().getNumA() + " - "+modTemp.getMetodosNocheVigente().getNumb()+" - "+modTemp.getMetodosNocheVigente().getNumc());
            txtNumeroNocheBusqueda.setText("Noche: " + modTemp.getNoche());
        }
    }
}
