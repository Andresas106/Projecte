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
public class LiniaComanda {
    private int numero;
    private int quantitat;
    private long codiPlat;
    private String nomPlat;
    private float preu;
    
    
    public LiniaComanda(int numero, int quantitat, long codiComanda, String nomPlat, float preu) {
        this.numero = numero;
        this.quantitat = quantitat;
        this.codiPlat = codiComanda;
        this.nomPlat = nomPlat;
        this.preu = preu;
    }

    public float getPreu() {
        return preu;
    }

    public void setPreu(float preu) {
        this.preu = preu;
    }

    
    
    public long getCodiPlat() {
        return codiPlat;
    }

    public void setCodiPlat(long codiPlat) {
        this.codiPlat = codiPlat;
    }

    public String getNomPlat() {
        return nomPlat;
    }

    public void setNomPlat(String nomPlat) {
        this.nomPlat = nomPlat;
    }
    

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    @Override
    public String toString() {
        return "LiniaComanda{" + "numero=" + numero + ", quantitat=" + quantitat + ", codiComanda=" + codiPlat + '}';
    }
    
    
    
}
