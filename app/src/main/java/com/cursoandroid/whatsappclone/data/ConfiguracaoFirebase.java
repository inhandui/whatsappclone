package com.cursoandroid.whatsappclone.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/* Class to get firebase references  */
public final class ConfiguracaoFirebase {
    private static  DatabaseReference databaseReference;
    private static FirebaseAuth firebaseAuth;

    /* Get data base reference  */
    public static DatabaseReference getFirebase(){
        if (databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }

        return databaseReference;
    }

    /* Get firebase auth reference */
    public static FirebaseAuth getFirebaseAuth(){
        if (firebaseAuth == null){
            firebaseAuth = FirebaseAuth.getInstance();
        }

        return firebaseAuth;
    }
}
