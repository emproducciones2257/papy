package com.emproducciones.papy.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;
import com.emproducciones.papy.R;
import com.emproducciones.papy.consultas;
import com.emproducciones.papy.modelo.modeloNochesJugadas;
import java.util.ArrayList;

public class adaptadorNochesJugadas extends RecyclerView.Adapter {

    ArrayList<modeloNochesJugadas> nochesJugadas;
    Context context;
    float pozoAcumulado = consultas.pozoInicial;
    float pozoClub = 0;
    float pozoClubNoche,pozoNoche;

    public adaptadorNochesJugadas (ArrayList<modeloNochesJugadas> nochesJugadas,Context context){
        this.nochesJugadas=nochesJugadas;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.actividad_mostrar_noches_jugadas,viewGroup,false);

        modelo modelo = new modelo(view);

        return modelo;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((modelo)viewHolder).onBind(i);
    }

    @Override
    public int getItemCount() {
        return nochesJugadas.size();
    }

    class modelo extends RecyclerView.ViewHolder{

        TextView txtNumeroNochesJugadas;
        TextView txtCantidadLotosVendidos;
        TextView txtPorcentajeClub;
        TextView txtPozo;
        TextView txtClubEstaNoche;
        TextView txtPozoEstaNoche;

        public modelo (@NonNull View itemView) {
            super(itemView);
            txtClubEstaNoche = itemView.findViewById(R.id.txtClubEstaNoche);
            txtPozoEstaNoche = itemView.findViewById(R.id.txtPozoEstaNoche);
            txtNumeroNochesJugadas = itemView.findViewById(R.id.txtNumeroNochesJugadas);
            txtCantidadLotosVendidos = itemView.findViewById(R.id.txtCantidadLotosVendidos);
            txtPorcentajeClub = itemView.findViewById(R.id.txtPorcentajeClub);
            txtPozo = itemView.findViewById(R.id.txtPozo);
        }

        public void onBind (int indice){
            modeloNochesJugadas modeloTemp = nochesJugadas.get(indice);
            txtNumeroNochesJugadas.setText(modeloTemp.getNumeroNoche()+"");
            txtCantidadLotosVendidos.setText("LOTOS VENDIDOS: " + modeloTemp.getLotosVendidos() + " // $" + modeloTemp.getLotosVendidos()*consultas.precioLoto);
            cargarResultadosNoche(modeloTemp.getLotosVendidos());
            txtClubEstaNoche.setText("30%: $" + pozoClubNoche);
            txtPozoEstaNoche.setText("70%: $" + pozoNoche);
            txtPorcentajeClub.setText("CLUB: $ " + getPorcentajeClub(modeloTemp));
            txtPozo.setText("POZO: $ " + pozoAcumulado);
        }
    }

    void cargarResultadosNoche(int lotosVendidos){
        int totalDinero = consultas.precioLoto*lotosVendidos;
        pozoClubNoche = ((float)(totalDinero*consultas.porcentajeClub)/100);
        pozoNoche = ((float)(totalDinero*consultas.porcentajePozo)/100);
    }

    String getPorcentajeClub(modeloNochesJugadas cantidadLotos){
        int totalDinero = consultas.precioLoto*cantidadLotos.getLotosVendidos();
        pozoAcumulado = pozoAcumulado + ((float)(totalDinero*consultas.porcentajePozo)/100);
        pozoClub = pozoClub + ((float)(totalDinero*consultas.porcentajeClub)/100);
        return Double.toString(pozoClub);
    }
}
