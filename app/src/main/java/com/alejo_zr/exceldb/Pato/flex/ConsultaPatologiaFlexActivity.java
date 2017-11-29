package com.alejo_zr.exceldb.Pato.flex;

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
import com.alejo_zr.exceldb.MainActivity;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.Segmento.Flexible.SegmentoFlexActivity;
import com.alejo_zr.exceldb.entidades.PatoFlex;
import com.alejo_zr.exceldb.utilidades.Utilidades;

import java.util.ArrayList;

public class ConsultaPatologiaFlexActivity extends AppCompatActivity {

    //Se declaran las variables y objetos java
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

        //Se enlazanlos objetos con los views
        baseDatos=new BaseDatos(this);
        listViewPatologiasFlex = (ListView) findViewById(R.id.listViewPatologiaFlex);
        tvnomCarretera_consultar_patoFlex = (TextView) findViewById(R.id.tvnomCarretera_consultar_patoFlex);
        tvIdSegmento_consultar_patoflex = (TextView) findViewById(R.id.tvIdSegmento_consultar_patoflex);

        //Se recibe el nombre de la carretera y el ID del segmento
        Bundle bundle = getIntent().getExtras();
        String dato_nom = bundle.getString("tv_nombre_carretera_segmento").toString();
        String id_segmento = bundle.getString("tv_id_segmento").toString();
        String dato_is= bundle.getString("campoIS".toString());
        tvnomCarretera_consultar_patoFlex.setText(dato_nom);
        tvIdSegmento_consultar_patoflex.setText(id_segmento);
        campoIS = dato_is;


        consultarListaPatologias();

        //Se le asigna el diseño y la lista que va a cargar el listview
        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacionPatologiasFlex);
        listViewPatologiasFlex.setAdapter(adaptador);

        listViewPatologiasFlex.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posS, long l) {

                //Al seleccionar un item de la lista, este selecciona el ID del segmento y lo envia a SegmentoFlexActivity
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


    private void consultarListaPatologias() {

        //Carga todas las patologías de pavimento flexible que se han registrado
        SQLiteDatabase db=baseDatos.getReadableDatabase();

        PatoFlex patoFlex=null;
        listaPatologiasFlex= new ArrayList<PatoFlex>();
        //select * from PatoFlex
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
        editarIdPato();
        obtenerLista();

    }

    private void editarIdPato() {
        /* Al ser eliminado un daño, si es necesario modificar los identificadores los ID's de las demás
        patologías se realiza para que sigan teniendo un orden consecutivo
         */
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        int id = 1;
        for (int i = 0; i < listaPatologiasFlex.size(); i++) {
            double idPatoFlex = listaPatologiasFlex.get(i).getId_patoFlex();
            double modulo = idPatoFlex / id;
            String idP = new String("" + idPatoFlex);
            if (modulo != 1) {
                ContentValues values = new ContentValues();
                String[] parametros = {idP};
                String patoflexId = (""+id);
                values.put(Utilidades.PATOLOGIAFLEX.CAMPO_ID_PATOLOGIA, patoflexId);
                db.update(Utilidades.PATOLOGIAFLEX.TABLA_PATOLOGIA, values, Utilidades.PATOLOGIAFLEX.CAMPO_ID_PATOLOGIA + "=?", parametros);
            }
            id = id + 1;

        }

        db.close();
    }

    private void obtenerLista() {

        /*Se filtran los deterioros asociados al segmento y a la carretera que se están consultando,
            de tal manera no se mostraran en pantalla los que no pertenezcan a esta búsqueda*/
        listaInformacionPatologiasFlex = new ArrayList<String>();
        listaIdPatoFlex = new ArrayList<Integer>();

        for (int i = 0; i < listaPatologiasFlex.size(); i++) {
            boolean nomCarretera = tvnomCarretera_consultar_patoFlex.getText().toString().equals(listaPatologiasFlex.get(i).getNombre_carretera_patoFlex());

            if (nomCarretera == true) {
                int idSegmento = Integer.parseInt(tvIdSegmento_consultar_patoflex.getText().toString());

                if (idSegmento == listaPatologiasFlex.get(i).getId_segmento_patoFlex()) {

                    listaInformacionPatologiasFlex.add(" ABS "+ listaPatologiasFlex.get(i).getAbscisa()+" -Daño: " + listaPatologiasFlex.get(i).getDanio()
                            +"- Severidad "+listaPatologiasFlex.get(i).getSeveridad());
                    listaIdPatoFlex.add(listaPatologiasFlex.get(i).getId_patoFlex()-1);
                }

            }


        }
    }

    public void onClick(View view) {
        //Al oprimir un boton entra a este metodo, y dependiendo del selecionado se selecciona el caso
        Intent intent = null;
        switch (view.getId()){
            case R.id.floabtnAddPatoFlex:
                //Abre la actividad RegistroPatologiaFlex, enviando el nombre de la carretera
                intent = new Intent(ConsultaPatologiaFlexActivity.this, RegistroPatologiaFlexActivity.class);
                intent.putExtra("id_segmento",tvIdSegmento_consultar_patoflex.getText().toString());
                intent.putExtra("nom_carretera_segmento",tvnomCarretera_consultar_patoFlex.getText().toString());
                intent.putExtra("campoIS",campoIS);
                startActivity(intent);
                break;
            case R.id.backConsulPatoFlexActivity:
                //Se devuelve a la actividad SegmentoFlex
                intent = new Intent(ConsultaPatologiaFlexActivity.this, SegmentoFlexActivity.class);
                intent.putExtra("id_segmento",tvIdSegmento_consultar_patoflex.getText().toString());
                startActivity(intent);
            case R.id.homeConsulPatoFlexActivity:
                intent = new Intent(ConsultaPatologiaFlexActivity.this, MainActivity.class);
                startActivity(intent);
                break;

        }
    }
}