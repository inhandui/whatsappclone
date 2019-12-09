package com.cursoandroid.whatsappclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText txtNome;
    private EditText txtEmail;
    private EditText txtSenha;
    private Button btnCadastrar;

    private Usuario usuario;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        txtNome = findViewById(R.id.txtNome);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Get user data from activity */
                usuario = new Usuario();
                usuario.setNome(txtNome.getText().toString());
                usuario.setEmail(txtEmail.getText().toString());
                usuario.setSenha(txtSenha.getText().toString());

                cadastrarUsuario(); //call function to save user to firebase
            }
        });
    }

    /* Function to create and save user data to firebase and get user id from firebase  */
    private void cadastrarUsuario(){
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth(); //get firebase auth reference
        firebaseAuth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText(CadastroUsuarioActivity.this, R.string.cadastroUsuario_ok, Toast.LENGTH_SHORT).show();

                    firebaseUser = task.getResult().getUser();
                    usuario.setId(firebaseUser.getUid()); //get user id from firebase
                    usuario.salvar(); //save user data to firebase

                    firebaseAuth.signOut();
                    finish();
                }
                else {
                    /* Show user friendly error messages related to user signup errors */
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e){
                        Toast.makeText(CadastroUsuarioActivity.this, R.string.erro_cadastroUsuario_senhaFraca, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();

                    } catch (FirebaseAuthInvalidCredentialsException e){
                        Toast.makeText(CadastroUsuarioActivity.this, R.string.erro_cadastroUsuario_emailMalFormado, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();

                    } catch (FirebaseAuthUserCollisionException e){
                        Toast.makeText(CadastroUsuarioActivity.this, R.string.erro_cadastroUsuario_emailJaCadastrado, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();

                    } catch (Exception e){
                        Toast.makeText(CadastroUsuarioActivity.this, R.string.erro_cadastroUsuario , Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
