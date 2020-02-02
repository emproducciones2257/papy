package com.emproducciones.papy.impresion;

public class ESCPOS {

    //Parece anda
    public static final String SaltoDeLinea = new String(new byte[]{0x0A});
    public static final String SubrayarFuerte = new String(new byte[]{0x1B, 0x2D, 02});
    public static final String ini = new String(new byte[]{0x1B, 0x40});
    public static final String CorteCompleto = new String(new byte[]{0x1D, 0x56, 66, 42});
    public static final String AligIzquierda = new String(new byte[]{0x1B, 0x61, 48});
    public static final String AligCentro = new String(new byte[]{0x1B, 0x61, 49});
    public static final String AligDerecha = new String(new byte[]{0x1B, 0x61, 50});
    public static final String TamanoCaracterExtraGrande = new String(new byte[]{0x1D, 0x21, 20});
    public static final String FuenteDobleDeALtura = new String(new byte[]{0x1D, 0x21, 18}); //55 muy grande, trabajar aca los tamaños
    public static final String FuenteChica = new String(new byte[]{0x1B, 0x21, 01});
    public static final String EstiloPasable = new String(new byte[]{0x1B, 0x21, 38});
    public static final String NegritaOn = new String(new byte[]{0x1B, 0x45, 01});
    public static final String NegritaOFF = new String(new byte[]{0x1B, 0x45, 00});

    // para el papyloto, tamaños
    public static final String Titulo = new String(new byte[]{0x1D, 0x21, 18});

    public static final String FuenteA = new String(new byte[]{0x1B, 0x4D, 48});
    public static final String FuenteB = new String(new byte[]{0x1B, 0x4D, 49});


    public static final String lineas= "--------------------------------";
}
