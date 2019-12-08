package com.cursoandroid.whatsappclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.whatsappclone.R;
import com.cursoandroid.whatsappclone.data.Usuario;
import com.cursoandroid.whatsappclone.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.Random;

public class CadastroMainActivity extends AppCompatActivity {

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
    private static final String localPhone = "5554";
    private SmsManager smsManager;
    private String msgToken = "";
    private static final int REQUEST_PERMISSION_SEND_SMS = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastromain);

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

                preferencias = new Preferencias(CadastroMainActivity.this);
                preferencias.salvarUsuarioReferencias(usuario, token);

                /* Perform send SMS */
                int permitionCheck = ContextCompat.checkSelfPermission(CadastroMainActivity.this, Manifest.permission.SEND_SMS);
                if (permitionCheck == PackageManager.PERMISSION_GRANTED){
                    //****WARNING***** Change localPhone to numeroCompleto before release to production (onRequestPermissionsResults too)
                    enviarSMS(localPhone, token);
                }
                else {
                    //try again
                    ActivityCompat.requestPermissions(CadastroMainActivity.this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_PERMISSION_SEND_SMS);
                    Toast.makeText(CadastroMainActivity.this, R.string.erro_permissao, Toast.LENGTH_SHORT).show();
                }



//                HashMap<String, String> usuario = preferencias.getDadosUsuario();
//                Toast.makeText(CadastroMainActivity.this, "usuario: " + usuario.get("token") + "- nome: " + usuario.get("nome") + " tel: " + usuario.get("telefone"),  Toast.LENGTH_LONG).show();
            }
        });

    }

    private void enviarSMS(String telefone, String token) {
        msgToken = getString(R.string.mensagem_token) + " " + token;
        smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(telefone, null,  msgToken, null, null);
        validarToken();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_PERMISSION_SEND_SMS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //****WARNING***** Change localPhone to numeroCompleto before release to production
                    enviarSMS(localPhone, token);
                } else {
                    Toast.makeText(CadastroMainActivity.this, R.string.erro_permissao, Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    private void validarToken(){
        Toast.makeText(CadastroMainActivity.this, R.string.envio_ok, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(CadastroMainActivity.this, ValidadorActivity.class);
        startActivity(intent);
        finish();
    }
}
