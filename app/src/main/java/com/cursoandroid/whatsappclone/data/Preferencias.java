package com.cursoandroid.whatsappclone.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.cursoandroid.whatsappclone.data.Usuario;

import java.util.HashMap;

/* Class to save user preferences  */
public class Preferencias {
    private Context context;
    private SharedPreferences preferencias;
    private final String NOME_ARQUIVO = "Whatsappclone.preferencias";
    private int MODE = 0;
    private SharedPreferences.Editor editor;
    private final String CHAVE_ID = "idUsuarioLogado";
    private final String CHAVE_NOME = "nome";
    private final String CHAVE_TELEFONE = "telefone";
    private final String CHAVE_TOKEN = "token";

    /* Class constructor */
    public Preferencias(Context context){
        this.context = context; //get context
        preferencias = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferencias.edit();
    }

    /* Save loging data from user*/
    public void salvarDadosUsuario(String idUsuario){
        editor.putString(CHAVE_ID, idUsuario);
        editor.commit();
    }

    public String getidUsuario(){
        return preferencias.getString(CHAVE_ID, null);
    }

    /* Save user preferences */
    public void salvarUsuarioPreferencias(Usuario usuario, String token){
        editor.putString(CHAVE_NOME, usuario.getNome());
        editor.putString(CHAVE_TELEFONE, usuario.getTelefoneCompleto());
        editor.putString(CHAVE_TOKEN, token);
        editor.commit();
    }

    /* get user data from shared preferences */
    public HashMap<String, String> getDadosUsuario(){
        HashMap<String, String> dadosUsuarios = new HashMap<>();

        dadosUsuarios.put(CHAVE_NOME, preferencias.getString(CHAVE_NOME, null));
        dadosUsuarios.put(CHAVE_TELEFONE, preferencias.getString(CHAVE_TELEFONE, null));
        dadosUsuarios.put(CHAVE_TOKEN, preferencias.getString(CHAVE_TOKEN, null));

        return dadosUsuarios;
    }
}
