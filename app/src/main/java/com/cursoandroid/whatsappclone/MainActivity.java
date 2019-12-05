package com.cursoandroid.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

//    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private EditText txtNome;
    private EditText codPais;
    private EditText codArea;
    private EditText telefone;
    private Button btnCadastrar;

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
        SimpleMaskFormatter maskArea = new SimpleMaskFormatter(" (NN) ");
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

            }
        });

    }
}
