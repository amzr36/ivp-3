package com.alejo_zr.exceldb.Carretera;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alejo_zr.exceldb.BaseDatos;
import com.alejo_zr.exceldb.MainActivity;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.Segmento.Flexible.ConsultarSegmentoFlexActivity;
import com.alejo_zr.exceldb.Segmento.Rigido.ConsultarSegmentoRigiActivity;
import com.alejo_zr.exceldb.entidades.Carretera;
import com.alejo_zr.exceldb.entidades.PatoFlex;
import com.alejo_zr.exceldb.entidades.PatoRigi;
import com.alejo_zr.exceldb.entidades.SegmentoFlex;
import com.alejo_zr.exceldb.entidades.SegmentoRigi;
import com.alejo_zr.exceldb.utilidades.Utilidades;

import java.util.ArrayList;

public class CarreteraActivity extends AppCompatActivity {

    //Se declaran las variables y objetos java

    private ArrayList<SegmentoFlex> listaSegmentosF;
    private ArrayList<SegmentoRigi> listaSegmentosR;
    private ArrayList<PatoFlex> listaPatologiasFlex;
    private ArrayList<PatoRigi> listaPatologiasRigi;
    private TextView tvIdCarretera,tvNomCarretera,tvNombreCarretera,tvCodigoCarretera,tvTerritorialCarretera,tvAdmonCarretera,
            tvLevantadoCarretera;
    private String nom_carretera;

    private BaseDatos baseDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carretera);

        baseDatos = new BaseDatos(this);
        //Se enlazanlos objetos con los views
        tvIdCarretera = (TextView) findViewById(R.id.tvIdCarretera);
        tvNomCarretera = (TextView) findViewById(R.id.tvNomCarretera);
        tvNombreCarretera = (TextView) findViewById(R.id.tvNombreCarretera);
        tvCodigoCarretera = (TextView) findViewById(R.id.tvCodigoCarretera);
        tvTerritorialCarretera = (TextView) findViewById(R.id.tvTerritorialCarretera);
        tvAdmonCarretera = (TextView) findViewById(R.id.tvAdmonCarretera);
        tvLevantadoCarretera = (TextView) findViewById(R.id.tvLevantadoCarretera);

        //Se recibe la carretera enviada
        Bundle objetoEnviado=getIntent().getExtras();
        Carretera carretera=null;

        if(objetoEnviado!=null){
            carretera= (Carretera) objetoEnviado.getSerializable("carretera");
            if(carretera!=null)
            {
                /*Si es enviado por ConsultarCarretera se recupera los datos de la carretera, mediante el ID
                    de esta*/
                tvIdCarretera.setText(carretera.getId().toString());
                tvNomCarretera.setText(carretera.getNombreCarretera().toString());
                tvNombreCarretera.setText(carretera.getNombreCarretera().toString());
                tvCodigoCarretera.setText(carretera.getCodCarretera().toString());
                tvTerritorialCarretera.setText(carretera.getTerritorial().toString());
                tvAdmonCarretera.setText(carretera.getAdmon().toString());
                tvLevantadoCarretera.setText(carretera.getLevantado().toString());
            }else{
                //Si es enviado por otra actividad se recupera los datos de la carretera, mediante el nombre
                    //de esta
                nom_carretera = objetoEnviado.getString("nom_carretera");
                SQLiteDatabase db = baseDatos.getWritableDatabase();
                String[] parametros ={nom_carretera.toString()};

                Cursor cursor = db.rawQuery("SELECT "+Utilidades.CARRETERA.CAMPO_NOMBRE_CARRETERA+","
                            +Utilidades.CARRETERA.CAMPO_CODIGO_CARRETERA+","+Utilidades.CARRETERA.CAMPO_TERRITO_CARRETERA+","+Utilidades.CARRETERA.CAMPO_ADMON_CARRETERA+
                            ","+Utilidades.CARRETERA.CAMPO_LEVANTADO_CARRETERA+" FROM "+Utilidades.CARRETERA.TABLA_CARRETERA+
                        " WHERE "+Utilidades.CARRETERA.CAMPO_NOMBRE_CARRETERA+"=? ",parametros);

                    cursor.moveToFirst();
                    tvNombreCarretera.setText(cursor.getString(0));
                    tvCodigoCarretera.setText(cursor.getString(1));
                    tvTerritorialCarretera.setText(cursor.getString(2));
                    tvAdmonCarretera.setText(cursor.getString(3));
                    tvLevantadoCarretera.setText(cursor.getString(4));

            }

        }

        cargarSegmentosFlex();
        cargarSegmentosRigi();
        cargarDañosFlex();
        cargarDañosRigi();

    }

    public void onClick(View view) {
        //Al oprimir un boton entra a este metodo, y dependiendo del boton se selecciona el caso
        Intent intent = null;
        switch (view.getId()) {

            case R.id.btnSegmentoFlexible:
                //Se abre la actividad ConsultarSegmentoFlex
                intent = new Intent(CarreteraActivity.this, ConsultarSegmentoFlexActivity.class);
                intent.putExtra("nom_carretera",tvNombreCarretera.getText().toString());
                startActivity(intent);
                break;
            case R.id.btnSegmentoRigido:
                //Se abre la actividad ConsultarSegmentoRigi
                intent = new Intent(CarreteraActivity.this, ConsultarSegmentoRigiActivity.class);
                intent.putExtra("nom_carretera",tvNombreCarretera.getText().toString());
                startActivity(intent);
                break;

            case R.id.btnEditarCarretera:
                //Se abre la actividad EditarCarretera, y se envian los datos de esta a la otra
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
                /* Se confirma por el usuario que desea eliminar la carretera, de ser confirmado no solo se
                    eliminan los datos de la tabla carretera, si no de todas las otras tablas los campos que
                    estén correlacionados con dicha carretera*/
                confirmar();
                break;
            case R.id.backCarreteraActivity:
                //Se devuelve a la actividad ConsultarCarreteraActivity
                intent = new Intent(CarreteraActivity.this,ConsultarCarreteraActivity.class);
                startActivity(intent);
                break;
            case R.id.homeConsulCarreteraActivity:
                intent = new Intent(CarreteraActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void confirmar() {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Confirmación");
        dialogo1.setMessage("¿ Desea eliminar la Carretera "+tvNombreCarretera.getText()+"?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                eliminarCarretera();
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelar();
            }
        });
        dialogo1.show();
    }

    public void cancelar() {
        //finish();
    }


    private void eliminarCarretera() {
        SQLiteDatabase db=baseDatos.getWritableDatabase();
        String[] parametros={tvIdCarretera.getText().toString()};

        db.delete(Utilidades.CARRETERA.TABLA_CARRETERA,Utilidades.CARRETERA.CAMPO_ID_CARRETERA+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Ya se Eliminó la carretera",Toast.LENGTH_LONG).show();
        
        eliminarSegFlex();
        eliminarSegRigi();
        eliminarDañosFlex();
        eliminarDañosRigi();


        Intent intent = new Intent(CarreteraActivity.this,ConsultarCarreteraActivity.class);
        startActivity(intent);
        db.close();
    }

    private void eliminarDañosRigi() {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        for (int i=0; i<listaPatologiasRigi.size();i++){
            boolean IdCarretera = tvNomCarretera.getText().toString().equals(listaPatologiasRigi.get(i).getNombre_carretera_patoRigi());

            if(IdCarretera==true){
                String[] parametrosDR={tvNomCarretera.getText().toString()};
                db.delete(Utilidades.PATOLOGIARIGI.TABLA_PATOLOGIA,Utilidades.PATOLOGIARIGI.CAMPO_NOMBRE_CARRETERA_PATOLOGIA+"=?",parametrosDR);
            }
        }

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

    }
    private void cargarDañosRigi() {

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

            listaPatologiasRigi.add(patoRigi);

        }

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
            patoFlex.setId_segmento_patoFlex(cursor.getInt(1));
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


}
