package com.alejo_zr.exceldb.Pato.rigi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alejo_zr.exceldb.BaseDatos;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.entidades.PatoRigi;
import com.alejo_zr.exceldb.utilidades.Utilidades;

public class PatologiaRigiActivity extends AppCompatActivity {

    private TextView tvIdDaño,tvIdSegmento,tvNombreCarreteraPatologiaActivity,tvNumeroLosa,tvLetraLosa,tvLargoLosa,tvAncholosa ,
            tvAclaraciones,tvanchRepa,tvlarRepa,tvdanionombre,tvlarDanio,tvanchoDanio,tvAbscisaPatoRigiActivity,
            tvLatPatoRigiActivity,tvLongRigiActivity,tvDireccionPatoRigi,tvSeveridadPatoRigiActivity,tvNombreFoto_patoRigiActivity;
    private String path;
    private ImageView imgDanio_PatoRigi;
    private BaseDatos baseDatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patologia_rigi);

        baseDatos = new BaseDatos (this);

        tvNombreCarreteraPatologiaActivity = (TextView) findViewById(R.id.tvNombreCarreteraPatologiaRigiActivity);
        tvAbscisaPatoRigiActivity = (TextView) findViewById(R.id.tvAbscisaPatoRigiActivity);
        tvLatPatoRigiActivity= (TextView) findViewById(R.id.tvLatPatoRigiActivity);
        tvLongRigiActivity= (TextView) findViewById(R.id.tvLongRigiActivity);
        tvNumeroLosa=(TextView) findViewById(R.id.tvnumero);
        tvLetraLosa=(TextView) findViewById(R.id.tvletra);
        tvLargoLosa=(TextView) findViewById(R.id.tvLargoLosa);
        tvAncholosa=(TextView) findViewById(R.id.tvancholosa);
        tvdanionombre = (TextView) findViewById(R.id.tvdanionombrePatoRigiActivity);
        tvSeveridadPatoRigiActivity = (TextView) findViewById(R.id.tvSeveridadPatoRigiActivity);
        tvlarDanio = (TextView) findViewById(R.id.tvlarDanioPatoRigiActivity);
        tvanchoDanio = (TextView) findViewById(R.id.tvanchDanioPatoRigiActivity);
        tvlarRepa = (TextView) findViewById(R.id.tvlarRepaPatoRigiActivity);
        tvanchRepa = (TextView) findViewById(R.id.tvanchoRepaPatoRigiActivity);
        tvAclaraciones=(TextView) findViewById(R.id.tvAclaracionesPatoRigiActivity);

        tvIdSegmento = (TextView) findViewById(R.id.tvIdSegmentoPatologiaRigiActivity);
        tvIdDaño = (TextView) findViewById(R.id.tvIdDañoPatoRigi);
        tvNombreFoto_patoRigiActivity = (TextView) findViewById(R.id.tvNoombreFoto_patoRigiActivity);
        tvDireccionPatoRigi = (TextView) findViewById(R.id.tvDireccionPatoRigi);

        imgDanio_PatoRigi = (ImageView) findViewById(R.id.imgDanio_PatoRigi) ;


        Bundle patologiaEnviado=getIntent().getExtras();
        PatoRigi patoRigi=null;

        if(patologiaEnviado!=null){

            patoRigi = (PatoRigi) patologiaEnviado.getSerializable("patologia");
            if(patoRigi!=null) {
                tvAbscisaPatoRigiActivity.setText(patoRigi.getAbscisa().toString());
                tvLatPatoRigiActivity.setText(patoRigi.getLatitud().toString());
                tvLongRigiActivity.setText(patoRigi.getLongitud().toString());
                tvNumeroLosa.setText(patoRigi.getNo_placa().toString());
                tvLetraLosa.setText((patoRigi.getLetra().toString()));
                tvLargoLosa.setText(patoRigi.getLargoLoza().toString());
                tvAncholosa.setText(patoRigi.getAnchoLoza().toString());
                tvdanionombre.setText(patoRigi.getDanio().toString());
                tvSeveridadPatoRigiActivity.setText(patoRigi.getSeveridad().toString());
                tvlarDanio.setText(patoRigi.getLargoDanio().toString());
                tvanchoDanio.setText(patoRigi.getAnchoDanio().toString());
                tvanchRepa.setText(patoRigi.getAnchoRepa().toString());
                tvlarRepa.setText(patoRigi.getLargoRepa().toString());
                tvAclaraciones.setText(patoRigi.getAclaraciones().toString());
                tvIdSegmento.setText(patoRigi.getId_segmento_patoRigi().toString());
                tvNombreCarreteraPatologiaActivity.setText(patoRigi.getNombre_carretera_patoRigi().toString());
                tvIdDaño.setText(patoRigi.getId_patoRigi().toString());
                tvNombreFoto_patoRigiActivity.setText(patoRigi.getNombreFoto().toString());
                tvDireccionPatoRigi.setText(patoRigi.getFoto().toString());
            }else{
                tvIdDaño.setText(patologiaEnviado.getString("tvIdDaño"));
                SQLiteDatabase db = baseDatos.getWritableDatabase();
                String [] parametros = {tvIdDaño.getText().toString()};

                Cursor cursor = db.rawQuery("SELECT "+Utilidades.PATOLOGIARIGI.CAMPO_ID_PATOLOGIA+","+Utilidades.PATOLOGIARIGI.CAMPO_ID_SEGMENTO_PATOLOGIA+","
                        +Utilidades.PATOLOGIARIGI.CAMPO_NOMBRE_CARRETERA_PATOLOGIA+","+Utilidades.PATOLOGIARIGI.CAMPO_ABSCISA_PATOLOGIA+","
                        +Utilidades.PATOLOGIARIGI.CAMPO_LATITUD+","+Utilidades.PATOLOGIARIGI.CAMPO_LONGITUD+","+Utilidades.PATOLOGIARIGI.CAMPO_LETRA_LOSA+","+
                        Utilidades.PATOLOGIARIGI.CAMPO_NUMERO_LOSA+","+Utilidades.PATOLOGIARIGI.CAMPO_LARGO_LOSA+","+Utilidades.PATOLOGIARIGI.CAMPO_ANCHO_LOSA+","+
                        Utilidades.PATOLOGIARIGI.CAMPO_DANIO_PATOLOGIA+","+Utilidades.PATOLOGIARIGI.CAMPO_SEVERIDAD+","+Utilidades.PATOLOGIARIGI.CAMPO_LARGO_PATOLOGIA+
                        ","+Utilidades.PATOLOGIARIGI.CAMPO_ANCHO_PATOLOGIA+","+Utilidades.PATOLOGIARIGI.CAMPO_LARGO_REPARACION+","+Utilidades.PATOLOGIARIGI.CAMPO_ANCHO_REPARACION+
                        ","+Utilidades.PATOLOGIARIGI.CAMPO_ACLARACIONES+","+Utilidades.PATOLOGIARIGI.CAMPO_NOMBRE_FOTO+","+Utilidades.PATOLOGIARIGI.CAMPO_FOTO_DANIO+
                        " FROM "+Utilidades.PATOLOGIARIGI.TABLA_PATOLOGIA+" WHERE "+Utilidades.PATOLOGIARIGI.CAMPO_ID_PATOLOGIA+"=?",parametros);

                cursor.moveToFirst();
                tvIdDaño.setText(cursor.getString(0));
                tvIdSegmento.setText(cursor.getString(1));
                tvNombreCarreteraPatologiaActivity.setText(cursor.getString(2));
                tvAbscisaPatoRigiActivity.setText(cursor.getString(3));
                tvLatPatoRigiActivity.setText(cursor.getString(4));
                tvLongRigiActivity.setText(cursor.getString(5));
                tvLetraLosa.setText(cursor.getString(6));
                tvNumeroLosa.setText(cursor.getString(7));
                tvLargoLosa.setText(cursor.getString(8));
                tvAncholosa.setText(cursor.getString(9));
                tvdanionombre.setText(cursor.getString(10));
                tvSeveridadPatoRigiActivity.setText(cursor.getString(11));
                tvlarDanio.setText(cursor.getString(12));
                tvanchoDanio.setText(cursor.getString(13));
                tvlarRepa.setText(cursor.getString(14));
                tvanchRepa.setText(cursor.getString(15));
                tvAclaraciones.setText(cursor.getString(16));
                tvNombreFoto_patoRigiActivity.setText(cursor.getString(17));
                tvDireccionPatoRigi.setText(cursor.getString(18));

            }

        }

        path = tvDireccionPatoRigi.getText().toString();
        obtenerFotoPatoRigi();
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvNombreCarreteraPatologiaActivity = (TextView) findViewById(R.id.tvNombreCarreteraPatologiaRigiActivity);
        tvAbscisaPatoRigiActivity = (TextView) findViewById(R.id.tvAbscisaPatoRigiActivity);
        tvLatPatoRigiActivity= (TextView) findViewById(R.id.tvLatPatoRigiActivity);
        tvLongRigiActivity= (TextView) findViewById(R.id.tvLongRigiActivity);
        tvNumeroLosa=(TextView) findViewById(R.id.tvnumero);
        tvLetraLosa=(TextView) findViewById(R.id.tvletra);
        tvLargoLosa=(TextView) findViewById(R.id.tvLargoLosa);
        tvAncholosa=(TextView) findViewById(R.id.tvancholosa);
        tvdanionombre = (TextView) findViewById(R.id.tvdanionombrePatoRigiActivity);
        tvSeveridadPatoRigiActivity = (TextView) findViewById(R.id.tvSeveridadPatoRigiActivity);
        tvlarDanio = (TextView) findViewById(R.id.tvlarDanioPatoRigiActivity);
        tvanchoDanio = (TextView) findViewById(R.id.tvanchDanioPatoRigiActivity);
        tvlarRepa = (TextView) findViewById(R.id.tvlarRepaPatoRigiActivity);
        tvanchRepa = (TextView) findViewById(R.id.tvanchoRepaPatoRigiActivity);
        tvAclaraciones=(TextView) findViewById(R.id.tvAclaracionesPatoRigiActivity);

        tvIdSegmento = (TextView) findViewById(R.id.tvIdSegmentoPatologiaRigiActivity);
        tvIdDaño = (TextView) findViewById(R.id.tvIdDañoPatoRigi);
        tvNombreFoto_patoRigiActivity = (TextView) findViewById(R.id.tvNoombreFoto_patoRigiActivity);
        tvDireccionPatoRigi = (TextView) findViewById(R.id.tvDireccionPatoRigi);

        imgDanio_PatoRigi = (ImageView) findViewById(R.id.imgDanio_PatoRigi) ;

        Bundle patologiaEnviado=getIntent().getExtras();
        PatoRigi patoRigi=null;

        if(patologiaEnviado!=null){

                patoRigi = (PatoRigi) patologiaEnviado.getSerializable("patologia");
            if(patoRigi!=null) {
                tvAbscisaPatoRigiActivity.setText(patoRigi.getAbscisa().toString());
                tvLatPatoRigiActivity.setText(patoRigi.getLatitud().toString());
                tvLongRigiActivity.setText(patoRigi.getLongitud().toString());
                tvNumeroLosa.setText(patoRigi.getNo_placa().toString());
                tvLetraLosa.setText((patoRigi.getLetra().toString()));
                tvLargoLosa.setText(patoRigi.getLargoLoza().toString());
                tvAncholosa.setText(patoRigi.getAnchoLoza().toString());
                tvdanionombre.setText(patoRigi.getDanio().toString());
                tvSeveridadPatoRigiActivity.setText(patoRigi.getSeveridad().toString());
                tvlarDanio.setText(patoRigi.getLargoDanio().toString());
                tvanchoDanio.setText(patoRigi.getAnchoDanio().toString());
                tvanchRepa.setText(patoRigi.getAnchoRepa().toString());
                tvlarRepa.setText(patoRigi.getLargoRepa().toString());
                tvAclaraciones.setText(patoRigi.getAclaraciones().toString());
                tvIdSegmento.setText(patoRigi.getId_segmento_patoRigi().toString());
                tvNombreCarreteraPatologiaActivity.setText(patoRigi.getNombre_carretera_patoRigi().toString());
                tvIdDaño.setText(patoRigi.getId_patoRigi().toString());
                tvNombreFoto_patoRigiActivity.setText(patoRigi.getNombreFoto().toString());
                tvDireccionPatoRigi.setText(patoRigi.getFoto().toString());
            }else{
                tvIdDaño.setText(patologiaEnviado.getString("tvIdDaño"));
                SQLiteDatabase db = baseDatos.getWritableDatabase();
                String [] parametros = {tvIdDaño.getText().toString()};

                Cursor cursor = db.rawQuery("SELECT "+Utilidades.PATOLOGIARIGI.CAMPO_ID_PATOLOGIA+","+Utilidades.PATOLOGIARIGI.CAMPO_ID_SEGMENTO_PATOLOGIA+","
                        +Utilidades.PATOLOGIARIGI.CAMPO_NOMBRE_CARRETERA_PATOLOGIA+","+Utilidades.PATOLOGIARIGI.CAMPO_ABSCISA_PATOLOGIA+","
                        +Utilidades.PATOLOGIARIGI.CAMPO_LATITUD+","+Utilidades.PATOLOGIARIGI.CAMPO_LONGITUD+","+Utilidades.PATOLOGIARIGI.CAMPO_LETRA_LOSA+","+
                        Utilidades.PATOLOGIARIGI.CAMPO_NUMERO_LOSA+","+Utilidades.PATOLOGIARIGI.CAMPO_LARGO_LOSA+","+Utilidades.PATOLOGIARIGI.CAMPO_ANCHO_LOSA+","+
                        Utilidades.PATOLOGIARIGI.CAMPO_DANIO_PATOLOGIA+","+Utilidades.PATOLOGIARIGI.CAMPO_SEVERIDAD+","+Utilidades.PATOLOGIARIGI.CAMPO_LARGO_PATOLOGIA+
                        ","+Utilidades.PATOLOGIARIGI.CAMPO_ANCHO_PATOLOGIA+","+Utilidades.PATOLOGIARIGI.CAMPO_LARGO_REPARACION+","+Utilidades.PATOLOGIARIGI.CAMPO_ANCHO_REPARACION+
                        ","+Utilidades.PATOLOGIARIGI.CAMPO_ACLARACIONES+","+Utilidades.PATOLOGIARIGI.CAMPO_NOMBRE_FOTO+","+Utilidades.PATOLOGIARIGI.CAMPO_FOTO_DANIO+
                        " FROM "+Utilidades.PATOLOGIARIGI.TABLA_PATOLOGIA+" WHERE "+Utilidades.PATOLOGIARIGI.CAMPO_ID_PATOLOGIA+"=?",parametros);

                cursor.moveToFirst();
                tvIdDaño.setText(cursor.getString(0));
                tvIdSegmento.setText(cursor.getString(1));
                tvNombreCarreteraPatologiaActivity.setText(cursor.getString(2));
                tvAbscisaPatoRigiActivity.setText(cursor.getString(3));
                tvLatPatoRigiActivity.setText(cursor.getString(4));
                tvLongRigiActivity.setText(cursor.getString(5));
                tvLetraLosa.setText(cursor.getString(6));
                tvNumeroLosa.setText(cursor.getString(7));
                tvLargoLosa.setText(cursor.getString(8));
                tvAncholosa.setText(cursor.getString(9));
                tvdanionombre.setText(cursor.getString(10));
                tvSeveridadPatoRigiActivity.setText(cursor.getString(11));
                tvlarDanio.setText(cursor.getString(12));
                tvanchoDanio.setText(cursor.getString(13));
                tvlarRepa.setText(cursor.getString(14));
                tvanchRepa.setText(cursor.getString(15));
                tvAclaraciones.setText(cursor.getString(16));
                tvNombreFoto_patoRigiActivity.setText(cursor.getString(17));
                tvDireccionPatoRigi.setText(cursor.getString(18));

            }

        }
        path = tvDireccionPatoRigi.getText().toString();
        obtenerFotoPatoRigi();
    }

    private void obtenerFotoPatoRigi() {

        MediaScannerConnection.scanFile(this, new String[]{path}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("Ruta de almacenamiento","Path: "+path);
                    }
                });

        Bitmap bitmap= BitmapFactory.decodeFile(path);
        /*int alto=300;//alto en pixeles
        int ancho=350;//ancho en pixeles
        bitmap = Bitmap.createScaledBitmap(bitmap,alto,ancho,true);*/
        imgDanio_PatoRigi.setImageBitmap(bitmap);
    }


    public void onClick(View view) {

        Intent intent = null;
        switch (view.getId()){
            case R.id.backPatoRigiActivity:
                intent = new Intent(PatologiaRigiActivity.this,ConsultaPatologiaRigiActivity.class);
                intent.putExtra("tv_id_segmento",tvIdSegmento.getText().toString());
                intent.putExtra("tv_nombre_carretera_segmento",tvNombreCarreteraPatologiaActivity.getText().toString());
                startActivity(intent);
                break;

            case R.id.btnEditarPatologiaRigi:
                intent = new Intent(PatologiaRigiActivity.this,EditarPatologiaRigiActivity.class);
                intent.putExtra("tvIdDaño",tvIdDaño.getText().toString());
                startActivity( intent);
                break;

            case R.id.btnEliminarPatoFlex:
                confirmar();
                break;

        }

    }
    private void confirmar() {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Confirmación");
        dialogo1.setMessage("¿ Desea eliminar el daño? ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                eliminarPato();
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

    private void eliminarPato() {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String[] parametros={tvIdDaño.getText().toString()};
        db.delete(Utilidades.PATOLOGIARIGI.TABLA_PATOLOGIA,Utilidades.PATOLOGIARIGI.CAMPO_ID_PATOLOGIA+"=?",parametros);
        Intent intent = new Intent (PatologiaRigiActivity.this, ConsultaPatologiaRigiActivity.class);
        intent.putExtra("tv_id_segmento",tvIdSegmento.getText().toString());
        intent.putExtra("tv_nombre_carretera_segmento",tvNombreCarreteraPatologiaActivity.getText().toString());
        startActivity(intent);

        db.close();
    }
}
