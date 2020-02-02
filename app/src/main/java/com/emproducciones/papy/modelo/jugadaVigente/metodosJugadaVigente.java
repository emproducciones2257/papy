package com.emproducciones.papy.modelo.jugadaVigente;

import android.content.ContentValues;
import com.emproducciones.papy.SQLiteConstant.NocheVigente;
import com.emproducciones.papy.modelo.validaciones;

import java.util.ArrayList;

public class metodosJugadaVigente implements Comparable<metodosJugadaVigente> {

    private Integer idLoto=null;
    private int idNocheFecha;
    private int numA;
    private int numb;
    private int numc;
    private String nombreJugador;
    private Boolean estado;
    private byte aciertos;

    validaciones uti = new validaciones();

    public metodosJugadaVigente(int idLoto, int idNocheFecha, int numA, int numb, int numc, String nombreJugador, byte aciertos) {
        this.idLoto=idLoto;
        this.idNocheFecha=idNocheFecha;
        this.numA = numA;
        this.numb = numb;
        this.numc = numc;
        this.nombreJugador = nombreJugador;
        this.aciertos=aciertos;
    }

    public metodosJugadaVigente(int idNocheFecha, int numA, int numb, int numc, String nombreJugador, byte aciertos) {
        this.idNocheFecha=idNocheFecha;
        this.numA = numA;
        this.numb = numb;
        this.numc = numc;
        this.nombreJugador = nombreJugador;
        this.aciertos=aciertos;
    }

    public metodosJugadaVigente(int numA, int numb, int numc) {
        this.numA = numA;
        this.numb = numb;
        this.numc = numc;
    }

    public metodosJugadaVigente(){

    }

    public Integer getIdLoto() {
        return idLoto;
    }

    public void setIdLoto(Integer idLoto) {
        this.idLoto = idLoto;
    }

    public int getIdNocheFecha() {
        return idNocheFecha;
    }

    public void setIdNocheFecha(int idNocheFecha) {
        this.idNocheFecha = idNocheFecha;
    }

    public int getNumA() {
        return numA;
    }

    public void setNumA(int numA) {
        this.numA = numA;
    }

    public int getNumb() {
        return numb;
    }

    public void setNumb(int numb) {
        this.numb = numb;
    }

    public int getNumc() {
        return numc;
    }

    public void setNumc(int numc) {
        this.numc = numc;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public byte getAciertos() {
        return aciertos;
    }

    public void setAciertos(byte aciertos) {
        this.aciertos = aciertos;
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues(7);
        contentValues.put(NocheVigente.COLUMN_ID_NOCHE_VIGENTE,idLoto);
        contentValues.put(NocheVigente.COLUMN_ID_NOCHE,idNocheFecha);
        contentValues.put(NocheVigente.COLUMN_NUM_A_NOCHE_VIGENTE,numA);
        contentValues.put(NocheVigente.COLUMN_NUM_B_NOCHE_VIGENTE,numb);
        contentValues.put(NocheVigente.COLUMN_NUM_C_NOCHE_VIGENTE,numc);
        contentValues.put(NocheVigente.COLUMN_NOMBRE_NOCHE_VIGENTE,nombreJugador);
        contentValues.put(NocheVigente.COLUMN_ACIERTOS_NOCHE_VIGENTE,aciertos);
        return contentValues;
    }

    @Override
    public String toString (){

        return nombreJugador + "  -  " + numA +", " +numb + ", "+ numc + ", Aciertos: " + aciertos;
    }

    @Override
    public int compareTo(metodosJugadaVigente o) {
        int i=0;

        if ((this.numA==o.getNumA())|| (this.numA==o.getNumb())||(this.numA==o.getNumc())) {
            i++;
        }

        if ((this.numb==o.getNumA())|| (this.numb==o.getNumb())||(this.numb==o.getNumc())) {
            i++;
        }

        if ((this.numc==o.getNumA())|| (this.numc==o.getNumb())||(this.numc==o.getNumc())) {
            i++;;
        }
        return i;
    }

    public ArrayList<metodosJugadaVigente> generarPapiAleatorio (ArrayList<metodosJugadaVigente> lotosAleatorios){
        int x =0;
        for (int i = 0;i<750;i++){
            metodosJugadaVigente temp=producirAleatorio(x);
            if (uti.verificarRepetidos(temp)){
                lotosAleatorios.add(temp);
                x++;
            }
        }
        return lotosAleatorios;
    }
    private metodosJugadaVigente producirAleatorio (int i){
        int tempa=(int)(Math.random()*((24-0)+1))+0;
        int tempb=(int)(Math.random()*((24-0)+1))+0;
        int tempc=(int)(Math.random()*((24-0)+1))+0;
        metodosJugadaVigente lte= new metodosJugadaVigente(29,tempa, tempb, tempc, "jugador " + i, (byte)0);
        return lte;
    }

}