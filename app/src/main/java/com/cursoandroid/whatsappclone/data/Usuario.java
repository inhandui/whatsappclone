package com.cursoandroid.whatsappclone.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;
    private String telefoneCompleto;
    private DatabaseReference databaseReference;

    public Usuario(){

    }

    /* save user data to firebase */
    public void salvar(){
        databaseReference = ConfiguracaoFirebase.getFirebase();
        databaseReference.child("usuarios").child(getId()).setValue(this);
    }

    public Usuario(String nome) {
        this.nome = nome;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefoneCompleto() {
        return telefoneCompleto;
    }

    public void setTelefoneCompleto(String telefoneCompleto) {
        this.telefoneCompleto = telefoneCompleto;
    }
}
