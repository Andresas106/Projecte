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
public class Categoria {
    private long codi;
    private String nom;

    public Categoria(long codi, String nom) {
        this.codi = codi;
        this.nom = nom;
    }

    public long getCodi() {
        return codi;
    }

    public void setCodi(long codi) {
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Categoria{" + "codi=" + codi + ", nom=" + nom + '}';
    }
    
    
}
