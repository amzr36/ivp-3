package com.alejo_zr.exceldb;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.alejo_zr.exceldb.Carretera.ConsultarCarreteraActivity;
import com.alejo_zr.exceldb.Carretera.RegistroCarreteraActivity;

import java.io.FileNotFoundException;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private final String NOMBRE_CARPETA="probandoNOmbre/";
    private final String GENERADOS = "pdf/";
    private final static String NOMBRE_DOCUMENTO = "prueba.pdf";
    private final String CARPETA_RAIZ="InventarioVial/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseDatos conn = new BaseDatos(this);

        validaPermisos();

    }

    public void onClick(View view) throws FileNotFoundException {
        //Al oprimir un boton entra a este metodo, y dependiendo del selecionado se selecciona el caso
        Intent intent = null;
        switch (view.getId()){
            case R.id.btnRegistroCarretera:
                //Abre la actividad RegistroCarretera
                intent = new Intent(MainActivity.this,RegistroCarreteraActivity.class);
                break;
            case R.id.btnConsultarCarretera:
                //Abre la actividad ConsultarCarretera
                intent = new Intent(MainActivity.this,ConsultarCarreteraActivity.class);
                break;

            case R.id.btnExportar:
                //Exporta los datos de la base de datos al archivo .xmls
                dialogoExportar();
                break;
            case R.id.btnManual:
                //Abre el MIVP
                try{
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.alejo_zr.manual");
                    startActivity(launchIntent);

                }catch (Exception e){
                    //De no tenerse instalado el manual mostrara el mensaje
                    Toast.makeText(getApplicationContext(),R.string.instale,Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnManualUsuario:
                intent = new Intent(MainActivity.this,ManualUsuario.class);
                break;
            }

        if(intent != null){
            startActivity(intent);
        }

    }

    private void dialogoExportar() {
        DialogFragment dialogFragment = new Exportar();
        dialogFragment.show(getSupportFragmentManager(),"dialogoexportar");

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
        AlertDialog.Builder dialogo=new AlertDialog.Builder(MainActivity.this);
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



}
