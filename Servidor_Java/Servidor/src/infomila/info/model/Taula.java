/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infomila.info.model;

/**
 *
 * @author averd
 */
public class Taula {
    private int numero;
    private long codi;
    private String nomCambrer;
    private int numPlats;
    private boolean es_meva;
    private int platsPreparats;
    private int platsPendents;

    public Taula(int numero, long codi, String nomCambrer, int numPlats, int platsPreparats, int platsPendents, boolean es_meva) {
        this.numero = numero;
        this.codi = codi;
        this.nomCambrer = nomCambrer;
        this.numPlats = numPlats;
        this.platsPreparats = platsPreparats;
        this.platsPendents = platsPendents;
        this.es_meva = es_meva;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public long getCodi() {
        return codi;
    }

    public void setCodi(long codi) {
        this.codi = codi;
    }

    public String getNomCambrer() {
        return nomCambrer;
    }

    public void setNomCambrer(String nomCambrer) {
        this.nomCambrer = nomCambrer;
    }

    public int getNumPlats() {
        return numPlats;
    }

    public void setNumPlats(int numPlats) {
        this.numPlats = numPlats;
    }

    public int getPlatsPreparats() {
        return platsPreparats;
    }

    public void setPlatsPreparats(int platsPreparats) {
        this.platsPreparats = platsPreparats;
    }

    public int getPlatsPendents() {
        return platsPendents;
    }

    public void setPlatsPendents(int platsPendents) {
        this.platsPendents = platsPendents;
    }

    public boolean isEs_meva() {
        return es_meva;
    }

    public void setEs_meva(boolean es_meva) {
        this.es_meva = es_meva;
    }
    
    

    @Override
    public String toString() {
        return "Taula{" + "numero=" + numero + ", codi=" + codi + ", nomCambrer=" + nomCambrer + ", numPlats=" + numPlats + ", platsPreparats=" + platsPreparats + ", platsPendents=" + platsPendents + '}';
    }
    
    
    
    
}
