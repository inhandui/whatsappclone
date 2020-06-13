package com.cursoandroid.whatsappclone.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cursoandroid.whatsappclone.R;
import com.cursoandroid.whatsappclone.adapter.PageAdapter;
import com.cursoandroid.whatsappclone.data.Base64Custom;
import com.cursoandroid.whatsappclone.data.ConfiguracaoFirebase;
import com.cursoandroid.whatsappclone.data.Contato;
import com.cursoandroid.whatsappclone.data.Preferencias;
import com.cursoandroid.whatsappclone.data.Usuario;
import com.cursoandroid.whatsappclone.fragment.ContatosFragment;
import com.cursoandroid.whatsappclone.fragment.ConversasFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout page_layout;
//    private AppBarLayout appBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PageAdapter pageAdapter;
    private FirebaseAuth firebaseAuth;

    private String idContato;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Adding layout elements reference */
        page_layout = findViewById(R.id.page_layout);
//        appBar = page_layout.findViewById(R.id.appbar);
        tabLayout = page_layout.findViewById(R.id.tablayout);
        viewPager = page_layout.findViewById(R.id.viewpager);

        /* Setting up Page Adapter */
        pageAdapter = new PageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pageAdapter.addFragment(new ConversasFragment(), getResources().getText(R.string.conversas_fragment).toString());
        pageAdapter.addFragment(new ContatosFragment(), getResources().getText(R.string.contatos_fragment).toString());

        /* Setting up View Pager */
        viewPager.setAdapter(pageAdapter);

        //Setting up Tab Layout
        tabLayout.setupWithViewPager(viewPager);

        //Get firebase authentincation
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.pesquisar:
                return true;
            case R.id.adicionar:
                abrirCadastroContato();
                return true;
            case R.id.sair:
                deslogarUsuario();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void abrirCadastroContato(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        /* Setting up alert dialog */
        //text part
        alertDialog.setTitle(R.string.alertdialog_main_adicionarcontato_titulo);
        alertDialog.setMessage(R.string.alertdialog_main_adicionarcontato_mensagem);
        alertDialog.setCancelable(false);
        final EditText textEmail = new EditText(MainActivity.this);
//        TextInputEditText textEmail = new TextInputEditText(MainActivity.this);
        alertDialog.setView(textEmail);


        //buttons part
        alertDialog.setPositiveButton(R.string.alertdialog_main_adicionarcontato_btn_positivo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String emailContato = textEmail.getText().toString();

                //Check if user fill the email input
                if (emailContato.isEmpty()){
                    Toast.makeText(MainActivity.this, R.string.erro_alertdialog_main_adicionarcontato_emailvazio,Toast.LENGTH_LONG).show();
                }
                else {
                    //Check if the email is already registered
                    idContato = Base64Custom.codificarBase64(emailContato);

                    //Retrieve firebase instance
                    databaseReference = ConfiguracaoFirebase.getFirebase().child("usuarios").child(idContato);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                //get data from data snapshot (from contato id)
                                Contato contato = dataSnapshot.getValue(Contato.class);

                                //Retrieve user id
                                Preferencias preferencias = new Preferencias(MainActivity.this);
                                String idUsuario = preferencias.getidUsuario();

                                //Setup database reference for the insert query
                                databaseReference = ConfiguracaoFirebase.getFirebase()
                                                                        .child("contatos")
                                                                        .child(idUsuario)
                                                                        .child(idContato);

                                //setting contato id
                                if (contato.getId() == null){
                                    contato.setId(idContato);
                                }
                                //save data from contato object
                                databaseReference.setValue(contato);

                            }
                            else {
                                Toast.makeText(MainActivity.this, R.string.erro_alertdialog_main_adicionarcontato_emailnaocadastrado, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        alertDialog.setNegativeButton(R.string.alertdialog_main_adicionarcontato_btn_negativo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        //create and show alert dialog
        alertDialog.create();
        alertDialog.show();
    }

    private void deslogarUsuario(){
        firebaseAuth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
