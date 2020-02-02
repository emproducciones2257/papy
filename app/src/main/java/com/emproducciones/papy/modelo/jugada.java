package com.emproducciones.papy.modelo;

import com.emproducciones.papy.modelo.jugadaVigente.metodosJugadaVigente;

import java.util.ArrayList;

public class jugada {

    ArrayList<metodosJugadaVigente> noche,ganadores,lotos2Coincidencias,ganadoresTerceraJugada;
    metodosJugadaVigente metodosNocheVigenteSorteado;

    public jugada (ArrayList<metodosJugadaVigente> noche, metodosJugadaVigente metodosNocheVigenteSorteado){
        this.noche= noche;
        this.metodosNocheVigenteSorteado = metodosNocheVigenteSorteado;
    }

    public ArrayList<metodosJugadaVigente> controlarPapis (){
        ganadores = new ArrayList<>();
        for(int i=0;i<noche.size();i++){
            byte t = (byte)noche.get(i).compareTo(metodosNocheVigenteSorteado);
            noche.get(i).setAciertos(t);
            if (t == 3) {
                ganadores.add(noche.get(i));
            }
        }
        return noche;
    }

    public ArrayList<metodosJugadaVigente> buscar3Coincidencias() {
        ganadores = new ArrayList<>();
        for(int i=0;i<noche.size();i++){
            byte t =noche.get(i).getAciertos();
            if (t == 3) {
                ganadores.add(noche.get(i));
            }
        }
        return ganadores;
    }

    public ArrayList<metodosJugadaVigente> buscar2Coincidencias() {
        lotos2Coincidencias = new ArrayList<>();
        for(int i=0;i<noche.size();i++){
            byte t =noche.get(i).getAciertos();
            if (t == 2) {
                lotos2Coincidencias.add(noche.get(i));
            }
        }
        return lotos2Coincidencias;
    }

    public ArrayList<metodosJugadaVigente> controlDeTerceraJugada(byte o) {
        ganadoresTerceraJugada = new ArrayList<>();
        for(int i=0;i<noche.size();i++){
            if ((noche.get(i).getNumA()==o) || (noche.get(i).getNumb()==o) || (noche.get(i).getNumc()==o))
                ganadoresTerceraJugada.add(noche.get(i));
            }
        return ganadoresTerceraJugada;
    }
}
