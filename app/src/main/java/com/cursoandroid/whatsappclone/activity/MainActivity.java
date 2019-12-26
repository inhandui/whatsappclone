package com.cursoandroid.whatsappclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.cursoandroid.whatsappclone.R;
import com.cursoandroid.whatsappclone.data.ConfiguracaoFirebase;
import com.cursoandroid.whatsappclone.ui.SlidingTabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAuth firebaseAuth;

    private SlidingTabLayout stl_abas;
    private ViewPager vp_pagina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();

        stl_abas = findViewById(R.id.stl_abas);
        vp_pagina = findViewById(R.id.vp_pagina);
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
            case R.id.sair:
                deslogarUsuario();
                return true;
            case R.id.pesquisar:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    public void deslogarUsuario(){
        firebaseAuth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
