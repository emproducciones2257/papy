package com.emproducciones.papy.modelo;

import com.emproducciones.papy.modelo.jugadaVigente.metodosJugadaVigente;

public class validaciones {

    public Boolean verificarRepetidos (metodosJugadaVigente l){
        boolean estado=false;
        if ((l.getNumA()!= l.getNumb())&&(l.getNumA()!= l.getNumc())&&(l.getNumb()!= l.getNumc())){

            estado=true;
        }
        return estado;
    }
}
