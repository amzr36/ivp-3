package com.alejo_zr.exceldb.utilidades;

import android.provider.BaseColumns;

/**
 * Created by Alejo on 21/09/2017.
 */

public class Utilidades {


    public static abstract class CARRETERA implements BaseColumns {
        //Constantes tabla Carretera
        public static final String TABLA_CARRETERA="carretera";
        public static final String CAMPO_ID_CARRETERA="id";
        public static final String CAMPO_NOMBRE_CARRETERA="nombreCarretera";
        public static final String CAMPO_CODIGO_CARRETERA="codigoCarretera";
        public static final String CAMPO_TERRITO_CARRETERA="territorial";
        public static final String CAMPO_ADMON_CARRETERA="admon";
        public static final String CAMPO_LEVANTADO_CARRETERA="levantado";

        // Sentencia para la creación de una tabla
        public static final  String CREAR_TABLA_CARRETERA = "CREATE TABLE "+TABLA_CARRETERA+
                " ("+CAMPO_ID_CARRETERA+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_NOMBRE_CARRETERA+" TEXT ,"+CAMPO_CODIGO_CARRETERA+" TEXT,"
                +CAMPO_TERRITO_CARRETERA+" TEXT,"+CAMPO_ADMON_CARRETERA+" TEXT,"+CAMPO_LEVANTADO_CARRETERA+" TEXT)";

    }

    public static abstract class SEGMENTOFLEX implements BaseColumns {
        //Constantes tabla Segmentos
        public static final String TABLA_SEGMENTO = "segmento_Flex";
        public static final String CAMPO_ID_SEGMENTO = "id_segmento";
        public static final String CAMPO_NOMBRE_CARRETERA_SEGMENTO = "nombre_carretera";
        public static final String CAMPO_CALZADAS_SEGMENTO = "numero_calzadas";
        public static final String CAMPO_CARRILES_SEGMENTO = "numero_carriles";
        public static final String CAMPO_ANCHO_CARRIL = "ancho_carril";
        public static final String CAMPO_ANCHO_BERMA = "ancho_berma";
        public static final String CAMPO_PRI_SEGMENTO = "pri";
        public static final String CAMPO_PRF_SEGMENTO = "prf";
        public static final String CAMPO_COMENTARIOS = "comentarios";
        public static final String CAMPO_FECHA="fecha";


        public static final String CREAR_TABLA_SEGMENTO = "CREATE TABLE " +
                "" + TABLA_SEGMENTO + " (" + CAMPO_ID_SEGMENTO + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ CAMPO_NOMBRE_CARRETERA_SEGMENTO + " TEXT,"+
                CAMPO_CALZADAS_SEGMENTO +" TEXT," + CAMPO_CARRILES_SEGMENTO + " TEXT," + CAMPO_ANCHO_CARRIL + " TEXT," + CAMPO_ANCHO_BERMA +" TEXT," +
                CAMPO_PRI_SEGMENTO + " TEXT,"+ CAMPO_PRF_SEGMENTO + " TEXT," + CAMPO_COMENTARIOS +" TEXT,"+CAMPO_FECHA +  " TEXT)";
    }

    public static abstract class SEGMENTORIGI implements BaseColumns {
        public static final String TABLA_SEGMENTO = "segmento_Rigi";
        public static final String CAMPO_ID_SEGMENTO = "id_segmento";
        public static final String CAMPO_NOMBRE_CARRETERA_SEGMENTO = "nombre_carretera";
        public static final String CAMPO_CALZADAS_SEGMENTO = "numero_calzadas";
        public static final String CAMPO_CARRILES_SEGMENTO = "numero_carriles";
        public static final String CAMPO_ESPESOR_LOSA = "espesor_losa";
        public static final String CAMPO_ANCHO_BERMA = "ancho_berma";
        public static final String CAMPO_PRI_SEGMENTO = "pri";
        public static final String CAMPO_PRF_SEGMENTO = "prf";
        public static final String CAMPO_COMENTARIOS = "comentarios";
        public static final String CAMPO_FECHA="fecha";


        public static final String CREAR_TABLA_SEGMENTO = "CREATE TABLE " +
                "" + TABLA_SEGMENTO + " (" + CAMPO_ID_SEGMENTO + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CAMPO_NOMBRE_CARRETERA_SEGMENTO + " TEXT,"+
                CAMPO_CALZADAS_SEGMENTO +" TEXT," + CAMPO_CARRILES_SEGMENTO + " TEXT,"+ CAMPO_ESPESOR_LOSA+" TEXT,"  + CAMPO_ANCHO_BERMA +" TEXT," + CAMPO_PRI_SEGMENTO+" TEXT,"
                + CAMPO_PRF_SEGMENTO + " TEXT," + CAMPO_COMENTARIOS + " TEXT,"+CAMPO_FECHA +" TEXT)";

    }

    public static abstract class PATOLOGIAFLEX implements BaseColumns {

        //Constantes tabla patologia
        public static final String TABLA_PATOLOGIA = "patologia_flex";
        public static final String CAMPO_ID_PATOLOGIA = "id_patologia";
        public static final String CAMPO_ID_SEGMENTO_PATOLOGIA = "id_segmento";
        public static final String CAMPO_NOMBRE_CARRETERA_PATOLOGIA = "nom_carretera";
        public static final String CAMPO_ABSCISA_PATOLOGIA= "abscisa_flex";
        public static final String CAMPO_LATITUD="latitud";
        public static final String CAMPO_LONGITUD="longitud";
        public static final String CAMPO_CARRIL_PATOLOGIA = "carril";
        public static final String CAMPO_DANIO_PATOLOGIA = "daño";
        public static final String CAMPO_SEVERIDAD="severidad";
        public static final String CAMPO_LARGO_PATOLOGIA = "largo";
        public static final String CAMPO_ANCHO_PATOLOGIA = "ancho";
        public static final String CAMPO_LARGO_REPARACION = "largo_reparacion";
        public static final String CAMPO_ANCHO_REPARACION = "ancho_reparacion";
        public static final String CAMPO_ACLARACIONES = "aclaraciones";
        public static final String CAMPO_NOMBRE_FOTO = "nombre_foto";
        public static final String CAMPO_FOTO_DANIO = "foto_daño";


        // Sentencia SQL para la creación de una tabla
        public static final String CREAR_TABLA_PATOLOGIA = "CREATE TABLE " + TABLA_PATOLOGIA +
                " (" + CAMPO_ID_PATOLOGIA + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ CAMPO_ID_SEGMENTO_PATOLOGIA + " TEXT," + CAMPO_NOMBRE_CARRETERA_PATOLOGIA + " TEXT,"+ CAMPO_ABSCISA_PATOLOGIA + " TEXT,"+
                CAMPO_LATITUD + " TEXT,"+ CAMPO_LONGITUD+ " TEXT," + CAMPO_DANIO_PATOLOGIA + " TEXT ,"+CAMPO_CARRIL_PATOLOGIA + " TEXT ,"
                + CAMPO_SEVERIDAD + " TEXT," +CAMPO_LARGO_PATOLOGIA + " TEXT," + CAMPO_ANCHO_PATOLOGIA + " TEXT,"+ CAMPO_LARGO_REPARACION + " TEXT," +
                CAMPO_ANCHO_REPARACION + " TEXT," + CAMPO_ACLARACIONES + " TEXT,"+ CAMPO_NOMBRE_FOTO + " TEXT," + CAMPO_FOTO_DANIO + " TEXT)";
    }


    public static abstract class FOTOFLEX implements BaseColumns {
        public static final String TABLA_FOTO = "fotos";
        public static final String CAMPO_ID_FOTO = "id_foto";
        public static final String CAMPO_NOMBRE_CARRETERA_FOTO = "nombreCarretera_foto";
        public static final String CAMPO_ID_SEGMENTO_FOTO = "segmento_foto";
        public static final String CAMPO_ID_PATOLOGIA_FOTO = "patologia_foto";


        // Sentencia SQL para la creación de una tabla
        public static final String CREAR_TABLA_FOTO = "CREATE TABLE " + TABLA_FOTO +
                " (" + CAMPO_ID_FOTO + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CAMPO_NOMBRE_CARRETERA_FOTO + " TEXT ," + CAMPO_ID_SEGMENTO_FOTO + " TEXT,"
                + CAMPO_ID_PATOLOGIA_FOTO + " TEXT)";
    }


    public static abstract class PATOLOGIARIGI implements BaseColumns {

        //Constantes tabla patologia
        public static final String TABLA_PATOLOGIA = "patologia_Rigi";
        public static final String CAMPO_ID_PATOLOGIA = "id_patologiaRigi";
        public static final String CAMPO_ID_SEGMENTO_PATOLOGIA = "id_segmentoRigi";
        public static final String CAMPO_NOMBRE_CARRETERA_PATOLOGIA = "nom_carreteraRigi";
        public static final String CAMPO_ABSCISA_PATOLOGIA= "abscisa_Rigi";
        public static final String CAMPO_LATITUD="latitud";
        public static final String CAMPO_LONGITUD="longitud";
        public static final String CAMPO_NUMERO_LOSA="no_losa";
        public static final String CAMPO_LETRA_LOSA="letra_losa";
        public static final String CAMPO_LARGO_LOSA  = "largo_losa";
        public static final String CAMPO_ANCHO_LOSA = "ancho_losa";
        public static final String CAMPO_DANIO_PATOLOGIA = "daño";
        public static final String CAMPO_SEVERIDAD="severidad";
        public static final String CAMPO_LARGO_PATOLOGIA = "largo";
        public static final String CAMPO_ANCHO_PATOLOGIA = "ancho";
        public static final String CAMPO_LARGO_REPARACION = "largo_reparacion";
        public static final String CAMPO_ANCHO_REPARACION = "ancho_reparacion";
        public static final String CAMPO_ACLARACIONES = "aclaraciones";
        public static final String CAMPO_NOMBRE_FOTO = "nombre_foto";
        public static final String CAMPO_FOTO_DANIO = "foto_daño";


        // Sentencia SQL para la creación de una tabla
        public static final String CREAR_TABLA_PATOLOGIA = "CREATE TABLE " + TABLA_PATOLOGIA +
                " (" + CAMPO_ID_PATOLOGIA + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ CAMPO_ID_SEGMENTO_PATOLOGIA + " TEXT," + CAMPO_NOMBRE_CARRETERA_PATOLOGIA + " TEXT,"+ CAMPO_ABSCISA_PATOLOGIA + " TEXT,"+
                CAMPO_LATITUD + " TEXT,"+ CAMPO_LONGITUD+ " TEXT,"+CAMPO_NUMERO_LOSA + " TEXT ,"+CAMPO_LETRA_LOSA + " TEXT ,"
                +CAMPO_LARGO_LOSA + " TEXT ,"+CAMPO_ANCHO_LOSA+ " TEXT ," + CAMPO_DANIO_PATOLOGIA + " TEXT ,"
                + CAMPO_SEVERIDAD + " TEXT," +CAMPO_LARGO_PATOLOGIA + " TEXT," + CAMPO_ANCHO_PATOLOGIA + " TEXT,"+ CAMPO_LARGO_REPARACION + " TEXT," +
                CAMPO_ANCHO_REPARACION + " TEXT," + CAMPO_ACLARACIONES + " TEXT,"+ CAMPO_NOMBRE_FOTO + " TEXT," + CAMPO_FOTO_DANIO + " TEXT)";
    }


    public static abstract class FOTORIGI implements BaseColumns {
        public static final String TABLA_FOTORIGI = "fotos";
        public static final String CAMPO_ID_FOTORIGI = "id_foto";
        public static final String CAMPO_NOMBRE_CARRETERA_FOTORIGI = "nombreCarretera_foto";
        public static final String CAMPO_ID_SEGMENTO_FOTORIGI = "segmento_foto";
        public static final String CAMPO_ID_PATOLOGIA_FOTORIGI = "patologia_foto";


        // Sentencia SQL para la creación de una tabla
        public static final String CREAR_TABLA_FOTORIGI = "CREATE TABLE " + TABLA_FOTORIGI +
                " (" + CAMPO_ID_FOTORIGI + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CAMPO_NOMBRE_CARRETERA_FOTORIGI + " TEXT ," + CAMPO_ID_SEGMENTO_FOTORIGI + " TEXT,"
                + CAMPO_ID_PATOLOGIA_FOTORIGI + " TEXT)";
    }

}
