package com.cursoandroid.whatsappclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.whatsappclone.R;
import com.cursoandroid.whatsappclone.data.Usuario;
import com.cursoandroid.whatsappclone.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;
import java.lang.Integer;

public class MainActivity extends AppCompatActivity {

//    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private EditText txtNome;
    private EditText codPais;
    private EditText codArea;
    private EditText telefone;
    private Button btnCadastrar;
    private Usuario usuario;
    private String numeroCompleto;
    private Random random;
    private int numeroRandom;
    private String token;
    private Preferencias preferencias;
    private int localPhone = 5554;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        databaseReference.child("Pontos").setValue(100);

        /* Get screen elements reference */
        txtNome = findViewById(R.id.txtNome);
        codPais = findViewById(R.id.nPais);
        codArea = findViewById(R.id.nEstado);
        telefone = findViewById(R.id.nNumero);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        /* Setting up masks */
        SimpleMaskFormatter maskPais = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter maskArea = new SimpleMaskFormatter("NN");
        SimpleMaskFormatter maskTelefone = new SimpleMaskFormatter("NNNN-NNNN");

        MaskTextWatcher maskWatcherPais = new MaskTextWatcher(codPais, maskPais);
        MaskTextWatcher maskWatcherArea = new MaskTextWatcher(codArea, maskArea);
        MaskTextWatcher maskWatcherTelefone = new MaskTextWatcher(telefone, maskTelefone);

        codPais.addTextChangedListener(maskWatcherPais);
        codArea.addTextChangedListener(maskWatcherArea);
        telefone.addTextChangedListener(maskWatcherTelefone);

        /* Setting up button click listener */

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = new Usuario();
                usuario.setNome(txtNome.getText().toString());
                numeroCompleto = codPais.getText().toString() + codArea.getText().toString() + telefone.getText().toString();
                numeroCompleto = numeroCompleto.replace("+", "");
                numeroCompleto = numeroCompleto.replace("-", "");
                usuario.setTelefoneCompleto(numeroCompleto);

                random = new Random();
                numeroRandom = random.nextInt( 9999 - 1000) + 1000;
                token = String.valueOf(numeroRandom);

                preferencias = new Preferencias(MainActivity.this);
                preferencias.salvarUsuarioReferencias(usuario, token);

                HashMap<String, String> usuario = preferencias.getDadosUsuario();
                Toast.makeText(MainActivity.this, "usuario: " + usuario.get("token") + "- nome: " + usuario.get("nome") + " tel: " + usuario.get("telefone"),  Toast.LENGTH_LONG).show();
            }
        });

    }
}
