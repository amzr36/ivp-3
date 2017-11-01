package com.alejo_zr.exceldb.Segmento.Rigido;

import android.content.ContentValues;
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
import android.widget.Toast;

import com.alejo_zr.exceldb.BaseDatos;
import com.alejo_zr.exceldb.Carretera.CarreteraActivity;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.entidades.PatoRigi;
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
    private ArrayList<PatoRigi> listaPatologiasRigi;
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
        cargarPatoRigi();
    }
    protected void onStart() {
        super.onStart();
        baseDatos=new BaseDatos(this);

        listViewSegmentos = (ListView) findViewById(R.id.listViewSegmentoRigi);
        tvnomCarretera_consultar_segmentoRigi = (TextView) findViewById(R.id.tvnomCarretera_consultar_segmentoRigi);

        Bundle bundle = getIntent().getExtras();
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
        cargarPatoRigi();
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
            segmento.setFecha(cursor.getString(9));
            segmento.setIs(cursor.getString(10));

            listaSegmentos.add(segmento);
        }
        editarIdSegmento();
        obtenerLista();
    }

    private void obtenerLista() {

        listaInformacionSegmentos = new ArrayList<String>();
        listaIdSegmentos = new ArrayList<Integer>();

        for (int i=0; i<listaSegmentos.size();i++){
            boolean nomCarretera = tvnomCarretera_consultar_segmentoRigi.getText().toString().equals(listaSegmentos.get(i).getNombre_carretera());
            if(nomCarretera==true){
                listaInformacionSegmentos.add("Segmento: "+listaSegmentos.get(i).getId_segmento()+" - PRI: "+listaSegmentos.get(i).getPri());
                listaIdSegmentos.add(listaSegmentos.get(i).getId_segmento()-1);
            }

        }
    }

    private void editarIdSegmento() {

        SQLiteDatabase dbSR = baseDatos.getWritableDatabase();

        int id = 1;
        for (int i = 0; i < listaSegmentos.size(); i++) {

            double idCarretera = listaSegmentos.get(i).getId_segmento();
            double modulo = idCarretera / id;
            String idS = new String("" + idCarretera);
            if (modulo != 1) {
                ContentValues values = new ContentValues();
                Toast.makeText(getApplicationContext(), "id" + id + "idS" + idCarretera + "M" + modulo, Toast.LENGTH_SHORT).show();
                String[] parametros = {idS};
                String carreteraId;
                carreteraId = ("" + id);
                values.put(Utilidades.SEGMENTORIGI.CAMPO_ID_SEGMENTO, carreteraId);
                dbSR.update(Utilidades.SEGMENTORIGI.TABLA_SEGMENTO, values, Utilidades.SEGMENTORIGI.CAMPO_ID_SEGMENTO + "=?", parametros);
            }
            id = id + 1;

        }

        dbSR.close();
    }
    private void cargarPatoRigi() {
        SQLiteDatabase db=baseDatos.getReadableDatabase();

        PatoRigi patoRigi=null;
        listaPatologiasRigi= new ArrayList<PatoRigi>();
        //select * from carretera
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.PATOLOGIARIGI.TABLA_PATOLOGIA,null);

        while(cursor.moveToNext()){
            patoRigi = new PatoRigi();
            patoRigi.setId_patoRigi(cursor.getInt(0));
            patoRigi.setId_segmento_patoRigi(cursor.getString(1));
            patoRigi.setNombre_carretera_patoRigi(cursor.getString(2));
            patoRigi.setAbscisa(cursor.getString(3));
            patoRigi.setLatitud(cursor.getString(4));
            patoRigi.setLongitud(cursor.getString(5));
            patoRigi.setNo_placa(cursor.getString(6));
            patoRigi.setLetra(cursor.getString(7));
            patoRigi.setLargoLoza(cursor.getString(8));
            patoRigi.setAnchoLoza(cursor.getString(9));
            patoRigi.setDanio(cursor.getString(10));
            patoRigi.setSeveridad(cursor.getString(11));
            patoRigi.setLargoDanio(cursor.getString(12));
            patoRigi.setAnchoDanio(cursor.getString(13));
            patoRigi.setLargoRepa(cursor.getString(14));
            patoRigi.setAnchoRepa(cursor.getString(15));
            patoRigi.setAclaraciones(cursor.getString(16));
            patoRigi.setNombreFoto(cursor.getString(17));
            patoRigi.setFoto(cursor.getString(18));
            patoRigi.setIs(cursor.getString(19));

            listaPatologiasRigi.add(patoRigi);
        }
        editarIdPatoRigi();
    }

    private void editarIdPatoRigi() {
        SQLiteDatabase dbPR = baseDatos.getWritableDatabase();
        int id = 1;
        for (int i = 0; i < listaPatologiasRigi.size(); i++) {
            double idSegRigi = listaPatologiasRigi.get(i).getId_patoRigi();
            double modulo = idSegRigi / id;
            String idS = new String("" + idSegRigi);
            String iS= new String(""+listaPatologiasRigi.get(i).getIs());
            for(int l =0;l<listaSegmentos.size();l++)
            {
                boolean isegmento = iS.equals(listaSegmentos.get(l).getIs());
                String idSegmento = new String(""+listaSegmentos.get(l).getId_segmento());
                if(isegmento==true){
                    if (modulo != 1) {
                        ContentValues values = new ContentValues();
                        String[] parametros = {idS};
                        String patorigiId = (""+id);
                        values.put(Utilidades.PATOLOGIARIGI.CAMPO_ID_PATOLOGIA, patorigiId);
                        values.put(Utilidades.PATOLOGIARIGI.CAMPO_ID_SEGMENTO_PATOLOGIA,idSegmento);
                        dbPR.update(Utilidades.PATOLOGIARIGI.TABLA_PATOLOGIA, values, Utilidades.PATOLOGIARIGI.CAMPO_ID_PATOLOGIA + "=?", parametros);
                    }
                }
            }

            id = id + 1;

        }

        dbPR.close();
    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.floabtnAddSegRigi:
                Intent intent = new Intent(ConsultarSegmentoRigiActivity.this,RegistroSegmentoRigiActivity.class);
                intent.putExtra("nom_carretera",tvnomCarretera_consultar_segmentoRigi.getText().toString());
                startActivity(intent);
                break;
            case R.id.backConsulSegRigiActivity:
                intent = new Intent(ConsultarSegmentoRigiActivity.this,CarreteraActivity.class);
                intent.putExtra("nom_carretera",tvnomCarretera_consultar_segmentoRigi.getText().toString());
                startActivity(intent);
                break;
        }


    }
}
