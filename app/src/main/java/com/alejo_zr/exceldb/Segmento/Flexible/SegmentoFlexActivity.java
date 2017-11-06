package com.alejo_zr.exceldb.Segmento.Flexible;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alejo_zr.exceldb.BaseDatos;
import com.alejo_zr.exceldb.Pato.flex.ConsultaPatologiaFlexActivity;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.entidades.PatoFlex;
import com.alejo_zr.exceldb.entidades.SegmentoFlex;
import com.alejo_zr.exceldb.utilidades.Utilidades;

import java.util.ArrayList;

public class SegmentoFlexActivity extends AppCompatActivity {

    //Se declaran las variables y objetos java
    private TextView tv_nombre_carretera_segmento,tv_id_segmento, tvnCalzadas, tvnCarriles, tvanchoCarril, tvanchoBerma, tvPRI, tvPRF, tvComentarios,tvFechaSegmentoFlex;
    private ArrayList<SegmentoFlex> listaSegmentos;
    private ArrayList<PatoFlex> listaPatologiasFlex;
    private BaseDatos baseDatos;
    private String campoIS,valoriS;
    private int id_segmento;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segmento_flex);

        //Se enlazan los objetos con los views
        baseDatos=new BaseDatos(this);
        tv_nombre_carretera_segmento = (TextView) findViewById(R.id.tv_nombre_carretera_segmento_flex);
        tv_id_segmento = (TextView) findViewById(R.id.tv_id_segmentoFlex);
        tvnCalzadas = (TextView) findViewById(R.id.tvnCalzadasFlex);
        tvnCarriles= (TextView) findViewById(R.id.tvnCarrilesFlex);
        tvanchoCarril= (TextView) findViewById(R.id.tvanchoCarril);
        tvanchoBerma= (TextView) findViewById(R.id.tvanchoBermaFlex);
        tvPRI= (TextView) findViewById(R.id.tvPRIFlex);
        tvPRF= (TextView) findViewById(R.id.tvPRFFlex);
        tvComentarios= (TextView) findViewById(R.id.tvComentariosFlex);
        tvFechaSegmentoFlex = (TextView) findViewById(R.id.tvFechaSegmentoFlex);

        //Se recibe el segmento enviado
        Bundle segmentoEnviado=getIntent().getExtras();
        SegmentoFlex segmento=null;

        if(segmentoEnviado!=null){
            segmento = (SegmentoFlex) segmentoEnviado.getSerializable("segmento");
            if(segmento!=null){
                //Si es enviado por ConsultarSegmentoFlex se recupera los datos del segmento de esta forma
                tv_id_segmento.setText(segmento.getId_segmento().toString());
                id_segmento = segmento.getId_segmento();
                tv_nombre_carretera_segmento.setText(segmento.getNombre_carretera().toString());
                tvnCalzadas.setText(segmento.getnCalzadas().toString());
                tvnCarriles.setText(segmento.getnCarriles().toString());
                tvanchoCarril.setText(segmento.getAnchoCarril().toString());
                tvanchoBerma.setText(segmento.getAnchoBerma().toString());
                tvPRI.setText(segmento.getPri().toString());
                tvPRF.setText(segmento.getPrf().toString());
                tvComentarios.setText(segmento.getComentarios().toString());
                tvFechaSegmentoFlex.setText(segmento.getFecha().toString());
                valoriS = segmento.getIs();
            }else{
                //Si se envian los datos desde otra actividad se recuperan de esta otra forma
                tv_id_segmento.setText(segmentoEnviado.getString("id_segmento"));
                SQLiteDatabase db = baseDatos.getWritableDatabase();
                String [] parametros = {tv_id_segmento.getText().toString()};

                Cursor cursor = db.rawQuery("SELECT "+Utilidades.SEGMENTOFLEX.CAMPO_ID_SEGMENTO+","+Utilidades.SEGMENTOFLEX.CAMPO_NOMBRE_CARRETERA_SEGMENTO+","+
                        Utilidades.SEGMENTOFLEX.CAMPO_CALZADAS_SEGMENTO+","+Utilidades.SEGMENTOFLEX.CAMPO_CARRILES_SEGMENTO+","+Utilidades.SEGMENTOFLEX.CAMPO_ANCHO_CARRIL+","+
                        Utilidades.SEGMENTOFLEX.CAMPO_ANCHO_BERMA+","+Utilidades.SEGMENTOFLEX.CAMPO_PRI_SEGMENTO+","+Utilidades.SEGMENTOFLEX.CAMPO_PRF_SEGMENTO+","+
                        Utilidades.SEGMENTOFLEX.CAMPO_COMENTARIOS+","+Utilidades.SEGMENTOFLEX.CAMPO_FECHA+" FROM "+Utilidades.SEGMENTOFLEX.TABLA_SEGMENTO+
                        " WHERE "+Utilidades.SEGMENTOFLEX.CAMPO_ID_SEGMENTO+"=?",parametros);

                cursor.moveToFirst();
                tv_id_segmento.setText(cursor.getString(0));
                tv_nombre_carretera_segmento.setText(cursor.getString(1));
                tvnCalzadas.setText(cursor.getString(2));
                tvnCarriles.setText(cursor.getString(3));
                tvanchoCarril.setText(cursor.getString(4));
                tvanchoBerma.setText(cursor.getString(5));
                tvPRI.setText(cursor.getString(6));
                tvPRF.setText(cursor.getString(7));
                tvComentarios.setText(cursor.getString(8));
                tvFechaSegmentoFlex.setText(cursor.getString(9));

            }
        }

        consultarListaSegmentos();
        agregarIS();
        cargarDañosFlex();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Se enlazan los objetos con los views
        baseDatos=new BaseDatos(this);
        tv_nombre_carretera_segmento = (TextView) findViewById(R.id.tv_nombre_carretera_segmento_flex);
        tv_id_segmento = (TextView) findViewById(R.id.tv_id_segmentoFlex);
        tvnCalzadas = (TextView) findViewById(R.id.tvnCalzadasFlex);
        tvnCarriles= (TextView) findViewById(R.id.tvnCarrilesFlex);
        tvanchoCarril= (TextView) findViewById(R.id.tvanchoCarril);
        tvanchoBerma= (TextView) findViewById(R.id.tvanchoBermaFlex);
        tvPRI= (TextView) findViewById(R.id.tvPRIFlex);
        tvPRF= (TextView) findViewById(R.id.tvPRFFlex);
        tvComentarios= (TextView) findViewById(R.id.tvComentariosFlex);
        tvFechaSegmentoFlex = (TextView) findViewById(R.id.tvFechaSegmentoFlex);

        //Se recibe el segmento enviado
        Bundle segmentoEnviado=getIntent().getExtras();
        SegmentoFlex segmento=null;

        if(segmentoEnviado!=null){
            segmento = (SegmentoFlex) segmentoEnviado.getSerializable("segmento");
            if(segmento!=null){
                //Si es enviado por ConsultarSegmentoFlex se recupera los datos del segmento de esta forma
                tv_id_segmento.setText(segmento.getId_segmento().toString());
                id_segmento = segmento.getId_segmento();
                tv_nombre_carretera_segmento.setText(segmento.getNombre_carretera().toString());
                tvnCalzadas.setText(segmento.getnCalzadas().toString());
                tvnCarriles.setText(segmento.getnCarriles().toString());
                tvanchoCarril.setText(segmento.getAnchoCarril().toString());
                tvanchoBerma.setText(segmento.getAnchoBerma().toString());
                tvPRI.setText(segmento.getPri().toString());
                tvPRF.setText(segmento.getPrf().toString());
                tvComentarios.setText(segmento.getComentarios().toString());
                tvFechaSegmentoFlex.setText(segmento.getFecha().toString());
                valoriS = segmento.getIs();
            }else{
                //Si se envian los datos desde otra actividad se recuperan de esta otra forma
                tv_id_segmento.setText(segmentoEnviado.getString("id_segmento"));
                SQLiteDatabase db = baseDatos.getWritableDatabase();
                String [] parametros = {tv_id_segmento.getText().toString()};

                Cursor cursor = db.rawQuery("SELECT "+Utilidades.SEGMENTOFLEX.CAMPO_ID_SEGMENTO+","+Utilidades.SEGMENTOFLEX.CAMPO_NOMBRE_CARRETERA_SEGMENTO+","+
                        Utilidades.SEGMENTOFLEX.CAMPO_CALZADAS_SEGMENTO+","+Utilidades.SEGMENTOFLEX.CAMPO_CARRILES_SEGMENTO+","+Utilidades.SEGMENTOFLEX.CAMPO_ANCHO_CARRIL+","+
                        Utilidades.SEGMENTOFLEX.CAMPO_ANCHO_BERMA+","+Utilidades.SEGMENTOFLEX.CAMPO_PRI_SEGMENTO+","+Utilidades.SEGMENTOFLEX.CAMPO_PRF_SEGMENTO+","+
                        Utilidades.SEGMENTOFLEX.CAMPO_COMENTARIOS+","+Utilidades.SEGMENTOFLEX.CAMPO_FECHA+" FROM "+Utilidades.SEGMENTOFLEX.TABLA_SEGMENTO+
                        " WHERE "+Utilidades.SEGMENTOFLEX.CAMPO_ID_SEGMENTO+"=?",parametros);

                cursor.moveToFirst();
                tv_id_segmento.setText(cursor.getString(0));
                tv_nombre_carretera_segmento.setText(cursor.getString(1));
                tvnCalzadas.setText(cursor.getString(2));
                tvnCarriles.setText(cursor.getString(3));
                tvanchoCarril.setText(cursor.getString(4));
                tvanchoBerma.setText(cursor.getString(5));
                tvPRI.setText(cursor.getString(6));
                tvPRF.setText(cursor.getString(7));
                tvComentarios.setText(cursor.getString(8));
                tvFechaSegmentoFlex.setText(cursor.getString(9));

            }
        }

        consultarListaSegmentos();
        agregarIS();
        cargarDañosFlex();

    }


    private void consultarListaSegmentos() {

        SQLiteDatabase db = baseDatos.getReadableDatabase();

        SegmentoFlex segmento = null;
        listaSegmentos = new ArrayList<SegmentoFlex>();
        //select * from carretera
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.SEGMENTOFLEX.TABLA_SEGMENTO, null);


        while (cursor.moveToNext()) {
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
    }

    private void agregarIS() {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String[] parametros={tv_id_segmento.getText().toString()};
        campoIS=new String(tv_nombre_carretera_segmento.getText().toString()+"-"+tv_id_segmento.getText().toString());

        ContentValues values = new ContentValues();
        values.put(Utilidades.SEGMENTOFLEX.CAMPO_IS , campoIS);
        db.update(Utilidades.SEGMENTOFLEX.TABLA_SEGMENTO,values,Utilidades.SEGMENTOFLEX.CAMPO_ID_SEGMENTO+"=?",parametros);
        editarIsPato();
        db.close();
    }

    public void onClick(View view) {

        Intent intent = null;
        switch (view.getId()) {
            //Abre la actividad ConsultarPatologiaFlex
            case R.id.btnConsultarPatologiaFlex:
                intent = new Intent(SegmentoFlexActivity.this, ConsultaPatologiaFlexActivity.class);
                intent.putExtra("tv_id_segmento",tv_id_segmento.getText().toString());
                intent.putExtra("tv_nombre_carretera_segmento",tv_nombre_carretera_segmento.getText().toString());
                intent.putExtra("campoIS",campoIS);
                startActivity(intent);
                break;

            case R.id.btnEditarSegmentoFlex:
            //Abre la actividad EditarSegmentoFlex
                intent = new Intent (SegmentoFlexActivity.this, EditarSegmentoFlexActivity.class);
                intent.putExtra("tv_id_segmento",tv_id_segmento.getText().toString());
                intent.putExtra("tv_nombre_carretera_segmento",tv_nombre_carretera_segmento.getText().toString());
                intent.putExtra("tvnCalzadas" , tvnCalzadas.getText().toString());
                intent.putExtra("tvnCarriles" , tvnCarriles.getText().toString());
                intent.putExtra("tvanchoCarril", tvanchoCarril.getText().toString());
                intent.putExtra("tvanchoBerma", tvanchoBerma.getText().toString());
                intent.putExtra("tvPRI" , tvPRI.getText().toString());
                intent.putExtra("tvPRF" , tvPRF.getText().toString());
                intent.putExtra("tvComentarios" , tvComentarios.getText().toString());
                intent.putExtra("tvFechaSegmentoFlex",tvFechaSegmentoFlex.getText().toString());

                startActivity(intent);
                break;
            case R.id.btnEliminarSegmentoFlex:
                /* Se confirma por el usuario que desea eliminar el segmento, de ser confirmado no solo se
                    eliminan los datos de la tabla segmento, si no de todas las otras tablas los campos que
                    estén correlacionados con dicha segmento*/
                confirmar();
                break;
            case R.id.backSegFlexActivity:
                //Se devuelve a la actividad ConsultarSegmentoFlex
                intent = new Intent (SegmentoFlexActivity.this, ConsultarSegmentoFlexActivity.class);
                intent.putExtra("nom_carretera",tv_nombre_carretera_segmento.getText().toString());
                startActivity(intent);
                break;

        }
    }

    private void confirmar() {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Confirmación");
        dialogo1.setMessage("¿ Desea eliminar el segmento ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                eliminarSegFlex();
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

    private void eliminarSegFlex() {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String[] parametrosSF={tv_id_segmento.getText().toString()};
        eliminarDañosFlex();
        db.delete(Utilidades.SEGMENTOFLEX.TABLA_SEGMENTO,Utilidades.SEGMENTOFLEX.CAMPO_ID_SEGMENTO+"=?",parametrosSF);

        Intent intent = new Intent (SegmentoFlexActivity.this, ConsultarSegmentoFlexActivity.class);
        intent.putExtra("nom_carretera",tv_nombre_carretera_segmento.getText().toString());
        startActivity(intent);

        db.close();
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
    private void editarIsPato() {

        SQLiteDatabase dbIS = baseDatos.getWritableDatabase();
        String[] parametros={valoriS};
        campoIS=new String(tv_nombre_carretera_segmento.getText().toString()+"-"+tv_id_segmento.getText().toString());

        ContentValues values = new ContentValues();
        values.put(Utilidades.PATOLOGIAFLEX.CAMPO_IS , campoIS);
        dbIS.update(Utilidades.PATOLOGIAFLEX.TABLA_PATOLOGIA,values,Utilidades.PATOLOGIAFLEX.CAMPO_IS+"=?",parametros);
        dbIS.close();
    }

    private void eliminarDañosFlex() {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        Toast.makeText(getApplicationContext(),"Eliminar daños flex",Toast.LENGTH_SHORT).show();
        for (int i=0; i<listaPatologiasFlex.size();i++){
            boolean IdSegmento = tv_id_segmento.getText().toString().equals(""+listaPatologiasFlex.get(i).getId_segmento_patoFlex());
            Toast.makeText(getApplicationContext(),"id_segmento "+tv_id_segmento.getText().toString()+"get Id segmento"+listaPatologiasFlex.get(i).getId_segmento_patoFlex(),Toast.LENGTH_SHORT).show();
            if(IdSegmento==true){
                Toast.makeText(getApplicationContext(),"entra if",Toast.LENGTH_SHORT).show();
                String[] parametrosDF={tv_id_segmento.getText().toString()};
                db.delete(Utilidades.PATOLOGIAFLEX.TABLA_PATOLOGIA,Utilidades.PATOLOGIAFLEX.CAMPO_ID_SEGMENTO_PATOLOGIA+"=?",parametrosDF);
            }
        }

    }

}
