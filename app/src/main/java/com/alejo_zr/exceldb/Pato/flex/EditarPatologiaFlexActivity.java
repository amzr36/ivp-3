package com.alejo_zr.exceldb.Pato.flex;

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

public class EditarPatologiaFlexActivity extends AppCompatActivity {

    //Se declaran las variables y objetos java
    private final String CARPETA_RAIZ="InventarioVial/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"PavimentoFlexible";

    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    private ImageButton botonCargar;
    private ImageView imagen;
    private String path;
    private String idFotoFlex;
    private BaseDatos baseDatos;
    private MaterialSpinner spinnerPatoFlex,spinnerSeveridadPatoFlexRegistro;
    private TextView tv_idDanio,tv_nombre_carretera_patologia,tv_id_segmento_patologia_flex,tv_direccion_foto,tv_idFotoFlex,tv_foto_nombre,ej_Pato_Flex;
    private TextInputLayout input_campoAbscisaFlex,input_campoCarrilPato,input_campoDanioPato,input_campoLargoDanio,input_campoAnchoDanio,input_campoSeveridad,
            input_campoidFotoFlex;
    private EditText campoCarrilPato, campoDanioPato, campoLargoDanio, campoAnchoDanio, campoLargoRepa, campoAnchoRepa,campoSeveridad, campoAclaracion,campoAbscisaFlex,
            campoLatitudPatoFlex,campoLongitudPatoFlex;
    private String[] tipoDanio = {"Fisuras longitudinales y transversales", "Fisura longitudinal en junta de construcción",
            "Fisuras por reflexión de juntas o grietas en placas de concreto", "Fisuras en medialuna", "Fisuras de borde", "Fisuras en bloque", "Piel de cocotrilo",
            "Fisuración por desplazamiento de capas", "Fisuración incipiente", "Ondulación", "Abultamiento", "Hundimiento", "Ahuellamiento", "Descascaramiento",
            "Baches", "Parche", "Desgaste superficial", "Perdida de agregado", "Pulimento del agregado", "Cabezas duras", "Exudación", "Surcos",
            "Corrimiento vertical de la berma", "Separación de la berma", "Afloramiento de finos", "Afloramiento de agua"};
    private String[] severidad = {"Alta", "Media", "Baja", "No aplica"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_patologia_flex);

        baseDatos = new BaseDatos(this);

        //Se enlazan los objetos con los views
        imagen= (ImageView) findViewById(R.id.imagenPatoFlexEditar);
        spinnerSeveridadPatoFlexRegistro = (MaterialSpinner) findViewById(R.id.spinnerSeveridadPatoFlexEditar);
        spinnerPatoFlex = (MaterialSpinner) findViewById(R.id.spinnerPatoFlexEditar);
        campoAbscisaFlex = (EditText) findViewById(R.id.campoAbscisaFlexEditar);
        campoLatitudPatoFlex = (EditText) findViewById(R.id.campoLatitudPatoFlexEditar);
        campoLongitudPatoFlex = (EditText) findViewById(R.id.campolongitudPatoFlexEditar);
        campoCarrilPato = (EditText) findViewById(R.id.campoCarrilPatoFlexEditar);
        campoDanioPato = (EditText) findViewById(R.id.campoDanioPatoFlexEditar);
        campoLargoDanio = (EditText) findViewById(R.id.campoLargoDanioFlexEditar);
        campoAnchoDanio = (EditText) findViewById(R.id.campoAnchoDanioFlexEditar);
        campoLargoRepa = (EditText) findViewById(R.id.campoLargoRepaFlexEditar);
        campoAnchoRepa = (EditText) findViewById(R.id.campoAnchoRepaFlexEditar);
        campoSeveridad = (EditText) findViewById(R.id.campoSeveridadEditarPatoFlex);
        campoAclaracion = (EditText) findViewById(R.id.campoAclaracionesFlexEditar);

        tv_nombre_carretera_patologia = (TextView) findViewById(R.id.tv_nombre_carretera_patologia_flexEditar);
        tv_id_segmento_patologia_flex = (TextView) findViewById(R.id.tv_id_segmento_patologia_flex_Editar);
        tv_idDanio = (TextView) findViewById(R.id.tv_id_danio_pato_flex_editar);
        tv_direccion_foto = (TextView) findViewById(R.id.tv_direccion_foto_flex);
        tv_idFotoFlex = (TextView) findViewById(R.id.tv_idFotoFlexEditar);
        tv_foto_nombre = (TextView) findViewById(R.id.tv_foto_nombreEditar);
        ej_Pato_Flex = (TextView) findViewById(R.id.ej_Pato_FlexEditar);
        input_campoAbscisaFlex = (TextInputLayout) findViewById(R.id.input_campoAbscisaFlexEditar);
        input_campoCarrilPato = (TextInputLayout) findViewById(R.id.input_campoCarrilPatoFlexEditar);
        input_campoDanioPato = (TextInputLayout) findViewById(R.id.input_campoDanioPatoFlexEditar);
        input_campoLargoDanio = (TextInputLayout) findViewById(R.id.input_campoLargoDanioFlexEditar);
        input_campoAnchoDanio = (TextInputLayout) findViewById(R.id.input_campoAnchoDanioFlexEditar);
        input_campoSeveridad = (TextInputLayout) findViewById(R.id.input_campoSeveridadFlexEditar);
        input_campoidFotoFlex = (TextInputLayout) findViewById(R.id.input_campoidFotoFlexEditar);
        tv_idFotoFlex.setText(".");

        //Se recibe el nombre de la carretera, y el ID del segmento
        Bundle bundle = getIntent().getExtras();
        String abscisa = bundle.getString("tvAbscisa");
        String latitud = bundle.getString("tvLatitud");
        String longitud= bundle.getString("tvLongitud");
        String carril= bundle.getString("tvCarrilDanio");
        String danio= bundle.getString("tvdanionombre");
        String seve= bundle.getString("tvSeveridadPatoFlexActivity");
        String larDanio= bundle.getString("tvlarDanio");
        String anchoDanio= bundle.getString("tvanchDanio");
        String anchoRepa= bundle.getString("tvanchRepa");
        String larRepa= bundle.getString("tvlarRepa");
        String aclaraciones= bundle.getString("tvAclaraciones");
        String direccion= bundle.getString("tvDireccionPatoFlex");
        String nombreFoto= bundle.getString("tvNombreFoto_patoFlexActivity");
        String idSeg= bundle.getString("tvIdSegmento");
        String nomCarre= bundle.getString("tvNombreCarreteraPatologiaActivity");
        String idDaño= bundle.getString("tvIdDaño");

        campoAbscisaFlex.setText(abscisa);
        campoLatitudPatoFlex.setText(latitud);
        campoLongitudPatoFlex.setText(longitud);
        campoCarrilPato.setText(carril);
        campoDanioPato.setText(danio);
        campoLargoDanio.setText(larDanio);
        campoAnchoDanio.setText(anchoDanio);
        campoSeveridad.setText(seve);
        campoLargoRepa.setText(larRepa);
        campoAnchoRepa.setText(anchoRepa);
        campoAclaracion.setText(aclaraciones);
        tv_nombre_carretera_patologia.setText(nomCarre);
        tv_id_segmento_patologia_flex.setText(idSeg);
        path = direccion;
        tv_idDanio.setText(idDaño);
        tv_foto_nombre.setText(nombreFoto);

        /*Se dan los spinners los datos que deben cargar, tanto como los tipos de deterioro,
                    y los tipos de severidades*/
        //Spinner Patologias
        ArrayAdapter<String> arrayAdapterPato = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, tipoDanio);
        spinnerPatoFlex.setAdapter(arrayAdapterPato);
        //Spinner Severidades
        ArrayAdapter<String> arrayAdapterSeveridad = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, severidad);
        spinnerSeveridadPatoFlexRegistro.setAdapter(arrayAdapterSeveridad);

        spinnerSeveridadPatoFlexRegistro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    //Dependiendo de lo seleccionado se determina el campoSeveridad
                    case 0:
                        campoSeveridad.setText("A");
                        break;
                    case 1:
                        campoSeveridad.setText("M");
                        break;
                    case 2:
                        campoSeveridad.setText("B");
                        break;
                    case 3:
                        campoSeveridad.setText("N.A.");
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

                    //Dependiendo del daño seleccionado, se determina el codigo asociado a ese deterioro
                    case 0:
                        campoDanioPato.setText(R.string.fisuras_fl_lt);

                        break;
                    case 1:
                        campoDanioPato.setText(R.string.fisura_fcl);
                        break;
                    case 2:
                        campoDanioPato.setText(R.string.fisura_fjl);
                        break;
                    case 3:
                        campoDanioPato.setText(R.string.fisura_fml);
                        break;
                    case 4:
                        campoDanioPato.setText(R.string.fbd);
                        break;
                    case 5:
                        campoDanioPato.setText(R.string.fisura_fb);
                        break;
                    case 6:
                        campoDanioPato.setText(R.string.pc);
                        break;
                    case 7:
                        campoDanioPato.setText(R.string.fdc);
                        break;
                    case 8:
                        campoDanioPato.setText(R.string.fin);
                        break;
                    case 9:
                        campoDanioPato.setText(R.string.ond);
                        break;
                    case 10:
                        campoDanioPato.setText(R.string.ab);
                        break;
                    case 11:
                        campoDanioPato.setText(R.string.hun);
                        break;
                    case 12:
                        campoDanioPato.setText(R.string.ahu);
                        break;
                    case 13:
                        campoDanioPato.setText(R.string.dcf);
                        break;
                    case 14:
                        campoDanioPato.setText(R.string.bchf);
                        break;
                    case 15:
                        campoDanioPato.setText(R.string.pch);
                        break;
                    case 16:
                        campoDanioPato.setText(R.string.dsu);
                        break;
                    case 17:
                        campoDanioPato.setText(R.string.pa);
                        break;
                    case 18:
                        campoDanioPato.setText(R.string.puf);
                        break;
                    case 19:
                        campoDanioPato.setText(R.string.cd);
                        break;
                    case 20:
                        campoDanioPato.setText(R.string.ex);
                        break;
                    case 21:
                        campoDanioPato.setText(R.string.su);
                        break;
                    case 22:
                        campoDanioPato.setText(R.string.cvb);
                        break;
                    case 23:
                        campoDanioPato.setText(R.string.sbf);
                        break;
                    case 24:
                        campoDanioPato.setText(R.string.afi);
                        break;
                    case 25:
                        campoDanioPato.setText(R.string.afa);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        obtenerFotoPatoFlex();
    }


    private void obtenerFotoPatoFlex() {

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

        //Al oprimir un boton entra a este metodo, y dependiendo del selecionado se selecciona el caso
        Intent intent = null;

        switch (view.getId()){

            case R.id.btnEditarPatologiaFlex:
                /*Se verifica que los campos requeridos para realizar el registro estén diligenciados,
                    si esto su cumple se realiza la modificación de l apatología*/
                verificarDatosPatoFlex();
                break;
            case R.id.btnFotoFlexEditar:
                //Se abre la camara, y se genera un nuevo identificador para la foto
                guardarFotografia();
                tomarFotografia();
                break;
            case R.id.btnObtenerCoordenadasPatoFlexEditar:
                //Se obtienen las coordenadas mediante el GPS del celular
                obtenerCoordenadas();
                break;
            case R.id.btnManualPatoFlexEditar:
                //Se obtienen las coordenadas mediante el GPS del celular
                abrirManual();
                break;
            case R.id.ej_Pato_FlexEditar:
                //Abre la actividad RegistroPatologiaFlexEjemplo
                intent = new Intent(EditarPatologiaFlexActivity.this, RegistroPatologiaFlexEjemploActivity.class);
                startActivity(intent);
                break;
            case R.id.backPatoFlexEditarActivity:
                //Se devuelve a la actividad PatologiaFlex
                intent = new Intent(EditarPatologiaFlexActivity.this,PatologiaFlexActivity.class);
                intent.putExtra("tvIdDaño",tv_idDanio.getText().toString());
                startActivity( intent);
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
        if(campoSeveridad.getText().toString().trim().isEmpty()){
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
        if(tv_direccion_foto.getText().toString().trim().isEmpty()){
            input_campoidFotoFlex.setError("Tome la foto del daño");
            isValid=false;
        }else{
            input_campoidFotoFlex.setErrorEnabled(false);
        }

        if(isValid){
            editarPatoFlex();
        }

    }

    private void editarPatoFlex() {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String[] parametros={tv_idDanio.getText().toString()};

        ContentValues values = new ContentValues();

        values.put(Utilidades.PATOLOGIAFLEX.CAMPO_ABSCISA_PATOLOGIA , campoAbscisaFlex.getText().toString());
        values.put(Utilidades.PATOLOGIAFLEX.CAMPO_LATITUD  , campoLatitudPatoFlex.getText().toString());
        values.put(Utilidades.PATOLOGIAFLEX.CAMPO_LONGITUD, campoLongitudPatoFlex.getText().toString());
        values.put(Utilidades.PATOLOGIAFLEX.CAMPO_CARRIL_PATOLOGIA  , campoCarrilPato.getText().toString());
        values.put(Utilidades.PATOLOGIAFLEX.CAMPO_DANIO_PATOLOGIA, campoDanioPato.getText().toString());
        values.put(Utilidades.PATOLOGIAFLEX.CAMPO_SEVERIDAD, campoSeveridad.getText().toString());
        values.put(Utilidades.PATOLOGIAFLEX.CAMPO_LARGO_PATOLOGIA  , campoLargoDanio.getText().toString());
        values.put(Utilidades.PATOLOGIAFLEX.CAMPO_ANCHO_PATOLOGIA, campoAnchoDanio.getText().toString());
        values.put(Utilidades.PATOLOGIAFLEX.CAMPO_LARGO_REPARACION, campoLargoRepa.getText().toString());
        values.put(Utilidades.PATOLOGIAFLEX.CAMPO_ANCHO_REPARACION, campoAnchoRepa.getText().toString());
        values.put(Utilidades.PATOLOGIAFLEX.CAMPO_ACLARACIONES, campoAclaracion.getText().toString());
        values.put(Utilidades.PATOLOGIAFLEX.CAMPO_NOMBRE_FOTO, tv_foto_nombre.getText().toString());
        values.put(Utilidades.PATOLOGIAFLEX.CAMPO_FOTO_DANIO, tv_direccion_foto.getText().toString());

        db.update(Utilidades.PATOLOGIAFLEX.TABLA_PATOLOGIA,values,Utilidades.PATOLOGIAFLEX.CAMPO_ID_PATOLOGIA+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Se edito el segmento",Toast.LENGTH_SHORT).show();

        db.close();

        Intent intent = new Intent(EditarPatologiaFlexActivity.this,PatologiaFlexActivity.class);
        intent.putExtra("tvIdDaño",tv_idDanio.getText().toString());
        startActivity( intent);
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

        tv_direccion_foto.setText(path);


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

                    float alto= 7/10;//alto en pixeles
                    float ancho= 7/10 ;//ancho en pixeles


                    Bitmap bitmap= BitmapFactory.decodeFile(path);
                    obtenerFotoPatoFlex();
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
        AlertDialog.Builder dialogo=new AlertDialog.Builder(EditarPatologiaFlexActivity.this);
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
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(EditarPatologiaFlexActivity.this);
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
        Local.setEditarPatologiaFlexActivity(this);
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
        EditarPatologiaFlexActivity editarPatologiaFlexActivity;

        public EditarPatologiaFlexActivity getEditarPatologiaFlexActivity() {
            return editarPatologiaFlexActivity;
        }

        public void setEditarPatologiaFlexActivity(EditarPatologiaFlexActivity editarPatologiaFlexActivity) {
            this.editarPatologiaFlexActivity = editarPatologiaFlexActivity;
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
