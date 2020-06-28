package com.cursoandroid.whatsappclone.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.cursoandroid.whatsappclone.data.Contato;

import java.util.ArrayList;


public class ContatoAdapter extends ArrayAdapter<Contato> {

    private Context context;
    private ArrayList<Contato> contatos;

    public ContatoAdapter(@NonNull Context context, @NonNull ArrayList<Contato> objects) {
        super(context, 0, objects);

        this.context = context;

        this.contatos = objects;
    }
}
