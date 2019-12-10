package com.cursoandroid.whatsappclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.whatsappclone.R;
import com.cursoandroid.whatsappclone.data.ConfiguracaoFirebase;
import com.cursoandroid.whatsappclone.data.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtSenha;
    private Button btnLogar;
    private Usuario usuario;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarLogin();

        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        btnLogar = findViewById(R.id.btnLogar);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* get user data from user input*/
                usuario = new Usuario();
                if (!txtEmail.getText().toString().equals("") &&  !txtSenha.getText().toString().equals("")){
                    usuario.setEmail( txtEmail.getText().toString() );
                    usuario.setSenha( txtSenha.getText().toString() );
                    validarLogin();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Preencha os campos de email e senha", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void verificarLogin(){
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();
        if (firebaseAuth.getCurrentUser() !=  null){
            abrirTelaPrincipal();
        }
    }

    /* Function to validate user login get from user input  */
    private void validarLogin(){
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();
        firebaseAuth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText( LoginActivity.this, R.string.login_ok, Toast.LENGTH_SHORT).show();
                            abrirTelaPrincipal();
                            finish();
                        }
                        else {
                            Toast.makeText( LoginActivity.this, R.string.erro_login, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /* Function to signup user */
    public void abrirCadastroUsuario(View view){
        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);

    }
}
