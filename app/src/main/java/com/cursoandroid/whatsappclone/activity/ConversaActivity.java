package com.cursoandroid.whatsappclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cursoandroid.whatsappclone.R;
import com.cursoandroid.whatsappclone.data.Base64Custom;
import com.cursoandroid.whatsappclone.data.ConfiguracaoFirebase;
import com.cursoandroid.whatsappclone.data.Mensagem;
import com.cursoandroid.whatsappclone.data.Preferencias;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DatabaseReference;

public class ConversaActivity extends AppCompatActivity {

    /* Interface UI*/
    private MaterialToolbar appToolbar;
    private EditText txt_mensagem;
    private ImageButton btn_enviar;
    private String texto_mensagem;

    //Data
    private Mensagem mensagem;

    //User data
    private Preferencias preferencias;
    private String idRemetente;

    //Receiver user data
    private String nomeDestinatario;
    private String idDestinatario;
    private String emailDestinatario;

    //Firebase reference
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        /* Adding layout elements reference */
        appToolbar = findViewById(R.id.tb_conversa);
        txt_mensagem = findViewById(R.id.mensagem);
        btn_enviar = findViewById(R.id.btn_enviar);

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

        //setup event for the button.
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
                    salvarMensagem(idDestinatario, mensagem);
                }
            }
        });

    }

    private boolean salvarMensagem(String idDestinatario, Mensagem mensagem){
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
}
