package com.emproducciones.papy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.*;
import android.view.*;
import com.emproducciones.papy.adaptadores.adaptadorNochesJugadas;
import com.emproducciones.papy.modelo.modeloNochesJugadas;
import java.util.ArrayList;


public class fragmentNochesJugadas extends Fragment {

    ArrayList<modeloNochesJugadas> noches;
    DataSQL data;


    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static fragmentNochesJugadas newInstance() {
        fragmentNochesJugadas fragment = new fragmentNochesJugadas();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_noches_jugadas, container, false);
        RecyclerView reciclerNochesJugadas = view.findViewById(R.id.reciclerNochesJugadas);
        data = new DataSQL(getContext());
        noches=data.getNochesJugadas();

        adaptadorNochesJugadas adaptadorNochesJugadas = new adaptadorNochesJugadas(noches,getActivity());
        reciclerNochesJugadas.setAdapter(adaptadorNochesJugadas);
        reciclerNochesJugadas.setLayoutManager(new LinearLayoutManager(getActivity()));
        reciclerNochesJugadas.setHasFixedSize(true);
        return view;
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
