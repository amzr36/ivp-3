package com.alejo_zr.exceldb.Pato.rigi;

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
import com.alejo_zr.exceldb.Segmento.Rigido.SegmentoRigiActivity;
import com.alejo_zr.exceldb.entidades.PatoRigi;
import com.alejo_zr.exceldb.utilidades.Utilidades;

import java.util.ArrayList;

public class ConsultaPatologiaRigiActivity extends AppCompatActivity {

    //Se declaran las variables y objetos java
    private ListView listViewPatologiasRigi;
    private ArrayList<String> listaInformacionPatologiasRigi;
    private ArrayList<PatoRigi> listaPatologiasRigi;
    private ArrayList<Integer> listaIdPatoRigi;

    private BaseDatos baseDatos;
    private TextView tvnomCarretera_consultar_patoRigi,tvIdSegmento_consultar_patoRigi;
    private String campoIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_patologia_rigi);

        //Se enlazanlos objetos con los views
        baseDatos=new BaseDatos(this);
        listViewPatologiasRigi = (ListView) findViewById(R.id.listViewPatologiaRigi);
        tvnomCarretera_consultar_patoRigi = (TextView) findViewById(R.id.tvnomCarretera_consultar_patoRigi);
        tvIdSegmento_consultar_patoRigi = (TextView) findViewById(R.id.tvIdSegmento_consultar_patoRigi);

        //Se recibe el nombre de la carretera y el ID del segmento
        Bundle bundle = getIntent().getExtras();
        String dato_nom = bundle.getString("tv_nombre_carretera_segmento").toString();
        String id_segmento = bundle.getString("tv_id_segmento").toString();
        String dato_is= bundle.getString("campoIS".toString());
        tvnomCarretera_consultar_patoRigi.setText(dato_nom);
        tvIdSegmento_consultar_patoRigi.setText(id_segmento);
        campoIS = dato_is;


        consultarListaPatologias();

        //Se le asigna el diseño y la lista que va a cargar el listview
        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacionPatologiasRigi);
        listViewPatologiasRigi.setAdapter(adaptador);

        listViewPatologiasRigi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posS, long l) {

                //Al seleccionar un item de la lista, este selecciona el ID del segmento y lo envia a SegmentoFlexActivity
                PatoRigi patologia=listaPatologiasRigi.get(listaIdPatoRigi.get(posS));
                Intent intent=new Intent(ConsultaPatologiaRigiActivity.this, PatologiaRigiActivity.class);

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
        listViewPatologiasRigi = (ListView) findViewById(R.id.listViewPatologiaRigi);
        tvnomCarretera_consultar_patoRigi = (TextView) findViewById(R.id.tvnomCarretera_consultar_patoRigi);
        tvIdSegmento_consultar_patoRigi = (TextView) findViewById(R.id.tvIdSegmento_consultar_patoRigi);

        Bundle bundle = getIntent().getExtras();
        String dato_nom = bundle.getString("tv_nombre_carretera_segmento").toString();
        String id_segmento = bundle.getString("tv_id_segmento").toString();
        tvnomCarretera_consultar_patoRigi.setText(dato_nom);
        tvIdSegmento_consultar_patoRigi.setText(id_segmento);


        consultarListaPatologias();

        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacionPatologiasRigi);
        listViewPatologiasRigi.setAdapter(adaptador);

        listViewPatologiasRigi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posS, long l) {


                PatoRigi patologia=listaPatologiasRigi.get(listaIdPatoRigi.get(posS));
                Intent intent=new Intent(ConsultaPatologiaRigiActivity.this, PatologiaRigiActivity.class);

                Bundle bundle=new Bundle();
                bundle.putSerializable("patologia",patologia);

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
    }


    private void consultarListaPatologias() {

        //Carga todas las patologías de pavimento rígido que se han registrado
        SQLiteDatabase db=baseDatos.getReadableDatabase();

        PatoRigi patoRigi=null;
        listaPatologiasRigi= new ArrayList<PatoRigi>();
        //select * from PatoRigi
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
        obtenerLista();

    }
    private void obtenerLista() {

        /*Se filtran los deterioros asociados al segmento y a la carretera que se están consultando,
        de tal manera no se mostraran en pantalla los que no pertenezcan a esta búsqueda*/


        listaInformacionPatologiasRigi = new ArrayList<String>();
        listaIdPatoRigi = new ArrayList<Integer>();

        for (int i = 0; i < listaPatologiasRigi.size(); i++) {
            boolean nomCarretera = tvnomCarretera_consultar_patoRigi.getText().toString().equals(listaPatologiasRigi.get(i).getNombre_carretera_patoRigi());

            if (nomCarretera == true) {
                boolean idSeg = tvIdSegmento_consultar_patoRigi.getText().toString().equals(listaPatologiasRigi.get(i).getId_segmento_patoRigi());

                if (idSeg == true) {
                    listaInformacionPatologiasRigi.add(" ABS "+ listaPatologiasRigi.get(i).getAbscisa()+ " - Daño: " + listaPatologiasRigi.get(i).getDanio()
                            +"- Severidad "+listaPatologiasRigi.get(i).getSeveridad());
                    listaIdPatoRigi.add(listaPatologiasRigi.get(i).getId_patoRigi()-1);
                }
            }
        }
    }

    public void onClick(View view) {
        //Al oprimir un boton entra a este metodo, y dependiendo del selecionado se selecciona el caso
        Intent intent = null;
        switch (view.getId()){
            case R.id.floabtnAddPatoRigi:
                //Abre la actividad RegistroPatologiaRigi, enviando el nombre de la carretera
                intent = new Intent(ConsultaPatologiaRigiActivity.this, RegistroPatologiaRigiActivity.class);
                intent.putExtra("id_segmento",tvIdSegmento_consultar_patoRigi.getText().toString());
                intent.putExtra("nom_carretera_segmento",tvnomCarretera_consultar_patoRigi.getText().toString());
                intent.putExtra("campoIS",campoIS);
                startActivity(intent);
                break;
            case R.id.backConsulPatoRigiActivity:
                //Se devuelve a la actividad SegmentoRigi
            intent = new Intent(ConsultaPatologiaRigiActivity.this, SegmentoRigiActivity.class);
            intent.putExtra("id_segmento",tvIdSegmento_consultar_patoRigi.getText().toString());
            startActivity(intent);
        }
    }
}
