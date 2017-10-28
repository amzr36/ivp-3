package com.alejo_zr.exceldb.Segmento.Rigido;

import android.app.AlertDialog;
import android.content.ContentValues;
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
import com.alejo_zr.exceldb.Pato.rigi.ConsultaPatologiaRigiActivity;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.entidades.PatoRigi;
import com.alejo_zr.exceldb.entidades.SegmentoRigi;
import com.alejo_zr.exceldb.utilidades.Utilidades;

import java.util.ArrayList;

public class SegmentoRigiActivity extends AppCompatActivity {

    private BaseDatos baseDatos;
    private String campoIS,valoriS;
    private TextView tv_nombre_carretera_segmento,tv_id_segmento, tvnCalzadas, tvnCarriles, tvespesorLosa, tvanchoBerma,
            tvPRI, tvPRF, tvComentarios,tvFechaSegmentoRigi;
    private ArrayList<PatoRigi> listaPatologiasRigi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segmento_rigi);

        baseDatos=new BaseDatos(this);
        tv_nombre_carretera_segmento = (TextView) findViewById(R.id.tv_nombre_carretera_segmento_Rigi);
        tv_id_segmento = (TextView) findViewById(R.id.tv_id_segmentoFlexRigi);
        tvnCalzadas = (TextView) findViewById(R.id.tvnCalzadasRigi);
        tvnCarriles= (TextView) findViewById(R.id.tvnCarrilesRigi);
        tvespesorLosa= (TextView) findViewById(R.id.tvespesorLosaRigi);
        tvanchoBerma= (TextView) findViewById(R.id.tvanchoBermaRigi);
        tvPRI= (TextView) findViewById(R.id.tvPRIRigi);
        tvPRF= (TextView) findViewById(R.id.tvPRFRigi);
        tvComentarios= (TextView) findViewById(R.id.tvComentariosRigi);
        tvFechaSegmentoRigi = (TextView) findViewById(R.id.tvFechaSegmentoRigi);


        Bundle segmentoEnviado=getIntent().getExtras();
        SegmentoRigi segmento=null;


        if(segmentoEnviado!=null){
            segmento = (SegmentoRigi) segmentoEnviado.getSerializable("segmento");
            tv_id_segmento.setText(segmento.getId_segmento().toString());
            tv_nombre_carretera_segmento.setText(segmento.getNombre_carretera().toString());
            tvnCalzadas.setText(segmento.getnCalzadas().toString());
            tvnCarriles.setText(segmento.getnCarriles().toString());
            tvespesorLosa.setText(segmento.getEspesorLosa().toString());
            tvanchoBerma.setText(segmento.getAnchoBerma().toString());
            tvPRI.setText(segmento.getPri().toString());
            tvPRF.setText(segmento.getPrf().toString());
            tvComentarios.setText(segmento.getComentarios().toString());
            tvFechaSegmentoRigi.setText(segmento.getFecha().toString());
            valoriS = segmento.getIs();
        }

        agregarIS();
        cargarDañosRigi();
    }

    private void agregarIS() {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String[] parametros={tv_id_segmento.getText().toString()};
        campoIS=new String(tv_nombre_carretera_segmento.getText().toString()+"-"+tv_id_segmento.getText().toString());

        ContentValues values = new ContentValues();
        values.put(Utilidades.SEGMENTORIGI.CAMPO_IS , campoIS);
        db.update(Utilidades.SEGMENTORIGI.TABLA_SEGMENTO,values,Utilidades.SEGMENTORIGI.CAMPO_ID_SEGMENTO+"=?",parametros);
        editarIsPato();
        db.close();
    }

    public void onClick(View view) {

        Intent intent = null;
        switch (view.getId()) {

            case R.id.btnConsultarPatologiaRigi:
                intent = new Intent(SegmentoRigiActivity.this,ConsultaPatologiaRigiActivity.class);
                intent.putExtra("tv_id_segmento",tv_id_segmento.getText().toString());
                intent.putExtra("tv_nombre_carretera_segmento",tv_nombre_carretera_segmento.getText().toString());
                intent.putExtra("campoIS",campoIS);
                startActivity(intent);
                break;

            case R.id.btnEditarSegmentoRigi:

                intent = new Intent (SegmentoRigiActivity.this, EditarSegmentoRigiActivity.class);
                intent.putExtra("tv_id_segmento",tv_id_segmento.getText().toString());
                intent.putExtra("tv_nombre_carretera_segmento",tv_nombre_carretera_segmento.getText().toString());
                intent.putExtra("tvnCalzadas" , tvnCalzadas.getText().toString());
                intent.putExtra("tvnCarriles" , tvnCarriles.getText().toString());
                intent.putExtra("tvanchoCarril", tvespesorLosa.getText().toString());
                intent.putExtra("tvanchoBerma", tvanchoBerma.getText().toString());
                intent.putExtra("tvPRI" , tvPRI.getText().toString());
                intent.putExtra("tvPRF" , tvPRF.getText().toString());
                intent.putExtra("tvComentarios" , tvComentarios.getText().toString());
                intent.putExtra("tvFechaSegmentoFlex",tvFechaSegmentoRigi.getText().toString());
                startActivity(intent);
                break;
            case R.id.btnEliminarSegmentoRigi:
                confirmar();
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
                eliminarSegRigi();
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
    private void eliminarSegRigi() {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String[] parametrosSR={tv_id_segmento.getText().toString()};
        eliminarDañosRigi();
        db.delete(Utilidades.SEGMENTORIGI.TABLA_SEGMENTO,Utilidades.SEGMENTORIGI.CAMPO_ID_SEGMENTO+"=?",parametrosSR);
        Intent intent = new Intent (SegmentoRigiActivity.this, ConsultarSegmentoRigiActivity.class);
        intent.putExtra("nom_carretera",tv_nombre_carretera_segmento.getText().toString());
        startActivity(intent);
        db.close();
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
            patoRigi.setIs(cursor.getString(19));

            listaPatologiasRigi.add(patoRigi);

        }
    }
    private void editarIsPato() {

        SQLiteDatabase dbIS = baseDatos.getWritableDatabase();
        String[] parametros={valoriS};
        campoIS=new String(tv_nombre_carretera_segmento.getText().toString()+"-"+tv_id_segmento.getText().toString());

        ContentValues values = new ContentValues();
        values.put(Utilidades.PATOLOGIARIGI.CAMPO_IS , campoIS);
        dbIS.update(Utilidades.PATOLOGIARIGI.TABLA_PATOLOGIA,values,Utilidades.PATOLOGIARIGI.CAMPO_IS+"=?",parametros);
        dbIS.close();
    }

    private void eliminarDañosRigi() {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        Toast.makeText(getApplicationContext(),"Eliminar daños flex",Toast.LENGTH_SHORT).show();
        for (int i=0; i<listaPatologiasRigi.size();i++){
            boolean IdSegmento = tv_id_segmento.getText().toString().equals(""+listaPatologiasRigi.get(i).getId_segmento_patoRigi());
            Toast.makeText(getApplicationContext(),"id_segmento "+tv_id_segmento.getText().toString()+"get Id segmento"+listaPatologiasRigi.get(i).getId_segmento_patoRigi(),Toast.LENGTH_SHORT).show();
            if(IdSegmento==true){
                Toast.makeText(getApplicationContext(),"entra if",Toast.LENGTH_SHORT).show();
                String[] parametrosDF={tv_id_segmento.getText().toString()};
                db.delete(Utilidades.PATOLOGIARIGI.TABLA_PATOLOGIA,Utilidades.PATOLOGIARIGI.CAMPO_ID_SEGMENTO_PATOLOGIA+"=?",parametrosDF);
            }
        }
    }
}


