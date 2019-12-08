package com.cursoandroid.whatsappclone.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.cursoandroid.whatsappclone.data.Usuario;

import java.util.HashMap;

public class Preferencias {
    private Context context;
    private SharedPreferences preferencias;
    private final String NOME_ARQUIVO = "Whatsappclone.preferencias";
    private int MODE = 0;
    private SharedPreferences.Editor editor;
    private final String CHAVE_NOME = "nome";
    private final String CHAVE_TELEFONE = "telefone";
    private final String CHAVE_TOKEN = "token";

    public Preferencias(Context context){
        this.context = context;
        preferencias = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferencias.edit();
    }

    public void salvarUsuarioReferencias(Usuario usuario, String token){
        editor.putString(CHAVE_NOME, usuario.getNome());
        editor.putString(CHAVE_TELEFONE, usuario.getTelefoneCompleto());
        editor.putString(CHAVE_TOKEN, token);
        editor.commit();
    }

    public HashMap<String, String> getDadosUsuario(){
        HashMap<String, String> dadosUsuarios = new HashMap<>();

        dadosUsuarios.put(CHAVE_NOME, preferencias.getString(CHAVE_NOME, null));
        dadosUsuarios.put(CHAVE_TELEFONE, preferencias.getString(CHAVE_TELEFONE, null));
        dadosUsuarios.put(CHAVE_TOKEN, preferencias.getString(CHAVE_TOKEN, null));

        return dadosUsuarios;
    }
}
