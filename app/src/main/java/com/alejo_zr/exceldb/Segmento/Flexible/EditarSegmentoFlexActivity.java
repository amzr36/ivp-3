package com.alejo_zr.exceldb.Segmento.Flexible;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alejo_zr.exceldb.BaseDatos;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.utilidades.Utilidades;

public class EditarSegmentoFlexActivity extends AppCompatActivity {


    EditText campoNCalzadas_EditarFlex, campoNCarriles_EditarFlex, campoAnchoCarril_EditarFlex, campoAnchoBerma_EditarFlex, campoPRI_EditarFlex,
            campoPRF_EditarFlex, campoComentarios_EditarFlex,campoFechaSegmentoFlexEditar;
    TextView tvId_Carretera_Segmento_EditarFlex,tvNombre_Carretera_Segmento_EditarFlex,campotipoPav_EditarFlex,tv_id_segmento_editarFlex;

    BaseDatos baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_segmento_flex);

        baseDatos = new BaseDatos(this);

        tv_id_segmento_editarFlex = (TextView) findViewById(R.id.tv_id_segmento_editarFlex);
        campoNCalzadas_EditarFlex = (EditText) findViewById(R.id.campoNCalzadas_EditarFlex);
        campoNCarriles_EditarFlex = (EditText) findViewById(R.id.campoNCarriles_EditarFlex);
        campoAnchoCarril_EditarFlex = (EditText) findViewById(R.id.campoAnchoCarril_EditarFlex);
        campoAnchoBerma_EditarFlex = (EditText) findViewById(R.id.campoAnchoBerma_EditarFlex);
        campoPRI_EditarFlex = (EditText) findViewById(R.id.campoPRI_EditarFlex);
        campoPRF_EditarFlex = (EditText) findViewById(R.id.campoPRF_EditarFlex);
        campoComentarios_EditarFlex = (EditText) findViewById(R.id.campoComentarios_EditarFlex);
        campoFechaSegmentoFlexEditar = (EditText) findViewById(R.id.campoFechaSegmentoFlexEditar);

        tvNombre_Carretera_Segmento_EditarFlex = (TextView) findViewById(R.id.tvNombre_Carretera_Segmento_EditarFlex);


        //Se toman los datos del segmento
        Bundle bundle = getIntent().getExtras();
        String id_segmento = bundle.getString("tv_id_segmento").toString();
        String nom_carretera_seg= bundle.getString("tv_nombre_carretera_segmento").toString();
        String nCalzadas = bundle.getString("tvnCalzadas").toString();
        String nCarriles = bundle.getString("tvnCarriles").toString();
        String anchoCarril = bundle.getString("tvanchoCarril".toString());
        String anchoBerma = bundle.getString("tvanchoBerma".toString());
        String pri = bundle.getString("tvPRI").toString();
        String prf = bundle.getString("tvPRF").toString();
        String comentarios = bundle.getString("tvComentarios").toString();
        String fecha = bundle.getString("tvFechaSegmentoFlex".toString());


        //Se asignan los datos de la carretera a cada EditText
        tv_id_segmento_editarFlex.setText(id_segmento);
        tvNombre_Carretera_Segmento_EditarFlex.setText(nom_carretera_seg);
        campoNCalzadas_EditarFlex.setText(nCalzadas);
        campoNCarriles_EditarFlex.setText(nCarriles);
        campoAnchoCarril_EditarFlex.setText(anchoCarril);
        campoAnchoBerma_EditarFlex.setText(anchoBerma);
        campoPRI_EditarFlex.setText(pri);
        campoPRF_EditarFlex.setText(prf);
        campoComentarios_EditarFlex.setText(comentarios);
        campoFechaSegmentoFlexEditar.setText(fecha);

    }

    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnEditarSegmentoActivityFlex:
                editarSegmento();
                break;
            case R.id.btnEliminarSegmentoFlex:
                eliminarSegmento();
                break;
        }
    }

    private void eliminarSegmento() {
        SQLiteDatabase db=baseDatos.getWritableDatabase();
        String[] parametros={tv_id_segmento_editarFlex.getText().toString()};
        db.delete(Utilidades.SEGMENTOFLEX.TABLA_SEGMENTO,Utilidades.SEGMENTOFLEX.CAMPO_ID_SEGMENTO+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Ya se Eliminó la carretera",Toast.LENGTH_LONG).show();
        tv_id_segmento_editarFlex.setText("");
        Intent intent = new Intent(EditarSegmentoFlexActivity.this,ConsultarSegmentoFlexActivity.class);
        startActivity(intent);
        db.close();
    }

    private void editarSegmento() {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String[] parametros={tv_id_segmento_editarFlex.getText().toString()};

        ContentValues values = new ContentValues();

        values.put(Utilidades.SEGMENTOFLEX.CAMPO_CALZADAS_SEGMENTO , campoNCalzadas_EditarFlex.getText().toString());
        values.put(Utilidades.SEGMENTOFLEX.CAMPO_CARRILES_SEGMENTO  , campoNCarriles_EditarFlex.getText().toString());
        values.put(Utilidades.SEGMENTOFLEX.CAMPO_ANCHO_CARRIL , campoAnchoCarril_EditarFlex.getText().toString());
        values.put(Utilidades.SEGMENTOFLEX.CAMPO_ANCHO_BERMA  , campoAnchoBerma_EditarFlex.getText().toString());
        values.put(Utilidades.SEGMENTOFLEX.CAMPO_PRI_SEGMENTO, campoPRI_EditarFlex.getText().toString());
        values.put(Utilidades.SEGMENTOFLEX.CAMPO_PRF_SEGMENTO, campoPRF_EditarFlex.getText().toString());
        values.put(Utilidades.SEGMENTOFLEX.CAMPO_COMENTARIOS  , campoComentarios_EditarFlex.getText().toString());
        values.put(Utilidades.SEGMENTOFLEX.CAMPO_FECHA, campoFechaSegmentoFlexEditar.getText().toString());


        db.update(Utilidades.SEGMENTOFLEX.TABLA_SEGMENTO,values,Utilidades.SEGMENTOFLEX.CAMPO_ID_SEGMENTO+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Se edito el segmento",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EditarSegmentoFlexActivity.this,ConsultarSegmentoFlexActivity.class);
        intent.putExtra("tv_id_segmento",tv_id_segmento_editarFlex.getText().toString());
        intent.putExtra("nom_carretera",tvNombre_Carretera_Segmento_EditarFlex.getText().toString());
        startActivity(intent);
        db.close();

    }


}
