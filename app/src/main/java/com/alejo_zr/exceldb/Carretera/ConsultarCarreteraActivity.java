package com.alejo_zr.exceldb.Carretera;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alejo_zr.exceldb.BaseDatos;
import com.alejo_zr.exceldb.MainActivity;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.entidades.Carretera;
import com.alejo_zr.exceldb.entidades.PatoFlex;
import com.alejo_zr.exceldb.entidades.PatoRigi;
import com.alejo_zr.exceldb.entidades.SegmentoFlex;
import com.alejo_zr.exceldb.entidades.SegmentoRigi;
import com.alejo_zr.exceldb.utilidades.Utilidades;

import java.util.ArrayList;

public class ConsultarCarreteraActivity extends AppCompatActivity {

    //Se declaran las variables y objetos java

    private ListView listViewCarreteras;
    private ArrayList<String> listaInformacion;
    private ArrayList<Carretera> listaCarreteras;
    private ArrayList<SegmentoFlex> listaSegmentosF;
    private ArrayList<SegmentoRigi> listaSegmentosR;
    private ArrayList<PatoFlex> listaPatologiasFlex;
    private ArrayList<PatoRigi> listaPatologiasRigi;

    private BaseDatos baseDatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_carretera);


        baseDatos=new BaseDatos(this);
        //Se enlazanlos objetos con los views
        listViewCarreteras= (ListView) findViewById(R.id.listViewCarretera);

        consultarListaCarreteras();
        //Se le asigna el diseño y la lista que va a cargar el listview
        ArrayAdapter adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
        listViewCarreteras.setAdapter(adaptador);

        listViewCarreteras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                //Al seleccionar un item de la lista, este toma su posición y la envia a CarreteraActivity
                Carretera carretera=listaCarreteras.get(pos);

                Intent intent=new Intent(ConsultarCarreteraActivity.this,CarreteraActivity.class);

                Bundle bundle=new Bundle();
                bundle.putSerializable("carretera",carretera);

                intent.putExtras(bundle);
                startActivity(intent);


            }
        });


        cargarSegmentosFlex();
        editarId();

    }


    protected void onStart() {
        super.onStart();
        baseDatos=new BaseDatos(this);

        listViewCarreteras= (ListView) findViewById(R.id.listViewCarretera);

        consultarListaCarreteras();


        ArrayAdapter adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
        listViewCarreteras.setAdapter(adaptador);

        listViewCarreteras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                Carretera carretera=listaCarreteras.get(pos);

                Intent intent=new Intent(ConsultarCarreteraActivity.this,CarreteraActivity.class);

                Bundle bundle=new Bundle();
                bundle.putSerializable("carretera",carretera);

                intent.putExtras(bundle);
                startActivity(intent);


            }
        });

        editarId();
    }

    private void consultarListaCarreteras() {
        SQLiteDatabase db=baseDatos.getReadableDatabase();

        Carretera carretera=null;
        listaCarreteras= new ArrayList<Carretera>();
        //select * from carretera
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.CARRETERA.TABLA_CARRETERA,null);

        while (cursor.moveToNext()){
            carretera=new Carretera();
            carretera.setId(cursor.getInt(0));
            carretera.setNombreCarretera(cursor.getString(1));
            carretera.setCodCarretera(cursor.getString(2));
            carretera.setTerritorial(cursor.getString(3));
            carretera.setAdmon(cursor.getString(4));
            carretera.setLevantado(cursor.getString(5));

            listaCarreteras.add(carretera);
        }

        obtenerLista();
    }

    private void editarId() {

        /*Al momento de ser eliminada una carretera, se carga esta actividad, y se actulizan los ID's de
           todos los datos de la base de datos     */
        editarIdCarreteras();
        cargarSegmentosFlex();
        cargarSegmentosRigi();
        cargarPatoFlex();
        cargarPatoRigi();
    }

    private void editarIdCarreteras() {

        SQLiteDatabase dbC = baseDatos.getWritableDatabase();

        int id=1;
        for (int i=0; i<listaCarreteras.size();i++) {

            double idCarretera = listaCarreteras.get(i).getId();
            double modulo = idCarretera / id;
            String idS = new String("" + idCarretera);
            if (modulo != 1) {
                ContentValues values = new ContentValues();
                String[] parametros={idS};
                String carreteraId;
                carreteraId = ("" + id);
                values.put(Utilidades.CARRETERA.CAMPO_ID_CARRETERA,carreteraId);
                dbC.update(Utilidades.CARRETERA.TABLA_CARRETERA,values,Utilidades.CARRETERA.CAMPO_ID_CARRETERA+"=?",parametros);
            }
            id = id + 1;

        }

        dbC.close();
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
            segmento.setIs(cursor.getString(10));

            listaSegmentosF.add(segmento);
        }
        editarIdSegFlex();
    }
    private void editarIdSegFlex() {

        SQLiteDatabase dbSF = baseDatos.getWritableDatabase();

        int id=1;
        for (int i=0; i<listaSegmentosF.size();i++) {

            double idCarretera = listaSegmentosF.get(i).getId_segmento();
            double modulo = idCarretera / id;
            String idS = new String("" + idCarretera);
            if (modulo != 1) {
                ContentValues values = new ContentValues();
                String[] parametros={idS};
                String carreteraId;
                carreteraId = ("" + id);
                values.put(Utilidades.SEGMENTOFLEX.CAMPO_ID_SEGMENTO,carreteraId);
                dbSF.update(Utilidades.SEGMENTOFLEX.TABLA_SEGMENTO,values,Utilidades.SEGMENTOFLEX.CAMPO_ID_SEGMENTO+"=?",parametros);
            }
            id = id + 1;

        }
        dbSF.close();
    }

    private void cargarSegmentosRigi() {
        SQLiteDatabase db=baseDatos.getReadableDatabase();

        SegmentoRigi segmento=null;
        listaSegmentosR= new ArrayList<SegmentoRigi>();
        //select * from carretera
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.SEGMENTORIGI.TABLA_SEGMENTO,null);
        while(cursor.moveToNext()){
            segmento = new SegmentoRigi();
            segmento.setId_segmento(cursor.getInt(0));
            segmento.setNombre_carretera(cursor.getString(1));
            segmento.setnCalzadas(cursor.getString(2));
            segmento.setnCarriles(cursor.getString(3));
            segmento.setEspesorLosa(cursor.getString(4));
            segmento.setAnchoBerma(cursor.getString(5));
            segmento.setPri(cursor.getString(6));
            segmento.setPrf(cursor.getString(7));
            segmento.setComentarios(cursor.getString(8));
            segmento.setFecha(cursor.getString(9));
            segmento.setIs(cursor.getString(10));

            listaSegmentosR.add(segmento);
        }
        editarIdSegmentoRigi();
    }

    private void editarIdSegmentoRigi() {
        SQLiteDatabase dbSR = baseDatos.getWritableDatabase();

        int id = 1;
        for (int i = 0; i < listaSegmentosR.size(); i++) {

            double idCarretera = listaSegmentosR.get(i).getId_segmento();
            double modulo = idCarretera / id;
            String idS = new String("" + idCarretera);
            if (modulo != 1) {
                ContentValues values = new ContentValues();
                String[] parametros = {idS};
                String carreteraId;
                carreteraId = ("" + id);
                values.put(Utilidades.SEGMENTORIGI.CAMPO_ID_SEGMENTO, carreteraId);
                dbSR.update(Utilidades.SEGMENTORIGI.TABLA_SEGMENTO, values, Utilidades.SEGMENTORIGI.CAMPO_ID_SEGMENTO + "=?", parametros);
            }
            id = id + 1;

        }

        dbSR.close();
    }

    private void cargarPatoFlex() {

        SQLiteDatabase db=baseDatos.getReadableDatabase();

        PatoFlex patoFlex=null;
        listaPatologiasFlex= new ArrayList<PatoFlex>();
        //select * from carretera
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.PATOLOGIAFLEX.TABLA_PATOLOGIA,null);

        while(cursor.moveToNext()){
            patoFlex = new PatoFlex();
            patoFlex.setId_patoFlex(cursor.getInt(0));
            patoFlex.setId_segmento_patoFlex(cursor.getInt(1));
            patoFlex.setNombre_carretera_patoFlex(cursor.getString(2));
            patoFlex.setAbscisa(cursor.getString(3));
            patoFlex.setLatitud(cursor.getString(4));
            patoFlex.setLongitud(cursor.getString(5));
            patoFlex.setDanio(cursor.getString(6));
            patoFlex.setCarril(cursor.getString(7));
            patoFlex.setSeveridad(cursor.getString(8));
            patoFlex.setLargoDanio(cursor.getString(9));
            patoFlex.setAnchoDanio(cursor.getString(10));
            patoFlex.setLargoRepa(cursor.getString(11));
            patoFlex.setAnchoRepa(cursor.getString(12));
            patoFlex.setAclaraciones(cursor.getString(13));
            patoFlex.setNombreFoto(cursor.getString(14));
            patoFlex.setFoto(cursor.getString(15));
            patoFlex.setIs(cursor.getString(16));

            listaPatologiasFlex.add(patoFlex);
        }
        editarIdPatoFlex();
    }

    private void editarIdPatoFlex() {

        SQLiteDatabase dbPF = baseDatos.getWritableDatabase();
        int id = 1;
        for (int i = 0; i < listaPatologiasFlex.size(); i++) {

            double idSegFlex = listaPatologiasFlex.get(i).getId_patoFlex();
            double modulo = idSegFlex / id;
            String idS = new String("" + idSegFlex);
            String iS= new String(""+listaPatologiasFlex.get(i).getIs());
            for(int l =0;l<listaSegmentosF.size();l++)
            {
                boolean isegmento = iS.equals(listaSegmentosF.get(l).getIs());
                String idSegmento = new String(""+listaSegmentosF.get(l).getId_segmento());
                if(isegmento==true){
                    if (modulo != 1) {
                        ContentValues values = new ContentValues();
                        String[] parametros = {idS};
                        String patoflexId = (""+id);
                        values.put(Utilidades.PATOLOGIAFLEX.CAMPO_ID_PATOLOGIA, patoflexId);
                        values.put(Utilidades.PATOLOGIAFLEX.CAMPO_ID_SEGMENTO_PATOLOGIA,idSegmento);
                        dbPF.update(Utilidades.PATOLOGIAFLEX.TABLA_PATOLOGIA, values, Utilidades.PATOLOGIAFLEX.CAMPO_ID_PATOLOGIA + "=?", parametros);
                    }
                }
            }

            id = id + 1;

        }

        dbPF.close();
    }

    private void cargarPatoRigi() {
        SQLiteDatabase db=baseDatos.getReadableDatabase();

        PatoRigi patoRigi=null;
        listaPatologiasRigi= new ArrayList<PatoRigi>();
        //select * from carretera
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.PATOLOGIARIGI.TABLA_PATOLOGIA,null);

        while(cursor.moveToNext()){
            patoRigi = new PatoRigi();
            patoRigi.setId_patoRigi(cursor.getInt(0));
            patoRigi.setId_segmento_patoRigi(cursor.getString(1));
            patoRigi.setNombre_carretera_patoRigi(cursor.getString(2));
            patoRigi.setAbscisa(cursor.getString(3));
            patoRigi.setLatitud(cursor.getString(4));
            patoRigi.setLongitud(cursor.getString(5));
            patoRigi.setNo_placa(cursor.getString(6));
            patoRigi.setLetra(cursor.getString(7));
            patoRigi.setLargoLoza(cursor.getString(8));
            patoRigi.setAnchoLoza(cursor.getString(9));
            patoRigi.setDanio(cursor.getString(10));
            patoRigi.setSeveridad(cursor.getString(11));
            patoRigi.setLargoDanio(cursor.getString(12));
            patoRigi.setAnchoDanio(cursor.getString(13));
            patoRigi.setLargoRepa(cursor.getString(14));
            patoRigi.setAnchoRepa(cursor.getString(15));
            patoRigi.setAclaraciones(cursor.getString(16));
            patoRigi.setNombreFoto(cursor.getString(17));
            patoRigi.setFoto(cursor.getString(18));
            patoRigi.setIs(cursor.getString(19));

            listaPatologiasRigi.add(patoRigi);

        }
        editarIdPatoRigi();
    }

    private void editarIdPatoRigi() {

        SQLiteDatabase dbPR = baseDatos.getWritableDatabase();
        //Toast.makeText(getApplicationContext(),"editgarIdPatoRigi",Toast.LENGTH_SHORT).show();
        int id = 1;
        for (int i = 0; i < listaPatologiasRigi.size(); i++) {

            double idPatoRigi = listaPatologiasRigi.get(i).getId_patoRigi();
            double modulo = idPatoRigi / id;
            String idS = new String("" + idPatoRigi);
            String iS= new String(""+listaPatologiasRigi.get(i).getIs());
            //Toast.makeText(getApplicationContext(),"idPatoRigi "+idPatoRigi+" -id "+id+" -M "+modulo,Toast.LENGTH_SHORT).show();
            for(int l =0;l<listaSegmentosR.size();l++)
            {
                //Toast.makeText(getApplicationContext(),"ENTRA 2 FOR-"+" iS "+iS+" -listaSEgmentosR "+listaSegmentosR.get(l).getIs(),Toast.LENGTH_SHORT).show();
                boolean isegmento = iS.equals(listaSegmentosR.get(l).getIs());
                String idSegmento = new String(""+listaSegmentosR.get(l).getId_segmento());
                if(isegmento==true){
                    if (modulo != 1) {
                        //Toast.makeText(getApplicationContext(),"EDITA PATO RIGI",Toast.LENGTH_SHORT).show();
                        ContentValues values = new ContentValues();
                        String[] parametros = {idS};
                        String patoflexId = (""+id);
                        values.put(Utilidades.PATOLOGIARIGI.CAMPO_ID_PATOLOGIA, patoflexId);
                        values.put(Utilidades.PATOLOGIARIGI.CAMPO_ID_SEGMENTO_PATOLOGIA,idSegmento);
                        dbPR.update(Utilidades.PATOLOGIARIGI.TABLA_PATOLOGIA, values, Utilidades.PATOLOGIARIGI.CAMPO_ID_PATOLOGIA + "=?", parametros);
                    }
                }
            }

            id = id + 1;
        }

        dbPR.close();
    }


    private void obtenerLista() {
        listaInformacion=new ArrayList<String>();

        for (int i=0; i<listaCarreteras.size();i++){
            listaInformacion.add(listaCarreteras.get(i).getNombreCarretera()+" - "+listaCarreteras.get(i).getCodCarretera()+" - "
                    +listaCarreteras.get(i).getTerritorial());
        }

    }

    public void onClick(View view) {
    //Al oprimir un boton entra a este metodo, y dependiendo del selecionado se selecciona el caso
        Intent intent =null;
        switch (view.getId()){

            case R.id.floabtnAddCarretera:
                //Abre la actividad RegistroCarretera
                intent = new Intent(ConsultarCarreteraActivity.this,RegistroCarreteraActivity.class);
                startActivity(intent);
                break;
            case R.id.backConsulCarreteraActivity:
                //Se devuelve la actividad princioal
                intent = new Intent(ConsultarCarreteraActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}




