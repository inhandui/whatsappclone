package com.cursoandroid.whatsappclone.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.cursoandroid.whatsappclone.data.Contato;

import java.util.List;

public class ContatoAdapter extends ArrayAdapter<Contato> {

    public ContatoAdapter(@NonNull Context context, int resource, @NonNull List<Contato> objects) {
        super(context, resource, objects);
    }
}
