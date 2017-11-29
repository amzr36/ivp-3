package com.alejo_zr.exceldb.Segmento.Flexible;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alejo_zr.exceldb.BaseDatos;
import com.alejo_zr.exceldb.MainActivity;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.entidades.SegmentoFlex;
import com.alejo_zr.exceldb.utilidades.Utilidades;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RegistroSegmentoFlexActivity extends AppCompatActivity {

    //Se declaran las variables y objetos java
    private BaseDatos baseDatos;
    private ArrayList<SegmentoFlex> listaSegmentosF;
    private EditText campoNCalzadas, campoNCarriles, campoAnchoCarril, campoAnchoBerma, campoKmPriFlex,campoMPriFlex,campoKmPrfFlex,campoMPrfFlex,campoComentarios,campoFecha;
    private TextView tvNombre_Carretera_SegmentoFlex,ej_Segmento_Flex;
    private TextInputLayout input_camponCalzadas,input_campoNCarriles,input_campoAnchoCarril,input_campoAnchoBerma,input_campoPRI;
    private String id_seg_flex,pri,prf;
    private Integer id_segmento;

    private  int dia, mes, ano;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_segmento_flex);

        //Se enlazan los objetos con los views
        baseDatos = new BaseDatos(this);
        campoNCalzadas = (EditText) findViewById(R.id.campoNCalzadasFlex);
        campoNCarriles = (EditText) findViewById(R.id.campoNCarrilesFlex);
        campoAnchoCarril = (EditText) findViewById(R.id.campoAnchoCarrilFlex);
        campoAnchoBerma = (EditText) findViewById(R.id.campoAnchoBermaFlex);

        campoKmPriFlex = (EditText) findViewById(R.id.campoKmPriFlex);
        campoMPriFlex  = (EditText) findViewById(R.id.campoMPriFlex);
        campoKmPrfFlex = (EditText) findViewById(R.id.campoKmPrfFlex);
        campoMPrfFlex  = (EditText) findViewById(R.id.campoMPrfFlex);

        campoComentarios = (EditText) findViewById(R.id.campoComentariosFlex);
        campoFecha = (EditText) findViewById(R.id.campoFechaSegmentoFlexRegistro);

        ej_Segmento_Flex = (TextView) findViewById(R.id.ej_Segmento_Flex);


        tvNombre_Carretera_SegmentoFlex = (TextView) findViewById(R.id.tvNombre_Carretera_SegmentoFlex);

        input_camponCalzadas = (TextInputLayout) findViewById(R.id.input_camponCalzadasFlex);
        input_campoNCarriles= (TextInputLayout) findViewById(R.id.input_campoNCarrilesFlex );
        input_campoAnchoCarril= (TextInputLayout) findViewById(R.id.input_campoAnchoCarrilFlex );
        input_campoAnchoBerma= (TextInputLayout) findViewById(R.id.input_campoAnchoBermaFlex);
        input_campoPRI= (TextInputLayout) findViewById(R.id.input_campoPRIFlex);

        //Se recibe el nombre de la carretera
        Bundle bundle = getIntent().getExtras();
        String dato_nom = bundle.getString("nom_carretera").toString();
        tvNombre_Carretera_SegmentoFlex.setText(dato_nom);
        
        fechaActual();
        //cargarSegmentosFlex();


    }

    private void fechaActual() {
        //Obtiene la fecha del sistema
        final Calendar c = Calendar.getInstance();
        ano = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH);
        dia = c.get(Calendar.DAY_OF_MONTH);

        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        String s = formatter.format(c.getTime());
        campoFecha.setText(s);
    }

    public void onClick(View view) {
        //Al oprimir un boton entra a este metodo, y dependiendo del selecionado se selecciona el caso
        Intent intent ;
        switch (view.getId()){

            case R.id.btnRegistroSegmentoFlex:
                //Se verifica que los campos requeridos para realizar el registro esten diligenciados,
                // si esto su cumple se abre la actividad SegmentoFlex, con los datos del semento
                verificarDatos();
                break;
            case R.id.btnFecha:
                //En caso de que el usuario quiera modificar la fecha proporcinada por el sistema, este la puede modificar
                obtenerFecha();
                break;
            case R.id.btnManualRegistroSegFlex:
                //Abre el MIVP
                try{
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.alejo_zr.manual");
                    startActivity(launchIntent);
                    break;
                }catch (Exception e){
                    //De no tenerse instalado el manual mostrara el mensaje
                    Toast.makeText(getApplicationContext(),"INSTALE EL MANUAL PARA LA INSPECCIÓN VISUAL DE PAVIMENTOS",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.ej_Segmento_Flex:
                //Abre la actividad RegistroSegmentoFlexEjemplo
                intent = new Intent(RegistroSegmentoFlexActivity.this,RegistroSegmentoFlexEjemploActivity.class);
                startActivity(intent);
            case R.id.backRegisSegFlexActivity:
                //Se devuelve a la actividad ConsultarSegmentoFlex
                intent = new Intent(RegistroSegmentoFlexActivity.this,ConsultarSegmentoFlexActivity.class);
                intent.putExtra("nom_carretera",tvNombre_Carretera_SegmentoFlex.getText().toString());
                startActivity(intent);
                break;
            case R.id.homeRegisSegFlexActivity:
                intent = new Intent(RegistroSegmentoFlexActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }


    }

    private void obtenerFecha() {
        final Calendar c= Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,android.R.style.Theme_Holo_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                campoFecha.setText(dayOfMonth + "/" + (monthOfYear) + "/" + year);
            }
        }, dia, mes, ano);
        datePickerDialog.show();
    }



    private void verificarDatos() {

        boolean isValid = true;
        if(campoNCalzadas.getText().toString().trim().isEmpty()){
            input_camponCalzadas.setError("Ingrese el número de calzadas");
            isValid=false;
        }else{
            input_camponCalzadas.setErrorEnabled(false);
        }
        if(campoNCarriles.getText().toString().trim().isEmpty()){
            input_campoNCarriles.setError("Ingrese el número de carriles");
            isValid=false;
        }else{
            input_campoNCarriles.setErrorEnabled(false);
        }
        if(campoAnchoCarril.getText().toString().trim().isEmpty()){
            input_campoAnchoCarril.setError("Ingrese el ancho de el carril");
            isValid=false;
        }else{
            input_campoAnchoCarril.setErrorEnabled(false);
        }
        if(campoAnchoBerma.getText().toString().trim().isEmpty()){
            input_campoAnchoBerma.setError("Ingrese el ancho de la berma");
            isValid=false;
        }else{
            input_campoAnchoBerma.setErrorEnabled(false);
        }
        if(campoMPriFlex.getText().toString().trim().isEmpty()){
            input_campoPRI.setError("Ingrese el PRI");
            isValid=false;
        }else{
            input_campoPRI.setErrorEnabled(false);
        }

        if(isValid){
            registrarSegmento();
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

    private void registrarSegmento() {

        pri = "K"+campoKmPriFlex.getText().toString()+"+"+campoMPriFlex.getText().toString();
        prf = "K"+campoKmPrfFlex.getText().toString()+"+"+campoMPrfFlex.getText().toString();

        BaseDatos bd=new BaseDatos(this);

        SQLiteDatabase db=bd.getWritableDatabase();

        String insert="INSERT INTO "+ Utilidades.SEGMENTOFLEX.TABLA_SEGMENTO
                +" ( "+Utilidades.SEGMENTOFLEX.CAMPO_NOMBRE_CARRETERA_SEGMENTO+","+Utilidades.SEGMENTOFLEX.CAMPO_CALZADAS_SEGMENTO+","
                +Utilidades.SEGMENTOFLEX.CAMPO_CARRILES_SEGMENTO+","+Utilidades.SEGMENTOFLEX.CAMPO_ANCHO_CARRIL+","+Utilidades.SEGMENTOFLEX.CAMPO_ANCHO_BERMA+","+Utilidades.SEGMENTOFLEX.CAMPO_PRI_SEGMENTO+","
                +Utilidades.SEGMENTOFLEX.CAMPO_PRF_SEGMENTO+","+Utilidades.SEGMENTOFLEX.CAMPO_COMENTARIOS+","+Utilidades.SEGMENTOFLEX.CAMPO_FECHA+")" +
                " VALUES ('"+tvNombre_Carretera_SegmentoFlex.getText().toString()+"' , '"
                +campoNCalzadas.getText().toString()+"' , '"+campoNCarriles.getText().toString()+"' , '"+campoAnchoCarril.getText().toString()+"' , '"
                +campoAnchoBerma.getText().toString()+"' , '"+pri+"' , '"+prf+"' , '"
                +campoComentarios.getText().toString()+"' , '"+campoFecha.getText().toString()+"')";

        db.execSQL(insert);
        Toast.makeText(getApplicationContext(),R.string.segRegis,Toast.LENGTH_SHORT).show();

        BaseDatos baseDatos = new BaseDatos(this);
        final Cursor cursor = baseDatos.getSegmentoFlex();

        if(cursor.moveToNext()){
            do{
                id_seg_flex = cursor.getString(cursor.getColumnIndex(Utilidades.SEGMENTOFLEX.CAMPO_ID_SEGMENTO));
            }while (cursor.moveToNext());

        }
        db.close();
        Intent intent = new Intent(RegistroSegmentoFlexActivity.this,SegmentoFlexActivity.class);
        intent.putExtra("id_segmento",id_seg_flex);
        startActivity(intent);

    }
}
