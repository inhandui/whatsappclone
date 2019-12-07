package com.cursoandroid.whatsappclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cursoandroid.whatsappclone.R;

public class ValidadorActivity extends AppCompatActivity {

    private EditText txtCodigo;
    private Button btnValidar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        txtCodigo = findViewById(R.id.txtCodigo);
        btnValidar = findViewById(R.id.btnValidar);

        btnValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
