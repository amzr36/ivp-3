package com.alejo_zr.exceldb.Carretera;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alejo_zr.exceldb.BaseDatos;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.Segmento.Flexible.ConsultarSegmentoFlexActivity;
import com.alejo_zr.exceldb.Segmento.Rigido.ConsultarSegmentoRigiActivity;
import com.alejo_zr.exceldb.entidades.Carretera;
import com.alejo_zr.exceldb.entidades.PatoFlex;
import com.alejo_zr.exceldb.entidades.SegmentoFlex;
import com.alejo_zr.exceldb.entidades.SegmentoRigi;
import com.alejo_zr.exceldb.utilidades.Utilidades;

import java.util.ArrayList;

public class CarreteraActivity extends AppCompatActivity {

    private ArrayList<SegmentoFlex> listaSegmentosF;
    private ArrayList<SegmentoRigi> listaSegmentosR;
    private ArrayList<PatoFlex> listaPatologiasFlex;
    private TextView tvIdCarretera,tvNomCarretera,tvNombreCarretera,tvCodigoCarretera,tvTerritorialCarretera,tvAdmonCarretera,
            tvLevantadoCarretera;

    private BaseDatos baseDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carretera);

        baseDatos = new BaseDatos(this);

        tvIdCarretera = (TextView) findViewById(R.id.tvIdCarretera);
        tvNomCarretera = (TextView) findViewById(R.id.tvNomCarretera);
        tvNombreCarretera = (TextView) findViewById(R.id.tvNombreCarretera);
        tvCodigoCarretera = (TextView) findViewById(R.id.tvCodigoCarretera);
        tvTerritorialCarretera = (TextView) findViewById(R.id.tvTerritorialCarretera);
        tvAdmonCarretera = (TextView) findViewById(R.id.tvAdmonCarretera);
        tvLevantadoCarretera = (TextView) findViewById(R.id.tvLevantadoCarretera);

        Bundle objetoEnviado=getIntent().getExtras();
        Carretera carretera=null;

        if(objetoEnviado!=null){
            carretera= (Carretera) objetoEnviado.getSerializable("carretera");
            tvIdCarretera.setText(carretera.getId().toString());
            tvNomCarretera.setText(carretera.getNombreCarretera().toString());
            tvNombreCarretera.setText(carretera.getNombreCarretera().toString());
            tvCodigoCarretera.setText(carretera.getCodCarretera().toString());
            tvTerritorialCarretera.setText(carretera.getTerritorial().toString());
            tvAdmonCarretera.setText(carretera.getAdmon().toString());
            tvLevantadoCarretera.setText(carretera.getLevantado().toString());

        }

        cargarSegmentosFlex();
        cargarSegmentosRigi();
        cargarDañosFlex();        

    }
    


    public void onClick(View view) {

        Intent intent = null;
        switch (view.getId()) {

            case R.id.btnSegmentoFlexible:
                intent = new Intent(CarreteraActivity.this, ConsultarSegmentoFlexActivity.class);
                intent.putExtra("id_carretera",tvIdCarretera.getText().toString());
                intent.putExtra("nom_carretera",tvNombreCarretera.getText().toString());
                startActivity(intent);
                break;
            case R.id.btnSegmentoRigido:
                intent = new Intent(CarreteraActivity.this, ConsultarSegmentoRigiActivity.class);
                intent.putExtra("id_carretera",tvIdCarretera.getText().toString());
                intent.putExtra("nom_carretera",tvNombreCarretera.getText().toString());
                startActivity(intent);
                break;

            case R.id.btnEditarCarretera:
                intent = new Intent(CarreteraActivity.this,EditarCarreteraActivity.class);
                intent.putExtra("id_carretera",tvIdCarretera.getText().toString());
                intent.putExtra("nom_carretera",tvNombreCarretera.getText().toString());
                intent.putExtra("cod_carretera",tvCodigoCarretera.getText().toString());
                intent.putExtra("territo",tvTerritorialCarretera.getText().toString());
                intent.putExtra("admon",tvAdmonCarretera.getText().toString());
                intent.putExtra("levantado",tvLevantadoCarretera.getText().toString());
                startActivity(intent);
                break;

            case R.id.btnEliminarCarretera:
                eliminarCarretera();
                break;


        }
    }

    private void eliminarCarretera() {
        SQLiteDatabase db=baseDatos.getWritableDatabase();
        String[] parametros={tvIdCarretera.getText().toString()};

        db.delete(Utilidades.CARRETERA.TABLA_CARRETERA,Utilidades.CARRETERA.CAMPO_ID_CARRETERA+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Ya se Eliminó la carretera",Toast.LENGTH_LONG).show();
        
        eliminarSegFlex();
        eliminarSegRigi();
        eliminarDañosFlex();


        Intent intent = new Intent(CarreteraActivity.this,ConsultarCarreteraActivity.class);
        startActivity(intent);
        idCarretera();
        db.close();
    }

    private void eliminarDañosFlex() {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        for (int i=0; i<listaPatologiasFlex.size();i++){
            boolean IdCarretera = tvNomCarretera.getText().toString().equals(listaPatologiasFlex.get(i).getNombre_carretera_patoFlex());

            if(IdCarretera==true){
                String[] parametrosDF={tvNomCarretera.getText().toString()};
                db.delete(Utilidades.PATOLOGIAFLEX.TABLA_PATOLOGIA,Utilidades.PATOLOGIAFLEX.CAMPO_NOMBRE_CARRETERA_PATOLOGIA+"=?",parametrosDF);
            }
        }
        idPatoFlex();

    }

    private void eliminarSegRigi() {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        for (int i=0; i<listaSegmentosR.size();i++){
            boolean IdCarretera = tvNomCarretera.getText().toString().equals(listaSegmentosR.get(i).getNombre_carretera());

            if(IdCarretera==true){
                String[] parametrosSR={tvNomCarretera.getText().toString()};
                db.delete(Utilidades.SEGMENTORIGI.TABLA_SEGMENTO,Utilidades.SEGMENTORIGI.CAMPO_NOMBRE_CARRETERA_SEGMENTO+"=?",parametrosSR);
            }
        }
        idSegRigi();
    }

    private void eliminarSegFlex() {

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        for (int i=0; i<listaSegmentosF.size();i++){
            boolean IdCarretera = tvNomCarretera.getText().toString().equals(listaSegmentosF.get(i).getNombre_carretera());

            if(IdCarretera==true){
                String[] parametrosSF={tvNomCarretera.getText().toString()};
                db.delete(Utilidades.SEGMENTOFLEX.TABLA_SEGMENTO,Utilidades.SEGMENTOFLEX.CAMPO_NOMBRE_CARRETERA_SEGMENTO+"=?",parametrosSF);
            }
        }
        //idSegFlex();


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


    private void idPatoFlex() {
    }

    private void idSegRigi() {
    }
/*
    private void idSegFlex() {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        int id=1;
        for (int i=0; i<listaSegmentosF.size();i++) {

            int idSegmento = listaSegmentosF.get(i).getId_segmento();
            int modulo = idSegmento / id;
            Toast.makeText(getApplicationContext(), "Modulo" + modulo, Toast.LENGTH_SHORT).show();
            if (modulo != 1) {
                Toast.makeText(getApplicationContext(), "Id " + id + "idS" + idSegmento + "M" + modulo, Toast.LENGTH_SHORT).show();
                String segmentoId;
                segmentoId = ("" + id);
                String[] parametrosSG = {segmentoId};
                ContentValues values = new ContentValues();
                values.put(Utilidades.SEGMENTOFLEX.CAMPO_ID_SEGMENTO, segmentoId);
                db.update(Utilidades.SEGMENTOFLEX.TABLA_SEGMENTO, values, Utilidades.SEGMENTOFLEX.CAMPO_ID_SEGMENTO + "=?", parametrosSG);
                id = id + 1;
            }
        }




    }*/

    private void idCarretera() {


    }


}
