package com.emproducciones.papy;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import com.emproducciones.papy.SQLiteConstant.*;
import com.emproducciones.papy.SQLiteHelepr.SQLiteHelpers;
import com.emproducciones.papy.modelo.GestionDeFechas.modeloNocheVigente;
import com.emproducciones.papy.modelo.jugadaVigente.metodosJugadaVigente;
import com.emproducciones.papy.modelo.modeloBusqueda;
import com.emproducciones.papy.modelo.modeloEstadistica;
import com.emproducciones.papy.modelo.modeloNochesJugadas;
import java.util.ArrayList;

public class DataSQL {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    SQLiteOpenHelper sqLiteOpenHelper;

    public DataSQL(Context context){
        this.context=context;
        sqLiteOpenHelper=new SQLiteHelpers(context);
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }
    public void open(){
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }
    public void close(){
        sqLiteOpenHelper.close();
    }

    public modeloNocheVigente consultarFechaVigente (){
        modeloNocheVigente todos = new modeloNocheVigente();
        open();
        Cursor cursor = sqLiteDatabase.query(GestionNocheFecha.NOMBRE_TABLA_GESTION_NOCHE_FECHA,
                GestionNocheFecha.COLUMNAS_CONSULTA_GESTION_NOCHE,
                GestionNocheFecha.CLAUSULA_WHERE_MAYOR,
                null,null,null,
                GestionNocheFecha.COLUMN_NRO_NOCHE_GESTION_NOCHE_FECHA,
                null);

        if(cursor.getCount()==0){
            return null;
        } else {

            cursor.moveToNext();

            todos.setIdNocheFecha(cursor.getInt(cursor.getColumnIndex(GestionNocheFecha.COLUMN_ID_GESTION_NOCHE_FECHA)));
            todos.setFecha(cursor.getString(cursor.getColumnIndex(GestionNocheFecha.COLUMN_FECHA_GESTION_NOCHE_FECHA)));
            todos.setNumeronoche((byte) cursor.getInt(cursor.getColumnIndex(GestionNocheFecha.COLUMN_NRO_NOCHE_GESTION_NOCHE_FECHA)));
            todos.setEstado((byte)cursor.getInt(cursor.getColumnIndex(GestionNocheFecha.COLUMN_ESTADO_NOCHE_GESTION_NOCHE_FECHA)));
            close();
            return todos;
        }
    }

    public void insertFecha(modeloNocheVigente fecha){
        open();
        sqLiteDatabase.insert(GestionNocheFecha.NOMBRE_TABLA_GESTION_NOCHE_FECHA,null,fecha.toValues());
        close();
    }

    public void insertarLotoNoche(metodosJugadaVigente loto){
        open();
        sqLiteDatabase.insert(NocheVigente.NOMBRE_TABLA_NOCHE_VIGENTE,null,loto.toValues());
        close();
    }

    public ArrayList<metodosJugadaVigente> recuperarLotosParaSorteo(byte numeroNoche){
        open();
        ArrayList<metodosJugadaVigente> temp = new ArrayList<>();

        Cursor c = sqLiteDatabase.query(NocheVigente.NOMBRE_TABLA_NOCHE_VIGENTE,
                                    NocheVigente.COLUMNAS_RECUPERAR_CONSULTA,
                                    null,null,null,null,null,null);
        while (c.moveToNext()){
            metodosJugadaVigente loto = new metodosJugadaVigente();
            loto.setIdNocheFecha(numeroNoche);
            loto.setNumA(c.getInt(c.getColumnIndex(NocheVigente.COLUMN_NUM_A_NOCHE_VIGENTE)));
            loto.setNumb(c.getInt(c.getColumnIndex(NocheVigente.COLUMN_NUM_B_NOCHE_VIGENTE)));
            loto.setNumc(c.getInt(c.getColumnIndex(NocheVigente.COLUMN_NUM_C_NOCHE_VIGENTE)));
            loto.setNombreJugador(c.getString(c.getColumnIndex(NocheVigente.COLUMN_NOMBRE_NOCHE_VIGENTE)));
            temp.add(loto);
        }
        close();
        return temp;
    }
    // lo que hago es poner la fecha en false para habilitar la proxima fecha
    public void cerrarFecha(int idNoche){
        open();
        ContentValues c = new ContentValues();
        c.put(GestionNocheFecha.COLUMN_ESTADO_NOCHE_GESTION_NOCHE_FECHA,0);
        String[] whereArgs = new String[] {String.valueOf(idNoche)};
        sqLiteDatabase.update(GestionNocheFecha.NOMBRE_TABLA_GESTION_NOCHE_FECHA,
                c, GestionNocheFecha.WHERE_CLAUSE_FECHA_FALSE,
                whereArgs);

        close();
    }

    // cargo los lotos sorteados en la tabla de todos los lotos
    public void cargarLotosARegistro(ArrayList<metodosJugadaVigente> arregloMetodosNocheVigente){
        open();
        for (metodosJugadaVigente e : arregloMetodosNocheVigente) {
            sqLiteDatabase.insert(lotosTodos.NOMBRE_TABLA,null,obtenerValues(e));
        }
        reiniciarTablaLotosNoche();
        close();
    }

    private ContentValues obtenerValues(metodosJugadaVigente e){
        ContentValues c = new ContentValues();
        c.put(lotosTodos.COLUMN_ID_NOCHE,e.getIdNocheFecha());
        c.put(lotosTodos.COLUMN_NOMBRE,e.getNombreJugador());
        c.put(lotosTodos.COLUMN_NUM_A,e.getNumA());
        c.put(lotosTodos.COLUMN_NUM_B,e.getNumb());
        c.put(lotosTodos.COLUMN_NUM_C,e.getNumc());
        c.put(lotosTodos.COLUMN_ACIERTOS,e.getAciertos());
      return c;
    }

    public void reiniciarTablaLotosNoche() {
        open();
        sqLiteDatabase.execSQL(NocheVigente.ELIMINAR_TABLA_NOCHE_VIGENTE);
        sqLiteDatabase.execSQL(NocheVigente.CREAR_TABLA_NOCHE_VIGENTE);
        close();
    }

    public void registrarNocheEstadisticas(modeloEstadistica modeloEstadistica) {
        open();
        sqLiteDatabase.insert(Estadisticas.NOMBRE_TABLA,null,obtenerValuesEstadisticas(modeloEstadistica));
        close();
    }

    private ContentValues obtenerValuesEstadisticas(modeloEstadistica h){
        ContentValues c = new ContentValues();
        c.put(Estadisticas.COLUMN_ID_NOCHE,h.getNumeroNoche());
        c.put(Estadisticas.COLUMN_NUM_A,h.getMetodosNocheVigente().getNumA());
        c.put(Estadisticas.COLUMN_NUM_B,h.getMetodosNocheVigente().getNumb());
        c.put(Estadisticas.COLUMN_NUM_C,h.getMetodosNocheVigente().getNumc());
        c.put(Estadisticas.ESTADO,h.getResultado());
        c.put(Estadisticas.ULTIMA_JUGADA,h.getUltimaJugada());
        return c;
    }

    public ArrayList<Integer> obtenerGanadoresLotosTodos(int idNoche) {
        open();
        ArrayList<Integer> id = new ArrayList<>();

        String []Args = new String[]{String.valueOf(idNoche),String.valueOf(3)};

        Cursor c = sqLiteDatabase.query(lotosTodos.NOMBRE_TABLA,
                lotosTodos.COLUMNAS_RECUPERAR_ID,
                lotosTodos.CLAUSULA_WHERE,Args,null,null,null);
        while (c.moveToNext()){
            id.add(c.getInt(c.getColumnIndex(lotosTodos.COLUMN_ID)));
        }
        close();
        return id;
    }
    //recupero registros de las estadisticas
    public ArrayList<modeloEstadistica> getEstadisticasNoche() {
        open();
        ArrayList<modeloEstadistica> estadisticas = new ArrayList<>();
        Cursor c = sqLiteDatabase.query(Estadisticas.NOMBRE_TABLA,
                                        Estadisticas.COLUMNAS_CONSULTA,
                null,null,null,null,null,null);
        while (c.moveToNext()){
            modeloEstadistica temp = new modeloEstadistica();
            metodosJugadaVigente mtemp = new metodosJugadaVigente();
            temp.setNumeroNoche(c.getInt(c.getColumnIndex(Estadisticas.COLUMN_ID_NOCHE)));
            mtemp.setNumA(c.getInt(c.getColumnIndex(Estadisticas.COLUMN_NUM_A)));
            mtemp.setNumb(c.getInt(c.getColumnIndex(Estadisticas.COLUMN_NUM_B)));
            mtemp.setNumc(c.getInt(c.getColumnIndex(Estadisticas.COLUMN_NUM_C)));
            temp.setResultado(c.getInt(c.getColumnIndex(Estadisticas.ESTADO)));
            temp.setUltimaJugada(c.getInt(c.getColumnIndex(Estadisticas.ULTIMA_JUGADA)));
            temp.setMetodosNocheVigente(mtemp);
            //si tengo uno en estado quiere decir que tengo ganadores, en este punto recupero los nombres de lotos ganadores
            if((temp.getResultado()==1)){
                temp.setNombres(obtenerNombresGanadores(temp.getNumeroNoche()));
            }
            //si tengo uno en la ultima jugada recupero los numeros anexos al sorteo
            if(temp.getUltimaJugada()==1){
                temp.setNrosUltimaJugada(obtenerNumerosUltimaJugada(temp.getNumeroNoche()));
            }
            temp.setResultado(c.getInt(c.getColumnIndex(Estadisticas.ESTADO)));
            estadisticas.add(temp);
        }
        close();
        return estadisticas;
    }

    private ArrayList<com.emproducciones.papy.modelo.NumerosUltimaJugada> obtenerNumerosUltimaJugada(int numeroNoche) {
        open();

        ArrayList<com.emproducciones.papy.modelo.NumerosUltimaJugada> numerosUltimaJugadas= new ArrayList<>();

        Cursor c = sqLiteDatabase.query(NumerosUltimaJugada.NOMBRE_TABLA,
                NumerosUltimaJugada.COLUMNAS,
                NumerosUltimaJugada.CLAUSULA_WHERE_ID_NOCHE,
                new String[]{String.valueOf(numeroNoche)},
                null,null,null,null);

        while (c.moveToNext()){
            int temp = c.getInt(c.getColumnIndex(NumerosUltimaJugada.COLUMN_ID_NOCHE));
            int temp2 = c.getInt(c.getColumnIndex(NumerosUltimaJugada.COLUMN_NUM));
            numerosUltimaJugadas.add(new com.emproducciones.papy.modelo.NumerosUltimaJugada((byte)temp,(byte)temp2));
        }

        close();
        return numerosUltimaJugadas;
    }

    private ArrayList<String> obtenerNombresGanadores(int numeroNoche) {
        open();
        ArrayList<String> nombres = new ArrayList<>();
        ArrayList<Integer> indicesGanadores = new ArrayList<>();
        String[] argumentos = new String[]{String.valueOf(numeroNoche)};
        //recupero los ID de la tabla ganadores para luego buscarlos en la tabla de todos los lotos
        Cursor cursorGanadores = sqLiteDatabase.query(ganadores.NOMBRE_TABLA,
                ganadores.COLUMNAS_RECUPERAR_ID_GANADORES,
                ganadores.CLAUSULA_WHERE_ID_GANADORES,
                argumentos,null,null,null,null);
        while (cursorGanadores.moveToNext()){
            int i = cursorGanadores.getInt(cursorGanadores.getColumnIndex(ganadores.COLUMN_ID_GANADOR));
            indicesGanadores.add(i);
        }

        for (Integer indicesGanadore : indicesGanadores) {
            String[]selectionArgunment = new String[]{String.valueOf(indicesGanadore)};
            Cursor c = sqLiteDatabase.query(lotosTodos.NOMBRE_TABLA,
                    lotosTodos.COLUMNAS_RECUPERAR_NOMBRES,
                    lotosTodos.CLAUSULA_WHERE_NOMBRES_GANADORES,
                    selectionArgunment,null,null,null);
            while (c.moveToNext()){
                nombres.add(c.getString(c.getColumnIndex(lotosTodos.COLUMN_NOMBRE)));
            }
        }
        close();
        return nombres;
    }

    //cargo los ganadores en la tabla ganadores con sus id
    public void cargarGanadores(byte numeroNoche, Integer e) {
        open();
        ContentValues c = new ContentValues(2);
        c.put(ganadores.COLUMN_ID_NOCHE,numeroNoche);
        c.put(ganadores.COLUMN_ID_GANADOR,e);
        sqLiteDatabase.insert(ganadores.NOMBRE_TABLA,null,c);
        close();
    }

    //obtengo el informe de las noches jugadas
    public ArrayList<modeloNochesJugadas> getNochesJugadas() {
        open();
        ArrayList<modeloNochesJugadas> mod = new ArrayList<>();
        int noches;
        //primero obtengo la cantidad de noches jugadas SELECT MAX(idNoche) FROM lotosTodos
        noches=obtenerCantidadDeNochesJugadas();
        //recorro desde 1 hasta el resultado de la consulta anterior contando cuantos lotos tengo
        // por noche SELECT COUNT(idNoche) FROM lotosTodos WHERE idNoche=?
        for (int i = 1;i<=noches;i++){
            modeloNochesJugadas temp = new modeloNochesJugadas();
            temp.setNumeroNoche((byte) i);
            temp.setLotosVendidos((int)getCantidadLotosVendidosPorNoche(i));
            mod.add(temp);
        }
        close();
        return mod;
    }

    private long getCantidadLotosVendidosPorNoche(int nocheABuscar) {
        long i;
        open();
        i= DatabaseUtils.queryNumEntries(sqLiteDatabase,
                lotosTodos.NOMBRE_TABLA,
                lotosTodos.CLAUSULA_WHERE_ID_NOCHE,
                new String[]{String.valueOf(nocheABuscar)});
        close();
        return i;
    }

    private int obtenerCantidadDeNochesJugadas(){
       int i;
       open();
       Cursor c = sqLiteDatabase.query(lotosTodos.NOMBRE_TABLA, new String [] {lotosTodos.CLAUSULA_MAX_ID_NOCHE},
               null,null,null,null,null);
       c.moveToNext();
       i=c.getInt(c.getColumnIndex(lotosTodos.CLAUSULA_MAX_ID_NOCHE));
       close();
       return i;
    }

    public ArrayList<modeloBusqueda> obtenerLotosTodos() {
        open();
        ArrayList<modeloBusqueda> lo = new ArrayList<modeloBusqueda>();

        Cursor c = sqLiteDatabase.query(lotosTodos.NOMBRE_TABLA,
                        lotosTodos.COLUMNAS_RECUPERAR_TODAS_BUSQUEDA,null,null,null,null,null,null);
        while (c.moveToNext()){
            modeloBusqueda temp = new modeloBusqueda();
            metodosJugadaVigente me = new metodosJugadaVigente();
            temp.setNoche((byte)c.getInt(c.getColumnIndex(lotosTodos.COLUMN_ID_NOCHE)));
            me.setNombreJugador(c.getString(c.getColumnIndex(lotosTodos.COLUMN_NOMBRE)));
            me.setNumA(c.getInt(c.getColumnIndex(lotosTodos.COLUMN_NUM_A)));
            me.setNumb(c.getInt(c.getColumnIndex(lotosTodos.COLUMN_NUM_B)));
            me.setNumc(c.getInt(c.getColumnIndex(lotosTodos.COLUMN_NUM_C)));
            temp.setMetodosNocheVigente(me);
            lo.add(temp);
        }
        close();
        return lo;
    }

    public ArrayList<modeloBusqueda> buscarPorNombre(String texto) {
        open();
        ArrayList<modeloBusqueda> lo = new ArrayList<>();

        Cursor c = sqLiteDatabase.query(lotosTodos.NOMBRE_TABLA,
                lotosTodos.COLUMNAS_RECUPERAR_TODAS_BUSQUEDA,
                lotosTodos.CLAUSULA_PARA_LIKE,new String[]{"%"+texto+"%"},null,null,null,null);
        while (c.moveToNext()){
            modeloBusqueda temp = new modeloBusqueda();
            metodosJugadaVigente me = new metodosJugadaVigente();
            temp.setNoche((byte)c.getInt(c.getColumnIndex(lotosTodos.COLUMN_ID_NOCHE)));
            me.setNombreJugador(c.getString(c.getColumnIndex(lotosTodos.COLUMN_NOMBRE)));
            me.setNumA(c.getInt(c.getColumnIndex(lotosTodos.COLUMN_NUM_A)));
            me.setNumb(c.getInt(c.getColumnIndex(lotosTodos.COLUMN_NUM_B)));
            me.setNumc(c.getInt(c.getColumnIndex(lotosTodos.COLUMN_NUM_C)));
            temp.setMetodosNocheVigente(me);
            lo.add(temp);
        }
        close();
        return lo;
    }

    public ArrayList<metodosJugadaVigente> obtenerLotosVigente() {
        ArrayList<metodosJugadaVigente> lotosTemp = new ArrayList<>();
        open();

        Cursor c = sqLiteDatabase.query(NocheVigente.NOMBRE_TABLA_NOCHE_VIGENTE,
                                    NocheVigente.COLUMNAS_RECUPERAR_CONSULTA_CONTROL,
                null,null,null,null,null,null);

        while (c.moveToNext()){
            metodosJugadaVigente temp = new metodosJugadaVigente();
            temp.setIdLoto(c.getInt(c.getColumnIndex(NocheVigente.COLUMN_ID_NOCHE_VIGENTE)));
            temp.setNombreJugador(c.getString(c.getColumnIndex(NocheVigente.COLUMN_NOMBRE_NOCHE_VIGENTE)));
            temp.setNumA(c.getInt(c.getColumnIndex(NocheVigente.COLUMN_NUM_A_NOCHE_VIGENTE)));
            temp.setNumb(c.getInt(c.getColumnIndex(NocheVigente.COLUMN_NUM_B_NOCHE_VIGENTE)));
            temp.setNumc(c.getInt(c.getColumnIndex(NocheVigente.COLUMN_NUM_C_NOCHE_VIGENTE)));
            lotosTemp.add(temp);
        }
        close();
        return lotosTemp;
    }

    public void eliminarLotoNocheVigente(Integer idLoto) {
        String [] wherArg = new String[]{String.valueOf(idLoto)};
        open();
        sqLiteDatabase.delete(NocheVigente.NOMBRE_TABLA_NOCHE_VIGENTE,NocheVigente.CLAUSULA_WHERE_ELIMINAR_LOTO,wherArg);
        close();
    }

    public modeloEstadistica recuperarRegistroNoche(byte numeroNoche) {
        open();
        String[] argumentos = new String[]{String.valueOf(numeroNoche)};
        modeloEstadistica temp = new modeloEstadistica();

        Cursor cursor = sqLiteDatabase.query(Estadisticas.NOMBRE_TABLA,
                Estadisticas.COLUMNAS_CONSULTA_AGREGAR_ULTIMA_NOCHE,
                Estadisticas.CLAUSULA_WHERE_ID_NOCHE,
                argumentos,null,null,null,null);

        while (cursor.moveToNext()){
            temp.setId(cursor.getInt(cursor.getColumnIndex(Estadisticas.COLUMN_ID)));
            temp.setNumeroNoche(cursor.getInt(cursor.getColumnIndex(Estadisticas.COLUMN_ID_NOCHE)));
            temp.setResultado(1);
            temp.setUltimaJugada(1);
            metodosJugadaVigente t = new metodosJugadaVigente();
            t.setNumA(cursor.getInt(cursor.getColumnIndex(Estadisticas.COLUMN_NUM_A)));
            t.setNumb(cursor.getInt(cursor.getColumnIndex(Estadisticas.COLUMN_NUM_B)));
            t.setNumc(cursor.getInt(cursor.getColumnIndex(Estadisticas.COLUMN_NUM_C)));
            temp.setMetodosNocheVigente(t);
        }
        close();
        return temp;
    }

    public void actualizarRegistroUtimaJugada(modeloEstadistica registroNoche) {
        open();
        sqLiteDatabase.update(Estadisticas.NOMBRE_TABLA,
                obtenerValuesEstadisticas(registroNoche),
                Estadisticas.CLAUSULA_WHERE_ID_NOCHE,new String[]{String.valueOf(registroNoche.getId())});
        close();
    }

    public void registrarNumerosUltimaJugada(ArrayList<com.emproducciones.papy.modelo.NumerosUltimaJugada> nrosUltimaJugada) {
        open();
        for (com.emproducciones.papy.modelo.NumerosUltimaJugada e : nrosUltimaJugada){
            sqLiteDatabase.insert(NumerosUltimaJugada.NOMBRE_TABLA,
                    null,
                    e.toValues());
        }
        close();
    }


    public void actualizarRegistroJugadorGanadorUltimaFecha(ArrayList<metodosJugadaVigente> resultadoInterno) {
        open();
        for (metodosJugadaVigente e : resultadoInterno){

            String[] argumentos = new String[]{String.valueOf(e.getIdNocheFecha()),
                    e.getNombreJugador(),String.valueOf(e.getNumA()),String.valueOf(e.getNumb()),
                    String.valueOf(e.getNumc())};
            e.setAciertos((byte)3);
            sqLiteDatabase.update(lotosTodos.NOMBRE_TABLA,obtenerValues(e),
                    lotosTodos.CLAUSULA_WHERE_ACTUALIZAR_GANADOR,argumentos);
        }
        close();
    }
}
