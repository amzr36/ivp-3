package com.alejo_zr.exceldb;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alejo_zr.exceldb.utilidades.Utilidades;

import java.io.File;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Created by Alejo on 06/11/2017.
 */

public class Exportar  extends android.support.v4.app.DialogFragment{

    private EditText nombreArchivo;
    private TextInputLayout inputNombre;
    private final String CARPETA_RAIZ="InventarioVial/";

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.exportar,null);

        nombreArchivo = (EditText) view.findViewById(R.id.nombreArchivo);
        inputNombre = (TextInputLayout) view.findViewById(R.id.input_nombreArchivo);

        builder.setView(view)
        .setTitle("Exportar Base de Datos")
        .setPositiveButton("Exportar", null)
        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positivo = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positivo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        validarDatos();
                    }
                });
            }
        });

        return dialog;
    }

    private void validarDatos() {

        boolean isValid = true;
        if(nombreArchivo.getText().toString().trim().isEmpty()){
            inputNombre.setError("Ingrese el nombre");
            isValid=false;
        }else{
            inputNombre.setErrorEnabled(false);
        }
        if(isValid){
            exportar();
            dismiss();
        }
    }

    private void exportar() {


        BaseDatos baseDatos = new BaseDatos(getActivity());
        //Se accede a cada tabla de la base datos que se desea exportar
        final Cursor cursor = baseDatos.getroad();
        final Cursor cursor1 = baseDatos.getSegmentoFlex();
        final Cursor cursor2 = baseDatos.getSegmentoRigi();
        final Cursor cursor3 = baseDatos.getPatoFlex();
        final Cursor cursor4 = baseDatos.getPatoRigi();

        File sd = Environment.getExternalStorageDirectory();
        //Se le da el nombre al archivo
        String csvFile = nombreArchivo.getText().toString()+".xls";

        File directory = new File(sd.getAbsolutePath(),CARPETA_RAIZ);
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {

            //Se crea el archivo
            File file = new File(directory,csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Se da el nombre a las hojas
            WritableSheet sheet = workbook.createSheet("Carreteras", 0);
            WritableSheet sheet1 = workbook.createSheet("Seg. Flex", 1);
            WritableSheet sheet2 = workbook.createSheet("Seg. Rigi.", 2);
            WritableSheet sheet3 = workbook.createSheet("Pato. Flex", 3);
            WritableSheet sheet4 = workbook.createSheet("Pato. Rigi.",4);


            //Se da el nombre a las columnas de pendiendo de la hoja

            //Hoja Carrreteras
            sheet.addCell(new Label(0, 0, "ID"));
            sheet.addCell(new Label(1, 0, "Nom. Carretera")); // column and row
            sheet.addCell(new Label(2, 0, "Cod. Carretera"));
            sheet.addCell(new Label(3, 0, "Territorial"));
            sheet.addCell(new Label(4, 0, "Admon"));
            sheet.addCell(new Label(5, 0, "Levantado por"));

            //Hoja Seg Flex
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

            //Hoja Seg Rigi
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


            //Hoja Pato. Rigi
            sheet4.addCell(new Label(0,0,"ID"));
            sheet4.addCell(new Label(1,0,"ID Segmento"));
            sheet4.addCell(new Label(2,0,"Nom Carretera"));
            sheet4.addCell(new Label(3,0,"Abscisa"));
            sheet4.addCell(new Label(4,0,"Latitud"));
            sheet4.addCell(new Label(5,0,"Longitud"));
            sheet4.addCell(new Label(6,0,"No Losa"));
            sheet4.addCell(new Label(7,0,"Letra Losa"));
            sheet4.addCell(new Label(8,0,"Largo Losa"));
            sheet4.addCell(new Label(9,0,"Ancho Losa"));
            sheet4.addCell(new Label(10,0,"Daño"));
            sheet4.addCell(new Label(11,0,"Severidad"));
            sheet4.addCell(new Label(12,0,"Largo Daño"));
            sheet4.addCell(new Label(13,0,"Ancho Daño"));
            sheet4.addCell(new Label(14,0,"Largo Reparacion"));
            sheet4.addCell(new Label(15,0,"Ancho Reparacion"));
            sheet4.addCell(new Label(16,0,"Aclaraciones"));
            sheet4.addCell(new Label(17,0,"Nombre Foto"));


            if (cursor.moveToNext()) {
                //Se Accede a la tabla carreteras
                do {

                    int i = cursor.getPosition();
                    int il =i+1;
                    //Se seleccionan los campos de la tabla carreteras
                    String id = cursor.getString(cursor.getColumnIndex(Utilidades.CARRETERA.CAMPO_ID_CARRETERA));
                    String nombre = cursor.getString(cursor.getColumnIndex(Utilidades.CARRETERA.CAMPO_NOMBRE_CARRETERA));
                    String codCarretera = cursor.getString(cursor.getColumnIndex(Utilidades.CARRETERA.CAMPO_CODIGO_CARRETERA));
                    String territorial = cursor.getString(cursor.getColumnIndex(Utilidades.CARRETERA.CAMPO_TERRITO_CARRETERA));
                    String admon= cursor.getString(cursor.getColumnIndex(Utilidades.CARRETERA.CAMPO_ADMON_CARRETERA));
                    String levantado = cursor.getString(cursor.getColumnIndex(Utilidades.CARRETERA.CAMPO_LEVANTADO_CARRETERA));

                    //Se Llenan las casillas de la hoja Carreteras, con los datos de la tabla carreteras
                    sheet.addCell(new Label(0, il, id));
                    sheet.addCell(new Label(1, il, nombre));
                    sheet.addCell(new Label(2, il, codCarretera));
                    sheet.addCell(new Label(3, il, territorial));
                    sheet.addCell(new Label(4, il, admon));
                    sheet.addCell(new Label(5, il, levantado));
                } while (cursor.moveToNext());

            }


            if (cursor1.moveToNext()) {
                //Se Accede a la tabla SegmentoFlex
                do {

                    int is = cursor1.getPosition();
                    int ils =is+1;

                    //Se seleccionan los campos de la tabla SegmentoFlex
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

                    //Se Llenan las casillas de la hoja Seg Flex, con los campos de la tabla SegmentoFlex
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
                //Se Accede a la tabla SegmentoRigi
                do {

                    int isR = cursor2.getPosition();
                    int ilsR = isR + 1;

                    //Se seleccionan los campos de la tabla SegmentoRigi
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

                    //Se Llenan las casillas de la hoja Seg Rigi, con los campos de la tabla SegmentoRigi
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
                //Se Accede a la tabla Pato. Flex
                do {

                    int iPF = cursor3.getPosition();
                    int ilPF = iPF + 1;

                    //Se seleccionan los campos de la tabla PatologiaFlex

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

                    //Se Llenan las casillas de la hoja Pato. Flex.., con los campos de la tabla PatologiaFlex
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

                }while(cursor3.moveToNext());
            }
            if (cursor4.moveToNext()) {
                //Se Accede a la tabla Pato. Rigi.
                do {

                    int iPF = cursor4.getPosition();
                    int ilPF = iPF + 1;

                    //Se seleccionan los campos de la tabla PatologiaRigi

                    String id_pato_Rigi = cursor4.getString(cursor4.getColumnIndex(Utilidades.PATOLOGIARIGI.CAMPO_ID_PATOLOGIA));
                    String id_segmento_Rigi = cursor4.getString(cursor4.getColumnIndex(Utilidades.PATOLOGIARIGI.CAMPO_ID_SEGMENTO_PATOLOGIA));
                    String nom_carretera_Rigi= cursor4.getString(cursor4.getColumnIndex(Utilidades.PATOLOGIARIGI.CAMPO_NOMBRE_CARRETERA_PATOLOGIA));
                    String abscisa_Rigi= cursor4.getString(cursor4.getColumnIndex(Utilidades.PATOLOGIARIGI.CAMPO_ABSCISA_PATOLOGIA));
                    String latitud_Rigi= cursor4.getString(cursor4.getColumnIndex(Utilidades.PATOLOGIARIGI.CAMPO_LATITUD));
                    String longitud_Rigi= cursor4.getString(cursor4.getColumnIndex(Utilidades.PATOLOGIARIGI.CAMPO_LONGITUD));
                    String Nolosa_Rigi= cursor4.getString(cursor4.getColumnIndex(Utilidades.PATOLOGIARIGI.CAMPO_NUMERO_LOSA));
                    String letralosa_Rigi= cursor4.getString(cursor4.getColumnIndex(Utilidades.PATOLOGIARIGI.CAMPO_LETRA_LOSA));
                    String largoLosa_Rigi= cursor4.getString(cursor4.getColumnIndex(Utilidades.PATOLOGIARIGI.CAMPO_LARGO_LOSA));
                    String ancholosa_Rigi= cursor4.getString(cursor4.getColumnIndex(Utilidades.PATOLOGIARIGI.CAMPO_ANCHO_LOSA));
                    String daño_Rigi= cursor4.getString(cursor4.getColumnIndex(Utilidades.PATOLOGIARIGI.CAMPO_DANIO_PATOLOGIA));
                    String severidad_Rigi= cursor4.getString(cursor4.getColumnIndex(Utilidades.PATOLOGIARIGI.CAMPO_SEVERIDAD));
                    String largo_Rigi= cursor4.getString(cursor4.getColumnIndex(Utilidades.PATOLOGIARIGI.CAMPO_LARGO_PATOLOGIA));
                    String ancho_Rigi= cursor4.getString(cursor4.getColumnIndex(Utilidades.PATOLOGIARIGI.CAMPO_ANCHO_PATOLOGIA));
                    String largorepa_Rigi= cursor4.getString(cursor4.getColumnIndex(Utilidades.PATOLOGIARIGI.CAMPO_LARGO_REPARACION));
                    String anchorepa_Rigi= cursor4.getString(cursor4.getColumnIndex(Utilidades.PATOLOGIARIGI.CAMPO_ANCHO_REPARACION));
                    String aclaraciones_Rigi= cursor4.getString(cursor4.getColumnIndex(Utilidades.PATOLOGIARIGI.CAMPO_ACLARACIONES));
                    String nombrefoto_Rigi= cursor4.getString(cursor4.getColumnIndex(Utilidades.PATOLOGIARIGI.CAMPO_NOMBRE_FOTO));

                    //Se Llenan las casillas de la hoja Pato. Rigi., con los campos de la tabla PatologiaRigi
                    sheet4.addCell(new Label(0, ilPF, id_pato_Rigi));
                    sheet4.addCell(new Label(1, ilPF, id_segmento_Rigi));
                    sheet4.addCell(new Label(2, ilPF, nom_carretera_Rigi));
                    sheet4.addCell(new Label(3, ilPF, abscisa_Rigi));
                    sheet4.addCell(new Label(4, ilPF, latitud_Rigi));
                    sheet4.addCell(new Label(5, ilPF, longitud_Rigi));
                    sheet4.addCell(new Label(6, ilPF, Nolosa_Rigi));
                    sheet4.addCell(new Label(7, ilPF, letralosa_Rigi));
                    sheet4.addCell(new Label(8, ilPF, largoLosa_Rigi));
                    sheet4.addCell(new Label(9, ilPF, ancholosa_Rigi));
                    sheet4.addCell(new Label(10, ilPF, daño_Rigi));
                    sheet4.addCell(new Label(11, ilPF, severidad_Rigi));
                    sheet4.addCell(new Label(12, ilPF, largo_Rigi));
                    sheet4.addCell(new Label(13, ilPF, ancho_Rigi));
                    sheet4.addCell(new Label(14, ilPF, largorepa_Rigi));
                    sheet4.addCell(new Label(15, ilPF, anchorepa_Rigi));
                    sheet4.addCell(new Label(16, ilPF, aclaraciones_Rigi));
                    sheet4.addCell(new Label(17, ilPF, nombrefoto_Rigi));

                }while(cursor4.moveToNext());
            }

            //Se cierra el acceso a las tablas de la base de datos
            cursor.close();
            cursor1.close();
            cursor2.close();
            cursor3.close();
            cursor4.close();
            //Se escribe el libro
            workbook.write();
            //Se cierra el libro
            workbook.close();

            Toast.makeText(getActivity(), "Se exporto el documento "+csvFile, Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
