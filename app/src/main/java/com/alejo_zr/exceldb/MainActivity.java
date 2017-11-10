package com.alejo_zr.exceldb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.alejo_zr.exceldb.Carretera.ConsultarCarreteraActivity;
import com.alejo_zr.exceldb.Carretera.RegistroCarreteraActivity;

import java.io.FileNotFoundException;

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
            }

        if(intent != null){
            startActivity(intent);
        }

    }

    private void dialogoExportar() {
        DialogFragment dialogFragment = new Exportar();
        dialogFragment.show(getSupportFragmentManager(),"dialogoexportar");

    }




}
