package com.alejo_zr.exceldb.Pato.rigi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.entidades.PatoRigi;

public class PatologiaRigiActivity extends AppCompatActivity {

    private TextView tvIdDaño,tvIdSegmento,tvNombreCarreteraPatologiaActivity,tvNumeroLosa,tvLetraLosa,tvLargoLosa,tvAncholoza ,tvAclaraciones,tvanchRepa,tvlarRepa,tvdanionombre,tvlarDanio,tvanchoDanio,
            tvAbscisaPatoRigiActivity,tvLatPatoRigiActivity,tvLongRigiActivity,tvDireccionPatoRigi,tvSeveridadPatoRigiActivity,tvNoombreFoto_patoRigiActivity;
    private String path;
    private ImageView imgDanio_PatoRigi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patologia_rigi);

        tvNombreCarreteraPatologiaActivity = (TextView) findViewById(R.id.tvNombreCarreteraPatologiaRigiActivity);
        tvAbscisaPatoRigiActivity = (TextView) findViewById(R.id.tvAbscisaPatoRigiActivity);
        tvLatPatoRigiActivity= (TextView) findViewById(R.id.tvLatPatoRigiActivity);
        tvLongRigiActivity= (TextView) findViewById(R.id.tvLongRigiActivity);
        tvNumeroLosa=(TextView) findViewById(R.id.tvnumero);
        tvLetraLosa=(TextView) findViewById(R.id.tvletra);
        tvLargoLosa=(TextView) findViewById(R.id.tvLargoLosa);
        tvAncholoza=(TextView) findViewById(R.id.tvancholosa);
        tvdanionombre = (TextView) findViewById(R.id.tvdanionombrePatoRigiActivity);
        tvSeveridadPatoRigiActivity = (TextView) findViewById(R.id.tvSeveridadPatoRigiActivity);
        tvlarDanio = (TextView) findViewById(R.id.tvlarDanioPatoRigiActivity);
        tvanchoDanio = (TextView) findViewById(R.id.tvanchDanioPatoRigiActivity);
        tvlarRepa = (TextView) findViewById(R.id.tvlarRepaPatoRigiActivity);
        tvanchRepa = (TextView) findViewById(R.id.tvanchoRepaPatoRigiActivity);
        tvAclaraciones=(TextView) findViewById(R.id.tvAclaracionesPatoRigiActivity);

        tvIdSegmento = (TextView) findViewById(R.id.tvIdSegmentoPatologiaRigiActivity);
        tvIdDaño = (TextView) findViewById(R.id.tvIdDañoPatoRigi);
        tvNoombreFoto_patoRigiActivity = (TextView) findViewById(R.id.tvNoombreFoto_patoRigiActivity);
        tvDireccionPatoRigi = (TextView) findViewById(R.id.tvDireccionPatoRigi);

        imgDanio_PatoRigi = (ImageView) findViewById(R.id.imgDanio_PatoRigi) ;


        Bundle patologiaEnviado=getIntent().getExtras();
        PatoRigi patoRigi=null;

        if(patologiaEnviado!=null){
            patoRigi = (PatoRigi) patologiaEnviado.getSerializable("patologia");
            tvAbscisaPatoRigiActivity.setText(patoRigi.getAbscisa().toString());
            tvLatPatoRigiActivity.setText(patoRigi.getLatitud().toString());
            tvLongRigiActivity.setText(patoRigi.getLongitud().toString());
            tvNumeroLosa.setText(patoRigi.getNo_placa().toString());
            tvLetraLosa.setText((patoRigi.getLetra().toString()));
            tvLargoLosa.setText(patoRigi.getLargoLoza().toString());
            tvAncholoza.setText(patoRigi.getAnchoLoza().toString());
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
            tvDireccionPatoRigi.setText(patoRigi.getFoto().toString());

        }

        path = tvDireccionPatoRigi.getText().toString();
        obtenerFotoPatoRigi();
    }

    protected void onStart() {
        super.onStart();

        tvNombreCarreteraPatologiaActivity = (TextView) findViewById(R.id.tvNombreCarreteraPatologiaRigiActivity);
        tvAbscisaPatoRigiActivity = (TextView) findViewById(R.id.tvAbscisaPatoRigiActivity);
        tvLatPatoRigiActivity= (TextView) findViewById(R.id.tvLatPatoRigiActivity);
        tvLongRigiActivity= (TextView) findViewById(R.id.tvLongRigiActivity);
        tvNumeroLosa=(TextView) findViewById(R.id.tvnumero);
        tvLetraLosa=(TextView) findViewById(R.id.tvletra);
        tvLargoLosa=(TextView) findViewById(R.id.tvLargoLosa);
        tvAncholoza=(TextView) findViewById(R.id.tvancholosa);
        tvdanionombre = (TextView) findViewById(R.id.tvdanionombrePatoRigiActivity);
        tvSeveridadPatoRigiActivity = (TextView) findViewById(R.id.tvSeveridadPatoRigiActivity);
        tvlarDanio = (TextView) findViewById(R.id.tvlarDanioPatoRigiActivity);
        tvanchoDanio = (TextView) findViewById(R.id.tvanchDanioPatoRigiActivity);
        tvlarRepa = (TextView) findViewById(R.id.tvlarRepaPatoRigiActivity);
        tvanchRepa = (TextView) findViewById(R.id.tvanchoRepaPatoRigiActivity);
        tvAclaraciones=(TextView) findViewById(R.id.tvAclaracionesPatoRigiActivity);

        tvIdSegmento = (TextView) findViewById(R.id.tvIdSegmentoPatologiaRigiActivity);
        tvIdDaño = (TextView) findViewById(R.id.tvIdDañoPatoRigi);
        tvNoombreFoto_patoRigiActivity = (TextView) findViewById(R.id.tvNoombreFoto_patoRigiActivity);
        tvDireccionPatoRigi = (TextView) findViewById(R.id.tvDireccionPatoRigi);

        imgDanio_PatoRigi = (ImageView) findViewById(R.id.imgDanio_PatoRigi) ;


        Bundle patologiaEnviado=getIntent().getExtras();
        PatoRigi patoRigi=null;

        if(patologiaEnviado!=null){
            patoRigi = (PatoRigi) patologiaEnviado.getSerializable("patologia");

            tvAbscisaPatoRigiActivity.setText(patoRigi.getAbscisa().toString());
            tvLatPatoRigiActivity.setText(patoRigi.getLatitud().toString());
            tvLongRigiActivity.setText(patoRigi.getLongitud().toString());
            tvNumeroLosa.setText(patoRigi.getNo_placa().toString());
            tvLetraLosa.setText((patoRigi.getLetra().toString()));
            tvLargoLosa.setText(patoRigi.getLargoLoza().toString());
            tvAncholoza.setText(patoRigi.getAnchoLoza().toString());
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
            tvDireccionPatoRigi.setText(patoRigi.getFoto().toString());

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
        imgDanio_PatoRigi.setImageBitmap(bitmap);
    }
}
