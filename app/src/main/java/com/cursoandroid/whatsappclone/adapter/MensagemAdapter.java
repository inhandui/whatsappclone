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
import com.cursoandroid.whatsappclone.data.Mensagem;
import com.cursoandroid.whatsappclone.data.Preferencias;

import java.util.ArrayList;


public class MensagemAdapter extends ArrayAdapter<Mensagem> {

    private Context context;
    private ArrayList<Mensagem> mensagens;

    /* UI elements */
    private TextView tv_mensagem;
    private Mensagem mensagem;

    /* User data */
    private Preferencias preferencias;
    private String idRemetente;

    public MensagemAdapter(@NonNull Context ctx, @NonNull ArrayList<Mensagem> objects) {
        super(ctx, 0, objects);
        this.context = ctx;
        this.mensagens = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        //Verify null data
        if (mensagens != null){
            /* Retrieve user data */
            preferencias = new Preferencias(context);
            idRemetente = preferencias.getidUsuario();

            //retrieve message from firebase
            mensagem = mensagens.get(position);

            //setup layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //mount layout via xml
            if (idRemetente.equals(mensagem.getId())){
                view = inflater.inflate(R.layout.item_mensagem_direita, parent, false);
            }
            else{
                view = inflater.inflate(R.layout.item_mensagem_esquerda, parent, false);
            }


            //Get Ui element reference
            tv_mensagem = view.findViewById(R.id.tv_mensagem);
            tv_mensagem.setText(mensagem.getMensagem());
        }

        return view;
    }
}
