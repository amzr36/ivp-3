package com.alejo_zr.exceldb.Pato.rigi;

import android.Manifest;
import android.content.ContentValues;
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

public class EditarPatologiaRigiActivity extends AppCompatActivity {

    private final String CARPETA_RAIZ="InventarioVial/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"PavimentoRigido";

    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    private BaseDatos baseDatos;
    private Button btnRegistrarPatologia;
    private ImageButton botonCargar;
    private ImageView imagen;
    private String path,seve;
    private String idFotoRigi,campoSeveridad,campoIS;

    private MaterialSpinner spinnerPatoRigi,spinnerSeveridadPatoRigiRegistro;
    private TextView tv_idDanio,tv_nombre_carretera_patologia,tv_id_segmento_patologia_Rigi,tv_foto_danio,tv_direccionFoto,tv_foto_nombre,ej_Pato_Rigi;
    private TextInputLayout input_campoAbscisaRigi,input_campoDanioPatoRigi,input_campoLargoDanio,input_campoAnchoDanio,input_campoSeveridad,
            input_campoidFotoRigi;
    private EditText campoNumeroLosa,campoLetraLosa,campoLargoLosa,campoAnchoLosa, campoDanioPatoRigi, campoLargoDanio,
            campoAnchoDanio, campoLargoRepa, campoAnchoRepa, campoAclaracion,campoAbscisaRigi,campoLatitudPatoRigi,campoLongitudPatoRigi;
    private String[] tipoDanioRigi = { "Grieta de esquina", "Grieta longitudinal",
            "Grieta Transversal", "Grieta en los extremos de los pasadores", "Grieta en bloque", "Grieta en pozos y sumideros", "separacion de juntas longitudinales",
            "Deterioro de sello", "Desportillamiento de juntas", "Descascaramiento", "Desintegracion", "Baches", "Pulimiento", "Escalonamiento de juntas longitudinales y transversale",
            "Levantamiento localizado", "Parche", "Hundimineto O Asentamiento", "Fisuracion por retraccion", "Fisuras ligeras de aparicion temprana", "Fisuracion por durabilidad", "Bombeo sobre la junta transversal/longitudinal", "Surcos",
            "Ondulaciones", "Descenso de la berma", "Separacion entre berma y pavimento"};
    private String[] severidad = {"Alta", "Media", "Baja", "No aplica"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_patologia_rigi);

        baseDatos = new BaseDatos(this);

        imagen= (ImageView) findViewById(R.id.imagenIdPatoRigiEditar);

        btnRegistrarPatologia= (Button) findViewById(R.id.btnRegistroPatologia);

        spinnerSeveridadPatoRigiRegistro = (MaterialSpinner) findViewById(R.id.spinnerSeveridadPatoRigiEditar);
        spinnerPatoRigi = (MaterialSpinner) findViewById(R.id.spinnerPatoRigiEditar);
        tv_idDanio = (TextView) findViewById(R.id.tv_id_danio_pato_rigi_editar);
        campoAbscisaRigi = (EditText) findViewById(R.id.campoAbscisaRigiEditar);
        campoLatitudPatoRigi = (EditText) findViewById(R.id.campoLatitudPatoRigiEditar);
        campoLongitudPatoRigi = (EditText) findViewById(R.id.campolongitudPatoRigiEditar);
        campoNumeroLosa = (EditText)findViewById(R.id.campoNumeroLosaEditar);
        campoLetraLosa= (EditText)findViewById(R.id.campoLetraLosaEditar);
        campoLargoLosa= (EditText)findViewById(R.id.campoLargoLosaRigiEditar);
        campoAnchoLosa= (EditText)findViewById(R.id.campoAnchoLosaRigiEditar);
        campoDanioPatoRigi = (EditText) findViewById(R.id.campoDanioPatoRigiEditar);
        campoLargoDanio = (EditText) findViewById(R.id.campoLargoDanioRigiEditar);
        campoAnchoDanio = (EditText) findViewById(R.id.campoAnchoDanioRigiEditar);
        campoLargoRepa = (EditText) findViewById(R.id.campoLargoRepaRigiEditar);
        campoAnchoRepa = (EditText) findViewById(R.id.campoAnchoRepaRigiEditar);
        campoAclaracion = (EditText) findViewById(R.id.campoAclaracionesRigiEditar);
        tv_nombre_carretera_patologia = (TextView) findViewById(R.id.tv_nombre_carretera_patologia_RigiEditar);
        tv_id_segmento_patologia_Rigi = (TextView) findViewById(R.id.tv_id_segmento_patologia_Rigi_Editar);
        tv_foto_danio = (TextView) findViewById(R.id.tv_idFotoRigiEditar);
        tv_direccionFoto = (TextView) findViewById(R.id.tv_direccionfoto_danioRigiEditar);
        tv_foto_nombre = (TextView) findViewById(R.id.tv_foto_nombreRigiEditar);
        ej_Pato_Rigi = (TextView) findViewById(R.id.ej_Pato_RigiEditar);
        input_campoAbscisaRigi = (TextInputLayout) findViewById(R.id.input_campoAbscisaRigiEditar);
        input_campoDanioPatoRigi = (TextInputLayout) findViewById(R.id.input_campoDanioPatoRigiEditar);
        input_campoLargoDanio = (TextInputLayout) findViewById(R.id.input_campoLargoDanioRigiEditar);
        input_campoAnchoDanio = (TextInputLayout) findViewById(R.id.input_campoAnchoDanioRigiEditar);
        input_campoSeveridad = (TextInputLayout) findViewById(R.id.input_campoSeveridadRigiEditar);
        input_campoidFotoRigi = (TextInputLayout) findViewById(R.id.input_campoidFotoRigiEditar);
        tv_foto_danio.setText("1");
        Bundle bundle = getIntent().getExtras();
        String abscisa = bundle.getString("tvAbscisa");
        String latitud = bundle.getString("tvLatitud");
        String longitud= bundle.getString("tvLongitud");
        String numero= bundle.getString("tvNumeroLosa");
        String letra = bundle.getString("tvLetraLosa");
        String largoLosa = bundle.getString("tvLargoLosa");
        String anchoLosa = bundle.getString("tvAnchoLosa");
        String danio= bundle.getString("tvdanionombre");
        String seve= bundle.getString("tvSeveridadPatoFlexActivity");
        String larDanio= bundle.getString("tvlarDanio");
        String anchoDanio= bundle.getString("tvanchDanio");
        String anchoRepa= bundle.getString("tvanchRepa");
        String larRepa= bundle.getString("tvlarRepa");
        String aclaraciones= bundle.getString("tvAclaraciones");
        String direccion= bundle.getString("tvDireccionPatoRigi");
        String nombreFoto= bundle.getString("tvNombreFoto_patoRigiActivity");
        String idSeg= bundle.getString("tvIdSegmento");
        String nomCarre= bundle.getString("tvNombreCarreteraPatologiaActivity");
        String idDaño= bundle.getString("tvIdDaño");

        campoAbscisaRigi.setText(abscisa);
        campoLatitudPatoRigi.setText(latitud);
        campoLongitudPatoRigi.setText(longitud);
        campoNumeroLosa.setText(numero);
        campoLetraLosa.setText(letra);
        campoLargoLosa.setText(largoLosa);
        campoAnchoLosa.setText(anchoLosa);
        campoDanioPatoRigi.setText(danio);
        campoLargoDanio.setText(larDanio);
        campoAnchoDanio.setText(anchoDanio);
        campoLargoRepa.setText(larRepa);
        campoAnchoRepa.setText(anchoRepa);
        campoAclaracion.setText(aclaraciones);
        tv_nombre_carretera_patologia.setText(nomCarre);
        tv_id_segmento_patologia_Rigi.setText(idSeg);
        tv_direccionFoto.setText(direccion);
        tv_idDanio.setText(idDaño);
        tv_foto_nombre.setText(nombreFoto);
        //tv_foto_danio.setText("1");

        path = tv_direccionFoto.getText().toString();


        ArrayAdapter<String> arrayAdapterPato = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, tipoDanioRigi);
        spinnerPatoRigi.setAdapter(arrayAdapterPato);
        ArrayAdapter<String> arrayAdapterSeveridad = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, severidad);
        spinnerSeveridadPatoRigiRegistro.setAdapter(arrayAdapterSeveridad);

        spinnerSeveridadPatoRigiRegistro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        spinnerPatoRigi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){

                    case 0:
                        campoDanioPatoRigi.setText(R.string.g_e);
                        break;
                    case 1:
                        campoDanioPatoRigi.setText(R.string.g_l);
                        break;
                    case 2:
                        campoDanioPatoRigi.setText(R.string.g_t);
                        break;
                    case 3:
                        campoDanioPatoRigi.setText(R.string.g_p);
                        break;
                    case 4:
                        campoDanioPatoRigi.setText(R.string.g_b);
                        break;
                    case 5:
                        campoDanioPatoRigi.setText(R.string.g_a);
                        break;
                    case 6:
                        campoDanioPatoRigi.setText(R.string.s_j);
                        break;
                    case 7:
                        campoDanioPatoRigi.setText(R.string.dst_dsl);
                        break;
                    case 8:
                        campoDanioPatoRigi.setText(R.string.dpt_dpl);
                        break;
                    case 9:
                        campoDanioPatoRigi.setText(R.string.de);
                        break;
                    case 10:
                        campoDanioPatoRigi.setText(R.string.di);
                        break;
                    case 11:
                        campoDanioPatoRigi.setText(R.string.bch);
                        break;
                    case 12:
                        campoDanioPatoRigi.setText(R.string.pu);
                        break;
                    case 13:
                        campoDanioPatoRigi.setText(R.string.ejl_ejt);
                        break;
                    case 14:
                        campoDanioPatoRigi.setText(R.string.let_lel);
                        break;
                    case 15:
                        campoDanioPatoRigi.setText(R.string.pcha);
                        break;
                    case 16:
                        campoDanioPatoRigi.setText(R.string.hu);
                        break;
                    case 17:
                        campoDanioPatoRigi.setText(R.string.fr);
                        break;
                    case 18:
                        campoDanioPatoRigi.setText(R.string.ft);
                        break;
                    case 19:
                        campoDanioPatoRigi.setText(R.string.fd);
                        break;
                    case 20:
                        campoDanioPatoRigi.setText(R.string.bot_bol);
                        break;
                    case 21:
                        campoDanioPatoRigi.setText(R.string.on);
                        break;
                    case 22:
                        campoDanioPatoRigi.setText(R.string.db);
                        break;
                    case 23:
                        campoDanioPatoRigi.setText(R.string.sb);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
        int alto=300;//alto en pixeles
        int ancho=350;//ancho en pixeles
        bitmap = Bitmap.createScaledBitmap(bitmap,alto,ancho,true);
        imagen.setImageBitmap(bitmap);
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnEditarPatologiaRigiActivity:
                verificarDatosPatoRigi();
                break;
            case R.id.btnFotoRigiEditar:
                guardarFotografia();
                tomarFotografia();
                break;
            case R.id.btnObtenerCoordenadasPatoRigiEditar:
                obtenerCoordenadas();
                break;
            case R.id.btnManualPatoRigiEditar:
                abrirManual();
                break;
            case R.id.ej_Pato_FlexEditar:
                Intent intent = new Intent(EditarPatologiaRigiActivity.this, RegistroPatologiaRigiEjemploActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void abrirManual() {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.alejo_zr.manual");
        startActivity(launchIntent);
    }

    private void verificarDatosPatoRigi() {
        boolean isValid = true;
        if(campoAbscisaRigi.getText().toString().trim().isEmpty()){
            input_campoAbscisaRigi.setError("Ingrese la abscisa");
            isValid=false;
        }else{
            input_campoAbscisaRigi.setErrorEnabled(false);
        }

        if(campoDanioPatoRigi.getText().toString().trim().isEmpty()){
            input_campoDanioPatoRigi.setError("Seleccion el daño");
            isValid=false;
        }else{
            input_campoDanioPatoRigi.setErrorEnabled(false);
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
        if(tv_direccionFoto.getText().toString().trim().isEmpty()){
            input_campoidFotoRigi.setError("Tome la foto del daño");
            isValid=false;
        }else{
            input_campoidFotoRigi.setErrorEnabled(false);
        }

        if(isValid){
            editarPatoRigi();
        }

    }

    private void editarPatoRigi() {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String[] parametros={tv_idDanio.getText().toString()};

        ContentValues values = new ContentValues();

        values.put(Utilidades.PATOLOGIARIGI.CAMPO_ABSCISA_PATOLOGIA , campoAbscisaRigi.getText().toString());
        values.put(Utilidades.PATOLOGIARIGI.CAMPO_LATITUD  , campoLatitudPatoRigi.getText().toString());
        values.put(Utilidades.PATOLOGIARIGI.CAMPO_LONGITUD, campoLongitudPatoRigi.getText().toString());
        values.put(Utilidades.PATOLOGIARIGI.CAMPO_NUMERO_LOSA  , campoNumeroLosa.getText().toString());
        values.put(Utilidades.PATOLOGIARIGI.CAMPO_LETRA_LOSA , campoLetraLosa.getText().toString());
        values.put(Utilidades.PATOLOGIARIGI.CAMPO_LARGO_LOSA  , campoLargoLosa.getText().toString());
        values.put(Utilidades.PATOLOGIARIGI.CAMPO_ANCHO_LOSA  , campoAnchoLosa.getText().toString());
        values.put(Utilidades.PATOLOGIARIGI.CAMPO_DANIO_PATOLOGIA, campoDanioPatoRigi.getText().toString());
        values.put(Utilidades.PATOLOGIARIGI.CAMPO_SEVERIDAD, campoSeveridad);
        values.put(Utilidades.PATOLOGIARIGI.CAMPO_LARGO_PATOLOGIA  , campoLargoDanio.getText().toString());
        values.put(Utilidades.PATOLOGIARIGI.CAMPO_ANCHO_PATOLOGIA, campoAnchoDanio.getText().toString());
        values.put(Utilidades.PATOLOGIARIGI.CAMPO_LARGO_REPARACION, campoLargoRepa.getText().toString());
        values.put(Utilidades.PATOLOGIARIGI.CAMPO_ANCHO_REPARACION, campoAnchoRepa.getText().toString());
        values.put(Utilidades.PATOLOGIARIGI.CAMPO_ACLARACIONES, campoAclaracion.getText().toString());
        values.put(Utilidades.PATOLOGIARIGI.CAMPO_NOMBRE_FOTO, tv_foto_nombre.getText().toString());
        values.put(Utilidades.PATOLOGIARIGI.CAMPO_FOTO_DANIO, tv_direccionFoto.getText().toString());

        db.update(Utilidades.PATOLOGIARIGI.TABLA_PATOLOGIA,values,Utilidades.PATOLOGIARIGI.CAMPO_ID_PATOLOGIA+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Se edito el segmento",Toast.LENGTH_SHORT).show();

    }
    private void guardarFotografia() {

        BaseDatos bd=new BaseDatos(this);

        SQLiteDatabase db=bd.getWritableDatabase();

        String insert="INSERT INTO "+ Utilidades.FOTORIGI.TABLA_FOTORIGI
                +" ( " +Utilidades.FOTORIGI.CAMPO_NOMBRE_CARRETERA_FOTORIGI+","+Utilidades.FOTORIGI.CAMPO_ID_SEGMENTO_FOTORIGI+")" +
                " VALUES ('"+tv_nombre_carretera_patologia.getText().toString()+"' , '"+tv_id_segmento_patologia_Rigi.getText().toString()+"')";

        db.execSQL(insert);

        BaseDatos baseDatos = new BaseDatos(this);
        final Cursor cursor = baseDatos.getfotoRigi();

        if(cursor.moveToNext()){
            do{
                idFotoRigi = cursor.getString(cursor.getColumnIndex(Utilidades.FOTORIGI.CAMPO_ID_FOTORIGI));
            }while (cursor.moveToNext());
            tv_foto_danio.setText(idFotoRigi);
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
            nombreImagen=("Carretera_"+tv_nombre_carretera_patologia.getText().toString()+"_Segmento_"+tv_id_segmento_patologia_Rigi.getText().toString()+"_Foto_"+tv_foto_danio.getText().toString()+".png");
        }

        tv_foto_nombre.setText(nombreImagen);
        path=Environment.getExternalStorageDirectory()+
                File.separator+RUTA_IMAGEN+File.separator+nombreImagen;

        tv_direccionFoto.setText(path);


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
                    obtenerFotoPatoRigi();
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
        AlertDialog.Builder dialogo=new AlertDialog.Builder(EditarPatologiaRigiActivity.this);
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
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(EditarPatologiaRigiActivity.this);
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
        Local.setEditarPatologiaRigiActivity(this);
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
        EditarPatologiaRigiActivity editarPatologiaRigiActivity;

        public EditarPatologiaRigiActivity getEditarPatologiaRigiActivity() {
            return editarPatologiaRigiActivity;
        }

        public void setEditarPatologiaRigiActivity(EditarPatologiaRigiActivity editarPatologiaRigiActivity) {
            this.editarPatologiaRigiActivity = editarPatologiaRigiActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();

            String latitud = ""+ loc.getLatitude();// + "\n Long = " + loc.getLongitude();
            String longitud = ""+loc.getLongitude();
            campoLatitudPatoRigi.setText(latitud);
            campoLongitudPatoRigi.setText(longitud);

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
