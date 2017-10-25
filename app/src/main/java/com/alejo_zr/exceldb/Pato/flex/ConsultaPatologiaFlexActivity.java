package com.alejo_zr.exceldb.Pato.flex;

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
import com.alejo_zr.exceldb.utilidades.Utilidades;

import java.util.ArrayList;

public class ConsultaPatologiaFlexActivity extends AppCompatActivity {

    private ListView listViewPatologiasFlex;
    private ArrayList<String> listaInformacionPatologiasFlex;
    private ArrayList<PatoFlex> listaPatologiasFlex;
    private ArrayList<Integer> listaIdPatoFlex;
    private String campoIS;

    private BaseDatos baseDatos;
    private TextView tvnomCarretera_consultar_patoFlex,tvIdSegmento_consultar_patoflex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_patologia_flex);

        baseDatos=new BaseDatos(this);
        listViewPatologiasFlex = (ListView) findViewById(R.id.listViewPatologiaFlex);
        tvnomCarretera_consultar_patoFlex = (TextView) findViewById(R.id.tvnomCarretera_consultar_patoFlex);
        tvIdSegmento_consultar_patoflex = (TextView) findViewById(R.id.tvIdSegmento_consultar_patoflex);

        Bundle bundle = getIntent().getExtras();
        String dato_nom = bundle.getString("tv_nombre_carretera_segmento").toString();
        String id_segmento = bundle.getString("tv_id_segmento").toString();
        String dato_is= bundle.getString("campoIS".toString());
        tvnomCarretera_consultar_patoFlex.setText(dato_nom);
        tvIdSegmento_consultar_patoflex.setText(id_segmento);
        campoIS = dato_is;


        consultarListaPatologias();

        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacionPatologiasFlex);
        listViewPatologiasFlex.setAdapter(adaptador);

        listViewPatologiasFlex.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posS, long l) {


                PatoFlex patologia=listaPatologiasFlex.get(listaIdPatoFlex.get(posS));
                Intent intent=new Intent(ConsultaPatologiaFlexActivity.this, PatologiaFlexActivity.class);

                Bundle bundle=new Bundle();
                bundle.putSerializable("patologia",patologia);

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
    }
    protected void onStart() {
        super.onStart();

        baseDatos=new BaseDatos(this);
        listViewPatologiasFlex = (ListView) findViewById(R.id.listViewPatologiaFlex);
        tvnomCarretera_consultar_patoFlex = (TextView) findViewById(R.id.tvnomCarretera_consultar_patoFlex);
        tvIdSegmento_consultar_patoflex = (TextView) findViewById(R.id.tvIdSegmento_consultar_patoflex);

        Bundle bundle = getIntent().getExtras();
        String dato_nom = bundle.getString("tv_nombre_carretera_segmento").toString();
        String id_segmento = bundle.getString("tv_id_segmento").toString();
        tvnomCarretera_consultar_patoFlex.setText(dato_nom);
        tvIdSegmento_consultar_patoflex.setText(id_segmento);


        consultarListaPatologias();

        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacionPatologiasFlex);
        listViewPatologiasFlex.setAdapter(adaptador);

        listViewPatologiasFlex.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posS, long l) {


                PatoFlex patologia=listaPatologiasFlex.get(listaIdPatoFlex.get(posS));
                Intent intent=new Intent(ConsultaPatologiaFlexActivity.this, PatologiaFlexActivity.class);

                Bundle bundle=new Bundle();
                bundle.putSerializable("patologia",patologia);

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
    }


    private void consultarListaPatologias() {

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

            listaPatologiasFlex.add(patoFlex);

        }
        obtenerLista();

    }

    private void obtenerLista() {

        listaInformacionPatologiasFlex = new ArrayList<String>();
        listaIdPatoFlex = new ArrayList<Integer>();

        for (int i = 0; i < listaPatologiasFlex.size(); i++) {
            boolean nomCarretera = tvnomCarretera_consultar_patoFlex.getText().toString().equals(listaPatologiasFlex.get(i).getNombre_carretera_patoFlex());

            if (nomCarretera == true) {
                int idSegmento = Integer.parseInt(tvIdSegmento_consultar_patoflex.getText().toString());
                //boolean idSeg = tvIdSegmento_consultar_patoflex.getText().toString().equals(listaPatologiasFlex.get(i).getId_segmento_patoFlex());

                if (idSegmento == listaPatologiasFlex.get(i).getId_segmento_patoFlex()) {
                    listaInformacionPatologiasFlex.add("Carretera: " + listaPatologiasFlex.get(i).getNombre_carretera_patoFlex() +"-Segmento "
                            +listaPatologiasFlex.get(i).getId_segmento_patoFlex()+ " - Daño: " + listaPatologiasFlex.get(i).getDanio()
                            +"- ABS "+ listaPatologiasFlex.get(i).getAbscisa()+"- Severidad "+listaPatologiasFlex.get(i).getSeveridad());
                    listaIdPatoFlex.add(listaPatologiasFlex.get(i).getId_patoFlex()-1);
                }

            }


        }
    }

    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.floabtnAddPatoFlex:
                intent = new Intent(ConsultaPatologiaFlexActivity.this, RegistroPatologiaFlexActivity.class);
                intent.putExtra("id_segmento",tvIdSegmento_consultar_patoflex.getText().toString());
                intent.putExtra("nom_carretera_segmento",tvnomCarretera_consultar_patoFlex.getText().toString());
                intent.putExtra("campoIS",campoIS);
                startActivity(intent);
                break;
        }
    }
}