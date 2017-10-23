package com.alejo_zr.exceldb.entidades;

import java.io.Serializable;

/**
 * Created by JULIAN on 09/10/2017.
 */

public class PatoRigi implements Serializable {
    private Integer id_patoRigi;
    private String nombre_carretera_patoRigi;
    private String id_segmento_patoRigi;
    private String abscisa;
    private String latitud;
    private String longitud;
    private String no_placa;
    private String letra;
    private String largoLoza;
    private String anchoLoza;
    private String danio;
    private String severidad;
    private String largoDanio;
    private String anchoDanio;
    private String largoRepa;
    private String anchoRepa;
    private String aclaraciones;
    private String nombreFoto;
    private String foto;

    public PatoRigi(Integer id_patoRigi, String nombre_carretera_patoRigi, String id_segmento_patoRigi, String abscisa, String latitud, String longitud,
                    String no_placa, String letra, String largoLoza, String anchoLoza, String danio, String severidad, String largoDanio, String anchoDanio,
                    String largoRepa, String anchoRepa, String aclaraciones, String nombreFoto, String foto) {
        this.id_patoRigi = id_patoRigi;
        this.nombre_carretera_patoRigi = nombre_carretera_patoRigi;
        this.id_segmento_patoRigi = id_segmento_patoRigi;
        this.abscisa = abscisa;
        this.latitud = latitud;
        this.longitud = longitud;
        this.no_placa = no_placa;
        this.letra = letra;
        this.largoLoza = largoLoza;
        this.anchoLoza = anchoLoza;
        this.danio = danio;
        this.severidad = severidad;
        this.largoDanio = largoDanio;
        this.anchoDanio = anchoDanio;
        this.largoRepa = largoRepa;
        this.anchoRepa = anchoRepa;
        this.aclaraciones = aclaraciones;
        this.nombreFoto = nombreFoto;
        this.foto = foto;
    }

    public PatoRigi() {

    }

    public Integer getId_patoRigi() {
        return id_patoRigi;
    }

    public void setId_patoRigi(Integer id_patoRigi) {
        this.id_patoRigi = id_patoRigi;
    }

    public String getNombre_carretera_patoRigi() {
        return nombre_carretera_patoRigi;
    }

    public void setNombre_carretera_patoRigi(String nombre_carretera_patoRigi) {
        this.nombre_carretera_patoRigi = nombre_carretera_patoRigi;
    }

    public String getId_segmento_patoRigi() {
        return id_segmento_patoRigi;
    }

    public void setId_segmento_patoRigi(String id_segmento_patoRigi) {
        this.id_segmento_patoRigi = id_segmento_patoRigi;
    }

    public String getAbscisa() {
        return abscisa;
    }

    public void setAbscisa(String abscisa) {
        this.abscisa = abscisa;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getNo_placa() {
        return no_placa;
    }

    public void setNo_placa(String no_placa) {
        this.no_placa = no_placa;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getLargoLoza() {
        return largoLoza;
    }

    public void setLargoLoza(String largoLoza) {
        this.largoLoza = largoLoza;
    }

    public String getAnchoLoza() {
        return anchoLoza;
    }

    public void setAnchoLoza(String anchoLoza) {
        this.anchoLoza = anchoLoza;
    }

    public String getDanio() {
        return danio;
    }

    public void setDanio(String danio) {
        this.danio = danio;
    }

    public String getSeveridad() {
        return severidad;
    }

    public void setSeveridad(String severidad) {
        this.severidad = severidad;
    }

    public String getLargoDanio() {
        return largoDanio;
    }

    public void setLargoDanio(String largoDanio) {
        this.largoDanio = largoDanio;
    }

    public String getAnchoDanio() {
        return anchoDanio;
    }

    public void setAnchoDanio(String anchoDanio) {
        this.anchoDanio = anchoDanio;
    }

    public String getLargoRepa() {
        return largoRepa;
    }

    public void setLargoRepa(String largoRepa) {
        this.largoRepa = largoRepa;
    }

    public String getAnchoRepa() {
        return anchoRepa;
    }

    public void setAnchoRepa(String anchoRepa) {
        this.anchoRepa = anchoRepa;
    }

    public String getAclaraciones() {
        return aclaraciones;
    }

    public void setAclaraciones(String aclaraciones) {
        this.aclaraciones = aclaraciones;
    }

    public String getNombreFoto() {
        return nombreFoto;
    }

    public void setNombreFoto(String nombreFoto) {
        this.nombreFoto = nombreFoto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}

