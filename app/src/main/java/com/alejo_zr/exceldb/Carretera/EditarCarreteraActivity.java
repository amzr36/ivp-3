package com.alejo_zr.exceldb.Carretera;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alejo_zr.exceldb.BaseDatos;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.entidades.PatoFlex;
import com.alejo_zr.exceldb.entidades.SegmentoFlex;
import com.alejo_zr.exceldb.entidades.SegmentoRigi;
import com.alejo_zr.exceldb.utilidades.Utilidades;

import java.util.ArrayList;

public class EditarCarreteraActivity extends AppCompatActivity {

    private ListView listViewSegmentos;

    private ArrayList<SegmentoFlex> listaSegmentosF;
    private ArrayList<SegmentoRigi> listaSegmentosR;
    private ArrayList<PatoFlex> listaPatologiasFlex;
    private EditText campoIdEditar,campoNombreEditar,campoCodigoEditar,campoTerritoEditar,campoLevantadoEditar,campoAdmonEditar;
    private TextView tvEditarNombreCarretera;
    private BaseDatos baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_carretera);

        baseDatos = new BaseDatos(this);

        campoIdEditar = (EditText) findViewById(R.id.campoIdEditar);
        campoNombreEditar = (EditText) findViewById(R.id.campoNombreEditar);
        campoCodigoEditar = (EditText) findViewById(R.id.campoCodigoEditar);
        campoTerritoEditar = (EditText) findViewById(R.id.campoTerritorialEditar);
        campoAdmonEditar = (EditText) findViewById(R.id.campoAdmonEditar);
        campoLevantadoEditar = (EditText) findViewById(R.id.campoLevantadoEditar);
        tvEditarNombreCarretera = (TextView) findViewById(R.id.tvEditarNombreCarretera);


        //Se toman los datos de la carretera
        Bundle bundle = getIntent().getExtras();
        String dato_id = bundle.getString("id_carretera").toString();
        String dato_nom = bundle.getString("nom_carretera").toString();
        String cod_carretera= bundle.getString("cod_carretera").toString();
        String territo = bundle.getString("territo").toString();
        String admon = bundle.getString("admon").toString();
        String levantado = bundle.getString("levantado").toString();

        //Se asignan los datos de la carretera a cada EditText
        campoIdEditar.setText(dato_id);
        campoNombreEditar.setText(dato_nom);
        tvEditarNombreCarretera.setText(dato_nom);
        campoCodigoEditar.setText(cod_carretera);
        campoTerritoEditar.setText(territo);
        campoAdmonEditar.setText(admon);
        campoLevantadoEditar.setText(levantado);

        cargarSegmentosFlex();
        cargarSegmentosRigi();
        cargarDañosFlex();


    }

    private void cargarDañosFlex() {
        SQLiteDatabase db=baseDatos.getReadableDatabase();

        PatoFlex patoFlex=null;
        listaPatologiasFlex= new ArrayList<PatoFlex>();
        //select * from carretera
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.PATOLOGIAFLEX.TABLA_PATOLOGIA,null);

        while(cursor.moveToNext()){
            patoFlex = new PatoFlex();
            patoFlex.setId_patoFlex(cursor.getInt(0));
            patoFlex.setId_segmento_patoFlex(cursor.getString(1));
            patoFlex.setNombre_carretera_patoFlex(cursor.getString(2));
            patoFlex.setAbscisa(cursor.getString(3));
            patoFlex.setLatitud(cursor.getString(4));
            patoFlex.setLongitud(cursor.getString(5));
            patoFlex.setDanio(cursor.getString(6));
            patoFlex.setCarril(cursor.getString(7));
            patoFlex.setLargoDanio(cursor.getString(8));
            patoFlex.setAnchoDanio(cursor.getString(9));
            patoFlex.setLargoRepa(cursor.getString(10));
            patoFlex.setAnchoRepa(cursor.getString(11));
            patoFlex.setAclaraciones(cursor.getString(12));
            patoFlex.setFoto(cursor.getString(13));

            listaPatologiasFlex.add(patoFlex);

        }
    }

    private void cargarSegmentosRigi() {
        SQLiteDatabase db=baseDatos.getReadableDatabase();

        SegmentoRigi segmento=null;
        listaSegmentosR= new ArrayList<SegmentoRigi>();
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

            listaSegmentosR.add(segmento);
        }
    }

    private void cargarSegmentosFlex() {

        SQLiteDatabase db=baseDatos.getReadableDatabase();

        SegmentoFlex segmento=null;
        listaSegmentosF= new ArrayList<SegmentoFlex>();
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

            listaSegmentosF.add(segmento);
        }
    }


    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnEditarCarreteraActivity:
                editarCarretera();
                break;
            case R.id.btnEliminarCarretera:
                eliminarCarretera();
                break;


        }
    }

       private void eliminarCarretera() {
        SQLiteDatabase db=baseDatos.getWritableDatabase();
        String[] parametros={campoIdEditar.getText().toString()};

        db.delete(Utilidades.CARRETERA.TABLA_CARRETERA,Utilidades.CARRETERA.CAMPO_ID_CARRETERA+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Ya se Eliminó la carretera",Toast.LENGTH_LONG).show();
        campoIdEditar.setText("");
        Intent intent = new Intent(EditarCarreteraActivity.this,ConsultarCarreteraActivity.class);
        startActivity(intent);
        db.close();
    }


    private void editarCarretera() {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String[] parametros={campoIdEditar.getText().toString()};

        ContentValues values = new ContentValues();
        values.put(Utilidades.CARRETERA.CAMPO_NOMBRE_CARRETERA,campoNombreEditar.getText().toString());
        values.put(Utilidades.CARRETERA.CAMPO_CODIGO_CARRETERA,campoCodigoEditar.getText().toString());
        values.put(Utilidades.CARRETERA.CAMPO_TERRITO_CARRETERA,campoTerritoEditar.getText().toString());
        values.put(Utilidades.CARRETERA.CAMPO_ADMON_CARRETERA,campoAdmonEditar.getText().toString());
        values.put(Utilidades.CARRETERA.CAMPO_LEVANTADO_CARRETERA,campoLevantadoEditar.getText().toString());

        db.update(Utilidades.CARRETERA.TABLA_CARRETERA,values,Utilidades.CARRETERA.CAMPO_ID_CARRETERA+"=?",parametros);

        editarSegFlex();
        editarSegRigi();
        editarDañoFlex();

        Toast.makeText(getApplicationContext(),"Se editó la carretera: "+campoNombreEditar.getText().toString(),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EditarCarreteraActivity.this,ConsultarCarreteraActivity.class);
        startActivity(intent);
        db.close();

    }

    private void editarDañoFlex() {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        for (int i=0; i<listaPatologiasFlex.size();i++){
            boolean IdCarretera = tvEditarNombreCarretera.getText().toString().equals(listaPatologiasFlex.get(i).getNombre_carretera_patoFlex());

            if(IdCarretera==true){

                String[] parametros={tvEditarNombreCarretera.getText().toString()};
                ContentValues values = new ContentValues();
                values.put(Utilidades.PATOLOGIAFLEX.CAMPO_NOMBRE_CARRETERA_PATOLOGIA,campoNombreEditar.getText().toString());
                db.update(Utilidades.PATOLOGIAFLEX.TABLA_PATOLOGIA,values,Utilidades.PATOLOGIAFLEX.CAMPO_NOMBRE_CARRETERA_PATOLOGIA+"=?",parametros);

            }

        }
    }

    private void editarSegRigi() {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        for (int i=0; i<listaSegmentosR.size();i++){
            boolean IdCarretera = tvEditarNombreCarretera.getText().toString().equals(listaSegmentosR.get(i).getNombre_carretera());

            if(IdCarretera==true){

                String[] parametros={tvEditarNombreCarretera.getText().toString()};
                ContentValues values = new ContentValues();
                values.put(Utilidades.SEGMENTORIGI.CAMPO_NOMBRE_CARRETERA_SEGMENTO,campoNombreEditar.getText().toString());
                db.update(Utilidades.SEGMENTORIGI.TABLA_SEGMENTO,values,Utilidades.SEGMENTORIGI.CAMPO_NOMBRE_CARRETERA_SEGMENTO+"=?",parametros);

            }

        }
    }

    private void editarSegFlex() {

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        for (int i=0; i<listaSegmentosF.size();i++){
            boolean IdCarretera = tvEditarNombreCarretera.getText().toString().equals(listaSegmentosF.get(i).getNombre_carretera());

            if(IdCarretera==true){

                String[] parametros={tvEditarNombreCarretera.getText().toString()};
                ContentValues values = new ContentValues();
                values.put(Utilidades.SEGMENTOFLEX.CAMPO_NOMBRE_CARRETERA_SEGMENTO,campoNombreEditar.getText().toString());
                db.update(Utilidades.SEGMENTOFLEX.TABLA_SEGMENTO,values,Utilidades.SEGMENTOFLEX.CAMPO_NOMBRE_CARRETERA_SEGMENTO+"=?",parametros);

            }

        }
    }
}
