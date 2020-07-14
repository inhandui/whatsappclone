package com.cursoandroid.whatsappclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cursoandroid.whatsappclone.R;
import com.google.android.material.appbar.MaterialToolbar;

public class ConversaActivity extends AppCompatActivity {

    private MaterialToolbar appToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        /* Adding layout elements reference */
        appToolbar = findViewById(R.id.tb_conversa);

        Bundle extra = getIntent().getExtras();

        if (extra != null){
            //set toolbar title
            appToolbar.setTitle(extra.getString("nome"));
        }
        else {
            appToolbar.setTitle("Undefined");
        }


        setSupportActionBar(appToolbar);

    }
}
