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
import android.widget.Toast;

import com.alejo_zr.exceldb.BaseDatos;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.entidades.SegmentoFlex;
import com.alejo_zr.exceldb.utilidades.Utilidades;

import java.util.ArrayList;

public class ConsultarSegmentoFlexActivity extends AppCompatActivity {

    private ListView listViewSegmentos;
    private ArrayList<String> listaInformacionSegmentos;
    private ArrayList<SegmentoFlex> listaSegmentos;
    private ArrayList<Integer> listaIdSegmentos;
    private TextView tvnomCarretera_consultar_segmentoFlex,idCarreteraSegFlexConsulta;

    private BaseDatos baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_segmento_flex);

        baseDatos=new BaseDatos(this);

        listViewSegmentos = (ListView) findViewById(R.id.listViewSegmentoFlex);
        tvnomCarretera_consultar_segmentoFlex = (TextView) findViewById(R.id.tvnomCarretera_consultar_segmentoFlex);
        idCarreteraSegFlexConsulta = (TextView) findViewById(R.id.idCarreteraSegFlexConsulta);

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
    }

    protected void onStart() {
        super.onStart();

        baseDatos=new BaseDatos(this);

        listViewSegmentos = (ListView) findViewById(R.id.listViewSegmentoFlex);
        tvnomCarretera_consultar_segmentoFlex = (TextView) findViewById(R.id.tvnomCarretera_consultar_segmentoFlex);
        idCarreteraSegFlexConsulta = (TextView) findViewById(R.id.idCarreteraSegFlexConsulta);

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

                startActivity(intent);
            }
        });

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

            listaSegmentos.add(segmento);
        }
        editarIdSegmento();
        obtenerLista();

    }

    private void editarIdSegmento() {

        SQLiteDatabase dbC = baseDatos.getWritableDatabase();

        int id=1;
        for (int i=0; i<listaSegmentos.size();i++) {

            double idCarretera = listaSegmentos.get(i).getId_segmento();
            double modulo = idCarretera / id;
            String idS = new String("" + idCarretera);
            if (modulo != 1) {
                ContentValues values = new ContentValues();
                Toast.makeText(getApplicationContext(),"id"+id+"idS"+idCarretera+"M"+modulo,Toast.LENGTH_SHORT).show();
                String[] parametros={idS};
                String carreteraId;
                carreteraId = ("" + id);
                values.put(Utilidades.SEGMENTOFLEX.CAMPO_ID_SEGMENTO,carreteraId);
                dbC.update(Utilidades.SEGMENTOFLEX.TABLA_SEGMENTO,values,Utilidades.SEGMENTOFLEX.CAMPO_ID_SEGMENTO+"=?",parametros);
            }
            id = id + 1;

        }

        dbC.close();
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
