package com.example.appcambrer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import inofmila.info.Model.Categoria;
import inofmila.info.Model.LiniaComanda;
import inofmila.info.Model.Plat;
import inofmila.info.Model.Taula;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ComandaActivity extends AppCompatActivity implements SelectedItemListenerCategoria {

    public static final String CODI = "codi";

    RecyclerView rcvCategories, rcvPlats, rcvLiniesComanda;
    Socket s = null;
    List<Categoria> categories = new ArrayList<>();
    List<Plat> plats = new ArrayList<>();
    List<LiniaComanda> linies = new ArrayList<>();
    CategoriesAdapter categoryAdapter;
    PlatsAdapter platsAdapter;
    LiniaComandaAdapter liniesAdapter;
    long codiComanda;

    TextView txvTotal;
    Button btnConfirmar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda);

        txvTotal = findViewById(R.id.txvPreuTotal);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        this.s = LoginActivity.s;
        rcvCategories = findViewById(R.id.rcvCategories);
        rcvPlats = findViewById(R.id.rcvPlats);
        rcvLiniesComanda = findViewById(R.id.rcvLiniesComanda);

        Intent intent = getIntent();
        codiComanda = intent.getLongExtra(CODI, 0);

        Observable.fromCallable(() -> {
            //---------------- START OF THREAD ------------------------------------
            // Això és el codi que s'executarà en un fil
            //Agafem categories + plats

            int opcio = 3;
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeInt(opcio);

            Thread.sleep(150);

            dos = new DataOutputStream(s.getOutputStream());
            dos.writeLong(TaulesActivity.sesion_id);

            //categories
            DataInputStream dis = new DataInputStream(s.getInputStream());
            int numCategories = dis.readInt();

            dos = new DataOutputStream(s.getOutputStream());
            dos.writeInt(0);

            for(int i=0;i<numCategories;i++)
            {
                dis = new DataInputStream(s.getInputStream());
                long codi = dis.readLong();

                dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(0);

                dis = new DataInputStream(s.getInputStream());
                String nom = dis.readUTF();

                dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(0);

                Categoria c = new Categoria(codi, nom);
                categories.add(c);
            }
            //Plats

            dis = new DataInputStream(s.getInputStream());
            int numPlats = dis.readInt();

            dos = new DataOutputStream(s.getOutputStream());
            dos.writeInt(0);

            for(int i=0;i<numPlats;i++)
            {
                dis = new DataInputStream(s.getInputStream());
                long codi = dis.readLong();

                dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(0);

                dis = new DataInputStream(s.getInputStream());
                String nom = dis.readUTF();

                dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(0);

                dis = new DataInputStream(s.getInputStream());
                float preu = dis.readFloat();

                dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(0);

                dis = new DataInputStream(s.getInputStream());
                int midaBytesFoto = dis.readInt();

                dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(0);

                dis = new DataInputStream(s.getInputStream());
                byte[] streamFoto = new byte[midaBytesFoto];
                dis.readFully(streamFoto, 0, streamFoto.length);

                dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(0);

                dis = new DataInputStream(s.getInputStream());
                int categoria = dis.readInt();

                dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(0);

                Plat p = new Plat(codi, nom, preu, midaBytesFoto, streamFoto, categoria);

                plats.add(p);
            }

            if(codiComanda == -1)
            {
                //create comanda

            }
            else
            {
                //get comanda
                opcio = 4;
                dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(opcio);

                Thread.sleep(150);

                dos = new DataOutputStream(s.getOutputStream());
                dos.writeLong(TaulesActivity.sesion_id);

                Thread.sleep(150);

                dos = new DataOutputStream(s.getOutputStream());
                dos.writeLong(codiComanda);

                dis = new DataInputStream(s.getInputStream());
                int numLinies = dis.readInt();

                dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(0);

                for(int i=0;i<numLinies;i++)
                {
                    dis = new DataInputStream(s.getInputStream());
                    int numero = dis.readInt();

                    dos = new DataOutputStream(s.getOutputStream());
                    dos.writeInt(0);

                    dis = new DataInputStream(s.getInputStream());
                    int quantitat = dis.readInt();

                    dos = new DataOutputStream(s.getOutputStream());
                    dos.writeInt(0);

                    dis = new DataInputStream(s.getInputStream());
                    long codiPlat = dis.readLong();

                    dos = new DataOutputStream(s.getOutputStream());
                    dos.writeInt(0);

                    dis = new DataInputStream(s.getInputStream());
                    String nomPlat = dis.readUTF();

                    dos = new DataOutputStream(s.getOutputStream());
                    dos.writeInt(0);

                    dis = new DataInputStream(s.getInputStream());
                    float preu = dis.readFloat();

                    dos = new DataOutputStream(s.getOutputStream());
                    dos.writeInt(0);

                    LiniaComanda lc = new LiniaComanda(numero, quantitat, codiComanda, nomPlat, preu);
                    Log.i("LC", lc.toString());
                    linies.add(lc);
                }
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
                    LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                            false);
                    rcvCategories.setLayoutManager(manager);
                    categoryAdapter = new CategoriesAdapter(categories, this);
                    rcvCategories.setAdapter(categoryAdapter);

                    manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    rcvPlats.setLayoutManager(manager);
                    platsAdapter = new PlatsAdapter(plats);
                    rcvPlats.setAdapter(platsAdapter);

                    manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    rcvLiniesComanda.setLayoutManager(manager);
                    liniesAdapter = new LiniaComandaAdapter(linies);
                    rcvLiniesComanda.setAdapter(liniesAdapter);
                    float totalPrice = 0;
                    for(int i=0;i<linies.size();i++)
                    {
                        totalPrice += linies.get(i).getPreu();
                    }

                    txvTotal.setText(String.valueOf(totalPrice));

                    if(codiComanda == -1)
                    {
                        btnConfirmar.setEnabled(true);
                    }
                    else
                    {
                        btnConfirmar.setEnabled(false);
                    }


                    //-------------  END OF UI THREAD ---------------------------------------
                });

    }

    @Override
    public void OnSelectedItem(Categoria c) {
        List<Plat> platsXCategoria = new ArrayList<>();
        for(int i=0;i<plats.size();i++)
        {
            if(plats.get(i).getCategoria() == c.getCodi())
            {
               platsXCategoria.add(plats.get(i));
            }
        }
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvPlats.setLayoutManager(manager);
        platsAdapter = new PlatsAdapter(platsXCategoria);
        rcvPlats.setAdapter(platsAdapter);
    }
}