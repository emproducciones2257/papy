package com.emproducciones.papy;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.*;
import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.ColorRes;

import java.util.ArrayList;

@EFragment(R.layout.fragment_botonera_carga_numeros)
public class botoneraCargaNumerosFragment extends Fragment {

    @ViewById
    static TextView edtN1,edtN2,edtN3;

    @ColorRes(R.color.primaryColor)
    static int primaryColor;

    @ColorRes(R.color.rojoError)
    static int error;

    static ArrayList<Button> botonesSeleccionados = new ArrayList<>();

    static Boolean estado=true;

    @Click(R.id.btnBorrarNumeros)
    void borrarNumeros(){borrarNumerosIngresadosIncorrectos();}

    @Click({R.id.btn00,R.id.btn01,R.id.btn02,R.id.btn03,R.id.btn04,R.id.btn05,R.id.btn06,R.id.btn07,R.id.btn08,R.id.btn09,
            R.id.btn10,R.id.btn11,R.id.btn12,R.id.btn13,R.id.btn14,R.id.btn15,R.id.btn16,R.id.btn17,R.id.btn18,R.id.btn19,
            R.id.btn20,R.id.btn21,R.id.btn22,R.id.btn23,R.id.btn24})
    void btn00(View view){
        escribirNumero(view);
    }

    public void borrarNumerosIngresadosIncorrectos(){
        if(!(edtN3.getText().equals("N° 3"))){
            habilitarBoton(edtN3.getText().toString());
            edtN3.setText("N° 3");
        }else if(!(edtN2.getText().equals("N° 2"))){
            habilitarBoton(edtN2.getText().toString());
            edtN2.setText("N° 2");
        }else {
            habilitarBoton(edtN1.getText().toString());
            edtN1.setText("N° 1");
        }
    }

    public void escribirNumero (View v){

        Button btnTemporal = (Button) v;

        if (edtN1.getText().equals("N° 1")){

            edtN1.setText(btnTemporal.getText());
        }else if (edtN2.getText().equals("N° 2")) {

            edtN2.setText(btnTemporal.getText());
        }else if (edtN3.getText().equals("N° 3")) {

            edtN3.setText(btnTemporal.getText());
        }else return;

        v.setEnabled(false);
        cargarBotonesSeleccionados(btnTemporal);
    }

    ArrayList<Button> getBotonesSeleccionados(){
        return botonesSeleccionados;
    }

    void cargarBotonesSeleccionados(Button temporal){

        botonesSeleccionados.add(temporal);
    }

    static void habilitarBoton (String b){

        for (Button e : botonesSeleccionados){
            if (e.getText().equals(b)){

                e.setEnabled(true);
            }
        }
    }

    static void desabilitarBoton (String b){

        for (Button e : botonesSeleccionados){
            if (e.getText().equals(b)){

                e.setEnabled(false);
            }
        }
    }

    static Boolean verificacion(){
        if(edtN1.getText().equals("N° 1")){
            edtN1.setError("Agregar Numero");
            estado=false;
            edtN1.setTextColor(error);
            return estado;
        }else {
            edtN1.setError(null);
            estado=true;
            edtN1.setTextColor(primaryColor);
        }

        if(edtN2.getText().equals("N° 2")){
            edtN2.setError("Agregar Numero");
            estado=false;
            edtN2.setTextColor(error);
            return estado;
        }else {
            edtN2.setError(null);
            edtN2.setTextColor(primaryColor);
            estado=true;
        }

        if(edtN3.getText().equals("N° 3")){
            edtN3.setError("Agregar Numero");
            edtN3.setTextColor(error);
            estado=false;
            return estado;
        }else {
            edtN3.setError(null);
            edtN3.setTextColor(primaryColor);
            estado=true;
        }
        return estado;
    }

    static Boolean verificacionUltimaJugada(){
        if(edtN1.getText().equals("N° 1")){
            edtN1.setError("Agregar Numero");
            estado=false;
            edtN1.setTextColor(error);
            return estado;
        }else {
            edtN1.setError(null);
            estado=true;
            edtN1.setTextColor(primaryColor);
        }
        return estado;
    }

    static void limpiarComponentes (){
        edtN1.setText("N° 1");
        edtN2.setText("N° 2");
        edtN3.setText("N° 3");
    }

    static void habilitarBotonesDesabilitados (){
        for (Button e : botonesSeleccionados){
            e.setEnabled(true);
        }
    }
}
