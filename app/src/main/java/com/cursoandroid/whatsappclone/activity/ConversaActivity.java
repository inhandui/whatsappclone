package com.cursoandroid.whatsappclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.cursoandroid.whatsappclone.R;
import com.cursoandroid.whatsappclone.adapter.MensagemAdapter;
import com.cursoandroid.whatsappclone.data.Base64Custom;
import com.cursoandroid.whatsappclone.data.ConfiguracaoFirebase;
import com.cursoandroid.whatsappclone.data.Mensagem;
import com.cursoandroid.whatsappclone.data.Preferencias;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConversaActivity extends AppCompatActivity {

    /* Interface UI*/
    private MaterialToolbar appToolbar;
    private EditText txt_mensagem;
    private ImageButton btn_enviar;
    private String texto_mensagem;
    private ListView lv_mensagens;

    //Activity Data
    private Mensagem mensagem;
    private ArrayList<Mensagem> mensagens;
    private ArrayAdapter<Mensagem> arrayAdapter;
    private ValueEventListener valueEventListenerMensagem;

    //User data
    private Preferencias preferencias;
    private String idRemetente;

    //Receiver user data
    private String nomeDestinatario;
    private String idDestinatario;
    private String emailDestinatario;
    private boolean retornoMensagemRemetente;
    private boolean retornoMensagemDestinatario;

    //Firebase reference
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        /* Get layout elements reference */
        appToolbar = findViewById(R.id.tb_conversa);
        txt_mensagem = findViewById(R.id.mensagem);
        btn_enviar = findViewById(R.id.btn_enviar);
        lv_mensagens = findViewById(R.id.lv_mensagens);


        /* get user data */
        preferencias = new Preferencias(ConversaActivity.this);
        idRemetente = preferencias.getidUsuario();

        /* Get contato data and set toolbar title*/
        Bundle extra = getIntent().getExtras();

        if (extra != null){
            //set toolbar title
            nomeDestinatario = extra.getString("nome");
            emailDestinatario = extra.getString("email");
            idDestinatario = Base64Custom.codificarBase64(emailDestinatario);
            appToolbar.setTitle(nomeDestinatario);
        }
        else {
            appToolbar.setTitle("Undefined");
        }
        setSupportActionBar(appToolbar);

        /* Set listview messages */
        mensagens = new ArrayList<Mensagem>();
        arrayAdapter = new MensagemAdapter(ConversaActivity.this, mensagens);
//        arrayAdapter = new ArrayAdapter(ConversaActivity.this, android.R.layout.simple_list_item_1);
        lv_mensagens.setAdapter( arrayAdapter );

        //Retrieve messagens from firebase
        databaseReference = ConfiguracaoFirebase.getFirebase().child("mensagens").child(idRemetente).child(idDestinatario);

        //Setup messages event listener
        valueEventListenerMensagem = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clear messages
                mensagens.clear();

                //retrieve messages
                for (DataSnapshot dados: dataSnapshot.getChildren()){
                    mensagem = dados.getValue( Mensagem.class );
                    mensagens.add( mensagem );
                }

                //notify
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //Add value event listener to firebase reference
        databaseReference.addValueEventListener( valueEventListenerMensagem );

        //setup event for the send button.
        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get message
                texto_mensagem = txt_mensagem.getText().toString();

                if (texto_mensagem.isEmpty() ){
                    Toast.makeText(ConversaActivity.this, R.string.erro_mensagem_vazia, Toast.LENGTH_LONG).show();
                }
                else {
                    /* Setting up data message */
                    mensagem = new Mensagem();
                    mensagem.setId(idRemetente);
                    mensagem.setMensagem(texto_mensagem);

                    //Save data message
                    retornoMensagemRemetente = salvarMensagemRemetente(idDestinatario, mensagem);
                    if (!retornoMensagemRemetente){
                        Toast.makeText(ConversaActivity.this, R.string.erro_mensagem_envio, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        retornoMensagemDestinatario = salvarMensagemDestinatario(idDestinatario, mensagem);
                        if (!retornoMensagemDestinatario){
                            Toast.makeText(ConversaActivity.this, R.string.erro_mensagem_envio, Toast.LENGTH_SHORT).show();
                        }
                    }


                    //delete data from message EditText
                    txt_mensagem.setText("");
                }
            }
        });

    }

    private boolean salvarMensagemRemetente(String idDestinatario, Mensagem mensagem){
        boolean retorno = false;
        try {
            databaseReference = ConfiguracaoFirebase.getFirebase().child("mensagens");
            databaseReference.child(mensagem.getId()).child(idDestinatario).push().setValue(mensagem);
            retorno = true;

        }catch (Exception e){
            e.printStackTrace();
        }


        return retorno;
    }

    private boolean salvarMensagemDestinatario(String idDestinatario, Mensagem mensagem){
        boolean retorno = false;
        try {
            databaseReference = ConfiguracaoFirebase.getFirebase().child("mensagens");
            databaseReference.child(idDestinatario).child(mensagem.getId()).push().setValue(mensagem);
            retorno = true;

        }catch (Exception e){
            e.printStackTrace();
        }


        return retorno;
    }

    @Override
    protected void onStop() {
        super.onStop();

        databaseReference.removeEventListener( valueEventListenerMensagem );
    }
}
