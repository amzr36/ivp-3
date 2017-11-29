package com.alejo_zr.exceldb.Pato.flex;

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
import com.alejo_zr.exceldb.MainActivity;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.entidades.PatoFlex;
import com.alejo_zr.exceldb.utilidades.Utilidades;

public class PatologiaFlexActivity extends AppCompatActivity {

    //Se declaran las variables y objetos java
    private final String CARPETA_RAIZ="InventarioVial/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"PavimentoFlexible/";

    private TextView tvIdDaño,tvIdSegmento,tvNombreCarreteraPatologiaActivity,tvCarrilDanio,tvAclaraciones,tvanchRepa,tvlarRepa,tvdanionombre,tvlarDanio,tvanchoDanio,
            tvAbscisaPatoFlexActivity,tvLatPatoFlexActivity,tvLongFlexActivity,tvDireccionPatoFlex,tvSeveridadPatoFlexActivity
            ,tvNombreFoto_patoFlexActivity;
    private String path;
    private ImageView imgPatoFlex;
    private BaseDatos baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patologia_flex);

        baseDatos = new BaseDatos(this);

        //Se enlazan los objetos con los views
        tvNombreCarreteraPatologiaActivity = (TextView) findViewById(R.id.tvNombreCarreteraPatologiaFlexActivity);
        tvAbscisaPatoFlexActivity = (TextView) findViewById(R.id.tvAbscisaPatoFlexActivity);
        tvLatPatoFlexActivity= (TextView) findViewById(R.id.tvLatPatoFlexActivity);
        tvLongFlexActivity= (TextView) findViewById(R.id.tvLongFlexActivity);
        tvCarrilDanio= (TextView) findViewById(R.id.tvCarrilDanioPatoFlexActivity);
        tvdanionombre = (TextView) findViewById(R.id.tvdanionombrePatoFlexActivity);
        tvSeveridadPatoFlexActivity = (TextView) findViewById(R.id.tvSeveridadPatoFlexActivity);
        tvlarDanio = (TextView) findViewById(R.id.tvlarDanioPatoFlexActivity);
        tvanchoDanio = (TextView) findViewById(R.id.tvanchDanioPatoFlexActivity);
        tvanchRepa = (TextView) findViewById(R.id.tvanchoRepaPatoFlexActivity);
        tvlarRepa = (TextView) findViewById(R.id.tvlarRepaPatoFlexActivity);
        tvAclaraciones=(TextView) findViewById(R.id.tvAclaracionesPatoFlexActivity);

        tvIdSegmento = (TextView) findViewById(R.id.tvIdSegmentoPatologiaFlexActivity);
        tvIdDaño = (TextView) findViewById(R.id.tvIdDañoPatoFlex);
        tvNombreFoto_patoFlexActivity = (TextView) findViewById(R.id.tvNombreFoto_patoFlexActivity);
        tvDireccionPatoFlex = (TextView) findViewById(R.id.tvDireccionPatoFlex);

        imgPatoFlex = (ImageView) findViewById(R.id.imgDanio_PatoFlex) ;

        //Se recibe la patología enviada
        Bundle patologiaEnviado=getIntent().getExtras();
        PatoFlex patoFlex=null;

        if(patologiaEnviado!=null){
            patoFlex = (PatoFlex) patologiaEnviado.getSerializable("patologia");
            if(patoFlex!=null){
                //Si es enviado por ConsultarPatologiaFlex se recupera los datos del segmento de esta forma
                tvAbscisaPatoFlexActivity.setText(patoFlex.getAbscisa().toString());
                tvLatPatoFlexActivity.setText(patoFlex.getLatitud().toString());
                tvLongFlexActivity.setText(patoFlex.getLongitud().toString());
                tvCarrilDanio.setText(patoFlex.getCarril().toString());
                tvdanionombre.setText(patoFlex.getDanio().toString());
                tvSeveridadPatoFlexActivity.setText(patoFlex.getSeveridad().toString());
                tvlarDanio.setText(patoFlex.getLargoDanio().toString());
                tvanchoDanio.setText(patoFlex.getAnchoDanio().toString());
                tvanchRepa.setText(patoFlex.getAnchoRepa().toString());
                tvlarRepa.setText(patoFlex.getLargoRepa().toString());
                tvAclaraciones.setText(patoFlex.getAclaraciones().toString());
                tvIdSegmento.setText(patoFlex.getId_segmento_patoFlex().toString());
                tvNombreCarreteraPatologiaActivity.setText(patoFlex.getNombre_carretera_patoFlex().toString());
                tvNombreFoto_patoFlexActivity.setText(patoFlex.getNombreFoto().toString());
                tvIdDaño.setText(patoFlex.getId_patoFlex().toString());
                tvDireccionPatoFlex.setText(patoFlex.getFoto().toString());
            }else{
                //Si se envian los datos desde otra actividad se recuperan de esta otra forma
                tvIdDaño.setText(patologiaEnviado.getString("tvIdDaño"));
                SQLiteDatabase db = baseDatos.getWritableDatabase();
                String [] parametros = {tvIdDaño.getText().toString()};

                Cursor cursor = db.rawQuery("SELECT "+Utilidades.PATOLOGIAFLEX.CAMPO_ID_PATOLOGIA+","+Utilidades.PATOLOGIAFLEX.CAMPO_ID_SEGMENTO_PATOLOGIA+","
                        +Utilidades.PATOLOGIAFLEX.CAMPO_NOMBRE_CARRETERA_PATOLOGIA+","+Utilidades.PATOLOGIAFLEX.CAMPO_ABSCISA_PATOLOGIA+","
                        +Utilidades.PATOLOGIAFLEX.CAMPO_LATITUD+","+Utilidades.PATOLOGIAFLEX.CAMPO_LONGITUD+","+Utilidades.PATOLOGIAFLEX.CAMPO_CARRIL_PATOLOGIA+","+
                        Utilidades.PATOLOGIAFLEX.CAMPO_DANIO_PATOLOGIA+","+Utilidades.PATOLOGIAFLEX.CAMPO_SEVERIDAD+","+Utilidades.PATOLOGIAFLEX.CAMPO_LARGO_PATOLOGIA+
                        ","+Utilidades.PATOLOGIAFLEX.CAMPO_ANCHO_PATOLOGIA+","+Utilidades.PATOLOGIAFLEX.CAMPO_LARGO_REPARACION+","+Utilidades.PATOLOGIAFLEX.CAMPO_ANCHO_REPARACION+
                        ","+Utilidades.PATOLOGIAFLEX.CAMPO_ACLARACIONES+","+Utilidades.PATOLOGIAFLEX.CAMPO_NOMBRE_FOTO+","+Utilidades.PATOLOGIAFLEX.CAMPO_FOTO_DANIO+
                        " FROM "+Utilidades.PATOLOGIAFLEX.TABLA_PATOLOGIA+" WHERE "+Utilidades.PATOLOGIAFLEX.CAMPO_ID_PATOLOGIA+"=?",parametros);

                cursor.moveToFirst();
                tvIdDaño.setText(cursor.getString(0));
                tvIdSegmento.setText(cursor.getString(1));
                tvNombreCarreteraPatologiaActivity.setText(cursor.getString(2));
                tvAbscisaPatoFlexActivity.setText(cursor.getString(3));
                tvLatPatoFlexActivity.setText(cursor.getString(4));
                tvLongFlexActivity.setText(cursor.getString(5));
                tvCarrilDanio.setText(cursor.getString(6));
                tvdanionombre.setText(cursor.getString(7));
                tvSeveridadPatoFlexActivity.setText(cursor.getString(8));
                tvlarDanio.setText(cursor.getString(9));
                tvanchoDanio.setText(cursor.getString(10));
                tvlarRepa.setText(cursor.getString(11));
                tvanchRepa.setText(cursor.getString(12));
                tvAclaraciones.setText(cursor.getString(13));
                tvNombreFoto_patoFlexActivity.setText(cursor.getString(14));
                tvDireccionPatoFlex.setText(cursor.getString(15));

            }


        }

        path = tvDireccionPatoFlex.getText().toString();
        obtenerFotoPatoFlex();
    }

    protected void onRestart() {
        super.onRestart();

        baseDatos = new BaseDatos(this);

        tvNombreCarreteraPatologiaActivity = (TextView) findViewById(R.id.tvNombreCarreteraPatologiaFlexActivity);
        tvAbscisaPatoFlexActivity = (TextView) findViewById(R.id.tvAbscisaPatoFlexActivity);
        tvLatPatoFlexActivity= (TextView) findViewById(R.id.tvLatPatoFlexActivity);
        tvLongFlexActivity= (TextView) findViewById(R.id.tvLongFlexActivity);
        tvCarrilDanio= (TextView) findViewById(R.id.tvCarrilDanioPatoFlexActivity);
        tvdanionombre = (TextView) findViewById(R.id.tvdanionombrePatoFlexActivity);
        tvSeveridadPatoFlexActivity = (TextView) findViewById(R.id.tvSeveridadPatoFlexActivity);
        tvlarDanio = (TextView) findViewById(R.id.tvlarDanioPatoFlexActivity);
        tvanchoDanio = (TextView) findViewById(R.id.tvanchDanioPatoFlexActivity);
        tvanchRepa = (TextView) findViewById(R.id.tvanchoRepaPatoFlexActivity);
        tvlarRepa = (TextView) findViewById(R.id.tvlarRepaPatoFlexActivity);
        tvAclaraciones=(TextView) findViewById(R.id.tvAclaracionesPatoFlexActivity);

        tvIdSegmento = (TextView) findViewById(R.id.tvIdSegmentoPatologiaFlexActivity);
        tvIdDaño = (TextView) findViewById(R.id.tvIdDañoPatoFlex);
        tvNombreFoto_patoFlexActivity = (TextView) findViewById(R.id.tvNombreFoto_patoFlexActivity);
        tvDireccionPatoFlex = (TextView) findViewById(R.id.tvDireccionPatoFlex);

        imgPatoFlex = (ImageView) findViewById(R.id.imgDanio_PatoFlex) ;


        Bundle patologiaEnviado=getIntent().getExtras();
        PatoFlex patoFlex=null;

        if(patologiaEnviado!=null){
            patoFlex = (PatoFlex) patologiaEnviado.getSerializable("patologia");
            if(patoFlex!=null){
                tvAbscisaPatoFlexActivity.setText(patoFlex.getAbscisa().toString());
                tvLatPatoFlexActivity.setText(patoFlex.getLatitud().toString());
                tvLongFlexActivity.setText(patoFlex.getLongitud().toString());
                tvCarrilDanio.setText(patoFlex.getCarril().toString());
                tvdanionombre.setText(patoFlex.getDanio().toString());
                tvSeveridadPatoFlexActivity.setText(patoFlex.getSeveridad().toString());
                tvlarDanio.setText(patoFlex.getLargoDanio().toString());
                tvanchoDanio.setText(patoFlex.getAnchoDanio().toString());
                tvanchRepa.setText(patoFlex.getAnchoRepa().toString());
                tvlarRepa.setText(patoFlex.getLargoRepa().toString());
                tvAclaraciones.setText(patoFlex.getAclaraciones().toString());
                tvIdSegmento.setText(patoFlex.getId_segmento_patoFlex().toString());
                tvNombreCarreteraPatologiaActivity.setText(patoFlex.getNombre_carretera_patoFlex().toString());
                tvNombreFoto_patoFlexActivity.setText(patoFlex.getNombreFoto().toString());
                tvIdDaño.setText(patoFlex.getId_patoFlex().toString());
                tvDireccionPatoFlex.setText(patoFlex.getFoto().toString());
            }else{
                tvIdDaño.setText(patologiaEnviado.getString("tvIdDaño"));
                SQLiteDatabase db = baseDatos.getWritableDatabase();
                String [] parametros = {tvIdDaño.getText().toString()};

                Cursor cursor = db.rawQuery("SELECT "+Utilidades.PATOLOGIAFLEX.CAMPO_ID_PATOLOGIA+","+Utilidades.PATOLOGIAFLEX.CAMPO_ID_SEGMENTO_PATOLOGIA+","
                        +Utilidades.PATOLOGIAFLEX.CAMPO_NOMBRE_CARRETERA_PATOLOGIA+","+Utilidades.PATOLOGIAFLEX.CAMPO_ABSCISA_PATOLOGIA+","
                        +Utilidades.PATOLOGIAFLEX.CAMPO_LATITUD+","+Utilidades.PATOLOGIAFLEX.CAMPO_LONGITUD+","+Utilidades.PATOLOGIAFLEX.CAMPO_CARRIL_PATOLOGIA+","+
                        Utilidades.PATOLOGIAFLEX.CAMPO_DANIO_PATOLOGIA+","+Utilidades.PATOLOGIAFLEX.CAMPO_SEVERIDAD+","+Utilidades.PATOLOGIAFLEX.CAMPO_LARGO_PATOLOGIA+
                        ","+Utilidades.PATOLOGIAFLEX.CAMPO_ANCHO_PATOLOGIA+","+Utilidades.PATOLOGIAFLEX.CAMPO_LARGO_REPARACION+","+Utilidades.PATOLOGIAFLEX.CAMPO_ANCHO_REPARACION+
                        ","+Utilidades.PATOLOGIAFLEX.CAMPO_ACLARACIONES+","+Utilidades.PATOLOGIAFLEX.CAMPO_NOMBRE_FOTO+","+Utilidades.PATOLOGIAFLEX.CAMPO_FOTO_DANIO+
                        " FROM "+Utilidades.PATOLOGIAFLEX.TABLA_PATOLOGIA+" WHERE "+Utilidades.PATOLOGIAFLEX.CAMPO_ID_PATOLOGIA+"=?",parametros);

                cursor.moveToFirst();
                tvIdDaño.setText(cursor.getString(0));
                tvIdSegmento.setText(cursor.getString(1));
                tvNombreCarreteraPatologiaActivity.setText(cursor.getString(2));
                tvAbscisaPatoFlexActivity.setText(cursor.getString(3));
                tvLatPatoFlexActivity.setText(cursor.getString(4));
                tvLongFlexActivity.setText(cursor.getString(5));
                tvCarrilDanio.setText(cursor.getString(6));
                tvdanionombre.setText(cursor.getString(7));
                tvSeveridadPatoFlexActivity.setText(cursor.getString(8));
                tvlarDanio.setText(cursor.getString(9));
                tvanchoDanio.setText(cursor.getString(10));
                tvlarRepa.setText(cursor.getString(11));
                tvanchRepa.setText(cursor.getString(12));
                tvAclaraciones.setText(cursor.getString(13));
                tvNombreFoto_patoFlexActivity.setText(cursor.getString(14));
                tvDireccionPatoFlex.setText(cursor.getString(15));

            }


        }

        path = tvDireccionPatoFlex.getText().toString();
        obtenerFotoPatoFlex();

    }


    private void obtenerFotoPatoFlex() {

        try{
            MediaScannerConnection.scanFile(this, new String[]{path}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("Ruta de almacenamiento","Path: "+path);
                        }
                    });

            Bitmap bitmap= BitmapFactory.decodeFile(path);
            int alto=300;//alto en pixeles
            int ancho=350;//ancho en pixeles
            bitmap = Bitmap.createScaledBitmap(bitmap,alto,ancho,true);
            imgPatoFlex.setImageBitmap(bitmap);
        }catch(Exception e){

        }

    }

    public void onClick (View view){

        Intent intent = null;
        switch (view.getId()){

            case R.id.backPatoFlexActivity:
                intent = new Intent(PatologiaFlexActivity.this,ConsultaPatologiaFlexActivity.class);
                intent.putExtra("tv_id_segmento",tvIdSegmento.getText().toString());
                intent.putExtra("tv_nombre_carretera_segmento",tvNombreCarreteraPatologiaActivity.getText().toString());
                startActivity(intent);
                break;

            case R.id.homePatoFlexActivity:
                intent = new Intent(PatologiaFlexActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.btnEditarPatoFlex:
                intent = new Intent(PatologiaFlexActivity.this, EditarPatologiaFlexActivity.class);
                intent.putExtra("tvAbscisa",tvAbscisaPatoFlexActivity.getText().toString());
                intent.putExtra("tvLatitud",tvLatPatoFlexActivity.getText().toString());
                intent.putExtra("tvLongitud",tvLongFlexActivity.getText().toString());
                intent.putExtra("tvCarrilDanio",tvCarrilDanio.getText().toString());
                intent.putExtra("tvdanionombre",tvdanionombre.getText().toString());
                intent.putExtra("tvSeveridadPatoFlexActivity",tvSeveridadPatoFlexActivity.getText().toString());
                intent.putExtra("tvlarDanio",tvlarDanio.getText().toString());
                intent.putExtra("tvanchDanio",tvanchoDanio.getText().toString());
                intent.putExtra("tvanchRepa", tvanchRepa.getText().toString());
                intent.putExtra("tvlarRepa",tvlarRepa.getText().toString());
                intent.putExtra("tvAclaraciones",tvAclaraciones.getText().toString());
                intent.putExtra("tvDireccionPatoFlex",tvDireccionPatoFlex.getText().toString());
                intent.putExtra("tvNombreFoto_patoFlexActivity",tvNombreFoto_patoFlexActivity.getText().toString());
                intent.putExtra("imgFoto",path);
                intent.putExtra("tvIdSegmento",tvIdSegmento.getText().toString());
                intent.putExtra("tvNombreCarreteraPatologiaActivity",tvNombreCarreteraPatologiaActivity.getText().toString());
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
        db.delete(Utilidades.PATOLOGIAFLEX.TABLA_PATOLOGIA,Utilidades.PATOLOGIAFLEX.CAMPO_ID_PATOLOGIA+"=?",parametros);

        Intent intent = new Intent (PatologiaFlexActivity.this, ConsultaPatologiaFlexActivity.class);
        intent.putExtra("tv_id_segmento",tvIdSegmento.getText().toString());
        intent.putExtra("tv_nombre_carretera_segmento",tvNombreCarreteraPatologiaActivity.getText().toString());
        startActivity(intent);

        db.close();
    }


}