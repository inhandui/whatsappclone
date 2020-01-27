package com.cursoandroid.whatsappclone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cursoandroid.whatsappclone.R;
import com.cursoandroid.whatsappclone.adapter.PageAdapter;
import com.cursoandroid.whatsappclone.data.ConfiguracaoFirebase;
import com.cursoandroid.whatsappclone.fragment.ContatosFragment;
import com.cursoandroid.whatsappclone.fragment.ConversasFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout page_layout;
//    private AppBarLayout appBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PageAdapter pageAdapter;
    private FirebaseAuth firebaseAuth;


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

    }

    public void deslogarUsuario(){
        firebaseAuth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
