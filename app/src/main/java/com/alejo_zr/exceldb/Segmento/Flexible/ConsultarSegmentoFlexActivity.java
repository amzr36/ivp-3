package com.alejo_zr.exceldb.Segmento.Flexible;

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

import com.alejo_zr.exceldb.BaseDatos;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.entidades.PatoFlex;
import com.alejo_zr.exceldb.entidades.SegmentoFlex;
import com.alejo_zr.exceldb.utilidades.Utilidades;

import java.util.ArrayList;

public class ConsultarSegmentoFlexActivity extends AppCompatActivity {

    private ListView listViewSegmentos;
    private ArrayList<String> listaInformacionSegmentos;
    private ArrayList<SegmentoFlex> listaSegmentos;
    private ArrayList<PatoFlex> listaPatologiasFlex;
    private ArrayList<Integer> listaIdSegmentos;
    private TextView tvnomCarretera_consultar_segmentoFlex,idCarreteraSegFlexConsulta;
    private String id,nombre,codigo,territorial,admon,levantado;

    private BaseDatos baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_segmento_flex);

        baseDatos=new BaseDatos(this);

        listViewSegmentos = (ListView) findViewById(R.id.listViewSegmentoFlex);
        tvnomCarretera_consultar_segmentoFlex = (TextView) findViewById(R.id.tvnomCarretera_consultar_segmentoFlex);

        Bundle bundle = getIntent().getExtras();
        /***Datos de la carretera***/
        id = bundle.getString("id_carretera").toString();
        nombre = bundle.getString("nom_carretera").toString();
        codigo = bundle.getString("cod_carretera").toString();
        territorial= bundle.getString("territo").toString();
        admon = bundle.getString("admon").toString();
        levantado = bundle.getString("levantado").toString();

        tvnomCarretera_consultar_segmentoFlex.setText(nombre);


        consultarListaSegmentos();

        ArrayAdapter adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacionSegmentos);
        listViewSegmentos.setAdapter(adaptador);

        listViewSegmentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posS, long l) {


                SegmentoFlex segmentoflex=listaSegmentos.get(listaIdSegmentos.get(posS));
                Intent intent=new Intent(ConsultarSegmentoFlexActivity.this,SegmentoFlexActivity.class);

                Bundle bundle=new Bundle();
                bundle.putSerializable("segmento",segmentoflex);

                intent.putExtras(bundle);

                startActivity(intent);            }
        });
        cargarPatoFlex();
    }

    protected void onStart() {
        super.onStart();

        baseDatos=new BaseDatos(this);

        listViewSegmentos = (ListView) findViewById(R.id.listViewSegmentoFlex);
        tvnomCarretera_consultar_segmentoFlex = (TextView) findViewById(R.id.tvnomCarretera_consultar_segmentoFlex);

        Bundle bundle = getIntent().getExtras();
        String dato_nom = bundle.getString("nom_carretera").toString();
        tvnomCarretera_consultar_segmentoFlex.setText(dato_nom);


        consultarListaSegmentos();

        ArrayAdapter adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacionSegmentos);
        listViewSegmentos.setAdapter(adaptador);

        listViewSegmentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posS, long l) {


                SegmentoFlex segmentoflex=listaSegmentos.get(listaIdSegmentos.get(posS));
                Intent intent=new Intent(ConsultarSegmentoFlexActivity.this,SegmentoFlexActivity.class);

                Bundle bundle=new Bundle();
                bundle.putSerializable("segmento",segmentoflex);

                intent.putExtras(bundle);

                startActivity(intent);            }
        });
        cargarPatoFlex();

    }

    private void consultarListaSegmentos() {

        SQLiteDatabase db=baseDatos.getReadableDatabase();

        SegmentoFlex segmento=null;
        listaSegmentos= new ArrayList<SegmentoFlex>();
        //select * from carretera
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.SEGMENTOFLEX.TABLA_SEGMENTO,null);


        while(cursor.moveToNext()){
            segmento = new SegmentoFlex();

            segmento.setId_segmento(cursor.getInt(0));
            segmento.setNombre_carretera(cursor.getString(1));
            segmento.setnCalzadas(cursor.getString(2));
            segmento.setnCarriles(cursor.getString(3));
            segmento.setAnchoCarril(cursor.getString(4));
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
            boolean nomCarretera = tvnomCarretera_consultar_segmentoFlex.getText().toString().equals(listaSegmentos.get(i).getNombre_carretera());
            if(nomCarretera==true){
                listaInformacionSegmentos.add("Carretera: "+listaSegmentos.get(i).getNombre_carretera()+" - PRI: "+listaSegmentos.get(i).getPri());
                listaIdSegmentos.add(listaSegmentos.get(i).getId_segmento()-1);
            }
        }
    }

    private void editarIdSegmento() {

        SQLiteDatabase dbS = baseDatos.getWritableDatabase();

        int id=1;
        for (int i=0; i<listaSegmentos.size();i++) {
            double idCarretera = listaSegmentos.get(i).getId_segmento();
            double modulo = idCarretera / id;
            String idS = new String("" + idCarretera);
            if (modulo != 1) {
                ContentValues values = new ContentValues();
                String[] parametros={idS};
                String carreteraId;
                carreteraId = ("" + id);
                values.put(Utilidades.SEGMENTOFLEX.CAMPO_ID_SEGMENTO,carreteraId);
                dbS.update(Utilidades.SEGMENTOFLEX.TABLA_SEGMENTO,values,Utilidades.SEGMENTOFLEX.CAMPO_ID_SEGMENTO+"=?",parametros);
            }
            id = id + 1;
        }

        dbS.close();
    }

    private void cargarPatoFlex() {

        SQLiteDatabase db=baseDatos.getReadableDatabase();

        PatoFlex patoFlex=null;
        listaPatologiasFlex= new ArrayList<PatoFlex>();
        //select * from carretera
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.PATOLOGIAFLEX.TABLA_PATOLOGIA,null);

        while(cursor.moveToNext()){
            patoFlex = new PatoFlex();
            patoFlex.setId_patoFlex(cursor.getInt(0));
            patoFlex.setId_segmento_patoFlex(cursor.getInt(1));
            patoFlex.setNombre_carretera_patoFlex(cursor.getString(2));
            patoFlex.setAbscisa(cursor.getString(3));
            patoFlex.setLatitud(cursor.getString(4));
            patoFlex.setLongitud(cursor.getString(5));
            patoFlex.setDanio(cursor.getString(6));
            patoFlex.setCarril(cursor.getString(7));
            patoFlex.setSeveridad(cursor.getString(8));
            patoFlex.setLargoDanio(cursor.getString(9));
            patoFlex.setAnchoDanio(cursor.getString(10));
            patoFlex.setLargoRepa(cursor.getString(11));
            patoFlex.setAnchoRepa(cursor.getString(12));
            patoFlex.setAclaraciones(cursor.getString(13));
            patoFlex.setNombreFoto(cursor.getString(14));
            patoFlex.setFoto(cursor.getString(15));
            patoFlex.setIs(cursor.getString(16));

            listaPatologiasFlex.add(patoFlex);
        }
        editarIdPatoFlex();
    }

    private void editarIdPatoFlex() {

        SQLiteDatabase dbPF = baseDatos.getWritableDatabase();
        int id = 1;
        for (int i = 0; i < listaPatologiasFlex.size(); i++) {
            double idSegFlex = listaPatologiasFlex.get(i).getId_patoFlex();
            double modulo = idSegFlex / id;
            String idS = new String("" + idSegFlex);
            String iS= new String(""+listaPatologiasFlex.get(i).getIs());
            for(int l =0;l<listaSegmentos.size();l++)
            {
                boolean isegmento = iS.equals(listaSegmentos.get(l).getIs());
                String idSegmento = new String(""+listaSegmentos.get(l).getId_segmento());
                if(isegmento==true){
                    if (modulo != 1) {
                        ContentValues values = new ContentValues();
                        String[] parametros = {idS};
                        String patoflexId = (""+id);
                        values.put(Utilidades.PATOLOGIAFLEX.CAMPO_ID_PATOLOGIA, patoflexId);
                        values.put(Utilidades.PATOLOGIAFLEX.CAMPO_ID_SEGMENTO_PATOLOGIA,idSegmento);
                        dbPF.update(Utilidades.PATOLOGIAFLEX.TABLA_PATOLOGIA, values, Utilidades.PATOLOGIAFLEX.CAMPO_ID_PATOLOGIA + "=?", parametros);
                    }
                }
            }

            id = id + 1;

        }

        dbPF.close();
    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.floabtnAddSegFlex:
                Intent intent = new Intent(ConsultarSegmentoFlexActivity.this,RegistroSegmentoFlexActivity.class);
                intent.putExtra("id_carretera",idCarreteraSegFlexConsulta.getText().toString());
                intent.putExtra("nom_carretera",tvnomCarretera_consultar_segmentoFlex.getText().toString());
                startActivity(intent);
                break;
        }
    }

}
