package com.alejo_zr.exceldb.Segmento.Rigido;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alejo_zr.exceldb.BaseDatos;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.entidades.SegmentoRigi;
import com.alejo_zr.exceldb.utilidades.Utilidades;

import java.util.ArrayList;

/**
 * Created by Alejo on 23/10/2017.
 */

public class ConsultarSegmentoRigiActivity extends AppCompatActivity {

    private ListView listViewSegmentos;
    private ArrayList<String> listaInformacionSegmentos;
    private ArrayList<SegmentoRigi> listaSegmentos;
    private ArrayList<Integer> listaIdSegmentos;
    private TextView tvnomCarretera_consultar_segmentoRigi;

    private BaseDatos baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_segmento_rigi);

        baseDatos=new BaseDatos(this);

        listViewSegmentos = (ListView) findViewById(R.id.listViewSegmentoRigi);
        tvnomCarretera_consultar_segmentoRigi = (TextView) findViewById(R.id.tvnomCarretera_consultar_segmentoRigi);

        Bundle bundle = getIntent().getExtras();
        String dato_id = bundle.getString("id_carretera").toString();
        String dato_nom = bundle.getString("nom_carretera").toString();
        tvnomCarretera_consultar_segmentoRigi.setText(dato_nom);

        consultarListaSegmentos();

        ArrayAdapter adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacionSegmentos);
        listViewSegmentos.setAdapter(adaptador);

        listViewSegmentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posS, long l) {

                SegmentoRigi segmentorigi=listaSegmentos.get(listaIdSegmentos.get(posS));
                Intent intent=new Intent(ConsultarSegmentoRigiActivity.this,SegmentoRigiActivity.class);

                Bundle bundle=new Bundle();
                bundle.putSerializable("segmento",segmentorigi);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
    protected void onStart() {
        super.onStart();
        baseDatos=new BaseDatos(this);

        listViewSegmentos = (ListView) findViewById(R.id.listViewSegmentoRigi);
        tvnomCarretera_consultar_segmentoRigi = (TextView) findViewById(R.id.tvnomCarretera_consultar_segmentoRigi);

        Bundle bundle = getIntent().getExtras();
        String dato_id = bundle.getString("id_carretera").toString();
        String dato_nom = bundle.getString("nom_carretera").toString();
        tvnomCarretera_consultar_segmentoRigi.setText(dato_nom);

        consultarListaSegmentos();

        ArrayAdapter adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacionSegmentos);
        listViewSegmentos.setAdapter(adaptador);

        listViewSegmentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posS, long l) {

                SegmentoRigi segmentorigi=listaSegmentos.get(listaIdSegmentos.get(posS));
                Intent intent=new Intent(ConsultarSegmentoRigiActivity.this,SegmentoRigiActivity.class);

                Bundle bundle=new Bundle();
                bundle.putSerializable("segmento",segmentorigi);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

    }



    private void consultarListaSegmentos() {

        SQLiteDatabase db=baseDatos.getReadableDatabase();

        SegmentoRigi segmento=null;
        listaSegmentos= new ArrayList<SegmentoRigi>();
        //select * from carretera
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.SEGMENTORIGI.TABLA_SEGMENTO,null);
        while(cursor.moveToNext()){
            segmento = new SegmentoRigi();
            segmento.setId_segmento(cursor.getInt(0));
            segmento.setNombre_carretera(cursor.getString(1));
            segmento.setnCalzadas(cursor.getString(2));
            segmento.setnCarriles(cursor.getString(3));
            segmento.setEspesorLosa(cursor.getString(4));
            segmento.setAnchoBerma(cursor.getString(5));
            segmento.setPri(cursor.getString(6));
            segmento.setPrf(cursor.getString(7));
            segmento.setComentarios(cursor.getString(8));

            listaSegmentos.add(segmento);
        }

        obtenerLista();
    }

    private void obtenerLista() {

        listaInformacionSegmentos = new ArrayList<String>();
        listaIdSegmentos = new ArrayList<Integer>();

        for (int i=0; i<listaSegmentos.size();i++){
            boolean nomCarretera = tvnomCarretera_consultar_segmentoRigi.getText().toString().equals(listaSegmentos.get(i).getNombre_carretera());
            if(nomCarretera==true){
                listaInformacionSegmentos.add("id"+listaSegmentos.get(i).getId_segmento()+"Carretera: "+listaSegmentos.get(i).getNombre_carretera()+" - PRI: "+listaSegmentos.get(i).getPri());
                listaIdSegmentos.add(listaSegmentos.get(i).getId_segmento()-1);
            }

        }
    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.floabtnAddSegRigi:
                Intent intent = new Intent(ConsultarSegmentoRigiActivity.this,RegistroSegmentoRigiActivity.class);
                intent.putExtra("nom_carretera",tvnomCarretera_consultar_segmentoRigi.getText().toString());
                startActivity(intent);
                break;
        }


    }
}
