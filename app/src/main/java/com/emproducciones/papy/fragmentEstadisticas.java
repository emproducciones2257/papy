package com.emproducciones.papy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.*;
import android.view.*;
import com.emproducciones.papy.adaptadores.adaptadorEstadistica;
import com.emproducciones.papy.modelo.modeloEstadistica;
import java.util.ArrayList;


public class fragmentEstadisticas extends Fragment {

    private DataSQL data;
    private ArrayList<modeloEstadistica> todos;
    private OnFragmentInteractionListener mListener;

    public static fragmentEstadisticas newInstance() {
        fragmentEstadisticas fragment = new fragmentEstadisticas();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista = inflater.inflate(R.layout.fragment_estadisticas, container, false);
        RecyclerView recicladorEstadistica = vista.findViewById(R.id.recicladorEstadistica);

        data = new DataSQL(getContext());
        todos=data.getEstadisticasNoche();//obtengo los datos de la base estadisticas

        adaptadorEstadistica adapter = new adaptadorEstadistica(todos,getActivity());
        recicladorEstadistica.setAdapter(adapter);
        recicladorEstadistica.setLayoutManager(new LinearLayoutManager(getActivity()));
        recicladorEstadistica.setHasFixedSize(true);

        return vista;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
