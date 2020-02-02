package com.emproducciones.papy.modelo;

import com.emproducciones.papy.modelo.jugadaVigente.metodosJugadaVigente;

public class modeloBusqueda {
    metodosJugadaVigente metodosNocheVigente;
    byte noche;

    public metodosJugadaVigente getMetodosNocheVigente() {
        return metodosNocheVigente;
    }

    public void setMetodosNocheVigente(metodosJugadaVigente metodosNocheVigente) {
        this.metodosNocheVigente = metodosNocheVigente;
    }

    public byte getNoche() {
        return noche;
    }

    public void setNoche(byte noche) {
        this.noche = noche;
    }

    @Override
    public String toString() {
        return "modeloBusqueda{" +
                "metodosNocheVigente=" + metodosNocheVigente +
                ", noche=" + noche +
                '}';
    }
}
