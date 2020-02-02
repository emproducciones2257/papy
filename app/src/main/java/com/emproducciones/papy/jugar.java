package com.emproducciones.papy;

import android.content.*;
import android.support.v7.app.*;
import android.view.View;
import android.widget.*;
import com.emproducciones.papy.impresion.ESCPOS;
import com.emproducciones.papy.modelo.GestionDeFechas.metodosNocheVigente;
import com.emproducciones.papy.modelo.GestionDeFechas.modeloNocheVigente;
import com.emproducciones.papy.modelo.jugadaVigente.metodosJugadaVigente;
import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.ColorRes;
import java.text.SimpleDateFormat;
import java.util.*;

@EActivity(R.layout.activity_jugar)
public class jugar extends AppCompatActivity {

    public static final String ID_NOCHE = "idNoche";
    public static final String NUMERO_NOCHE="numeroNoche";
    metodosJugadaVigente sorteo;
    ArrayList<Button> botonesSeleccionados;
    private modeloNocheVigente fechaNoche;
    DataSQL dat;
    private Boolean estado;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    final String appPackageName = "ru.a402d.rawbtprinter";

    @ViewById
    EditText edtNombre;

    @ViewById
    TextView edtN1, edtN2, edtN3,txtNumeroDeJugada;

    @ColorRes(R.color.primaryColor)
    static int primaryColor;

    @ColorRes(R.color.rojoError)
    static int error;

    @AfterViews
    void init (){
        sorteo = new metodosJugadaVigente();
        botonesSeleccionados = new ArrayList<>();
        dat = new DataSQL(getApplicationContext());
        //lo primero que hago es evaluar que fecha tengo que jugar
        mostrarFechaVigente();
    }

    @Click(R.id.btnSorteo)
        void btnSorete(View v){
        Intent intento = new Intent(getApplicationContext(),sorteActividad_.class);
        intento.putExtra(ID_NOCHE,fechaNoche.getIdNocheFecha());
        intento.putExtra(NUMERO_NOCHE,fechaNoche.getNumeronoche());
        startActivity(intento);
    }

    @Click(R.id.imgBtnCargar)
    void btnCarga(View v){
        cargarLoto(v);
        //ArrayList<metodosJugadaVigente> aleatorios = new ArrayList<>();
        /*aleatorios=sorteo.generarPapiAleatorio(aleatorios);

        for (metodosJugadaVigente e : aleatorios) {
            dat.insertarLotoNoche((e));
        }*/
    }

    private void cargarLoto (View view) {

        if ((botoneraCargaNumerosFragment_.verificacion()==false)||(verificarNombre()==false)){
            Toast.makeText(getApplicationContext(),"Verificar los campos ingresados",Toast.LENGTH_SHORT).show();
            estado=true;
        }else {
            sorteo = generaLoto();
            try {
                imprimir(sorteo);

            }catch (Exception e){
                Toast.makeText(this, "Verificar conexión con impresora", Toast.LENGTH_SHORT).show();
            }
            dat.insertarLotoNoche(sorteo);//guardo en la base el loto nuevo
            limpiarComponentes(edtNombre.getText().toString());

        }
    }

    Boolean verificarNombre(){
        if(edtNombre.getText().toString().isEmpty()){
            edtNombre.setError("Agregar Nombre");
            edtNombre.setHintTextColor(error);
            estado=false;
        }else {
            edtNombre.setError(null);
            edtNombre.setHintTextColor(primaryColor);
            estado=true;
        }
        return estado;
    }

    public void imprimir(metodosJugadaVigente sorteo) {
        String Titulo = "CLUB ATLETICO CASTILLA";
        String Subt = "PAPYLOTO";
        String publi = "Sistemas EMProducciones";

        String paraImprimir =  ESCPOS.EstiloPasable + ESCPOS.FuenteDobleDeALtura + ESCPOS.AligCentro + Titulo + ESCPOS.SaltoDeLinea// titulo
                + ESCPOS.EstiloPasable + ESCPOS.FuenteDobleDeALtura + ESCPOS.AligCentro + Subt + ESCPOS.SaltoDeLinea // sub
                + ESCPOS.SaltoDeLinea + ESCPOS.EstiloPasable + ESCPOS.AligCentro + sorteo.getNombreJugador() + ESCPOS.SaltoDeLinea // nombre
                + ESCPOS.SaltoDeLinea + ESCPOS.EstiloPasable + ESCPOS.AligCentro + sorteo.getNumA() + " - " + sorteo.getNumb() + " - " + sorteo.getNumc() + ESCPOS.SaltoDeLinea // numeros
                + ESCPOS.SaltoDeLinea + ESCPOS.FuenteB + ESCPOS.AligIzquierda + formatter.format(MainActivity_.fechaFormatoFecha) + ESCPOS.SaltoDeLinea // fecha
                + ESCPOS.FuenteB + ESCPOS.AligDerecha + "Noche: " + fechaNoche.getNumeronoche() + ESCPOS.SaltoDeLinea // Noche
                + ESCPOS.SaltoDeLinea + ESCPOS.lineas + ESCPOS.SaltoDeLinea
                + ESCPOS.SaltoDeLinea + ESCPOS.FuenteB + ESCPOS.AligCentro + publi // publi
                + ESCPOS.CorteCompleto;

        String textToPrint = String.format(Locale.ROOT, paraImprimir, 4);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, textToPrint);
        sendToPrint(intent);
    }

    public void mostrarFechaVigente(){
        metodosNocheVigente mod=new metodosNocheVigente(getApplicationContext());
        fechaNoche=mod.verificarFecha(MainActivity_.fechaFormatoFecha);
        txtNumeroDeJugada.setText("Jugada N°: " + fechaNoche.getNumeronoche());
    }

    //metodo que guarda el loto en la bdd
    private metodosJugadaVigente generaLoto (){
        int a = Integer.parseInt(edtN1.getText().toString());
        int b = Integer.parseInt(edtN2.getText().toString());
        int c = Integer.parseInt(edtN3.getText().toString());
        String d = edtNombre.getText().toString();
        metodosJugadaVigente temp = new metodosJugadaVigente(fechaNoche.getIdNocheFecha(),a,b,c,d,(byte)0);
        return temp;
    }

    public void limpiarComponentes(String nombre){
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Repetir Jugador");
        builder.setMessage("¿Jugar otro loto para " + nombre + " ?");
        builder.setPositiveButton("ACEPTAR",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        botoneraCargaNumerosFragment.habilitarBotonesDesabilitados();
                        botoneraCargaNumerosFragment.limpiarComponentes();
                    }
                });

        builder.setNegativeButton("CANCELAR",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edtNombre.setText("");
                        botoneraCargaNumerosFragment.habilitarBotonesDesabilitados();
                        botoneraCargaNumerosFragment.limpiarComponentes();
                    }
                });
        builder.show();
    }

    public void sendToPrint(Intent intent) {
        intent.setPackage(appPackageName);
        startActivity(intent);
    }
}


