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
import com.cursoandroid.whatsappclone.data.Base64Custom;
import com.cursoandroid.whatsappclone.data.ConfiguracaoFirebase;
import com.cursoandroid.whatsappclone.data.Preferencias;
import com.cursoandroid.whatsappclone.data.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    /* Ui elements*/
    private EditText txtEmail;
    private EditText txtSenha;
    private Button btnLogar;

    /* User data */
    private Usuario usuario;
    private String idUsuario;
    private Preferencias preferencias;

    //Firebase reference
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ValueEventListener valueEventListener;

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

                            idUsuario = Base64Custom.codificarBase64(usuario.getEmail());

                            if (usuario.getNome().equals("")){
                                databaseReference = ConfiguracaoFirebase.getFirebase().child("usuarios").child(idUsuario);

                                valueEventListener = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Usuario usuarioRecuperado = dataSnapshot.getValue(Usuario.class);

                                        usuario.setNome(usuarioRecuperado.getNome());

                                        preferencias = new Preferencias(LoginActivity.this);
                                        preferencias.salvarUsuarioPreferencias(usuario);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                };

                                databaseReference.addListenerForSingleValueEvent(valueEventListener);
                            }


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
