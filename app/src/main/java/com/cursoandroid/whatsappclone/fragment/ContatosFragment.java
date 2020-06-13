package com.cursoandroid.whatsappclone.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cursoandroid.whatsappclone.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<String> contatos;

    public ContatosFragment() {
        // Required empty public constructor
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
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, contatos);

        //Setting up list view
        listView.setAdapter(adapter);





        return view;
    }

}
