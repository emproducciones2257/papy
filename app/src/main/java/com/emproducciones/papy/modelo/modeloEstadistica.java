package com.emproducciones.papy.modelo;

import com.emproducciones.papy.modelo.jugadaVigente.metodosJugadaVigente;
import java.util.ArrayList;

public class modeloEstadistica {

    private int id;
    private int numeroNoche;
    private metodosJugadaVigente metodosNocheVigente;
    private int resultado;
    private ArrayList<String> nombres;
    private int ultimaJugada; //1 para cuando es ultima jugada, 0 para cuando no
    private ArrayList<NumerosUltimaJugada> nrosUltimaJugada;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroNoche() {
        return numeroNoche;
    }

    public void setNumeroNoche(int numeroNoche) {
        this.numeroNoche = numeroNoche;
    }

    public metodosJugadaVigente getMetodosNocheVigente() {
        return metodosNocheVigente;
    }

    public void setMetodosNocheVigente(metodosJugadaVigente metodosNocheVigente) {
        this.metodosNocheVigente = metodosNocheVigente;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    public int getResultado() {
        return resultado;
    }

    public ArrayList<String> getNombres() {
        return nombres;
    }

    public void setNombres(ArrayList<String> nombres) {
        this.nombres = nombres;
    }

    public int getUltimaJugada() {
        return ultimaJugada;
    }

    public void setUltimaJugada(int ultimaJugada) {
        this.ultimaJugada = ultimaJugada;
    }

    public ArrayList<NumerosUltimaJugada> getNrosUltimaJugada() {
        return nrosUltimaJugada;
    }

    public void setNrosUltimaJugada(ArrayList<NumerosUltimaJugada> nrosUltimaJugada) {
        this.nrosUltimaJugada = nrosUltimaJugada;
    }
}
