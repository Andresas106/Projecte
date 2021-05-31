package com.example.appcambrer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;

import inofmila.info.Model.Cambrer;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText edtUsername, edtPassword;
    private Socket s;
    private Context c;
    private int resposta = -1;
    Cambrer cambrer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        c = this;

        btnLogin = findViewById(R.id.btnLogin);
        edtPassword = findViewById(R.id.edtPassword);
        edtUsername = findViewById(R.id.edtUsername);

        Observable.fromCallable(() -> {
            //---------------- START OF THREAD ------------------------------------
            // Això és el codi que s'executarà en un fil

            try {
                s = new Socket("192.168.1.31", 9876);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
            //--------------- END OF THREAD-------------------------------------
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((retornInutil) -> {
                    //-------------  UI THREAD ---------------------------------------
                    // El codi que tenim aquí s'executa només quan el fil
                    // ha acabat !! A més, aquest codi s'executa en el fil
                    // d'interfície gràfica.
                    //-------------  END OF UI THREAD ---------------------------------------
                });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.fromCallable(() -> {
                    //---------------- START OF THREAD ------------------------------------
                    // Això és el codi que s'executarà en un fil
                    int opcio = 1;

                    DataOutputStream dos = null;
                    DataInputStream dis = null;
                    try {
                        dos = new DataOutputStream(s.getOutputStream());
                        dos.writeInt(opcio);
                        Thread.sleep(250);
                        dos = new DataOutputStream(s.getOutputStream());
                        dos.writeUTF(edtUsername.getText().toString());
                        dis = new DataInputStream(s.getInputStream());
                        resposta = dis.readInt();
                        if(resposta == 0)
                        {
                            //Fins aqui tot be
                            dos = new DataOutputStream(s.getOutputStream());
                            dos.writeUTF(edtPassword.getText().toString());
                            dis = new DataInputStream(s.getInputStream());
                            resposta = dis.readInt();
                            if(resposta == 0)
                            {
                                //Seguim be
                                resposta = dis.readInt();
                                if(resposta == 0)
                                {
                                    resposta = dis.readInt();

                                }else if(resposta == 1)
                                {
                                    dis = new DataInputStream(s.getInputStream());
                                    long codi = dis.readLong();

                                    dis = new DataInputStream(s.getInputStream());
                                    String nom = dis.readUTF();

                                    dis = new DataInputStream(s.getInputStream());
                                    String cognom1 = dis.readUTF();

                                    dis = new DataInputStream(s.getInputStream());
                                    String cognom2 = dis.readUTF();

                                    dis = new DataInputStream(s.getInputStream());
                                    String usuari = dis.readUTF();

                                    dis = new DataInputStream(s.getInputStream());
                                    String passw = dis.readUTF();

                                    cambrer = new Cambrer(codi, nom, cognom1, cognom2, usuari, passw);

                                }
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return true;
                    //--------------- END OF THREAD-------------------------------------
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((retornInutil) -> {
                            //-------------  UI THREAD ---------------------------------------
                            // El codi que tenim aquí s'executa només quan el fil
                            // ha acabat !! A més, aquest codi s'executa en el fil
                            // d'interfície gràfica.
                            if(resposta == -1)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                                builder.setMessage("Usuari o contrasenya invalids");
                                builder.setTitle("Error");
                                builder.setPositiveButton("OK", null);
                                builder.setCancelable(true);
                                builder.create().show();
                            }else
                            {
                                Intent i = new Intent(c, TaulesActivity.class);
                                i.putExtra(TaulesActivity.CODI, cambrer.getCodi());
                                i.putExtra(TaulesActivity.NOM, cambrer.getNom());
                                i.putExtra(TaulesActivity.COGNOM1, cambrer.getCognom1());
                                i.putExtra(TaulesActivity.COGNOM2, cambrer.getCognom2());
                                i.putExtra(TaulesActivity.USER, cambrer.getUsuari());
                                i.putExtra(TaulesActivity.PASSWD, cambrer.getPassw());
                                startActivity(i);
                            }

                            //-------------  END OF UI THREAD ---------------------------------------
                        });

            }
        });
    }
/*
*
*
* */
/*
*
*
* */
}