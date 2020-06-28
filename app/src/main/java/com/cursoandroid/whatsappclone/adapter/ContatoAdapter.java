package com.cursoandroid.whatsappclone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cursoandroid.whatsappclone.R;
import com.cursoandroid.whatsappclone.data.Contato;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class ContatoAdapter extends ArrayAdapter<Contato> {

    private Context context;
    private ArrayList<Contato> contatos;
    private TextView nomeContato;
    private TextView emailContato;

    public ContatoAdapter(@NonNull Context context, @NonNull ArrayList<Contato> objects) {
        super(context, 0, objects);

        this.context = context;

        this.contatos = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        //check contatos data.
        if (contatos != null){
            //Setting up view inflater
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //set view
            view = layoutInflater.inflate(R.layout.list_contatos, parent, false);

            /* Get UI reference  */
            nomeContato = (TextView) view.findViewById(R.id.tv_nome);
            emailContato = (TextView) view.findViewById(R.id.tv_email);

            /* Recovery data and set UI */
            nomeContato.setText(contatos.get(position).getNome());
            emailContato.setText(contatos.get(position).getEmail());

        }

        return view;
    }
}
