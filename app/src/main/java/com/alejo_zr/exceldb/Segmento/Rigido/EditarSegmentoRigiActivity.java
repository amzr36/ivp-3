package com.alejo_zr.exceldb.Segmento.Rigido;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alejo_zr.exceldb.BaseDatos;
import com.alejo_zr.exceldb.MainActivity;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.utilidades.Utilidades;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditarSegmentoRigiActivity extends AppCompatActivity {

    private EditText campoNCalzadas, campoNCarriles, campoEspesorLosa, campoAnchoBerma, campoPRI, campoPRF, campoComentarios,campoFecha;
    private TextView tvNombre_Carretera_Segmento;
    private TextInputLayout input_camponCalzadas,input_campoNCarriles,input_campoEspesorLosa,input_campoAnchoBerma,input_campoPRI;
    private String id;
    private BaseDatos baseDatos;

    private  int dia, mes, ano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_segmento_rigi);

        baseDatos = new BaseDatos(this);

        campoNCalzadas = (EditText) findViewById(R.id.campoNCalzadas_EditarRigi);
        campoNCarriles = (EditText) findViewById(R.id.campoNCarriles_EditarRigi);
        campoEspesorLosa = (EditText) findViewById(R.id.campoEspesorLosa_EditarRigi);
        campoAnchoBerma = (EditText) findViewById(R.id.campoAnchoBerma_EditarRigi);
        campoPRI = (EditText) findViewById(R.id.campoPRI_EditarRigi);
        campoPRF = (EditText) findViewById(R.id.campoPRF_EditarRigi);
        campoComentarios = (EditText) findViewById(R.id.campoComentarios_EditarRigi);
        campoFecha = (EditText) findViewById(R.id.campoFechaSegmentoRigiEditar);

        tvNombre_Carretera_Segmento = (TextView) findViewById(R.id.tvNombre_Carretera_Segmento_EditarRigi);

        input_camponCalzadas = (TextInputLayout) findViewById(R.id.input_campoNCalzadas_EditarRigi);
        input_campoNCarriles= (TextInputLayout) findViewById(R.id.input_campoNCarriles_EditarRigi );
        input_campoEspesorLosa= (TextInputLayout) findViewById(R.id.input_campoEspesorLosa_EditarRigi );
        input_campoAnchoBerma= (TextInputLayout) findViewById(R.id.input_campoAnchoBerma_EditarRigi);
        input_campoPRI= (TextInputLayout) findViewById(R.id.input_campoPRIRigiEditar);


        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("tv_id_segmento").toString();
        String nom_carretera_seg= bundle.getString("tv_nombre_carretera_segmento").toString();
        String nCalzadas = bundle.getString("tvnCalzadas").toString();
        String nCarriles = bundle.getString("tvnCarriles").toString();
        String espesorLosa = bundle.getString("tvespesorLosa".toString());
        String anchoBerma = bundle.getString("tvanchoBerma".toString());
        String pri = bundle.getString("tvPRI").toString();
        String prf = bundle.getString("tvPRF").toString();
        String comentarios = bundle.getString("tvComentarios").toString();
        String fecha = bundle.getString("tvFechaSegmentoFlex".toString());


        //Se asignan los datos de la carretera a cada EditText
        tvNombre_Carretera_Segmento.setText(nom_carretera_seg);
        campoNCalzadas.setText(nCalzadas);
        campoNCarriles.setText(nCarriles);
        campoEspesorLosa.setText(espesorLosa);
        campoAnchoBerma.setText(anchoBerma);
        campoPRI.setText(pri);
        campoPRF.setText(prf);
        campoComentarios.setText(comentarios);
        campoFecha.setText(fecha);

        fechaActual();
    }

    private void fechaActual() {

        final Calendar c = Calendar.getInstance();
        ano = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH);
        dia = c.get(Calendar.DAY_OF_MONTH);

        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        String s = formatter.format(c.getTime());
        campoFecha.setText(s);
    }
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnEditarSegmentoActivityRigi:
                editarSegmento();
                break;
            case R.id.backEditarSegRigiActivity:
                Intent intent = new Intent(EditarSegmentoRigiActivity.this,SegmentoRigiActivity.class);
                intent.putExtra("id_segmento",id);
                startActivity(intent);
                break;
            case R.id.homeEditarSegRigiActivity:
                intent = new Intent(EditarSegmentoRigiActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void editarSegmento() {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String[] parametros={id};

        ContentValues values = new ContentValues();

        values.put(Utilidades.SEGMENTORIGI.CAMPO_CALZADAS_SEGMENTO , campoNCalzadas.getText().toString());
        values.put(Utilidades.SEGMENTORIGI.CAMPO_CARRILES_SEGMENTO  , campoNCarriles.getText().toString());
        values.put(Utilidades.SEGMENTORIGI.CAMPO_ANCHO_BERMA  , campoAnchoBerma.getText().toString());
        values.put(Utilidades.SEGMENTORIGI.CAMPO_ESPESOR_LOSA , campoEspesorLosa.getText().toString());
        values.put(Utilidades.SEGMENTORIGI.CAMPO_PRI_SEGMENTO, campoPRI.getText().toString());
        values.put(Utilidades.SEGMENTORIGI.CAMPO_PRF_SEGMENTO, campoPRF.getText().toString());
        values.put(Utilidades.SEGMENTORIGI.CAMPO_COMENTARIOS  , campoComentarios.getText().toString());
        values.put(Utilidades.SEGMENTORIGI.CAMPO_FECHA, campoFecha.getText().toString());


        db.update(Utilidades.SEGMENTORIGI.TABLA_SEGMENTO,values,Utilidades.SEGMENTORIGI.CAMPO_ID_SEGMENTO+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Se edito el segmento",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EditarSegmentoRigiActivity.this,SegmentoRigiActivity.class);
        intent.putExtra("id_segmento",id);
        intent.putExtra("nom_carretera",tvNombre_Carretera_Segmento.getText().toString());
        startActivity(intent);
        db.close();

    }

}
