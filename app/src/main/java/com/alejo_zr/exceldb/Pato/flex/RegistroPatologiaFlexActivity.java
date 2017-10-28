package com.alejo_zr.exceldb.Pato.flex;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alejo_zr.exceldb.BaseDatos;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.utilidades.Utilidades;

import java.io.File;

import fr.ganfra.materialspinner.MaterialSpinner;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RegistroPatologiaFlexActivity extends AppCompatActivity {

    private final String CARPETA_RAIZ="InventarioVial/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"PavimentoFlexible";

    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    private Button btnRegistrarPatologia;
    private ImageButton botonCargar;
    private ImageView imagen;
    private String path;
    private String idFotoFlex,campoSeveridad,campoIS;

    private MaterialSpinner spinnerPatoFlex,spinnerSeveridadPatoFlexRegistro;
    private TextView tv_nombre_carretera_patologia,tv_id_segmento_patologia_flex,tv_foto_danio,tv_idFotoFlex,tv_foto_nombre,ej_Pato_Flex;
    private TextInputLayout input_campoAbscisaFlex,input_campoCarrilPato,input_campoDanioPato,input_campoLargoDanio,input_campoAnchoDanio,input_campoSeveridad,
            input_campoidFotoFlex;
    private EditText campoCarrilPato, campoDanioPato, campoLargoDanio, campoAnchoDanio, campoLargoRepa, campoAnchoRepa, campoAclaracion,campoAbscisaFlex,
            campoLatitudPatoFlex,campoLongitudPatoFlex;
    private String[] tipoDanio = {"Seleccione el tipo de Daño", "Fisuras longitudinales y transversales", "Fisura longitudinal en junta de construcción",
            "Fisuras por reflexión de juntas o grietas en placas de concreto", "Fisuras en medialuna", "Fisuras de borde", "Fisuras en bloque", "Piel de cocotrilo",
            "Fisuración por desplazamiento de capas", "Fisuración incipiente", "Ondulación", "Abultamiento", "Hundimiento", "Ahuellamiento", "Descascaramiento",
            "Baches", "Parche", "Desgaste superficial", "Perdida de agregado", "Pulimento del agregado", "Cabezas duras", "Exudación", "Surcos",
            "Corrimiento vertical de la berma", "Separación de la berma", "Afloramiento de finos", "Afloramiento de agua"};
    private String[] severidad = {"Alta", "Media", "Baja", "No aplica"};
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_patologia_flex);

        botonCargar= (ImageButton) findViewById(R.id.btnDanio);
        if(validaPermisos()){
            botonCargar.setEnabled(true);
        }else{
            botonCargar.setEnabled(false);
        }
        imagen= (ImageView) findViewById(R.id.imagemId);

        btnRegistrarPatologia= (Button) findViewById(R.id.btnRegistroPatologia);

        spinnerSeveridadPatoFlexRegistro = (MaterialSpinner) findViewById(R.id.spinnerSeveridadPatoFlexRegistro);
        spinnerPatoFlex = (MaterialSpinner) findViewById(R.id.spinnerPatoFlex);
        campoCarrilPato = (EditText) findViewById(R.id.campoCarrilPatoFlex);
        campoDanioPato = (EditText) findViewById(R.id.campoDanioPatoFlex);
        campoLargoDanio = (EditText) findViewById(R.id.campoLargoDanioFlex);
        campoAnchoDanio = (EditText) findViewById(R.id.campoAnchoDanioFlex);
        campoLargoRepa = (EditText) findViewById(R.id.campoLargoRepaFlex);
        campoAnchoRepa = (EditText) findViewById(R.id.campoAnchoRepaFlex);
        campoAclaracion = (EditText) findViewById(R.id.campoAclaracionesFlex);
        campoAbscisaFlex = (EditText) findViewById(R.id.campoAbscisaFlex);
        campoLatitudPatoFlex = (EditText) findViewById(R.id.campoLatitudPatoFlex);
        campoLongitudPatoFlex = (EditText) findViewById(R.id.campolongitudPatoFlex);
        tv_nombre_carretera_patologia = (TextView) findViewById(R.id.tv_nombre_carretera_patologia_flex);
        tv_id_segmento_patologia_flex = (TextView) findViewById(R.id.tv_id_segmento_patologia_flex_registro);
        tv_foto_danio = (TextView) findViewById(R.id.tv_foto_danio);
        tv_idFotoFlex = (TextView) findViewById(R.id.tv_idFotoFlex);
        tv_foto_nombre = (TextView) findViewById(R.id.tv_foto_nombre);
            ej_Pato_Flex = (TextView) findViewById(R.id.ej_Pato_Flex);
        input_campoAbscisaFlex = (TextInputLayout) findViewById(R.id.input_campoAbscisaFlex);
        input_campoCarrilPato = (TextInputLayout) findViewById(R.id.input_campoCarrilPatoFlex);
        input_campoDanioPato = (TextInputLayout) findViewById(R.id.input_campoDanioPatoFlex);
        input_campoLargoDanio = (TextInputLayout) findViewById(R.id.input_campoLargoDanioFlex);
        input_campoAnchoDanio = (TextInputLayout) findViewById(R.id.input_campoAnchoDanioFlex);
        input_campoSeveridad = (TextInputLayout) findViewById(R.id.input_campoSeveridadFlex);
        input_campoidFotoFlex = (TextInputLayout) findViewById(R.id.input_campoidFotoFlex);

        Bundle bundle = getIntent().getExtras();
        String dato_nom_carretera = bundle.getString("nom_carretera_segmento");
        String id_segmento = bundle.getString("id_segmento");
        String dato_is= bundle.getString("campoIS".toString());
        tv_nombre_carretera_patologia.setText(dato_nom_carretera);
        tv_id_segmento_patologia_flex.setText(id_segmento);
        campoIS = dato_is;


        ArrayAdapter<String> arrayAdapterPato = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, tipoDanio);
        spinnerPatoFlex.setAdapter(arrayAdapterPato);
        ArrayAdapter<String> arrayAdapterSeveridad = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, severidad);
        spinnerSeveridadPatoFlexRegistro.setAdapter(arrayAdapterSeveridad);

            spinnerSeveridadPatoFlexRegistro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    switch (position) {


                        case 0:
                            campoSeveridad = "A";
                            break;
                        case 1:
                            campoSeveridad = "M";
                            break;
                        case 2:
                            campoSeveridad = "B";
                            break;
                        case 3:
                            campoSeveridad="N.A";
                            break;
                        default:
                            campoSeveridad="";
                            break;
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        spinnerPatoFlex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){

                    case 0:
                        //No se ha seleccionado el Spinner
                        break;
                    case 1:
                        campoDanioPato.setText(R.string.fisuras_fl_lt);
                        break;
                    case 2:
                        campoDanioPato.setText(R.string.fisura_fcl);
                        break;
                    case 3:
                        campoDanioPato.setText(R.string.fisura_fjl);
                        break;
                    case 4:
                        campoDanioPato.setText(R.string.fisura_fml);
                        break;
                    case 5:
                        campoDanioPato.setText(R.string.fbd);
                        break;
                    case 6:
                        campoDanioPato.setText(R.string.fisura_fb);
                        break;
                    case 7:
                        campoDanioPato.setText(R.string.pc);
                        break;
                    case 8:
                        campoDanioPato.setText(R.string.fdc);
                        break;
                    case 9:
                        campoDanioPato.setText(R.string.fin);
                        break;
                    case 10:
                        campoDanioPato.setText(R.string.ond);
                        break;
                    case 11:
                        campoDanioPato.setText(R.string.ab);
                        break;
                    case 12:
                        campoDanioPato.setText(R.string.hun);
                        break;
                    case 13:
                        campoDanioPato.setText(R.string.ahu);
                        break;
                    case 14:
                        campoDanioPato.setText(R.string.dcf);
                        break;
                    case 15:
                        campoDanioPato.setText(R.string.bchf);
                        break;
                    case 16:
                        campoDanioPato.setText(R.string.pch);
                        break;
                    case 17:
                        campoDanioPato.setText(R.string.dsu);
                        break;
                    case 18:
                        campoDanioPato.setText(R.string.pa);
                        break;
                    case 19:
                        campoDanioPato.setText(R.string.puf);
                        break;
                    case 20:
                        campoDanioPato.setText(R.string.cd);
                        break;
                    case 21:
                        campoDanioPato.setText(R.string.ex);
                        break;
                    case 22:
                        campoDanioPato.setText(R.string.su);
                        break;
                    case 23:
                        campoDanioPato.setText(R.string.cvb);
                        break;
                    case 24:
                        campoDanioPato.setText(R.string.sbf);
                        break;
                    case 25:
                        campoDanioPato.setText(R.string.afi);
                        break;
                    case 26:
                        campoDanioPato.setText(R.string.afa);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnRegistroPatologia:
                verificarDatosPatoFlex();
                break;
            case R.id.btnDanio:
                guardarFotografia();
                tomarFotografia();
                break;
            case R.id.btnObtenerCoordenadasPatoFlex:
                obtenerCoordenadas();
                break;
            case R.id.btnManualPatoFlex:
                abrirManual();
                break;
            case R.id.ej_Pato_Flex:
                Intent intent = new Intent(RegistroPatologiaFlexActivity.this, RegistroPatologiaFlexEjemploActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void abrirManual() {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.alejo_zr.manual");
        startActivity(launchIntent);
    }

    private void verificarDatosPatoFlex() {
        boolean isValid = true;
        if(campoAbscisaFlex.getText().toString().trim().isEmpty()){
            input_campoAbscisaFlex.setError("Ingrese la abscisa");
            isValid=false;
        }else{
            input_campoAbscisaFlex.setErrorEnabled(false);
        }
        if(campoCarrilPato.getText().toString().trim().isEmpty()){
            input_campoCarrilPato.setError("Ingrese el caril");
            isValid=false;
        }else{
            input_campoCarrilPato.setErrorEnabled(false);
        }
        if(campoDanioPato.getText().toString().trim().isEmpty()){
            input_campoDanioPato.setError("Seleccion el daño");
            isValid=false;
        }else{
            input_campoDanioPato.setErrorEnabled(false);
        }
        if(campoSeveridad.trim().isEmpty()){
            input_campoSeveridad.setError("Ingrese la severidad");
            isValid=false;
        }else{
            input_campoSeveridad.setErrorEnabled(false);
        }
        if(campoLargoDanio.getText().toString().trim().isEmpty()){
            input_campoLargoDanio.setError("Ingrese el largo del daño");
            isValid=false;
        }else{
            input_campoLargoDanio.setErrorEnabled(false);
        }
        if(tv_idFotoFlex.getText().toString().trim().isEmpty()){
            input_campoidFotoFlex.setError("Tome la foto del daño");
            isValid=false;
        }else{
            input_campoidFotoFlex.setErrorEnabled(false);
        }

        if(isValid){
            registrarPatoFlex();
        }

    }

    private void registrarPatoFlex() {

        BaseDatos bd=new BaseDatos(this);

        SQLiteDatabase db=bd.getWritableDatabase();

        String insert="INSERT INTO "+ Utilidades.PATOLOGIAFLEX.TABLA_PATOLOGIA
                +" ( " +Utilidades.PATOLOGIAFLEX.CAMPO_NOMBRE_CARRETERA_PATOLOGIA+","+Utilidades.PATOLOGIAFLEX.CAMPO_ID_SEGMENTO_PATOLOGIA+","+
                Utilidades.PATOLOGIAFLEX.CAMPO_ABSCISA_PATOLOGIA+","+Utilidades.PATOLOGIAFLEX.CAMPO_LATITUD+","+Utilidades.PATOLOGIAFLEX.CAMPO_LONGITUD+","
                +Utilidades.PATOLOGIAFLEX.CAMPO_CARRIL_PATOLOGIA+","+Utilidades.PATOLOGIAFLEX.CAMPO_DANIO_PATOLOGIA+","+Utilidades.PATOLOGIAFLEX.CAMPO_SEVERIDAD+","
                +Utilidades.PATOLOGIAFLEX.CAMPO_LARGO_PATOLOGIA+","+Utilidades.PATOLOGIAFLEX.CAMPO_ANCHO_PATOLOGIA+","+Utilidades.PATOLOGIAFLEX.CAMPO_LARGO_REPARACION
                +"," +Utilidades.PATOLOGIAFLEX.CAMPO_ANCHO_REPARACION+","+Utilidades.PATOLOGIAFLEX.CAMPO_ACLARACIONES+","+Utilidades.PATOLOGIAFLEX.CAMPO_NOMBRE_FOTO+","
                +Utilidades.PATOLOGIAFLEX.CAMPO_FOTO_DANIO+","+Utilidades.PATOLOGIAFLEX.CAMPO_IS+")" +
                " VALUES ('"+tv_nombre_carretera_patologia.getText().toString()+"' , '"+tv_id_segmento_patologia_flex.getText().toString()+"' , '"+
                campoAbscisaFlex.getText().toString()+"' , '"+campoLatitudPatoFlex.getText().toString()+"' , '"+campoLongitudPatoFlex.getText().toString()+"' , '"
                +campoCarrilPato.getText().toString()+"' , '"+campoDanioPato.getText().toString()+"' , '"+campoSeveridad+"' , '"
                +campoLargoDanio.getText().toString()+"' , '"+campoAnchoDanio.getText().toString()+"' , '"+campoLargoRepa.getText().toString()+"' , '"
                +campoAnchoRepa.getText().toString()+"' , '"+campoAclaracion.getText().toString()+"' , '"+tv_foto_nombre.getText().toString()+"' , '"
                +tv_foto_danio.getText().toString()+"' , '"+campoIS+"')";

        Toast.makeText(getApplicationContext(),"Se registro el Daño: "+campoDanioPato.getText().toString(),Toast.LENGTH_SHORT).show();
        db.execSQL(insert);

        db.close();

    }

    private void cargarImagen() {

        final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(RegistroPatologiaFlexActivity.this);
        alertOpciones.setTitle("Seleccione una Opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    guardarFotografia();
                    tomarFotografia();

                }else{
                    if (opciones[i].equals("Cargar Imagen")){
                        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),COD_SELECCIONA);
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();

    }

    private void guardarFotografia() {

        BaseDatos bd=new BaseDatos(this);

        SQLiteDatabase db=bd.getWritableDatabase();


        String insert="INSERT INTO "+ Utilidades.FOTOFLEX.TABLA_FOTO
                +" ( " +Utilidades.FOTOFLEX.CAMPO_NOMBRE_CARRETERA_FOTO+","+Utilidades.FOTOFLEX.CAMPO_ID_SEGMENTO_FOTO+")" +
                " VALUES ('"+tv_nombre_carretera_patologia.getText().toString()+"' , '"+tv_id_segmento_patologia_flex.getText().toString()+"')";


        db.execSQL(insert);

        BaseDatos baseDatos = new BaseDatos(this);
        final Cursor cursor = baseDatos.getfotoFLex();

        if(cursor.moveToNext()){
            do{
                idFotoFlex = cursor.getString(cursor.getColumnIndex(Utilidades.FOTOFLEX.CAMPO_ID_FOTO));
            }while (cursor.moveToNext());
            tv_idFotoFlex.setText(idFotoFlex);
        }

        db.close();

    }

    private void tomarFotografia() {

        File fileImagen=new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
        boolean isCreada=fileImagen.exists();
        String nombreImagen="";
        if(isCreada==false){
            isCreada=fileImagen.mkdirs();
        }

        if(isCreada==true){
            nombreImagen=("Carretera_"+tv_nombre_carretera_patologia.getText().toString()+"_Segmento_"+tv_id_segmento_patologia_flex.getText().toString()+"_Foto_"+tv_idFotoFlex.getText().toString()+".png");
        }

        tv_foto_nombre.setText(nombreImagen);
        path=Environment.getExternalStorageDirectory()+
                File.separator+RUTA_IMAGEN+File.separator+nombreImagen;

        tv_foto_danio.setText(path);


        File imagen=new File(path);

        Intent intent=null;
        intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ////
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
        {
            String authorities=getApplicationContext().getPackageName()+".provider";
            Uri imageUri= FileProvider.getUriForFile(this,authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else
        {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent,COD_FOTO);

        ////
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){

            switch (requestCode){
                case COD_SELECCIONA:
                    Uri miPath=data.getData();
                    imagen.setImageURI(miPath);
                    break;

                case COD_FOTO:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Ruta de almacenamiento","Path: "+path);
                                }
                            });

                    Bitmap bitmap= BitmapFactory.decodeFile(path);
                    imagen.setImageBitmap(bitmap);

                    break;
            }


        }
    }

    private boolean validaPermisos() {

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }

        return false;
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(RegistroPatologiaFlexActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(RegistroPatologiaFlexActivity.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }
    private void obtenerCoordenadas() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setRegistroPatologiaFlexActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 50, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 50, (LocationListener) Local);


    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerCoordenadas();
                return;
            }
        }
    }

    private class Localizacion implements LocationListener {
        RegistroPatologiaFlexActivity registroPatologiaFlexActivity;

        public RegistroPatologiaFlexActivity getRegistroPatologiaFlexActivity() {
            return registroPatologiaFlexActivity;
        }

        public void setRegistroPatologiaFlexActivity(RegistroPatologiaFlexActivity registroPatologiaFlexActivity) {
            this.registroPatologiaFlexActivity = registroPatologiaFlexActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();

            String latitud = ""+ loc.getLatitude();// + "\n Long = " + loc.getLongitude();
            String longitud = ""+loc.getLongitude();
            campoLatitudPatoFlex.setText(latitud);
            campoLongitudPatoFlex.setText(longitud);

        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            Toast.makeText(getApplicationContext(),"GPS DESACTIVADO",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            //Este metodo se ejecuta cuando el GPS es activado

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }
}