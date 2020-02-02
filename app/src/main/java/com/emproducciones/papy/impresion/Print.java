package com.emproducciones.papy.impresion;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import java.util.Locale;

public class Print extends AppCompatActivity {

   public void imprimir(){
        String demoStr = "EMProducciones";
        String Titulo = "CLUB ATLETICO CASTILLA";
        String Subt = "PAPYLOTO";
        String n1 = "1";
        String n2 = "13";
        String n3 = "11";
        String noche = "Noche: 30";
        String publi = "Sistemas EMProducciones";
        String fecha="2019-11-16";

       String paraImprimir =  ESCPOS.EstiloPasable + ESCPOS.FuenteDobleDeALtura + ESCPOS.AligCentro + Titulo + ESCPOS.SaltoDeLinea// titulo
               + ESCPOS.EstiloPasable + ESCPOS.FuenteDobleDeALtura + ESCPOS.AligCentro + Subt + ESCPOS.SaltoDeLinea // sub
               + ESCPOS.SaltoDeLinea + ESCPOS.EstiloPasable + ESCPOS.AligCentro + demoStr + ESCPOS.SaltoDeLinea // nombre
               + ESCPOS.SaltoDeLinea + ESCPOS.EstiloPasable + ESCPOS.AligCentro + n1 + " - " + n2 + " - " + n3 + ESCPOS.SaltoDeLinea // numeros
               + ESCPOS.SaltoDeLinea + ESCPOS.FuenteB + ESCPOS.AligIzquierda + fecha + ESCPOS.SaltoDeLinea // fecha
               + ESCPOS.FuenteB + ESCPOS.AligDerecha + noche + ESCPOS.SaltoDeLinea // Noche
               + ESCPOS.SaltoDeLinea + ESCPOS.lineas + ESCPOS.SaltoDeLinea
               + ESCPOS.SaltoDeLinea + ESCPOS.FuenteB + ESCPOS.AligCentro + publi // publi
               + ESCPOS.CorteCompleto;

        String textToPrint = String.format(Locale.ROOT, paraImprimir, 4);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, textToPrint);
        sendToPrint(intent);

        }

    protected void sendToPrint(Intent intent) {
        final String appPackageName = "ru.a402d.rawbtprinter";
        PackageManager pm = getPackageManager();

        // check app installed
        PackageInfo pi = null;
        if (pm != null) {
            try {
                pi = pm.getPackageInfo(appPackageName, 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (pi == null) {
            System.out.println("NO ESTA INSTALADA LA COSA");
        } else {
            // send to print
            intent.setPackage(appPackageName);
            startActivity(intent);
        }
    }
}
