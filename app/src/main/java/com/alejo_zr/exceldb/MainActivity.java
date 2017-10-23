package com.alejo_zr.exceldb;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.alejo_zr.exceldb.Carretera.ConsultarCarreteraActivity;
import com.alejo_zr.exceldb.Carretera.RegistroCarreteraActivity;
import com.alejo_zr.exceldb.utilidades.Utilidades;

import java.io.File;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseDatos conn = new BaseDatos(this);
        //conn.insertData();

    }

    public void onClick(View view){
        Intent intent = null;
        switch (view.getId()){
            case R.id.btnRegistroCarretera:
                intent = new Intent(MainActivity.this,RegistroCarreteraActivity.class);
                break;
            case R.id.btnConsultarCarretera:
                intent = new Intent(MainActivity.this,ConsultarCarreteraActivity.class);
                break;

            case R.id.btnExportar:
                exportar();
                break;
            case R.id.btnManual:
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.alejo_zr.manual");
                startActivity(launchIntent);
                break;


        }
        if(intent != null){
            startActivity(intent);
        }

    }

    private void exportar() {

        Toast.makeText(getApplicationContext(),"SI RECONOCE EL BOTON",Toast.LENGTH_SHORT).show();
        BaseDatos baseDatos = new BaseDatos(this);
        //conn.insertData();
        final Cursor cursor = baseDatos.getroad();
        final Cursor cursor1 = baseDatos.getSegmentoFlex();
        final Cursor cursor2 = baseDatos.getSegmentoRigi();
        final Cursor cursor3 = baseDatos.getPatoFlex();

        File sd = Environment.getExternalStorageDirectory();
        String csvFile = "prueba3.xls";

        File directory = new File(sd.getAbsolutePath());
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {

            //file path
            File file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("Carreteras", 0);
            WritableSheet sheet1 = workbook.createSheet("Segmento Flexible", 1);
            WritableSheet sheet2 = workbook.createSheet("Segmento Rigido", 2);
            WritableSheet sheet3 = workbook.createSheet("Pato. Flexible", 3);
            WritableSheet sheet4 = workbook.createSheet("Pato. Rigído",4);


            //Hoja Carreteras
                                //FF/CC
            sheet.addCell(new Label(0, 0, "ID"));
            sheet.addCell(new Label(1, 0, "Nom. Carretera")); // column and row
            sheet.addCell(new Label(2, 0, "Cod. Carretera"));
            sheet.addCell(new Label(3, 0, "Territorial"));
            sheet.addCell(new Label(4, 0, "Admon"));
            sheet.addCell(new Label(5, 0, "Levantado por"));

            //Hoja Segmento Flexible
            sheet1.addCell(new Label(0, 0, "ID"));
            sheet1.addCell(new Label(1, 0, "Nom. Carretera"));
            sheet1.addCell(new Label(2, 0, "N° Calzadas"));
            sheet1.addCell(new Label(3, 0, "N° Carriles"));
            sheet1.addCell(new Label(4, 0, "Ancho carril(m)"));
            sheet1.addCell(new Label(5, 0, "Ancho berma(m)"));
            sheet1.addCell(new Label(6, 0, "PRI"));
            sheet1.addCell(new Label(7, 0, "PRF"));
            sheet1.addCell(new Label(8, 0, "Comentarios"));
            sheet1.addCell(new Label(9, 0, "Fecha"));

            //Hoja Segmento Rigi
            sheet2.addCell(new Label(0,0,"ID"));
            sheet2.addCell(new Label(1, 0, "Nom. Carretera"));
            sheet2.addCell(new Label(2, 0, "N° Calzadas"));
            sheet2.addCell(new Label(3, 0, "N° Carriles"));
            sheet2.addCell(new Label(4, 0, "Espesor Losa(mm)"));
            sheet2.addCell(new Label(5, 0, "Ancho berma(m)"));
            sheet2.addCell(new Label(6, 0, "PRI"));
            sheet2.addCell(new Label(7, 0, "PRF"));
            sheet2.addCell(new Label(8, 0, "Comentarios"));
            sheet2.addCell(new Label(9, 0, "Fecha"));

            //Hoja Pato. Flex
            sheet3.addCell(new Label(0,0,"ID"));
            sheet3.addCell(new Label(1, 0, "ID Segmento"));
            sheet3.addCell(new Label(2,0,"Nom. Carretera"));
            sheet3.addCell(new Label(3,0, "ABS"));
            sheet3.addCell(new Label(4,0,"Latitud"));
            sheet3.addCell(new Label(5,0,"Longitud"));
            sheet3.addCell(new Label(6,0,"Carril"));
            sheet3.addCell(new Label(7,0,"Daño"));
            sheet3.addCell(new Label(8,0,"Severidad"));
            sheet3.addCell(new Label(9,0,"Largo Daño(m)"));
            sheet3.addCell(new Label(10,0,"Ancho Daño(m"));
            sheet3.addCell(new Label(11,0,"Largo Reparación(m)"));
            sheet3.addCell(new Label(12,0,"Ancho Reparación(m)"));
            sheet3.addCell(new Label(13,0,"Aclaracion"));
            sheet3.addCell(new Label(14,0,"Foto"));


            if (cursor.moveToNext()) {

                do {

                    int i = cursor.getPosition();
                    int il =i+1;
                    //Campos Carreteras
                    String id = cursor.getString(cursor.getColumnIndex(Utilidades.CARRETERA.CAMPO_ID_CARRETERA));
                    String nombre = cursor.getString(cursor.getColumnIndex(Utilidades.CARRETERA.CAMPO_NOMBRE_CARRETERA));
                    String codCarretera = cursor.getString(cursor.getColumnIndex(Utilidades.CARRETERA.CAMPO_CODIGO_CARRETERA));
                    String territorial = cursor.getString(cursor.getColumnIndex(Utilidades.CARRETERA.CAMPO_TERRITO_CARRETERA));
                    String admon= cursor.getString(cursor.getColumnIndex(Utilidades.CARRETERA.CAMPO_ADMON_CARRETERA));
                    String levantado = cursor.getString(cursor.getColumnIndex(Utilidades.CARRETERA.CAMPO_LEVANTADO_CARRETERA));


                    //Se Llenan las casillas Carreteras
                    sheet.addCell(new Label(0, il, id));
                    sheet.addCell(new Label(1, il, nombre));
                    sheet.addCell(new Label(2, il, codCarretera));
                    sheet.addCell(new Label(3, il, territorial));
                    sheet.addCell(new Label(4, il, admon));
                    sheet.addCell(new Label(5, il, levantado));
                } while (cursor.moveToNext());

            }
            //Toast.makeText(getApplicationContext(),"Lleno carreteras",Toast.LENGTH_SHORT).show();

            //int ifs = 1;

            if (cursor1.moveToNext()) {

                do {

                    int is = cursor1.getPosition();
                    int ils =is+1;

                    /****Campos Segmento Flex****/
                    String id_seg_flex = cursor1.getString(cursor1.getColumnIndex(Utilidades.SEGMENTOFLEX.CAMPO_ID_SEGMENTO));
                    String id_seg_flex_car = cursor1.getString(cursor1.getColumnIndex(Utilidades.SEGMENTOFLEX.CAMPO_NOMBRE_CARRETERA_SEGMENTO));
                    String nCalzadas_flex = cursor1.getString(cursor1.getColumnIndex(Utilidades.SEGMENTOFLEX.CAMPO_CALZADAS_SEGMENTO));
                    String nCarriles_flex = cursor1.getString(cursor1.getColumnIndex(Utilidades.SEGMENTOFLEX.CAMPO_CARRILES_SEGMENTO));
                    String anchoCarril_flex = cursor1.getString(cursor1.getColumnIndex(Utilidades.SEGMENTOFLEX.CAMPO_ANCHO_CARRIL));
                    String anchoBerma_flex = cursor1.getString(cursor1.getColumnIndex(Utilidades.SEGMENTOFLEX.CAMPO_ANCHO_BERMA));
                    String pri_flex = cursor1.getString(cursor1.getColumnIndex(Utilidades.SEGMENTOFLEX.CAMPO_PRI_SEGMENTO));
                    String prf_flex = cursor1.getString(cursor1.getColumnIndex(Utilidades.SEGMENTOFLEX.CAMPO_PRF_SEGMENTO));
                    String comentarios_flex = cursor1.getString(cursor1.getColumnIndex(Utilidades.SEGMENTOFLEX.CAMPO_COMENTARIOS));
                    String fecha_flex = cursor1.getString(cursor1.getColumnIndex(Utilidades.SEGMENTOFLEX.CAMPO_FECHA));

                    //Toast.makeText(getApplicationContext(),"Va a llenar campos segmentos",Toast.LENGTH_SHORT).show();

                    //Se Llenan las casillas Segmento Flex
                    sheet1.addCell(new Label(0, ils, id_seg_flex));
                    sheet1.addCell(new Label(1, ils, id_seg_flex_car));
                    sheet1.addCell(new Label(2, ils, nCalzadas_flex));
                    sheet1.addCell(new Label(3, ils, nCarriles_flex));
                    sheet1.addCell(new Label(4, ils, anchoCarril_flex));
                    sheet1.addCell(new Label(5, ils, anchoBerma_flex));
                    sheet1.addCell(new Label(6, ils, pri_flex));
                    sheet1.addCell(new Label(7, ils, prf_flex));
                    sheet1.addCell(new Label(8, ils, comentarios_flex));
                    sheet1.addCell(new Label(9,ils,fecha_flex));


                } while (cursor1.moveToNext());

            }

            if (cursor2.moveToNext()) {

                do {

                    int isR = cursor2.getPosition();
                    int ilsR = isR + 1;

                    //Campos Segmento Rigi
                    String id_seg_rigi = cursor2.getString(cursor2.getColumnIndex(Utilidades.SEGMENTORIGI.CAMPO_ID_SEGMENTO));
                    String id_seg_rigi_car = cursor2.getString(cursor2.getColumnIndex(Utilidades.SEGMENTORIGI.CAMPO_NOMBRE_CARRETERA_SEGMENTO));
                    String nCalzadas_rigi = cursor2.getString(cursor2.getColumnIndex(Utilidades.SEGMENTORIGI.CAMPO_CALZADAS_SEGMENTO));
                    String nCarriles_rigi = cursor2.getString(cursor2.getColumnIndex(Utilidades.SEGMENTORIGI.CAMPO_CARRILES_SEGMENTO));
                    String espesorLosa_rigi = cursor2.getString(cursor2.getColumnIndex(Utilidades.SEGMENTORIGI.CAMPO_ESPESOR_LOSA));
                    String anchoBerma_rigi = cursor2.getString(cursor2.getColumnIndex(Utilidades.SEGMENTORIGI.CAMPO_ANCHO_BERMA));
                    String pri_rigi = cursor2.getString(cursor2.getColumnIndex(Utilidades.SEGMENTORIGI.CAMPO_PRI_SEGMENTO));
                    String prf_rigi = cursor2.getString(cursor2.getColumnIndex(Utilidades.SEGMENTORIGI.CAMPO_PRF_SEGMENTO));
                    String comentarios_rigi = cursor2.getString(cursor2.getColumnIndex(Utilidades.SEGMENTORIGI.CAMPO_COMENTARIOS));
                    String fecha_rigi = cursor2.getString(cursor2.getColumnIndex(Utilidades.SEGMENTORIGI.CAMPO_FECHA));

                    //boolean nomCarretera = id_seg_rigi.equals("");
                    //if(nomCarretera==false) {

                    //Se Llenan las casillas Segmento Rigi
                    sheet2.addCell(new Label(0, ilsR, id_seg_rigi));
                    sheet2.addCell(new Label(1, ilsR, id_seg_rigi_car));
                    sheet2.addCell(new Label(2, ilsR, nCalzadas_rigi));
                    sheet2.addCell(new Label(3, ilsR, nCarriles_rigi));
                    sheet2.addCell(new Label(4, ilsR, espesorLosa_rigi));
                    sheet2.addCell(new Label(5, ilsR, anchoBerma_rigi));
                    sheet2.addCell(new Label(6, ilsR, pri_rigi));
                    sheet2.addCell(new Label(7, ilsR, prf_rigi));
                    sheet2.addCell(new Label(8, ilsR, comentarios_rigi));
                    sheet2.addCell(new Label(9, ilsR, fecha_rigi));
                //}


                }while (cursor2.moveToNext());
            }

            if (cursor3.moveToNext()) {

                do {

                    int iPF = cursor3.getPosition();
                    int ilPF = iPF + 1;

                    String id_pato_flex = cursor3.getString(cursor3.getColumnIndex(Utilidades.PATOLOGIAFLEX.CAMPO_ID_PATOLOGIA));
                    String id_segmento_flex = cursor3.getString(cursor3.getColumnIndex(Utilidades.PATOLOGIAFLEX.CAMPO_ID_SEGMENTO_PATOLOGIA));
                    String nom_carretera_flex= cursor3.getString(cursor3.getColumnIndex(Utilidades.PATOLOGIAFLEX.CAMPO_NOMBRE_CARRETERA_PATOLOGIA));
                    String abscisa_flex= cursor3.getString(cursor3.getColumnIndex(Utilidades.PATOLOGIAFLEX.CAMPO_ABSCISA_PATOLOGIA));
                    String latitud_flex= cursor3.getString(cursor3.getColumnIndex(Utilidades.PATOLOGIAFLEX.CAMPO_LATITUD));
                    String longitu_flex= cursor3.getString(cursor3.getColumnIndex(Utilidades.PATOLOGIAFLEX.CAMPO_LONGITUD));
                    String carril_flex= cursor3.getString(cursor3.getColumnIndex(Utilidades.PATOLOGIAFLEX.CAMPO_CARRIL_PATOLOGIA));
                    String danio_flex= cursor3.getString(cursor3.getColumnIndex(Utilidades.PATOLOGIAFLEX.CAMPO_DANIO_PATOLOGIA));
                    String severidad_flex= cursor3.getString(cursor3.getColumnIndex(Utilidades.PATOLOGIAFLEX.CAMPO_SEVERIDAD));
                    String largodanio_flex= cursor3.getString(cursor3.getColumnIndex(Utilidades.PATOLOGIAFLEX.CAMPO_LARGO_PATOLOGIA));
                    String anchodanio_flex= cursor3.getString(cursor3.getColumnIndex(Utilidades.PATOLOGIAFLEX.CAMPO_ANCHO_PATOLOGIA));
                    String largorepa_flex= cursor3.getString(cursor3.getColumnIndex(Utilidades.PATOLOGIAFLEX.CAMPO_LARGO_REPARACION));
                    String anchorepa_flex= cursor3.getString(cursor3.getColumnIndex(Utilidades.PATOLOGIAFLEX.CAMPO_ANCHO_REPARACION));
                    String aclaraciones_flex= cursor3.getString(cursor3.getColumnIndex(Utilidades.PATOLOGIAFLEX.CAMPO_ACLARACIONES));
                    String nombrefoto_flex= cursor3.getString(cursor3.getColumnIndex(Utilidades.PATOLOGIAFLEX.CAMPO_NOMBRE_FOTO));
                    String tododanio_flex= cursor3.getString(cursor3.getColumnIndex(Utilidades.PATOLOGIAFLEX.CAMPO_FOTO_DANIO));


                    sheet3.addCell(new Label(0, ilPF, id_pato_flex));
                    sheet3.addCell(new Label(1, ilPF, id_segmento_flex));
                    sheet3.addCell(new Label(2, ilPF, nom_carretera_flex));
                    sheet3.addCell(new Label(3, ilPF, abscisa_flex));
                    sheet3.addCell(new Label(4, ilPF, latitud_flex));
                    sheet3.addCell(new Label(5, ilPF, longitu_flex));
                    sheet3.addCell(new Label(6, ilPF, carril_flex));
                    sheet3.addCell(new Label(7, ilPF, danio_flex));
                    sheet3.addCell(new Label(8, ilPF, severidad_flex));
                    sheet3.addCell(new Label(9, ilPF, largodanio_flex));
                    sheet3.addCell(new Label(10, ilPF, anchodanio_flex));
                    sheet3.addCell(new Label(11, ilPF, largorepa_flex));
                    sheet3.addCell(new Label(12, ilPF, anchorepa_flex));
                    sheet3.addCell(new Label(13, ilPF, aclaraciones_flex));
                    sheet3.addCell(new Label(14, ilPF, nombrefoto_flex));
                    sheet3.addCell(new Label(15, ilPF, tododanio_flex));

                    Toast.makeText(getApplicationContext(),"id Pato"+id_pato_flex,Toast.LENGTH_SHORT).show();


                }while(cursor3.moveToNext());
            }


            //closing cursor
            cursor.close();
            cursor1.close();
            cursor2.close();
            cursor3.close();
            workbook.write();
            workbook.close();
            //Toast.makeText(getApplicationContext(),"ANTES TOAST EXCEL",Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplication(), "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Entra al CATCH",Toast.LENGTH_SHORT).show();
        }

    }
}
