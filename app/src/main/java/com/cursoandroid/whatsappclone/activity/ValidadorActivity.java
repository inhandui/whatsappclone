package com.cursoandroid.whatsappclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.whatsappclone.R;
import com.cursoandroid.whatsappclone.data.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

public class ValidadorActivity extends AppCompatActivity {

    private EditText txtCodigo;
    private Button btnValidar;

    private SimpleMaskFormatter simpleMaskFormatter;
    private MaskTextWatcher maskTWValidacao;

    private Preferencias preferencias;
    HashMap<String, String> dadosUsuario;

    private String tokenGerado, tokenUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        txtCodigo = findViewById(R.id.txtCodigo);
        btnValidar = findViewById(R.id.btnValidar);

        simpleMaskFormatter = new SimpleMaskFormatter("NNNN");
        maskTWValidacao = new MaskTextWatcher(txtCodigo, simpleMaskFormatter);
        txtCodigo.addTextChangedListener(maskTWValidacao);

        btnValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferencias = new Preferencias(ValidadorActivity.this);
                dadosUsuario = preferencias.getDadosUsuario();

                tokenGerado = dadosUsuario.get("token");
                tokenUsuario = txtCodigo.getText().toString();

                if (tokenUsuario.equals(tokenGerado)){
                    Toast.makeText(ValidadorActivity.this, R.string.token_ok, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ValidadorActivity.this, R.string.erro_token, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
