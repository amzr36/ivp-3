package com.alejo_zr.exceldb.entidades;

import java.io.Serializable;

/**
 * Created by Alejo on 21/09/2017.
 */

public class Carretera implements Serializable {

    private Integer id;
    private String levantado;
    private String nombreCarretera;
    private String codCarretera;
    private String territorial;
    private String admon;

    public Carretera(Integer id, String levantado, String nombreCarretera, String codCarretera, String territorial,
                     String admon) {
        this.id = id;
        this.levantado = levantado;
        this.nombreCarretera = nombreCarretera;
        this.codCarretera = codCarretera;
        this.territorial = territorial;
        this.admon = admon;
    }

    public Carretera() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevantado() {
        return levantado;
    }

    public void setLevantado(String levantado) {
        this.levantado = levantado;
    }

    public String getNombreCarretera() {
        return nombreCarretera;
    }

    public void setNombreCarretera(String nombreCarretera) {
        this.nombreCarretera = nombreCarretera;
    }

    public String getCodCarretera() {
        return codCarretera;
    }

    public void setCodCarretera(String codCarretera) {
        this.codCarretera = codCarretera;
    }

    public String getTerritorial() {
        return territorial;
    }

    public void setTerritorial(String territorial) {
        this.territorial = territorial;
    }

    public String getAdmon() {
        return admon;
    }

    public void setAdmon(String admon) {
        this.admon = admon;
    }
}
