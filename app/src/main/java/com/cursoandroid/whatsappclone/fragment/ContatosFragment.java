package com.cursoandroid.whatsappclone.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cursoandroid.whatsappclone.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {
    private List<String> test;

    public ContatosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contatos, container, false);
    }

}
