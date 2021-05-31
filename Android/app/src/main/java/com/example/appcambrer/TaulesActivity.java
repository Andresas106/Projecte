package com.example.appcambrer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import inofmila.info.Model.Cambrer;

public class TaulesActivity extends AppCompatActivity {


    public static final String CODI = "Codi";
    public static final String NOM = "Nom";
    public static final String COGNOM1 = "Cognom1";
    public static final String COGNOM2 = "Cognom2";
    public static final String USER = "User";
    public static final String PASSWD = "Passw";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taules);

        Intent i = getIntent();
        long codi = i.getLongExtra(CODI, 0);
        String nom = i.getStringExtra(NOM);
        String cognom1 = i.getStringExtra(COGNOM1);
        String cognom2 = i.getStringExtra(COGNOM2);
        String user = i.getStringExtra(USER);
        String passwd = i.getStringExtra(PASSWD);

        Cambrer c = new Cambrer(codi, nom, cognom1, cognom2, user, passwd);
    }
}