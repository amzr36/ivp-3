package com.alejo_zr.exceldb.Carretera;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alejo_zr.exceldb.BaseDatos;
import com.alejo_zr.exceldb.MainActivity;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.utilidades.Utilidades;

import fr.ganfra.materialspinner.MaterialSpinner;

public class RegistroCarreteraActivity extends AppCompatActivity {

    //Se declaran las variables y objetos java
    private EditText campoNombre,campoCodigo,campoTerrito,campoLevantado;
    private TextInputLayout inputLayoutNombre;
    private String[] AdmonCarreteraRegistro = {"Concesión", "MTTO Integral", "A.M.V."};
    private MaterialSpinner spinnerAdrmonCarreteraRegistro;
    private String campoAdmon;
    private ImageButton btnHermes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_carretera);

        //Se enlazan los objetos con los views

        spinnerAdrmonCarreteraRegistro = (MaterialSpinner) findViewById(R.id.spinnerAdrmonCarreteraRegistro) ;

        campoNombre = (EditText) findViewById(R.id.campoNombre);
        campoCodigo = (EditText) findViewById(R.id.campoCodigo);
        campoTerrito = (EditText) findViewById(R.id.campoTerritorial);
        campoLevantado = (EditText) findViewById(R.id.campoLevantado);

        inputLayoutNombre= (TextInputLayout) findViewById(R.id.input_layout_nombre);

        //Se dan los valores al spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, AdmonCarreteraRegistro);
        spinnerAdrmonCarreteraRegistro.setAdapter(arrayAdapter);


        spinnerAdrmonCarreteraRegistro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        //De pendiendo de lo seleccionado en el spinner se le da valor al campoAdmon
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        campoAdmon = "Concesión";
                        break;
                    case 1:
                        campoAdmon = "MTTO Integral";
                        break;
                    case 2:
                        campoAdmon = "A.M.V.";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                campoAdmon = "";
            }
        });
    }

    public void onClick(View view) {
        Intent intent = null;
        //Al oprimir un boton entra a este metodo, y dependiendo del selecionado se selecciona el caso
        switch (view.getId())
        {
            case R.id.backCarreteraActivity:
                //Se devuelvo a la actividad principal
                intent = new Intent(RegistroCarreteraActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btnManualCarretera:
                //Abre el MIVP
                try{
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.alejo_zr.manual");
                    startActivity(launchIntent);

                }catch (Exception e){
                    //De no tenerse instalado el manual mostrara el mensaje
                    Toast.makeText(getApplicationContext(),R.string.instale,Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btnRegistro:
                //Se verifica que los campos requeridos para realizar el registro esten diligenciados,
                // si esto su cumple se abre la actividad CarreteraActivity, con los datos de la carretera
                verficarCampo();
                break;
            case R.id.tvEjemploCarretera:
                //Se abre la activity CarreteraRegistroEjemploActivity
                intent = new Intent(RegistroCarreteraActivity.this, CarreteraRegistroEjemploActivity.class);
                startActivity(intent);
                break;
            case R.id.homeRegistroCarreteraActivity:
                intent = new Intent(RegistroCarreteraActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btnHermes:
                Uri uri = Uri.parse("http://hermes.invias.gov.co/carreteras/");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }

    }

    private void verficarCampo() {
        boolean isValid = true;
        if(campoNombre.getText().toString().trim().isEmpty()){
            inputLayoutNombre.setError("Ingrese el nombre");
            isValid = false;
        }else{
            inputLayoutNombre.setErrorEnabled(false);
        }
        if(isValid) {
            registrarCarretera();
        }
    }

    //Metodo con el cual se registran los datos en la base de datos correspondientes a la tabla 'Carretera'
    private void registrarCarretera() {
        BaseDatos baseDatos=new BaseDatos(this);

        SQLiteDatabase db=baseDatos.getWritableDatabase();

        String insert="INSERT INTO "+Utilidades.CARRETERA.TABLA_CARRETERA
                +" ( " +Utilidades.CARRETERA.CAMPO_NOMBRE_CARRETERA+","+Utilidades.CARRETERA.CAMPO_CODIGO_CARRETERA+","+Utilidades.CARRETERA.CAMPO_TERRITO_CARRETERA+","
                +Utilidades.CARRETERA.CAMPO_ADMON_CARRETERA+","+Utilidades.CARRETERA.CAMPO_LEVANTADO_CARRETERA+")" +
                " VALUES ('"+campoNombre.getText().toString()+"' , '"+campoCodigo.getText().toString()+"' , '"
                +campoTerrito.getText().toString()+"' , '"+campoAdmon+"' , '"+campoLevantado.getText().toString()+"')";

        db.execSQL(insert);
        Toast.makeText(getApplicationContext(),"Se registro la vía : "+campoNombre.getText().toString(),Toast.LENGTH_SHORT).show();

        db.close();

        Intent intent= new Intent(RegistroCarreteraActivity.this, CarreteraActivity.class);
        intent.putExtra("nom_carretera",campoNombre.getText().toString());
        startActivity(intent);
    }



}
