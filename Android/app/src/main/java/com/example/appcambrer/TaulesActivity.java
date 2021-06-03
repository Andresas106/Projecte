package com.example.appcambrer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import inofmila.info.Model.Cambrer;
import inofmila.info.Model.Taula;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TaulesActivity extends AppCompatActivity implements SelectedItemListener {


    public static final String CODI = "Codi";
    public static final String NOM = "Nom";
    public static final String COGNOM1 = "Cognom1";
    public static final String COGNOM2 = "Cognom2";
    public static final String USER = "User";
    public static final String PASSWD = "Passw";

    Socket s = null;
    RecyclerView rcvTaules;
    TaulesAdapter adapter;
    public static long sesion_id;
    List<Taula> taules = new ArrayList<>();
    Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taules);

        rcvTaules = findViewById(R.id.rcvTaules);

        Intent i = getIntent();
        sesion_id = i.getLongExtra(CODI, 0);
        s = LoginActivity.s;
        //Cambrer c = new Cambrer(codi, nom, cognom1, cognom2, user, passwd);

        Observable.fromCallable(() -> {
            //---------------- START OF THREAD ------------------------------------
            // Això és el codi que s'executarà en un fil
            int opcio = 2;
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeInt(opcio);

            Thread.sleep(100); 
            dos = new DataOutputStream(s.getOutputStream());
            dos.writeLong(sesion_id);

            DataInputStream dis = new DataInputStream(s.getInputStream());
            int numTaules = dis.readInt();

            for(int j=0;j< numTaules;j++)
            {
                //rebem numero de taula
                dis = new DataInputStream(s.getInputStream());
                int numero = dis.readInt();

                dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(0);

                //rebem codi de comanda
                dis = new DataInputStream(s.getInputStream());
                long codi = dis.readLong();

                dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(0);

                //rebem nom de cambrer
                dis = new DataInputStream(s.getInputStream());
                String nom = dis.readUTF();

                dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(0);

                //Rebem numPlats
                dis = new DataInputStream(s.getInputStream());
                int numPlats = dis.readInt();

                dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(0);

                //rebem num platsPreparats
                dis = new DataInputStream(s.getInputStream());
                int platsPreparats = dis.readInt();

                dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(0);

                //rebem platsPendents
                dis = new DataInputStream(s.getInputStream());
                int platsPendents = dis.readInt();

                dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(0);

                //rebem es_meva
                dis = new DataInputStream(s.getInputStream());
                boolean es_meva = dis.readBoolean();

                Taula t = new Taula(numero, codi, nom, numPlats, platsPreparats, platsPendents, es_meva);
                taules.add(t);
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
                    rcvTaules.setLayoutManager(new GridLayoutManager(this, 3));
                    adapter = new TaulesAdapter(taules, this);
                    rcvTaules.setAdapter(adapter);
                    //-------------  END OF UI THREAD ---------------------------------------
                });


    }

    @Override
    public void onSelectedItem(Taula t) {
        Intent i = new Intent(this, ComandaActivity.class);
        i.putExtra(ComandaActivity.CODI, t.getCodi());
        startActivity(i);
    }

    @Override
    public void onLongSelectedItem(Taula t, int idxSeleccionat) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Segur que vols buidar aquesta taula?");
        builder.setTitle("Buidar Taula");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Observable.fromCallable(() -> {
                    //---------------- START OF THREAD ------------------------------------
                    // Això és el codi que s'executarà en un fil
                   int opcio = 6;

                   DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                   dos.writeInt(opcio);

                   Thread.sleep(100);

                   dos = new DataOutputStream(s.getOutputStream());
                   dos.writeLong(sesion_id);

                   Thread.sleep(100);

                   dos = new DataOutputStream(s.getOutputStream());
                   Log.i("KLK", "" + t.getNumero());
                   dos.writeInt(t.getNumero());

                   dos = new DataOutputStream(s.getOutputStream());
                   dos.writeLong(t.getCodi());

                   DataInputStream dis = new DataInputStream(s.getInputStream());
                   dis.readInt();

                   taules.get(idxSeleccionat).setCodi(-1);



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
                            adapter.notifyItemChanged(idxSeleccionat);
                            //-------------  END OF UI THREAD ---------------------------------------
                        });
            }
        });
        builder.setNegativeButton("CANCEL", null);
        builder.setCancelable(true);
        builder.create().show();
    }
}