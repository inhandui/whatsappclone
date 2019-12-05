package com.cursoandroid.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

//    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private EditText txtNome;
    private EditText codPais;
    private EditText codArea;
    private EditText telefone;

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



//        SimpleMaskFormatter maskNome = new SimpleMaskFormatter()
    }
}
