package com.cursoandroid.whatsappclone.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cursoandroid.whatsappclone.R;
import com.cursoandroid.whatsappclone.data.ConfiguracaoFirebase;
import com.cursoandroid.whatsappclone.data.Preferencias;
import com.cursoandroid.whatsappclone.data.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<String> contatos;
    private DatabaseReference databaseReference;
    private Preferencias preferencias;
    private Usuario contato;
    private ValueEventListener valueEventListenerContatos;

    public ContatosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener( valueEventListenerContatos );
    }

    @Override
    public void onStop() {
        super.onStop();
        databaseReference.removeEventListener( valueEventListenerContatos );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);



        //Instantiate array list
        contatos = new ArrayList<String>();


        // Adding layout element reference
        listView = view.findViewById(R.id.lv_contatos);

        // Setting up array adapter
        adapter = new ArrayAdapter(getActivity(), R.layout.list_contatos, contatos);

        //Setting up list view
        listView.setAdapter(adapter);

        /* Get user id */
        preferencias = new Preferencias(getContext());
        String idUsuario = preferencias.getidUsuario();

        //get user contact list
        databaseReference = ConfiguracaoFirebase.getFirebase().child("contatos").child(idUsuario);

        valueEventListenerContatos =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //clear contact list
                contatos.clear();

                //list contacts
                for (DataSnapshot dados: dataSnapshot.getChildren()){
                    contato = dados.getValue(Usuario.class);
                    contatos.add(contato.getNome());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };




        return view;
    }

}
